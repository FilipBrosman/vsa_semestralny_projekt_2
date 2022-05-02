package sk.stuba.fei.uim.vsa.pr2.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import sk.stuba.fei.uim.vsa.pr2.CarParkService;
import sk.stuba.fei.uim.vsa.pr2.entity.CarPark;
import sk.stuba.fei.uim.vsa.pr2.web.response.ObjectNotFoundException;

import java.util.List;

@Path("/carparks")
public class CarParkFloorResource {
    public static final String EMPTY_RESPONSE = "{}";
    private final ObjectMapper json = new ObjectMapper();
    private final CarParkService cps = new CarParkService();

    @GET
    @Path("/{id}/floors")
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

}
