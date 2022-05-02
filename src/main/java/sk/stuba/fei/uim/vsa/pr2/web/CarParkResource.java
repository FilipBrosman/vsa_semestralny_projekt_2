package sk.stuba.fei.uim.vsa.pr2.web;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import sk.stuba.fei.uim.vsa.pr2.CarParkService;

import java.util.List;

@Path("/carpark")
public class CarParkResource {
    public static final String EMPTY_RESPONSE = "{}";
    private final ObjectMapper json = new ObjectMapper();

    private final CarParkService cps = new CarParkService();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String getAllCarParks() {
        List<Object> carparks = cps.getCarParks();

        if (carparks.isEmpty())
            return EMPTY_RESPONSE;

        try{
            return json.writeValueAsString(carparks);
        }
        catch (JsonProcessingException e){
            try{
                return json.writeValueAsString(e.getMessage());
            }
            catch (JsonProcessingException ex){
                // ignore
                return EMPTY_RESPONSE;
            }
        }
    }
}
