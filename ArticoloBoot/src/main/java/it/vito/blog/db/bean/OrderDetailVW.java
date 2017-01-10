package it.vito.blog.db.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="bg_order_detail_vw")
public class OrderDetailVW implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = 2200413938844393461L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_order_detail")
	private Integer idOrderDetail;
	
	@Column(name="id_Order")
	private Integer idOrder;

	@Column(name="utente")
	private String utente;

	@Column(name="id_item")
	private Integer idItem;
	
	@Column(name="quantita")
	private int quantita;
	
	@Column(name="costo")
	private Float costo;
	
	@Column(name="descrizione")
	private String descrizione;
	
	public Integer getIdOrderDetail() {
		return idOrderDetail;
	}

	public void setIdOrderDetail(Integer idOrderDetail) {
		this.idOrderDetail = idOrderDetail;
	}

	public String getUtente() {
		return utente;
	}

	public void setUtente(String utente) {
		this.utente = utente;
	}

	public Integer getIdOrder() {
		return idOrder;
	}

	public void setIdOrder(Integer idOrder) {
		this.idOrder = idOrder;
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

	public int getQuantita() {
		return quantita;
	}

	public void setQuantita(int quantita) {
		this.quantita = quantita;
	}

	public Float getCosto() {
		return costo;
	}

	public void setCosto(Float costo) {
		this.costo = costo;
	}


	@Override
	public String toString() {
		return "OrderDetailVW [idOrderDetail=" + idOrderDetail + ", idOrder=" + idOrder + ", utente=" + utente + ", idItem="
				+ idItem + ", quantita=" + quantita + ", costo=" + costo + ", descrizione=" + descrizione + "]";
	}

}
