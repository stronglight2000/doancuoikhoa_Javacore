package doancuoikhoa.login.entities;

import java.time.LocalDate;

public class RentalRequest {
    private String id;
    private int autoId;
    private int tenantId;
    private String roomId;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean approved;


    public RentalRequest(int tenantId, String roomId, LocalDate startDate, LocalDate endDate) {
        this.id = "RQ" + ++autoId;
        this.tenantId = tenantId;
        this.roomId = roomId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.approved = false;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getTenantId() {
        return tenantId;
    }

    public void setTenantId(int tenantId) {
        this.tenantId = tenantId;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "RentalRequest{" +
                "id='" + id + '\'' +
                ", tenantId=" + tenantId +
                ", roomId='" + roomId + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", approved=" + approved +
                '}';
    }
}
