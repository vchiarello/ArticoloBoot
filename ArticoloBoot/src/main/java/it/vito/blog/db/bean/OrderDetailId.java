package it.vito.blog.db.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;

@Embeddable
public class OrderDetailId implements Serializable{

 	/**
	 * 
	 */
	private static final long serialVersionUID = 4351848623853328316L;

	@ManyToOne
	@JoinColumns({@JoinColumn(name="id_Order", referencedColumnName="id_Order")})
	private Order order;
	
 	@Column(name="progressivo")
	private Integer progressivo;


	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}

	public Integer getProgressivo() {
		return progressivo;
	}

	public void setProgressivo(Integer progressivo) {
		this.progressivo = progressivo;
	}

	@Override
	public String toString() {
		return "OrderDetailId [order=" + order.getIdOrder() + ", progressivo=" + progressivo + "]";
	}
	
}
