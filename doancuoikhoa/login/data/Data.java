package doancuoikhoa.login.data;

import doancuoikhoa.login.entities.*;
import doancuoikhoa.login.enums.Role;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Data {
    public static List<User> users = new ArrayList<>();
    public static List<Room> rooms = new ArrayList<>();
    public static List<FavouriteRoom> favouriteRooms = new ArrayList<>();
    public static List<Contract> contracts = new ArrayList<>();
    public static List<Review> reviews = new ArrayList<>();
    public static List<RoomReservation> roomReservations = new ArrayList<>();
    public static List<Complaint> complaints = new ArrayList<>();
    public static List<RentalRequest> rentalRequests = new ArrayList<>();
    public static void initializeData() {
        User user = new User("hoaly1880","hoaly1880@gmail.com","123.Mquang","0976920666", Role.LANDLORD);
        User user2 = new User("hoaly1890","hoaly1890@gmail.com","123,Mquang","0976920686", Role.LANDLORD);
        Room room = new Room("12m2, đẹp","Đống Đa, Hà Nội","Nhà chung cư",new BigDecimal(1500000),user.getId());
        Room room2 = new Room("24m2, đẹp","Hoàng Mai, Hà Nội","Nhà đất",new BigDecimal(2000000),user.getId());
        Room room3 = new Room("19m2, đẹp","Hoàn Kiếm, Hà Nội","Nhà đất",new BigDecimal(2500000),user2.getId());
        User user3=   new User("hoaly1870","hoaly1890@gmail.com","123.Mquang","0976920686", Role.TENANT);
        RentalRequest rentalRequest = new RentalRequest(3,"R2",LocalDate.of(2024,10,8),LocalDate.of(2025,10,8));

        users.add(user);
        users.add(user2);
        users.add(user3);
        rooms.add(room);
        rooms.add(room2);
        rooms.add(room3);
        rentalRequests.add(rentalRequest);
    }
}
