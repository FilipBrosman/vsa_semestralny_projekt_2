package sk.stuba.fei.uim.vsa.pr2.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "CAR_PARK_FLOOR")
public class CarParkFloor {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String floorIdentifier;

    @ManyToOne
    @JsonBackReference
    private CarPark carPark;

    @OneToMany(mappedBy = "carParkFloor", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER)
    //@JoinColumn(name = "park_floor")
    @JsonManagedReference
    private final List<ParkingSpot> parkingSpots = new ArrayList<>();

    public CarParkFloor(CarPark carPark, String floorIdentifier) {
        this.carPark = carPark;
        this.floorIdentifier = floorIdentifier;
    }

    public CarParkFloor(String floorIdentifier) {
        this.floorIdentifier = floorIdentifier;
    }

    public CarParkFloor() {
    }

    public String getFloorIdentifier() {
        return floorIdentifier;
    }

    public CarPark getCarPark() {
        return carPark;
    }

    public void setCarPark(CarPark carPark) {
        this.carPark = carPark;
    }

    public List<ParkingSpot> getParkingSpots() {
        return parkingSpots;
    }

    public List<ParkingSpot> getAvailableSpots() {
        return this.parkingSpots.stream().filter(ps -> ps.getCar() == null).collect(Collectors.toList());
    }

    public List<ParkingSpot> getOccupiedSpots() {
        return this.parkingSpots.stream().filter(ps -> ps.getCar() != null).collect(Collectors.toList());
    }

    public Long getId() {
        return id;
    }

    public void setFloorIdentifier(String floorIdentifier) {
        this.floorIdentifier = floorIdentifier;
    }

    @Override
    public String toString() {
        return "CarParkFloor{" +
                "id=" + id +
                ", floorIdentifier='" + floorIdentifier + '\'' +
                ", carPark=" + carPark +
                '}'+'\n';
    }
}
