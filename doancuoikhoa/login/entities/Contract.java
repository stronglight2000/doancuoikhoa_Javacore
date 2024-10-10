package doancuoikhoa.login.entities;

import doancuoikhoa.login.enums.ContractStatus;

import java.time.LocalDate;

public class Contract {
    private String id;
    private static int autoId;
    private String roomId;
    private int tenantId;
    private int landLordId;
    private String clause;
    private LocalDate startDate;
    private LocalDate endDate;
    private ContractStatus contractStatus;

    public Contract(String roomId, int tenantId, int landLordId, String clause, LocalDate startDate, LocalDate endDate) {
        this.id = "C" + ++autoId;
        this.roomId = roomId;
        this.tenantId = tenantId;
        this.landLordId = landLordId;
        this.clause = clause;
        this.startDate = startDate;
        this.endDate = endDate;
        this.contractStatus = ContractStatus.PENDING;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
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

    public String getClause() {
        return clause;
    }

    public void setClause(String clause) {
        this.clause = clause;
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

    public ContractStatus getContractStatus() {
        return contractStatus;
    }

    public void setContractStatus(ContractStatus contractStatus) {
        this.contractStatus = contractStatus;
    }
}
