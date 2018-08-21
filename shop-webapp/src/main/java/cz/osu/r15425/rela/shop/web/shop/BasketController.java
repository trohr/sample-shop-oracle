package cz.osu.r15425.rela.shop.web.shop;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import cz.osu.r15425.rela.shop.domain.basket.BasketDto;
import cz.osu.r15425.rela.shop.domain.basket.BasketService;
import cz.osu.r15425.rela.shop.web.interceptors.LastModelAndViewInterceptor;


@Controller
@RequestMapping(BasketController.PATH_SHOP_BASKET)
public class BasketController {

	static final String PATH_SHOP_BASKET = ShopController.PATH_SHOP+"/basket";


	// Spring-data JPA repository
	@Autowired BasketService service;
	

	@ModelAttribute("module")
	public String getModule ()
	{
		return "basket";
	}

	
	// 
	// LIST
	// 
	
	/** 1) List all page */
	@GetMapping
	public String getBasketView (
			Model model,
			HttpSession session
			)
	{
		BasketDto basketData = getCurrentSessionBasket(session);

		model.addAttribute("basket", basketData);

		return "basket-view";
	}
	

	
	// 
	// ADD TO BASKET
	// 

	/**
	 * 2) ADD PRODUCT TO BASKET
	 */
	@PostMapping("/add")
	public String addProductToBasket (
			@RequestParam (name="productId", required=true) long productId,
			HttpSession session
			)
	{
		BasketDto basket = getCurrentSessionBasket(session);
		int quantity = 1;
		
		service.addItemToBasket (basket.getId().longValue(), productId, quantity);
		
		return "redirect:"+PATH_SHOP_BASKET;
		/*
		Optional<ModelAndView> prevModel = getPreviousModelAndViewBySession(session);
		if (prevModel.isPresent())
		{
			return prevModel.get();
			//return prevModel;
		}
		else
		{
			return new ModelAndView("redirect:"+PATH_SHOP_BASKET);
		}
//		return "redirect:"+
	 	*/
	}


	// 
	// REMOVE FROM BASKET
	// 

	/**
	 * 3) REMOVE LINE FROM BASKET
	 */
	@PostMapping("/remove")
	public String removeLineFromBasket (
			@RequestParam (name="productId", required=true) long productId,
			@RequestParam (name="lineNo", required=true) int lineNo,
			HttpSession session
			)
	{
		BasketDto basket = getCurrentSessionBasket(session);
		
		service.removeItemFromBasket(basket.getId().longValue(), productId, lineNo);
		
		return "redirect:"+PATH_SHOP_BASKET;
	}



	private BasketDto getCurrentSessionBasket (HttpSession session)
	{
		Optional<String> basketId = getBasketIdFromSession(session);
		
		BasketDto basketData = null;
		if (basketId.isPresent())
		{
			basketData = service.findValidBasketByUuid(basketId.get()).orElse(null);
			
			// if not found, then let's generate a new basket id and create the basket
			if (basketData == null) {
				basketId = Optional.empty();
			}
		}

		if (!basketId.isPresent())
		{
			// create basket
			basketData = new BasketDto();
			basketData.setCustomerUri(getSomeCustomerUri(session));
			basketData.setLastUsed(LocalDateTime.now());
			basketData.setUuid(UUID.randomUUID().toString());
			basketData.setValidUntil(basketData.getLastUsed().plus(Duration.ofMinutes(120)));
			
			basketData = service.storeNewBasket(basketData);
			
			basketId = Optional.of(basketData.getUuid());
			storeBasketIdToSession(session, basketId.get());
		}
		return basketData;
	}

	/**
	 * 
     * @exception IllegalStateException
     *                if this method is called on an invalidated session
	 * @param session
	 * @return
	 */
	protected String getSomeCustomerUri(HttpSession session)
	{
		String customerUri = (String) session.getAttribute("Shop.CustomerURI");
		if (customerUri == null) {
			// generate customer id
			customerUri = "online://shop?sessionId="+session.getId();
			session.setAttribute("Shop.CustomerURI", customerUri);
		}
		return customerUri;
	}
	
	protected Optional<String> getBasketIdFromSession (HttpSession session)
	{
		return Optional.ofNullable((String) session.getAttribute("Shop.BasketID"));
	}

	protected void storeBasketIdToSession (HttpSession session, String basketID)
	{
		Assert.notNull(basketID, "BasketID cannot be null when storing into session");
		session.setAttribute("Shop.BasketID", basketID);
	}

	protected Optional<ModelAndView> getPreviousModelAndViewBySession(HttpSession session)
	{
		ModelAndView mv = (ModelAndView)session.getAttribute(LastModelAndViewInterceptor.LAST_MODEL_VIEW_ATTRIBUTE);
		return Optional.ofNullable(mv);
	}
	/**
	* Returns the viewName to return for coming back to the sender url
	*
	* @param request Instance of {@link HttpServletRequest} or use an injected instance
	* @return Optional with the view name. Recomended to use an alternativa url with
	* {@link Optional#orElse(java.lang.Object)}
	*/
	protected Optional<String> getPreviousPageByRequest(HttpServletRequest request)
	{
	   return Optional.ofNullable(request.getHeader("Referer")).map(requestUrl -> "redirect:" + requestUrl);
	}


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
