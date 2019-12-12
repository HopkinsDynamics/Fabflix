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

function initPage() {
    console.log(arguments.callee.name + "()");
    // Set initial image
    setInitialBGImage();
}

function submitLoginInfo() {
    var request = serializeFormJSON('#loginForm');
    console.log(request);
    console.log(JSON.stringify(request));

    // if (request.username === '' || request.password === '') { // print error message }

    $.ajax({
        url: "api/employeeLogin",
        type: "POST",
        data: JSON.stringify(serializeFormJSON('#loginForm')),
        contentType: "application/json",
        dataType: "json",
        success: function(result) {
            console.log(result);
            handleLoginRequest(result);
        },
        statusCode: {
            403: function(result) {
                console.log("ERROR 403");
                $('#loginErrMsg').append("Invalid username or password or reCaptcha not checked");
            },
            500: function(result) {
                console.log("ERROR 500");
            }
        }
    })
}

function handleLoginRequest(result) {
    console.log(arguments.callee.name + "()");
    $('#loginErrMsg').empty();
    console.log("  sessionID: " + result.sessionID);
    console.log("  username: " + result.username);
    console.log("  timeCreated: " + result.timeCreated);

    setSessionCookie("sessionCookie", result.sessionID, result.username, result.timeCreated);
    window.location.replace("_dashboard.html");
}

