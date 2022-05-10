package sk.stuba.fei.uim.vsa.pr2;

import sk.stuba.fei.uim.vsa.pr2.entity.*;

import javax.persistence.*;
import java.util.*;
import java.util.stream.Collectors;

public class CarParkService extends AbstractCarParkService {

    EntityManager entityManager;

    public CarParkService() {
        this.entityManager = this.emf.createEntityManager();
    }

    @Override
    public Object createCarPark(String name, String address, Integer pricePerHour) {
        if (name==null || address == null || pricePerHour == null) return null;

        Optional<CarPark> cp = Optional.ofNullable((CarPark) getCarPark(name));
        if (cp.isPresent())
            return null;
        CarPark newCp = new CarPark(name, address, pricePerHour);
        persist(newCp);
        return newCp;
    }

    @Override
    public Object getCarPark(Long carParkId) {
        if (carParkId == null) return null;
        return entityManager.find(CarPark.class, carParkId);
    }

    @Override
    public Object getCarPark(String carParkName) {
        if (carParkName == null) return null;
        TypedQuery<CarPark> query = entityManager.createQuery("select cp from CarPark cp where cp.name=:car_park_name", CarPark.class);
        query.setParameter("car_park_name", carParkName);
        Optional<CarPark> optionalCarPark = query.getResultStream().findFirst();
        return optionalCarPark.orElse(null);
    }

    @Override
    public List<Object> getCarParks() {
        return entityManager.createQuery("select cp from CarPark cp", CarPark.class).getResultStream().collect(Collectors.toList());
    }

    @Override
    public Object updateCarPark(Object carPark) {
        if (carPark == null) return null;
        Optional<CarPark> cp = Optional.ofNullable((CarPark) getCarPark(((CarPark)carPark).getName()));
        if (cp.isPresent()) {
            entityManager.refresh(carPark);
            return null;
        }
        merge(carPark);
        return carPark;
    }

    @Override
    public Object deleteCarPark(Long carParkId) {
        if (carParkId == null) return null;
        Optional<CarPark> cp = Optional.ofNullable(entityManager.find(CarPark.class, carParkId));
        if(cp.isPresent()) {
            remove(cp.get());
            return cp.get();
        }
        return null;
    }

    @Override
    public Object createCarParkFloor(Long carParkId, String floorIdentifier) {
        if (carParkId == null) return null;
        if (floorIdentifier==null) return null;

        CarPark cp = entityManager.find(CarPark.class, carParkId);
        if (cp == null)
            return null;

        Optional<CarParkFloor> optionalCarParkFloor = Optional.ofNullable((CarParkFloor) getCarParkFloorByCarParkId(cp.getId(), floorIdentifier));
        if (optionalCarParkFloor.isPresent())
            return null;
        CarParkFloor cpf = new CarParkFloor(cp, floorIdentifier);
        cp.getFloors().add(cpf);
        persist(cp,cpf);
        return cpf;
    }

    public Object getCarParkFloorByCarParkId(Long carParkId, String floorIdentifier) {
        if (carParkId == null) return null;
        if (floorIdentifier==null) return null;

        CarPark cp = (CarPark) getCarPark(carParkId);
        if (cp == null)
            return null;

        TypedQuery<CarParkFloor> query = entityManager.createQuery("select cpf from CarParkFloor cpf " +
                "where cpf.identifier=:floor_identifier AND cpf.carPark=:cp",CarParkFloor.class);
        query.setParameter("floor_identifier", floorIdentifier);
        query.setParameter("cp", cp);

        Optional<CarParkFloor> optionalCarParkFloor = query.getResultStream().findFirst();
        return optionalCarParkFloor.orElse(null);
    }

    @Override
    public Object getCarParkFloor(Long carParkFloorId) {
        if (carParkFloorId == null) return null;
        return entityManager.find(CarParkFloor.class, carParkFloorId);
    }

    @Override
    public List<Object> getCarParkFloors(Long carParkId) {
        if (carParkId == null) {
            return new ArrayList<>();
        }
        CarPark cp = (CarPark) getCarPark(carParkId);
        if (cp == null)
            return new ArrayList<>();

        return Arrays.asList(cp.getFloors().toArray());
    }

    @Override
    public Object updateCarParkFloor(Object carParkFloor) {
        if (carParkFloor==null) return null;

        Optional<CarParkFloor> cp = Optional.ofNullable((CarParkFloor) getCarParkFloor(((CarParkFloor)carParkFloor).getId()));
        if (cp.isPresent()) {
            entityManager.refresh(carParkFloor);
            return null;
        }
        merge(carParkFloor);
        return carParkFloor;
    }

    @Override
    public Object deleteCarParkFloor(Long carParkFloorId) {
        if (carParkFloorId==null) return null;

        Optional<CarParkFloor> cpf = Optional.ofNullable(entityManager.find(CarParkFloor.class, carParkFloorId));
        if(cpf.isPresent()) {
            cpf.get().getCarPark().getFloors().remove(cpf.get());
            remove(cpf.get());
            return cpf.get();
        }
        return null;
    }

    @Override
    public Object createParkingSpot(Long carParkId, String floorIdentifier, String spotIdentifier) {
        if (spotIdentifier == null || carParkId == null || floorIdentifier == null)
            return null;

        CarPark cp = (CarPark)getCarPark(carParkId);
        if (cp == null)
            return null;

        CarParkFloor cpf = (CarParkFloor)getCarParkFloorByCarParkId(cp.getId(), floorIdentifier);
        if (cpf == null)
            return null;

        ParkingSpot ps = new ParkingSpot(cpf, spotIdentifier);
        cpf.getSpots().add(ps);
        persist(cpf, ps);
        return ps;
    }

    @Override
    public Object getParkingSpot(Long parkingSpotId) {
        if (parkingSpotId == null) return null;

        return entityManager.find(ParkingSpot.class, parkingSpotId);
    }

    @Override
    public List<Object> getParkingSpots(Long carParkId, String floorIdentifier) {
        if ( carParkId == null || floorIdentifier == null) return new ArrayList<>();

        CarPark cp = (CarPark)getCarPark(carParkId);
        if (cp == null)
            return new ArrayList<>();
        CarParkFloor cpf = (CarParkFloor)getCarParkFloorByCarParkId(cp.getId(), floorIdentifier);
        if (cpf == null)
            return new ArrayList<>();
        return Arrays.asList(cpf.getSpots().toArray());
    }

    @Override
    public Map<String, List<Object>> getParkingSpots(Long carParkId) {
        Map<String, List<Object>> parkingSpots = new HashMap<>();
        if (carParkId == null) return new HashMap<>();

        CarPark cp = entityManager.find(CarPark.class, carParkId);
        if (cp == null) return new HashMap<>();

        cp.getFloors().forEach(carParkFloor -> parkingSpots.put(carParkFloor.getIdentifier(),
                Arrays.asList(carParkFloor.getSpots().toArray())));
        return parkingSpots;
    }

    @Override
    public Map<String, List<Object>> getAvailableParkingSpots(String carParkName) {
        Map<String, List<Object>> parkingSpots = new HashMap<>();
        CarPark cp = (CarPark) getCarPark(carParkName);
        if (cp == null) return new HashMap<>();

        cp.getFloors().forEach(carParkFloor -> {
            CarParkFloor cpf = (CarParkFloor) getCarParkFloorByCarParkId(cp.getId(), carParkFloor.getIdentifier());
            if (cpf == null) return;
            parkingSpots.put(carParkFloor.getIdentifier(),
                Arrays.asList(carParkFloor.getAvailableSpots().toArray()));
        });

        return parkingSpots;
    }

    @Override
    public Map<String, List<Object>> getOccupiedParkingSpots(String carParkName) {
        Map<String, List<Object>> parkingSpots = new HashMap<>();
        CarPark cp = (CarPark) getCarPark(carParkName);
        if (cp == null) return new HashMap<>();

        cp.getFloors().forEach(carParkFloor -> {
            CarParkFloor cpf = (CarParkFloor) getCarParkFloorByCarParkId(cp.getId(), carParkFloor.getIdentifier());
            if (cpf == null) return;
            parkingSpots.put(carParkFloor.getIdentifier(),
                    Arrays.asList(carParkFloor.getOccupiedSpots().toArray()));
        });

        return parkingSpots;
    }

    @Override
    public Object updateParkingSpot(Object parkingSpot) {
        if (parkingSpot == null) return null;
        merge(parkingSpot);
        return parkingSpot;
    }

    @Override
    public Object deleteParkingSpot(Long parkingSpotId) {
        if (parkingSpotId == null) return null;

        Optional<ParkingSpot> ps = Optional.ofNullable(entityManager.find(ParkingSpot.class, parkingSpotId));
        if(ps.isPresent()) {
            ps.get().getCarParkFloor().getSpots().remove(ps.get());
            remove(ps.get());
            return ps.get();
        }
        return null;
    }

    @Override
    public Object createCar(Long userId, String brand, String model, String colour, String vehicleRegistrationPlate) {
        if ( userId == null || brand == null || model == null || colour == null || vehicleRegistrationPlate == null)
            return null;

        User user = entityManager.find(User.class, userId);
        if (user == null)
            return null;

        Optional<Car> optionalCar = Optional.ofNullable((Car)getCar(vehicleRegistrationPlate));
        if(optionalCar.isPresent())
            return null;

        Car car = new Car(vehicleRegistrationPlate, brand, model, colour, user);
        user.getCars().add(car);
        persist(car);
        return car;
    }

    @Override
    public Object getCar(Long carId) {
        if (carId == null) return null;
        return entityManager.find(Car.class, carId);
    }

    @Override
    public Object getCar(String vehicleRegistrationPlate) {
        if (vehicleRegistrationPlate == null) return null;

        TypedQuery<Car> query = entityManager.createQuery("select car from Car car " +
                "where car.vrp=:vehicleRegistrationPlate",Car.class);
        query.setParameter("vehicleRegistrationPlate", vehicleRegistrationPlate);

        Optional<Car> optionalCarParkFloor = query.getResultStream().findFirst();
        return optionalCarParkFloor.orElse(null);
    }

    @Override
    public List<Object> getCars(Long userId) {
        if (userId==null) return new ArrayList<>();

        User u = entityManager.find(User.class, userId);
        if (u==null)
            return new ArrayList<>();
        return Arrays.asList(u.getCars().toArray());
    }

    @Override
    public Object updateCar(Object car) {
        if (car==null) return null;
        merge(car);
        return car;
    }

    @Override
    public Object deleteCar(Long carId) {
        if (carId==null) return null;
        Optional<Car> car = Optional.ofNullable(entityManager.find(Car.class, carId));
        if(car.isPresent()) {
            car.get().getOwner().getCars().remove(car.get());
            remove(car.get());
            return car.get();
        }
        return null;
    }

    @Override
    public Object createUser(String firstname, String lastname, String email) {
        if (firstname == null || lastname == null || email == null) return null;

        Optional<User> optionalCar = Optional.ofNullable((User)getUser(email));
        if(optionalCar.isPresent())
            return null;

        User user = new User(firstname, lastname,email);
        persist(user);
        return user;
    }

    @Override
    public Object getUser(Long userId) {
        if (userId==null) return null;
        return entityManager.find(User.class, userId);
    }

    @Override
    public Object getUser(String email) {
        if (email==null) return null;

        TypedQuery<User> query = entityManager.createQuery("select u from User u " +
                "where u.email=:email",User.class);
        query.setParameter("email", email);

        Optional<User> optionalCarParkFloor = query.getResultStream().findFirst();
        return optionalCarParkFloor.orElse(null);
    }

    @Override
    public List<Object> getUsers() {
        return entityManager.createQuery("select u from User u", User.class).getResultStream().collect(Collectors.toList());
    }

    @Override
    public Object updateUser(Object user) {
        if (user==null) return null;
        merge(user);
        return user;
    }

    @Override
    public Object deleteUser(Long userId) {
        if (userId==null) return null;
        Optional<User> user = Optional.ofNullable(entityManager.find(User.class, userId));
        if(!user.isPresent())
            return null;

        remove(user.get());
        return user.get();
    }

    @Override
    public Object createReservation(Long parkingSpotId, Long cardId) {
        if (parkingSpotId==null) return null;

        ParkingSpot ps = (ParkingSpot)getParkingSpot(parkingSpotId);
        if(ps ==null) return null;

        if (ps.getCar() != null) return null;

        Car car = (Car) getCar(cardId);
        if(car ==null)
            return null;

        if (car.getReservation() != null)
            return null;

        TypedQuery<Reservation> query = entityManager.createQuery("select r from Reservation r " +
                "where r.spot=:parking_spot", Reservation.class);
        query.setParameter("parking_spot", ps);
        Optional<Reservation> reservation = query.getResultList().stream().findFirst();
        if (reservation.isPresent()){
            if (reservation.get().getEnd() == null){
                return null;
            }
        }

        Reservation newReservation = new Reservation(ps, car);
        newReservation.setStart(new Date());
        car.setReservation(newReservation);
        ps.setCar(car);
        ps.getReservations().add(newReservation);
        persist(ps, car, newReservation);
        return newReservation;
    }

    @Override
    public Object endReservation(Long reservationId) {
        if (reservationId==null) return null;

        Reservation res = entityManager.find(Reservation.class, reservationId);
        if (res == null)
            return null;

        res.setEnd(new Date());

        long hours = (res.getEnd().getTime() - res.getStart().getTime())/1000/60/60;
        Integer pph = (int) ((hours==0)?1 : hours*res.getSpot().getCarParkFloor().getCarPark().getPrices());
        res.setPrices(pph);
        ParkingSpot ps = res.getSpot();
        Car car = ps.getCar();
        car.setReservation(null);
        ps.setCar(null);
        merge(car, ps,res);
        return res;
    }

    @Override
    public List<Object> getReservations(Long parkingSpotId, Date date) {
        if (parkingSpotId==null || date == null) new ArrayList<>();

        ParkingSpot ps = entityManager.find(ParkingSpot.class, parkingSpotId);
        if (ps == null)
            return new ArrayList<>();
        TypedQuery<Reservation> query = entityManager.createQuery("select r from Reservation r " +
                "where r.reservationDate = :date AND r.spot=:parking_spot_id", Reservation.class);
        query.setParameter("date", date);
        query.setParameter("parking_spot_id", ps);
        return query.getResultStream().collect(Collectors.toList());
    }

    @Override
    public List<Object> getMyReservations(Long userId) {
        if (userId == null) return new ArrayList<>();

        List<Reservation> reservationList = new ArrayList<>();
        User user = (User) getUser(userId);
        if (user==null)
            return new ArrayList<>();

        user.getCars().forEach(car -> {
            if (car.getReservation() != null)
                reservationList.add(car.getReservation());
        });

        return Arrays.asList(reservationList.toArray());
    }

    public Object getReservationById(Long reservationId) {
        if (reservationId == null) return null;
        return entityManager.find(Reservation.class, reservationId);
    }

    @Override
    public Object updateReservation(Object reservation) {
        if (reservation == null) return null;
        merge(reservation);
        return reservation;
    }

    @Override
    public Object createDiscountCoupon(String name, Integer discount) {
        if (name == null || discount == null) return null;
        Coupon c = new Coupon(name, discount);
        persist(c);
        return c;
    }

    @Override
    public void giveCouponToUser(Long couponId, Long userId) {
        if (couponId == null || userId == null) return;

        Optional<Coupon> c = Optional.ofNullable(entityManager.find(Coupon.class, couponId));
        Optional<User> u = Optional.ofNullable(entityManager.find(User.class, userId));
        if (!c.isPresent())
            return;

        if(!u.isPresent())
            return;

        u.get().getCoupons().add(c.get());
        c.get().setOwner(u.get());
        persist(u.get(),c.get());
    }

    @Override
    public Object getCoupon(Long couponId) {
        if (couponId == null) return null;
        return entityManager.find(Coupon.class, couponId);
    }

    @Override
    public List<Object> getCoupons(Long userId) {
        if (userId == null) return new ArrayList<>();;

        User u = entityManager.find(User.class, userId);
        if (u == null)
            return new ArrayList<>();
        return Arrays.asList(u.getCoupons().toArray());
    }

    @Override
    public Object endReservation(Long reservationId, Long couponId) {
        if (reservationId == null || couponId == null) return null;

        Reservation res = entityManager.find(Reservation.class, reservationId);
        if (res == null)
            return null;

        Coupon discount = entityManager.find(Coupon.class, couponId);
        if (discount == null)
            return null;

        if (discount.getUsedDate() != null)
            return null;

        res.setEnd(new Date());
        discount.setUsedDate(res.getEnd());

        long hours = (res.getEnd().getTime() - res.getStart().getTime())/1000/60/60;
        int pph = (int) ((hours==0)?1 : hours*res.getSpot().getCarParkFloor().getCarPark().getPrices());
        Integer discounted = pph - ((pph/100)* discount.getDiscount());
        res.setPrices(discounted);
        res.setCoupon(discount);
        persist(res);
        return res;
    }

    @Override
    public Object deleteCoupon(Long couponId) {
        if (couponId == null) return null;

        Optional<Coupon> c = Optional.ofNullable(entityManager.find(Coupon.class ,couponId));
        if (!c.isPresent())
            return null;
        if (c.get().getOwner() != null)
            c.get().getOwner().getCoupons().remove(c.get());

        remove(c.get());
        return c.get();
    }

    public void persist(Object... objects) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        try {
            for(Object entity : objects){
                entityManager.persist(entity);
            }
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        }
    }

    private void remove(Object... objects) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        try {
            for(Object entity : objects){
                entityManager.remove(entity);
            }
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        }
    }

    private void merge(Object... objects) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        try {
            for(Object entity : objects){
                entityManager.merge(entity);
            }
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            transaction.rollback();
        }
    }
}
