package it.vito.blog.business;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

import it.vito.blog.db.bean.Cart;
import it.vito.blog.db.bean.CartDetail;
import it.vito.blog.db.bean.CartDetailVW;
import it.vito.blog.db.bean.Item;
import it.vito.blog.db.dao.CartDetailRepository;
import it.vito.blog.db.dao.CartDetailVWRepository;
import it.vito.blog.db.dao.CartRepository;
import it.vito.blog.web.bean.CartWeb;

@Component("gestioneCart")
@EnableAspectJAutoProxy
public class GestioneCart {

	@Autowired
	CartRepository cartRepository;
	
	@Autowired
	CartDetailRepository cartDetailRepository;
	
	@Autowired
	CartDetailVWRepository cartDetailVWRepository;
	
	public CartWeb addToCart(Integer itemId, String itemName, String utente){
		//se si tratta di un utente loggato il carrello si salva sempre sul db in modo che in 
		//sessioni successive si prende il carrello salvato
		//per utenti non loggati si può pensare di tenere il carrello solo in memoria fino al login

		
		Cart c = cartRepository.findByUtente(utente);
		Item item = new Item();
		item.setId(itemId);
		//non c'è alcun carrello salvato per l'utente
		if (c==null){
			c = initCart(utente);
			initCartDetail(item, c, utente);
		//C'è già un carrello salvato per l'utente	
		}else{
			c.setDataModifica(new Date());
			cartRepository.save(c);
			List<CartDetail> lc = cartDetailRepository.findByCartAndItem(c, item);
			//il prodotto non era presente nel carrello
			if (lc.size() == 0)
				initCartDetail(item, c, utente);
			//il prodotto non era presente nel carrello si aggiunge la quantita'
			else{
				CartDetail cd = lc.get(0);
				cd.setQuantita(cd.getQuantita()+1);
				cd.setDataModifica(new Date());
				cartDetailRepository.save(cd);
			}
		}
		return getCart(utente);
	}
	
	private Cart initCart(String utente){
		Cart cart = new Cart();
		cart.setDataInserimento(new Date());
		cart.setUtente(utente);
		cartRepository.save(cart);
		return cart;
	}
	
	private CartDetail initCartDetail(Item item, Cart c, String utente){
		CartDetail cd = new CartDetail();

		cd.setCart(c);
		cd.setItem(item);

		cd.setDataInserimento(new Date());
		cd.setQuantita(1);
		cartDetailRepository.save(cd);
		return cd;
	}
	
	public CartWeb removeFromCart(Integer idCartDetail, String utente){
		Cart c = cartRepository.findByUtente(utente);

		//non c'è alcun carrello salvato per l'utente, non viene cancellata alcuna riga
		if (c==null){
			c = initCart(utente);
		//C'è già un carrello salvato per l'utente	
		}else{
			c.setDataModifica(new Date());
			cartRepository.save(c);
			CartDetail cd = cartDetailRepository.findByIdCartDetail(idCartDetail);
			
			if (cd != null){
				cartDetailRepository.delete(cd.getIdCartDetail());
			}
		}
		return getCart(utente);
	}
	
	public CartWeb updateCart(CartWeb cart, String utente){
		Cart c = cartRepository.findByUtente(utente);
		for (int i = 0; i < cart.getCartDetailWeb().size(); i++){
			for (int j = 0; j < c.getCartDetail().size(); j++){
				if (cart.getCartDetailWeb().get(i).getIdCartDetail()==c.getCartDetail().get(j).getIdCartDetail()){
					c.getCartDetail().get(j).setQuantita(cart.getCartDetailWeb().get(i).getQuantita());
					cartDetailRepository.save(c.getCartDetail().get(j));
					break;
				}
			}
		}
		c.setDataModifica(new Date());

		return getCart(utente);
	}
	
	public CartWeb getCart(String utente){
		List<CartDetailVW> l = cartDetailVWRepository.findByUtente(utente);
		CartWeb risultato = new CartWeb();
		risultato.fromCartDetailVWToCartWeb(l);
		return risultato;
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
