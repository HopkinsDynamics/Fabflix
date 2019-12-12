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

function redirectToMovieListPage(genre, title) {
    console.log(arguments.callee.name + "(" + genre + ", " + title + ")");
    // Get session information
    var s = getEmployeeSession();

    // Build request
    // var request = {"sessionID":session.sessionID,"username":session.username,"genre":genre,"title":title};
    // console.log(JSON.stringify(request));

    // Build search params
    // browse genre, browse title, search title, search movie year, search movie director, search star name, limit, offset, order
    var searchParams = {
        "sessionID": s.sessionID,
        "username": s.username,
        "bGenre": genre,
        "bTitle": title,
        "sTitle": "null",
        "sYear": "null",
        "sDir": "null",
        "sStar": "null",
        "orderBy": "Title",
        "order": "Ascending",
        "limit": 10,
        "offset": 0
    };

    // Save result in local storage
    var storedResult = localStorage.setItem('searchParams', JSON.stringify(searchParams));
    // Redirect page
    window.location.replace('movieList.html');

    // Make request
    // makeAjaxRequest("api/browse", "POST", request, handleRedirectRequest, searchParams, null_func, null_func, null_func, null_func)
}

function redirectToSearchPage() {
    location.href='search.html';
}

function redirectToCart() {
    location.href='shoppingCart.html'
}
