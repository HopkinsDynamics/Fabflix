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

import fabflix.models.MovieModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

public class Index {
    public static Index index = new Index();

    public MovieModel[] getTopRatedMovies(String genre) {
//        System.out.println("getTopRatedMovies(" + genre + ")");
        ArrayList<Movie> movies = new ArrayList<>();
        MovieModel[] mmArray = null;
        PreparedStatement ps;
        ResultSet rs;
        ResultSetMetaData rsmd;

        try {
            ps = Core.getCon().prepareStatement(Queries.GET_TOP_RATED_MOVIES_NOT_NULL_POSTER_ALT);
            ps.setString(1, genre);
//            System.out.println("Trying Query: " + ps.toString());
            rs = ps.executeQuery();
            rsmd = rs.getMetaData();
//            System.out.println("Finished query.");
            // Set information to return
            MovieInfoOptions options = new MovieInfoOptions();
            // Get ArrayList of Movies
            movies = Core.brain.buildListFromResultSet(rs, options);
            // Convert list to MovieModel array
            mmArray = Core.brain.buildMovieModelArrayFromList(movies);

        } catch (SQLException e) {
            e.printStackTrace();
        }
//        System.out.println("Returning movies!");
        return mmArray;
    }
}
