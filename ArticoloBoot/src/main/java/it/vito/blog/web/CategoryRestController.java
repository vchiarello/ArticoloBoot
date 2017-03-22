package it.vito.blog.web;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import it.vito.blog.business.GestioneBlog;
import it.vito.blog.web.bean.CategoryWeb;

@RestController
@RequestMapping("/rest/category")
public class CategoryRestController {

	private Logger logger = LoggerFactory.getLogger(CartRestController.class);
	
	@Autowired
	GestioneBlog gestioneBlog;

	@RequestMapping(method=RequestMethod.GET)
	public List<CategoryWeb>getAllCatetory(){
		logger.info("Get lista all category...");
		return gestioneBlog.getCategory();
	}
}
