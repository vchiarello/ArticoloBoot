package it.vito.blog.web;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import it.vito.blog.business.GestioneCart;
import it.vito.blog.web.bean.CartWeb;

@RestController
@RequestMapping("/rest/cart")
public class CartRestController {

	private Logger logger = LoggerFactory.getLogger(CartRestController.class);
	
	@Autowired
	GestioneCart gestioneCart;

	
	@RequestMapping( method=RequestMethod.GET)
	public CartWeb get() {
		logger.debug("Get cart " + SecurityContextHolder.getContext().getAuthentication().getName());
		CartWeb item = gestioneCart.getCart(SecurityContextHolder.getContext().getAuthentication().getName());
		
		return item;
	}
	
	
	@RequestMapping(value="/{id}/{name}", method=RequestMethod.POST)
	public CartWeb add(@PathVariable("id") int itemId,@PathVariable("name") String itemName, Locale locale) {
		CartWeb cart = gestioneCart.addToCart(itemId,itemName, SecurityContextHolder.getContext().getAuthentication().getName());
		return cart;
	}

	@RequestMapping(value="/{id}/{name}", method=RequestMethod.PUT)
	public CartWeb update(@PathVariable("id") int itemId, @PathVariable("name") String itemName,Locale locale) {
		CartWeb cart = gestioneCart.updateCart(itemId,itemName, SecurityContextHolder.getContext().getAuthentication().getName());
		return cart;
	}

	@RequestMapping(value="/{id}/{name}", method=RequestMethod.DELETE)
	public CartWeb delete(@PathVariable("id") int itemId, @PathVariable("name") String itemName) {
		CartWeb cart = gestioneCart.removeFromCart(itemId,itemName, SecurityContextHolder.getContext().getAuthentication().getName());
		return cart;
	}

}
