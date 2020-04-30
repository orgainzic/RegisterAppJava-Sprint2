document.addEventListener("DOMContentLoaded", () => {
	const productListElements = document.getElementById("productsListing").children;
	document.getElementById("backButtonImage")
		.addEventListener("click", backToMainClick);

	for (let i = 0; i < productListElements.length; i++) {
		productListElements[i].addEventListener("click", productClick);
	}
});

//Back to Main Menu Page
function backToMainClick() {
	window.location.replace("/mainMenu");
	
}
//End to Main Menu Page

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
		"/productDetail/"
		+ listItem.querySelector("input[name='productId'][type='hidden']").value);
}
