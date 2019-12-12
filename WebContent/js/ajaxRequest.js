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


function makeAjaxRequest(url, type, request, func_success, s_params, func_400, func_401, func_403, func_500) {
    console.log(arguments.callee.name + "()");
    console.log(JSON.stringify(request));
    $.ajax({
        url:url,
        type:type,
        data:JSON.stringify(request),
        contentType:"application/json",
        dataType:"json",
        success: function(result) {
            console.log("  RECEIVED CODE 200");
            var params = [result];
            params.push(s_params);
            func_success.apply(this, params);
        },
        statusCode: {
            400: function() {
                console.log("  RECEIVED ERROR 400");
                func_400();
            },
            401: function() {
                console.log("  RECEIVED ERROR 401");
                func_401();
            },
            403: function() {
                console.log("  RECEIVED ERROR 403");
                func_403();
            },
            500: function() {
                console.log("  RECEIVED ERROR 500");
                func_500();
            }
        }
    })
}

function null_func() { };