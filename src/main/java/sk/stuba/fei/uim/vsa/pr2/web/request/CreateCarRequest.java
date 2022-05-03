package sk.stuba.fei.uim.vsa.pr2.web.request;

import sk.stuba.fei.uim.vsa.pr2.entity.User;

public class CreateCarRequest {
    private String ecv;
    private String brand;
    private String model;
    private String color;
    private Long owner;

    public String getEcv() {
        return ecv;
    }

    public void setEcv(String ecv) {
        this.ecv = ecv;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Long getOwner() {
        return owner;
    }

    public void setOwner(Long owner) {
        this.owner = owner;
    }
}
