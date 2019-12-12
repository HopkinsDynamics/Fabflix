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

import java.util.ArrayList;

public class ShoppingCart {
        private ArrayList<Movie> movies = new ArrayList<>();

        public ShoppingCart() {
            this.movies = new ArrayList<>();
        }

        /* Add Movie To Shopping Cart Functions */
        public void addMovie(Movie m) {
            System.out.println("addMovie(" + m + ")");
            movies.add(m);
        }

        public void removeMovie(String id) {
            for (int i = 0; i < movies.size(); ++i) {
                if (movies.get(i).getId().equals(id)) {
                    movies.remove(i);
                }
            }
        }

        public ArrayList<Movie> getMovies() {
//            System.out.println("getMovies()");
            return movies;
        }

        public void setMovies(ArrayList<Movie> movies) {
            this.movies = movies;
        }

        public void printMovies() {
            for (int i = 0; i < movies.size(); ++i) {
                System.out.println(getMovies().get(i));
            }
        }
}
