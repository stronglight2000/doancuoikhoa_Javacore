package doancuoikhoa.login.entities;

import java.time.LocalDate;

public class FavouriteRoom {
    private String id;
    private static int autoId;
    private int tenantId;
    private String roomId;

    public FavouriteRoom(int tenantId, String roomId) {
        this.id = "R" + ++autoId;
        this.tenantId = tenantId;
        this.roomId = roomId;
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
}
