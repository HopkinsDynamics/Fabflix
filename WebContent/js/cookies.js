/* ====================================================================================================
   DATE:      06 APR 2018
   AUTHORS:   Kelly McKeown   David Hopkins
   UCInetIDs: KPMCKEOW        HOPKINSD
   ID#'s:     42806243        70404050
   CLASS:     CS 122B
   PROJECT:   #1
   ----------------------------------------------------------------------------------------------------
   DESCRIPTION:
   ====================================================================================================
 */

function setSessionCookie(cname, sessionID, username, timeCreated) {
    console.log("setSessionCookie(" + cname + ", " + sessionID + ", " + username + ", " + timeCreated);
    Cookies.set('cname', cname);
    Cookies.set('sessionID', sessionID);
    Cookies.set('username', username);
    Cookies.set('timeCreated', timeCreated);
}
