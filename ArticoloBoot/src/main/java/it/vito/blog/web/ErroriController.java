package it.vito.blog.web;


import java.util.Enumeration;
import java.util.Locale;
import java.util.ResourceBundle;

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
		ResourceBundle rb = ResourceBundle.getBundle("MessaggiErrore", locale);
		logger.debug("Locale used is:"+rb.getLocale().toString());
		logger.debug("valore per item.edit.name.required"+rb.getString("item.edit.name.required"));
		//return "i18n/messaggi.errore";

		StringBuffer risultato = new StringBuffer("var messaggiErrore = new Array();");
		for (Enumeration<String> e = rb.getKeys(); e.hasMoreElements();){
			String chiave = e.nextElement();
			String valore = rb.getString(chiave);
			if (chiave.indexOf("function")>=0)
				risultato = risultato.append("messaggiErrore['"+chiave+"'] = "+valore+";");
			else
				risultato = risultato.append("messaggiErrore['"+chiave+"'] = '"+valore+"';");
		}
		return risultato.toString();
//		"var messaggiErrore = new Array();"+
//		"messaggiErrore['item.edit.name.required'] = 'Nome: campo obbligatorio';"+
//		"messaggiErrore['item.edit.titolo.required'] = 'Titolo: nome campo obbligatorio';"+
//		"messaggiErrore['item.edit.testo.required'] = 'Testo: campo obbligatorio';"+
//		"";
	}

}
