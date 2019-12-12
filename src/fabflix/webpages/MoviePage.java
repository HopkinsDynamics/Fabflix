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
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

@Path("movie")
public class MoviePage {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response singleMovieResponse(String request) {
        System.out.println("==================================================");
        System.out.println("RECEIVED REQUEST: singleMovieResponse(" + request + ")");
        System.out.println("==================================================");
        ObjectMapper mapper = new ObjectMapper();
        String movieID = "";
        MovieModel mm;

        try {
            movieID = mapper.readTree(request).get("movieID").textValue();
            // Set information to return
            MovieInfoOptions options = new MovieInfoOptions();
            // Get ArrayList of Movies
            mm = Core.brain.getSingleMovieData(movieID, options);
            System.out.println("GOT MovieModel mm");
        } catch (IOException e) {
            e.printStackTrace();
            return Response.status(400).build();
        }
        System.out.println("RETURNING MOVIE TO MOVIE.HTML!");
        return Response.status(200).entity(mm).build();
    }
}
