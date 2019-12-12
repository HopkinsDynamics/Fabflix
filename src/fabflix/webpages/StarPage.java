/* ================================================================================================
   DATE:     08 FEB 2018
   AUTHOR:   Kelly McKeown
   ID#:      42806243
   UCInetID: KPMCKEOW
   CLASS:    CS122B W18
   PROJECT:  #2
   ------------------------------------------------------------------------------------------------
   DESCRIPTION:
   ================================================================================================
 */

package fabflix.webpages;

import com.fasterxml.jackson.databind.ObjectMapper;
import fabflix.core.Core;
import fabflix.core.MovieInfoOptions;
import fabflix.models.MovieModel;
import fabflix.models.StarModel;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

@Path("star")
public class StarPage {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response singleStarResponse(String request) {
        System.out.println("==================================================");
        System.out.println("RECEIVED REQUEST: singleStarResponse(" + request + ")");
        System.out.println("--------------------------------------------------");
        ObjectMapper mapper = new ObjectMapper();
        String starID = "";
        StarModel star;

        try {
            starID = mapper.readTree(request).get("starID").textValue();
            star = Core.brain.getSingleStar(starID);
            if (star != null) {
                System.out.println("  RETURNING STAR!");
                return Response.status(200).entity(star).build();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Response.status(400).build();
    }
}
