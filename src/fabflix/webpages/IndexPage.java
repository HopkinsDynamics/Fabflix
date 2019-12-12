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
import fabflix.core.Core;
import fabflix.core.Index;
import fabflix.models.MovieContainerModel;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

@Path("index")
public class IndexPage {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response topRatedMoviesResponse(String request) {
//        System.out.println("==================================================");
//        System.out.println("RECEIVED REQUEST: topRatedMoviesResponse(" + request + ")");
//        System.out.println("==================================================");
        ObjectMapper mapper = new ObjectMapper();
        String genre = "";
        MovieContainerModel container = null;

        try {
            genre = mapper.readTree(request).get("genre").textValue();
            // Get top rated movies results
            container = new MovieContainerModel(Index.index.getTopRatedMovies(genre));
//            System.out.println("  RECEIVED CONTAINER OF MOVIES!");
        } catch (IOException e) {
            e.printStackTrace();
        }
//        System.out.println("  RETURNING RESPONSE!");
        return Response.status(200).entity(container).build();
    }
}
