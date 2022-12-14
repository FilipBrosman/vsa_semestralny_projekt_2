package sk.stuba.fei.uim.vsa.pr2.web;


import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import sk.stuba.fei.uim.vsa.pr2.entity.Car;
import sk.stuba.fei.uim.vsa.pr2.entity.User;
import sk.stuba.fei.uim.vsa.pr2.web.request.CarRequest;
import sk.stuba.fei.uim.vsa.pr2.web.response.ObjectNotFoundException;

import java.util.List;

@Path("/cars")
public class CarResource extends AbstractResource {

    @GET
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllCars(@QueryParam("user") Long user) {
        try{
            User u = (User) cps.getUser(user);
            if (u == null) throw new ObjectNotFoundException();

            List<Object> cars = cps.getCars(user);
            if (cars == null) throw new ObjectNotFoundException();

            return Response
                    .status(Response.Status.OK)
                    .entity(json.writeValueAsString(cars))
                    .build();
        }
        catch (JsonProcessingException e){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        catch (ObjectNotFoundException ex){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCar(@PathParam("id") Long id) {
        try{
            Car car = (Car) cps.getCar(id);
            if (car == null) return Response
                    .status(Response.Status.NOT_FOUND)
                    .build();
            return Response
                    .status(Response.Status.OK)
                    .entity(json.writeValueAsString(car))
                    .build();
        }
        catch (JsonProcessingException e){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @POST
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createCar(String body) {
        try{
            CarRequest cr = json.readValue(body, CarRequest.class);
            User u;
            if (cr.getOwner().getId() == null)
                u = (User) cps.createUser(cr.getOwner().getFirstname(), cr.getOwner().getLastname(), cr.getOwner().getEmail());
            else
                u = (User) cps.getUser(cr.getOwner().getId());

            if (u == null )
                return Response.status(Response.Status.BAD_REQUEST).build();

            Car c = (Car) cps.createCar(u.getId(), cr.getBrand(), cr.getModel(), cr.getColour(), cr.getVrp());
            if (c == null) return Response.status(Response.Status.BAD_REQUEST).build();

            return Response
                    .status(Response.Status.CREATED)
                    .entity(json.writeValueAsString(c))
                    .build();
        }
        catch (JsonProcessingException e){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    //TODO: PUT /cars/{id}

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteCar(@PathParam("id") Long id) {
            Car car = (Car) cps.getCar(id);
            if (car == null) return Response.status(Response.Status.NOT_FOUND).build();
            cps.deleteCar(id);
            return Response
                    .status(Response.Status.NO_CONTENT)
                    .build();
    }
}
