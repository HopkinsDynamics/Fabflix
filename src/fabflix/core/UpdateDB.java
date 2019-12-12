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

package fabflix.core;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class UpdateDB extends Thread {
    private String urlFront = "https://api.themoviedb.org/3/movie/";
    private String urlTail = "?api_key=dd6e0ba702e309cf6da963d385e62ac6";
    private Movie[] ourMovies = getAllMovieIDs();
    ObjectMapper mapper = new ObjectMapper();
    Timer timer = new Timer();
    int index = 0;

    UpdateDB() {

    }

    @Override
    public void run() {
        System.out.println("run()");

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                if (index < ourMovies.length)
                    updateDatabase();
                else
                    timer.cancel();
            }
        };
        timer.scheduleAtFixedRate(task, 100, 30000);
    }

    private void updateDatabase() {
        System.out.println("updateDatabase()");
        for ( ; index < ourMovies.length; ++index) {
            System.out.println("  INDEX = " + index);
            System.out.println("  NEW ID = " + ourMovies[index].getId());
            // get the movie string
            String movieString = makeUpdateRequest(urlFront + ourMovies[index].getId() + urlTail);
            // get the data from the JSON string
            try {
                String[] movieData = new String[6];
                movieData[0] = ourMovies[index].getId();
                movieData[1] = mapper.readTree(movieString).get("backdrop_path").textValue();
                movieData[2] = mapper.readTree(movieString).get("budget").textValue();
                movieData[3] = mapper.readTree(movieString).get("overview").textValue();
                movieData[4] = mapper.readTree(movieString).get("poster_path").textValue();
                movieData[5] = mapper.readTree(movieString).get("revenue").textValue();
                System.out.println("  id            = " + movieData[0]);
                System.out.println("  backdrop_path = " + movieData[1]);
                System.out.println("  budget        = " + movieData[2]);
                System.out.println("  overview      = " + movieData[3]);
                System.out.println("  poster_path   = " + movieData[4]);
                System.out.println("  revenue       = " + movieData[5]);
                Core.brain.insertNewDataIntoDB(movieData);
            } catch (FileNotFoundException e) {
                System.out.println("--------------------------------------------------");
                System.out.println(" **WARNING! MOVIE DATA NOT FOUND!");
                System.out.println("--------------------------------------------------");
            } catch (IOException e) {
                System.out.println("--------------------------------------------------");
//                e.printStackTrace();
                System.out.println("--------------------------------------------------");
            }
            try {
                sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private String makeUpdateRequest(String request) {
        System.out.println("makeUpdateRequest(" + request + ")");
        URL obj;
        StringBuffer response;
        String responseString = "";

        try {
            obj = new URL(request);
            HttpURLConnection con = (HttpURLConnection)obj.openConnection();
            con.setRequestMethod("GET");

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            responseString = response.toString();
            System.out.println(responseString);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseString;
    }

    private Movie[] getAllMovieIDs() {
        System.out.println("getAllMovieIDs()");
        ArrayList<Movie> movies = new ArrayList<>();
        Movie[] movieArray = null;
        PreparedStatement ps;
        ResultSet rs;
        ResultSetMetaData rsmd;

        try {
            ps = Core.getCon().prepareStatement(Queries.GET_ALL_MOVIE_IDs);
            System.out.println("  Trying Query: " + ps.toString());
            rs = ps.executeQuery();
            rsmd = rs.getMetaData();
            System.out.println("  Finished Query.");
            // Set options for info to extract
            MovieInfoOptions options = new MovieInfoOptions(true, false, false, false, false, false, false, false, false, false, false, false, false);
            // get ArrayList of Movies
            movies = Core.brain.buildListFromResultSet(rs, options);
            // Convert ArrayList to Array
            movieArray = Core.brain.buildMovieArrayFromList(movies);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return movieArray;
    }
}
