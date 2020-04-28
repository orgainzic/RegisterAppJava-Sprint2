package edu.uark.registerapp.controllers.enums;

public enum ViewNames {
	SIGN_IN("signIn", "/"),
	MAIN_MENU("mainMenu"),
	PRODUCT_DETAIL("productDetail"),
	EMPLOYEE_DETAIL("employeeDetail"),
	SEARCH("searchForProduct"),
	SUCCESS("success"),
	PRODUCT_LISTING("productListing"),
	TRANSACTION("transaction"),
	ENTRY_DETAIL("transactionEntryDetail");
	
	public String getRoute() {
		return this.route;
	}
	public String getViewName() {
		return this.viewName;
	}

	private String route;
	private String viewName;

	private ViewNames(final String viewName) {
		this(viewName, "/".concat(viewName));
	}

	private ViewNames(final String viewName, final String route) {
		this.route = route;
		this.viewName = viewName;
	}
}