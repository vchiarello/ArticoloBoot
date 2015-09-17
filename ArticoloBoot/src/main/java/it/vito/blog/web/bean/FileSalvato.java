package it.vito.blog.web.bean;

public class FileSalvato{
	public FileSalvato(Integer idAllegato, String nomeAllegato){
		this.idAllegato=idAllegato;
		this.nomeAllegato=nomeAllegato;
	}
	
	public FileSalvato(){
	}
	
	public Integer idAllegato;
	public String nomeAllegato;
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
	
}
