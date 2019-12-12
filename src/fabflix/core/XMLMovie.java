package fabflix.core;

import java.util.ArrayList;

public class XMLMovie {
    private String director;
    private String title;
    private int    year;
    private ArrayList<String> genres;

    public XMLMovie() {
        genres = new ArrayList<>();
    }

    public XMLMovie(String director, String title, int year) {
        this.director = director;
        this.title = title;
        this.year = year;
        genres = new ArrayList<>();
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
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

    public ArrayList<String> getGenres() {
        return genres;
    }

    @Override
    public String toString() {
        String g = "";
        if (genres.size() > 0) {
            g += genres.get(0);
            for (int i = 1; i < genres.size(); ++i) {
                g += ", " + genres.get(i);
            }
        }
        String s = "Year: " + year + "\tDirector: " + director + "\t\t\tTitle: " + title + "\t\t\tGenres: " + g;
        return s;
    }

    public boolean equals(Object xmlm) {
        if ( !(xmlm instanceof XMLMovie) )
            return false;

        XMLMovie m = (XMLMovie) xmlm;
        return this.director.equals(m.getDirector()) && this.title.equals(m.getTitle()) && this.year == m.getYear();
    }

    @Override
    public int hashCode() {
        int hashCode = 1;
        hashCode = hashCode * 37 + this.director.hashCode();
        hashCode = hashCode * 37 + this.title.hashCode();
        return hashCode;
    }
}