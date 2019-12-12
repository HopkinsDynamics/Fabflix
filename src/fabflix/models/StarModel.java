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

package fabflix.models;

import fabflix.core.Movie;
import fabflix.core.Star;

public class StarModel {
    private String id;
    private String name;
    private String birthYear;
    private MovieModel[] starsIn;

    public StarModel(String id, String name, String birthYear) {
        this.id = id;
        this.name = name;
        this.birthYear = birthYear;
    }

    public StarModel(Star s) {
        this.id = s.getId();
        this.name = s.getName();
        this.birthYear = Integer.toString(s.getBirthYear());
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getBirthYear() {
        return birthYear;
    }

    public MovieModel[] getStarsIn() {
        return starsIn;
    }

    public void setStarsIn(MovieModel[] starsIn) {
        this.starsIn = starsIn;
    }
}