package sk.stuba.fei.uim.vsa.pr2.entity;

import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "PARKING_SPOT")
public class ParkingSpot implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String identifier;

    @ManyToOne
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
            property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    private CarParkFloor carParkFloor;

    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class,
            property = "id")
    @JsonIdentityReference(alwaysAsId = true)
    @Transient
    private CarPark carPark;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JsonIgnore
    private Car car;

    public void setId(Long id) {
        this.id = id;
    }

    public CarPark getCarPark() {
        return carParkFloor.getCarPark();
    }

    public void setCarPark(CarPark carPark) {
        this.carPark = carPark;
    }

    @OneToMany(mappedBy = "spot",cascade = CascadeType.REMOVE, orphanRemoval = true)
    @JsonManagedReference
    private List<Reservation> reservations = new ArrayList<>();

    public ParkingSpot() {
    }

    public ParkingSpot(CarParkFloor carParkFloor, String spotLabel) {
        this.carParkFloor = carParkFloor;
        this.identifier = spotLabel;
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

    public void setCarParkFloor(CarParkFloor carParkFloor) {
        this.carParkFloor = carParkFloor;
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

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String spotLabel) {
        this.identifier = spotLabel;
    }

    @Override
    public String toString() {
        if (car == null)
            return "ParkingSpot{" +
                "id=" + id +
                ", spotLabel='" + identifier + '\'' +
                ", carParkFloor=" + carParkFloor +
                ", car=" + false +
                '}'+'\n';

        return "ParkingSpot{" +
                "id=" + id +
                ", spotLabel='" + identifier + '\'' +
                ", carParkFloor=" + carParkFloor +
                ", car=" + car.toString() +
                '}'+'\n';
    }
}
