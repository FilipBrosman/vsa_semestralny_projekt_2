package sk.stuba.fei.uim.vsa.pr2.web.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CarParkFloorRequest {
    private Long id;
    private String identifier;
    private Long carPark;
    private List<ParkingSpotRequest> spots;

    public List<ParkingSpotRequest> getSpots() {
        return spots;
    }

    public void setSpots(List<ParkingSpotRequest> spots) {
        this.spots = spots;
    }

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

    public Long getCarPark() {
        return carPark;
    }

    public void setCarPark(Long carPark) {
        this.carPark = carPark;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
