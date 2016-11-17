package it.vito.blog.web;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import it.vito.blog.business.GestioneBlog;
import it.vito.blog.db.bean.AllegatoInfo;
import it.vito.blog.web.bean.ErroreWeb;
import it.vito.blog.web.bean.ItemShopWeb;
import it.vito.blog.web.bean.ItemWeb;

@RestController
@RequestMapping("/rest/itemShop")
public class ItemShopRestController {

	private Logger logger = LoggerFactory.getLogger(ItemRestController.class);
	
	@Autowired
	GestioneBlog gestioneBlog;

	@RequestMapping(method=RequestMethod.GET)
	public List<ItemWeb> list() {
		logger.debug("Get lista item...");
		List<ItemWeb> l = gestioneBlog.getAllItem();
		return l;
	}
	
	@RequestMapping(value="/{attivi}", method=RequestMethod.GET)
	public List<ItemWeb> listAttivi(@PathVariable("attivi") String attivi) {
		logger.debug("Get lista item attivi...");
		List<ItemWeb> l = gestioneBlog.getAllItemAttivi();
		return l;
	}
	
	@RequestMapping(value="/{id}/{name}", method=RequestMethod.GET)
	public ItemWeb get(@PathVariable("id") int id,@PathVariable("name") String name) {
		logger.debug("Get item...");
		ItemWeb item = gestioneBlog.getItemShop(id);
		
		return item;
	}
	
	
	@RequestMapping(value="/{name}", method=RequestMethod.POST)
	public ItemWeb create(@RequestBody @Valid ItemShopWeb itemShopWeb ,@PathVariable("name") String name, Locale locale) {
		if (this.valida(itemShopWeb,locale)) return this.gestioneBlog.saveItem(itemShopWeb);
		else return itemShopWeb;
	}

	@RequestMapping(value="/{id}/{name}", method=RequestMethod.PUT)
	public ItemWeb update(@PathVariable("id") int id, @PathVariable("name") String name, @RequestBody @Valid ItemShopWeb itemShopWeb,Locale locale) {
		if (this.valida(itemShopWeb,locale)) return this.gestioneBlog.saveItem(itemShopWeb);
		else return itemShopWeb;
	}

	@RequestMapping(value="/{id}/{name}", method=RequestMethod.DELETE)
	public void delete(@PathVariable("id") int id, @PathVariable("name") String name) {
		this.gestioneBlog.deleteItem(id);
	}

	@RequestMapping(value="/{id}/{name}", method=RequestMethod.GET)
	public List<AllegatoInfo> getImagesInfo(@PathVariable("id") int id, @PathVariable("name") String name) {
		return this.gestioneBlog.getResourceInfo(id,name);
	}


	//TODO internazionalizzare i messaggi di errore
	private boolean valida(ItemWeb itemWeb, Locale locale){
		ResourceBundle rb = ResourceBundle.getBundle("MessaggiErrore", locale);
		boolean risultato = true;
		ErroreWeb errore = null;
		if (itemWeb.getNome()==null || itemWeb.getNome().trim().length()==0){
			risultato = false;
			if (errore==null)errore = new ErroreWeb();
			errore.setErroreNome(rb.getString("item.edit.name.required"));
		}
		if (itemWeb.getTitolo()==null || itemWeb.getTitolo().trim().length()==0){
			risultato = false;
			if (errore==null)errore = new ErroreWeb();
			errore.setErroreTitolo(rb.getString("item.edit.title.required"));
		}
		if (itemWeb.getTesto()==null || itemWeb.getTesto().trim().length()==0){
			risultato = false;
			if (errore==null)errore = new ErroreWeb();
			errore.setErroreTesto(rb.getString("item.edit.text.required"));
		}
		
		if (itemWeb.getTesto()==null || itemWeb.getTesto().trim().length()==0){
			risultato = false;
			if (errore==null)errore = new ErroreWeb();
			errore.setErroreTesto(rb.getString("item.edit.author.required"));
		}
		
		if (errore !=null) itemWeb.setErroreWeb(errore);
		else itemWeb.setErroreWeb(null);
		return risultato;
	}
	


}
