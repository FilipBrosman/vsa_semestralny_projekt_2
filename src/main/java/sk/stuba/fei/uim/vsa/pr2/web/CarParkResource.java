package sk.stuba.fei.uim.vsa.pr2.web;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import sk.stuba.fei.uim.vsa.pr2.CarParkService;
import sk.stuba.fei.uim.vsa.pr2.entity.CarPark;
import sk.stuba.fei.uim.vsa.pr2.entity.CarParkFloor;
import sk.stuba.fei.uim.vsa.pr2.web.response.ObjectNotFoundException;

import java.util.List;

@Path("/")
public class CarParkResource {
    public static final String EMPTY_RESPONSE = "{}";
    private final ObjectMapper json = new ObjectMapper();
    private final CarParkService cps = new CarParkService();

    @GET
    @Path("/carparks")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllCarParks() {
        try{
            List<Object> carparks = cps.getCarParks();
            if (carparks.isEmpty()) throw new ObjectNotFoundException();
            return Response
                    .status(Response.Status.OK.getStatusCode())
                    .entity(json.writeValueAsString(carparks))
                    .build();
        }
        catch (JsonProcessingException | ObjectNotFoundException e){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("/carparks/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCarPark(@PathParam("id") Long id) {
        try{
            CarPark carpark = (CarPark) cps.getCarPark(id);
            if (carpark == null) throw new ObjectNotFoundException();
            return Response
                    .status(Response.Status.OK.getStatusCode())
                    .entity(json.writeValueAsString(carpark))
                    .build();
        }
        catch (JsonProcessingException | ObjectNotFoundException e){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    @Path("/carparks")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createCarPark(String body) {
        try{
            CarPark cp = json.readValue(body, CarPark.class);

            CarPark carPark = (CarPark) cps.createCarPark(cp.getName(), cp.getAddress(), cp.getPricePerHour());
            if (carPark == null) throw new ObjectNotFoundException();
            return Response
                    .status(Response.Status.CREATED)
                    .entity(json.writeValueAsString(carPark))
                    .build();
        }
        catch (JsonProcessingException | ObjectNotFoundException e){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    //TODO: PUT carparks/{id}

    @DELETE
    @Path("/carparks/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteCarPark(@PathParam("id") Long id) {
        try{
            CarPark carPark = (CarPark) cps.deleteCarPark(id);
            if (carPark == null) throw new ObjectNotFoundException();
            return Response
                    .status(Response.Status.NO_CONTENT)
                    .entity(json.writeValueAsString(carPark))
                    .build();
        }
        catch (JsonProcessingException | ObjectNotFoundException e){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

}
