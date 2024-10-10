package doancuoikhoa.login.entities;

import java.time.LocalDate;

public class SearchHistory {
    private int id;
    private static int autoId;
    private int tenantId;
    private Room room;
    private LocalDate localDate;

    public SearchHistory(int tenantId, Room room, LocalDate localDate) {
        this.id = ++autoId;
        this.tenantId = tenantId;
        this.room = room;
        this.localDate = localDate;
    }
}
