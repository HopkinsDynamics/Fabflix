package fabflix.core;

import fabflix.models.MovieModel;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

public class AndroidFulltextSearch {
    public static AndroidFulltextSearch  ml = new AndroidFulltextSearch ();

   public AndroidFulltextSearch() { }

    public MovieModel[] buildMovieList(String title, int offset) {
        System.out.println("====================================================================================================");
        System.out.println("AndroidFulltextSearch()");
        System.out.println("----------------------------------------------------------------------------------------------------");
        ArrayList<Movie> movies;
        MovieModel[] movieArray;
        String query =  "SELECT m.id, m.title, m.year, m.director," +
                " m.backdrop_path, m.budget, m.overview, m.poster_path, m.revenue, r.rating, " +
                "r.numVotes, GROUP_CONCAT(DISTINCT g.id SEPARATOR ';') AS genreIDs, GROUP_CONCAT(DISTINCT g.name SEPARATOR ';') " +
                "AS genres, GROUP_CONCAT(DISTINCT CONCAT(s.id,'=', s.name) SEPARATOR ';') " +
                "AS stars FROM movies m, ratings r, genres_in_movies gm, genres g, stars s, stars_in_movies sm" +
                " WHERE MATCH (m.title) AGAINST( ? IN BOOLEAN MODE) AND  g.id = gm.genreId AND gm.movieId = m.id AND r.movieId= m.id AND" +
                " s.id = sm.starId AND sm.movieId = m.id    GROUP BY m.id  ORDER BY m.title  ASC  LIMIT 10 OFFSET ?;";

        PreparedStatement ps;
        ResultSet rs;
        ResultSetMetaData rsmd;
        String tokenList= buildMatchingString(title);


        try {
            System.out.println("  QUERY SHELL IS: " + query);
            ps = Core.getCon().prepareStatement(query);
            // set parameters for ps
            System.out.println("  Setting parameters for query...");

//                System.out.println("    Setting parameter: " + sp.getParams()[indexOfParam] + " to index " + indexOfQuery);
                ps.setString(1,tokenList);
                ps.setInt(2,offset);



            System.out.println("  Trying Query: " + ps.toString());
            rs = ps.executeQuery();
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
    private String buildMatchingString(String s){
        String ms="";
        String[] splitTokens = s.split(" ");
        for(int i = 0;i<splitTokens.length;++i){

            if(i==0){
                ms+= "+";
            }
            else{
                ms+= " +";
            }
            ms+=splitTokens[i] + "*";
        }
        System.out.println("ms= "+ms);
        return ms;
    }
}
