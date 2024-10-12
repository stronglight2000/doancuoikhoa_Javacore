package doancuoikhoa.login.service;

import doancuoikhoa.login.data.Data;
import doancuoikhoa.login.entities.Contract;
import doancuoikhoa.login.entities.Review;
import doancuoikhoa.login.entities.User;

import java.util.Scanner;

public class ReviewService {

    ContractService contractService = new ContractService();
    public Review creatReview(Scanner scanner, User user){
        System.out.println("Mời bạn nhập review");
        Contract contract;
        String roomId;
        do {
            System.out.println("Mời bạn nhập Id room cần đánh giá");
            roomId = scanner.nextLine();
            contract = contractService.checkTenantSignedContract(user.getId(), roomId);
            if(contract == null){
                System.out.println("Không thể đánh giá do không tìm thấy hợp đồng giữa bạn và căn phòng này.");
            }
        }while(contract == null);
        for (Review review : Data.reviews) {
            if (review.getTenantId() == user.getId() && review.getRoomId() == roomId) {
                System.out.println("Bạn đã từng review căn phòng này");
            }
        }
        System.out.println("Mời bạn nhập nội dung đánh giá");
        String content = scanner.nextLine();
        System.out.println("Mời bạn chấm điểm cho căn phòng(Theo mức từ 1 tới 5)");
        int rating = Integer.parseInt(scanner.nextLine());
        Review review = new Review(user.getId(), rating,content,roomId);
        return review;

    }


}

