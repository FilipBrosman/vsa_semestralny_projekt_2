package sk.stuba.fei.uim.vsa.pr2.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import sk.stuba.fei.uim.vsa.pr2.CarParkService;
import sk.stuba.fei.uim.vsa.pr2.entity.CarPark;
import sk.stuba.fei.uim.vsa.pr2.entity.CarParkFloor;
import sk.stuba.fei.uim.vsa.pr2.web.request.CarParkFloorRequest;
import sk.stuba.fei.uim.vsa.pr2.web.response.ObjectNotFoundException;

import java.lang.reflect.Array;
import java.util.List;

@Path("/")
public class CarParkFloorResource {
    public static final String EMPTY_RESPONSE = "{}";
    private final ObjectMapper json = new ObjectMapper();
    private final CarParkService cps = new CarParkService();

    @GET
    @Path("/carparks/{id}/floors")
    public Response getCarParkFloors(@PathParam("id") Long id){
        try{
            if ((cps.getCarPark(id)) == null)
                return Response
                    .status(Response.Status.NOT_FOUND)
                    .entity(json.writeValueAsString("[]"))
                    .build();

            List<Object> carparksfloors = cps.getCarParkFloors(id);
            return Response
                .status(Response.Status.OK)
                .entity(json.writeValueAsString(carparksfloors))
                .build();
        }
        catch (JsonProcessingException e){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    @Path("/carparks/{id}/floors")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createCarParkFloors(@PathParam("id") Long id, String body){
        try{
            CarPark cp = (CarPark)cps.getCarPark(id);
            if (cp == null)
                return Response
                        .status(Response.Status.NOT_FOUND)
                        .entity(json.writeValueAsString("[]"))
                        .build();

            CarParkFloorRequest cpfr = json.readValue(body, CarParkFloorRequest.class);
            CarParkFloor cpf = (CarParkFloor) cps.createCarParkFloor(cp.getId(), cpfr.getFloorIdentifier());
            if (cpf == null)
                throw new ObjectNotFoundException();
            return Response
                    .status(Response.Status.CREATED)
                    .entity(json.writeValueAsString(cpf))
                    .build();
        }
        catch (JsonProcessingException | ObjectNotFoundException e){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
}
