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
        this.resolvedDate = complainDate.plusDays(2);
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

}
