package doancuoikhoa.login;

import doancuoikhoa.login.data.Data;
import doancuoikhoa.login.view.Menu;


import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Menu menu =Menu.getInstance();
        Data data = new Data();
        data.initializeData();
        menu.selectRegisterMenu(scanner);



    }

}
