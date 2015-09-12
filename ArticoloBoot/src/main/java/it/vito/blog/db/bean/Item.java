package it.vito.blog.db.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="bg_item")
public class Item implements Serializable, Comparable<Item>{

	private static final long serialVersionUID = 8072996997982600054L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_item")
	private Integer id;
	
	@ManyToOne
	@JoinColumns({@JoinColumn(name="id_Tipo_Item", referencedColumnName="id_Tipo")})
	private TipoItem tipoItem;
	
	@OneToMany(mappedBy="articolo", cascade=CascadeType.REMOVE)
	private Set<Allegato> allegati;
	
	@OneToMany(mappedBy="item", cascade=CascadeType.REMOVE)
	private Set<LkTagItem> tag;
	
	@Column(name="testo")
	private String testo;
	
	@Column(name="titolo")
	private String titolo;
	
	@Column(name="nome")
	private String nome;
	
	@Column(name="riassunto")
	private String riassunto;
	
	@Column(name="autore")
	private String autore;
	
	@Column(name="data_pubblicazione")
	private Date dataPubblicazione;
	
	@Column(name="data_modifica")
	private Date dataModifica;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTesto() {
		return testo;
	}

	public void setTesto(String testo) {
		this.testo = testo;
	}

	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getRiassunto() {
		return riassunto;
	}

	public void setRiassunto(String riassunto) {
		this.riassunto = riassunto;
	}

	public String getAutore() {
		return autore;
	}

	public void setAutore(String autore) {
		this.autore = autore;
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
	
	

	public Set<Allegato> getAllegati() {
		return allegati;
	}

	public void setAllegati(Set<Allegato> allegati) {
		this.allegati = allegati;
	}

	public Set<LkTagItem> getTag() {
		return tag;
	}

	public void setTag(Set<LkTagItem> tag) {
		this.tag = tag;
	}

	public TipoItem getTipoItem() {
		return tipoItem;
	}

	public void setTipoItem(TipoItem tipoItem) {
		this.tipoItem = tipoItem;
	}

	@Override
	public int compareTo(Item arg0) {
		if (this.id > arg0.id)return 1;
		if (this.id < arg0.id)return -1;
		return 0;
	}
	
	
	@Override
	public String toString() {
		 StringBuffer risultato = new StringBuffer( "Articolo [id=" + id + ", testo=" + testo + ", titolo=" + titolo
				+ ", nome=" + nome + ", riassunto=" + riassunto + ", autore="
				+ autore + ", dataPubblicazione=" + dataPubblicazione
				+ ", dataModifica=" + dataModifica);
		 		if (allegati !=null)
		 			risultato.append(
				", con =" + allegati.size() + "allegati"); 
				risultato.append("]");
		 return risultato.toString();
	}
	
	

}
