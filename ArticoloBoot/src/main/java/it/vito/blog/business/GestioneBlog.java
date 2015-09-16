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
		
		return new ItemWeb(item, getAllTag());
	}
	
	public List<Tag> getAllTag() {
		List<Tag> l = tagRepository.findAll();
		if (l!=null) logger.debug("Trovati " + l.size() + " tag...");
		else logger.debug("Nessun tag trovato");
		return l;
	}
	
	
	public ItemWeb saveItem(ItemWeb itemWeb){
		ItemWeb risultato = new ItemWeb();
		
		logger.debug("Salvataggio Item...");

		Item itemSalvato = itemRepository.save(itemWeb.toItem());
		
		if (itemWeb.getListaFile()!=null)
			for (int i = 0; i < itemWeb.getListaFile().size();i++){
				Allegato alle = new Allegato();
				try {
					//FileInputStream fis = new FileInputStream(new File(itemWeb.getListaFile().get(i)));
					ByteArrayOutputStream bao = new ByteArrayOutputStream();
					File f = new File(pathFile+"/"+itemWeb.getListaFile().get(i));
					//Files.probeContentType(FileSystems.getDefault().getPath(pathFile, itemWeb.getListaFile().get(i)));
					logger.debug("File letto da: " + f.getAbsolutePath());
					org.apache.commons.io.FileUtils.copyFile(f, bao);
					alle.setDati(bao.toByteArray());
					alle.setContentType(Files.probeContentType(FileSystems.getDefault().getPath(pathFile, itemWeb.getListaFile().get(i))));
					alle.setDataModifica(new Date());
					alle.setDataPubblicazione(new Date());
					alle.setNomeAllegato(itemWeb.getListaFile().get(i));
					Item item = new Item();
					item.setId(itemSalvato.getId());
					alle.setArticolo(item);
					
					allegatoRepository.save(alle);
				} catch (IOException ioe) {
					// TODO Auto-generated catch block
					ioe.printStackTrace();
				}
			}	
	
		if (itemWeb.getTagSelezionati()!=null)	{
			for (int i = 0; i < itemWeb.getTagSelezionati().size();i++){
				LkTagItem lk = new LkTagItem();
				lk.setItem(itemSalvato);
				Tag t = new Tag();
				t.setId(itemWeb.getTagSelezionati().get(i).id);
				lk.setTag(t);
				lkTagItemRepository.save(lk);
				
			}
		}
		
		return risultato;
	}
	
}
