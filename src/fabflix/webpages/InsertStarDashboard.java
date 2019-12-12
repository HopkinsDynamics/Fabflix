package fabflix.webpages;

import com.fasterxml.jackson.databind.ObjectMapper;
import fabflix.core.*;
import fabflix.models.MovieContainerModel;
import fabflix.models.MovieModel;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;


/*
 * if or switch statement
 * insert star ->if has birthyear or not and make null in prepared statement
 * report success
 * add movie
 * report success or failure based on if movie exists
 *
 * show metadata of database-> basically print out table names and column names and types
 *
 *
 *
 *
 *
 *
 * */

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





@Path("insertStarDashboard")
public class InsertStarDashboard  {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDashBoard(String request) {
        System.out.println("====================================================================================================");
        System.out.println("RECEIVED REQUEST: getStarDashboardResponse(" + request + ")");
        System.out.println("----------------------------------------------------------------------------------------------------");

        ObjectMapper mapper = new ObjectMapper();
        String sessionID;
        String username;
        String response=" ";
        boolean success=false;
        int birthyear;

        String starname;
        boolean completed=true;



        try {
            System.out.println("entering try");
            // Verify user's session
            //   sessionID = mapper.readTree(request).get("sessionID").textValue();
            //    username  = mapper.readTree(request).get("username") .textValue();
            //    if (Core.brain.verifySession(sessionID, username)) {
            // Get parameters



                starname=mapper.readTree(request).get("starname").textValue();
            System.out.println("  starname   = " + starname);
            birthyear   = mapper.readTree(request).get("birthyear").asInt();
            System.out.println("  birthyear   = " + birthyear);


            System.out.println("GOT PARAMETERS! Building SearchParameters object...");
            //  SearchParameters sp = new SearchParameters(bGenre, bTitle, sTitle, sYear, sDir, sStar, orderBy, order, Integer.toString(limit), Integer.toString(offset));
            PreparedStatement ps;
            ResultSet rs;
            ResultSetMetaData rsmd;
            String query ;
            try {

                    query = Queries.INSERT_STAR_QUERY;
                    System.out.println("  QUERY SHELL IS: " + query);
                    ps = Core.getWriteCon().prepareStatement(query);
                    ps.setString(1,starname);
                    ps.setInt(2,birthyear);
                    System.out.println("  Trying Query: " + ps.toString());
                    rs = ps.executeQuery();
                    rs.next();
                    success=rs.getBoolean(1);
                    rsmd = rs.getMetaData();
                if(success){
                    response="{\"success\":\"1\"}";
                }else{
                    response= "{\"success\":\"0\"}";
                }
                    System.out.println("  Finished Query!");


            } catch (SQLException e) {
                e.printStackTrace();
                completed=false;

            }


            if (completed == false)
                return Response.status(400).build();
            else {

                return Response.status(200).entity(response).build();
            }
            //   }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
