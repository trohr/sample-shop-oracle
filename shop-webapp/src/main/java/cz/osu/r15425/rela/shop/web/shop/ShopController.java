package cz.osu.r15425.rela.shop.web.shop;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import cz.osu.r15425.rela.shop.domain.product.ProductDefinitionDto;
import cz.osu.r15425.rela.shop.domain.product.ProductsDefinitionService;
import cz.osu.r15425.rela.shop.domain.sorting.OrderBy;


@Controller
@RequestMapping(ShopController.PATH_SHOP)
public class ShopController {

	static final String PATH_SHOP = "/shop";

	// Spring-data JPA repository
	@Autowired ProductsDefinitionService service;
	
	@ModelAttribute("module")
	public String getModule ()
	{
		return "shop";
	}
	
	
	// 
	// LIST
	// 
	
	/** 1) List all page */
	@GetMapping
	public String getAllProductDefinitions (
			@RequestParam(name = "priceOrdering", required=false) String priceOrdering,
			@RequestParam(name = "searchByName", required=false) String searchByName,
			Model model)
	{
		if (!StringUtils.hasText(searchByName)) {
			searchByName = null;
		}
		final Optional<OrderBy> priceOrderBy = getOrderingEnum(priceOrdering).map(dir -> new OrderBy("unitPrice", dir));
		
		final OrderBy[] orderBy = Stream.of (priceOrderBy)
				.filter(Optional::isPresent)
				.map(Optional::get)
				.toArray(n -> new OrderBy[n]);
		
		final List<ProductDefinitionDto> products;
		if (StringUtils.hasText(searchByName))
		{
			products = service.loadProductsByName(searchByName, orderBy);
		}
		else
		{
			products = service.loadAllProducts(orderBy);
		}

		model.addAttribute("products", products);
		if (orderBy.length > 0) {
			model.addAttribute("orderBy", orderBy);
			
			Arrays.stream(orderBy)
				.map((OrderBy o) -> new String[] {o.getProperty()+"Ordering", o.getDirection().toString()})
				.forEach(prop -> model.addAttribute(prop[0], prop[1]));
		}
		if (searchByName != null) {
			model.addAttribute("searchByName", searchByName);
		}

		return "shop";
	}
	
	static Optional<OrderBy.Direction> getOrderingEnum(String ordering)
	{
		if (ordering == null) {
			return Optional.empty();
		}
		try {
			return Optional.of(
					OrderBy.Direction.valueOf(
							ordering.toUpperCase(Locale.ROOT))
			);
		} catch (Exception e) {
			return Optional.empty();
		}
	}


	
	// 
	// ADD TO CART
	// 
//
//	/**
//	 * 2) ADD PRODUCT TO CART
//	 */
//	@PostMapping("cart/add")
//	public ModelAndView addProductToCart (
//			@RequestParam (name="productId", required=true) long productId,
//			HttpSession session
//			)
//	{
//		String cartId = getCartId(session);
//		
//		
//		Optional<ModelAndView> prevModel = getPreviousModelAndViewBySession(session);
//		if (prevModel.isPresent())
//		{
//			return prevModel.get();
//			//return prevModel;
//		}
//		else
//		{
//			return new ModelAndView("redirect:"+PATH_SHOP);
//		}
////		return "redirect:"+
//	}
//
//	protected String getCartId (HttpSession session)
//	{
//		String cartId = (String) session.getAttribute("Shop.CartID");
//		if (cartId == null) {
//			// generate cart id
//			cartId = "fake-cart-id";
//			session.setAttribute("Shop.CartID", cartId);
//		}
//		return cartId;
//	}
//
//	protected Optional<ModelAndView> getPreviousModelAndViewBySession(HttpSession session)
//	{
//		ModelAndView mv = (ModelAndView)session.getAttribute(LastModelAndViewInterceptor.LAST_MODEL_VIEW_ATTRIBUTE);
//		return Optional.ofNullable(mv);
//	}
//	/**
//	* Returns the viewName to return for coming back to the sender url
//	*
//	* @param request Instance of {@link HttpServletRequest} or use an injected instance
//	* @return Optional with the view name. Recomended to use an alternativa url with
//	* {@link Optional#orElse(java.lang.Object)}
//	*/
//	protected Optional<String> getPreviousPageByRequest(HttpServletRequest request)
//	{
//	   return Optional.ofNullable(request.getHeader("Referer")).map(requestUrl -> "redirect:" + requestUrl);
//	}


//	/**
//	 * 2) GET page with form to create a new product
//	 */
//	@GetMapping("/create")
//	public String getCreateNewProductPage (Model model)
//	{
//		return "product-definition-detail";
//	}
//
//	/** 3) POST for new product */
//	@PostMapping("/create")
//	public String addProductDefinition (
//			ProductDefinitionDto content, Model model)
//	{
//		Assert.notNull(content, "Content cannot be null");
//		
//		// Store in a DB
//		final ProductDefinitionDto createdProduct = service.storeNewProduct(content);
//		
////		// Return the product model to the view
////		model.addAttribute("newProduct", createdProduct);
//
//		return "redirect:"+EDIT_PATH(createdProduct.getId());
////		return "redirect:"+PATH_PRODUCT_DEFINITIONS;
//	}
//
//
//
//	// 
//	// EDIT
//	// 
//
//	/**
//	 * 4) GET page with form to edit an existing product
//	 */
//	@GetMapping("/{productID}/edit")
//	public String getEditProductPage (
//			@PathVariable(name="productID", required=true) long productID,
//			Model model
//			)
//			throws EntityNotFoundException // TODO handle the exception using spring-web handler
//	{
//		// Retrieve
//		final ProductDefinitionDto product = service.getProduct(productID);
//		// Return information to the view
//		model.addAttribute("product", product);
//		return "product-definition-detail";
//	}
//
//	/** 5) POST for updating of the product */
//	@PostMapping("/{productID}/edit")
//	public String updateProductDefinition (
//			@PathVariable(name="productID", required=true) long productID,
//			ProductDefinitionDto content, Model model
//			)
//			throws EntityNotFoundException // TODO handle the exception using spring-web handler
//	{
//		Assert.notNull(content, "Content cannot be null");
//		
//		// Store in a DB
//		content.setId(productID);
//		final ProductDefinitionDto updatedProduct = service.updateProduct(content);
//
////		// Return the product model to the view
////		model.addAttribute("updatedProduct", updatedProduct);
//
//		return "redirect:"+EDIT_PATH(updatedProduct.getId());
////		return "redirect:"+PATH_PRODUCT_DEFINITIONS;
//	}

}
