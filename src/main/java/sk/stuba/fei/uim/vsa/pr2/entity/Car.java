package sk.stuba.fei.uim.vsa.pr2.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import javax.swing.*;
import java.io.Serializable;

@Entity
@Table(name = "CAR")
public class Car implements Serializable  {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String ecv;

    private String brand;
    private String model;
    private String color;

    @ManyToOne
    @JsonBackReference
    private User owner;

    @OneToOne
    @JsonBackReference
    private Reservation reservation;

    public Car() {
    }

    public Car(String ecv, String brand, String model, String color, User owner) {
        this.ecv = ecv;
        this.brand = brand;
        this.model = model;
        this.color = color;
        this.owner = owner;
    }

    public Long getId() {
        return id;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    @Override
    public String toString() {
        return "Car{" +
                "id=" + id +
                ", ecv='" + ecv + '\'' +
                ", brand='" + brand + '\'' +
                ", model='" + model + '\'' +
                ", color='" + color + '\'' +
                ", owner=" + owner.toString() +
                '}'+
                '\n';
    }
}
