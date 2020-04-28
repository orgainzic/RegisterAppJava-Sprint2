document.addEventListener("DOMContentLoaded", () => {
	const searchProductListElements = document.getElementById("productsSearchedListing").children;
	document.getElementById("searchButton")
        .addEventListener("click", searchActionClick);
    for (let i = 0; i < searchProductListElements.length; i++) {
		searchProductListElements[i].addEventListener("click", productClick);
	}
});

//Search for Product
function getURL(){
	return window.location;
}

function searchActionClick(event){
    if (!validateSearch()) {
        return;
    }
    else {
		document.getElementById("searchForm").action=getURL();
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
		alert("THIS SHOULD NOT BE HAPPENING")
        window.location.replace(callbackResponse.data.redirectUrl);
        return;
    }
}

function findClickedListItemElement(clickedTarget) {
	if (clickedTarget.tagName.toLowerCase() === "li") {
		return clickedTarget;
	} else {
		let ancestorIsListItem = false;
		let ancestorElement = clickedTarget.parentElement;

		while (!ancestorIsListItem && (ancestorElement != null)) {
			ancestorIsListItem = (ancestorElement.tagName.toLowerCase() === "li");

			if (!ancestorIsListItem) {
				ancestorElement = ancestorElement.parentElement;
			}
		}

		return (ancestorIsListItem ? ancestorElement : null);
	}
}

function productClick(event) {
	let listItem = findClickedListItemElement(event.target);
	window.location.assign(
		window.location + "/"
		+ listItem.querySelector("input[name='productId'][type='hidden']").value);
}

/*

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
}*/



