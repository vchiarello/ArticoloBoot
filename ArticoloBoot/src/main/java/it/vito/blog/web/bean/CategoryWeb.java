package it.vito.blog.web.bean;

import java.io.Serializable;
import java.util.List;

public class CategoryWeb implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1630077790593795420L;
	Integer id;
	String categoryName;
	List<CategoryWeb> descendant;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public List<CategoryWeb> getDescendant() {
		return descendant;
	}
	public void setDescendant(List<CategoryWeb> descendant) {
		this.descendant = descendant;
	}
	@Override
	public String toString() {
		return "CategoryWeb [id=" + id + ", categoryName=" + categoryName + ", descendant=" + descendant + "]";
	}
	
}
