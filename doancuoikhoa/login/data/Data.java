package doancuoikhoa.login.data;

import doancuoikhoa.login.entities.*;
import doancuoikhoa.login.enums.Role;
import doancuoikhoa.login.enums.UserStatus;

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
        User user = new User("admin","hoaly16200@gmail.com","123123","0976920669", Role.ADMIN, UserStatus.ACTIVE);
        User user2 = new User("hoaly1880","hoaly1880@gmail.com","123.Pquang","0976920666", Role.LANDLORD, UserStatus.ACTIVE);
        User user3 = new User("hoaly1890","hoaly1890@gmail.com","123,Pquang","0976920686", Role.LANDLORD,UserStatus.ACTIVE);
        Room room = new Room("12m2, đẹp","Đống Đa, Hà Nội","Nhà chung cư",new BigDecimal(1500000), user2.getId());
        Room room2 = new Room("24m2, đẹp","Hoàng Mai, Hà Nội","Nhà đất",new BigDecimal(2000000),user2.getId());
        Room room3 = new Room("19m2, đẹp","Hoàn Kiếm, Hà Nội","Nhà đất",new BigDecimal(2500000),user3.getId());
        Room room4 = new Room("24m2, đẹp","Hoàn Kiếm, Hà Nội","Nhà đất",new BigDecimal(2500000),user3.getId());
        User user4=   new User("hoaly1870","hoaly1890@gmail.com","123.Pquang","0976920686", Role.TENANT,UserStatus.ACTIVE);
        RentalRequest rentalRequest = new RentalRequest(4,"R2",LocalDate.of(2024,10,8),LocalDate.of(2025,10,8));

        users.add(user);
        users.add(user2);
        users.add(user3);
        users.add(user4);
        rooms.add(room);
        rooms.add(room2);
        rooms.add(room3);
        rentalRequests.add(rentalRequest);
    }
}
