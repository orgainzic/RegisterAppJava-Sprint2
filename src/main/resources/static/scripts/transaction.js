let hideTransactionSavedAlertTimer = undefined;

document.addEventListener("DOMContentLoaded", () => {
	document.getElementById("addButton")
		.addEventListener("click", addActionClick);

	//const employeeFirstNameEditElement =
	//	getEmployeeFirstNameEditElement();
	//employeeFirstNameEditElement.focus();
	//employeeFirstNameEditElement.select();
});

// Save
function addActionClick(event) {
	if (!validateSave()) {
		return;
	}

	const addActionElement = event.target;
	addActionElement.disabled = true;

	const productId = getEmployeeId();
	const productIdIsDefined = (productId.trim() !== "");
	const saveActionUrl = ("/api/checkout/"
		+ (productIdIsDefined ? productId : ""));
	const saveTransactionProducts = {
	/*	id: employeeId,
		managerId: getEmployeeManagerId(),
		lastName: getEmployeeLastNameEditElement().value,
		password: getEmployeePasswordEditElement().value,
		firstName: getEmployeeFirstNameEditElement().value,
		classification: getEmployeeTypeSelectElement().value */
	};

	if (productIdIsDefined) {
		ajaxPatch(saveActionUrl, saveEmployeeRequest, (callbackResponse) => {
			saveActionElement.disabled = false;

			if (isSuccessResponse(callbackResponse)) {
				completeSaveAction(callbackResponse);
			}
		});
	}/* else {
		ajaxPost(saveActionUrl, saveEmployeeRequest, (callbackResponse) => {
			saveActionElement.disabled = false;

			if (isSuccessResponse(callbackResponse)) {
				completeSaveAction(callbackResponse);
			}
		}); 
	} */
}

/* function validateSave() {
	const firstNameEditElement = getEmployeeFirstNameEditElement();
	if (firstNameEditElement.value.trim() === "") {
		displayError("Please provide a valid employee first name.");
		firstNameEditElement.focus();
		firstNameEditElement.select();
		return false;
	}

	const lastNameEditElement = getEmployeeLastNameEditElement();
	if (lastNameEditElement.value.trim() === "") {
		displayError("Please provide a valid employee last name.");
		lastNameEditElement.focus();
		lastNameEditElement.select();
		return false;
	}

	const passwordEditElement = getEmployeePasswordEditElement();
	if (passwordEditElement.value.trim() === "") {
		displayError("Please provide a valid employee password.");
		passwordEditElement.focus();
		passwordEditElement.select();
		return false;
	}

	if (passwordEditElement.value !== getEmployeeConfirmPassword()) {
		displayError("Passwords do not match.");
		passwordEditElement.focus()
		passwordEditElement.select();
		return false;
	}

	const employeeTypeSelectElement = getEmployeeTypeSelectElement();
	if (!employeeTypeSelectElement.closest("tr").classList.contains("hidden")) {
		if (employeeTypeSelectElement.value <= 0) {
			displayError("Please provide a valid employee Type.");
			employeeTypeSelectElement.focus();
			return false;
		}
	}

	return true;
} */

function completeSaveAction(callbackResponse) {
	if (callbackResponse.data == null) {
		return;
	}

	if ((callbackResponse.data.redirectUrl != null)
		&& (callbackResponse.data.redirectUrl !== "")) {

		window.location.replace(callbackResponse.data.redirectUrl);
		return;
	}
	
	displayEmployeeSavedAlertModal();

	const employeeEmployeeIdElement = getEmployeeEmployeeIdElement();
	const employeeEmployeeIdRowElement = employeeEmployeeIdElement.closest("tr");
	if (employeeEmployeeIdRowElement.classList.contains("hidden")) {
		setEmployeeId(callbackResponse.data.id);
		employeeEmployeeIdElement.value = callbackResponse.data.employeeId;
		employeeEmployeeIdRowElement.classList.remove("hidden");
	}
}

function displayEmployeeSavedAlertModal() {
	if (hideEmployeeSavedAlertTimer) {
		clearTimeout(hideEmployeeSavedAlertTimer);
	}

	const savedAlertModalElement = getSavedAlertModalElement();
	savedAlertModalElement.style.display = "none";
	savedAlertModalElement.style.display = "block";

	hideEmployeeSavedAlertTimer = setTimeout(hideEmployeeSavedAlertModal, 1200);
}

function hideEmployeeSavedAlertModal() {
	if (hideEmployeeSavedAlertTimer) {
		clearTimeout(hideEmployeeSavedAlertTimer);
	}

	getSavedAlertModalElement().style.display = "none";
}
// End save

//Getters and setters

//End getters and setters
