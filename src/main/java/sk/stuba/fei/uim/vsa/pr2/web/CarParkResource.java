package sk.stuba.fei.uim.vsa.pr2.web;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import sk.stuba.fei.uim.vsa.pr2.CarParkService;
import sk.stuba.fei.uim.vsa.pr2.entity.CarPark;
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
            if (carparks == null) throw new ObjectNotFoundException();
            return Response
                    .status(Response.Status.OK)
                    .entity(json.writeValueAsString(carparks))
                    .build();
        }
        catch (JsonProcessingException | ObjectNotFoundException e){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
