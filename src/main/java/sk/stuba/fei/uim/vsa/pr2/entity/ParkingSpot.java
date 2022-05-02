package sk.stuba.fei.uim.vsa.pr2.entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "PARKING_SPOT")
public class ParkingSpot {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String spotLabel;

    @ManyToOne
    private CarParkFloor carParkFloor;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private Car car;

    @OneToMany(mappedBy = "parkingSpot",cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Reservation> reservations = new ArrayList<>();

    public ParkingSpot() {
    }

    public ParkingSpot(CarParkFloor carParkFloor, String spotLabel) {
        this.carParkFloor = carParkFloor;
        this.spotLabel = spotLabel;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public Long getId() {
        return id;
    }

    public CarParkFloor getCarParkFloor() {
        return carParkFloor;
    }

    public List<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(List<Reservation> reservations) {
        this.reservations = reservations;
    }

    @Override
    public String toString() {
        if (car == null)
            return "ParkingSpot{" +
                "id=" + id +
                ", spotLabel='" + spotLabel + '\'' +
                ", carParkFloor=" + carParkFloor +
                ", car=" + false +
                '}'+'\n';

        return "ParkingSpot{" +
                "id=" + id +
                ", spotLabel='" + spotLabel + '\'' +
                ", carParkFloor=" + carParkFloor +
                ", car=" + car.toString() +
                '}'+'\n';
    }
}
