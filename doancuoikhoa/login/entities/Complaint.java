package doancuoikhoa.login.entities;

import doancuoikhoa.login.enums.ComplaintStatus;
import doancuoikhoa.login.enums.Role;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Complaint {
    private String id;
    private static int autoId;
    private int complainantId;
    private String roomId;
    private Role userType;
    private String content;
    private LocalDateTime complainDate;
    private ComplaintStatus complaintStatus;
    private LocalDateTime resolvedDate;



    public Complaint(int complainantId, String roomId, String content) {
        this.id = "CP" +  ++autoId;
        this.complainantId = complainantId;
        this.roomId = roomId;
        this.content = content;
        this.userType = Role.TENANT;
        this.complainDate = LocalDateTime.now();
        this.complaintStatus = ComplaintStatus.PENDING;
        this.resolvedDate = complainDate.plusDays(7);
    }
    public Complaint(int complainantId, String content) {
        this.id = "CP"+ ++autoId;
        this.complainantId = complainantId;
        this.content = content;
        this.userType = Role.LANDLORD;
        this.complainDate = LocalDateTime.now();
        this.complaintStatus = ComplaintStatus.PENDING;
        this.resolvedDate = complainDate.plusDays(2);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getComplainantId() {
        return complainantId;
    }

    public void setComplainantId(int complainantId) {
        this.complainantId = complainantId;
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
