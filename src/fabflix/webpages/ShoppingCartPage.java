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
import fabflix.core.Movie;
import fabflix.core.ShoppingCart;
import fabflix.models.ShoppingCartModel;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.ArrayList;

@Path("shoppingCart")
public class ShoppingCartPage {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response shoppingCartResponse(String request) {
        System.out.println("shoppingCartResponse(" + request + ")");
        ObjectMapper mapper = new ObjectMapper();
        String sessionID;
        String username;
        String movieID;
        String action;
        int qty;
        ShoppingCart cart = null;
        ShoppingCartModel cartModel;

        try {
            // Verify the session
            sessionID = mapper.readTree(request).get("sessionID").textValue();
            username = mapper.readTree(request).get("username").textValue();
            if (Core.brain.verifySession(sessionID, username)) {
                movieID = mapper.readTree(request).get("movieID").textValue();
                action  = mapper.readTree(request).get("action") .textValue();
                qty     = mapper.readTree(request).get("qty")    .intValue();
                // Perform request
                switch (action) {
                    case "add":
                        // Add movie to shopping cart
                        cart = Core.brain.addMovieToShoppingCart(username, movieID, qty);
                        break;
                    case "remove":
                        // Remove movie from shopping cart
                        cart = Core.brain.removeMovieFromCart(username, movieID, qty);
                        break;
                    case "get":
                        cart = Core.brain.getShoppingCart(username);
                }
                // Convert cart to model
                cartModel = new ShoppingCartModel(Core.brain.buildMovieModelArrayFromList(cart.getMovies()));
                return Response.status(200).entity(cartModel).build();
            } else {
                System.out.println("ERROR 401");
                return Response.status(401).build();
            }
        } catch (IOException | NullPointerException e) {
            System.out.println("IOEXCEPTION OR NullPointerException");
            e.printStackTrace();
            return Response.status(400).build();
        }
    }
}
