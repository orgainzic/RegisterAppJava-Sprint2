document.addEventListener("DOMContentLoaded", () => {
    document.getElementById("purchaseButton")
        .addEventListener("click", purchaseActionClick);
});

function purchaseActionClick(){
    ajaxPost("/api/purchase", (callbackResponse) => {
		if ((callbackResponse.data != null)
			&& (callbackResponse.data.redirectUrl != null)
			&& (callbackResponse.data.redirectUrl !== "")) {
	
			window.location.replace(callbackResponse.data.redirectUrl);
		} else {
			window.location.replace("/success");
		}
}