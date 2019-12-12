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
import fabflix.core.AndroidFulltextSearch;
import fabflix.core.Core;
import fabflix.core.MovieList;
import fabflix.core.SearchParameters;
import fabflix.models.MovieContainerModel;
import fabflix.models.MovieModel;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

@Path("android-search")
public class AndroidSearchPage {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMovieList(String request) {
        System.out.println("====================================================================================================");
        System.out.println("RECEIVED REQUEST: androidSearchResponse(" + request + ")");
        System.out.println("----------------------------------------------------------------------------------------------------");

        ObjectMapper mapper = new ObjectMapper();
        String sessionID;
        String username;

        String title;

        int limit;
        int offset;

        MovieModel[] movieArray = null;
        MovieContainerModel movies = null;

        try {
            // Verify user's session
            sessionID = mapper.readTree(request).get("sessionID").textValue();
            username  = mapper.readTree(request).get("username") .textValue();
            if (true)//Core.brain.verifySession(sessionID, username))
            {
                // Get search parameters

                title  = mapper.readTree(request).get("title") .textValue();
                offset  = mapper.readTree(request).get("offset") .intValue();

                System.out.println("GOT PARAMETERS! Building SearchParameters object...");
              //  SearchParameters sp = new SearchParameters(bGenre, bTitle, sTitle, sYear, sDir, sStar, orderBy, order, Integer.toString(limit), Integer.toString(offset));
                AndroidFulltextSearch as = new AndroidFulltextSearch();

                movieArray = as.buildMovieList(title,offset);//MovieList.ml.buildMovieList(sp);
                if (movieArray == null)
                    return Response.status(400).build();
                else {
                    movies = new MovieContainerModel(movieArray);
                    return Response.status(200).entity(movies).build();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
