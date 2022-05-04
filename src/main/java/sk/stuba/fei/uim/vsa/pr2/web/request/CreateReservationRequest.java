package sk.stuba.fei.uim.vsa.pr2.web.request;

public class CreateReservationRequest {
    private Long parkingSpotId;
    private Long carId;

    public Long getParkingSpotId() {
        return parkingSpotId;
    }

    public void setParkingSpotId(Long parkingSpotId) {
        this.parkingSpotId = parkingSpotId;
    }

    public Long getCarId() {
        return carId;
    }

    public void setCarId(Long carId) {
        this.carId = carId;
    }
}
