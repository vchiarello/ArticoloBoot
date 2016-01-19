package it.vito.blog.web;


import java.util.Locale;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class ErroriController {

	private Logger logger = LoggerFactory.getLogger(ErroriController.class);


	
	@RequestMapping(value="/messaggiErrore", method = RequestMethod.GET,produces = "application/javascript; charset=utf-8")
	@ResponseBody
	public String messaggiErrore(HttpServletResponse response, Locale locale) {
		logger.debug("Messaggi di errore. ");
		response.setContentType("application/javascript");
		//return "i18n/messaggi.errore";
		return 
		"var messaggiErrore = new Array();"+
		"messaggiErrore['item.edit.name.required'] = 'Nome: campo obbligatorio';"+
		"messaggiErrore['item.edit.titolo.required'] = 'Titolo: nome campo obbligatorio';"+
		"messaggiErrore['item.edit.testo.required'] = 'Testo: campo obbligatorio';"+
		"";
	}

}
