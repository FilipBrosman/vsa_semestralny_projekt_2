package sk.stuba.fei.uim.vsa.pr2.web.request;

public class ParkingSpotRequest {
    private Long id;
    private String identifier;
    private String CarParkFloor;
    private Long CarPark;
    private Boolean free;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getCarParkFloor() {
        return CarParkFloor;
    }

    public void setCarParkFloor(String carParkFloor) {
        CarParkFloor = carParkFloor;
    }

    public Long getCarPark() {
        return CarPark;
    }

    public void setCarPark(Long carPark) {
        CarPark = carPark;
    }

    public Boolean getFree() {
        return free;
    }

    public void setFree(Boolean free) {
        this.free = free;
    }
}
