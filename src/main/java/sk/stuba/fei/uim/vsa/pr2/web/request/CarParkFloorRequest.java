package sk.stuba.fei.uim.vsa.pr2.web.request;

public class CarParkFloorRequest {

    private String identifier;
    private String carParkFloor;

    public CarParkFloorRequest() {
    }

    public CarParkFloorRequest(String identifier, Long carPark) {
        this.identifier = identifier;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getCarParkFloor() {
        return carParkFloor;
    }

    public void setCarParkFloor(String carParkFloor) {
        this.carParkFloor = carParkFloor;
    }
}
