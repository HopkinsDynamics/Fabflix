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
import fabflix.models.StarModel;
import fabflix.token.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.jasypt.util.password.StrongPasswordEncryptor;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class Core {
    private static final int ID_INDEX = 1;
    private static final int TITLE_INDEX = 2;
    private static final int YEAR_INDEX = 3;
    private static final int DIRECTOR_INDEX = 4;
    private static final int BACKDROP_PATH_INDEX = 5;
    private static final int BUDGET_INDEX = 6;
    private static final int OVERVIEW_INDEX = 7;
    private static final int POSTER_PATH_INDEX = 8;
    private static final int REVENUE_INDEX = 9;
    private static final int RATING_INDEX = 10;
    private static final int NUMVOTES_INDEX = 11;
    private static final int GENRE_ID_INDEX = 12;
    private static final int GENRE_NAME_INDEX = 13;
    private static final int STAR_ID_INDEX = 14;
    //    private static final int    STAR_NAME_INDEX = 15;
    private static final int NUM_POPULAR_MOVIES_FOR_INDEX_PAGE = 8;
    private static final int SQUERY_STAR_ID_INDEX = 1;
    private static final int SQUERY_STAR_NAME_INDEX = 2;
    private static final int SQUERY_STAR_BYEAR_INDEX = 3;
    private static final String URL = "jdbc:mysql://localhost:3306/moviedb?autoReconnect=true&useSSL=false";
    private static final String USERNAME = "mytestuser";
    private static final String PASSWORD = "mypassword";

    private static Connection con;
    private static Connection writeCon;

    public static Core brain = new Core();
    private final ConcurrentHashMap<String, Session> currentSessions = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<String, ShoppingCart> shoppingCarts = new ConcurrentHashMap<>();
    private final String XML_MOVIEID_PREFIX = "xml";
    private int xmlMovieCount = 0;

    /* -------------------- CONSTRUCTORS AND INIT FUNCTIONS -------------------- */
    private Core() {
        init();

        /*
        // Parse xml
        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        XMLParser xmlp = new XMLParser();
                        xmlp.run();
                    }
                }, 5000);
        */
    }

    private static void init() {
        System.out.println("==================================================");
        System.out.println("init()");
        System.out.println("==================================================");
        System.out.println("**** INITIALIZING BRAIN ****");
//        String myDriver = "com.mysql.cj.jdbc.Driver";

        try {
//            Class.forName(myDriver);
//            con = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            Context initCtx = new InitialContext();
            Context envCtx = (Context) initCtx.lookup("java:comp/env");
            if (envCtx == null)
                System.out.println("ENVCTX IS NULL");

            DataSource ds = (DataSource) envCtx.lookup("jdbc/moviedb");
            con = ds.getConnection();

            DataSource wds = (DataSource) envCtx.lookup("jdbc/wmoviedb");
            writeCon = ds.getConnection();


            if (con == null || writeCon == null)
                System.out.println("con is null");
        } catch ( NamingException | SQLException e) {
            e.printStackTrace();
        }
    }

    /* -------------------- LOGIN AND SESSION FUNCTIONS -------------------- */
    public Session login(String username, String password, boolean notBot) {
        System.out.println("==================================================");
        System.out.println("login(" + username + ", " + password + ")");
        System.out.println("==================================================");
        System.out.println("    Getting hashed passowrd...");
        String returnedPassword = query_for_password(username);
        System.out.println("    GOT IT!");
//        printSessions();
        boolean success = true;
        System.out.println("    PASSWORD COMPARISON RESULT INITIALIZED TO: " + success);
        System.out.println("    COMPARING PASSWORDS... ");
        success = new StrongPasswordEncryptor().checkPassword(password, returnedPassword);
        System.out.println("    DONE!");
        System.out.println("    PASSWORD COMPARISON RESULT: " + success);
        //  if (password.equals(returnedPassword)) {
        if (success) {
            System.out.println("    PASSWORD IS VALID!");
            if (notBot) {
                System.out.println("    NOT A BOT = TRUE");
                System.out.println("    CREATING NEW SESSION");
                return createNewSession(username);
            } else {
                System.out.println("    reCaptcha not-a-Bot verification failed! ");
                return null;
            }
        } else {
            System.out.println("     PASSWORD INVALID! NO SESSION CREATED!");
            return null;
        }
    }

    public Session employeeLogin(String username, String password, boolean notBot) {
        System.out.println("==================================================");
        System.out.println("employeeLogin(" + username + ", " + password + ")");
        System.out.println("==================================================");
        String returnedPassword = query_for_employeePassword(username);
//        printSessions();
        boolean success = false;
        success = new StrongPasswordEncryptor().checkPassword(password, returnedPassword);

        if (success) {
            if (notBot) {
                return createNewSession(username);
            } else {
                System.out.println("reCaptcha not-a-Bot verification failed! ");
                return null;
            }
        } else {
            System.out.println("**** PASSWORD INVALID! NO SESSION CREATED!");
            return null;
        }
    }

    private String query_for_password(String username) {
        System.out.println("query_for_password(" + username + ")");
        ResultSetMetaData rsmd;
        try {
            PreparedStatement ps = con.prepareStatement(Queries.LOGIN_QUERY);
            ps.setString(1, username);
            System.out.println("TRYING QUERY: " + ps.toString());
            ResultSet rs = ps.executeQuery();
            System.out.println("FINISHED QUERY!");
            rsmd = rs.getMetaData();

            if (rs.next()) {
                return rs.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String query_for_employeePassword(String username) {
        ResultSetMetaData rsmd;
        try {
            PreparedStatement ps = con.prepareStatement(Queries.EMPLOYEE_LOGIN_QUERY);
            ps.setString(1, username);
            ResultSet rs = ps.executeQuery();
            rsmd = rs.getMetaData();

            if (rs.next()) {
                return rs.getString(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Session createNewSession(String username) {
        System.out.println("createNewSession(" + username + ")");
        Session s = Session.newSession(username);
        if (currentSessions.containsKey(username)) {
//            System.out.println("  currentSessions contains username. Replacing...");
            currentSessions.replace(username, s);
        } else {
//            System.out.println("  currentSessions DOES NOT contain username. Adding...");
            currentSessions.put(username, s);
        }
        return s;
    }

    public boolean verifySession(String sessionID, String username) {
        System.out.println("verifySession(" + sessionID + ", " + username + ")");
//        printSessions();
        boolean hasKey = currentSessions.containsKey(username);
//        System.out.println("  hasKey = " + hasKey);
        boolean isValid = currentSessions.get(username).isValid();

//        System.out.println("  sessionIsValid = " + hasKey);
        return (hasKey && isValid);
//        return currentSessions.containsKey(username) && currentSessions.get(username).isValid();
    }

    public boolean employeeEmailExists(String email) {
        ResultSetMetaData rsmd;
        try {
            PreparedStatement ps = con.prepareStatement(Queries.EMPLOYEE_EXISTS_QUERY);
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            rsmd = rs.getMetaData();

            if (rs.next()) {
                // return rs.getBoolean("email");
                //    System.out.println(rs.getString("email"));
                //    if(!rs.getString(email).equals("null")){
                return true;
                //  }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean verifyEmployeeSession(String sessionID, String username) {
        System.out.println("verifySession(" + sessionID + ", " + username + ")");
//        printSessions();
        boolean hasKey = currentSessions.containsKey(username);
//        System.out.println("  hasKey = " + hasKey);
        boolean isValid = currentSessions.get(username).isValid();
        boolean isEmployee = employeeEmailExists(username);
//        System.out.println("  sessionIsValid = " + hasKey);
        return (hasKey && isValid && isEmployee);
//        return currentSessions.containsKey(username) && currentSessions.get(username).isValid();
    }

    public void printSessions() {
        System.out.println("PRINTING CURRENT SESSIONS!");
        for (Map.Entry<String, Session> entry : currentSessions.entrySet()) {
            String key = entry.getKey();
            currentSessions.get(key).print();
        }
        System.out.println("FINISHED PRINTING CURRENT SESSIONS!");
    }

    /* -------------------- UPDATE DATABASE FUNCTIONS -------------------- */
    public void insertNewDataIntoDB(String[] movieData) {
        System.out.println("insertNewDataIntoDB()");
        try {
            PreparedStatement ps = con.prepareStatement(Queries.INSERT_UPDATED_DATA_FROM_TMDB);
            ps.setString(1, movieData[1]);
            ps.setString(2, movieData[2]);
            ps.setString(3, movieData[3]);
            ps.setString(4, movieData[4]);
            ps.setString(5, movieData[5]);
            ps.setString(6, movieData[0]);
            System.out.println("Trying query: " + ps.toString());
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("--------------------------------------------------");
            System.out.println("  ERROR IN INSERT QUERY!");
            e.printStackTrace();
            System.out.println("--------------------------------------------------");
        }

    }

    /* -------------------- QUERY FUNCTIONS -------------------- */
    public MovieModel getSingleMovieData(String movieID, MovieInfoOptions options) {
        System.out.println("getSingleMovieData(" + movieID + ")");
        PreparedStatement ps;
        ResultSet rs;
        ResultSetMetaData rsmd;
        Movie m;

        try {
            ps = Core.getCon().prepareStatement(Queries.GET_SINGLE_MOVIE_DATA_ALL);
            ps.setString(1, movieID);
            System.out.println("  Trying Query: " + ps.toString());
            rs = ps.executeQuery();
            rsmd = rs.getMetaData();
            System.out.println("  Finished Query.");
            if (rs.next()) {
                m = getMovieFromResultSet(rs, options);
                return new MovieModel(m);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public StarModel getSingleStar(String starID) {
        System.out.println("getSingleStar(" + starID + ")");
        PreparedStatement ps;
        ResultSet rs;
        ResultSetMetaData rsmd;
        Star s;
        StarModel sm;

        try {
            ps = Core.getCon().prepareStatement(Queries.GET_SINGLE_STAR_INFO);
            ps.setString(1, starID);
            System.out.println("Trying Query: " + ps.toString());
            rs = ps.executeQuery();
            rsmd = rs.getMetaData();
            System.out.println("Query Finished!");
            if (rs.next()) {
                s = getStarFromResultSet(rs);
                if (s != null) {
                    System.out.println("Star is not null!");
                    s.setStarsIn(getAllMoviesWithStar(starID));
                    sm = new StarModel(s);
                    sm.setStarsIn(buildMovieModelArrayFromMovieArray(s.getStarsIn()));
                    System.out.println("RETURNING StarModel to StarPage.java");
                    return sm;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Movie[] getAllMoviesWithStar(String starID) {
        System.out.println("getAllMoviesWithStar(" + starID + ")");
        PreparedStatement ps;
        ResultSet rs;
        ResultSetMetaData rsmd;
        ArrayList<Movie> movieList;

        try {
            ps = Core.getCon().prepareStatement(Queries.GET_ALL_MOVIES_WITH_STAR);
            ps.setString(1, starID);
            System.out.println("Trying Query: " + ps.toString());
            rs = ps.executeQuery();
            rsmd = rs.getMetaData();
            System.out.println("Query Finished!");

            if (rs.next()) {
                System.out.println("Building list from RS");
                movieList = buildListFromResultSet(rs, new MovieInfoOptions());
                System.out.println("Returning Movie[] of all movies with star");
                return buildMovieArrayFromList(movieList);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("RETURNING NULL!");
        return null;
    }


    /* -------------------- SHOPPING CART FUNCTIONS -------------------- */
    public ShoppingCart addMovieToShoppingCart(String username, String movieID, int Qty) {
        PreparedStatement ps;
        ResultSet rs;
        ResultSetMetaData rsmd;
        ShoppingCart sc = getShoppingCart(username);

        try {
            ps = getCon().prepareStatement(Queries.GET_SINGLE_MOVIE_DATA_ALL);
            ps.setString(1, movieID);
            rs = ps.executeQuery();
            rsmd = rs.getMetaData();

            if (rs.next()) {
                Movie m = getMovieFromResultSet(rs, new MovieInfoOptions());
                for (int i = 0; i < Qty; i++)
                    sc.addMovie(m);
            }
            return sc;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sc;
    }

    public ShoppingCart removeMovieFromCart(String username, String movieID, int qty) {
        ShoppingCart sc = getShoppingCart(username);
        for (int i = 0; i < qty; ++i) {
            sc.removeMovie(movieID);
        }
        return sc;
    }

    /* -------------------- HELPER FUNCTIONS -------------------- */
    private Star getStarFromResultSet(ResultSet rs) {
//        System.out.println("getStarFromResultSet()");
        try {
            String id = rs.getString(SQUERY_STAR_ID_INDEX);
            String name = rs.getString(SQUERY_STAR_NAME_INDEX);
            int year = rs.getInt(SQUERY_STAR_BYEAR_INDEX);
            return new Star(id, name, year);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Accepts a ResultSet as an argument and returns a new Movie containing the information
    // from the current cursor position of the ResultSet.
    public Movie getMovieFromResultSet(ResultSet rs, MovieInfoOptions o) {
//        System.out.println("getMovieFromResultSet()");
        try {
//            System.out.println("----------------------------------------------------------------------------------------------------");
            Movie m = new Movie(rs.getString("id"));
//            System.out.println("m.id = " + m.getId());
            if (o.getTitle)
                m.setTitle(rs.getString("title"));
//            System.out.println("m.title = " + m.getTitle());
            if (o.getYear)
                m.setYear(rs.getInt("year"));

//            System.out.println("m.year = " + m.getYear());
            if (o.getDirector)
                m.setDirector(rs.getString("director"));
//            System.out.println("m.director = " + m.getDirector());
            if (o.getBackdropPath)
                m.setBackdropPath(rs.getString("backdrop_path"));
//            System.out.println("m.backdropPath = " + m.getBackdropPath());
            if (o.getBudget)
                m.setBudget(rs.getInt("budget"));
//            System.out.println("m.budget = " + m.getBudget());
            if (o.getOverview)
                m.setOverview(rs.getString("overview"));
//            System.out.println("m.overview = " + m.getOverview());
            if (o.getPosterPath)
                m.setPosterPath(rs.getString("poster_path"));
//            System.out.println("m.posterPath = " + m.getPosterPath());
//            if (o.getRevenue)
//                m.setRevenue(rs.getInt(rs.getInt("revenue)));
//            System.out.println("m.revenue = " + m.getRevenue());
            if (o.getRating)
                m.setRating(rs.getFloat("rating"));
//            System.out.println("m.rating = " + m.getRating());
            if (o.getNumVotes)
                m.setNumVotes(rs.getInt("numVotes"));
//            System.out.println("m.numVotes = " + m.getNumVotes());
            if (o.getStars)
                m.addGenres(rs.getString("genreIDs"), rs.getString("genres"));
            if (o.getGenres)
//                m.addStars(rs.getString(STAR_ID_INDEX), rs.getString(STAR_NAME_INDEX));
                m.addStars(rs.getString("stars"));
//            System.out.println("----------------------------------------------------------------------------------------------------");
//            System.out.println("RETURNING MOVIE M!");
            return m;
        } catch (SQLException e) {
            e.printStackTrace();
        }
//        System.out.println("RETURNING NULL!");
        return null;
    }

    // Accepts a ResultSet and boolean as arguments. Creates an ArrayList of Movies from
    // movie information in ResultSet. The brief parameter decides how much information
    // will be pulled from each entry in the ResultSet. A value of true will pull only
    // the movieID. A value of false will pull all information.
    public ArrayList<Movie> buildListFromResultSet(ResultSet rs, MovieInfoOptions options) {
//        System.out.println("buildContainerFromResults()");
        ArrayList<Movie> movies = new ArrayList<>();
        try {
            if (rs.next()) {
                do {
                    Movie m = getMovieFromResultSet(rs, options);
                    if (m != null) {
                        movies.add(m);
                    }
                } while (rs.next());
                return movies;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return movies;
    }

    public MovieModel[] buildMovieModelArrayFromList(ArrayList<Movie> movies) {
        MovieModel[] mMovies = new MovieModel[movies.size()];
        for (int i = 0; i < mMovies.length; ++i)
            mMovies[i] = new MovieModel(movies.get(i));
        return mMovies;
    }

    public MovieModel[] buildMovieModelArrayFromMovieArray(Movie[] movies) {
        MovieModel[] mm = new MovieModel[movies.length];
        for (int i = 0; i < mm.length; ++i)
            mm[i] = new MovieModel(movies[i]);
        return mm;
    }

    public Movie[] buildMovieArrayFromList(ArrayList<Movie> movies) {
//        System.out.println("buildMovieArrayFromList()");
        Movie[] movieArray = new Movie[movies.size()];
        for (int i = 0; i < movieArray.length; ++i)
            movieArray[i] = movies.get(i);
        return movieArray;
    }

    public MovieModel[] buildShoppingCartModelMovieArray(ShoppingCart sc) {
//        System.out.println("buildShoppingCartModelMovieArray(" + sc + ")");
        ArrayList<Movie> list = sc.getMovies();
        MovieModel[] mMovies = new MovieModel[list.size()];
        for (int i = 0; i < mMovies.length; ++i) {
            mMovies[i] = new MovieModel(list.get(i));
        }
        return mMovies;
    }

    /* -------------------- GETTERS AND SETTERS -------------------- */
    public static Connection getCon() {
        return con;
    }

    public static Connection getWriteCon() { return writeCon; }

    public ShoppingCart getShoppingCart(String username) {
//        System.out.println("getShoppingCart(" + username + ")");
        // check if username has a shopping cart associated with them
        if (shoppingCarts.get(username) == null) {
//            System.out.println("NO SHOPPING CART EXISTS! MAKING ONE NOW");
            shoppingCarts.put(username, new ShoppingCart());
        }
//        System.out.println("RETURNING SHOPPING CART");
        return shoppingCarts.get(username);
    }

    public void removeShoppingCart(String username) {
//        System.out.println("removeShoppingCart(" + username + ")");
        if (shoppingCarts.get(username) != null) {
            shoppingCarts.remove(username);
        }
    }

    public String getNewXMLMovieID() {
        String id = XML_MOVIEID_PREFIX;
        xmlMovieCount++;
        int digits = (int) (Math.log10(xmlMovieCount) + 1);
        for (int i = 0; i < (6 - digits); ++i) {
            id += "0";
        }
        id += Integer.toString(xmlMovieCount);
        return id;
    }
}
