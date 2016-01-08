package it.vito.blog.web.bean;

import it.vito.blog.db.bean.Allegato;
import it.vito.blog.db.bean.Item;
import it.vito.blog.db.bean.LkTagItem;
import it.vito.blog.db.bean.Tag;
import it.vito.blog.db.bean.TipoItem;

import java.lang.reflect.InvocationTargetException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import com.mysql.jdbc.Field;

public class ItemWeb {

	private Integer id;
	private Integer tipoItem;
	private String testo;
	private String titolo;
	private String nome;
	private String riassunto;
	private String autore;
	private String dataPubblicazione;
	private String dataScadenza;
	private String dataHidden;
	private String dataInserimento;
	private String dataModifica;
	private String nuoviTag;
	private List<Option> tagDisponibili;
	private List<Option> tagSelezionati;
	private List<FileAllegato> listaFile;
	private List<FileAllegato> listaFileSalvati;
	private List<Integer> listaFileDaCancellare;
	
	
	public ItemWeb(){
		
	}
	
	public ItemWeb(Item item, List<Tag> tags){
		if (item==null) return; 
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		this.autore=item.getAutore();
		if (item.getDataPubblicazione() !=null)
			this.dataPubblicazione = sdf.format(item.getDataPubblicazione());
		if (item.getDataHidden() !=null)
			this.dataHidden = sdf.format(item.getDataHidden());
		if (item.getDataInserimento() !=null)
			this.dataInserimento = sdf.format(item.getDataInserimento());
		if (item.getDataModifica() !=null)
			this.dataModifica = sdf.format(item.getDataModifica());
		if (item.getDataPubblicazione()!=null)
			this.dataPubblicazione = sdf.format(item.getDataPubblicazione());
		if (item.getDataScadenza()!=null)
			this.dataScadenza = sdf.format(item.getDataScadenza());
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
			this.listaFileSalvati=new LinkedList<FileAllegato>();
			for (Iterator<Allegato> i = item.getAllegati().iterator(); i.hasNext();){
				Allegato a = i.next();
				this.listaFileSalvati.add(new FileAllegato(a.getId(), a.getNomeAllegato(), a.getTesto()));
			}
		
		this.testo=item.getTesto();
		this.tipoItem=item.getTipoItem().getId();
		this.titolo=item.getTitolo();
	}
	
	public ItemWeb(Item item){
		if (item==null) return; 
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		this.autore=item.getAutore();
		
		if (item.getDataPubblicazione() !=null)
			this.dataPubblicazione = sdf.format(item.getDataPubblicazione());
		if (item.getDataHidden() !=null)
			this.dataHidden = sdf.format(item.getDataHidden());
		if (item.getDataInserimento() !=null)
			this.dataInserimento = sdf.format(item.getDataInserimento());
		if (item.getDataModifica() !=null)
			this.dataModifica = sdf.format(item.getDataModifica());
		if (item.getDataPubblicazione()!=null)
			this.dataPubblicazione = sdf.format(item.getDataPubblicazione());
		if (item.getDataScadenza()!=null)
			this.dataScadenza = sdf.format(item.getDataScadenza());
			
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
				if (this.listaFileSalvati ==null)this.listaFileSalvati=new LinkedList<FileAllegato>();
				Allegato a = i.next();
				this.listaFileSalvati.add(new FileAllegato(a.getId(), a.getNomeAllegato(), a.getTesto()));
			}
		this.testo=item.getTesto();
		this.tipoItem=item.getTipoItem().getId();
		this.titolo=item.getTitolo();
	}
	
	public ItemWeb (LinkedHashMap<String, Object> input){
		ItemWeb risultato = new ItemWeb();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		try {
			java.lang.reflect.Method m = null;
			m = ItemWeb.class.getMethod("setId", java.lang.Integer.class);
			if (input.get("id") != null)
			m.invoke(risultato, new Integer((String)input.get("id")));

//			f = ItemWeb.class.getField("tipoItem");
//			if (input.get("tipoItem") != null)
//			f.set(risultato, input.get("tipoItem"));
//
//			f = ItemWeb.class.getField("testo");
//			f.set(risultato, input.get("testo"));
//
//			f = ItemWeb.class.getField("titolo");
//			f.set(risultato, input.get("titolo"));
//
//			f = ItemWeb.class.getField("nome");
//			f.set(risultato, input.get("nome"));
//
//			f = ItemWeb.class.getField("riassunto");
//			f.set(risultato, input.get("riassunto"));
//
//			f = ItemWeb.class.getField("autore");
//			f.set(risultato, input.get("autore"));
//
//			f = ItemWeb.class.getField("dataPubblicazione");
//			f.set(risultato, sdf.parse((String)input.get("dataPubblicazione")));
//
//			f = ItemWeb.class.getField("dataScadenza");
//			f.set(risultato, sdf.parse((String)input.get("dataScadenza")));
//
//			f = ItemWeb.class.getField("dataHidden");
//			f.set(risultato, sdf.parse((String)input.get("dataHidden")));

		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
	}
	
	
	public Item toItem(){
		Item risultato = new Item();
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		risultato.setAutore(this.autore);
		
		if (this.dataModifica !=null)
		try {
			risultato.setDataModifica(sdf.parse(this.dataModifica));
		} catch (ParseException e) {
			risultato.setDataModifica(new Date());
		}

		if (this.dataPubblicazione!=null)
			try {
				risultato.setDataPubblicazione(sdf.parse(this.dataPubblicazione));
			} catch (ParseException e) {
				risultato.setDataPubblicazione(new Date());
			}
		else  risultato.setDataPubblicazione(new Date());
		if (this.dataHidden!=null)
			try {
				risultato.setDataHidden(sdf.parse(this.dataHidden));
			} catch (ParseException e) {
				risultato.setDataHidden(null);
			}
		else  risultato.setDataHidden(null);
		if (this.dataInserimento != null)
			try {
				risultato.setDataInserimento(sdf.parse(this.dataInserimento));
			} catch (ParseException e) {
				risultato.setDataInserimento(new Date());
			}
		else  risultato.setDataInserimento(new Date());

		if (this.dataScadenza != null)
			try {
				risultato.setDataScadenza(sdf.parse(this.dataScadenza));
			} catch (ParseException e) {
				risultato.setDataScadenza(null);
			}
		else risultato.setDataScadenza(null);
			
		
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
	public String getDataPubblicazione() {
		return dataPubblicazione;
	}
	public void setDataPubblicazione(String dataPubblicazione) {
		this.dataPubblicazione = dataPubblicazione;
	}
	public String getDataModifica() {
		return dataModifica;
	}
	public void setDataModifica(String dataModifica) {
		this.dataModifica = dataModifica;
	}

	public List<FileAllegato> getListaFile() {
		return listaFile;
	}

	public void setListaFile(List<FileAllegato> listaFile) {
		this.listaFile = listaFile;
	}
	

	public List<FileAllegato> getListaFileSalvati() {
		return listaFileSalvati;
	}

	public void setListaFileSalvati(List<FileAllegato> listaFileSalvati) {
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

	public List<Integer> getListaFileDaCancellare() {
		return listaFileDaCancellare;
	}

	public void setListaFileDaCancellare(List<Integer> listaFileDaCancellare) {
		this.listaFileDaCancellare = listaFileDaCancellare;
	}

	public String getNuoviTag() {
		return nuoviTag;
	}

	public void setNuoviTag(String nuoviTag) {
		this.nuoviTag = nuoviTag;
	}

	public String getDataScadenza() {
		return dataScadenza;
	}

	public void setDataScadenza(String dataScadenza) {
		this.dataScadenza = dataScadenza;
	}

	public String getDataHidden() {
		return dataHidden;
	}

	public void setDataHidden(String dataHidden) {
		this.dataHidden = dataHidden;
	}

	public String getDataInserimento() {
		return dataInserimento;
	}

	public void setDataInserimento(String dataInserimento) {
		this.dataInserimento = dataInserimento;
	}

	@Override
	public String toString() {
		return "ItemWeb [id=" + id + ", tipoItem=" + tipoItem + ", testo="
				+ testo + ", titolo=" + titolo + ", nome=" + nome
				+ ", riassunto=" + riassunto + ", autore=" + autore
				+ ", dataPubblicazione=" + dataPubblicazione
				+ ", dataScadenza=" + dataScadenza + ", dataHidden="
				+ dataHidden + ", dataInserimento=" + dataInserimento
				+ ", dataModifica=" + dataModifica + ", nuoviTag=" + nuoviTag
				+ ", tagDisponibili=" + tagDisponibili + ", tagSelezionati="
				+ tagSelezionati + ", listaFile=" + listaFile
				+ ", listaFileSalvati=" + listaFileSalvati
				+ ", listaFileDaCancellare=" + listaFileDaCancellare + "]";
	}

	
	
	
}
