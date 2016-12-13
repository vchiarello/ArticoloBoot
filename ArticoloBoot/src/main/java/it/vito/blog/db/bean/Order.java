package it.vito.blog.db.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
	@Column(name="id_carrello")
	private int idCarrello;
	
	@Column(name="utente")
	private String utente;
	
	@Column(name="data_inserimento")
	private Date dataInserimento;
	
	@Column(name="data_modifica")
	private Date dataModifica;

	public int getIdCarrello() {
		return idCarrello;
	}

	public void setIdCarrello(int idCarrello) {
		this.idCarrello = idCarrello;
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
		return "Order [idCarrello=" + idCarrello + ", utente=" + utente + ", dataInserimento=" + dataInserimento
				+ ", dataModifica=" + dataModifica + "]";
	}
	
}
