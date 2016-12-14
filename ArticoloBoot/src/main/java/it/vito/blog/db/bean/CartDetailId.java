package it.vito.blog.db.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;

@Embeddable
public class CartDetailId implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1376434457886836663L;

 	@ManyToOne
	@JoinColumns({@JoinColumn(name="id_carrello", referencedColumnName="id_carrello")})
	private Cart cart;
	
	@Column(name="progressivo")
	private Integer progressivo;


	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}

	public Integer getProgressivo() {
		return progressivo;
	}

	public void setProgressivo(Integer progressivo) {
		this.progressivo = progressivo;
	}

	@Override
	public String toString() {
		return "CartDetailId [Carrello=" + cart.getIdCart() + ", progressivo=" + progressivo + "]";
	}
	
}
