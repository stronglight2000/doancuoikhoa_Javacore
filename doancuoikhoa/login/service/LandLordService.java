package doancuoikhoa.login.service;

import doancuoikhoa.login.data.Data;
import doancuoikhoa.login.entities.*;
import doancuoikhoa.login.enums.ContractStatus;
import doancuoikhoa.login.enums.RentalRequestStatus;
import doancuoikhoa.login.enums.RoomStatus;
import doancuoikhoa.login.utils.Utils;
import doancuoikhoa.login.view.Menu;

import java.util.Scanner;

public class LandLordService {
    ContractService contractService = new ContractService();
    RoomService roomService = new RoomService();
    RentalRequestService rentalRequestService = new RentalRequestService();

    // Tìm hợp đồng

    public void displayContractInPendingStatus(int landLordId) {
        for (Contract contract : Data.contracts) {
            Contract contract1 = findContractByLandLordId(landLordId);
            contractService.formatContract(contract1.getId());
        }
    }

    public void displayListRoom(int landLordId) {
        System.out.println("=============================== Danh sách phòng trọ ==============================================================");
        System.out.printf("%-10s %-20s %-30s %-15s %-15s %-10s %-10s\n",
                "ID", "Mô tả", "Vị trí", "Loại phòng", "Giá", "Trạng thái", "Bị khóa");
        System.out.println("==================================================================================================================");

        for (Room room : Data.rooms) {
            if (room.getLandLordId() == landLordId) {
                String roomStatus = (room.getRoomStatus() != null) ? room.getRoomStatus().toString() : "N/A";
                String isBlocked = room.isBlocked() ? "Có" : "Không";

                // Giới hạn mô tả tối đa 20 ký tự
                String description = room.getDescription();
                if (description.length() > 20) {
                    description = description.substring(0, 17) + "...";
                }

                System.out.printf("%-10s %-20s %-30s %-15s %-15s %-10s %-10s\n",
                        room.getId(),
                        room.getDescription(),
                        room.getLocation(),
                        room.getPropertyType(),
                        room.getPrice().toString(),
                        roomStatus,
                        isBlocked
                );
            }
        }
            System.out.println("==================================================================================================================");

    }



    public void displayRentalRequest(User user, Scanner scanner) {
        Menu menu = Menu.getInstance();
        System.out.println("=================Yêu cầu thuê phòng==================================");
        System.out.printf("%-3s \t %-15s \t %-30s %-10s \n", "ID", "ID người thuê", "Id phòng muốn thuê","Trạng thái");
        System.out.println("=====================================================================");
        boolean hasPendingRequests = false;
        for (RentalRequest rentalRequest : Data.rentalRequests) {
            if (roomService.findLandLordIdByIdRoom(rentalRequest.getRoomId()) == user.getId()&&rentalRequest.getStatus() == RentalRequestStatus.PENDING) {
                System.out.printf("%-3s \t %-15s \t %-30s %-10s \n", rentalRequest.getId(), rentalRequest.getTenantId(), rentalRequest.getRoomId(),rentalRequest.getStatus());
                hasPendingRequests = true;
            }
        }
        // Kiểm tra nếu không có yêu cầu nào
        if (!hasPendingRequests) {
            System.out.println("Không có yêu cầu thuê phòng nào đang chờ duyệt.");
            menu.selectMenuLandLord(scanner, user); // Quay lại menu chính nếu không có yêu cầu
            return;
        }
        while(true){
            System.out.println("Bạn muốn chọn \n1. Duyệt \n2. Từ chối \n3.Quay lại ");
            int choice = Utils.inputInteger(scanner);
            switch (choice){
                case 1:
                    approvedRentalRequest(user.getId(), scanner);
                    break;
                case 2:
                    refuseRentalRequest(user.getId(), scanner);
                    break;
                case 3:
                    menu.selectMenuLandLord(scanner, user);
                    return; // Thoát vòng lặp sau khi quay lại menu chính
                default:
                    System.out.println("Không có lựa chọn phù hợp.");
                    break; // Tiếp tục vòng lặp nếu không có lựa chọn phù hợp

            }
        }

    }
    public void refuseRentalRequest(int landLordId, Scanner scanner){
        String id, roomId;
        System.out.println();
        do {
            System.out.println("Nhập id căn phòng mà bạn muốn từ chối duyệt");
            id = scanner.nextLine();
            roomId = rentalRequestService.checkRoomById(id);

        } while (roomId == null);
        for (RentalRequest rentalRequest: Data.rentalRequests) {
            if(rentalRequest.getRoomId().equalsIgnoreCase(roomId) && rentalRequest.getStatus() == RentalRequestStatus.PENDING){
                rentalRequest.setStatus(RentalRequestStatus.REJECTED);
                System.out.println("Đã từ chối cho thuê phòng.");
            }
        }


    }


    public void approvedRentalRequest(int landLordId, Scanner scanner) {
        String id, roomId;
        do {
            System.out.println("Nhập id căn phòng mà bạn muốn duyệt");
            id = scanner.nextLine();
            roomId = rentalRequestService.checkRoomById(id);

        } while (roomId == null);
        for (RentalRequest rentalRequest : Data.rentalRequests) {
            if (rentalRequest.getRoomId().equalsIgnoreCase(roomId) && rentalRequest.getStatus() == RentalRequestStatus.PENDING) {
                rentalRequest.setStatus(RentalRequestStatus.APPROVED);
                System.out.println("Yêu cầu đã được duyệt, bạn có thể tạo hợp đồng.");
                Contract contract = contractService.createContract(scanner, rentalRequest.getTenantId(), rentalRequest.getStartDate(), rentalRequest.getEndDate());
                Data.contracts.add(contract);
                // Sau khi chủ phòng đã tạo hợp đồng, căn phòng đã được chuyển về trạng thái đặt trước.
                Room room = roomService.findRoomById(contract.getRoomId());
                room.setRoomStatus(RoomStatus.RESERVED);
                contractService.formatContract(contract.getId());
            }
        }

    }

    public Contract findContractByLandLordId(int landLordId) {
        for (Contract contract : Data.contracts) {
            if (contract.getLandLordId() == landLordId && contract.getContractStatus() == ContractStatus.PENDING) {
                return contract;
            }
        }
        System.out.println("Không tìm thấy hợp đồng với id chủ phòng yêu cầu.");
        return null;
    }

    public void findSignedContractsByLandLordId(int landLordId) {
        for (Contract contract : Data.contracts) {
            if (contract.getLandLordId() == landLordId && contract.getContractStatus() == ContractStatus.SIGNED) {
                contractService.formatContract(contract.getId());
            }
        }
        System.out.println("Không tìm thấy hợp đồng với ID phù hợp.");
    }

    public void findPendingContractsByLandLordId(int landLordId) {
        for (Contract contract : Data.contracts) {
            if (contract.getLandLordId() == landLordId && contract.getContractStatus() == ContractStatus.PENDING) {
                contractService.formatContract(contract.getId());
            }
        }
        System.out.println("Không tìm thấy hợp đồng với ID phù hợp.");
    }

    public void findPendingCancelContractsByLandLordId(User user, Scanner scanner) {
        Menu menu = Menu.getInstance();
        boolean foundContract = false;
        for (Contract contract : Data.contracts) {
            if (contract.getLandLordId() == user.getId() && contract.getContractStatus() == ContractStatus.PENDINGCANCEL) {
                contractService.formatContract(contract.getId());
                foundContract = true;
            }
        }
        if(!foundContract){
            System.out.println("Không tìm thấy hợp đồng với id phù hợp.");
            menu.selectMenuLandLord(scanner,user);
        }

    }
    public Contract findPendingCancelContractByLandLordId(int landLordId) {
        for (Contract contract : Data.contracts) {
            if (contract.getLandLordId() == landLordId && contract.getContractStatus() == ContractStatus.PENDINGCANCEL) {
                contractService.formatContract(contract.getId());
                return contract;
            }
        }
        System.out.println("Không tìm thấy hợp đồng với id phù hợp.");
        return null;
    }

    public void processCancelContract(Scanner scanner, User user) {
        Menu menu = Menu.getInstance();
        System.out.println("Bạn có muốn hủy/từ chối hợp đồng nào không (Y/N)");
        String choice = scanner.nextLine();
        Contract cancelContract;
        if (choice.equalsIgnoreCase("Y")) {
            do{
                System.out.println("Nhập số hợp đồng mà bạn muốn hủy/từ chối (Id hợp đồng)");
                String id = scanner.nextLine();
                cancelContract = contractService.findPendingCancelContractById(id); //
            }while(cancelContract == null); // Vòng lăp sẽ tìm cho tới khi nào chủ trọ nhập đúng Id của phòng trọ thì thôi.
            boolean isValidChoice = false; // Để kiểm tra tính hợp lệ của lựa chọn
            while (!isValidChoice) {
                System.out.println("Mời bạn lựa chọn 1.Hủy \n2.Từ chối");
                int choice2 = Utils.inputInteger(scanner);
                switch (choice2) {
                    case 1:
                        cancelContract.setContractStatus(ContractStatus.CANCELED);
                        System.out.println("Hủy hợp đồng thành công");
                        isValidChoice = true;
                        break;
                    case 2:
                        cancelContract.setContractStatus(ContractStatus.REJECTED);
                        System.out.println("Đã từ chối hủy hợp đồng");
                        isValidChoice = true;
                        break;
                    default:
                        System.out.println("Lựa chọn không hợp lệ, vui lòng nhập lại");

                }
            }
            // Sau khi xử lý xong chuyển về menu của chủ trọ
            menu.selectMenuLandLord(scanner,user);

        }else {
            menu.selectMenuLandLord(scanner,user);
        }


    }
}


