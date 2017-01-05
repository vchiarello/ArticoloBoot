package it.vito.blog.db.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

@Entity
@Table(name="bg_cart")
public class Cart implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = 4224673751798520212L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_cart")
	private Integer idCart;
	
	@Column(name="utente")
	private String utente;
	
	@Column(name="data_inserimento")
	private Date dataInserimento;
	
	@Column(name="data_modifica")
	private Date dataModifica;

	@OneToMany(targetEntity=it.vito.blog.db.bean.CartDetail.class, mappedBy="cart",fetch=FetchType.EAGER)
	@OrderBy("idCartDetail ASC")
	private List<CartDetail> cartDetail;

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

	public List<CartDetail> getCartDetail() {
		return cartDetail;
	}

	public void setCartDetail(List<CartDetail> cartDetail) {
		this.cartDetail = cartDetail;
	}

	@Override
	public String toString() {
		return "Cart [idCart=" + idCart + ", utente=" + utente + ", dataInserimento=" + dataInserimento
				+ ", dataModifica=" + dataModifica + "]";
	}

}
