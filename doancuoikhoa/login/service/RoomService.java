package doancuoikhoa.login.service;

import doancuoikhoa.login.data.Data;
import doancuoikhoa.login.entities.Room;
import doancuoikhoa.login.entities.User;
import doancuoikhoa.login.enums.RoomStatus;
import doancuoikhoa.login.utils.Utils;

import java.math.BigDecimal;
import java.util.List;
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
    public void update(Scanner scanner, User user) {
        Room existedRoom = findRoomById(scanner, user);
        if (existedRoom != null) {
            System.out.println("Mời bạn nhập lại mô tả cho căn phòng");
            String description = scanner.nextLine();
            existedRoom.setDescription(description);
            System.out.println("Mời bạn nhập lại vị trí của căn phòng");
            String location = scanner.nextLine();
            existedRoom.setLocation(location);
            System.out.println("Mời bạn nhập lại mức giá của căn phòng");
            BigDecimal price = Utils.inputBigDecimal(scanner);
            existedRoom.setPrice(price);
            System.out.println("Cập nhật thông tin phòng thành công");
        }
    }

    @Override
    public void delete(Scanner scanner, User user) {
        Room existedRoom = findRoomById(scanner, user);
        if (existedRoom != null) {
            Data.rooms.remove(existedRoom);
            System.out.println("Xóa phòng thành công");
        } else {
            System.out.println("Không tìm thấy phòng trọ với Id mà bạn nhập");
        }

    }

    @Override
    public void display(List<Room> rooms) {

        System.out.println("=============================== Danh sách phòng trọ ===============================");
        System.out.printf("%-10s %-20s %-30s %-15s %-15s %-10s %-10s\n",
                "ID", "Mô tả", "Vị trí", "Loại phòng", "Giá", "Trạng thái", "Bị khóa");
        System.out.println("===================================================================================");

        for (Room room : rooms) {
            String roomStatus = (room.getRoomStatus() != null) ? room.getRoomStatus().toString() : "N/A";
            String isBlocked = room.isBlocked() ? "Có" : "Không";

            // Giới hạn mô tả tối đa 20 ký tự
            String description = room.getDescription();
            if (description.length() > 20) {
                description = description.substring(0, 17) + "...";
            }

            System.out.printf("%-10s %-20s %-30s %-15s %-15s %-10s %-10s\n",
                    room.getId(),
                    room.getDescription(),
                    room.getLocation(),
                    room.getPropertyType(),
                    room.getPrice().toString(),
                    roomStatus,
                    isBlocked
            );
        }
        System.out.println("===================================================================================");
    }


    public Room findRoomById(Scanner scanner,User user) {
        for (Room room : Data.rooms) {
            if (user.getId() == room.getLandLordId()) {
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
    // Hàm khóa phòng
    public void blockRoom(String roomId){
        for (Room room: Data.rooms) {
            if(room.getId().equalsIgnoreCase(roomId)){
                if(room.isBlocked()){
                    System.out.println("Phòng với Id" + roomId +"đã bị khóa trước đó");
                }else{
                    room.setBlocked(true);
                    System.out.println("Phòng với ID"+ roomId + "đã bị khóa");
                    break;
                }

            }
        }
    }


}
