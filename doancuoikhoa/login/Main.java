package doancuoikhoa.login;

import doancuoikhoa.login.data.Data;
import doancuoikhoa.login.view.Menu;


import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Menu menu = new Menu();
        Data data = new Data();
        data.initializeData();
        menu.selectRegisterMenu(scanner);

        /*String choice;
        do{*/
            /*UserService userService = new UserService();
            User user = userService.registerAccount(scanner, users);
            users.add(user);
            System.out.println("Tạo tài khoản thành công");*/
           /* System.out.println("Bạn có muốn tiếp tục không (Y/N)");
            choice = scanner.nextLine();
        }while(choice.equalsIgnoreCase("Y"));*/


       /* System.out.println(users);*/

        /*Menu menu = new Menu();
        menu.registerMenu();
*/



    }

}
