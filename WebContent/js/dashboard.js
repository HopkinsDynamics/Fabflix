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

function serializeFormJSON(form) {
    var o = {};
    var a = $(form).serializeArray();

    $.each(a, function () {
        if (o[this.name]) {
            if (!o[this.name].push) {
                o[this.name] = [o[this.name]];
            }
            o[this.name].push(this.value || '');
        } else {
            o[this.name] = this.value || '';
        }
    });
    return o;
}
function submitAddMovieInfo() {
    // Get session
  //  var s = getEmployeeSession();
    // Build request
    // var rrequest = {"sessionID":s.sessionID, "username":s.username};
    var request = serializeFormJSON('#addMoveForm');

    console.log(request);
    console.log(JSON.stringify(request));

    // if (request.username === '' || request.password === '') { // print error message }

    $.ajax({
        url: "api/dashboard",
        type: "POST",
        data: JSON.stringify(serializeFormJSON('#addMovieForm')),
        contentType: "application/json",
        dataType: "json",
        success: function(result) {
            console.log(result);
            handleAddMovieRequest(result);
        },
        statusCode: {
            403: function(result) {
                console.log("ERROR 403");
                $('#addMovieMsg').empty();
                $('#AddMovieMsg').append("Invalid info");
            },
            500: function(result) {
                console.log("ERROR 500");
            }
        }
    })
}
function submitStarInfo(){
    var request = serializeFormJSON('#insertStarForm');
    console.log(request);
    console.log(JSON.stringify(request));

    // if (request.username === '' || request.password === '') { // print error message }

    $.ajax({
        url: "api/insertStarDashboard",
        type: "POST",
        data: JSON.stringify(serializeFormJSON('#insertStarForm')),
        contentType: "application/json",
        dataType: "json",
        success: function(result) {
            console.log(result);
            handleInsertStarRequest(result);
        },
        statusCode: {
            403: function(result) {
                console.log("ERROR 403");
                $('#insertStarMsg').empty();
               $('#insertStarMsg').append("Invalid info");
            },
            500: function(result) {
                console.log("ERROR 500");
            }
        }
    })
}
function viewMetadataInfo(){
   // var request = serializeFormJSON('#viewMetadataForm');


   // Get session
    // var s = getEmployeeSession();
 //   Build request
    var request = "new metadata request";//{"sessionID":s.sessionID, "username":s.username};
    // if (request.username === '' || request.password === '') { // print error message }
    console.log(request);
    console.log(JSON.stringify(request));
    $.ajax({
        url: "api/metadataDashboard",
        type: "POST",
        data: JSON.stringify(request),
        contentType: "application/json",
        dataType: "json",
        success: function(result) {
            console.log("recieved result..")
            console.log(result);
            handleViewMetadataRequest(result);
        },
        statusCode: {
            400: function(result){
                console.log("ERROR 400");
            },
            403: function(result) {
                console.log("ERROR 403");
          //      $('#loginErrMsg').append("Invalid info");
            },
            500: function(result) {
                console.log("ERROR 500");
            }
        }
    })
}

function handleAddMovieRequest(result) {
    console.log(arguments.callee.name + "()");
    var success;
    console.log(result.success);
    if(result.success==1){
        success="Success";
    }else{
        success="Failure.. duplicate movie"
    }
      $('#addMovieMsg').empty();
    $('#addMovieMsg').append(success);

}
function handleInsertStarRequest(result) {
    console.log(arguments.callee.name + "()");
    var success;
    console.log(result.success);
    if(result.success==1){
        success="Success";
    }else{
        success="Failure.. "
    }
    $('#insertStarMsg').empty();
    $('#insertStarMsg').append(success);

}
function handleViewMetadataRequest(result) {
    console.log(arguments.callee.name + "()");

    var metadataTable="<table class='tableBorder'>" +
        "<tr ><th class='tableBorder'>Table Name</th>" +
        "<th class='tableBorder'>Column Name</th>" +
        "<th class='tableBorder'>Field Type</th></tr>";
    var len=result.length;
    var currentTable=" ";
    for(var i =0;i<len;++i){

      //  if(!currentTable.localeCompare(result[i].table_name)) {
      //      currentTable = result[i].table_name;
            metadataTable += "<tr  >" +
                "<td class='tableBorder'>" + result[i].table_name + "</td>" +
                "<td class='tableBorder'>" + result[i].column_name + "</td>" +
                "<td class='tableBorder'>" + result[i].field_type + "</td></tr>"
   //     }
    //    else {
    //        metadataTable += "<tr><td>" + ""+ "</td><td>" + result[i].column_name + "</td><td>" + result[i].field_type + "</td></tr>"

    //    }
        }
    metadataTable+="</table>";
    $('#metadata').empty();
    $('#metadata').append(metadataTable);
}

function showAddMovieSuccessMessage(){

}
function showAddStarSuccessMessage(){

}