package doancuoikhoa.login.service;

import doancuoikhoa.login.data.Data;
import doancuoikhoa.login.entities.LandLord;
import doancuoikhoa.login.entities.Tenant;
import doancuoikhoa.login.entities.User;
import doancuoikhoa.login.enums.Role;
import doancuoikhoa.login.enums.UserStatus;
import doancuoikhoa.login.utils.Utils;
import doancuoikhoa.login.view.Menu;


import java.util.ArrayList;
import java.util.Scanner;

public class UserService {
    boolean isValid;
    private User duplicateEmail;
    private User duplicateUserName;
    User duplicatePassWord;
    /*String loggedInUsername;*/

    public User registerAccount(Scanner scanner) {

        String username;
        String email;
        String password;
        String phoneNumber;
        Utils utils = new Utils();
        System.out.println("===============ĐĂNG KÝ==================");
        do {
            System.out.println("Mời bạn nhập vào username:");
            username = scanner.nextLine();
            duplicateUserName = findUserName(username);
            if (duplicateUserName != null) {
                System.out.println("Username đã tồn tại, mời bạn nhập lại:");
            }
        }
        while (duplicateUserName !=null);
        do {

            System.out.println("Mời bạn nhập vào email:");
            email = scanner.nextLine();
            isValid = utils.checkEmail(email);
            duplicateEmail = findUserEmail(email);
            if (!isValid || duplicateEmail != null) {
                System.out.println("Email không hợp lệ hoặc đã tồn tại, mời bạn nhập lại");
            }
        } while (!isValid || duplicateEmail != null);
        do {
            System.out.println("Mời bạn nhập vào password:(password dài từ 7 đến 15 ký tự, chứa ít nhất 1 ký tự in hoa, 1 ký tự đặc biệt)");
            password = scanner.nextLine();
            isValid = utils.checkPassword(password);
            if (!isValid) {
                System.out.println("Password không hợp lệ, mời bạn nhập lại");
            }
        } while (!isValid);
        do {
            System.out.println("Mời bạn nhập số điện thoại(có 10 số và bắt buộc phải bắt đầu bằng 0)");
            phoneNumber = scanner.nextLine();
            isValid = utils.checkphoneNumber(phoneNumber);
            if (!isValid) {
                System.out.println("Số điện thoại không hợp lệ, mời bạn nhập lại");
            }
        } while (!isValid);
        System.out.println("Bạn là:");
        System.out.println("1. Chủ trọ");
        System.out.println("2. Người đi thuê");
        int choice = Utils.inputInteger(scanner);
        Role role;
        if(choice == 1){
            role = Role.LANDLORD;
        }
        else{
            role = Role.TENANT;
        }
        System.out.println("Tạo tài khoản thành công");
        User user = new User(username, email, password,phoneNumber,role);
        Data.users.add(user);
        return user;


    }




    public User findUserEmail(String email) {
        for (User user : Data.users) {
            if (email.equals(user.getEmail())) {
                return user;
            }
        }
        return null;

    }

    public User findUserName(String username) {
        for (User user : Data.users) {
            if (username.equals(user.getUsername())) {
                return user;
            }
        }
        return null;

    }

    public User findPassWord(String username, String password) {
        for (User user : Data.users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }
        return null;

    }
    public Role findRoleByUsername(String username){
        for (User user: Data.users) {
            if(user.getUsername().equals(username)){
                return user.getRole();
            }
        }
        return null;
    }

    public void changePassWordWhenForget(Scanner scanner) {
        System.out.println("Mời bạn nhập vào email");
        String email = scanner.nextLine();
        duplicateEmail = findUserEmail(email);

        if (duplicateEmail != null) {
                System.out.println("Mời bạn nhập vào mật khẩu mới");
                String password = scanner.nextLine();
                duplicateEmail.setPassword(password);
                System.out.println("Mật khẩu đã được thay đổi.");
                Menu menu = new Menu();
                menu.selectMenuPassWordWrong(scanner);
            }


         else {
            System.out.println("Chưa tồn tại tài khoản");
            Menu menu = new Menu();
            menu.selectRegisterMenu(scanner);
        }
    }


    public User logIn(Scanner scanner) {
        String username;
        String password;
//      boolean isValid;
        System.out.println("===============ĐĂNG NHẬP================");


            do {
                System.out.println("Mời bạn nhập vào user name:");
                username = scanner.nextLine();
                System.out.println("Mời bạn nhập vào password:");
                password = scanner.nextLine();
                duplicateUserName=findUserName(username);
                duplicatePassWord = findPassWord(username, password);
                User duplicateUser = findUserName(username);
                Menu menu = new Menu();
                if (duplicatePassWord!=null && duplicateUserName !=null && duplicatePassWord.getStatus() == UserStatus.ACTIVE ) {
                    if(findRoleByUsername(username) == Role.ADMIN){
                        System.out.println("Chào mừng" + " " + username);
                        menu.selectMenuAdminWhenLogInSuccess(scanner);
                    }else if(findRoleByUsername(username) == Role.LANDLORD){
                        System.out.println("Chào mừng" + " " + username);
                        menu.selectMenuLandLord(scanner,duplicateUser);
                    }else{
                        System.out.println("Chào mừng" + " " + username);
                        menu.selectMenuTenant(scanner,duplicateUser);
                    }
                    return duplicateUser;
                    /*Utils.loggedInUsername = username;*/

                } else if(duplicatePassWord==null && duplicateUserName !=null) {
                    menu.selectMenuPassWordWrong(scanner);
                }else{
                    System.out.println("Tài khoản đã sai,mời bạn nhập lại ");
                }
            }while (duplicateUserName == null);


        User user = new User(username, password);
        return user;
    }

    public void changeUserNameWhenLogIn(Scanner scanner) {
        System.out.println("Mời bạn nhập username để xác minh");
        String username = scanner.nextLine();
        User existedUser = findUserName(username);
        if (existedUser != null) {
            System.out.println("Nhập username mới mà bạn muốn đổi");
            String newUserName = scanner.nextLine();
            existedUser.setUsername(newUserName);
            System.out.println("Đổi username thành công");
        }
    }

    public void changePassWordWhenLogIn(Scanner scanner, User user) {
        boolean isValidPassword;
        String newPassWord;
        Utils utils = new Utils();
        System.out.println("Mời bạn nhập lại password để xác minh");
        String password = scanner.nextLine();
        User existedUser = findPassWord(user.getUsername(), password);
        // Kiểm tra nếu password nhập sai
        if (existedUser == null) {
            System.out.println("Mật khẩu hiện tại không chính xác.");
            return;
        }
            do{
                System.out.println("Mời bạn nhập vào password:(password dài từ 7 đến 15 ký tự, chứa ít nhất 1 ký tự in hoa, 1 ký tự đặc biệt)");
                newPassWord = scanner.nextLine();
                isValidPassword = utils.checkPassword(newPassWord);
                if(!isValidPassword){
                    System.out.println("Password không hợp lệ, mời bạn nhập lại.");
                }else{
                    System.out.println("Xác nhận lại password mới:");
                    String confirmPassword = scanner.nextLine();
                    if (!newPassWord.equals(confirmPassword)) {
                        System.out.println("Password xác nhận không khớp. Vui lòng thử lại.");
                        isValidPassword = false;
                    }
                }
            }while (!isValidPassword);
            user.setPassword(newPassWord);
            System.out.println("Đổi password thành công");
        }

    public void changeEmailWhenLogIn(Scanner scanner, User user) {
        Utils utils = new Utils();
        String newEmail;
        boolean isValidEmail;
        User existedEmail;
            do {
                System.out.println("Nhập email mới mà bạn muốn đổi");
                newEmail = scanner.nextLine();
                isValidEmail = utils.checkEmail(newEmail);
                existedEmail = findUserEmail(newEmail);
                if (!isValidEmail) {
                    System.out.println("Email không hợp lệ. Vui lòng nhập đúng định dạng.");
                } else if (existedEmail != null) {
                    System.out.println("Email này đã tồn tại. Vui lòng nhập email khác.");
                }

            }while(existedEmail !=null || !isValidEmail );
            user.setEmail(newEmail);
            System.out.println("Đổi email thành công");
    }

    public void displayUser(){

    }





}
