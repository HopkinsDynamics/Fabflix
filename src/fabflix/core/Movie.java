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

import fabflix.models.GenreModel;
import fabflix.models.StarModel;

import java.util.ArrayList;

public class Movie {
    private String id;
    private String title;
    private int year;
    private String director;
    private String backdropPath;
    private int budget;
    private String overview;
    private String posterPath;
    private int revenue;
    private float rating;
    private int numVotes;
    private ArrayList<Genre> genres = new ArrayList<>();
    private ArrayList<Star> stars = new ArrayList<>();

    public Movie(String id, String title, int year, String director, String backdropPath, int budget, String overview, String posterPath, int revenue, float rating, int numVotes, ArrayList<Genre> genres, ArrayList<Star> stars) {
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
        this.genres = genres;
        this.stars = stars;
    }
    public Movie(XMLMovie xmlm) {
        this.id = Core.brain.getNewXMLMovieID();
        this.title = xmlm.getTitle();
        this.director = xmlm.getDirector();
        this.year = xmlm.getYear();
        this.backdropPath = null;
        this.budget = 0;
        this.overview = null;
        this.posterPath = null;
        this.revenue = 0;
        this.rating = 0;
        this.numVotes = 0;
        this.genres = null;
        this.stars = null;
    }
    public Movie(String id) {
        this.id = id;
    }

    public void addStars(String starInfo) {
//        System.out.println("  addStars()");
//        String[] splitIDs = IDs.split(";");
//        String[] splitNames = names.split(";");
//        System.out.println("    starInfo = " + starInfo);
        String[] splitStarInfo = starInfo.split("[=|;]");
        for (int i = 0; i < splitStarInfo.length; ++i) {
//            System.out.println("      ID: " + splitStarInfo[i] + ", Name: " + splitStarInfo[i + 1]);
            addStar(new Star(splitStarInfo[i], splitStarInfo[++i]));
        }
//        System.out.println("  DONE addStars()");
    }

    private void addStar(Star s) {
        if (!hasStar(s)) {
            stars.add(s);
        }
    }

    public String[] getStarNames() {
        String[] starNames = new String[stars.size()];
        for (int i = 0; i < starNames.length; ++i) {
            starNames[i] = stars.get(i).getName();
        }
        return starNames;
    }

    public boolean hasStar(Star s) {
        return (stars.contains(s));
    }

    public void addGenres(String IDs, String names) {
//        System.out.println("addGenres(" + IDs + ", " + names + ")");
        String[] splitIDs = IDs.split(";");
        String[] splitNames = names.split(";");
        for (int i = 0; i < splitIDs.length; ++i) {
//            System.out.println("ID: " + splitIDs[i] + ", Name: " + splitNames[i]);
            addGenre(new Genre(splitIDs[i], splitNames[i]));
        }
    }

    private void addGenre(Genre g) {
        if (!hasGenre(g)) {
            genres.add(g);
        }
    }

    public String[] getGenreNames() {
        String[] genreNames = new String[genres.size()];
        for (int i = 0; i < genreNames.length; ++i) {
            genreNames[i] = genres.get(i).getName();
        }
        return genreNames;
    }

    public boolean hasGenre(Genre g) {
        return (genres.contains(g));
    }

    public StarModel[] getStarModelArray() {
        StarModel[] s = new StarModel[stars.size()];
        for (int i = 0; i < s.length; ++i) {
            s[i] = new StarModel(stars.get(i));
        }
        return s;
    }

    public GenreModel[] getGenreModelArray() {
        GenreModel[] g = new GenreModel[genres.size()];
        for (int i = 0; i < g.length; ++i) {
            g[i] = new GenreModel(genres.get(i));
        }
        return g;
    }


    @Override
    public String toString() {
        return String.format("id: " + id + ", title: " + title);
    }

    /* -------------------- GETTERS AND SETTERS -------------------- */
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public int getBudget() {
        return budget;
    }

    public void setBudget(int budget) {
        this.budget = budget;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public int getRevenue() {
        return revenue;
    }

    public void setRevenue(int revenue) {
        this.revenue = revenue;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public int getNumVotes() {
        return numVotes;
    }

    public void setNumVotes(int numVotes) {
        this.numVotes = numVotes;
    }

    public ArrayList<Genre> getGenres() {
        return genres;
    }

    public void setGenres(ArrayList<Genre> genres) {
        this.genres = genres;
    }

    public ArrayList<Star> getStars() {
        return stars;
    }

    public void setStars(ArrayList<Star> stars) {
        this.stars = stars;
    }
}
