package doancuoikhoa.login.service;

import doancuoikhoa.login.data.Data;
import doancuoikhoa.login.entities.Complaint;

import java.util.Scanner;

public class ComplainService {
    RoomService roomService = new RoomService();
    ContractService contractService = new ContractService();
        public Complaint createTenantComplaintFile(Scanner scanner){
        System.out.println("Mời bạn điền thông tin đơn khiếu nại");
        System.out.println("Mời bạn nhập Id căn phòng khiếu nại");
        String roomId = scanner.nextLine();
        int landLordId = roomService.findLandLordIdByIdRoom(roomId);
        int tenantId = contractService.findTenantIdByIdRoom(roomId);
        System.out.println("Mời bạn nhập nội dung khiếu nại:");
        String content = scanner.nextLine();
        System.out.println("Tạo khiếu nại thành công. Khiếu nại đang được gửi đến cho quản trị viên.");
        Complaint complaint = new Complaint(tenantId,landLordId,roomId,content);
        Data.complaints.add(complaint);
        return complaint;
    }
    public Complaint createLandLordComplaintFile(Scanner scanner, int landLordId){
        System.out.println("Mời bạn điền thông tin đơn khiếu nại.");
        System.out.println("Mời bạn nhập nội dung khiếu nại:");
        String content = scanner.nextLine();
        System.out.println("Tạo khiếu nại thành công. Khiếu nại đang được gửi đến cho quản trị viên.");
        Complaint complaint = new Complaint(landLordId,content);
        Data.complaints.add(complaint);
        return complaint;
    }
}
