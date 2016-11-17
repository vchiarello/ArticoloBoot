package it.vito.blog.web.bean;

import java.io.Serializable;
import java.util.List;

import it.vito.blog.db.bean.Item;
import it.vito.blog.db.bean.Tag;

public class ItemShopWeb extends ItemWeb implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8582050919513532112L;
	private List<ItemPropertyWeb> coloriSelezionati;
	private List<ItemPropertyWeb> taglieSelezionati;

	public ItemShopWeb(){
	}
	public ItemShopWeb(Item item, List<Tag> tags){
		super(item,tags);
	}
	public ItemShopWeb(Item item){
		super(item);
	}
	public List<ItemPropertyWeb> getColoriSelezionati() {
		return coloriSelezionati;
	}
	public void setColoriSelezionati(List<ItemPropertyWeb> coloriSelezionati) {
		this.coloriSelezionati = coloriSelezionati;
	}
	public List<ItemPropertyWeb> getTaglieSelezionati() {
		return taglieSelezionati;
	}
	public void setTaglieSelezionati(List<ItemPropertyWeb> taglieSelezionati) {
		this.taglieSelezionati = taglieSelezionati;
	}

	
}