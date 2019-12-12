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

import fabflix.models.SessionModel;
import fabflix.core.Core;
import fabflix.token.Session;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

@Path("login")
public class LoginPage {
    private ObjectMapper mapper = new ObjectMapper();
    private String username;
    private String password;
    private Session s = null;
    private SessionModel sm = null;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response loginResponse(String request) {
        System.out.println("==================================================");
        System.out.println("RECEIVED REQUEST: loginResponse(" + request + ")");
        System.out.println("==================================================");
    boolean notBot=false;
        // Verify reCAPTCHA

        try {
            String gRecaptchaResponse =mapper.readTree(request).get("g-recaptcha-response").textValue();
            System.out.println("gRecaptchaResponse=" + gRecaptchaResponse);
            try {
                ReCaptchaVerify.verify(gRecaptchaResponse);
                notBot=true;

            } catch (Exception e) {
                System.out.println(e);

            }
            username = mapper.readTree(request).get("username").textValue();
            password = mapper.readTree(request).get("password").textValue();
            System.out.println("  username = " + username);
            System.out.println("  password = " + password);
            s = Core.brain.login(username, password, notBot);
            if (s != null) {
                System.out.println("  NEW SESSION: " + s);
                System.out.println("    sessionId = " + s.getSessionID());
                System.out.println("    username = " + s.getUsername());
                System.out.println("    timeCreated = " + s.getTimeCreated());
            } else {
                System.out.println("  **** ERROR! NULL SESSION!");
                return Response.status(403).build();
            }
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
            return Response.status(400).build();
        }
        sm = new SessionModel(s);
        System.out.println("  RETURNING NEW SESSION!");
        return Response.status(200).entity(sm).build();
    }
}
