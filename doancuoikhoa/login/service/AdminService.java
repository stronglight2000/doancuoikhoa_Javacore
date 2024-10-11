package doancuoikhoa.login.service;

import doancuoikhoa.login.data.Data;
import doancuoikhoa.login.entities.User;
import doancuoikhoa.login.enums.UserStatus;

import java.util.Scanner;

public class AdminService {
    public void blockUser(Scanner scanner){
        System.out.println("Mời bạn nhập id User cần khóa:");
        int userId = Integer.parseInt(scanner.nextLine());
        boolean existedUser = false;
        for (User user : Data.users) {
            if(user.getId() == userId && user.getStatus() == UserStatus.ACTIVE){
                user.setStatus(UserStatus.BLOCKED);
                existedUser = true;
            }
        }
        if(!existedUser){
            System.out.println("Không tồn tại người dùng hoặc tài khoản người dùng này đã bị khóa.");
        }
    }

}
