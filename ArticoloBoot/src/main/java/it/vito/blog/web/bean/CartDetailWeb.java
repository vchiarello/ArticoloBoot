package it.vito.blog.web.bean;

import java.io.Serializable;

public class CartDetailWeb implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2694748867549797453L;
	private Integer idCartDetail;
	private Integer idItem;
	private String descrizione;
	private Integer quantita;
	private Float price;
	public Integer getIdCartDetail() {
		return idCartDetail;
	}
	public void setIdCartDetail(Integer idCartDetail) {
		this.idCartDetail = idCartDetail;
	}
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
	public Integer getQuantita() {
		return quantita;
	}
	public void setQuantita(Integer quantita) {
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
		return "CartDetailWeb [idCartDetail=" + idCartDetail + ", idItem=" + idItem + ", descrizione=" + descrizione
				+ ", quantita=" + quantita + ", price=" + price + "]";
	}
	
}
