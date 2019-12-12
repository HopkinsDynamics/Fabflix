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

public class MovieContainerModel {
    private MovieModel[] container;

    public MovieContainerModel(MovieModel[] container) {
        this.container = container;
    }

    public MovieContainerModel() {

    }

    public MovieModel[] getContainer() {
        return this.container;
    }
}
