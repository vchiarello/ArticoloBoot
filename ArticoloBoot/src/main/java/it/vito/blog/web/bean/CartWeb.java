package it.vito.blog.web.bean;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import it.vito.blog.db.bean.Cart;
import it.vito.blog.db.bean.CartDetail;
import it.vito.blog.db.bean.CartDetailVW;


public class CartWeb implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8871262538483045161L;
	Integer idCart;
	List<CartDetailWeb> cartDetailWeb;
	
	public Integer getIdCart() {
		return idCart;
	}

	public void setIdCart(Integer idCart) {
		this.idCart = idCart;
	}

	public List<CartDetailWeb> getCartDetailWeb() {
		return cartDetailWeb;
	}

	public void setCartDetailWeb(List<CartDetailWeb> cartDetailWeb) {
		this.cartDetailWeb = cartDetailWeb;
	}

	public void fromCartToCartWeb(Cart c){
		if (c.getCartDetail() != null){
			this.idCart = c.getIdCart();
			for (int i = 0; i < c.getCartDetail().size();i++){
				if (cartDetailWeb==null) cartDetailWeb = new LinkedList<CartDetailWeb>();
				CartDetail cd = c.getCartDetail().get(i);
				CartDetailWeb cdw = new CartDetailWeb();
				cdw.setIdCartDetail(cd.getIdCartDetail());
				cdw.setIdItem(cd.getItem().getId());
				cdw.setDescrizione(cd.getItem().getNome());
				cdw.setPrice(cd.getCosto());
				cdw.setQuantita(cd.getQuantita());;
				this.cartDetailWeb.add(cdw);
				
			}
		}	
	}
	
	public void fromCartDetailVWToCartWeb (List<CartDetailVW> l){
		if (l==null)return;
		for (int i = 0; i < l.size(); i++){
			CartDetailVW cdvw = l.get(i);
			this.idCart = cdvw.getIdCart();
			if (cartDetailWeb==null) cartDetailWeb = new LinkedList<CartDetailWeb>();
			CartDetailWeb cdw = new CartDetailWeb();
			cdw.setIdCartDetail(cdvw.getIdCartDetail());
			cdw.setIdItem(cdvw.getIdItem());
			cdw.setPrice(cdvw.getPrice());
			cdw.setQuantita(cdvw.getQuantita());
			cdw.setDescrizione(cdvw.getDescrizione());
			this.cartDetailWeb.add(cdw);
		}
	}

	@Override
	public String toString() {
		return "CartWeb [idCart=" + idCart + ", cartDetailWeb=" + cartDetailWeb + "]";
	}
	
	
}
