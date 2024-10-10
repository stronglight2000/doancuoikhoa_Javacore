package doancuoikhoa.login.service;

import doancuoikhoa.login.data.Data;
import doancuoikhoa.login.entities.RentalRequest;

public class RentalRequestService {
  public String checkRoomById(String roomId){
        for (RentalRequest rentalRequest: Data.rentalRequests) {
            if(rentalRequest.getRoomId().equalsIgnoreCase(roomId) && !rentalRequest.isApproved()){
                return rentalRequest.getRoomId();
            }
        }
        System.out.println("Không tìm thấy Id phòng mà bạn yêu cầu");
        return null;
    }
    public String checkRoomStatusById(String roomId){
        for (RentalRequest rentalRequest: Data.rentalRequests) {
            if(rentalRequest.getRoomId().equalsIgnoreCase(roomId) && rentalRequest.isApproved()){
                return rentalRequest.getId();
            }
        }
        System.out.println("Không tìm thấy Id căn phòng mà bạn nhập");
        return null;
    }

}
