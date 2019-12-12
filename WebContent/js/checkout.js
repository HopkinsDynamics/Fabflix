
function makeCheckoutRequest() {
    console.log(arguments.callee.name + "()");
    // Get session
    var s = getSession();
    var formData = serializeFormJSON('#customerValidationForm')

    var request = {
        "sessionID":s.sessionID,
        "username":s.username,
        "formData":formData
    };

    makeAjaxRequest('api/checkout', 'POST', request, handleCheckoutResponse, null, handleCheckouError, handleCheckouError, handleCheckouError, handleCheckouError);
}

function handleCheckoutResponse(result) {
    console.log(arguments.callee.name + "()");
    if (result.result === 'success') {
        location.href = 'successCheckout.html';
    }
}

function handleCheckouError() {
    console.log(arguments.callee.name + "()");
    $('#checkoutErrorText').html("Invalid input.");
}
