package fabflix.webpages;

import com.fasterxml.jackson.databind.ObjectMapper;
import fabflix.core.Core;
import fabflix.models.SessionModel;
import fabflix.token.Session;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;


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


@Path("employeeLogin")
public class EmployeeLoginPage {
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
        System.out.println("RECEIVED REQUEST: EmployeeLoginResponse(" + request + ")");
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
            s = Core.brain.employeeLogin(username, password, notBot);
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
        System.out.println("  RETURNING NEW EMPLOYEE SESSION!");
        return Response.status(200).entity(sm).build();
    }
}
