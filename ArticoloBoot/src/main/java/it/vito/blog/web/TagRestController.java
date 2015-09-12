package it.vito.blog.web;

import it.vito.blog.business.GestioneBlog;
import it.vito.blog.db.bean.Tag;
import it.vito.blog.db.dao.TagRepository;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/tags")
public class TagRestController {

	private Logger logger = LoggerFactory.getLogger(TagRestController.class);
	
	@Autowired
	GestioneBlog gestioneBlog;
	

	@RequestMapping(method=RequestMethod.GET)
	public List<Tag> list() {
		logger.debug("Get lista tag...");
		List<Tag> l = gestioneBlog.getAllTag();
		return l;
	}
	


}
