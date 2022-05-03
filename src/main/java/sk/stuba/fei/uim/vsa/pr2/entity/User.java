package sk.stuba.fei.uim.vsa.pr2.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "USER")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;
    private String surname;

    @Column(unique = true)
    private String email;

    @OneToMany(mappedBy = "owner",cascade = CascadeType.REMOVE, fetch=FetchType.EAGER)
    @JsonManagedReference
    private final List<Car> cars = new ArrayList<>();

    @OneToMany(mappedBy = "owner",cascade = CascadeType.REMOVE, fetch=FetchType.EAGER)
    @JsonManagedReference
    private final List<Coupon> coupons = new ArrayList<>();

    public User() {
    }

    public User(String name, String surname, String email) {
        this.name = name;
        this.surname = surname;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public List<Car> getCars() {
        return cars;
    }

    public List<Coupon> getCoupons() {
        return coupons;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                '}'+
                '\n';
    }
}
