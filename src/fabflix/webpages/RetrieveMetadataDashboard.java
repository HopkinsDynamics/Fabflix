package fabflix.webpages;

import com.fasterxml.jackson.databind.ObjectMapper;
import fabflix.core.*;
import fabflix.models.MetadataModel;
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
import java.util.ArrayList;


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





@Path("metadataDashboard")
public class RetrieveMetadataDashboard  {

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDashBoard(String request) {
        System.out.println("====================================================================================================");
        System.out.println("RECEIVED REQUEST: getMetadataDashboardResponse(" + request + ")");
        System.out.println("----------------------------------------------------------------------------------------------------");

        ObjectMapper mapper = new ObjectMapper();
        String sessionID;
        String username;



        boolean completed=true;
        String metadataJsonObject="{\"database_rows\":[";
        // Verify user's session
        //   sessionID = mapper.readTree(request).get("sessionID").textValue();
        //    username  = mapper.readTree(request).get("username") .textValue();
        //    if (Core.brain.verifySession(sessionID, username)) {
        ArrayList<MetadataModel> md= new ArrayList<>();

            try {
            PreparedStatement ps;
            ResultSet rs;
            ResultSetMetaData rsmd;
            String query =Queries.METADATA_QUERY;
            ps = Core.getCon().prepareStatement(query);


                System.out.println("  Trying Query: " + ps.toString());
                rs = ps.executeQuery();


                rsmd = rs.getMetaData();
                System.out.println("  Finished Query!");
                System.out.println("  Retrieving Data from Result Set!");
                boolean hasMore=rs.next();
                System.out.println("hasMore:"+hasMore);

                        if (hasMore) {
                            do {
//                                metadataJsonObject+=
//                                        "{\"table\":"+rs.getString("TABLE_NAME")
//                                         +",\"column\":\""+rs.getString("COLUMN_NAME")
//                                         +"\", \"data_type\":\""+rs.getString("DATA_TYPE")+"\"}";

                                md.add(new MetadataModel(rs.getString("TABLE_NAME"),rs.getString("COLUMN_NAME"),rs.getString("DATA_TYPE")));

                                hasMore=rs.next();
                            //    System.out.println(metadataJsonObject);
                            //    System.out.println("hasMore:"+hasMore);
                                if(hasMore){
                            //        metadataJsonObject+=",";
                                }
                            } while (hasMore);

                        }


                  //  metadataJsonObject+="]}";
                        completed=true;
                System.out.println("finished iteration..built arraylist");

            } catch (SQLException e) {
                System.out.println("sql exception");
                e.printStackTrace();
                completed=false;

            }
//}

            if (completed == false) {
                System.out.println("sending failure code");
                return Response.status(400).build();
            }
            else {
                System.out.println("building array from arraylist");
                MetadataModel[] metaArray=new MetadataModel[md.size()];
                for(int i =0;i<metaArray.length;i++){
                    metaArray[i]=new MetadataModel(md.get(i));
                }
                System.out.println("sending completed response");
                return Response.status(200).entity( metaArray).build();
            }



    }
}
