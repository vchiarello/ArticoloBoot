package it.vito.blog.business;

import java.util.List;

import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

import it.vito.blog.web.bean.CartDetailWeb;
import it.vito.blog.web.bean.CartWeb;

@Component("gestioneCart")
@EnableAspectJAutoProxy
public class GestioneCart {

	public CartWeb addToCart(Integer itemId, String itemName, String utente){
		return null;
	}
	
	public CartWeb removeFromCart(Integer itemId, String itemName, String utente){
		return null;
	}
	
	public CartWeb updateCart(Integer itemId, String itemName, String utente){
		return null;
	}
	
	public CartWeb getCart(String utente){
		return null;
	}
	
	public CartWeb saveCart(CartWeb cartWeb){
		return null;
	}
	
	public List<CartWeb> getAllCart(){
		return null;
	}
	
	public void toOrder(CartWeb cartWeb){
		
	}
	
}
