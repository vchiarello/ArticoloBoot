package it.vito.blog.business;

import it.vito.blog.aspect.AddIndexEntryAnnotation;
import it.vito.blog.db.bean.Allegato;
import it.vito.blog.db.bean.AllegatoInfo;
import it.vito.blog.db.bean.Category;
import it.vito.blog.db.bean.Property;
import it.vito.blog.db.bean.Item;
import it.vito.blog.db.bean.LkCategoryItem;
import it.vito.blog.db.bean.LkPropertyItem;
import it.vito.blog.db.bean.LkTagItem;
import it.vito.blog.db.bean.QItem;
import it.vito.blog.db.bean.Tag;
import it.vito.blog.db.dao.AllegatoInfoRepository;
import it.vito.blog.db.dao.AllegatoRepository;
import it.vito.blog.db.dao.AnagraficaProprietaRepository;
import it.vito.blog.db.dao.CategoryRepository;
import it.vito.blog.db.dao.ItemRepository;
import it.vito.blog.db.dao.LkCategoryItemRepository;
import it.vito.blog.db.dao.LkItemPropertyItemRepository;
import it.vito.blog.db.dao.LkTagItemRepositoryRepository;
import it.vito.blog.db.dao.TagRepository;
import it.vito.blog.index.IndexArticolo;
import it.vito.blog.web.bean.CategoryWeb;
import it.vito.blog.web.bean.ItemPropertyWeb;
import it.vito.blog.web.bean.ItemShopWeb;
import it.vito.blog.web.bean.ItemWeb;
import it.vito.blog.web.bean.Option;

import static org.hamcrest.CoreMatchers.instanceOf;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.util.Date;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;

import org.apache.lucene.queryparser.classic.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

import com.mysema.query.types.Predicate;
import com.mysema.query.types.expr.BooleanExpression;

@Component("gestioneBlog")
@EnableAspectJAutoProxy
public class GestioneBlog {

	private Logger logger = LoggerFactory.getLogger(GestioneBlog.class);
	
	@Autowired
	ItemRepository itemRepository;

	@Autowired
	TagRepository tagRepository;
	
	@Autowired
	LkTagItemRepositoryRepository lkTagItemRepository;
	
	@Autowired
	AllegatoRepository allegatoRepository;
	
	@Autowired
	AllegatoInfoRepository allegatoInfoRepository;
	
	@Autowired
	AnagraficaProprietaRepository anagraficaProprietaRepository;
	
	@Autowired
	LkItemPropertyItemRepository itemPropertyItemRepository;
	
	@Autowired
	IndexArticolo indexArticolo;
	
	@Autowired
	LkCategoryItemRepository lkCategoryItemRepository;
	
	@Autowired
	CategoryRepository categoryRepository;
	
	@Value("${pathFile}")
	String pathFile;

	public Allegato getAllegato(Integer idAllegato, String nomeAllegato){
//		return allegatoRepository.findOne(idAllegato,nomeAllegato);
		return allegatoRepository.findByIdAndNomeAllegato(idAllegato,nomeAllegato);
		
		//return allegato.getDati();
	}

	public List<ItemWeb> getAllItemAttivi(){
		logger.debug("Get lista item attivi...");
		//BooleanExpression QItem.item.dataPubblicazione.in(new Date());
		BooleanExpression dataPubblicazione = QItem.item.dataPubblicazione.before(new Date());
		BooleanExpression dataHidden = QItem.item.dataHidden.isNull().or(QItem.item.dataHidden.after(new Date()));
		BooleanExpression dataScadenza = QItem.item.dataScadenza.isNull().or(QItem.item.dataScadenza.after(new Date()));
		Predicate p = dataPubblicazione.and(dataHidden).and(dataScadenza);
		
		Iterable<Item> l = itemRepository.findAll(p);
		
		
		if (l==null){
			logger.debug("Nessun item trovato");
			return null;
		}
		
		List<ItemWeb> risultato = new LinkedList<ItemWeb>();
		for (Item item : l) {
			risultato.add(new ItemWeb(item));
		}
		
		logger.debug("Trovati " + risultato.size() + " item...");
		
		
		return risultato;
		
	}
	
	public List<ItemWeb> getSearchItemAttivi(List<Integer> idTrovati){
		logger.debug("Search item attivi...");
		if (idTrovati == null || idTrovati.size()==0)return null;
		//BooleanExpression QItem.item.dataPubblicazione.in(new Date());
		BooleanExpression dataPubblicazione = QItem.item.dataPubblicazione.before(new Date());
		BooleanExpression dataHidden = QItem.item.dataHidden.isNull().or(QItem.item.dataHidden.after(new Date()));
		BooleanExpression dataScadenza = QItem.item.dataScadenza.isNull().or(QItem.item.dataScadenza.after(new Date()));
		BooleanExpression listaIdCercati = QItem.item.id.in(idTrovati);
		
		Predicate p = dataPubblicazione.and(dataHidden).and(dataScadenza).and(listaIdCercati);
		
		
		Iterable<Item> l = itemRepository.findAll(p);
		
		
		if (l==null){
			logger.debug("Nessun item trovato");
			return null;
		}
		
		List<ItemWeb> risultato = new LinkedList<ItemWeb>();
		for (Item item : l) {
			risultato.add(new ItemWeb(item));
		}
		
		logger.debug("Trovati " + risultato.size() + " item...");
		
		
		return risultato;
		
	}
	
	public List<AllegatoInfo> getResourceInfo(Integer idItem, String itemName){
		return allegatoInfoRepository.findByIdItemAndNome(idItem,itemName);
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
		else {
			logger.debug("Item non trovato"); return null;
		}
		
		return new ItemWeb(item);
	}
	
	public ItemShopWeb getItemShop(Integer idItem){
		Item item = itemRepository.findById(idItem);
	
		if (item!=null) logger.debug("item shop trovato " + item.toString());
		else {
			logger.debug("Item non trovato"); return null;
		}
		
		ItemShopWeb risultato = new ItemShopWeb(item);
		
		List<LkPropertyItem> props = this.itemPropertyItemRepository.findByItem(item);
		Hashtable<String, List<ItemPropertyWeb>> propsXNome = new Hashtable<String, List<ItemPropertyWeb>>();
		for (int i = 0; i < props.size();i++){
			ItemPropertyWeb ipw = new ItemPropertyWeb(props.get(i).getProp());
			//se in lk è segnato il valore si prende da lk
			if (props.get(i).getValue()!=null)ipw.setValore(props.get(i).getValue());
			
			List<ItemPropertyWeb> lPropWeb = propsXNome.get(ipw.getNome());
			if (lPropWeb == null){
				lPropWeb = new LinkedList<ItemPropertyWeb>();
				propsXNome.put(ipw.getNome(), lPropWeb);
			}
			lPropWeb.add(ipw);
		}
		
		if (propsXNome.get("Colore")!= null) 
			risultato.setColoriSelezionati(propsXNome.get("Colore"));
		if (propsXNome.get("Taglia")!= null) risultato.setTaglieSelezionati(propsXNome.get("Taglia"));
		
		
		if (propsXNome.get("Prezzo")!= null && propsXNome.get("Prezzo").get(0)!=null) 
			risultato.setPrezzo(propsXNome.get("Prezzo").get(0));
		else {
			//se non si era salvato alcun colore allora si prende la proprietà dall'anagrafica.
			//per le proprietà sopra non è necessario perché la combo viene popolata prendendo tutti i valori dall'anagrafica
			List<Property> l = this.anagraficaProprietaRepository.findByNomeProprieta("Prezzo");
			ItemPropertyWeb ipw = new ItemPropertyWeb();
			ipw.setId(l.get(0).getId());
			ipw.setNome("Prezzo");
			risultato.setPrezzo(ipw);
		}
		
		return risultato;
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
		itemPropertyItemRepository.deleteByItem(item);
		itemRepository.delete(item);
		logger.info("Fine cancellazione dell'item con id: " + id);
	}
	
	public ItemWeb saveItem(ItemWeb itemWeb){
		
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
		itemWeb.setId(itemSalvato.getId());
		soloPerAdvice(itemSalvato);
		
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
		
		if (itemWeb.getTipoItem()==3){
			saveProperty(itemSalvato,((ItemShopWeb)itemWeb).getColoriSelezionati());
			saveProperty(itemSalvato,((ItemShopWeb)itemWeb).getTaglieSelezionati());
			saveProperty(itemSalvato,((ItemShopWeb)itemWeb).getPrezzo());
		}
			
		
		return itemWeb;
	}
	
	private void saveProperty(Item itemsalvato, List<ItemPropertyWeb> itemPropertyWeb){
		for (int i = 0; itemPropertyWeb != null && i<itemPropertyWeb.size();i++){
			saveProperty(itemsalvato,itemPropertyWeb.get(i));
		}
	}
	
	private void saveProperty(Item itemsalvato, ItemPropertyWeb itemPropertyWeb){

			List<LkPropertyItem> l = this.itemPropertyItemRepository.findByPropAndItem(itemPropertyWeb.toItemProperty(), itemsalvato);
			LkPropertyItem pi = null;
			if (l!=null && l.size()>0) {
				pi = l.get(0);
			}else{
				pi = new LkPropertyItem();
				pi.setDataInserimento(new Date());
				pi.setProp(itemPropertyWeb.toItemProperty());
				pi.setItem(itemsalvato);
			}
			pi.setValue(itemPropertyWeb.getValore());
			pi.setDataModifica(new Date());
			this.itemPropertyItemRepository.save(pi);
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
	
	public Item soloPerAdvice(Item itemSalvato){
		logger.debug("Metodo adviced per salvaItem...");
		return itemSalvato;
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
	
	public List<ItemPropertyWeb> getProprieta(String nomeProprieta){
		List<Property> l = this.anagraficaProprietaRepository.findByNomeProprieta(nomeProprieta);
		if (l==null || l.size()==0)return null;
		List<ItemPropertyWeb> risultato = new LinkedList<ItemPropertyWeb>();
		for (int i = 0; i < l.size();i++)
			risultato.add(new ItemPropertyWeb(l.get(i)));
		
		return risultato;
		
	}
	
	public List<ItemWeb> cercaItem(String daRicercare){
		try {
			List<Integer> idList = this.indexArticolo.cerca(daRicercare);
			List<ItemWeb> risultato = this.getSearchItemAttivi(idList);
			
			return risultato;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public List<CategoryWeb> getCategory(){
		List<Category> l = this.categoryRepository.findAll();
		if (l==null || l.size()==0)return null;
		return getAlberoCategory(l,null);
	}
	
	private List<CategoryWeb> getAlberoCategory(List<Category> l, Integer nodoPadre){
		List<CategoryWeb> risultato = null;
		int i=0;
		while (l.size()>0 && i < l.size()){
			Category appo = l.get(i);
			if ((nodoPadre==null && appo.getPadre()==null ) || (nodoPadre !=null && appo.getPadre()!=null && nodoPadre.intValue() == appo.getPadre().getId())){
				if(risultato == null) risultato = new LinkedList<CategoryWeb>(); 
				CategoryWeb cw = new CategoryWeb();
				cw.setId(appo.getId());
				cw.setCategoryName(appo.getCategoryName());
				l.remove(i);
				cw.setDescendant(getAlberoCategory(l, appo.getId()));
				risultato.add(cw);
			}else{
				i++;
			}
		}
		return risultato;
	}
	
}
