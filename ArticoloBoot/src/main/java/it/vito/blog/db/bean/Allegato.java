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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="bg_allegato")
public class Allegato implements Serializable,Comparable<Allegato>{


	private static final long serialVersionUID = -6907447629654592210L;


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_Allegato")
	private int id;
	
	@ManyToOne
	@JoinColumns({@JoinColumn(name="id_item", referencedColumnName="id_item")})
	private Item item;
	
	@Lob
	@Column(name="contenuto")
	private byte[] dati;
		
	@Column(name="contenuto_Testo")
	private String testo;
		
	@Column(name="data_pubblicazione")
	private Date dataPubblicazione;
	
	@Column(name="data_modifica")
	private Date dataModifica;

	@Column(name="nome_Allegato")
	private String nomeAllegato;

	@Column(name="content_type")
	private String contentType;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTesto() {
		return testo;
	}

	public void setTesto(String testo) {
		this.testo = testo;
	}


	public Date getDataPubblicazione() {
		return dataPubblicazione;
	}

	public void setDataPubblicazione(Date dataPubblicazione) {
		this.dataPubblicazione = dataPubblicazione;
	}

	public Date getDataModifica() {
		return dataModifica;
	}

	public void setDataModifica(Date dataModifica) {
		this.dataModifica = dataModifica;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}
	

	public byte[] getDati() {
		return dati;
	}

	public void setDati(byte[] dati) {
		this.dati = dati;
	}

	public String getNomeAllegato() {
		return nomeAllegato;
	}

	public void setNomeAllegato(String nomeAllegato) {
		this.nomeAllegato = nomeAllegato;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	@Override
	public int compareTo(Allegato arg0) {
		if (this.id > arg0.id)return 1;
		if (this.id < arg0.id)return -1;
		return 0;
	}
	
	@Override
	public String toString() {
		return "Allegato [id=" + id + ", testo="
				+ testo + ", dataPubblicazione=" + dataPubblicazione
				+ ", dataModifica=" + dataModifica + ", nomeAllegato="
				+ nomeAllegato + ", contentType=" + contentType + "]";
	}

}
