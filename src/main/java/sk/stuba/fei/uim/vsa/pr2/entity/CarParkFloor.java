package sk.stuba.fei.uim.vsa.pr2.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "CAR_PARK_FLOOR")
public class CarParkFloor implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String identifier;

    @ManyToOne
    @JsonBackReference
    private CarPark carPark;

    @OneToMany(mappedBy = "carParkFloor", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER, orphanRemoval = true)
    //@JoinColumn(name = "park_floor")
    @JsonManagedReference
    private final List<ParkingSpot> spots = new ArrayList<>();

    public CarParkFloor(CarPark carPark, String identifier) {
        this.carPark = carPark;
        this.identifier = identifier;
    }

    public CarParkFloor(String identifier) {
        this.identifier = identifier;
    }

    public CarParkFloor() {
    }

    public String getIdentifier() {
        return identifier;
    }

    public CarPark getCarPark() {
        return carPark;
    }

    public void setCarPark(CarPark carPark) {
        this.carPark = carPark;
    }


    public List<ParkingSpot> getSpots() {
        return spots;
    }

    @JsonIgnore
    public List<ParkingSpot> getAvailableSpots() {
        return this.spots.stream().filter(ps -> ps.getCar() == null).collect(Collectors.toList());
    }

    @JsonIgnore
    public List<ParkingSpot> getOccupiedSpots() {
        return this.spots.stream().filter(ps -> ps.getCar() != null).collect(Collectors.toList());
    }

    public Long getId() {
        return id;
    }

    public void setIdentifier(String floorIdentifier) {
        this.identifier = floorIdentifier;
    }

    @Override
    public String toString() {
        return "CarParkFloor{" +
                "id=" + id +
                ", floorIdentifier='" + identifier + '\'' +
                ", carPark=" + carPark +
                '}'+'\n';
    }
}
