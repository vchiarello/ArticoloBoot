package it.vito.blog.web.bean;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class WebFile {

	private List<MultipartFile> file;

	public List<MultipartFile> getFile() {
		return file;
	}

	public void setFile(List<MultipartFile> file) {
		this.file = file;
	}

	
	
}
