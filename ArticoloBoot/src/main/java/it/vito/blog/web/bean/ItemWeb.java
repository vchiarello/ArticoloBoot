package it.vito.blog.web.bean;

import it.vito.blog.db.bean.Item;
import it.vito.blog.db.bean.LkTagItem;
import it.vito.blog.db.bean.TipoItem;

import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class ItemWeb {

	private Integer id;
	private Integer tipoItem;
	private List<Integer> tag;
	private String testo;
	private String titolo;
	private String nome;
	private String riassunto;
	private String autore;
	private Date dataPubblicazione;
	private Date dataModifica;
	private List<String> listaFile;
	
	public ItemWeb(){
		
	}
	
	public ItemWeb(Item item){
		if (item==null) return; 
		this.autore=item.getAutore();
		this.dataModifica=item.getDataModifica();
		this.dataPubblicazione=item.getDataPubblicazione();
		this.id=item.getId();
		this.nome=item.getNome();
		this.riassunto=item.getRiassunto();
		if (item.getTag()!=null){
			this.tag= new LinkedList<Integer>();
			for (Iterator<LkTagItem> i = item.getTag().iterator(); i.hasNext();){
				this.tag.add(i.next().getTag().getId());
			}
		}
		this.testo=item.getTesto();
		this.tipoItem=item.getTipoItem().getId();
		this.titolo=item.getTitolo();
	}
	
	public Item toItem(){
		Item risultato = new Item();
		risultato.setAutore(this.autore);
		risultato.setDataModifica(this.dataModifica);
		if (this.dataPubblicazione!=null) risultato.setDataPubblicazione(this.dataPubblicazione);
		else  risultato.setDataPubblicazione(new Date());
		risultato.setId(this.id);
		risultato.setNome(this.nome);
		risultato.setRiassunto(this.riassunto);
		risultato.setTesto(this.testo);
		TipoItem t = new TipoItem();
		if (this.tipoItem!=null) t.setId(this.tipoItem);
		else t.setId(1);
		risultato.setTipoItem(t);
		risultato.setTitolo(this.titolo);
		return risultato;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getTipoItem() {
		return tipoItem;
	}
	public void setTipoItem(Integer tipoItem) {
		this.tipoItem = tipoItem;
	}
	public List<Integer> getTag() {
		return tag;
	}
	public void setTag(List<Integer> tag) {
		this.tag = tag;
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

	public List<String> getListaFile() {
		return listaFile;
	}

	public void setListaFile(List<String> listaFile) {
		this.listaFile = listaFile;
	}

	@Override
	public String toString() {
		return "ItemWeb [id=" + id + ", tipoItem=" + tipoItem + ", tag=" + tag
				+ ", testo=" + testo + ", titolo=" + titolo + ", nome=" + nome
				+ ", riassunto=" + riassunto + ", autore=" + autore
				+ ", dataPubblicazione=" + dataPubblicazione
				+ ", dataModifica=" + dataModifica + ", listaFile=" + listaFile
				+ "]";
	}
	
	
}
