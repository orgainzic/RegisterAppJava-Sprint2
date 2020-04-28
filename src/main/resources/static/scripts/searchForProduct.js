document.addEventListener("DOMContentLoaded", () => {
    document.getElementById("purchaseButton")
        .addEventListener("click", purchaseActionClick);
});

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


