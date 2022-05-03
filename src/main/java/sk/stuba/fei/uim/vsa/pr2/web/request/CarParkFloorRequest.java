package sk.stuba.fei.uim.vsa.pr2.web.request;

public class CarParkFloorRequest {

    private String floorIdentifier;

    public CarParkFloorRequest() {
    }

    public CarParkFloorRequest(String floorIdentifier, Long carPark) {
        this.floorIdentifier = floorIdentifier;
    }

    public String getFloorIdentifier() {
        return floorIdentifier;
    }

    public void setFloorIdentifier(String floorIdentifier) {
        this.floorIdentifier = floorIdentifier;
    }

}
