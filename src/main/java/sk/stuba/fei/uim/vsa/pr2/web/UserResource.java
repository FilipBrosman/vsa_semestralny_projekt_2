    package sk.stuba.fei.uim.vsa.pr2.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import sk.stuba.fei.uim.vsa.pr2.entity.Car;
import sk.stuba.fei.uim.vsa.pr2.entity.Coupon;
import sk.stuba.fei.uim.vsa.pr2.entity.User;
import sk.stuba.fei.uim.vsa.pr2.web.request.CarRequest;
import sk.stuba.fei.uim.vsa.pr2.web.request.CouponRequest;
import sk.stuba.fei.uim.vsa.pr2.web.request.UserRequest;

import java.util.ArrayList;
import java.util.List;

@Path("/")
public class UserResource extends AbstractResource {

    @GET
    @Path("/users")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllUsersOrByEmail(@QueryParam("email") String email) {
        try{
            List<Object> us = new ArrayList<>();
            if (email == null) {
                us = cps.getUsers();
                return Response
                    .status(Response.Status.OK)
                    .entity(json.writeValueAsString(us))
                    .build();
            }
            User u = (User)cps.getUser(email);
            if (u == null) return Response.status(Response.Status.NOT_FOUND).build();
            us.add(u);
            return Response
                .status(Response.Status.OK)
                .entity(json.writeValueAsString(us))
                .build();
        }
        catch (JsonProcessingException e){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @GET
    @Path("/users/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser(@PathParam("id") Long id){
        try{
            User ps = (User) cps.getUser(id);
            if (ps == null) return Response
                    .status(Response.Status.NOT_FOUND)
                    .build();
            return Response
                    .status(Response.Status.OK)
                    .entity(json.writeValueAsString(ps))
                    .build();
        }
        catch (JsonProcessingException e){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    //TODO: PUT /users/{id}

    @POST
    @Path("/users")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUser(String body){
        try{
            UserRequest ur = json.readValue(body, UserRequest.class);

            User u = (User) cps.createUser(ur.getFirstname(), ur.getLastname(), ur.getEmail());
            if (u == null) return Response.status(Response.Status.BAD_REQUEST).build();
            
            for (CarRequest car: ur.getCars()) {
                Car c = (Car) cps.createCar(u.getId(), car.getBrand(), car.getModel(), car.getColour(), car.getVrp());
                if (c==null)
                    return Response.status(Response.Status.BAD_REQUEST).build();
            }

            for (CouponRequest coupon: ur.getCoupons()){
                Coupon c = (Coupon) cps.createDiscountCoupon(coupon.getName(),coupon.getDiscount());
                if (c==null)
                    return Response.status(Response.Status.BAD_REQUEST).build();
                cps.giveCouponToUser(c.getId(), u.getId());
            }

            return Response
                    .status(Response.Status.CREATED)
                    .entity(json.writeValueAsString(u))
                    .build();
        }
        catch (JsonProcessingException e){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @DELETE
    @Path("/users/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteParkingSpot(@PathParam("id") Long id){
            User ps = (User) cps.getUser(id);
            if (ps == null) return Response.status(Response.Status.NOT_FOUND).build();
            cps.deleteUser(id);
            return Response.status(Response.Status.NO_CONTENT).build();
    }
}
