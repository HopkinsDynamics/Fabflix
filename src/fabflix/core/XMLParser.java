package fabflix.core;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ConcurrentHashMap;

public class XMLParser extends Thread {
    private ArrayList<XMLMovie> xmlMovies;
    ArrayList<Movie> moviesList;
    ArrayList<Movie> moviesToInsert = new ArrayList<>();
    Movie[] movies;

    public XMLParser() { }

    public void run() {
        parseMovieXML();
    }

    private void parseMovieXML() {
        System.out.println("parseMovieXML()");
        XMLMovieParser mp = new XMLMovieParser();
        xmlMovies = mp.parseMovieFile();
        cleanXMLListOfMovies();
   //   printXMLMovies(xmlMovies);
        try {
            moviesList = getExistingMovies();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        movies = Core.brain.buildMovieArrayFromList(moviesList);
        compareAllMovies();

       printMovies(moviesToInsert);
      BatchInsert bi = new BatchInsert();
       bi.insertBatch( moviesToInsert);
    }

    private void cleanXMLListOfMovies() {
        System.out.println("cleanXMLListOfMovie reached.......");
        for (int i = 0; i < xmlMovies.size(); ++i) {
            XMLMovie xmlm = xmlMovies.get(i);

            if (xmlm.getDirector().contains("Unknown")) {
            //    System.out.println("DIRECTOR NAME CONTAINS  UNKNOWN");
                xmlMovies.remove(i);
                continue;
            }
            if (xmlm.getDirector().contains("UnYear")) {
         //       System.out.println("DIRECTOR NAME CONTAINS  UNYEAR");
                xmlMovies.remove(i);
            }
        }
    }

    private void printXMLMovies(ArrayList<XMLMovie> list) {
        System.out.println("printXMLMovies()");
        for (int i = 0; i < list.size(); ++i) {
            System.out.println("Movie #" + i + " = " + list.get(i).toString());
        }
    }

    private void printMovies(ArrayList<Movie> list) {
        System.out.println("printMovies()");
        for (int i = 0; i < list.size(); ++i) {
            System.out.println("Movie #" + i + " = " + list.get(i).toString());
        }
    }

    private ArrayList<Movie> getExistingMovies() {
        PreparedStatement ps;
        ResultSet rs;
        ResultSetMetaData rsmd;
        System.out.println("getExistingMovies() reached.......");

        try {
            ps = Core.getCon().prepareStatement(Queries.GET_ALL_MOVIES_FOR_XML);
            rs = ps.executeQuery();
            rsmd = rs.getMetaData();
            // Set information to return
            MovieInfoOptions options = new MovieInfoOptions(true, true, true, true, false, false, false, false, false, false, false, false, false);
            // Get ArrayList of movies
            return Core.brain.buildListFromResultSet(rs, options);
        } catch (SQLException | NullPointerException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void compareAllMovies() {
       // System.out.println("compareAllMovies() reached.......");
        // For each XMLMovie
        for (int i = 0; i < xmlMovies.size(); ++i) {
            XMLMovie xmlm = xmlMovies.get(i);
            // Find the index where the title begins
            int index = binarySearchOnMovies(xmlm.getTitle());
            compareSubset(xmlm, index);
        }
    }

    private void compareSubset(XMLMovie xmlm, int index) {

        int first = index;
        int last = index;
        if(index!=-1) {
         //   for (; first >= 0 && xmlm.getTitle().compareToIgnoreCase(movies[index].getTitle()) == 0; first--) ;
           // for (; (last < movies.length) && xmlm.getTitle().compareToIgnoreCase(movies[index].getTitle()) == 0; last++)
                ;

         //   for (int i = first; i <= last; ++i) {
                if (compareMovie(xmlm, movies[index])) {
                    System.out.println("inside of compareSubset() if statement");
                    return;
                }
         //   }
        }
            Movie m = new Movie(xmlm);
            //  System.out.println("end of compareSubset() inserting "+m.toString());
            moviesToInsert.add(m);
        }

    private boolean compareMovie(XMLMovie xmlm, Movie m) {
       System.out.println("compareMovie() reached.......");
        return (xmlm.getYear() == m.getYear()) ;
               // && (m.getDirector().contains(xmlm.getDirector())

    }



    private int binarySearchOnMovies(String title) {
     //   System.out.println("----------------------------------------------------------------------------------------------------");
     //   System.out.println("binarySearchOnMovies(" + title + ")");
        int front = 0;
        int back = movies.length - 1;
        int middle = (front + back) / 2;
   //     System.out.println("    movies[" + middle + "] = " + movies[middle].getTitle());
        int result = movies[middle].getTitle().compareToIgnoreCase(title);
  //     System.out.println("    " + movies[middle].getTitle() + " vs " + title + " = " + result);

        while (result != 0 && front <= back) {
       //     System.out.println("    movies[" + middle + "] = " + movies[middle].getTitle());
            result = movies[middle].getTitle().compareToIgnoreCase(title);
     //      System.out.println("    " + movies[middle].getTitle() + " vs " + title + " = " + result);
            if (result < 0) {
// middle.title > title
            //   System.out.println("        result > 0 --> increasing front");
                front = middle + 1;
            } else if(result>0){
// middle.title < title
            //   System.out.println("        result < 0 --> decreasing back");
                back = middle - 1;
            }else if(result==0){
                System.out.println(" MATCH FOUND RETURNING ****************************************/");
             //   System.out.println(" MATCH FOUND RETURNING ****************************************/");
             //   System.out.println(" MATCH FOUND RETURNING **********************8*****************/");
                return middle;}
            middle = (front + back) / 2;
        }
        if ( front >= back ) {
// NO MATCH FOUND
        //    System.out.println("NO MATCH FOUND RETURNING -1");
            return -1;
        }
        else {
// MATCH WAS FOUND
            return middle;
        }
    }

}