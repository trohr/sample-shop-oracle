package cz.osu.r15425.rela.shop.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/")
public class WelcomeController {
	
	static Logger LOGGER = LoggerFactory.getLogger(WelcomeController.class);
	

	// 1) Welcome page
	@GetMapping({"/","index.html", "welcome"})
	public String getWelcomePage ()
	{
		LOGGER.trace("WELCOME");
		LOGGER.debug("WELCOME");
		LOGGER.info("WELCOME");
		LOGGER.warn("WELCOME");
		LOGGER.error("WELCOME");
		return "welcome";
	}

}
