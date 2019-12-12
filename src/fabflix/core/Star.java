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

public class Star {
    private String id;
    private String name;
    private int birthYear;
    private Movie[] starsIn;

    public Star(String id, String name, int birthYear) {
        this.id = id;
        this.name = name;
        this.birthYear = birthYear;
    }

    public Star(String id, String name) {
//        System.out.println("        CREATING STAR: " + id + ", " + name);
        this.id = id;
        this.name = name;
    }

    @Override
    public int hashCode() {
        return getNum(id);
    }

    @Override
    public boolean equals (Object obj) {
        return obj instanceof Star && ((Star) obj).id.equals(this.id);
    }

    private static int getNum(String s) {
        long val = 0;
        for (char c : s.toCharArray()) {
            val += c;
        }
        return (int)val;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBirthYear() {
        return birthYear;
    }

    public void setBirthYear(int birthYear) {
        this.birthYear = birthYear;
    }

    public Movie[] getStarsIn() {
        return starsIn;
    }

    public void setStarsIn(Movie[] starsIn) {
        this.starsIn = starsIn;
    }
}
