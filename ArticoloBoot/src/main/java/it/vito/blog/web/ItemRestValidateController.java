package it.vito.blog.web;

import java.util.LinkedHashMap;

import it.vito.blog.business.GestioneBlog;
import it.vito.blog.web.bean.ItemWeb;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

//pippo
@RestController
@RequestMapping("/rest/validaItemWeb")
public class ItemRestValidateController {

	private Logger logger = LoggerFactory.getLogger(ItemRestValidateController.class);
	
	@Autowired
	GestioneBlog gestioneBlog;

	
	@RequestMapping(method=RequestMethod.POST)
	public ItemWeb validatePost(@RequestBody @Valid LinkedHashMap<String, Object> daValidare) {
		logger.debug("validatzione di ItemWeb...");
		ItemWeb risultato = new ItemWeb(daValidare);
		logger.debug("validazione effettuata");
		return risultato;
	}

	@RequestMapping(value="/{itemWeb}", method=RequestMethod.GET)
	public String validateGet(@PathVariable("itemWeb") String itemWeb) {
		logger.debug("validatzione di ItemWeb...");
		logger.debug("itemWeb: " + itemWeb);
		logger.debug("validazione effettuata");
		return itemWeb;
	}

}
