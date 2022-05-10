package sk.stuba.fei.uim.vsa.pr2.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import sk.stuba.fei.uim.vsa.pr2.entity.CarPark;
import sk.stuba.fei.uim.vsa.pr2.entity.CarParkFloor;
import sk.stuba.fei.uim.vsa.pr2.web.request.CarParkFloorRequest;
import sk.stuba.fei.uim.vsa.pr2.web.request.CarParkRequest;
import sk.stuba.fei.uim.vsa.pr2.web.request.ParkingSpotRequest;
import sk.stuba.fei.uim.vsa.pr2.web.response.ObjectNotFoundException;

import java.util.List;

@Path("/")
public class CarParkFloorResource extends AbstractResource {

    @GET
    @Path("/carparks/{id}/floors")
    @Produces(MediaType.APPLICATION_JSON)
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
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @POST
    @Path("/carparks/{id}/floors")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createCarParkFloors(@PathParam("id") Long id, String body){
        try{
            CarPark carPark = (CarPark)cps.getCarPark(id);
            if (carPark == null)
                return Response
                        .status(Response.Status.NOT_FOUND)
                        .build();

            CarParkFloorRequest cpfr = json.readValue(body, CarParkFloorRequest.class);
            CarParkFloor cpf = (CarParkFloor) cps.createCarParkFloor(carPark.getId(), cpfr.getIdentifier());

            for (ParkingSpotRequest spot: cpfr.getSpots()) {
               if (cps.createParkingSpot(cpfr.getCarPark(), cpfr.getIdentifier(), spot.getIdentifier()) == null)
                   return Response.status(Response.Status.BAD_REQUEST).build();
            }

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

    @DELETE
    @Path("/carparks/{id}/floors/{identifier}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteCarParkFloors(@PathParam("id") Long id, @PathParam("identifier") String identifier){
            CarParkFloor cpf = (CarParkFloor) cps.getCarParkFloorByCarParkId(id, identifier);
            if (cpf == null) return Response.status(Response.Status.NOT_FOUND).build();

            cpf = (CarParkFloor) cps.deleteCarParkFloor(cpf.getId());
            if (cpf == null) return Response.status(Response.Status.NOT_FOUND).build();

            return Response
                    .status(Response.Status.NO_CONTENT)
                    .build();
    }
}
