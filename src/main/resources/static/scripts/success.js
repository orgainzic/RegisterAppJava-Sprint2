document.addEventListener("DOMContentLoaded", () => {
	document.getElementById("homeButtonImage")
        .addEventListener("click", homeActionClick);
});

function homeActionClick() {
    ajaxDelete("/api/mainMenuRedirect", (callbackResponse) => {
		if ((callbackResponse.data != null)
			&& (callbackResponse.data.redirectUrl != null)
			&& (callbackResponse.data.redirectUrl !== "")) {
	
			window.location.replace(callbackResponse.data.redirectUrl);
		} else {
			window.location.replace("/mainMenu");
		}
	});
}