package edu.uark.registerapp.controllers;

import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import edu.uark.registerapp.commands.transactions.ProductSearchByPartialLookupCode;
import edu.uark.registerapp.controllers.enums.ViewModelNames;
import edu.uark.registerapp.controllers.enums.ViewNames;
import edu.uark.registerapp.models.api.Product;
import edu.uark.registerapp.models.entities.ActiveUserEntity;
import edu.uark.registerapp.models.enums.EmployeeClassification;

@Controller
@RequestMapping(value = "/transaction")
public class TransactionRouteController extends BaseRouteController {
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView landing(
		@RequestParam final Map<String, String> queryParameters,
		final HttpServletRequest request
	) {
        ModelAndView modelAndView =
			this.setErrorMessageFromQueryString(
				new ModelAndView(ViewNames.TRANSACTION.getViewName()),
				queryParameters);

			return modelAndView;
	}

	@RequestMapping(value = "/searchForProduct", method = RequestMethod.GET)
	public ModelAndView search(
		@RequestParam final Map<String, String> queryParameters,
		final HttpServletRequest request
	) {
        ModelAndView modelAndView =
			this.setErrorMessageFromQueryString(
				new ModelAndView(ViewNames.SEARCH.getViewName()),
				queryParameters);
	
		return modelAndView;
	}
	
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public ModelAndView searched(
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

	@Autowired
	private ProductSearchByPartialLookupCode searchByPartialLookup;
}
