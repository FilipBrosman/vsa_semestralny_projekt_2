package sk.stuba.fei.uim.vsa.pr2.web.request;

import org.omg.PortableInterceptor.ServerRequestInfo;

public class CreateReservationRequest {
    private String start;
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

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }
}
