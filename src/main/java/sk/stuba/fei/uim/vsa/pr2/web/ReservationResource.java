package sk.stuba.fei.uim.vsa.pr2.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import sk.stuba.fei.uim.vsa.pr2.entity.ParkingSpot;
import sk.stuba.fei.uim.vsa.pr2.entity.Reservation;
import sk.stuba.fei.uim.vsa.pr2.entity.User;
import sk.stuba.fei.uim.vsa.pr2.web.request.CreateReservationRequest;
import sk.stuba.fei.uim.vsa.pr2.web.request.GetReservationsRequest;
import sk.stuba.fei.uim.vsa.pr2.web.request.ParkingSpotRequest;
import sk.stuba.fei.uim.vsa.pr2.web.response.ObjectNotFoundException;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Path("/")
public class ReservationResource extends AbstractResource {

    @GET
    @Path("/reservations")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMyReservations( @QueryParam("user") Long user ){
        try{
            if (user == null) throw new ObjectNotFoundException();
            User u = (User) cps.getUser(user);
            if (u == null) throw new ObjectNotFoundException();

            List<Object> res = cps.getMyReservations(user);
            if (res.isEmpty()) return Response
                    .status(Response.Status.NO_CONTENT)
                    .build();
            return Response
                    .status(Response.Status.OK)
                    .entity(json.writeValueAsString(res))
                    .build();
        }
        catch (JsonProcessingException e){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        catch (ObjectNotFoundException ex){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    //TODO: GET /reservations/{id}

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
    //TODO: PUT /reservations/{id}
    @POST
    @Path("/reservations/{id}/end")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response endReservation(@PathParam("id") Long id, String body) {
        try{
            Reservation res = (Reservation) cps.endReservation(id);
            if (res == null) throw new ObjectNotFoundException();
            return Response
                    .status(Response.Status.OK)
                    .entity(json.writeValueAsString(res))
                    .build();
        }
        catch (JsonProcessingException | ObjectNotFoundException e){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
}
