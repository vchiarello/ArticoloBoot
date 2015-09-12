package it.vito.blog.web;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ItemController {

	private Logger logger = LoggerFactory.getLogger(ItemController.class);
	

	@RequestMapping(value="/items", method=RequestMethod.GET)
	public String list(Model model) {
		return "items/homeItem";
	}


}
