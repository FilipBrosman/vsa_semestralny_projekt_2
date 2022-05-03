package sk.stuba.fei.uim.vsa.pr2.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import sk.stuba.fei.uim.vsa.pr2.CarParkService;
import sk.stuba.fei.uim.vsa.pr2.entity.ParkingSpot;
import sk.stuba.fei.uim.vsa.pr2.web.request.ParkingSpotRequest;
import sk.stuba.fei.uim.vsa.pr2.web.response.ObjectNotFoundException;
import java.util.List;

@Path("/")
public class ParkingSpotsResource extends AbstractResource {
    @GET
    @Path("/carparks/{id}/floors/{identifier}/spots")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllParkingSpots(@PathParam("id") Long id, @PathParam("identifier") String identifier){
        try{
            List<Object> ps =  cps.getParkingSpots(id, identifier);
            if (ps.isEmpty()) return Response
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

    @GET
    @Path("/parkingspots/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getParkingSpot(@PathParam("id") Long id){
        try{
            ParkingSpot ps = (ParkingSpot) cps.getParkingSpot(id);
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

    @POST
    @Path("/carparks/{id}/floors/{identifier}/spots")
    @Produces(MediaType.APPLICATION_JSON)
    public Response createParkingSpot(@PathParam("id") Long id, @PathParam("identifier") String identifier, String body){
        try{
            ParkingSpotRequest psr = json.readValue(body, ParkingSpotRequest.class);

            ParkingSpot ps = (ParkingSpot) cps.createParkingSpot(id, identifier, psr.getSpotLabel());
            if (ps == null) return Response
                    .status(Response.Status.NOT_FOUND)
                    .build();
            return Response
                    .status(Response.Status.CREATED)
                    .entity(json.writeValueAsString(ps))
                    .build();
        }
        catch (JsonProcessingException e){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @DELETE
    @Path("/parkingspots/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteParkingSpot(@PathParam("id") Long id){
        try{
            ParkingSpot ps = (ParkingSpot) cps.deleteParkingSpot(id);
            if (ps == null) return Response
                    .status(Response.Status.NOT_FOUND)
                    .build();
            return Response
                    .status(Response.Status.NO_CONTENT)
                    .entity(json.writeValueAsString(ps))
                    .build();
        }
        catch (JsonProcessingException e){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
}
