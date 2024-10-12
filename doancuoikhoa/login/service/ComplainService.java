package doancuoikhoa.login.service;

import doancuoikhoa.login.data.Data;
import doancuoikhoa.login.entities.Complaint;
import doancuoikhoa.login.entities.Room;
import doancuoikhoa.login.enums.ComplaintStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ComplainService {
    RoomService roomService = new RoomService();
    ContractService contractService = new ContractService();
    // Hàm tạo đơn khiếu nại cho người đi thuê
        public Complaint createTenantComplaintFile(Scanner scanner, int tenantId){
        System.out.println("Mời bạn điền thông tin đơn khiếu nại");
        String id, roomId;
        do{
            System.out.println("Mời bạn nhập Id căn phòng khiếu nại");
            id = scanner.nextLine();
            // Kiểm tra xem có đúng là người dùng này đã kí hợp đồng với căn phòng này không?
            roomId = contractService.findRoomIdByTenantId(tenantId);
            if(!roomId.equalsIgnoreCase(id)){
                System.out.println("Do bạn chưa kí hợp đồng với chủ phòng nên không thể khiếu nại.");
            }
        }while (!roomId.equalsIgnoreCase(id));
        System.out.println("Mời bạn nhập nội dung khiếu nại:");
        String content = scanner.nextLine();
        System.out.println("Tạo khiếu nại thành công. Khiếu nại đang được gửi đến cho quản trị viên.");
        Complaint complaint = new Complaint(tenantId,roomId,content);
        return complaint;
    }
    // Hàm tạo đơn khiếu nại cho chủ trọ
    public Complaint createLandLordComplaintFile(Scanner scanner, int landLordId){
        System.out.println("Mời bạn điền thông tin đơn khiếu nại.");
        System.out.println("Mời bạn nhập nội dung khiếu nại:");
        String content = scanner.nextLine();
        System.out.println("Tạo khiếu nại thành công. Khiếu nại đang được gửi đến cho quản trị viên.");
        Complaint complaint = new Complaint(landLordId,content);
        return complaint;
    }
    public void checkAndLockRoom(String roomId){
            int count = 0;
        for (Complaint complaint: Data.complaints) {
            if(complaint.getRoomId().equalsIgnoreCase(roomId) && complaint.getComplaintStatus() == ComplaintStatus.PENDING){
              count++;
            }
        }
        if(count>5){
            roomService.blockRoom(roomId);
            System.out.println("Room với ID"+ roomId + "đã bị khóa vì đã nhận quá nhiều khiếu nại");
        }
    }
    public void processComplaints(){
        for (Complaint complaint:Data.complaints) {
            if(ComplaintStatus.PENDING.equals(complaint.getComplaintStatus()) && LocalDateTime.now().isAfter(complaint.getResolvedDate())){
                complaint.setComplaintStatus(ComplaintStatus.OVERDUE);
                System.out.println("Đơn khiếu nại"+ complaint.getId()+"đã bị quá hạn");
            }
        }
    }
    public void processPendingComplaint(Scanner scanner){
        System.out.println("Mời bạn nhập Id phiếu khiếu nại cần xử lý");
        String complaintId = scanner.nextLine();
        Complaint complaint = findPendingComplaintById(complaintId);
        if(complaint == null){
            System.out.println("Không tìm thấy khiếu nại với id phù hợp.");
        }else {
            System.out.println("Đã chuyển khiếu nại sang trạng thái đang xử lý.");
        }

    }
    public Complaint findPendingComplaintById(String complaintId){
        for (Complaint complaint: Data.complaints) {
            if(complaint.getId().equalsIgnoreCase(complaintId) && complaint.getComplaintStatus()== ComplaintStatus.PENDING){
                complaint.setComplaintStatus(ComplaintStatus.INREVIEW);
                return complaint;
            }
        }
        return null;
    }
    /*public void displayComplaint(){
        System.out.printf("%-5s %-10s %-10s %-10s %-10s %-15s %-20s %-15s %-20s%n",
                "ID", "Tenant ID", "Landlord ID", "Room ID", "User Type", "Content", "Complaint Date", "Status", "Resolved Date");
        System.out.println("-------------------------------------------------------------------------------------------------------------");

        for (Complaint complaint : Data.complaints) {
            System.out.printf("%-5d %-10d %-10d %-10s %-10s %-15s %-20s %-15s %-20s%n",
                    complaint.getId(),
                    complaint.getTenantId(),
                    complaint.getLandLordId(),
                    complaint.getRoomId() != null ? complaint.getRoomId() : "N/A",
                    complaint.getUserType(),
                    complaint.getContent().length() > 12 ? complaint.getContent().substring(0, 12) + "..." : complaint.getContent(),
                    complaint.getComplainDate().toLocalDate(),
                    complaint.getComplaintStatus(),
                    complaint.getResolvedDate().toLocalDate()
            );
        }
    }
    public void displayComplaintInPending(){
        System.out.printf("%-5s %-10s %-10s %-10s %-10s %-15s %-20s %-15s %-20s%n",
                "ID", "Tenant ID", "Landlord ID", "Room ID", "User Type", "Content", "Complaint Date", "Status", "Resolved Date");
        System.out.println("-------------------------------------------------------------------------------------------------------------");

        for (Complaint complaint : Data.complaints) {
            if(ComplaintStatus.PENDING.equals(complaint.getComplaintStatus()) ){
                System.out.printf("%-5d %-10d %-10d %-10s %-10s %-15s %-20s %-15s %-20s%n",
                        complaint.getId(),
                        complaint.getTenantId(),
                        complaint.getLandLordId(),
                        complaint.getRoomId() != null ? complaint.getRoomId() : "N/A",
                        complaint.getUserType(),
                        complaint.getContent().length() > 12 ? complaint.getContent().substring(0, 12) + "..." : complaint.getContent(),
                        complaint.getComplainDate().toLocalDate(),
                        complaint.getComplaintStatus(),
                        complaint.getResolvedDate().toLocalDate()
                );
            }

        }

    }*/
    public void displayComplaintsByStatus(ComplaintStatus status) {
        List<Complaint> filteredComplaints = new ArrayList<>();

        // Lọc các khiếu nại theo trạng thái truyền vào
        for (Complaint complaint : Data.complaints) {
            if (complaint.getComplaintStatus() == status) {
                filteredComplaints.add(complaint);
            }
        }

        // Hiển thị danh sách các khiếu nại theo trạng thái
        if (filteredComplaints.isEmpty()) {
            System.out.println("Không có khiếu nại nào trong trạng thái " + status);
        } else {
            System.out.println("Danh sách khiếu nại trong trạng thái " + status + ":");
            System.out.println("-------------------------------------------------------------------------------------------------------------");
            System.out.printf("%-5s %-20s %-10s %-10s %-15s %-20s %-15s %-20s%n",
                    "ID", "Người khiếu nại", "Room ID", "User Type", "Nội dung", "Ngày khiếu nại", "Trạng thái", "Hạn xử lý");
            System.out.println("-------------------------------------------------------------------------------------------------------------");

            for (Complaint complaint : filteredComplaints) {
                if(status.equals(complaint.getComplaintStatus()) ){
                    System.out.printf("%-5d %-20d %-10s %-10s %-15s %-20s %-15s %-20s%n",
                            complaint.getId(),
                            complaint.getComplainantId(),
                            complaint.getRoomId() != null ? complaint.getRoomId() : "N/A",
                            complaint.getUserType(),
                            complaint.getContent().length() > 12 ? complaint.getContent().substring(0, 12) + "..." : complaint.getContent(),
                            complaint.getComplainDate().toLocalDate(),
                            complaint.getComplaintStatus(),
                            complaint.getResolvedDate().toLocalDate()
                    );
                }
            }
        }
    }
    }


