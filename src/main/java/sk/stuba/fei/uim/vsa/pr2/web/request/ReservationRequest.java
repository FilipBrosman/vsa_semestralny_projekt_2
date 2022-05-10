package sk.stuba.fei.uim.vsa.pr2.web.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ReservationRequest {
    private Long id;
    private String start;
    private String end;
    private Integer prices;
    private CarRequest car;
    private ParkingSpotRequest spot;
    private CouponRequest coupon;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public ParkingSpotRequest getSpot() {
        return spot;
    }

    public void setSpot(ParkingSpotRequest spot) {
        this.spot = spot;
    }

    public Integer getPrices() {
        return prices;
    }

    public void setPrices(Integer prices) {
        this.prices = prices;
    }

    public CarRequest getCar() {
        return car;
    }

    public void setCar(CarRequest car) {
        this.car = car;
    }

    public CouponRequest getCoupon() {
        return coupon;
    }

    public void setCoupon(CouponRequest coupon) {
        this.coupon = coupon;
    }
}
