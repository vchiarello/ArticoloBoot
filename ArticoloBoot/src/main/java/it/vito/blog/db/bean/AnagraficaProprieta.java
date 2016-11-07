package it.vito.blog.db.bean;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="bg_property")
public class AnagraficaProprieta implements Serializable,Comparable<AnagraficaProprieta>{

	private static final long serialVersionUID = -7138638040952963959L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id_prop")
	private int id;
	
	@Column(name="nome_proprieta")
	private String nomeProprieta;
		
	@Column(name="valore_proprieta")
	private String valoreProprieta;
	
	@Column(name="flag_multiplo")
	private String flagMultiplo;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNomeProprieta() {
		return nomeProprieta;
	}

	public void setNomeProprieta(String nomeProprieta) {
		this.nomeProprieta = nomeProprieta;
	}

	public String getValoreProprieta() {
		return valoreProprieta;
	}

	public void setValoreProprieta(String valoreProprieta) {
		this.valoreProprieta = valoreProprieta;
	}

	public String getFlagMultiplo() {
		return flagMultiplo;
	}

	public void setFlagMultiplo(String flagMultiplo) {
		this.flagMultiplo = flagMultiplo;
	}

	@Override
	public String toString() {
		return "AnagraficaProprieta [id=" + id + ", nomeProprieta=" + nomeProprieta + ", valoreProprieta="
				+ valoreProprieta + ", flagMultiplo=" + flagMultiplo + "]";
	}

	@Override
	public int compareTo(AnagraficaProprieta arg0) {
		if (this.id > arg0.id)return 1;
		if (this.id < arg0.id)return -1;
		return 0;
	}
	
}
