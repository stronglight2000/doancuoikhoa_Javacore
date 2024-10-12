package doancuoikhoa.login.view;

import doancuoikhoa.login.data.Data;
import doancuoikhoa.login.entities.*;
import doancuoikhoa.login.enums.ComplaintStatus;
import doancuoikhoa.login.enums.ContractStatus;
import doancuoikhoa.login.service.*;
import doancuoikhoa.login.utils.Utils;


import java.util.ArrayList;
import java.util.Scanner;

public class Menu {
    private static Menu instance;

    int choice;
    TenantService tenantService = new TenantService();
    RoomService roomService = new RoomService();
    LandLordService landLordService = new LandLordService();
    ContractService contractService = new ContractService();
    ComplainService complainService = new ComplainService();
    AdminService adminService = new AdminService();
    UserService userService = new UserService();
    private Menu(){}
    public static synchronized Menu getInstance(){
        if(instance == null){
            instance = new Menu();
        }
        return instance;
    }
    private void registerMenu(){
        System.out.println("1-Đăng nhập");
        System.out.println("2-Đăng ký");
    }
    public void selectRegisterMenu(Scanner scanner){
        while (true){
            registerMenu();
            choice = Utils.inputInteger(scanner);
            switch (choice){
                case 1:
                    userService.logIn(scanner);
                    break;
                case 2:
                    userService.registerAccount(scanner);
                    userService.logIn(scanner);
                    break;
                default:
                    System.out.println("Yêu cầu không hợp lệ");
                    break;
            }
        }

    }
    private void displayMenuPassWordWrong(){
        System.out.println("1. Đăng nhập lại");
        System.out.println("2. Quên mật khẩu");
    }
    public void selectMenuPassWordWrong(Scanner scanner){
        displayMenuPassWordWrong();
        choice = Utils.inputInteger(scanner);
        switch (choice){
            case 1:
                userService.logIn(scanner);
                break;
            case 2:
                userService.changePassWordWhenForget(scanner);
                break;
        }

    }
    private void displayMenuAdmin(){
        System.out.println("1. Thêm user");
        System.out.println("2. Danh sách người dùng");
        System.out.println("3. Danh sách khiếu nại");
        System.out.println("4. Khóa phòng");
        System.out.println("5. Khóa tài khoản người dùng.");
        System.out.println("6. Đăng xuất");
    }
    private void displayMenuListOfComplaints(){
        System.out.println("1.Danh sách đơn khiếu nại chưa được xử lý");
        System.out.println("2. Danh sách đơn khiếu nại đang xử lý");
        System.out.println("3. Danh sách đơn khiếu nại đã xử lý");
        System.out.println("4. Danh sách đơn khiếu nại đã trễ hạn");
        System.out.println("5. Quay lại");
    }
    public void selectMenuListOfComplanints(Scanner scanner){
        while (true) {
            displayMenuListOfComplaints();
            int choice = Utils.inputInteger(scanner);
            switch (choice) {
                case 1:
                    complainService.displayComplaintsByStatus(ComplaintStatus.PENDING);
                    complainService.processPendingComplaint(scanner);
                    break;
                case 2:
                    complainService.displayComplaintsByStatus(ComplaintStatus.INREVIEW);
                    break;
                case 3:
                    complainService.displayComplaintsByStatus(ComplaintStatus.RESOLVED);
                    break;
                case 4:
                    complainService.displayComplaintsByStatus(ComplaintStatus.OVERDUE);
                    break;
                case 5:
                    selectMenuAdminWhenLogInSuccess(scanner);
                    break;
                default:
                    System.out.println("Không có lựa chọn phù hợp.");
                    break;

            }
        }
    }
    public void selectMenuAdminWhenLogInSuccess(Scanner scanner){
        while (true){
            displayMenuAdmin();
            int choice = Utils.inputInteger(scanner);
            switch (choice){
                case 1:
                    User user = userService.creatNewUser(scanner);
                    Data.users.add(user);
                    break;
                case 2:
                    userService.displayUser();
                    break;
                case 3:
                    selectMenuListOfComplanints(scanner);
                    break;
                case 4:
                    break;
                case 5:
                    adminService.blockUser(scanner);
                    break;
                case 6:
                    selectRegisterMenu(scanner);
                    break;
                default:
                    System.out.println("Không có lựa chọn phù hợp");
                    break;

            }
        }

    }
    private void displayMenuLandLord(){
        System.out.println("1. Thêm phòng trọ");
        System.out.println("2. Sửa thông tin phòng trọ");
        System.out.println("3. Ẩn phòng trọ");
        System.out.println("4. Hiển thị danh sách phòng trọ");
        System.out.println("5. Yêu cầu thuê phòng trọ.");
        System.out.println("6. Hợp đồng thuê trọ");
        System.out.println("7. Sửa thông tin người dùng");
        System.out.println("8. Khiếu nại");
        System.out.println("9. Đăng xuất");
    }
    private void displayLandLordContractMenu(){
        System.out.println("1. Hợp đồng đang chờ người thuê đồng ý.");
        System.out.println("2. Hợp đồng đã được kí.");
        System.out.println("3. Hợp đồng đã hoàn thành.");
        System.out.println("4. Hợp đồng yêu cầu hủy");
        System.out.println("5. Quay lại");
    }
    private void displayLandLordComplainMenu(){
        System.out.println("1. Khiếu nại");
        System.out.println("2. Quay lại");
    }
    public void selectLandLordComplainMenu(Scanner scanner, User user) {
        while (true) {
            displayLandLordComplainMenu();
            int choice = Utils.inputInteger(scanner);
            switch (choice) {
                case 1:
                    Complaint complaint = complainService.createLandLordComplaintFile(scanner, user.getId());
                    Data.complaints.add(complaint);
                    complainService.processComplaints();
                    break;
                case 2:
                    selectMenuLandLord(scanner,user);
                    break;
                default:
                    System.out.println("Không có lựa chọn phù hợp.");
                    break;

            }
        }
    }
    public void selectLandLordContractMenu(Scanner scanner, User user) {
        while (true) {
            displayLandLordContractMenu();
            int choice = Utils.inputInteger(scanner);
            switch (choice) {
                case 1:
                    landLordService.findPendingContractsByLandLordId(user.getId());
                    break;
                case 2:
                    landLordService.findSignedContractsByLandLordId(user.getId());
                    break;
                case 3:
                    break;
                case 4:
                    landLordService.findPendingCancelContractsByLandLordId(user, scanner);
                    landLordService.processCancelContract(scanner, user);
                    break;
                case 5:
                    selectMenuLandLord(scanner, user);
                    break;
                default:
                    System.out.println("Không có lựa chọn phù hợp, mời bạn nhập lại.");
                    break;


            }
        }
    }
    private void displayMenuChangeInformationLandLord(){
        System.out.println("1. Đổi email");
        System.out.println("2. Đổi mật khẩu");
        System.out.println("3. Quay lại.");
    }
    public void selectMenuChangeInformationLandLord(Scanner scanner, User user){
        while(true){
            displayMenuChangeInformationLandLord();
            int choice = Utils.inputInteger(scanner);
            switch (choice){
                case 1:
                    userService.changeEmailWhenLogIn(scanner,user);
                    break;
                case 2:
                    userService.changePassWordWhenLogIn(scanner,user);
                    break;
                case 3:
                    selectMenuLandLord(scanner,user);
                    break;
                default:
                    System.out.println("Không có lựa chọn phù hợp.");
                    break;

            }
        }
    }
    public void selectMenuLandLord(Scanner scanner, User user){
        while (true){
            displayMenuLandLord();
            int choice = Utils.inputInteger(scanner);
            switch (choice){
                case 1:
                    Room room = roomService.createRoom(scanner, user.getId());
                    roomService.insert(room);
                    break;
                case 2:
                    roomService.update(scanner,user);
                    break;
                case 3:
                    roomService.delete(scanner,user);
                    break;
                case 4:
                    landLordService.displayListRoom(user.getId());
                    break;
                case 5:
                    landLordService.displayRentalRequest(user,scanner);
                    break;
                case 6:
                    selectLandLordContractMenu(scanner, user);
                    break;
                case 7:
                    selectMenuChangeInformationLandLord(scanner,user);
                    break;
                case 8:
                    selectLandLordComplainMenu(scanner, user);
                    break;
                case 9:
                    selectRegisterMenu(scanner);
                    break;
                default:
                    System.out.println("Không có chức năng phù hợp");
                    break;

            }
        }
    }
    private void displayMenuTenant(){
        System.out.println("1. Tìm kiếm phòng trọ");
        System.out.println("2. Yêu cầu thuê phòng");
        System.out.println("3. Hợp đồng");
        System.out.println("4. Danh sách phòng trọ yêu thích");
        System.out.println("5. Sửa thông tin người dùng");
        System.out.println("6. Khiếu nại");
        System.out.println("7. Đăng xuất");
    }

    private void displayMenuSearchRoom(){
        System.out.println("1. Tìm kiếm theo khoảng giá");
        System.out.println("2. Tìm kiếm theo địa chỉ");
        System.out.println("3. Tìm kiếm theo loại nhà đất ");
        System.out.println("4. Quay lại");
    }
    public void selectMenuSearchRoom(Scanner scanner, User user){
        while(true){
            displayMenuSearchRoom();
            int choice = Utils.inputInteger(scanner);
            switch (choice){
                case 1:
                    tenantService.searchRoomsByPrice(scanner, user);
                    break;
                case 2: tenantService.searchRoomsByLocation(scanner, user);
                    break;
                case 3:
                    tenantService.searchRoomsByType(scanner, user);
                    break;
                case 4:
                    selectMenuTenant(scanner,user);
                    break;
                default:
                    System.out.printf("Không có lựa chọn phù hợp");
                    break;
            }
        }
    }
    private void displayMenuRequestToRent(){
        System.out.println("1. Yêu cầu thuê phòng");
        System.out.println("2. Yêu cầu thuê phòng bị từ chối");
        System.out.println("3. Quay lại");
    }
    public void selectMenuRequestToRent(Scanner scanner,User user){
        TenantService tenantService = new TenantService();
        while(true){
            displayMenuRequestToRent();
            int choice = Utils.inputInteger(scanner);
            switch (choice){
                case 1:
                    tenantService.requestToRentRoom(scanner, user.getId());
                    break;
                case 2:
                    tenantService.displayRefusedRequest(user.getId());
                    break;
                case 3:
                    selectMenuTenant(scanner,user);
                    break;
                default:
                    System.out.println("Không có lựa chọn phù hợp.");
                    break;

            }
        }
    }
    private void displayTenantContractMenu(){
        System.out.println("1. Danh sách hợp đồng đã được chủ phòng duyệt.");
        System.out.println("2. Danh sách hợp đồng đang kí kết.");
        System.out.println("3. Hủy hợp đồng");
        System.out.println("4. Danh sách hợp đồng bị từ chối hủy.");
        System.out.println("5. Danh sách hợp đồng đã hoàn thành");
        System.out.println("6. Quay lại");
    }
    public void selectTenantContractMenu(Scanner scanner,User user){
        while(true){
           displayTenantContractMenu();
            int choice = Utils.inputInteger(scanner);
            switch (choice){
                case 1:
                    tenantService.displayContractAfterLandLordApproved(user, scanner);
                    break;
                case 2:
                    tenantService.findStatusConTractsByTenantId(user.getId(), ContractStatus.SIGNED);
                    break;
                case 3:
                    tenantService.cancelContract(scanner, user);
                    break;
                case 4:
                    tenantService.findStatusConTractsByTenantId(user.getId(), ContractStatus.REJECTED);
                    break;
                case 5:
                    tenantService.findStatusConTractsByTenantId(user.getId(), ContractStatus.COMPLETED);
                    break;
                case 6:
                    selectMenuTenant(scanner,user);
                    break;
                default:
                    System.out.println("Không có lựa chọn phù hợp.");
                    break;

            }
        }
    }
    private void displayTenantComplaintMenu(){
        System.out.println("1. Khiếu nại");
        System.out.println("2. Quay lại.");
    }
    public void selectTenantComplaintMenu(Scanner scanner, User user){
        while(true){
            displayTenantComplaintMenu();
            int choice = Utils.inputInteger(scanner);
            switch (choice){
                case 1:
                    Complaint complaint = complainService.createTenantComplaintFile(scanner, user.getId());
                    Data.complaints.add(complaint);
                    break;
                case 2:
                    selectMenuTenant(scanner,user);
                    break;
                default:
                    System.out.println("Không có lựa chọn phù hợp.");

            }
        }
    }
    private void displayMenuChangeInformationTenant(){
        System.out.println("1. Đổi email");
        System.out.println("2. Đổi mật khẩu");
        System.out.println("3. Quay lại.");
    }
    public void selectMenuChangeInformationTenant(Scanner scanner, User user){
        while(true){
            displayMenuChangeInformationTenant();
            int choice = Utils.inputInteger(scanner);
            switch (choice){
                case 1:
                    userService.changeEmailWhenLogIn(scanner,user);
                    break;
                case 2:
                    userService.changePassWordWhenLogIn(scanner,user);
                    break;
                case 3:
                    selectMenuLandLord(scanner,user);
                    break;
                default:
                    System.out.println("Không có lựa chọn phù hợp.");
                    break;

            }
        }
    }

    public void selectMenuTenant(Scanner scanner, User user){
        while (true){
            displayMenuTenant();
            int choice = Utils.inputInteger(scanner);
            switch (choice){
                case 1:
                    selectMenuSearchRoom(scanner,user);
                    break;
                case 2:
                   selectMenuRequestToRent(scanner,user);
                    break;
                case 3:
                    selectTenantContractMenu(scanner, user);
                    break;
                case 4:
                    tenantService.displayFavouriteList();
                    break;
                case 5:
                    selectMenuChangeInformationTenant(scanner,user);
                    break;
                case 6:
                    selectTenantComplaintMenu(scanner, user);
                    break;
                case 7:
                    selectRegisterMenu(scanner);
                    break;
                default:
                    System.out.println("Không có chức năng phù hợp.");
                    break;

            }
        }
    }
    /*private void displayMenuSuccessful(){
        System.out.println("Mời bạn chọn các dịch vụ sau");
        System.out.println("1. Thay đổi username");
        System.out.println("2. Thay đổi email");
        System.out.println("3. Thay đổi mật khẩu");
        System.out.println("4. Đăng xuất");
        System.out.println("0. Thoát chương trình");

    }
    public void selectMenuSuccessful(Scanner scanner, User user){
        while(true){
            displayMenuSuccessful();
            choice = Integer.parseInt(scanner.nextLine());
            switch (choice){
                case 1:
                    userService.changeUserNameWhenLogIn(scanner);
                    break;
                case 2:
                    userService.changeEmailWhenLogIn(scanner,user);
                    break;
                case 3:
                    userService.changePassWordWhenLogIn(scanner, user);
                    break;
                case 4:
                    selectRegisterMenu(scanner);
                    break;
                case 0:
                    System.exit(0);
                case 5:
                    System.out.println(Data.users);
                    break;

            }
        }



    }*/




}
