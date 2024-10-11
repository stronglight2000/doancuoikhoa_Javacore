package doancuoikhoa.login.entities;

import doancuoikhoa.login.enums.ComplaintStatus;
import doancuoikhoa.login.enums.Role;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Complaint {
    private int id;
    private static int autoId;
    private int tenantId;
    private int landLordId;
    private String roomId;
    private Role userType;
    private String content;
    private LocalDateTime complainDate;
    private ComplaintStatus complaintStatus;
    private LocalDateTime resolvedDate;



    public Complaint(int tenantId, int landLordId, String roomId, String content) {
        this.id = ++autoId;
        this.tenantId = tenantId;
        this.landLordId = landLordId;
        this.roomId = roomId;
        this.content = content;
        this.userType = Role.TENANT;
        this.complainDate = LocalDateTime.now();
        this.complaintStatus = ComplaintStatus.PENDING;
        this.resolvedDate = complainDate.plusDays(7);
    }
    public Complaint(int landLordId, String content) {
        this.id = ++autoId;
        this.landLordId = landLordId;
        this.content = content;
        this.userType = Role.LANDLORD;
        this.complainDate = LocalDateTime.now();
        this.complaintStatus = ComplaintStatus.PENDING;
        this.resolvedDate = complainDate.plusDays(2);
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

    public int getLandLordId() {
        return landLordId;
    }

    public void setLandLordId(int landLordId) {
        this.landLordId = landLordId;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public Role getUserType() {
        return userType;
    }

    public void setUserType(Role userType) {
        this.userType = userType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getComplainDate() {
        return complainDate;
    }

    public void setComplainDate(LocalDateTime complainDate) {
        this.complainDate = complainDate;
    }

    public ComplaintStatus getComplaintStatus() {
        return complaintStatus;
    }

    public void setComplaintStatus(ComplaintStatus complaintStatus) {
        this.complaintStatus = complaintStatus;
    }

    public LocalDateTime getResolvedDate() {
        return resolvedDate;
    }

    public void setResolvedDate(LocalDateTime resolvedDate) {
        this.resolvedDate = resolvedDate;
    }
}
