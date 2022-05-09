package sk.stuba.fei.uim.vsa.pr2.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

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

    @Temporal(TemporalType.DATE)
    private final Date reservationDate = new Date();

    private Integer sumPrice;

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

    public Integer getSumPrice() {
        return sumPrice;
    }

    public void setSumPrice(Integer sumPrice) {
        this.sumPrice = sumPrice;
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

    @Override
    public String toString() {
        if (car == null)
            return "Reservation{" +
                    "id=" + id +
                    ", startTime=" + start + '\n'+
                    ", endTime=" + end + '\n'+
                    ", sumPrice=" + sumPrice + '\n'+
                    ", parkingSpot=" + spot.toString() +
                    ", car=" + false +
                    '}';

        return "Reservation{" +
                "id=" + id +
                ", startTime=" + start + '\n'+
                ", endTime=" + end + '\n'+
                ", sumPrice=" + sumPrice + '\n'+
                ", parkingSpot=" + spot.toString() +
                ", car=" + car.toString() +
                '}';
    }
}
