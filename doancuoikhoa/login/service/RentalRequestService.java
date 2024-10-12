package doancuoikhoa.login.service;

import doancuoikhoa.login.data.Data;
import doancuoikhoa.login.entities.RentalRequest;
import doancuoikhoa.login.enums.RentalRequestStatus;

public class RentalRequestService {
  public String checkRoomById(String roomId){
        for (RentalRequest rentalRequest: Data.rentalRequests) {
            if(rentalRequest.getRoomId().equalsIgnoreCase(roomId) && rentalRequest.getStatus() == RentalRequestStatus.PENDING){
                return rentalRequest.getRoomId();
            }
        }
        System.out.println("Không tìm thấy Id phòng mà bạn yêu cầu");
        return null;
    }
    public String checkRoomStatusById(String roomId){
        for (RentalRequest rentalRequest: Data.rentalRequests) {
            if(rentalRequest.getRoomId().equalsIgnoreCase(roomId) && rentalRequest.getStatus()== RentalRequestStatus.APPROVED){
                return rentalRequest.getId();
            }
        }
        System.out.println("Không tìm thấy Id căn phòng mà bạn nhập");
        return null;
    }
    public RentalRequest checkTenantRequest(int tenantId, String roomId){
        for (RentalRequest rentalRequest: Data.rentalRequests) {
            if(rentalRequest.getTenantId() == tenantId && rentalRequest.getRoomId().equalsIgnoreCase(roomId)){
                return rentalRequest;

            }
        }
        return null;
    }

}
