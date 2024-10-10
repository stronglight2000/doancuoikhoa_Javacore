package doancuoikhoa.login.entities;

import doancuoikhoa.login.enums.RoomStatus;

import java.math.BigDecimal;

public class Room {
    private String id;
    private static int autoId;
    private String description;
    private String location;
    private String propertyType;
    private BigDecimal price;
    private RoomStatus roomStatus;
    private boolean isBlocked;
    private int landLordId;

    public Room(String description, String location,String propertyType, BigDecimal price, int landLordId) {
        this.id = "R"+ ++autoId;
        this.description = description;
        this.location = location;
        this.propertyType = propertyType;
        this.price = price;
        this.roomStatus = RoomStatus.AVAIABLE;
        this.isBlocked = false;
        this.landLordId = landLordId;
    }

    public Room(String description, String location, BigDecimal price) {
        this.id = "R" + ++autoId;
        this.description = description;
        this.location = location;
        this.price = price;
        this.roomStatus = RoomStatus.AVAIABLE;
        this.isBlocked = false;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(String propertyType) {
        this.propertyType = propertyType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public RoomStatus getRoomStatus() {
        return roomStatus;
    }

    public void setRoomStatus(RoomStatus roomStatus) {
        this.roomStatus = roomStatus;
    }

    public int getLandLordId() {
        return landLordId;
    }

    public void setLandLordId(int landLordId) {
        this.landLordId = landLordId;
    }
}
