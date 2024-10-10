package doancuoikhoa.login.service;

import doancuoikhoa.login.data.Data;
import doancuoikhoa.login.entities.LandLord;
import doancuoikhoa.login.entities.Room;
import doancuoikhoa.login.entities.User;
import doancuoikhoa.login.enums.RoomStatus;
import doancuoikhoa.login.utils.Utils;
import doancuoikhoa.login.view.Menu;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;

public class RoomService implements IAction<Room> {
    public Room createRoom(Scanner scanner, int landLordId) {
        System.out.println("Tạo phòng trọ");
        System.out.println("Mời bạn nhập mô tả cho căn phòng");
        String description = scanner.nextLine();
        System.out.println("Mời bạn nhập vị trí của căn phòng");
        String location = scanner.nextLine();
        System.out.println("Mời bạn nhập loại phòng:");
        String propertyType = scanner.nextLine();
        System.out.println("Mời bạn nhập mức giá của căn phòng");
        BigDecimal price = Utils.inputBigDecimal(scanner);
        Room room = new Room(description, location, propertyType, price,landLordId);
        return room;
    }

    @Override
    public void insert(Room room) {
        Data.rooms.add(room);
        System.out.println("Thêm phòng thành công");
    }

    @Override
    public void update(Scanner scanner) {
        Room existedRoom = findRoomById(scanner);
        if (existedRoom != null) {
            System.out.println("Mời bạn nhập lại mô tả cho căn phòng");
            String description = scanner.nextLine();
            existedRoom.setDescription(description);
            System.out.println("Mời bạn nhập lại vị trí của căn phòng");
            String location = scanner.nextLine();
            existedRoom.setLocation(location);
            System.out.println("Mời bạn nhập lại mức giá của căn phòng");
            BigDecimal price = new BigDecimal(scanner.nextLine());
            existedRoom.setPrice(price);
            System.out.println("Cập nhật thông tin phòng thành công");
        }
    }

    @Override
    public void delete(Scanner scanner) {
        Room existedRoom = findRoomById(scanner);
        if (existedRoom != null) {
            Data.rooms.remove(existedRoom);
            System.out.println("Xóa phòng thành công");
        } else {
            System.out.println("Không tìm thấy phòng trọ với Id mà bạn nhập");
        }

    }

    @Override
    public void display(List<Room> rooms) {
        System.out.println("=====================Danh sách phòng trọ===============================================================");
        System.out.printf("%-3s \t %-70s \t %-50s \t %-20s %-10s %-15s \n", "ID", "Mô tả", "Vị trí", "Loại phòng", "Mức giá","Trạng thái");
        System.out.println("====================================================================================================================================================================================");
        for (Room room : rooms) {
            System.out.printf("%-3s \t %-70s \t %-50s \t %-20s %-10s %-15s \n", room.getId(), room.getDescription(), room.getLocation(), room.getPropertyType(), room.getPrice(),room.getRoomStatus());
        }

    }
    public Room findRoomById(Scanner scanner) {
        System.out.println("Mời bạn nhập Id phòng cần tìm kiếm");
        String id = scanner.nextLine();
        for (Room room : Data.rooms) {
            if (room.getId().equals(id)) {
                return room;
            }
        }
        return null;
    }

    public Room findRoomById(String idRoom){
        for (Room room: Data.rooms) {
            if(room.getId().equalsIgnoreCase(idRoom)){
                return room;

            }
        }
        System.out.println("Không tìm thấy phòng với id bạn nhập vào");
       return null;
    }
    // Hàm tìm kiếm phòng trọ đang ở trạng thái avaiable
    public Room findRoomInAvaiableStatusById(String idRoom){
        for (Room room: Data.rooms) {
            if(room.getId().equalsIgnoreCase(idRoom) && room.getRoomStatus() == RoomStatus.AVAIABLE){
                return room;

            }
        }
        return null;
    }

    //Hàm tìm id landlord dựa vào id phòng
    public int findLandLordIdByIdRoom(String roomId){
        for (Room room: Data.rooms) {
            if(room.getId().equalsIgnoreCase(roomId)){
                return room.getLandLordId();
            }
        }
        throw new IllegalArgumentException("Không tìm thấy phòng với id: " + roomId);
    }


}
