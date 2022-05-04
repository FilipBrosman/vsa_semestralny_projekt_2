package sk.stuba.fei.uim.vsa.pr2.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import sk.stuba.fei.uim.vsa.pr2.entity.ParkingSpot;
import sk.stuba.fei.uim.vsa.pr2.entity.Reservation;
import sk.stuba.fei.uim.vsa.pr2.web.request.CreateReservationRequest;
import sk.stuba.fei.uim.vsa.pr2.web.request.ParkingSpotRequest;
import sk.stuba.fei.uim.vsa.pr2.web.response.ObjectNotFoundException;

import java.util.List;
import java.util.Objects;

@Path("/")
public class ReservationResource extends AbstractResource {

    @POST
    @Path("/reservations")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createReservation(String body) {
        try{
            CreateReservationRequest crr = json.readValue(body, CreateReservationRequest.class);
            Reservation res = (Reservation) cps.createReservation(crr.getParkingSpotId(),crr.getCarId());

            if (res == null) throw new ObjectNotFoundException();

            return Response
                    .status(Response.Status.CREATED)
                    .entity(json.writeValueAsString(res))
                    .build();
        }
        catch (JsonProcessingException | ObjectNotFoundException e){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @POST
    @Path("/reservations/{id}/end")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response endReservation(@PathParam("id") Long id, String body) {
        try{
            if (!Objects.equals(body, "")) throw new ObjectNotFoundException();

            Reservation res = (Reservation) cps.endReservation(id);
            if (res == null) throw new ObjectNotFoundException();
            return Response
                    .status(Response.Status.CREATED)
                    .entity(json.writeValueAsString(res))
                    .build();
        }
        catch (JsonProcessingException | ObjectNotFoundException e){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
}
