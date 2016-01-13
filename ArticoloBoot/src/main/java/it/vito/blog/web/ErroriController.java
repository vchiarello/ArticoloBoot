package it.vito.blog.web;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class ErroriController {

	private Logger logger = LoggerFactory.getLogger(ErroriController.class);


	
	@RequestMapping(value="/messaggiErrore", method = RequestMethod.GET)
	public String messaggiErrore() {
		
		logger.debug("Messaggi di errore. ");
		return "i18n/messaggi.errore";
	}

}
