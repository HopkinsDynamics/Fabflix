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

public final class QueryBuilder {
    public static QueryBuilder qb = new QueryBuilder();
    private SearchParameters sp;

    public QueryBuilder() { }

    public String buildQuery(SearchParameters sp) {
        String query = Queries.MOVIE_QUERY_HEADER;

        if (!sp.getbGenre().equals("null")) {
            // browse by genre
//            return Queries.MOVIE_QUERY_HEADER + Queries.MOVIE_QUERY_BROWSE_BY_GENRE + Queries.MOVIE_QUERY_FOOTER;
            query += Queries.MOVIE_QUERY_BROWSE_BY_GENRE;
        }

        if (!sp.getbTitle().equals("null")) {
            // browse by title
//            return Queries.MOVIE_QUERY_HEADER + Queries.MOVIE_QUERY_BROWSE_OR_SEARCH_BY_TITLE + Queries.MOVIE_QUERY_FOOTER;
            query += Queries.MOVIE_QUERY_BROWSE_OR_SEARCH_BY_TITLE;
        }

        if (!sp.getsTitle().equals("null")) {
            // search by title
            query += Queries.MOVIE_QUERY_BROWSE_OR_SEARCH_BY_TITLE;
        }

        if (!sp.getsYear().equals("null")) {
            // search by year
            query += Queries.MOVIE_QUERY_SEARCH_BY_YEAR;
        }

        if (!sp.getsDir().equals("null")) {
            // search by director
            query += Queries.MOVIE_QUERY_SEARCH_BY_DIRECTOR;
        }

        if (!sp.getsStar().equals("null")) {
            // search by star
            query += Queries.MOVIE_QUERY_SEARCH_BY_STAR;
        }

        // Always include these:
        query += Queries.GROUP_BY;

        if (sp.getOrderBy().equals("m.title"))
            query += Queries.ORDER_BY_MOVIE;
        if (sp.getOrderBy().equals("r.rating"))
            query += Queries.ORDER_BY_RATING;

        if (sp.getOrder().equals("ASC"))
            query += Queries.ORDER_ASC;
        if (sp.getOrder().equals("DESC"))
            query += Queries.ORDER_DESC;

        query += Queries.LIMIT;
        query += Queries.OFFSET;
        query += ";";

        return query;
    }
}
