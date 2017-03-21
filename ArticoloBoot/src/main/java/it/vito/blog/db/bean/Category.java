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
@Table(name="bg_category")
public class Category implements Serializable,Comparable<Category>{

	private static final long serialVersionUID = 1361420209124282601L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_category")
	private int id;
	
	@Column(name="category_name")
	private String categoryName;
		
	@ManyToOne
	@JoinColumns({@JoinColumn(name="id_parent_category", referencedColumnName="id_category")})
	private Category padre;

	@Column(name="date_ins")
	private Date dateIns;
	
	@Column(name="user_ins")
	private String userIns;
	
	@Column(name="date_edit")
	private Date dateEdit;
	
	@Column(name="user_edit")
	private String userEdit;
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}


	public Category getPadre() {
		return padre;
	}

	public void setPadre(Category padre) {
		this.padre = padre;
	}

	public Date getDateIns() {
		return dateIns;
	}

	public void setDateIns(Date dateIns) {
		this.dateIns = dateIns;
	}

	public String getUserIns() {
		return userIns;
	}

	public void setUserIns(String userIns) {
		this.userIns = userIns;
	}

	public Date getDateEdit() {
		return dateEdit;
	}

	public void setDateEdit(Date dateEdit) {
		this.dateEdit = dateEdit;
	}

	public String getUserEdit() {
		return userEdit;
	}

	public void setUserEdit(String userEdit) {
		this.userEdit = userEdit;
	}

	@Override
	public int compareTo(Category arg0) {
		if (this.id > arg0.id)return 1;
		if (this.id < arg0.id)return -1;
		return 0;
	}

	@Override
	public String toString() {
		return "Category [id=" + id + ", categoryName=" + categoryName + ", padre=" + padre + ", dateIns=" + dateIns
				+ ", userIns=" + userIns + ", dateEdit=" + dateEdit + ", userEdit=" + userEdit + "]";
	}

	
}
