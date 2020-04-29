package edu.uark.registerapp.controllers;

import java.util.Map;
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
import edu.uark.registerapp.commands.transactions.TransactionCreateCommand;
import edu.uark.registerapp.commands.transactions.TransactionEntriesQueriedByTransactionId;
import edu.uark.registerapp.commands.transactions.TransactionEntryQuery;
import edu.uark.registerapp.controllers.enums.ViewModelNames;
import edu.uark.registerapp.controllers.enums.ViewNames;
import edu.uark.registerapp.models.api.Product;
import edu.uark.registerapp.models.api.TransactionEntry;

@Controller
@RequestMapping(value = "/transaction")
public class TransactionRouteController extends BaseRouteController {
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public RedirectView landing(@RequestParam final Map<String, String> queryParameters,
			final HttpServletRequest request) {

		createTransaction.setCashierID(this.getCurrentUser(request).get().getEmployeeId());
		UUID transactionId = createTransaction.execute().getId();
	    return new RedirectView("http://localhost:8080/transaction/" + transactionId);
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

		System.out.println(queryParameters);

		System.out.println(queryParameters.get("searchProduct"));
		
		searchByPartialLookup.setPartialLookupCode(queryParameters.get("searchProduct"));
		
		Product[] products = searchByPartialLookup.execute();
		System.out.println(products);

			try {
				modelAndView.addObject(
					ViewModelNames.PRODUCTS.getValue(),
					products);
			} catch (final Exception e) {
				System.out.println(e.toString());
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

		return new RedirectView("http://localhost:8080/transaction/" + transactionId);	
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
}
