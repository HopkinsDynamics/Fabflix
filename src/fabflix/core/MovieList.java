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

import static java.lang.Thread.sleep;

public final class MovieList {
    public static MovieList ml = new MovieList();

    private MovieList() { }

    public MovieModel[] buildMovieList(SearchParameters sp) {
        System.out.println("====================================================================================================");
        System.out.println("buildMovieList()");
        System.out.println("----------------------------------------------------------------------------------------------------");
        ArrayList<Movie> movies;
        MovieModel[] movieArray;
        String query = QueryBuilder.qb.buildQuery(sp);
        PreparedStatement ps;
        ResultSet rs;
        ResultSetMetaData rsmd;
        StatTimerLogger stl = new StatTimerLogger("TJ_timings.txt");

        try {
            System.out.println("  QUERY SHELL IS: " + query);
            ps = Core.getCon().prepareStatement(query);
            // set parameters for ps
            System.out.println("  Setting parameters for query...");
            for (int indexOfParam = 0, indexOfQuery = 1; indexOfParam < sp.getParams().length - 2; ++indexOfParam, ++indexOfQuery) {
//                System.out.println("    Setting parameter: " + sp.getParams()[indexOfParam] + " to index " + indexOfQuery);
                ps.setString(indexOfQuery, sp.getParams()[indexOfParam]);
            }
            ps.setInt(sp.getParams().length - 1, sp.getLimitInt());
            ps.setInt(sp.getParams().length, sp.getOffsetInt());
//            System.out.println("  DONE!");
//            sleep(3000);
            System.out.println("  Trying Query: " + ps.toString());
            stl.setQuery(ps.toString());
            stl.startTiming();
            rs = ps.executeQuery();
            stl.stopTiming();
            rsmd = rs.getMetaData();
            System.out.println("  Finished Query!");
            movies = Core.brain.buildListFromResultSet(rs, new MovieInfoOptions());
            movieArray = Core.brain.buildMovieModelArrayFromList(movies);
            return movieArray;
        } catch (SQLException e) {
            e.printStackTrace();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
        }
        return null;
    }
}
