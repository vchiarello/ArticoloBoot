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
public class LkCategoryItem implements Serializable, Comparable<LkCategoryItem>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 221827921955679090L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_item_category")
	private Integer id;
	
	@ManyToOne
	@JoinColumns({@JoinColumn(name="id_category", referencedColumnName="id_category")})
	private Category category;
	
	@ManyToOne
	@JoinColumns({@JoinColumn(name="id_item", referencedColumnName="id_item")})
	private Item item;
	
	@OneToMany(mappedBy="item", cascade=CascadeType.REMOVE)
	private Set<Allegato> allegati;
	
	@OneToMany(mappedBy="item", cascade=CascadeType.REMOVE)
	private Set<LkTagItem> tag;
	
	@Column(name="date_ins")
	private Date dateIns;
	
	@Column(name="user_ins")
	private String userIns;
	
	@Column(name="date_edit")
	private Date dateEdit;
	
	@Column(name="user_edit")
	private String userEdit;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	@Override
	public int compareTo(LkCategoryItem arg0) {
		if (this.id > arg0.id)return 1;
		if (this.id < arg0.id)return -1;
		return 0;
	}
	
	
	@Override
	public String toString() {
		return "LkCategoryItem [id=" + id + ", category=" + category + ", item=" + item + ", allegati=" + allegati
				+ ", tag=" + tag + ", dateIns=" + dateIns + ", userIns=" + userIns + ", dateEdit=" + dateEdit
				+ ", userEdit=" + userEdit + "]";
	}
	
	

}
