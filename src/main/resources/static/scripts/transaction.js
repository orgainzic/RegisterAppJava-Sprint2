document.addEventListener("DOMContentLoaded", () => {
    const transactionProductListElements = document.getElementById("transactionEntry").children;
    document.getElementById("cancelTransactionImage")
        .addEventListener("click", cancelTransactionActionClickHandler);
    document.getElementById("checkoutImage")
        .addEventListener("click", checkoutActionClick);
    document.getElementById("searchForProductButton")
        .addEventListener("click", searchForProductClickHandler);
    for (let i = 0; i < transactionProductListElements.length; i++) {
		transactionProductListElements[i].addEventListener("click", transactionClick);
	}
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
    window.location.replace(window.location + "/search")

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

function transactionClick(event) {
	let listItem = findClickedListItemElement(event.target);
	window.location.assign(
		window.location + "/details/" + listItem.querySelector("input[name='transactionEntryId'][type='hidden']").value);
}

//Checkout
function checkoutActionClick() {
    window.location + "/checkout";
}




