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

public class Queries {

    /** NEW QUERIES **/
    // add movie query
    public static final  String ADD_MOVIE_QUERY=  " call moviedb.add_movie(?, ?, ?, ?, ?);  ";
    // insert star query
    public static final String INSERT_STAR_QUERY = "call moviedb.insert_star(?,?);";
    // display movie db metadata
    public static final String METADATA_QUERY= "SELECT TABLE_NAME,COLUMN_NAME, DATA_TYPE FROM INFORMATION_SCHEMA.COLUMNS WHERE table_name LIKE '%' AND table_schema = 'moviedb' AND column_name LIKE '%';";
    // login query
    public static final String LOGIN_QUERY = "SELECT password FROM customers WHERE email = ?";
   // employee login query
    public static final String EMPLOYEE_LOGIN_QUERY = "SELECT password FROM employees WHERE email = ?";
    // employee exists query
    public static final String EMPLOYEE_EXISTS_QUERY = "SELECT * FROM employees WHERE email = ?";
    // customer exists query
    public static final String CUSTOMER_EXISTS_QUERY = "SELECT * FROM customers WHERE email = ?";
// get all movies for xml query
    public static final String GET_ALL_MOVIES_FOR_XML="SELECT m.id, m.title, m.director, m.year FROM movies m ORDER BY m.title;";
 // get all movie ID's
    public static final String GET_ALL_MOVIE_IDs = "SELECT m.id FROM movies m;";
    // get top-rated movies for a specified genre ALTERNATE
    public static final String GET_TOP_RATED_MOVIES_NOT_NULL_POSTER_ALT = "SELECT m.id, m.title, m.year, m.director, m.backdrop_path, m.budget, m.overview, m.poster_path, m.revenue, r.rating, r.numVotes, GROUP_CONCAT(DISTINCT g.id SEPARATOR ';') AS genreIDs, GROUP_CONCAT(DISTINCT g.name SEPARATOR ';') AS genres, GROUP_CONCAT(DISTINCT CONCAT(s.id,'=', s.name) SEPARATOR ';') AS stars FROM movies m, ratings r, genres_in_movies gm, genres g, stars s, stars_in_movies sm WHERE g.id = gm.genreId AND gm.movieId = m.id AND r.movieId= m.id AND s.id = sm.starId AND sm.movieId = m.id AND g.name = ? AND m.poster_path IS NOT NULL GROUP BY m.id ORDER BY r.rating DESC LIMIT 8;";
    // get all data for single movie
    public static final String GET_SINGLE_MOVIE_DATA_ALL = "SELECT m.id, m.title, m.year, m.director, m.backdrop_path, m.budget, m.overview, m.poster_path, m.revenue, r.rating, r.numVotes, GROUP_CONCAT(DISTINCT g.id SEPARATOR ';') AS genreIDs, GROUP_CONCAT(DISTINCT g.name SEPARATOR ';') AS genres, GROUP_CONCAT(DISTINCT CONCAT(s.id,'=', s.name) SEPARATOR ';') AS stars FROM movies m, ratings r, genres_in_movies gm, genres g, stars s, stars_in_movies sm WHERE g.id = gm.genreId AND gm.movieId = m.id AND r.movieId= m.id AND s.id = sm.starId AND sm.movieId = m.id AND m.id = ?;";
    // movie query header: selects all columns and joins them appropriately
    public static final String MOVIE_QUERY_HEADER = "SELECT m.id, m.title, m.year, m.director, m.backdrop_path, m.budget, m.overview, m.poster_path, m.revenue, r.rating, r.numVotes, GROUP_CONCAT(DISTINCT g.id SEPARATOR ';') AS genreIDs, GROUP_CONCAT(DISTINCT g.name SEPARATOR ';') AS genres, GROUP_CONCAT(DISTINCT CONCAT(s.id,'=', s.name) SEPARATOR ';') AS stars FROM movies m, ratings r, genres_in_movies gm, genres g, stars s, stars_in_movies sm WHERE g.id = gm.genreId AND gm.movieId = m.id AND r.movieId= m.id AND s.id = sm.starId AND sm.movieId = m.id ";
    // if browsing by genre
    public static final String MOVIE_QUERY_BROWSE_BY_GENRE = " AND g.name = ? ";
    // if browsing/searching by title
    public static final String MOVIE_QUERY_BROWSE_OR_SEARCH_BY_TITLE = " AND m.title LIKE ? ";
    // if searching by year published
    public static final String MOVIE_QUERY_SEARCH_BY_YEAR = " AND m.year = ? ";
    // if searching by director
    public static final String MOVIE_QUERY_SEARCH_BY_DIRECTOR = " AND m.director LIKE ? ";
    // if searching by star
    public static final String MOVIE_QUERY_SEARCH_BY_STAR = " AND s.name LIKE ?";
    // group by
    public static final String GROUP_BY = " GROUP BY m.id ";
    // order by
    public static final String ORDER_BY_MOVIE = " ORDER BY m.title ";
    public static final String ORDER_BY_RATING = " ORDER BY r.rating ";
    // order
    public static final String ORDER_ASC = " ASC ";
    public static final String ORDER_DESC = " DESC ";
    // limit
    public static final String LIMIT = " LIMIT ? ";
    // offset
    public static final String OFFSET = "OFFSET ?";
    // Get single star info
    public static final String GET_SINGLE_STAR_INFO = "SELECT stars.id, stars.name, stars.birthYear FROM stars WHERE stars.id = ?;";
    // Get all movies with star
    public static final String GET_ALL_MOVIES_WITH_STAR = "SELECT m.id, m.title, m.year, m.director, m.backdrop_path, m.budget, m.overview, m.poster_path, m.revenue, r.rating, r.numVotes, GROUP_CONCAT(DISTINCT g.id SEPARATOR ';') AS genreIDs, GROUP_CONCAT(DISTINCT g.name SEPARATOR ';') AS genres, GROUP_CONCAT(DISTINCT CONCAT(s.id,'=', s.name) SEPARATOR ';') AS stars FROM movies m, ratings r, genres_in_movies gm, genres g, stars s, stars_in_movies sm WHERE g.id = gm.genreId AND gm.movieId = m.id AND r.movieId= m.id AND s.id = sm.starId AND sm.movieId = m.id  AND s.id = ? GROUP BY m.id  ORDER BY r.rating DESC;";
    public static final String VERIFY_CUSTOMER_EXISTS = "SELECT * FROM customers c WHERE c.firstName = ? AND c.lastName = ? AND c.email = ?;";
    // insert sale
    public static final String INSERT_SALE = "INSERT INTO sales (customerId, movieId, saleDate) VALUES((SELECT c.id FROM customers c WHERE c.id = ?), (SELECT m.id FROM movies m WHERE m.id = ?), ?);";
    // insert new data to DB
    public static final String INSERT_UPDATED_DATA_FROM_TMDB = "UPDATE movies m SET backdrop_path = ?, budget = ?, overview = ?, poster_path = ?, revenue = ? WHERE m.id = ?;";
}
