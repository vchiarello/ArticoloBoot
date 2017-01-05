package it.vito.blog.web.bean;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import it.vito.blog.db.bean.Cart;
import it.vito.blog.db.bean.CartDetail;


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
				cdw.setPrice(cd.getCosto());
				cdw.setQuantita(cd.getQuantita());;
				this.cartDetailWeb.add(cdw);
				
			}
		}	
	}

	@Override
	public String toString() {
		return "CartWeb [idCart=" + idCart + ", cartDetailWeb=" + cartDetailWeb + "]";
	}
	
	
}
