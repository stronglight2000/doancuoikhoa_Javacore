package doancuoikhoa.login.entities;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class RoomReservation {
    private int id;
    private static int autoId;
    private int tenantId;
    private int roomId;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public RoomReservation(int tenantId, int roomId, LocalDateTime startDate, LocalDateTime endDate) {
        this.id = ++autoId;
        this.tenantId = tenantId;
        this.roomId = roomId;
        this.startDate = startDate;
        this.endDate = startDate.plusHours(4);
    }
}
