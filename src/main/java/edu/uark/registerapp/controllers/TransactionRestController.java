package edu.uark.registerapp.controllers;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import edu.uark.registerapp.commands.transactions.RemoveProductFromTransaction;
import edu.uark.registerapp.commands.transactions.UpdateTransactionEntryQuantity;
import edu.uark.registerapp.controllers.enums.ViewNames;
import edu.uark.registerapp.models.api.ApiResponse;
import edu.uark.registerapp.models.api.TransactionEntry;

@RestController
@RequestMapping(value = "/api/entry")
public class TransactionRestController extends BaseRestController {
	@RequestMapping(value = "/update", method = RequestMethod.PUT)
	public @ResponseBody ApiResponse updateEntry(
		@RequestBody final TransactionEntry entry,
		final HttpServletRequest request,
		final HttpServletResponse response
	) {

		final ApiResponse elevatedUserResponse =
			this.redirectUserNotElevated(
				request,
				response,
				ViewNames.SIGN_IN.getRoute());

		if (!elevatedUserResponse.getRedirectUrl().equals(StringUtils.EMPTY)) {
			return elevatedUserResponse;
		}

		return this.updateEntry.setApiTransactionEntry(entry).execute();
	}

	@RequestMapping(value = "/delete/{entryId}", method = RequestMethod.DELETE)
	public @ResponseBody ApiResponse deleteEntry(
        @PathVariable final UUID entryId,
		final HttpServletRequest request,
		final HttpServletResponse response
	) {
		final ApiResponse elevatedUserResponse =
			this.redirectUserNotElevated(
				request,
				response,
				ViewNames.SIGN_IN.getRoute());

		if (!elevatedUserResponse.getRedirectUrl().equals(StringUtils.EMPTY)) {
			return elevatedUserResponse;
		}

        removeEntry.setTransactionEntryId(entryId);
        removeEntry.execute();
		return new ApiResponse();
	}

	// Properties
    @Autowired
    private UpdateTransactionEntryQuantity updateEntry;

    @Autowired
    private RemoveProductFromTransaction removeEntry;
}