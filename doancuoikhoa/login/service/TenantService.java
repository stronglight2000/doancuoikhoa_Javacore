package doancuoikhoa.login.service;

import doancuoikhoa.login.data.Data;
import doancuoikhoa.login.entities.*;
import doancuoikhoa.login.enums.ContractStatus;
import doancuoikhoa.login.enums.RoomStatus;
import doancuoikhoa.login.utils.Utils;
import doancuoikhoa.login.view.Menu;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TenantService {
    RoomService roomService = new RoomService();
    ContractService contractService = new ContractService();

    public void searchRoomsByPrice(Scanner scanner, int tenantId) {
        List<Room> roomss = new ArrayList<>();
        System.out.println("Mời bạn nhập khoảng giá thấp nhất");
        BigDecimal minPrice = Utils.inputBigDecimal(scanner);
        boolean existedRooms = false;
        System.out.println("Mời bạn nhập khoảng giá cao nhất");
        BigDecimal maxPrice = Utils.inputBigDecimal(scanner);
        for (Room room : Data.rooms) {
            if (room.getPrice().compareTo(minPrice) >= 0 && room.getPrice().compareTo(maxPrice) <= 0) {
                roomss.add(room);
                existedRooms = true;

            }

        }
        // Nếu tồn tại phòng thì hiển thị, không thì thông báo lỗi
        if(existedRooms){
            roomService.display(roomss);
            askTenantifWantToAddRRoomToFavourList(scanner,tenantId);
        }
        else {
            System.out.println("Không tìm thấy phòng phù hợp");

        }
    }

    public void searchRoomsByLocation(Scanner scanner, int tenantId){
        List<Room> roomss = new ArrayList<>();
        System.out.println("Mời bạn nhập vào địa chỉ phòng cần tìm");
        String location = scanner.nextLine();
        // Loại bỏ dấu của chuỗi nhập vào
        String searchResult = Utils.removeAccents(location).toLowerCase();
        boolean existedRooms = false;
        for (Room room : Data.rooms) {
            String normalAddress = Utils.removeAccents(room.getLocation()).toLowerCase();
            if (normalAddress.contains(searchResult)){
                roomss.add(room);
                existedRooms = true;

            }

        }
        if(existedRooms){
            roomService.display(roomss);
            askTenantifWantToAddRRoomToFavourList(scanner,tenantId);
        }else{
            System.out.println("Không tìm thấy phòng phù hợp.");
        }

    }
    public void searchRoomsByType(Scanner scanner, int tenantId){
        Menu menu = new Menu();
        List<Room> roomss = new ArrayList<>();
        boolean existedRooms = false;
        System.out.println("Mời bạn nhập vào loại phòng cần tìm");
        String type = scanner.nextLine();
        String typeAfterRemove = Utils.removeAccents(type).toLowerCase();
        for (Room room: Data.rooms) {
            String typeRoomAfter = Utils.removeAccents(room.getPropertyType()).toLowerCase();
            if(typeRoomAfter.contains(typeAfterRemove)){
                roomss.add(room);
                existedRooms = true;
            }
        }
        if(existedRooms){
            roomService.display(roomss);
            askTenantifWantToAddRRoomToFavourList(scanner,tenantId);

        }else{
            System.out.println("Không tìm thấy loại phòng phù hợp.");
        }
    }
    public void askTenantifWantToAddRRoomToFavourList(Scanner scanner, int tenantId) {
        Menu menu = new Menu();
        String choice;
        System.out.println("Bạn có muốn thêm phòng trọ nào vào danh sách yêu thích không(Y/N)?");
        choice = scanner.nextLine();
        if (choice.equalsIgnoreCase("Y")) {
            do {

                addRoomToFavouriteList(scanner, tenantId);
                System.out.println("Bạn có muốn tiếp tục thêm không(Y/N)");
                choice = scanner.nextLine();
            } while (choice.equalsIgnoreCase("Y"));
        }else {
            menu.selectMenuSearchRoom(scanner,tenantId);
        }
    }
    public void addRoomToFavouriteList(Scanner scanner, int tenantId){
        System.out.println("Mời bạn nhập vào id căn phòng yêu thích");
        boolean existedRooms = false;
        String id = scanner.nextLine();
        for (Room room: Data.rooms) {
            if(room.getId().equalsIgnoreCase(id)){
                FavouriteRoom favouriteRoom = new FavouriteRoom(tenantId,room.getId());
                Data.favouriteRooms.add(favouriteRoom);
                existedRooms = true;
            }
        }
        if(existedRooms){
            System.out.println("Đã thêm phòng vào danh sách yêu thích");
        }
        else{
            System.out.println("Không tìm thấy phòng theo ID bạn yêu cầu.");
        }

    }
    public void displayFavouriteList(){
        System.out.println("=====================Danh sách phòng trọ yêu thích===============================================================");
        System.out.printf("%-3s \t %-70s \t %-50s \t %-20s %-10s %-15s \n", "ID", "Mô tả", "Vị trí", "Loại phòng", "Mức giá","Trạng thái");
        System.out.println("====================================================================================================================================================================================");

        for (FavouriteRoom favouriteRoom: Data.favouriteRooms) {
            Room room = roomService.findRoomById(favouriteRoom.getRoomId());
            System.out.printf("%-3s \t %-70s \t %-50s \t %-20s %-10s %-15s \n", room.getId(), room.getDescription(), room.getLocation(), room.getPropertyType(), room.getPrice(),room.getRoomStatus());
        }
    }
    public Contract findPendingContractByTenantId(int tenantId, Scanner scanner){
        Menu menu = new Menu();
        for (Contract contract: Data.contracts) {
            if(contract.getTenantId() == tenantId && contract.getContractStatus() == ContractStatus.PENDING){
                return contract;
            }
        }
        System.out.println("Không tìm thấy hợp đồng với Id bạn chọn.");
        menu.selectMenuTenant(scanner, tenantId);
        return null;
    }
    public void findSignedContractsByTenantId(int tenantId){
        for (Contract contract: Data.contracts) {
            if(contract.getTenantId() == tenantId && contract.getContractStatus() == ContractStatus.SIGNED){
                contractService.formatContract(contract.getId());
            }
        }
    }
    public void requestToRentRoom(Scanner scanner,int tenantId){
        Menu menu = new Menu();
        System.out.println("Mời bạn nhập các thông tin sau");
        Room existedRoom;
        String roomId;
        LocalDate startDate,endDate;

       do{
           System.out.println("Mời bạn nhập id căn phòng muốn thuê");
           roomId = scanner.nextLine();
           existedRoom = roomService.findRoomInAvaiableStatusById(roomId);
           if(existedRoom == null){
               System.out.println("Căn phòng bạn tìm kiếm đã được cho thuê/đặt trước hoặc id bạn nhập vào không hợp lệ.");
           }
       }while(existedRoom == null);
        do {
            startDate = Utils.checkDateValidate(scanner);
            endDate = Utils.checkDateValidate2(scanner);

            // Kiểm tra ngày bắt đầu phải trước ngày kết thúc
            if (endDate.isBefore(startDate)) {
                System.out.println("Ngày kết thúc phải sau ngày bắt đầu. Vui lòng nhập lại.");
            }
        } while (endDate.isBefore(startDate));  // Lặp lại cho đến khi ngày kết thúc hợp lệ

        RentalRequest rentalRequest = new RentalRequest(tenantId, roomId,startDate,endDate);
        Data.rentalRequests.add(rentalRequest);
        System.out.println("Yêu cầu thuê phòng đã được gửi đến cho chủ phòng.");
    }
    public void displayContractAfterLandLordApproved(int tenantId,Scanner scanner){
        Menu menu = new Menu();
        Contract contract = findPendingContractByTenantId(tenantId,scanner);
        // Format và hiển thị hợp đồng
        contractService.formatContract(contract.getId());
        Contract existedContract;
        // Vòng lặp tìm hợp đồng với ID cụ thể từ người dùng nhập
        do{
            System.out.println("Nhập id hợp đồng mà bạn muốn ký kết");
            String contractId = scanner.nextLine();
            existedContract = contractService.findPendingContractById(contractId);
        }while (existedContract == null);
        // Xử lý quyết định ký hợp đồng
        boolean validInput = false;
        do{
            System.out.println("Bạn có muốn đồng ý ký kết hợp đồng này không?(Y/N)");
            String choice = scanner.nextLine();
            if(choice.equalsIgnoreCase("Y")){
                existedContract.setContractStatus(ContractStatus.SIGNED);
                // Sau khi hợp đồng được kí kết phòng sẽ chuyển trạng thái đặt trước sang đã được thuê
                Room room = roomService.findRoomById(existedContract.getRoomId());
                room.setRoomStatus(RoomStatus.RENTED);
                System.out.println("Kí hợp đồng thành công.");
                Data.contracts.add(existedContract);
                validInput = true;
            }else if(choice.equalsIgnoreCase("N")) {
                existedContract.setContractStatus(ContractStatus.REJECTED);
                validInput = true;
            }else {
                System.out.println("Lựa chọn không hợp lệ, vui lòng nhập lại.");
            }
        }while (!validInput);
        // Sau khi xử lý xong, quay lại menu cho người thuê
        menu.selectMenuTenant(scanner, tenantId);

    }
    public void cancelContract(Scanner scanner, int tenantId){
        Menu menu = new Menu();
        Contract cancelContract;
        do{
            System.out.println("Mời bạn nhập vào ID hợp đồng muốn hủy");
            String contractId = scanner.nextLine();
            cancelContract = contractService.findSignedContractById(contractId);
        }while(cancelContract == null);
        System.out.println("Bạn có muốn thật sự hủy hợp đồng này không?(Y/N))");
        String choice = scanner.nextLine();
        if(choice.equalsIgnoreCase("Y")){
            cancelContract.setContractStatus(ContractStatus.PENDINGCANCEL);
            System.out.println("Yêu cầu hủy sẽ được chuyển tới cho chủ trọ.");
        }else{
            menu.selectMenuTenant(scanner,tenantId);
        }
    }
}
