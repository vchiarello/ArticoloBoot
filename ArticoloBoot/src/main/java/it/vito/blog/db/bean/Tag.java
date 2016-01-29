package it.vito.blog.db.bean;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="bg_tag")
public class Tag implements Serializable, Comparable<Tag>{


	/**
	 * 
	 */
	private static final long serialVersionUID = -7480211732650170822L;

	public Tag(){
		
	}
	
	public Tag(String nomeTag){
		this.nomeTag=nomeTag;
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_Tag")
	private int id;

	@Column(name="nome_tag")
	private String nomeTag;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNomeTag() {
		return nomeTag;
	}

	public void setNomeTag(String nomeTag) {
		this.nomeTag = nomeTag;
	}

	public void setDescrizione(String nomeTag) {
		this.nomeTag = nomeTag;
	}


	@Override
	public int compareTo(Tag arg0) {
		if (this.id > arg0.id)return 1;
		if (this.id < arg0.id)return -1;
		return 0;
	}

	@Override
	public String toString() {
		return "Tag [id=" + id + ", nomeTag=" + nomeTag + "]";
	}

	
}
