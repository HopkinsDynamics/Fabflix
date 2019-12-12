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

import fabflix.core.Genre;

public class GenreModel {
    private String id;
    private String name;

    public GenreModel(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public GenreModel(Genre g) {
        this.id = g.getId();
        this.name = g.getName();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
