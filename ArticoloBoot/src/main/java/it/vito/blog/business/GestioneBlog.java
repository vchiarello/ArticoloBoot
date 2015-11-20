package it.vito.blog.business;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javassist.bytecode.ByteArray;
import it.vito.blog.db.bean.Allegato;
import it.vito.blog.db.bean.Item;
import it.vito.blog.db.bean.LkTagItem;
import it.vito.blog.db.bean.Tag;
import it.vito.blog.db.dao.AllegatoRepository;
import it.vito.blog.db.dao.ItemRepository;
import it.vito.blog.db.dao.LkTagItemRepository;
import it.vito.blog.db.dao.TagRepository;
import it.vito.blog.web.bean.ItemWeb;
import it.vito.blog.web.bean.Option;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component("gestioneBlog")
public class GestioneBlog {

	private Logger logger = LoggerFactory.getLogger(GestioneBlog.class);
	
	@Autowired
	ItemRepository itemRepository;

	@Autowired
	TagRepository tagRepository;
	
	@Autowired
	LkTagItemRepository lkTagItemRepository;
	
	@Autowired
	AllegatoRepository allegatoRepository;
	
	@Value("${pathFile}")
	String pathFile;

	public byte[] getAllegato(Integer idAllegato){
		Allegato allegato = allegatoRepository.findOne(idAllegato);
		return allegato.getDati();
	}

	public List<ItemWeb>getAllItem(){
		logger.debug("Get lista item...");
		List<Item> l = itemRepository.findAll();
		
		if (l==null){
			logger.debug("Nessun item trovato");
			return null;
		}
		logger.debug("Trovati " + l.size() + " item...");
		
		List<ItemWeb> risultato = new LinkedList<ItemWeb>();
		for (int i = 0; i < l.size(); i++){
			risultato.add(new ItemWeb(l.get(i)));
		}
		
		return risultato;
		
	}
	
	public ItemWeb getItem(Integer idItem){
		Item item = itemRepository.findById(idItem);
	
		if (item!=null) logger.debug("item trovato " + item.toString());
		else logger.debug("Item trovato");
		
		return new ItemWeb(item, tagRepository.findAll());
	}
	
	public List<Option> getAllTag() {
		List<Tag> l = tagRepository.findAll();

		if (l==null) {
			logger.debug("Nessun tag trovato");
			return null;
		}
		
		logger.debug("Trovati " + l.size() + " tag...");
		List<Option> risultato = new LinkedList<Option>();
		for (int i = 0; i < l.size();i++){
			risultato.add(new Option(l.get(i).getId(),l.get(i).getNomeTag()));
		}
		
		return risultato;
	}
	
	
	public ItemWeb saveItem(ItemWeb itemWeb){
		ItemWeb risultato = new ItemWeb();
		
		logger.debug("Salvataggio Item...");

		//salvataggio, in anagrafica tag, dei nuovi tag eventualmente inseriti
		Tag[] tags = null;
		if (itemWeb.getNuoviTag()!=null){
			String[] nuoviTag = itemWeb.getNuoviTag().split("[,.;]");
			if (nuoviTag != null && nuoviTag.length>0)
				tags = salvaNuoviTag(nuoviTag);
		}
		
		//salvataggio del nuovo item
		Item itemSalvato = itemRepository.save(itemWeb.toItem());
		
		//salvataggio degli allegati
		if (itemWeb.getListaFile()!=null)
			for (int i = 0; i < itemWeb.getListaFile().size();i++)
				salvaAllegato(itemWeb.getListaFile().get(i), itemSalvato.getId());
	
		//assegnazione dei tag vecchi all'item
		if (itemWeb.getTagSelezionati()!=null)	
			for (int i = 0; i < itemWeb.getTagSelezionati().size();i++)
				salvaLtTagItem(itemSalvato,itemWeb.getTagSelezionati().get(i).id);
			
		//assegnazione dei tag nuovi all'item
		if (tags!=null)	
			for (int i = 0; i < tags.length;i++)
				salvaLtTagItem(itemSalvato,tags[i].getId());

		//cancellazione degli eventuali file da cancellare
		if (itemWeb.getListaFileDaCancellare()!=null && itemWeb.getListaFileDaCancellare().size()>0)
			for (int i=0;i < itemWeb.getListaFileDaCancellare().size();i++)
				this.allegatoRepository.delete(itemWeb.getListaFileDaCancellare().get(i));
		
		return risultato;
	}
	
	public Tag[] salvaNuoviTag(String[] nuoviTag){
		if (nuoviTag==null || nuoviTag.length==0)return null;
		Tag[] risultato = new Tag[nuoviTag.length];
		for (int i= 0; i < nuoviTag.length;i++){
			risultato[i]=this.tagRepository.save(new Tag(nuoviTag[i]));
		}
		return risultato;
	}
	
	public void salvaAllegato(String nomeFile, Integer idItem){
		Allegato alle = new Allegato();
		try {
			//FileInputStream fis = new FileInputStream(new File(itemWeb.getListaFile().get(i)));
			ByteArrayOutputStream bao = new ByteArrayOutputStream();
			File f = new File(pathFile+"/"+nomeFile);
			//Files.probeContentType(FileSystems.getDefault().getPath(pathFile, itemWeb.getListaFile().get(i)));
			logger.debug("File letto da: " + f.getAbsolutePath());
			org.apache.commons.io.FileUtils.copyFile(f, bao);
			
			alle.setDati(bao.toByteArray());
			alle.setContentType(Files.probeContentType(FileSystems.getDefault().getPath(pathFile, nomeFile)));
			alle.setDataModifica(new Date());
			alle.setDataPubblicazione(new Date());
			alle.setNomeAllegato(nomeFile);
			Item item = new Item();
			item.setId(idItem);
			alle.setArticolo(item);
			
			
			
			allegatoRepository.save(alle);
		} catch (IOException ioe) {
			// TODO Auto-generated catch block
			ioe.printStackTrace();
		}
	}
	
	public void salvaLtTagItem(Item itemSalvato, Integer idTag){
		LkTagItem lk = new LkTagItem();
		lk.setItem(itemSalvato);
		Tag t = new Tag();
		t.setId(idTag);
		lk.setTag(t);
		lkTagItemRepository.save(lk);
	}
	

}
