package it.vito.blog.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * The only purpose of this root controller is to redirect the user on root context 
 * access to the right home page
 * 
 * @author Biju Kunjummen
 *
 */
@Controller
public class RootController {
	private Logger logger = LoggerFactory.getLogger(RootController.class);
	@RequestMapping("/")
	public String onRootAccess() {
		logger.debug("Redirecting to /items...");
		return "redirect:/items";
	}
}
