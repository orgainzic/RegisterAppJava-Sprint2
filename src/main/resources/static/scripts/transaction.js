document.addEventListener("DOMContentLoaded", () => {
    document.getElementById("searchButton")
        .addEventListener("click", searchActionClick);
    document.getElementById("cancelTransactionImage")
        .addEventListener("click", cancelTransactionActionClickHandler);
    document.getElementById("checkoutImage")
        .addEventListener("click", checkoutActionClick);
    document.getElementById("searchForProductButton")
        .addEventListener("click", searchForProductClickHandler);
    const searchProductElement =
        getSearchProductElement();
    searchProductElement.focus();
    searchProductElement.select();
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
    window.location.replace("/searchForProduct")

}

//Checkout
function checkoutActionClick() {
    ajaxPatch("/api/checkout", (callbackResponse) => {
        if ((callbackResponse.data != null)
			&& (callbackResponse.data.redirectUrl != null)
			&& (callbackResponse.data.redirectUrl !== "")) {
	
			window.location.replace(callbackResponse.data.redirectUrl);
		} else {
			window.location.replace("/cart");
		}
    });
}
/*
function getClickedListItemElement(target) {
    let clickedElement = target;
    while (clickedElement.tagName !== "LI") {
        clickedElement = clickedElement.parentElement;
    }
    return clickedElement;
}

function addProduct(event) {
    const unorderedListElement = document.getElementById("productsListing");
    const nextEntryId = (unorderedListElement.childElementCount + 1).toString();
    const listItemElement = document.createElement("li");
    listItemElement.addEventListener("click", removeProduct);
    const lookupCodeDisplayElement = document.createElement("span");
    lookupCodeDisplayElement.innerHTML = ("Product Lookup Code " + nextEntryId);
    lookupCodeDisplayElement.classList.add("lookupCodeDisplay");
    listItemElement.appendChild(lookupCodeDisplayElement);
    listItemElement.appendChild(
        document.createElement("br"));
    const entryIdDisplayElement = document.createElement("span");
    entryIdDisplayElement.innerHTML = ("\u00A0\u00A0" + nextEntryId);
    listItemElement.appendChild(entryIdDisplayElement);
    unorderedListElement.appendChild(listItemElement);
}
document.addEventListener("DOMContentLoaded", () => {
    document.getElementById("addButton")
        .addEventListener("click", addProduct);
    const listItemElements = document.getElementById("productsListing")
        .querySelectorAll("li");
    for (let i = 0; i < listItemElements.length; i++) {
        listItemElements[i].addEventListener("click", removeProduct);
    }
});
/*
function updateQuantity(event) {

}
*/
/*
function removeProduct(event) {
    const unorderedListElement = document.getElementById("productsListing");
    unorderedListElement.removeChild(
        getClickedListItemElement(event.target));
}*/
/*
function submitTransaction(event) {

}
function cancelTransaction(event) {

}

// Getters and setters





function getremoveButton() {
    return document.getElementById("removeButton");
}

function checkoutButton() {
    return document.getElementById("checkoutButton");
}

*/