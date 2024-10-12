package doancuoikhoa.login.entities;

import java.time.LocalDate;

public class Review {
    private int id;
    private static int autoId;
    private int tenantId;
    private int rating;
    private String comment;
    private LocalDate reviewDate;
    private String roomId;

    public Review(int tenantId, int rating, String comment, String roomId) {
        this.id = ++autoId;
        this.tenantId = tenantId;
        this.rating = rating;
        this.comment = comment;
        this.reviewDate = LocalDate.now();
        this.roomId = roomId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTenantId() {
        return tenantId;
    }

    public void setTenantId(int tenantId) {
        this.tenantId = tenantId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public LocalDate getReviewDate() {
        return reviewDate;
    }

    public void setReviewDate(LocalDate reviewDate) {
        this.reviewDate = reviewDate;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }
}
