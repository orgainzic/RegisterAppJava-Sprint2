document.addEventListener("DOMContentLoaded", () => {
	document.getElementById("searchButton")
        .addEventListener("click", searchActionClick);
    document.getElementById("purchaseButton")
        .addEventListener("click", purchaseActionClick);
});

//Search for Product
function searchActionClick(event){
    if (!validateSearch()) {
        return;
    }
    else {
        document.getElementById("searchForm").submit();
    }
}
function getSearchId(){
    return document.getElementById("searchProduct").value;
}
function getSearchProductElement(){
    return document.getElementById("searchProduct");
}
function validateSearch(){
    const searchProductElement = getSearchProductElement();
    if(searchProductElement.value.trim() === "") {
        displayError("Please enter a product to search for.");
        searchProductElement.focus();
        searchProductElement.select();
        return false;
    }
    return true;
}
function completeSearchAction(callbackResponse) {
    if (callbackResponse.data == null) {
        return;
    }
    if ((callbackResponse.data.redirectUrl != null)
        && (callbackResponse.data.redirectUrl !== "")) {
        window.location.replace(callbackResponse.data.redirectUrl);
        return;
    }
}

function purchaseActionClick(){
    ajaxPost("/api/selected", (callbackResponse) => {
		if ((callbackResponse.data != null)
			&& (callbackResponse.data.redirectUrl != null)
			&& (callbackResponse.data.redirectUrl !== "")) {
	
			window.location.replace(callbackResponse.data.redirectUrl);
		} else {
			window.location.replace("/success");
		}
});
}

function getClickedListItemElement(target) {
    let clickedElement = target;
    while (clickedElement.tagName !== "LI") {
        clickedElement = clickedElement.parentElement;
    }
    return clickedElement;
}


