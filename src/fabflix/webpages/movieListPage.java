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
import fabflix.core.MovieList;
import fabflix.core.SearchParameters;
import fabflix.core.StatTimerLogger;
import fabflix.models.MovieContainerModel;
import fabflix.models.MovieModel;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

@Path("movieList")
public class movieListPage {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMovieList(String request) {
        System.out.println("====================================================================================================");
        System.out.println("RECEIVED REQUEST: getMovieListResponse(" + request + ")");
        System.out.println("----------------------------------------------------------------------------------------------------");

        StatTimerLogger stl = new StatTimerLogger("TS_timings.txt");
        stl.startTiming();

        ObjectMapper mapper = new ObjectMapper();
        String sessionID;
        String username;
        String bGenre;
        String bTitle;
        String sTitle;
        String sYear;
        String sDir;
        String sStar;
        String orderBy;
        String order;
        int limit;
        int offset;

        MovieModel[] movieArray = null;
        MovieContainerModel movies = null;

        try {
            // Verify user's session
            sessionID = mapper.readTree(request).get("sessionID").textValue();
            username  = mapper.readTree(request).get("username") .textValue();
            if (Core.brain.verifySession(sessionID, username)) {
                // Get search parameters
                bGenre  = mapper.readTree(request).get("bGenre") .textValue();
                bTitle  = mapper.readTree(request).get("bTitle") .textValue();
                sTitle  = mapper.readTree(request).get("sTitle") .textValue();
                sDir    = mapper.readTree(request).get("sDir")   .textValue();
                sStar   = mapper.readTree(request).get("sStar")  .textValue();
                orderBy = mapper.readTree(request).get("orderBy").textValue();
                order   = mapper.readTree(request).get("order")  .textValue();
                sYear   = mapper.readTree(request).get("sYear")  .textValue();
                limit   = mapper.readTree(request).get("limit")  .intValue();
                offset  = mapper.readTree(request).get("offset") .intValue();
                if (orderBy.equals("Title"))  orderBy = "m.title";
                if (orderBy.equals("Rating")) orderBy = "r.rating";
                if (order.equals("Ascending"))  order = "ASC";
                if (order.equals("Descending")) order = "DESC";
//                System.out.println("  bGenre  = " + bGenre);
//                System.out.println("  bTitle  = " + bTitle);
//                System.out.println("  sTitle  = " + sTitle);
//                System.out.println("  sDir    = " + sDir);
//                System.out.println("  sStar   = " + sStar);
//                System.out.println("  orderBy = " + orderBy);
//                System.out.println("  order   = " + order);
//                System.out.println("  sYear   = " + sYear);
//                System.out.println("  limit   = " + limit);
//                System.out.println("  offset  = " + offset);

                System.out.println("GOT PARAMETERS! Building SearchParameters object...");
                SearchParameters sp = new SearchParameters(bGenre, bTitle, sTitle, sYear, sDir, sStar, orderBy, order, Integer.toString(limit), Integer.toString(offset));

                movieArray = MovieList.ml.buildMovieList(sp);
                if (movieArray == null) {
                    stl.stopTiming();
                    return Response.status(400).build();
                }
                else {
                    movies = new MovieContainerModel(movieArray);
                    stl.stopTiming();
                    return Response.status(200).entity(movies).build();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        stl.stopTiming();
        return null;
    }
}
