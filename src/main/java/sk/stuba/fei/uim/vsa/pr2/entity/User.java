package sk.stuba.fei.uim.vsa.pr2.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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

    private String firstname;
    private String lastname;

    @Column(unique = true)
    private String email;

    @OneToMany(mappedBy = "owner",cascade = CascadeType.REMOVE, fetch=FetchType.EAGER)

    private final List<Car> cars = new ArrayList<>();

    @OneToMany(mappedBy = "owner",cascade = CascadeType.REMOVE, fetch=FetchType.EAGER)
    @JsonManagedReference
    private final List<Coupon> coupons = new ArrayList<>();

    public User() {
    }

    public User(String firstname, String lastname, String email) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String name) {
        this.firstname = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String surname) {
        this.lastname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
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
                ", name='" + firstname + '\'' +
                ", surname='" + lastname + '\'' +
                ", email='" + email + '\'' +
                '}'+
                '\n';
    }
}
