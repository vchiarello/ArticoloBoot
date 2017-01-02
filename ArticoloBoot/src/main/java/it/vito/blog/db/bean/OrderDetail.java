package it.vito.blog.db.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

//@Entity
@Table(name="bg_order_detail")
public class OrderDetail implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = -5744588234200152078L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_order_detail")
	private Integer idOrderDetail;
	
	@ManyToOne
	@JoinColumns({@JoinColumn(name="id_Order", referencedColumnName="id_Order")})
	private Order order;

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
	

	public Integer getIdOrderDetail() {
		return idOrderDetail;
	}

	public void setIdOrderDetail(Integer idOrderDetail) {
		this.idOrderDetail = idOrderDetail;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
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
		return "OrderDetail [idOrderDetail=" + idOrderDetail + ", order=" + order + ", item=" + item + ", quantita="
				+ quantita + ", costo=" + costo + ", dataInserimento=" + dataInserimento + ", dataModifica="
				+ dataModifica + "]";
	}

}
