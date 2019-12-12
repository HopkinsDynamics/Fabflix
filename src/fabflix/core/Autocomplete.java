package fabflix.core;

import fabflix.models.MovieModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

public final class Autocomplete {
    public static Autocomplete ac = new Autocomplete();

    private Autocomplete() {

    }

    public MovieModel[] buildSearchResults(String searchString) {
        System.out.println("buildSearchResults(" + searchString + ")");
        String ss = buildMatchString(searchString);
        String query = "SELECT id, title FROM movies WHERE MATCH (title) AGAINST (\'" + ss + "\' IN BOOLEAN MODE) LIMIT 10;";
        System.out.println("QUERY = " + query);

        ArrayList<Movie> movies;
        MovieModel[] movieArray;
        PreparedStatement ps;
        ResultSet rs;
        ResultSetMetaData rsmd;

         try {
             ps = Core.getCon().prepareStatement(query);
             System.out.println("  Trying Query: " + ps.toString());
             rs = ps.executeQuery();
             rsmd = rs.getMetaData();
             System.out.println("  Finished Query!");
             MovieInfoOptions mo = new MovieInfoOptions(true, true, false, false, false, false, false, false, false, false, false, false, false);
             movies = Core.brain.buildListFromResultSet(rs, mo);
             movieArray = Core.brain.buildMovieModelArrayFromList(movies);
             return movieArray;
         } catch (SQLException e) {
             e.printStackTrace();
             return null;
         }
    }

    private String buildMatchString(String s) {
        String ms = "";
        String[] splitTokens = s.split(" ");
        for (int i = 0; i < splitTokens.length; ++i) {
            ms += " +";
            ms += splitTokens[i];
        }
        System.out.println("ms = " + ms);
        return ms;
    }
}
