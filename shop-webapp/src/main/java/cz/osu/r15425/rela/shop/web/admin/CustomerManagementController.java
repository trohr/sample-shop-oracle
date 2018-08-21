package cz.osu.r15425.rela.shop.web.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import cz.osu.r15425.rela.shop.domain.EntityNotFoundException;
import cz.osu.r15425.rela.shop.domain.customer.CustomerDto;
import cz.osu.r15425.rela.shop.domain.customer.CustomerManagementService;


@Controller
@RequestMapping(CustomerManagementController.PATH_CUSTOMER_MANAGEMENT)
public class CustomerManagementController {

	static final String PATH_CUSTOMER_MANAGEMENT = "/admin/customers";
	static final String EDIT_PATH(long id) {
		return String.format("%s/%d/%s/",
				PATH_CUSTOMER_MANAGEMENT,
				id,
				"edit"
		);
	}


	// Spring-data JPA repository
	@Autowired CustomerManagementService service;
	

	@ModelAttribute("module")
	public String getModule ()
	{
		return "admin";
	}
	
	
	// 
	// LIST
	// 
	
	/** 1) List all page */
	@GetMapping
	public String getAllCustomers (Model model)
	{
		final List<CustomerDto> customers = service.loadAllCustomers();

		model.addAttribute("customers", customers);

		return "customer-list";
	}
	

	
	// 
	// CREATE
	// 

	/**
	 * 2) GET page with form to create a new customer
	 */
	@GetMapping("/create")
	public String getCreateNewCustomerPage (Model model)
	{
		return "customer-detail";
	}

	/** 3) POST for new customer */
	@PostMapping("/create")
	public String addCustomer (
			CustomerDto content, Model model)
	{
		Assert.notNull(content, "Content cannot be null");
		
		// Store in a DB
		final CustomerDto createdCustomer = service.storeNewCustomer(content);
		
//		// Return the customer model to the view
//		model.addAttribute("newCustomer", createdCustomer);

		return "redirect:"+EDIT_PATH(createdCustomer.getId());
//		return "redirect:"+PATH_CUSTOMER_DEFINITIONS;
	}



	// 
	// EDIT
	// 

	/**
	 * 4) GET page with form to edit an existing customer
	 */
	@GetMapping("/{customerID}/edit")
	public String getEditCustomerPage (
			@PathVariable(name="customerID", required=true) long customerID,
			Model model
			)
			throws EntityNotFoundException // TODO handle the exception using spring-web handler
	{
		// Retrieve
		final CustomerDto customer = service.getCustomer(customerID);
		// Return information to the view
		model.addAttribute("customer", customer);
		return "customer-detail";
	}

	/** 5) POST for updating of the customer */
	@PostMapping("/{customerID}/edit")
	public String updateCustomer (
			@PathVariable(name="customerID", required=true) long customerID,
			CustomerDto content, Model model
			)
			throws EntityNotFoundException // TODO handle the exception using spring-web handler
	{
		Assert.notNull(content, "Content cannot be null");
		
		// Store in a DB
		content.setId(customerID);
		final CustomerDto updatedCustomer = service.updateCustomer(content);

//		// Return the customer model to the view
//		model.addAttribute("updatedCustomer", updatedCustomer);

		return "redirect:"+EDIT_PATH(updatedCustomer.getId());
//		return "redirect:"+PATH_CUSTOMER_DEFINITIONS;
	}



	// 
	// DELETE
	// 
	/** 6) POST for deleting of the customer (in REST should be DELETE) */
	@PostMapping("/{customerID}/delete")
	public String deleteCustomer (
			@PathVariable(name="customerID", required=true) long customerID,
			Model model
			)
			throws EntityNotFoundException // TODO handle the exception using spring-web handler
	{
		// Retrieve
		final CustomerDto customer = service.getCustomer(customerID);
		// Delete
		service.deleteCustomer(customer.getId());
		// Return information to the view
		model.addAttribute("deletedCustomer", customer);
		
		return "redirect:"+PATH_CUSTOMER_MANAGEMENT;
	}

}
