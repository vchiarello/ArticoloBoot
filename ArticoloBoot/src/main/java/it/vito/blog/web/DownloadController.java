package it.vito.blog.web;


import it.vito.blog.business.GestioneBlog;
import it.vito.blog.db.bean.Allegato;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.stream.FileImageInputStream;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DownloadController {

	private Logger logger = LoggerFactory.getLogger(DownloadController.class);

	@Value("${pathFile}")
	String pathFile;

	@Autowired
	GestioneBlog gestioneBlog;

	
	@RequestMapping(value="/rest/download", method = RequestMethod.GET)
	public void download(@RequestParam("idAllegato") Integer idAllegato,HttpServletResponse response) {
		
			try {
				Allegato allegato = gestioneBlog.getAllegato(idAllegato);
				byte[] b = allegato.getDati();
				response.setContentType(allegato.getContentType());
				FileCopyUtils.copy(b, response.getOutputStream());
				logger.debug("File scaricato. ");
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

	@RequestMapping(value="/rest/downloadTemp", method = RequestMethod.GET)
	public void dowloadTemp(@RequestParam("nomeAllegato") String nomeAllegato,HttpServletResponse response) {
		
			try {
				FileInputStream in = new FileInputStream(new File(pathFile+"/"+nomeAllegato));
				FileCopyUtils.copy(in, response.getOutputStream());
				logger.debug("File scaricato. ");
			} catch (IllegalStateException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

}
