package it.vito.blog.business;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

import it.vito.blog.db.bean.Cart;
import it.vito.blog.db.bean.CartDetail;
import it.vito.blog.db.bean.CartDetailId;
import it.vito.blog.db.bean.Item;
import it.vito.blog.db.dao.CartDetailRepository;
import it.vito.blog.db.dao.CartRepository;
import it.vito.blog.web.bean.CartWeb;

@Component("gestioneCart")
@EnableAspectJAutoProxy
public class GestioneCart {

	@Autowired
	CartRepository cartRepository;
	
	@Autowired
	CartDetailRepository cartDetailRepository;
	
	public CartWeb addToCart(Integer itemId, String itemName, String utente){
		//si prende il carrello associato all'utente
		Cart c = cartRepository.findByUtente(utente);
		if (c==null){
			Cart cart = new Cart();
			//cart.setIdCart(new Integer(1));
			cart.setDataInserimento(new Date());
			cart.setUtente(utente);
			//cart.setDataModifica(new Date());
			cartRepository.save(cart);
			CartDetail cd = new CartDetail();
			CartDetailId cdId =new CartDetailId();
			cdId.setCart(cart);
			cdId.setProgressivo(1);
			cd.setIdCartDetail(cdId);
			cd.setDataInserimento(new Date());
			Item item = new Item();
			item.setId(itemId);
			cd.setItem(item);
			cd.setQuantita(1);
			cartDetailRepository.save(cd);
		}
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
