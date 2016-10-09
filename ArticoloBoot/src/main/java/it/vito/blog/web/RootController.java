package it.vito.blog.web;

import it.vito.blog.service.HelloWorldService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

	@Autowired
	private HelloWorldService helloWorldService;

	
	@RequestMapping("/")
	public String onRootAccess() {
		helloWorldService.getHelloMessage();
		logger.debug("Redirecting to /items...");
		return "redirect:/items";
	}
}
