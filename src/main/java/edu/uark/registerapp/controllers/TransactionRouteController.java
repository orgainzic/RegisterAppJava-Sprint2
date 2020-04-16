package edu.uark.registerapp.controllers;

import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import edu.uark.registerapp.controllers.enums.ViewModelNames;
import edu.uark.registerapp.controllers.enums.ViewNames;
import edu.uark.registerapp.models.entities.ActiveUserEntity;
import edu.uark.registerapp.models.enums.EmployeeClassification;

@Controller
@RequestMapping(value = "/transaction")
public class TransactionRouteController extends BaseRouteController {
	@RequestMapping(method = RequestMethod.GET)
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

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView searched(
		@RequestParam final Map<String, String> queryParameters,
		final HttpServletRequest request
	) {
        ModelAndView modelAndView =
			this.setErrorMessageFromQueryString(
				new ModelAndView(ViewNames.TRANSACTION.getViewName()),
				queryParameters);

		System.out.println(queryParameters);
		/*
				try {
				modelAndView.addObject(
					//ViewModelNames.PRODUCTS.getValue(),  change to checkout viewmodel or use this one
					//this.productsQuery.execute());   change to checking the cart command
			} catch (final Exception e) {
				modelAndView.addObject(
					ViewModelNames.ERROR_MESSAGE.getValue(),
					e.getMessage());
				modelAndView.addObject(
					//ViewModelNames.PRODUCTS.getValue(), change to checkout viewmodel or use this one
					//(new Product[0]));  change to checkout model or use this one
			}
		*/
		return modelAndView;
	}
}
