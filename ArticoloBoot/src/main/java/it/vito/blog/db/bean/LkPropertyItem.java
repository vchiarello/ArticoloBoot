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
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="bg_lk_item_property")
public class LkPropertyItem implements Serializable, Comparable<LkPropertyItem> {


	/**
	 * 
	 */
	private static final long serialVersionUID = 7536194838091825857L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_lk_item_prop")
	private int id;

	@ManyToOne
	@JoinColumns({@JoinColumn(name="id_prop", referencedColumnName="id_prop")})
	private AnagraficaProprieta prop;

	@ManyToOne
	@JoinColumns({@JoinColumn(name="id_item", referencedColumnName="id_item")})
	private Item item;

	@Column(name="value")
	private String value;

	@Column(name="data_modifica")
	private Date dataInserimento;

	@Column(name="data_inserimento")
	private Date dataModifica;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}


	public AnagraficaProprieta getProp() {
		return prop;
	}

	public void setProp(AnagraficaProprieta prop) {
		this.prop = prop;
	}

	public Item getItem() {
		return item;
	}

	public void setItem(Item item) {
		this.item = item;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
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
	public int compareTo(LkPropertyItem arg0) {
		if (this.id > arg0.id)return 1;
		if (this.id < arg0.id)return -1;
		return 0;
	}

	@Override
	public String toString() {
		return "LkPropertyItem [id=" + id + ", prop=" + prop + ", item=" + item + "]";
	}


	
}
