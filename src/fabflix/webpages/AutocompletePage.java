package fabflix.webpages;

import com.fasterxml.jackson.databind.ObjectMapper;
import fabflix.core.Autocomplete;
import fabflix.models.MovieContainerModel;
import fabflix.models.MovieModel;

import javax.ws.rs.*;
//import javax.ws.rs.Consumes;
//import javax.ws.rs.POST;
//import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;

@Path("autocomplete")
public class AutocompletePage {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response autocompleteResponse(String request) {
        System.out.println("==================================================");
        System.out.println("RECEIVED REQUEST: autocompleteResponse(" + request + ")");
        System.out.println("==================================================");
        ObjectMapper mapper = new ObjectMapper();
        String searchString = "";
        MovieModel[] movies;
        MovieContainerModel mmovies;

        try {
            searchString = mapper.readTree(request).get("searchString").textValue();
            System.out.println("searchString is " + searchString);
            movies = Autocomplete.ac.buildSearchResults(searchString);
            if (movies == null) {
                return Response.status(400).build();
            } else {
                mmovies = new MovieContainerModel(movies);
                return Response.status(200).entity(mmovies).build();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return Response.status(400).build();
        }
    }
}
