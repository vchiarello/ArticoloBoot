package it.vito.blog.index;

import it.vito.blog.db.bean.Item;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

import javax.validation.Valid;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.IntField;
import org.apache.lucene.document.LongField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
@Component("indexArticolo")
public class IndexArticolo {

	private static final Log log = LogFactory.getLog(IndexArticolo.class);

	@Value("${path.index.lucene}")
	private String path;

	private IndexWriter writer;
	
	public void setPath(String path) throws IOException {
		this.path = path;
		if (writer ==null)
			this.creaIndice();
	}
	
	public IndexArticolo() throws IOException {
	}

	public IndexArticolo(String path) throws IOException {
		this.path = path;
		this.creaIndice();
	}

	public void creaIndice() throws IOException{
		 Directory dir;
		try {
			dir = FSDirectory.open(new File(path));
			// :Post-Release-Update-Version.LUCENE_XY:
			Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_48);
			IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_48, analyzer);
			iwc.setOpenMode(OpenMode.CREATE_OR_APPEND);
			
			writer = new IndexWriter(dir, iwc);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		}
	}

	public boolean addEntry(Item item) throws IOException{
		if (item == null)return false;
        Document doc = new Document();

        Field pathField = new IntField("id", item.getId(), Field.Store.YES);
        doc.add(pathField);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        Long d = null;
        if (item.getDataPubblicazione()!=null){
        	d = new Long(sdf.format(item.getDataPubblicazione()));
            Field dataPubblicazioneField = new LongField("dataPubblicazione", d, Field.Store.YES);
        	doc.add(dataPubblicazioneField);
        }
        
        Field contenuto = new TextField("contenuto", new BufferedReader(new InputStreamReader(new ByteArrayInputStream(item.getTesto().getBytes()),StandardCharsets.UTF_8)));
        doc.add(contenuto);

        Field titolo = new StringField("titolo", item.getTitolo(), Field.Store.YES);
        doc.add(titolo);

        if (writer.getConfig().getOpenMode() == OpenMode.CREATE) {
			log.debug("adding item " + item.getId() + " to lucene.");
			writer.addDocument(doc);
			writer.commit();
        } else {
			log.debug("updating item " + item.getId() + " to lucene.");
			writer.updateDocument(new Term("id", item.getId()+""), doc);
			writer.commit();
        }
		return true;
	}
	
	public boolean deleteEntry(Item item) throws IOException{
		if (item == null)return false;
        
        Term termId = new Term("id", item.getId()+"");
        
    	log.debug("sto per cancellare gli articoli con id: " + item.getId());
		writer.deleteDocuments(termId);
		writer.commit();
		return true;
	}
	
	public List<Item> cerca(String cosaCercare)throws IOException,org.apache.lucene.queryparser.classic.ParseException{
		List<Item> risultato = null;
		IndexReader reader = DirectoryReader.open(FSDirectory.open(new File(this.path)));
	    IndexSearcher searcher = new IndexSearcher(reader);

	    Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_48);
	    QueryParser parser = new QueryParser(Version.LUCENE_48, "contenuto", analyzer);
		Query query = parser.parse(cosaCercare);
	    TopDocs results = searcher.search(query, 25);
	    ScoreDoc[] hits = results.scoreDocs;
	    for (int i = 0; i < hits.length; i++){
	    	Item art = new Item();
	        Document doc = searcher.doc(hits[i].doc);
	    	art.setId(new Integer(doc.get("id")));
	    	if (risultato == null)risultato = new LinkedList<Item>();
	    	risultato.add(art);
	    }

		return risultato;
	}

	public List<Item> getAll()throws IOException,org.apache.lucene.queryparser.classic.ParseException{
		List<Item> risultato = null;
		IndexReader reader = DirectoryReader.open(FSDirectory.open(new File(this.path)));
	    
	    for (int i = 0; i < reader.maxDoc(); i++){
	    	Item art = new Item();
	        Document doc = reader.document(i);
	    	art.setId(new Integer(doc.get("id")));
	    	art.setTesto(doc.get("titolo"));
	    	if (risultato == null)risultato = new LinkedList<Item>();
	    	risultato.add(art);
	    }

		return risultato;
	}
}
