package it.vito.blog.web.bean;

public class FileAllegato{
	public FileAllegato(Integer idAllegato, String nomeAllegato, String note){
		this.idAllegato=idAllegato;
		this.nomeAllegato=nomeAllegato;
		this.note=note;
	}
	
	public FileAllegato(){
	}
	
	public Integer idAllegato;
	public String nomeAllegato;
	public String note;
	public Integer getIdAllegato() {
		return idAllegato;
	}

	public void setIdAllegato(Integer idAllegato) {
		this.idAllegato = idAllegato;
	}

	public String getNomeAllegato() {
		return nomeAllegato;
	}

	public void setNomeAllegato(String nomeAllegato) {
		this.nomeAllegato = nomeAllegato;
	}

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
	}
	
}
