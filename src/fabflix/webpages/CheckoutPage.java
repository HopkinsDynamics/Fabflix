/* ====================================================================================================
   DATE:      06 APR 2018
   AUTHORS:   Kelly McKeown   David Hopkins
   UCInetIDs: KPMCKEOW        HOPKINSD
   ID#'s:     42806243        70404050
   CLASS:     CS 122B
   PROJECT:   #2
   ----------------------------------------------------------------------------------------------------
   DESCRIPTION:
   ====================================================================================================
 */

package fabflix.webpages;

import com.fasterxml.jackson.databind.ObjectMapper;
import fabflix.core.Checkout;
import fabflix.core.Core;
import fabflix.core.CreditCard;
import fabflix.core.Customer;
import fabflix.models.CheckoutResponseModel;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

@Path("checkout")
public class CheckoutPage {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response checkoutResponse(String request) {
        System.out.println("====================================================================================================");
        System.out.println("checkoutResponse(" + request + ")");
        System.out.println("----------------------------------------------------------------------------------------------------");

        ObjectMapper mapper = new ObjectMapper();
        String sessionID;
        String username;
        Customer c;
        CreditCard cc;
        String transactionResult = "";
        CheckoutResponseModel crm;

        try {
            // Verify session
            sessionID = mapper.readTree(request).get("sessionID").textValue();
            username = mapper.readTree(request).get("username").textValue();
            if (Core.brain.verifySession(sessionID, username)) {
                // Build customer data
                String firstName = mapper.readTree(request).get("formData").get("firstname").textValue();
                String lastName  = mapper.readTree(request).get("formData").get("lastname") .textValue();
                String address   = mapper.readTree(request).get("formData").get("address")  .textValue();
                String ccnum     = mapper.readTree(request).get("formData").get("ccnumber") .textValue();
                cc = new CreditCard(ccnum, firstName, lastName);
                c = new Customer(firstName, lastName, address, username, cc);
                boolean checkoutResult = Checkout.co.checkout(c, c.getCc());

                if (checkoutResult) {
                    transactionResult = "success";
                } else if (!checkoutResult) {
                    transactionResult = "failure";
                }
                crm = new CheckoutResponseModel(transactionResult);
                return Response.status(200).entity(crm).build();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Response.status(403).build();
    }
}
