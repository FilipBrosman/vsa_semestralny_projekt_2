package sk.stuba.fei.uim.vsa.pr2.web;


import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import sk.stuba.fei.uim.vsa.pr2.entity.CarPark;
import sk.stuba.fei.uim.vsa.pr2.entity.CarParkFloor;
import sk.stuba.fei.uim.vsa.pr2.entity.ParkingSpot;
import sk.stuba.fei.uim.vsa.pr2.web.request.CarParkFloorRequest;
import sk.stuba.fei.uim.vsa.pr2.web.request.CarParkRequest;
import sk.stuba.fei.uim.vsa.pr2.web.request.ParkingSpotRequest;
import sk.stuba.fei.uim.vsa.pr2.web.response.ObjectNotFoundException;

import java.util.List;

@Path("/")
public class CarParkResource extends AbstractResource {

    @GET
    @Path("/carparks")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllCarParks() {
        try{
            List<Object> carparks = cps.getCarParks();
            if (carparks.isEmpty()) throw new ObjectNotFoundException();
            return Response
                    .status(Response.Status.OK)
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
            CarParkRequest cp = json.readValue(body, CarParkRequest.class);

            CarPark carPark = (CarPark) cps.createCarPark(
                    cp.getName(),
                    cp.getAddress(),
                    cp.getPrices()
            );
            if (carPark == null)
                return Response.status(Response.Status.CONFLICT).build();

            for (CarParkFloorRequest floor:cp.getFloors()){
                CarParkFloor cpf = (CarParkFloor) cps.createCarParkFloor(carPark.getId(), floor.getIdentifier());
                if (cpf == null)
                    return Response.status(Response.Status.CONFLICT).build();
                for (ParkingSpotRequest spot: floor.getSpots()) {
                    if (cps.createParkingSpot(cpf.getCarPark().getId(), cpf.getIdentifier(), spot.getIdentifier()) == null)
                        return Response.status(Response.Status.CONFLICT).build();
                }
            }

            return Response
                    .status(Response.Status.CREATED)
                    .entity(json.writeValueAsString(carPark))
                    .build();
        }
        catch (JsonProcessingException e){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    //TODO: PUT carparks/{id}

    @DELETE
    @Path("/carparks/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteCarPark(@PathParam("id") Long id) {
            CarPark carPark = (CarPark) cps.getCarPark(id);
            if (carPark == null) return Response.status(Response.Status.NOT_FOUND).build();
            cps.deleteCarPark(id);
            return Response
                    .status(Response.Status.NO_CONTENT)
                    .build();
    }

}
