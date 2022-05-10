package sk.stuba.fei.uim.vsa.pr2.web.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UserRequest {
    private Long id;
    private String firstname;
    private String lastname;
    private String email;
    private List<CarRequest> cars;
    private List<CouponRequest> coupons;

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
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

    public void setId(Long id) {
        this.id = id;
    }

    public List<CarRequest> getCars() {
        return cars;
    }

    public void setCars(List<CarRequest> cars) {
        this.cars = cars;
    }

    public List<CouponRequest> getCoupons() {
        return coupons;
    }

    public void setCoupons(List<CouponRequest> coupons) {
        this.coupons = coupons;
    }
}
