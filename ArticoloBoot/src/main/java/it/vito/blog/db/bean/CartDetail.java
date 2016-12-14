package it.vito.blog.db.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="bg_cart_detail")
public class CartDetail implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = 4224673751798520212L;

	@EmbeddedId
	private CartDetailId idCartDetail;
	
	@ManyToOne
	@JoinColumns({@JoinColumn(name="id_item", referencedColumnName="id_item")})
	private Item item;
	
	
	@Column(name="quantita")
	private int quantita;
	
	@Column(name="costo")
	private Float costo;
	
	@Column(name="data_inserimento")
	private Date dataInserimento;
	
	@Column(name="data_modifica")
	private Date dataModifica;

	
	public CartDetailId getIdCartDetail() {
		return idCartDetail;
	}

	public void setIdCartDetail(CartDetailId idCartDetail) {
		this.idCartDetail = idCartDetail;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
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

	public Date getDataInserimento() {
		return dataInserimento;
	}

	public void setDataInserimento(Date dataInserimento) {
		this.dataInserimento = dataInserimento;
	}

	public Date getDataModifica() {
		return dataModifica;
	}

	public void setDataModifica(Date dataModifica) {
		this.dataModifica = dataModifica;
	}

	@Override
	public String toString() {
		return "CartDetail [idCartDetail=" + idCartDetail + ", item=" + item.getId() + ", quantita="
				+ quantita + ", costo=" + costo + ", dataInserimento=" + dataInserimento + ", dataModifica="
				+ dataModifica + "]";
	}

}
