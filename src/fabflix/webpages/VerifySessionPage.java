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

import fabflix.core.Core;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

@Path("verifySession")
public class VerifySessionPage {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response verifySession(String request) {
        System.out.println("==================================================");
        System.out.println("RECEIVED REQUEST: verifySession(" + request + ")");
        System.out.println("==================================================");

        ObjectMapper mapper = new ObjectMapper();
        String sessionValid = "";
        boolean clearedHot = false;

        try {
            System.out.println("  Reading inputs from request...");
            String sessionID = mapper.readTree(request).get("sessionID").textValue();
            String username = mapper.readTree(request).get("username").textValue();

            if (!(sessionID == null)) {
                System.out.println("  INPUT WAS NOT NULL! Verifying Session...");
                clearedHot = Core.brain.verifySession(sessionID, username);
                if (clearedHot) {
                    System.out.println("  SESSION IS VALID! Returning TRUE");
                    sessionValid = "{\"sessionValid\":\"TRUE\"}";
                    return Response.status(200).entity(sessionValid).build();
                } else {
                    System.out.println("  SESSION IS INVALID! Returning FALSE");
                    sessionValid = "{\"sessionValid\":\"FALSE\"}";
                    return Response.status(403).entity(sessionValid).build();
                }
            } else {
                System.out.println("  INPUT IS NULL!");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return Response.status(400).build();
        } catch (NullPointerException e) {
            return Response.status(403).build();
        }
        System.out.println("RETURNING RESPONSE");
        System.out.println("==================================================");
        return Response.status(500).build();
    }
}
