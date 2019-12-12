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

public class MovieInfoOptions {
    public boolean getID;
    public boolean getTitle;
    public boolean getYear;
    public boolean getDirector;
    public boolean getBackdropPath;
    public boolean getBudget;
    public boolean getOverview;
    public boolean getPosterPath;
    public boolean getRevenue;
    public boolean getRating;
    public boolean getNumVotes;
    public boolean getGenres;
    public boolean getStars;

    public MovieInfoOptions(boolean getID, boolean getTitle, boolean getYear, boolean getDirector,
                            boolean getBackdropPath, boolean getBudget, boolean getOverview,
                            boolean getPosterPath, boolean getRevenue, boolean getRating,
                            boolean getNumVotes, boolean getGenres, boolean getStars) {
        this.getID = getID;
        this.getTitle = getTitle;
        this.getYear = getYear;
        this.getDirector = getDirector;
        this.getBackdropPath = getBackdropPath;
        this.getBudget = getBudget;
        this.getOverview = getOverview;
        this.getPosterPath = getPosterPath;
        this.getRevenue = getRevenue;
        this.getRating = getRating;
        this.getNumVotes = getNumVotes;
        this.getGenres = getGenres;
        this.getStars = getStars;
    }

    public MovieInfoOptions() {
        this.getID = true;
        this.getTitle = true;
        this.getYear = true;
        this.getDirector = true;
        this.getBackdropPath = true;
        this.getBudget = true;
        this.getOverview = true;
        this.getPosterPath = true;
        this.getRevenue = true;
        this.getRating = true;
        this.getNumVotes = true;
        this.getGenres = true;
        this.getStars = true;
    }

}
