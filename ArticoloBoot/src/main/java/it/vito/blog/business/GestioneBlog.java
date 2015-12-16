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
	
	public void deleteItem(int id){
		logger.info("cancellazione dell'item con id: " + id);
		Item item = itemRepository.findById(id);
		lkTagItemRepository.deleteByItem(item);
		allegatoRepository.deleteByItem(item);
		itemRepository.delete(item);
		logger.info("Fine cancellazione dell'item con id: " + id);
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
		Item it = itemWeb.toItem();
		it.setDataModifica(new Date());
		Item itemSalvato = itemRepository.save(it);
		
		//salvataggio degli allegati
		if (itemWeb.getListaFile()!=null)
			for (int i = 0; i < itemWeb.getListaFile().size();i++)
				salvaAllegato(itemWeb.getListaFile().get(i).nomeAllegato, itemWeb.getListaFile().get(i).getNote(), itemSalvato.getId());
	
		//assegnazione dei tag vecchi all'item
		salvaLkTagItem(itemSalvato,itemWeb.getTagSelezionati());
			
		//assegnazione dei tag nuovi all'item
		if (tags!=null)	
			for (int i = 0; i < tags.length;i++)
				salvaLkTagItem(itemSalvato,tags[i].getId());

		//cancellazione degli eventuali file da cancellare
		if (itemWeb.getListaFileDaCancellare()!=null && itemWeb.getListaFileDaCancellare().size()>0)
			for (int i=0;i < itemWeb.getListaFileDaCancellare().size();i++)
				this.allegatoRepository.delete(itemWeb.getListaFileDaCancellare().get(i));
		
		return itemWeb;
	}
	
	public Tag[] salvaNuoviTag(String[] nuoviTag){
		if (nuoviTag==null || nuoviTag.length==0)return null;
		Tag[] risultato = new Tag[nuoviTag.length];
		for (int i= 0; i < nuoviTag.length;i++){
			risultato[i]=this.tagRepository.save(new Tag(nuoviTag[i]));
		}
		return risultato;
	}
	
	public void salvaAllegato(String nomeFile, String testo, Integer idItem){
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
			alle.setTesto(testo);
			Item item = new Item();
			item.setId(idItem);
			alle.setItem(item);
			
			
			
			allegatoRepository.save(alle);
		} catch (IOException ioe) {
			// TODO Auto-generated catch block
			ioe.printStackTrace();
		}
	}
	
	private void salvaLkTagItem(Item itemSalvato, List<Option> tags ){
		//se nessun tag è da salvare si esce
		if (tags == null)return;
		//lista tag salvati per l'item
		List<LkTagItem> l = lkTagItemRepository.findByItem(itemSalvato);
		
		//per ogni tag da salvare
		for (int i = 0; i < tags.size();i++){
			//si verifica se già è assegnato
			boolean trovato = false;
			for (LkTagItem lkTagItem : l){
				if (lkTagItem.getTag().getId()==tags.get(i).getId().intValue()){
					trovato = true;
					l.remove(lkTagItem);
					break;
				}
			}
			//se non è presente si salva
			if (!trovato )
				salvaLkTagItem(itemSalvato, tags.get(i).getId());
		}
		
		//si cancellano tutti i tag vecchi non più associati
		for (LkTagItem lkTagItem : l){
			lkTagItemRepository.delete(lkTagItem);
		}

	}
	
	public void salvaLkTagItem(Item itemSalvato, Integer idTag){
		LkTagItem lk = new LkTagItem();
		lk.setItem(itemSalvato);
		Tag t = new Tag();
		t.setId(idTag);
		lk.setTag(t);
		lkTagItemRepository.save(lk);
	}
	

}
