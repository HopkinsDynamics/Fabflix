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

import fabflix.token.Session;

public class SessionModel {
    private final String sessionID;
    private final String username;
    private final String timeCreated;

    public SessionModel(Session session) {
        sessionID = session.getSessionID().toString();
        username = session.getUsername();
        timeCreated = Long.toString(session.getTimeCreated());
    }

    public String getSessionID() {
        return sessionID;
    }

    public String getUsername() {
        return username;
    }

    public String getTimeCreated() {
        return timeCreated;
    }
}
