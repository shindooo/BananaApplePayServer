package jp.gr.java_conf.shindooo.bananaapplepay.api;

import java.util.HashMap;
import java.util.Map;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import jp.pay.Payjp;
import jp.pay.exception.APIConnectionException;
import jp.pay.exception.APIException;
import jp.pay.exception.AuthenticationException;
import jp.pay.exception.CardException;
import jp.pay.exception.InvalidRequestException;
import jp.pay.model.Charge;

@Path("orders")
public class OrderService {

    @Path("/")
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    public Response create(@FormParam("token") String token, @FormParam("amount") Integer amount, @FormParam("email") String email) {
        // ※サンプルのためアプリ独自の処理は省略し、PAY.JPとの連携のみ記述する
        
        Payjp.apiKey = "PAY.JPの設定画面で確認した秘密鍵";
        Map<String, Object> chargeParams = new HashMap<>();
        chargeParams.put("card", token);
        chargeParams.put("amount", amount);
        chargeParams.put("currency", "jpy");

        try {
            Charge.create(chargeParams);
            return Response.ok("Complete!.", MediaType.TEXT_PLAIN).build();

        } catch (InvalidRequestException | CardException ex) {
            throw new WebApplicationException(Response.status(Status.BAD_REQUEST).entity(ex.getMessage()).build());

        } catch (AuthenticationException | APIConnectionException | APIException ex) {
            throw new WebApplicationException(Response.status(Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build());
        }
    }
}
