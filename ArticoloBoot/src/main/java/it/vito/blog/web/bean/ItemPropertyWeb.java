package it.vito.blog.web.bean;

import java.io.Serializable;

import it.vito.blog.db.bean.AnagraficaProprieta;

public class ItemPropertyWeb implements Serializable{

	private static final long serialVersionUID = 5660234030224599373L;
	String nome;
	String valore;
	Integer id;
	
	public ItemPropertyWeb() {
	}
	public ItemPropertyWeb(AnagraficaProprieta anag) {
		this.nome = anag.getNomeProprieta();
		this.valore = anag.getValoreProprieta();
		this.id = anag.getId();
	}
	public AnagraficaProprieta toItemProperty(){
		AnagraficaProprieta risultato = new AnagraficaProprieta();
		risultato.setId(this.getId());
		risultato.setNomeProprieta(this.getNome());
		risultato.setValoreProprieta(this.valore);
		return risultato;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getValore() {
		return valore;
	}
	public void setValore(String valore) {
		this.valore = valore;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "ItemPropertyWeb [nome=" + nome + ", valore=" + valore + ", id=" + id + "]";
	}
	/**
	 * 
	 */
}
