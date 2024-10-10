package doancuoikhoa.login.service;

import doancuoikhoa.login.data.Data;
import doancuoikhoa.login.entities.Contract;
import doancuoikhoa.login.entities.RentalRequest;
import doancuoikhoa.login.entities.Room;
import doancuoikhoa.login.entities.Tenant;
import doancuoikhoa.login.enums.ContractStatus;
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
        System.out.println("=====================Danh sách phòng trọ===============================================================");
        System.out.printf("%-3s \t %-70s \t %-50s \t %-20s %-10s %-15s \n", "ID", "Mô tả", "Vị trí", "Loại phòng", "Mức giá", "Trạng thái");
        System.out.println("====================================================================================================================================================================================");
        for (Room room : Data.rooms) {
            if (room.getLandLordId() == landLordId) {
                System.out.printf("%-3s \t %-70s \t %-50s \t %-20s %-10s %-15s \n", room.getId(), room.getDescription(), room.getLocation(), room.getPropertyType(), room.getPrice(), room.getRoomStatus());
            }
        }
    }

    public void processRentalRequest(int landLordId, Scanner scanner) {
        System.out.println("=================Yêu cầu thuê phòng===================");
        System.out.printf("%-3s \t %-15s \t %-3s \n", "ID", "ID người thuê", "Id phòng muốn thuê");
        System.out.println("======================================================");
        for (RentalRequest rentalRequest : Data.rentalRequests) {
            if (roomService.findLandLordIdByIdRoom(rentalRequest.getRoomId()) == landLordId) {
                System.out.printf("%-3s \t %-15s \t %-3s \n", rentalRequest.getId(), rentalRequest.getTenantId(), rentalRequest.getRoomId());
            }
        }
        String id, roomId;
        do {
            System.out.println("Nhập id căn phòng mà bạn muốn duyệt");
            id = scanner.nextLine();
            roomId = rentalRequestService.checkRoomById(id);

        } while (roomId == null);
        for (RentalRequest rentalRequest : Data.rentalRequests) {
            if (rentalRequest.getRoomId().equalsIgnoreCase(roomId) && !rentalRequest.isApproved()) {
                rentalRequest.setApproved(true);
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

    public void findPendingCancelContractsByLandLordId(int landLordId, Scanner scanner) {
        Menu menu = new Menu();
        boolean foundContract = false;
        for (Contract contract : Data.contracts) {
            if (contract.getLandLordId() == landLordId && contract.getContractStatus() == ContractStatus.PENDINGCANCEL) {
                contractService.formatContract(contract.getId());
                foundContract = true;
            }
        }
        if(!foundContract){
            System.out.println("Không tìm thấy hợp đồng với id phù hợp.");
            menu.selectMenuLandLord(scanner,landLordId);
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

    public void processCancelContract(Scanner scanner, int landLordId) {
        System.out.println("Bạn có muốn hủy/từ chối nào không (Y/N)");
        Menu menu = new Menu();
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
                System.out.println("Mời bạn lựa chọn 1.Hủy 2.Từ chối");
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
            menu.selectMenuLandLord(scanner,landLordId);

        }else {
            menu.selectMenuLandLord(scanner,landLordId);
        }


    }
}


