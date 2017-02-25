package it.vito.blog.db.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

@Entity
@Table(name="bg_order")
public class Order implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1788095184046806929L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_order")
	private Integer idOrder;
	
	@Column(name="utente")
	private String utente;
	
	@Column(name="data_inserimento")
	private Date dataInserimento;
	
	@Column(name="data_modifica")
	private Date dataModifica;
	
	@OneToMany(targetEntity=it.vito.blog.db.bean.OrderDetail.class, mappedBy="order")
	@OrderBy("idOrderDetail ASC")
	private List<OrderDetail> orderDetail;



	public Integer getIdOrder() {
		return idOrder;
	}

	public void setIdOrder(Integer idOrder) {
		this.idOrder = idOrder;
	}

	public List<OrderDetail> getOrderDetail() {
		return orderDetail;
	}

	public void setOrderDetail(List<OrderDetail> orderDetail) {
		this.orderDetail = orderDetail;
	}

	public String getUtente() {
		return utente;
	}

	public void setUtente(String utente) {
		this.utente = utente;
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
		return "Order [idOrder=" + idOrder + ", utente=" + utente + ", dataInserimento=" + dataInserimento
				+ ", dataModifica=" + dataModifica + ", orderDetail=" + orderDetail + "]";
	}

	
}
