package doancuoikhoa.login.service;

import doancuoikhoa.login.data.Data;
import doancuoikhoa.login.entities.Contract;
import doancuoikhoa.login.entities.RentalRequest;
import doancuoikhoa.login.entities.User;
import doancuoikhoa.login.enums.ContractStatus;
import doancuoikhoa.login.utils.Utils;

import java.time.LocalDate;
import java.util.Scanner;

import static doancuoikhoa.login.utils.Utils.DEFAULT_CLAUSE;

public class ContractService {
    RoomService roomService = new RoomService();
    RentalRequestService rentalRequestService = new RentalRequestService();
    public Contract createContract(Scanner scanner,int tenantId, LocalDate startDate, LocalDate endDate){
        System.out.println("Mời bạn nhập vào các thông tin của hợp đồng");
        String id,roomId;
        do{
            System.out.println("Mời bạn nhập id của căn phòng muốn kí hợp đồng ");
            id = scanner.nextLine();
            roomId = rentalRequestService.checkRoomStatusById(id);// check xem có đúng căn phòng đó đã yêu cầu được thuê và được chủ phòng chấp nhận
        }while(roomId ==null);
        int landLordId = roomService.findLandLordIdByIdRoom(id);
        System.out.println("Mời bạn nhập vào các điều khoản bổ sung nếu có (hoặc Enter để bỏ qua):");
        String additionalClause = scanner.nextLine(); // Điều khoản bổ sung từ chủ trọ
        // Kết hợp điều khoản mặc định và điều khoản bổ sung
        String clause = DEFAULT_CLAUSE;
        if (!additionalClause.isEmpty()) {
            clause += "\nCÁC ĐIỀU KHOẢN BỔ SUNG:\n" + additionalClause;
        }
        Contract contract = new Contract(id,tenantId,landLordId,clause,startDate,endDate );
        Data.contracts.add(contract);
        System.out.println("Tạo hợp đồng thành công. Hợp đồng đang được gửi đến cho người thuê");
        return contract;
    }
    public void formatContract(String contractId){
        StringBuilder sb = new StringBuilder();
        boolean foundContract = false;
        for (Contract contract: Data.contracts) {
            if(contract.getId().equalsIgnoreCase(contractId)){
                foundContract = true;
                sb.append("=======================================\n");
                sb.append("               HỢP ĐỒNG THUÊ PHÒNG              \n");
                sb.append("=======================================\n");
                sb.append(String.format("Hợp đồng số      : %s\n", contract.getId()));
                sb.append(String.format("Phòng ID         : %s\n", contract.getRoomId()));
                sb.append(String.format("Người thuê ID    : %d\n", contract.getTenantId()));
                sb.append(String.format("Chủ phòng ID     : %d\n", contract.getLandLordId()));
                sb.append(String.format("Ngày bắt đầu     : %s\n", contract.getStartDate()));
                sb.append(String.format("Ngày kết thúc    : %s\n", contract.getEndDate()));
                sb.append("Điều khoản hợp đồng:\n");
                sb.append(contract.getClause()).append("\n");
                sb.append("=======================================\n");
                sb.append("               CHỮ KÝ CÁC BÊN              \n");
                sb.append("=======================================\n");
                sb.append("Người thuê:  _______________________\n");
                sb.append("Chủ phòng:  _______________________\n");
                sb.append("=======================================\n");

                // Sau khi format xong, in hợp đồng ra
                System.out.println(sb.toString());
                break; // Khi đã tìm thấy hợp đồng, không cần lặp tiếp
            }

        }
        if(!foundContract){
            System.out.println("Không tìm thấy hợp đồng với id yêu cầu.");
        }

    }

    public Contract findPendingContractById(String contractId){
        for (Contract contract: Data.contracts) {
            if(contract.getId().equalsIgnoreCase(contractId) && contract.getContractStatus() == ContractStatus.PENDING){
                return contract;
            }
        }
        System.out.println("Không tìm thấy hợp đồng với Id bạn chọn.");
        return null;
    }
    public Contract findSignedContractById(String contractId){
        for (Contract contract: Data.contracts) {
            if(contract.getId().equalsIgnoreCase(contractId) && contract.getContractStatus() == ContractStatus.SIGNED){
                return contract;
            }
        }
        System.out.println("Không tìm thấy hợp đồng với Id bạn chọn.");
        return null;
    }
    public void changeContractStatusAfterExpiration(){
        for (Contract contract: Data.contracts) {
            if(LocalDate.now().isAfter(contract.getEndDate()) && contract.getContractStatus() == ContractStatus.SIGNED){
                contract.setContractStatus(ContractStatus.COMPLETED);
            }
        }
    }
    public Contract findPendingCancelContractById(String contractId){
        for (Contract contract: Data.contracts) {
            if(contract.getId().equalsIgnoreCase(contractId) && contract.getContractStatus() == ContractStatus.PENDINGCANCEL){
                return contract;
            }
        }
        System.out.println("Không tìm thấy hợp đồng với Id bạn chọn.");
        return null;
    }
    public int findTenantIdByIdRoom(String roomId){
        for (Contract contract: Data.contracts) {
            if(contract.getRoomId().equalsIgnoreCase(roomId) && contract.getContractStatus() == ContractStatus.SIGNED){
                return contract.getTenantId();
            }
        }
        throw new IllegalArgumentException("Không tìm thấy phòng với id: " + roomId);
    }
    public String findRoomIdByTenantId(int tenantId){
        for (Contract contract: Data.contracts) {
            if(contract.getTenantId() == tenantId && contract.getContractStatus() == ContractStatus.SIGNED){
                return contract.getRoomId();
            }
        }
        return null;
    }
    public Contract checkTenantSignedContract(int tenantId, String roomId){
        for (Contract contract:Data.contracts) {
            if(contract.getTenantId()== tenantId && contract.getRoomId().equalsIgnoreCase(roomId)){
                return contract;
            }
        }
        return null;
    }
    }

