package sk.stuba.fei.uim.vsa.pr2.web;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import sk.stuba.fei.uim.vsa.pr2.entity.Car;
import sk.stuba.fei.uim.vsa.pr2.entity.CarPark;
import sk.stuba.fei.uim.vsa.pr2.entity.User;
import sk.stuba.fei.uim.vsa.pr2.web.request.CarParkRequest;
import sk.stuba.fei.uim.vsa.pr2.web.request.CarRequest;
import sk.stuba.fei.uim.vsa.pr2.web.response.ObjectNotFoundException;
import java.util.List;

@Path("/")
public class CarResource extends AbstractResource {

    @GET
    @Path("/cars")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllCars(String body) {

            return Response.status(Response.Status.BAD_REQUEST).build();

    }

    @GET
    @Path("/cars/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCar(@PathParam("id") Long id) {
        try{
            Car car = (Car) cps.getCar(id);
            if (car == null) return Response
                    .status(Response.Status.NOT_FOUND)
                    .build();
            return Response
                    .status(Response.Status.OK.getStatusCode())
                    .entity(json.writeValueAsString(car))
                    .build();
        }
        catch (JsonProcessingException e){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @POST
    @Path("/cars")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createCar(String body) {
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    //TODO: PUT /cars/{id}

    @DELETE
    @Path("/cars/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteCar(@PathParam("id") Long id) {
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

}
