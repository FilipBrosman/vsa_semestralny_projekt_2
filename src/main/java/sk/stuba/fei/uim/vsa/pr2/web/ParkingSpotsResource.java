package sk.stuba.fei.uim.vsa.pr2.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import sk.stuba.fei.uim.vsa.pr2.entity.CarPark;
import sk.stuba.fei.uim.vsa.pr2.entity.ParkingSpot;
import sk.stuba.fei.uim.vsa.pr2.web.request.ParkingSpotRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Path("/")
public class ParkingSpotsResource extends AbstractResource {

    @GET
    @Path("/carparks/{id}/spots")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getParkingSpots(@PathParam("id") Long id, @QueryParam("free") Boolean par){
        try{
            CarPark cp = (CarPark) cps.getCarPark(id);
            Map<String,List<Object>> ps = new HashMap<>();

            if (par == null) {
                ps = cps.getParkingSpots(id);
                return Response
                        .status(Response.Status.OK)
                        .entity(json.writeValueAsString(ps))
                        .build();
            }

            if (par)
                ps =  cps.getAvailableParkingSpots(cp.getName());
            else
                ps =  cps.getOccupiedParkingSpots(cp.getName());

            if (ps.isEmpty()) return Response
                    .status(Response.Status.NO_CONTENT)
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

    //TODO: PUT /carparks/{id}/floors/{identifier}/spots

    @POST
    @Path("/carparks/{id}/floors/{identifier}/spots")
    @Produces(MediaType.APPLICATION_JSON)
    public Response createParkingSpot(@PathParam("id") Long id, @PathParam("identifier") String identifier, String body){
        try{
            ParkingSpotRequest psr = json.readValue(body, ParkingSpotRequest.class);

            ParkingSpot ps = (ParkingSpot) cps.createParkingSpot(id, identifier, psr.getIdentifier());
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
