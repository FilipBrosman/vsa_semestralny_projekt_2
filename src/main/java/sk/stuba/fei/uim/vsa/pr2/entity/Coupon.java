package sk.stuba.fei.uim.vsa.pr2.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "COUPON")
public class Coupon implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private Integer discount;
    @Temporal(TemporalType.TIMESTAMP)
    private Date usedDate;

    @ManyToOne
    @JsonBackReference
    private User owner;

    @OneToOne(mappedBy = "coupon")
    @JsonManagedReference
    private Reservation reservation;

    public Coupon() {
    }

    public Long getId() {
        return id;
    }

    public Coupon(String name, Integer discount) {
        this.name = name;
        this.discount = discount;
    }

    public Integer getDiscount() {
        return discount;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public Date getUsedDate() {
        return usedDate;
    }

    public void setUsedDate(Date usedDate) {
        this.usedDate = usedDate;
    }

    @Override
    public String toString() {
        return "Coupon{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", discount=" + discount +
                ", usedDate=" + usedDate +
                '}';
    }
}
