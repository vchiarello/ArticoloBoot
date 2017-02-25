package it.vito.blog.web;

import java.util.Locale;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.WebUtils;

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
	public CartWeb add(@PathVariable("id") int itemId,@PathVariable("name") String itemName, Locale locale, HttpServletRequest request) {

		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		CartWeb cart = null;
		if (username == null){
			HttpSession sessione = request.getSession();
			CartWeb c = (CartWeb)sessione.getAttribute("SessionCart");
			cart = gestioneCart.addToSessionCart(itemId,itemName,c);
		}else
			cart = gestioneCart.addToCart(itemId,itemName, SecurityContextHolder.getContext().getAuthentication().getName());
		return cart;
	}

	@RequestMapping( method=RequestMethod.PUT)
	public CartWeb update(@RequestBody @Valid CartWeb cart, Locale locale) {
		CartWeb cart1 = gestioneCart.updateCart(cart, SecurityContextHolder.getContext().getAuthentication().getName());
		return cart1;
	}

	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public CartWeb delete(@PathVariable("id") int idCartDetail) {
		CartWeb cart = gestioneCart.removeFromCart(idCartDetail, SecurityContextHolder.getContext().getAuthentication().getName());
		return cart;
	}

}
