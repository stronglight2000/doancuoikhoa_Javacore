package doancuoikhoa.login.view;

import doancuoikhoa.login.data.Data;
import doancuoikhoa.login.entities.LandLord;
import doancuoikhoa.login.entities.RentalRequest;
import doancuoikhoa.login.entities.Room;
import doancuoikhoa.login.entities.User;
import doancuoikhoa.login.service.*;
import doancuoikhoa.login.utils.Utils;


import java.util.ArrayList;
import java.util.Scanner;

public class Menu {
    UserService userService = new UserService();
    int choice;
    RoomService roomService = new RoomService();
    TenantService tenantService = new TenantService();
    LandLordService landLordService = new LandLordService();
    ContractService contractService = new ContractService();
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
        System.out.println("1. Thêm chủ trọ");
        System.out.println("2. Sửa thông tin chủ trọ");
        System.out.println("3. Ẩn chủ trọ");
        System.out.println("4. Thêm người cho thuê");
        System.out.println("5. Sửa thông tin người cho thuê");
        System.out.println("6. Ẩn người cho thuê");
        System.out.println("7. Đăng xuất");
    }
    public void selectMenuAdminWhenLogInSuccess(Scanner scanner){
        while (true){
            displayMenuAdmin();
            int choice = Utils.inputInteger(scanner);
            switch (choice){
                case 1:
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    break;
                case 6:
                    break;
                case 7:
                    selectRegisterMenu(scanner);
                    break;
                case 8:
                    System.exit(0);
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
        System.out.println("8. Đăng xuất");
    }
    private void displayLandLordContractMenu(){
        System.out.println("1. Hợp đồng đang chờ người thuê đồng ý.");
        System.out.println("2. Hợp đồng đã được kí.");
        System.out.println("3. Hợp đồng đã hoàn thành.");
        System.out.println("4. Hợp đồng yêu cầu hủy");
        System.out.println("5. Quay lại");
    }
    public void selectLandLordContractMenu(Scanner scanner, int landLordId) {
        while (true) {
            displayLandLordContractMenu();
            int choice = Utils.inputInteger(scanner);
            switch (choice) {
                case 1:
                    landLordService.findPendingContractsByLandLordId(landLordId);
                    break;
                case 2:
                    landLordService.findSignedContractsByLandLordId(landLordId);
                    break;
                case 3:
                    break;
                case 4:
                    landLordService.findPendingCancelContractsByLandLordId(landLordId,scanner);
                    landLordService.processCancelContract(scanner,landLordId);
                    break;
                case 5:
                    selectMenuLandLord(scanner, landLordId);
                default:
                    System.out.println("Không có lựa chọn phù hợp, mời bạn nhập lại.");


            }
        }
    }
    public void selectMenuLandLord(Scanner scanner, int landLordId){
        while (true){
            displayMenuLandLord();
            int choice = Utils.inputInteger(scanner);
            switch (choice){
                case 1:
                    Room room = roomService.createRoom(scanner,landLordId);
                    roomService.insert(room);
                    break;
                case 2:
                    roomService.update(scanner);
                    break;
                case 3:

                    break;
                case 4:
                    landLordService.displayListRoom(landLordId);
                    break;
                case 5:
                    landLordService.processRentalRequest(landLordId,scanner);
                    break;
                case 6:
                    selectLandLordContractMenu(scanner,landLordId);
                    break;
                case 7:
                    System.out.println(Data.rentalRequests);
                    break;
                case 8:
                    selectRegisterMenu(scanner);
                default:
                    System.out.println("Không có chức năng phù hợp");

            }
        }
    }
    private void displayMenuTenant(){
        System.out.println("1. Tìm kiếm phòng trọ");
        System.out.println("2. Yêu cầu thuê phòng");
        System.out.println("3. Hợp đồng");
        System.out.println("4. Danh sách phòng trọ yêu thích");
        System.out.println("5. Sửa thông tin người dùng");
        System.out.println("6. Đăng xuất");
    }

    private void displayMenuSearchRoom(){
        System.out.println("1. Tìm kiếm theo khoảng giá");
        System.out.println("2. Tìm kiếm theo địa chỉ");
        System.out.println("3. Tìm kiếm theo loại nhà đất ");
        System.out.println("4. Quay lại");
    }
    public void selectMenuSearchRoom(Scanner scanner, int tenantId){
        while(true){
            displayMenuSearchRoom();
            int choice = Utils.inputInteger(scanner);
            switch (choice){
                case 1:
                    tenantService.searchRoomsByPrice(scanner,tenantId);
                    break;
                case 2:
                    tenantService.searchRoomsByLocation(scanner,tenantId);
                    break;
                case 3:
                    tenantService.searchRoomsByType(scanner,tenantId);
                    break;
                case 4:
                    selectMenuTenant(scanner,tenantId);
            }
        }
    }
    private void displayMenuRequestToRent(){
        System.out.println("1. Yêu cầu thuê phòng");
        System.out.println("2. Quay lại");
    }
    public void selectMenuRequestToRent(Scanner scanner,int tenantId){
        while(true){
            displayMenuRequestToRent();
            int choice = Utils.inputInteger(scanner);
            switch (choice){
                case 1:
                    tenantService.requestToRentRoom(scanner,tenantId);
                    break;
                case 2:
                    selectMenuTenant(scanner,tenantId);
                    break;
                default:
                    System.out.println("Không có lựa chọn phù hợp.");

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
    public void selectTenantContractMenu(Scanner scanner,int tenantId){
        while(true){
           displayTenantContractMenu();
            int choice = Utils.inputInteger(scanner);
            switch (choice){
                case 1:
                    tenantService.displayContractAfterLandLordApproved(tenantId,scanner);
                    break;
                case 2:
                    tenantService.findSignedContractsByTenantId(tenantId);
                    break;
                case 3:
                    tenantService.cancelContract(scanner,tenantId);
                    break;
                case 4:
                    break;
                case 5:
                    break;
                case 6:
                    selectMenuTenant(scanner,tenantId);
                    break;
                default:
                    System.out.println("Không có lựa chọn phù hợp.");

            }
        }
    }
    public void selectMenuTenant(Scanner scanner, int tenantId){
        while (true){
            displayMenuTenant();
            int choice = Utils.inputInteger(scanner);
            switch (choice){
                case 1:
                    selectMenuSearchRoom(scanner,tenantId);
                    break;
                case 2:
                   selectMenuRequestToRent(scanner,tenantId);
                    break;
                case 3:
                    selectTenantContractMenu(scanner,tenantId);
                    break;
                case 4:
                    tenantService.displayFavouriteList();
                    break;
                case 5:
                    System.out.println(Data.rentalRequests);
                    break;
                case 6:
                    selectRegisterMenu(scanner);
                case 7:
                    System.out.println(Data.users);
                    break;
                case 8:
                    System.out.println(tenantId);
                    break;
                default:
                    System.out.println("Không có chức năng phù hợp.");

            }
        }
    }
    private void displayMenuSuccessful(){
        System.out.println("Mời bạn chọn các dịch vụ sau");
        System.out.println("1. Thay đổi username");
        System.out.println("2. Thay đổi email");
        System.out.println("3. Thay đổi mật khẩu");
        System.out.println("4. Đăng xuất");
        System.out.println("0. Thoát chương trình");

    }
    public void selectMenuSuccessful(Scanner scanner){
        while(true){
            displayMenuSuccessful();
            choice = Integer.parseInt(scanner.nextLine());
            switch (choice){
                case 1:
                    userService.changeUserNameWhenLogIn(scanner);
                    break;
                case 2:
                    userService.changeEmailWhenLogIn(scanner);
                    break;
                case 3:
                    userService.changePassWordWhenLogIn(scanner);
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



    }




}
