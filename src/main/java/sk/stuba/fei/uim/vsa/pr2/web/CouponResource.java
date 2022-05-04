package sk.stuba.fei.uim.vsa.pr2.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import sk.stuba.fei.uim.vsa.pr2.entity.Coupon;
import sk.stuba.fei.uim.vsa.pr2.entity.User;
import sk.stuba.fei.uim.vsa.pr2.web.request.CouponRequest;
import sk.stuba.fei.uim.vsa.pr2.web.request.UserRequest;
import sk.stuba.fei.uim.vsa.pr2.web.response.ObjectNotFoundException;

import java.util.List;

@Path("/")
public class CouponResource extends AbstractResource {

    @GET
    @Path("/coupons")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserCoupon( @QueryParam("user") Long user ){
        try{
            if (user == null) throw new ObjectNotFoundException();

            List<Object> coupons = cps.getCoupons(user);
            if (coupons.isEmpty()) return Response
                    .status(Response.Status.NO_CONTENT)
                    .build();
            return Response
                    .status(Response.Status.OK)
                    .entity(json.writeValueAsString(coupons))
                    .build();
        }
        catch (JsonProcessingException e){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        catch (ObjectNotFoundException ex){
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    @Path("/coupons")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createCoupon(String body){
        try{
            CouponRequest cr = json.readValue(body, CouponRequest.class);

            Coupon c = (Coupon) cps.createDiscountCoupon(cr.getName(),cr.getDiscount());
            if (c == null) return Response.status(Response.Status.BAD_REQUEST).build();

            return Response
                    .status(Response.Status.CREATED)
                    .entity(json.writeValueAsString(c))
                    .build();
        }
        catch (JsonProcessingException e){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }
}
