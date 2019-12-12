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
import fabflix.core.Movie;
import fabflix.core.Star;

import java.util.ArrayList;

public class MovieModel {
    private final String id;
    private final String title;
    private final int year;
    private final String director;
    private final String backdropPath;
    private final int budget;
    private final String overview;
    private final String posterPath;
    private final int revenue;
    private final float rating;
    private final int numVotes;
    private final GenreModel[] genres;
    private final StarModel[] stars;

    public MovieModel(String id, String title, int year, String director, String backdropPath, int budget, String overview, String posterPath, int revenue, float rating, int numVotes, GenreModel[] genres, StarModel[] stars) {
        this.id = id;
        this.title = title;
        this.year = year;
        this.director = director;
        this.backdropPath = backdropPath;
        this.budget = budget;
        this.overview = overview;
        this.posterPath = posterPath;
        this.revenue = revenue;
        this.rating = rating;
        this.numVotes = numVotes;
//        this.genres = genres.toArray(new String[genres.size()]);
//        this.stars = stars.toArray(new String[stars.size()]);
        this.genres = genres;
        this.stars = stars;
    }

    public MovieModel(Movie m) {
        this.id = m.getId();
        this.title = m.getTitle();
        this.year = m.getYear();
        this.director = m.getDirector();
        this.backdropPath = m.getBackdropPath();
        this.budget = m.getBudget();
        this.overview = m.getOverview();
        this.posterPath = m.getPosterPath();
        this.revenue = m.getRevenue();
        this.rating = m.getRating();
        this.numVotes = m.getNumVotes();
//        this.genres = m.getGenreNames();
//        this.stars = m.getStarNames();
        this.genres = m.getGenreModelArray();
        this.stars = m.getStarModelArray();
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getYear() {
        return year;
    }

    public String getDirector() {
        return director;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public int getBudget() {
        return budget;
    }

    public String getOverview() {
        return overview;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public int getRevenue() {
        return revenue;
    }

    public float getRating() {
        return rating;
    }

    public int getNumVotes() {
        return numVotes;
    }

    public GenreModel[] getGenres() {
        return genres;
    }

    public StarModel[] getStars() {
        return stars;
    }
}
