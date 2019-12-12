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

package fabflix.token;

public class Session {
    public static long SESSION_TIMEOUT = 1200000;
    private final Token sessionID;
    private final String username;
    private final long timeCreated;
    private long deltaUse;

    /* -------------------- CONSTRUCTORS  -------------------- */
    private Session(String username) {
        sessionID = Token.generateToken();
        this.username = username;
        timeCreated = System.currentTimeMillis();
    }

    public static Session newSession(String name) {
        return new Session(name);
    }


    /* -------------------- FUNCTION DEFINITIONS -------------------- */
    public void update() {
        if (isValid())
            deltaUse = System.currentTimeMillis();
    }

    public void print() {
        System.out.println("--------------------");
        System.out.println("Username: " + username + ", Time Created: " + timeCreated + ", Token: " + sessionID);
        System.out.println("--------------------");
    }

    public boolean isValid() {
//        System.out.println("    isValid()");
        long currentTime = System.currentTimeMillis();
//        System.out.println("      currentTime     = " + currentTime);
//        System.out.println("      SESSION_TIMEOUT = " + SESSION_TIMEOUT);
//        System.out.println("      timeCreated     = " + timeCreated);
        deltaUse = currentTime - (timeCreated + SESSION_TIMEOUT);
//        System.out.println("      deltaUse        = " + deltaUse);
//        System.out.println("      *** NEGATIVE VALUE --> VALID   SESSION");
//        System.out.println("      *** POSITIVE VALUE --> INVALID SESSION");
        return (deltaUse < 0);
//        return System.currentTimeMillis() <= (deltaUse + SESSION_TIMEOUT);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Session)) return false;

        Session session = (Session) o;
        return getTimeCreated() == session.getTimeCreated() &&
                getSessionID().equals(session.getSessionID()) &&
                getUsername().equals(session.getUsername());
    }


    /* -------------------- GETTERS AND SETTERS -------------------- */
    public Token getSessionID() {
        return sessionID;
    }

    public String getUsername() {
        return username;
    }

    public long getTimeCreated() {
        return timeCreated;
    }
}
