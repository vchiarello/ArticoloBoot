package it.vito.blog.web;

import it.vito.blog.business.GestioneBlog;
import it.vito.blog.web.bean.ErroreWeb;
import it.vito.blog.web.bean.ItemWeb;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/items")
public class ItemRestController {

	private Logger logger = LoggerFactory.getLogger(ItemRestController.class);
	
	@Autowired
	GestioneBlog gestioneBlog;

	@RequestMapping(method=RequestMethod.GET)
	public List<ItemWeb> list() {
		logger.debug("Get lista item...");
		List<ItemWeb> l = gestioneBlog.getAllItem();
		return l;
	}
	
	@RequestMapping(value="/{id}/{name}", method=RequestMethod.GET)
	public ItemWeb get(@PathVariable("id") int id,@PathVariable("name") String name) {
		logger.debug("Get item...");
		ItemWeb item = gestioneBlog.getItem(id);
		
		return item;
	}
	
	
	@RequestMapping(method=RequestMethod.POST)
	public ItemWeb create(@RequestBody @Valid ItemWeb itemWeb) {
		return this.gestioneBlog.saveItem(itemWeb);
	}

	@RequestMapping(value="/{id}", method=RequestMethod.PUT)
	public ItemWeb update(@PathVariable("id") int id, @RequestBody @Valid ItemWeb itemWeb) {
		if (this.valida(itemWeb)) return this.gestioneBlog.saveItem(itemWeb);
		else return itemWeb;
	}

	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)
	public void delete(@PathVariable("id") int id) {
		this.gestioneBlog.deleteItem(id);
	}


	
	private boolean valida(ItemWeb itemWeb){
		boolean risultato = true;
		ErroreWeb errore = null;
		if (itemWeb.getNome()==null || itemWeb.getNome().trim().length()==0){
			risultato = false;
			if (errore==null)errore = new ErroreWeb();
			errore.setErroreNome("Nome: campo obbligatorio");
		}
		if (itemWeb.getTitolo()==null || itemWeb.getTitolo().trim().length()==0){
			risultato = false;
			if (errore==null)errore = new ErroreWeb();
			errore.setErroreTitolo("Titolo: campo obbligatorio");
		}
		if (itemWeb.getTesto()==null || itemWeb.getTesto().trim().length()==0){
			risultato = false;
			if (errore==null)errore = new ErroreWeb();
			errore.setErroreTesto("Testo: campo obbligatorio");
		}
		
		if (errore !=null) itemWeb.setErroreWeb(errore);
		else itemWeb.setErroreWeb(null);
		return risultato;
	}
	


}
