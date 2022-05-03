package sk.stuba.fei.uim.vsa.pr2.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import org.eclipse.persistence.jpa.jpql.parser.DateTime;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Table(name = "RESERVATION")
public class Reservation implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date startTime;

    @Temporal(TemporalType.TIMESTAMP)
    private Date endTime;

    @Temporal(TemporalType.DATE)
    private final Date reservationDate = new Date();

    private Integer sumPrice;

    @ManyToOne
    @JsonBackReference
    private ParkingSpot parkingSpot;
    @OneToOne(mappedBy = "reservation", fetch = FetchType.EAGER)
    @JsonManagedReference
    private Car car;

    @OneToOne
    @JsonBackReference
    private Coupon coupon;

    public Reservation() {
    }

    public Reservation(ParkingSpot parkingSpot, Car car) {
        this.parkingSpot = parkingSpot;
        this.car = car;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getEndTime() {
        return endTime;
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

    public ParkingSpot getParkingSpot() {
        return parkingSpot;
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
                    ", startTime=" + startTime + '\n'+
                    ", endTime=" + endTime + '\n'+
                    ", sumPrice=" + sumPrice + '\n'+
                    ", parkingSpot=" + parkingSpot.toString() +
                    ", car=" + false +
                    '}';

        return "Reservation{" +
                "id=" + id +
                ", startTime=" + startTime + '\n'+
                ", endTime=" + endTime + '\n'+
                ", sumPrice=" + sumPrice + '\n'+
                ", parkingSpot=" + parkingSpot.toString() +
                ", car=" + car.toString() +
                '}';
    }
}
