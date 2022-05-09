package sk.stuba.fei.uim.vsa.pr2.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "CAR_PARK")
public class CarPark implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String name;
    private String address;
    private Integer prices;

    @OneToMany(mappedBy = "carPark", cascade = CascadeType.REMOVE, fetch = FetchType.EAGER, orphanRemoval = true)
    @JsonManagedReference
    private final List<CarParkFloor> floors = new ArrayList<>();

    public CarPark() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CarPark(String name, String address, Integer pricePerHour) {
        this.name = name;
        this.address = address;
        this.prices = pricePerHour;
    }

    public List<CarParkFloor> getFloors() {
        return floors;
    }

    public Integer getPrices() {
        return prices;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPrices(Integer pricePerHour) {
        this.prices = pricePerHour;
    }

    @Override
    public String toString() {
        return "CarPark{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", pricePerHour=" + prices +
                '}'+'\n';
    }
}
