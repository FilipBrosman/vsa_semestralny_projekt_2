package sk.stuba.fei.uim.vsa.pr2.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import sk.stuba.fei.uim.vsa.pr2.entity.Reservation;
import sk.stuba.fei.uim.vsa.pr2.entity.User;
import sk.stuba.fei.uim.vsa.pr2.web.request.ReservationRequest;
import sk.stuba.fei.uim.vsa.pr2.web.response.ObjectNotFoundException;

import java.util.List;

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

    @GET
    @Path("/reservations/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getReservationsById(@PathParam("id") Long id){
        try{
            Reservation res = (Reservation) cps.getReservationById(id);
            if (res == null) return Response
                    .status(Response.Status.NOT_FOUND)
                    .build();
            return Response
                    .status(Response.Status.OK)
                    .entity(json.writeValueAsString(res))
                    .build();
        }
        catch (JsonProcessingException e){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @POST
    @Path("/reservations")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createReservation(String body) {
        try{
            ReservationRequest crr = json.readValue(body, ReservationRequest.class);
            Reservation res = (Reservation) cps.createReservation(crr.getSpot().getId(),crr.getCar().getId());

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
