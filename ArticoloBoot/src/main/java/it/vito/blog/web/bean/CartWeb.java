package it.vito.blog.web.bean;

import java.util.LinkedList;
import java.util.List;

import it.vito.blog.db.bean.Cart;
import it.vito.blog.db.bean.CartDetail;


public class CartWeb {

	Integer idCart;
	List<CartDetailWeb> cartDetailWeb;
	
	public CartWeb fromCartToCartWeb(Cart c){
		CartWeb risultato = new CartWeb();
		if (c.getCartDetail() != null)
			for (int i = 0; i < c.getCartDetail().size();i++){
				if (cartDetailWeb==null) cartDetailWeb = new LinkedList<CartDetailWeb>();
				CartDetail cd = c.getCartDetail().get(i);
				CartDetailWeb cdw = new CartDetailWeb();
				cdw.setIdCartDetail(cd.getIdCartDetail());
				cdw.setPrice(cd.getCosto());
				cdw.setQuantita(cd.getQuantita());;
				
			}
		return risultato;
	}
}
