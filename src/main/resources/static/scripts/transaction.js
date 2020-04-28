document.addEventListener("DOMContentLoaded", () => {
    
    document.getElementById("cancelTransactionImage")
        .addEventListener("click", cancelTransactionActionClickHandler);
    //document.getElementById("checkoutImage")
      //  .addEventListener("click", checkoutActionClick);
    document.getElementById("searchForProductButton")
        .addEventListener("click", searchForProductClickHandler);
    const searchProductElement =
        getSearchProductElement();
    searchProductElement.focus();
    searchProductElement.select();
});



//Cancel Transaction
function cancelTransactionActionClickHandler() {
	ajaxDelete("/api/cancelTransaction", (callbackResponse) => {
		if ((callbackResponse.data != null)
			&& (callbackResponse.data.redirectUrl != null)
			&& (callbackResponse.data.redirectUrl !== "")) {
	
			window.location.replace(callbackResponse.data.redirectUrl);
		} else {
			window.location.replace("/mainMenu");
		}
	});
}

//SearchForProduct

function searchForProductClickHandler() {
    window.location.replace("/transaction/searchForProduct")

}



//Checkout
/*function checkoutActionClick() {
    ajaxPatch("/api/checkout", (callbackResponse) => {
        if ((callbackResponse.data != null)
			&& (callbackResponse.data.redirectUrl != null)
			&& (callbackResponse.data.redirectUrl !== "")) {
	
			window.location.replace(callbackResponse.data.redirectUrl);
		} else {
			window.location.replace("/success");
		}
    });
}*/




