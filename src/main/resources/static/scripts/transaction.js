document.addEventListener("DOMContentLoaded", function(event) {
    /*getaddButton().addEventListener(
        "click", () => {addProduct;});
    getupdateButton().addEventListener(
        "click", () => {updateQuantity;});
    //getremoveButton().addEventListener(
    //    "click", () => {removeProduct;});
    getcheckoutButton().addEventListener(
        "click", () => {submitTransaction;});
    getcancelButton().addEventListener(
        "click", () => {cancelTransaction;});
*/
});

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

function updateQuantity(event) {

}

function removeProduct(event) {
    const unorderedListElement = document.getElementById("productsListing");
    unorderedListElement.removeChild(
        getClickedListItemElement(event.target));
}

function submitTransaction(event) {

}
function cancelTransaction(event) {

}

// Getters and setters

function getaddButton() {
    return document.getElementById("addButton");
}

function getupdateButton() {
    return document.getElementById("updateButton");
}

function getremoveButton() {
    return document.getElementById("removeButton");
}

function checkoutButton() {
    return document.getElementById("checkoutButton");
}

function cancelButton() {
    return document.getElementById("cancelButton");
}
