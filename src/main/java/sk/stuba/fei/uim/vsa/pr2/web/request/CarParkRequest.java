package sk.stuba.fei.uim.vsa.pr2.web.request;

import java.util.List;

public class CarParkRequest {
    private Long id;
    private String name;
    private String address;
    private Integer prices;
    private List<CarParkFloorRequest> floors;

    public List<CarParkFloorRequest> getFloors() {
        return floors;
    }

    public void setFloors(List<CarParkFloorRequest> floors) {
        this.floors = floors;
    }

    public CarParkRequest() {
    }

    public CarParkRequest(String name, String address, Integer pricePerHour) {
        this.name = name;
        this.address = address;
        this.prices = pricePerHour;
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

    public Integer getPrices() {
        return prices;
    }

    public void setPrices(Integer prices) {
        this.prices = prices;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
