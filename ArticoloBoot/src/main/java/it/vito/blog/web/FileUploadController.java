package it.vito.blog.web;


import it.vito.blog.web.bean.WebFile;

import java.io.File;
import java.io.IOException;

import javax.management.RuntimeErrorException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/upload")
public class FileUploadController {

	private Logger logger = LoggerFactory.getLogger(FileUploadController.class);
	
	@Value("${pathFile}")
	String pathFile;
	
	@RequestMapping(method = RequestMethod.POST)
	public void upload(@ModelAttribute("uploadForm") WebFile uploadForm, Model map) {

		//Messo per simulare l'errore
		//throw new RuntimeErrorException(new Error("test di errore"));
		
		for (int i = 0; i < uploadForm.getFile().size();i++){
			try {
				uploadForm.getFile().get(i).transferTo(new File(pathFile+"/"+uploadForm.getFile().get(i).getOriginalFilename()));
				logger.debug("File salvato: " + pathFile+"/"+uploadForm.getFile().get(i).getOriginalFilename());
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
	}

}
