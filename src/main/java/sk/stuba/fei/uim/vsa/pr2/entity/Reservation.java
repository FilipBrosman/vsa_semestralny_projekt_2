package sk.stuba.fei.uim.vsa.pr2.entity;

import com.fasterxml.jackson.annotation.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "RESERVATION")
public class Reservation implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date start;

    @Temporal(TemporalType.TIMESTAMP)
    private Date end;

    @JsonIgnore
    @Temporal(TemporalType.DATE)
    private final Date reservationDate = new Date();

    private Integer prices;

    @OneToOne(mappedBy = "reservation", fetch = FetchType.EAGER)
    @JsonManagedReference
    private Car car;

    @ManyToOne
    @JsonBackReference
    private ParkingSpot spot;

    @OneToOne
    @JsonBackReference
    private Coupon coupon;

    public Reservation() {
    }

    public Reservation(ParkingSpot spot, Car car) {
        this.spot = spot;
        this.car = car;
    }

    public void setStart(Date startTime) {
        this.start = startTime;
    }

    public void setEnd(Date endTime) {
        this.end = endTime;
    }

    public Date getStart() {
        return start;
    }

    public Date getEnd() {
        return end;
    }

    public Long getId() {
        return id;
    }

    public Integer getPrices() {
        return prices;
    }

    public void setPrices(Integer sumPrice) {
        this.prices = sumPrice;
    }

    public ParkingSpot getSpot() {
        return spot;
    }

    public Coupon getCoupon() {
        return coupon;
    }

    public void setCoupon(Coupon coupon) {
        this.coupon = coupon;
    }

    public Date getReservationDate() {
        return reservationDate;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Car getCar() {
        return car;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setSpot(ParkingSpot spot) {
        this.spot = spot;
    }

    @Override
    public String toString() {
        if (car == null)
            return "Reservation{" +
                    "id=" + id +
                    ", startTime=" + start + '\n'+
                    ", endTime=" + end + '\n'+
                    ", sumPrice=" + prices + '\n'+
                    ", parkingSpot=" + spot.toString() +
                    ", car=" + false +
                    '}';

        return "Reservation{" +
                "id=" + id +
                ", startTime=" + start + '\n'+
                ", endTime=" + end + '\n'+
                ", sumPrice=" + prices + '\n'+
                ", parkingSpot=" + spot.toString() +
                ", car=" + car.toString() +
                '}';
    }
}
