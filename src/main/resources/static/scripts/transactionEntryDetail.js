let hideTransactionEntrySavedAlertTimer = undefined;

document.addEventListener("DOMContentLoaded", () => {
    getTransactionEntryQuantityElement().addEventListener("keypress", transactionEntryQuantityKeypress);

    getSaveActionElement().addEventListener("click", saveActionClick)
    getDeleteActionElement().addEventListener("click", deleteActionClick);
});


function transactionEntryQuantityKeypress(event) {
    if (event.which !== 13) { // Enter key
        return;
    }

    const transactionEntryQuantityElement = getTransactionEntryQuantityElement();
    transactionEntryQuantityElement.focus();
    transactionEntryQuantityElement.select();
}

// Save
function saveActionClick(event) {
    if (!validateSave()) {
        return;
    }

    const saveActionElement = event.target;
    saveActionElement.disabled = true;

    const transactionEntryId = getTransactionEntryId();
    const transactionEntryIdIsDefined = ((transactionEntryId != null) && (transactionEntryId.trim() !== ""));
    const saveActionUrl = ("/api/transactionEntry/" // TODO: verify this is the correct URL
        + (transactionEntryIdIsDefined ? transactionEntryId : ""));
    // TODO: need to make sure this is synchronous with an api
    const saveTransactionEntryRequest = {
        id: transactionEntryId,
        transactionId: getTransactionEntryTransactionId(),
        productId: getTransactionEntryProductId(),
        quantity: getTransactionEntryQuantity(),
        price: getTransactionEntryPrice()
    };

    if (transactionEntryIdIsDefined) {
        ajaxPut(saveActionUrl, saveTransactionEntryRequest, (callbackResponse) => {
           saveActionElement.disabled = false;

           if (isSuccessResponse(callbackResponse)) {
               displayTransactionEntrySavedAlertModal();
           }
        });
    } else {
        ajaxPost(saveActionUrl, saveTransactionEntryRequest, (callbackResponse) => {
            saveActionElement.disabled = false;

            if (isSuccessResponse(callbackResponse)) {
                displayTransactionEntrySavedAlertModal();

                if ((callbackResponse.data != null)
                    && (callbackResponse.data.id != null)
                    && (callbackResponse.data.id.trim() !== "")
                    && (callbackResponse.data.transactionId != null)
                    && (callbackResponse.data.transactionId.trim() !== "")
                    && (callbackResponse.data.transactionId.trim() !== "")) {

                    document.getElementById("deleteActionContainer").classList.remove("hidden");

                    setTransactionEntryId(callbackResponse.data.id.trim());
                    setTransactionEntryTransactionId(callbackResponse.data.transactionId.trim());
                }
            }
        });
    }
}

function validateSave() {
    const quantity = getTransactionEntryQuantity();
    if ((quantity == null) || isNaN(quantity)) {
        displayError("Please provide a valid TransactionEntry quantity.");
        return false;
    } else if (quantity < 0) {
        displayError("TransactionEntry quantity may not be negative.");
        return false;
    }

    return true;
}

function displayTransactionEntrySavedAlertModal() {
    if (hideTransactionEntrySavedAlertTimer) {
        clearTimeout(hideTransactionEntrySavedAlertTimer);
    }

    const savedAlertModalElement = getSavedAlertModalElement();
    savedAlertModalElement.style.display = "none";
    savedAlertModalElement.style.display = "block";

    hideTransactionEntrySavedAlertTimer = setTimeout(hideTransactionEntrySavedAlertModal, 1200);
}

function hideTransactionEntrySavedAlertModal() {
    if (hideTransactionEntrySavedAlertTimer) {
        clearTimeout(hideTransactionEntrySavedAlertTimer);
    }

    getSavedAlertModalElement().style.display = "none";
}
// End save

// Delete
function deleteActionClick(event) {
    const deleteActionElement = event.target;
    // TODO: Check this URL
    const deleteActionUrl = ("/api/entry/delete/" + getTransactionEntryId());

    deleteActionElement.disabled = true;

    ajaxDelete(deleteActionUrl, (callbackResponse) => {
        deleteActionElement.disabled = false;

        if (isSuccessResponse(callbackResponse)) {
            // TODO: Check this URL (pathArray[0] may not work
            let pathArray = window.location.pathname.split('/');
            let deleteRedirectUrl = pathArray[0] + '/' + pathArray[1] + '/' + pathArray[2];
            alert(deleteRedirectUrl);
            window.location.replace(deleteRedirectUrl);
        }
    });
};
// End delete

// Getters and setters
function getSaveActionElement() {
    return document.getElementById("saveButton");
}

function getSavedAlertModalElement() {
    return document.getElementById("transactionEntrySavedAlertModal");
}

function getDeleteActionElement() {
    return document.getElementById("deleteButton");
}

function getTransactionEntryIdElement() {
    return document.getElementById("transactionEntryId");
}

function getTransactionEntryId() {
    return getTransactionEntryIdElement().value;
}

function setTransactionEntryId(transactionEntryId) {
    getTransactionEntryIdElement().value = transactionEntryId;
}

function getTransactionEntryTransactionIdElement() {
    return document.getElementById("transactionEntryTransactionId");
}

function getTransactionEntryTransactionId() {
    return getTransactionEntryTransactionIdElement().value;
}

function setTransactionEntryTransactionId(transactionEntryTransactionId) {
    getTransactionEntryTransactionIdElement().value = transactionEntryTransactionId;
}

function getTransactionEntryProductIdElement() {
    return document.getElementById("transactionEntryProductId");
}

function getTransactionEntryProductId() {
    return getTransactionEntryProductIdElement().value;
}

function setTransactionEntryProductId(transactionEntryProductId) {
    getTransactionEntryProductIdElement().value = transactionEntryProductId;
}

function getTransactionEntryQuantityElement() {
    return document.getElementById("transactionEntryQuantity");
}

function getTransactionEntryQuantity() {
    return getTransactionEntryQuantityElement().value;
}

function getTransactionEntryPriceElement() {
    return document.getElementById("transactionEntryPrice");
}

function getTransactionEntryPrice() {
    return getTransactionEntryPriceElement().value;
}
// End getters and setters