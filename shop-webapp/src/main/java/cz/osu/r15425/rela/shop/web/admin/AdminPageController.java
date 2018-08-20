package cz.osu.r15425.rela.shop.web.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import cz.osu.r15425.rela.shop.domain.product.ProductsDefinitionService;


@Controller
@RequestMapping(AdminPageController.PATH_ADMIN_PAGE)
public class AdminPageController {

	static final String PATH_ADMIN_PAGE = "/admin";


	// Spring-data JPA repository
	@Autowired ProductsDefinitionService service;
	
	
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
	public String getAdminPage ()
	{
		return "admin-page";
	}
	
}
