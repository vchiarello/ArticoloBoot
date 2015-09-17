package it.vito.blog.web.bean;

public class Option{
	public Option(Integer idOption, String nameOption){
		this.id=idOption;
		this.name=nameOption;
	}
	public Option(){
	}
	public Integer id;
	public String name;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
}
