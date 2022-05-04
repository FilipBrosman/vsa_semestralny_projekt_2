package sk.stuba.fei.uim.vsa.pr2.web.request;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.Date;

public class GetReservationsRequest {
    private Date start;
    private Long car;
    private Long spot;
    private Long coupon;

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Long getCar() {
        return car;
    }

    public void setCar(Long car) {
        this.car = car;
    }

    public Long getSpot() {
        return spot;
    }

    public void setSpot(Long spot) {
        this.spot = spot;
    }

    public Long getCoupon() {
        return coupon;
    }

    public void setCoupon(Long coupon) {
        this.coupon = coupon;
    }
}
