package it.vito.blog.db.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="bg_cart_detail_vw")
public class CartDetailVW implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = 4224673751798520212L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_cart_detail")
	private Integer idCartDetail;

 	@Column(name="id_cart")
	private Integer idCart;
	
 	@Column(name="utente")
	private String utente;
	
	@Column(name="id_item")
	private Integer idItem;
	
	@Column(name="quantita")
	private int quantita;
	
	@Column(name="price")
	private Float price;
	
	@Column(name="descrizione")
	private String descrizione;
	

	public Integer getIdItem() {
		return idItem;
	}

	public void setIdItem(Integer idItem) {
		this.idItem = idItem;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public Integer getIdCartDetail() {
		return idCartDetail;
	}

	public void setIdCartDetail(Integer idCartDetail) {
		this.idCartDetail = idCartDetail;
	}

	public Integer getIdCart() {
		return idCart;
	}

	public void setIdCart(Integer idCart) {
		this.idCart = idCart;
	}

	public String getUtente() {
		return utente;
	}

	public void setUtente(String utente) {
		this.utente = utente;
	}

	public int getQuantita() {
		return quantita;
	}

	public void setQuantita(int quantita) {
		this.quantita = quantita;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "CartDetailVW [idCartDetail=" + idCartDetail + ", idCart=" + idCart + ", utente=" + utente + ", idItem="
				+ idItem + ", quantita=" + quantita + ", price=" + price + ", descrizione=" + descrizione + "]";
	}

}
