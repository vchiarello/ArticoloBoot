package it.vito.blog.web.bean;

import it.vito.blog.db.bean.Allegato;
import it.vito.blog.db.bean.Item;
import it.vito.blog.db.bean.LkTagItem;
import it.vito.blog.db.bean.Tag;
import it.vito.blog.db.bean.TipoItem;

import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class ItemWeb {

	private Integer id;
	private Integer tipoItem;
	private String testo;
	private String titolo;
	private String nome;
	private String riassunto;
	private String autore;
	private Date dataPubblicazione;
	private Date dataModifica;
	private List<String> listaFile;
	private List<FileSalvato> listaFileSalvati;
	private List<Option> tagDisponibili;
	private List<Option> tagSelezionati;
	
	
	public ItemWeb(){
		
	}
	
	public ItemWeb(Item item, List<Tag> tags){
		if (item==null) return; 
		this.autore=item.getAutore();
		this.dataModifica=item.getDataModifica();
		this.dataPubblicazione=item.getDataPubblicazione();
		this.id=item.getId();
		this.nome=item.getNome();
		this.riassunto=item.getRiassunto();
		
		if (tags !=null){
			this.tagDisponibili = new LinkedList<Option>();
			for (int i = 0; i < tags.size();i++)
				this.tagDisponibili.add(new Option(tags.get(i).getId(), tags.get(i).getNomeTag()));
		}
		
		if (item.getTag()!=null){
			this.tagSelezionati= new LinkedList<Option>();
			for (Iterator<LkTagItem> i = item.getTag().iterator(); i.hasNext();){
				Tag t = i.next().getTag();
				this.tagSelezionati.add(new Option(t.getId(),t.getNomeTag()));
			}
		}
		
		if (item.getAllegati()!=null)
			this.listaFileSalvati=new LinkedList<FileSalvato>();
			for (Iterator<Allegato> i = item.getAllegati().iterator(); i.hasNext();){
				Allegato a = i.next();
				this.listaFileSalvati.add(new FileSalvato(a.getId(), a.getNomeAllegato()));
			}
		
		this.testo=item.getTesto();
		this.tipoItem=item.getTipoItem().getId();
		this.titolo=item.getTitolo();
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
			this.tagSelezionati= new LinkedList<Option>();
			for (Iterator<LkTagItem> i = item.getTag().iterator(); i.hasNext();){
				LkTagItem lk = i.next();
				this.tagSelezionati.add(new Option(lk.getTag().getId(), lk.getTag().getNomeTag()));
			}
		}
		
		if (item.getAllegati()!=null)
			for (Iterator<Allegato> i = item.getAllegati().iterator(); i.hasNext();){
				if (this.listaFileSalvati ==null)this.listaFileSalvati=new LinkedList<FileSalvato>();
				Allegato a = i.next();
				this.listaFileSalvati.add(new FileSalvato(a.getId(), a.getNomeAllegato()));
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
	

	public List<FileSalvato> getListaFileSalvati() {
		return listaFileSalvati;
	}

	public void setListaFileSalvati(List<FileSalvato> listaFileSalvati) {
		this.listaFileSalvati = listaFileSalvati;
	}

	public List<Option> getTagDisponibili() {
		return tagDisponibili;
	}

	public void setTagDisponibili(List<Option> tagDisponibili) {
		this.tagDisponibili = tagDisponibili;
	}

	public List<Option> getTagSelezionati() {
		return tagSelezionati;
	}

	public void setTagSelezionati(List<Option> tagSelezionati) {
		this.tagSelezionati = tagSelezionati;
	}

	@Override
	public String toString() {
		return "ItemWeb [id=" + id + ", tipoItem=" + tipoItem + ", tag=" + tagSelezionati
				+ ", testo=" + testo + ", titolo=" + titolo + ", nome=" + nome
				+ ", riassunto=" + riassunto + ", autore=" + autore
				+ ", dataPubblicazione=" + dataPubblicazione
				+ ", dataModifica=" + dataModifica + ", listaFile=" + listaFile
				+ "]";
	}
	
	public class FileSalvato{
		FileSalvato(Integer idAllegato, String nomeAllegato){
			this.idAllegato=idAllegato;
			this.nomeAllegato=nomeAllegato;
		}
		public Integer idAllegato;
		public String nomeAllegato;
	}
	
	public class Option{
		Option(Integer idOption, String nameOption){
			this.id=idOption;
			this.name=nameOption;
		}
		public Integer id;
		public String name;
	}
	
}
