package doancuoikhoa.login.entities;

import java.time.LocalDate;

public class Review {
    private int id;
    private static int autoId;
    private int tenantId;
    private int rating;
    private String comment;
    private LocalDate reviewDate;
    private int roomId;

    public Review(int tenantId, int rating, String comment, LocalDate reviewDate, int roomId) {
        this.id = ++autoId;
        this.tenantId = tenantId;
        this.rating = rating;
        this.comment = comment;
        this.reviewDate = reviewDate;
        this.roomId = roomId;
    }
}
