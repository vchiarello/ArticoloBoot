package it.vito.blog.web;

import it.vito.blog.business.GestioneBlog;
import it.vito.blog.web.bean.Option;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/property")
public class PropertyRestController {

	private Logger logger = LoggerFactory.getLogger(PropertyRestController.class);
	
	@Autowired
	GestioneBlog gestioneBlog;
	

	@RequestMapping(value="/{nome}", method=RequestMethod.GET)
	public List<Option> list(@PathVariable("nome") String nomeProprieta) {
		logger.debug("Get lista valori proprieta "+nomeProprieta + "...");
//		List<Option> l = gestioneBlog.getAllTag();
		return null;
	}
	


}
