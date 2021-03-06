package edu.uark.registerapp.controllers;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import edu.uark.registerapp.commands.products.ProductQuery;
import edu.uark.registerapp.commands.transactions.AddProductToTransaction;
import edu.uark.registerapp.commands.transactions.ProductSearchByPartialLookupCode;
import edu.uark.registerapp.commands.transactions.TransactionCancel;
import edu.uark.registerapp.commands.transactions.TransactionCreateCommand;
import edu.uark.registerapp.commands.transactions.TransactionEntriesQueriedByTransactionId;
import edu.uark.registerapp.commands.transactions.TransactionEntryQuery;
import edu.uark.registerapp.commands.transactions.TransactionSubmission;
import edu.uark.registerapp.commands.transactions.TransactionSummaryQuery;
import edu.uark.registerapp.commands.transactions.UpdateTransactionPrice;
import edu.uark.registerapp.controllers.enums.ViewModelNames;
import edu.uark.registerapp.controllers.enums.ViewNames;
import edu.uark.registerapp.models.api.ApiResponse;
import edu.uark.registerapp.models.api.Product;
import edu.uark.registerapp.models.api.Transaction;
import edu.uark.registerapp.models.api.TransactionEntry;
import edu.uark.registerapp.models.api.TransactionSummary;
import edu.uark.registerapp.models.entities.ActiveUserEntity;
import edu.uark.registerapp.models.enums.EmployeeClassification;

@Controller
@RequestMapping(value = "/transaction")
public class TransactionRouteController extends BaseRouteController {
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public RedirectView landing(@RequestParam final Map<String, String> queryParameters,
			final HttpServletRequest request) {

		createTransaction.setCashierID(this.getCurrentUser(request).get().getEmployeeId());
		UUID transactionId = createTransaction.execute().getId();
	    return new RedirectView("https://orgainzic-register-app.herokuapp.com/transaction/" + transactionId);
	}

	@RequestMapping(value = "/{transactionId}", method = RequestMethod.GET)
	public ModelAndView transaction(
			@PathVariable final UUID transactionId,
			@RequestParam final Map<String, String> queryParameters,
			final HttpServletRequest request) {

			ModelAndView modelAndView =
			this.setErrorMessageFromQueryString(
				new ModelAndView(ViewNames.TRANSACTION.getViewName()),
				queryParameters);
				
				updatePrice.setTransactionId(transactionId);
				updatePrice.execute();

				summaryQuery.setTransactionId(transactionId);
				queryEntries.setTransactionId(transactionId);

				try {
					modelAndView.addObject(
						ViewModelNames.ENTRIES.getValue(),
						queryEntries.execute());
				} catch (final Exception e) {
					System.out.println(e.toString());
					modelAndView.addObject(
						ViewModelNames.ERROR_MESSAGE.getValue(),
						e.getMessage());
					modelAndView.addObject(
						ViewModelNames.ENTRIES.getValue(),
						(new TransactionEntry[0]));
				}

				try {
					modelAndView.addObject(
						ViewModelNames.SUMMARY.getValue(),
						summaryQuery.execute());
				} catch (final Exception e) {
					modelAndView.addObject(
						ViewModelNames.ERROR_MESSAGE.getValue(),
						e.getMessage());
					modelAndView.addObject(
						ViewModelNames.SUMMARY.getValue(),
						(new TransactionSummary[0]));
				}
	
		return modelAndView;
	}

	@RequestMapping(value = "/{transactionId}/search", method = RequestMethod.GET)
	public ModelAndView searchLanding(
		@RequestParam final Map<String, String> queryParameters,
		final HttpServletRequest request
	) {
        ModelAndView modelAndView =
			this.setErrorMessageFromQueryString(
				new ModelAndView(ViewNames.SEARCH.getViewName()),
				queryParameters);
	
		return modelAndView;
	}
	
	@RequestMapping(value = "/{transactionId}/search", method = RequestMethod.POST)
	public ModelAndView searchActive(
		@RequestParam final Map<String, String> queryParameters,
		final HttpServletRequest request
	) {
        ModelAndView modelAndView =
			this.setErrorMessageFromQueryString(
				new ModelAndView(ViewNames.SEARCH.getViewName()),
				queryParameters);
		
		searchByPartialLookup.setPartialLookupCode(queryParameters.get("searchProduct"));
		
		Product[] products = searchByPartialLookup.execute();

			try {
				modelAndView.addObject(
					ViewModelNames.PRODUCTS.getValue(),
					products);
			} catch (final Exception e) {
				modelAndView.addObject(
					ViewModelNames.ERROR_MESSAGE.getValue(),
					e.getMessage());
				modelAndView.addObject(
					ViewModelNames.PRODUCTS.getValue(),
					(new Product[0]));
			}
	
		return modelAndView;
	}

	@RequestMapping(value = "/{transactionId}/search/{productId}", method = RequestMethod.GET)
	public RedirectView addToCart(
		@PathVariable final UUID transactionId,
		@PathVariable final UUID productId,
		@RequestParam final Map<String, String> queryParameters,
		final HttpServletRequest request
	) {
		
		addToTransaction.setApiProduct(searchById.setProductId(productId).execute());
		addToTransaction.setTransactionId(transactionId);
		addToTransaction.execute();

		//return new ApiResponse();
		return new RedirectView("https://orgainzic-register-app.herokuapp.com/transaction/" + transactionId);	
	}

	@RequestMapping(value = "/{transactionId}/details/{entryId}", method = RequestMethod.GET)
	public ModelAndView detailsLanding(
		@PathVariable final UUID transactionId,
		@PathVariable final UUID entryId,
		@RequestParam final Map<String, String> queryParameters,
		final HttpServletRequest request
	) {
		ModelAndView modelAndView =
			this.setErrorMessageFromQueryString(
				new ModelAndView(ViewNames.ENTRY_DETAIL.getViewName()),
				queryParameters);


			querySpecificEntry.setTransactionEntryId(entryId);

			final Optional<ActiveUserEntity> activeUserEntity =
			this.getCurrentUser(request);
			if (!activeUserEntity.isPresent()) {
			return this.buildInvalidSessionResponse();
			} else if (!this.isElevatedUser(activeUserEntity.get())) {
				return this.buildNoPermissionsResponse(
				ViewNames.PRODUCT_LISTING.getRoute());
			}      

			modelAndView.addObject(
			ViewModelNames.IS_ELEVATED_USER.getValue(),
			EmployeeClassification.isElevatedUser(
			activeUserEntity.get().getClassification()));
			
				
			try {
				modelAndView.addObject(
					ViewModelNames.ENTRY.getValue(),
					querySpecificEntry.execute());
			} catch (final Exception e) {
				System.out.println(e.toString());
				modelAndView.addObject(
					ViewModelNames.ERROR_MESSAGE.getValue(),
					e.getMessage());
				modelAndView.addObject(
					ViewModelNames.ENTRY.getValue(),
					(new TransactionEntry()));
			}
		
		
		return modelAndView; 
	}

	@RequestMapping(value = "/{transactionId}/checkout", method = RequestMethod.GET)
	public ModelAndView checkout(
		@PathVariable final UUID transactionId,
		@RequestParam final Map<String, String> queryParameters,
		final HttpServletRequest request
	) {
		submitTransaction.setTransactionId(transactionId);

		ModelAndView modelAndView =
			this.setErrorMessageFromQueryString(
				new ModelAndView(ViewNames.SUCCESS.getViewName()),
				queryParameters);
		
		

			try {
				modelAndView.addObject(
					ViewModelNames.SUCCESS.getValue(),
					submitTransaction.execute());
			} catch (final Exception e) {
				modelAndView.addObject(
					ViewModelNames.ERROR_MESSAGE.getValue(),
					e.getMessage());
				modelAndView.addObject(
					ViewModelNames.SUCCESS.getValue(),
					(new Transaction[0]));
			}

		return modelAndView;
	}

	@RequestMapping(value = "/{transactionId}/cancel", method = RequestMethod.GET)
	public RedirectView cancel(
		@PathVariable final UUID transactionId,
		@RequestParam final Map<String, String> queryParameters,
		final HttpServletRequest request
	) {
		cancelTransaction.setTransactionId(transactionId);
		cancelTransaction.execute();
		return new RedirectView("/mainMenu");
	}


	@Autowired
	private ProductSearchByPartialLookupCode searchByPartialLookup;

	@Autowired 
	private ProductQuery searchById;

	@Autowired
	private AddProductToTransaction addToTransaction;

	@Autowired
	private TransactionCreateCommand createTransaction;

	@Autowired
	private TransactionEntriesQueriedByTransactionId queryEntries;

	@Autowired
	private TransactionEntryQuery querySpecificEntry;

	@Autowired
	private UpdateTransactionPrice updatePrice;

	@Autowired
	private TransactionSummaryQuery summaryQuery;

	@Autowired
	private TransactionSubmission submitTransaction;

	@Autowired
	private TransactionCancel cancelTransaction;
}
