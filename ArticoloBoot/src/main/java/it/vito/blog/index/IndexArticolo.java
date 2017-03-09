package it.vito.blog.index;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.IntPoint;
import org.apache.lucene.document.LongPoint;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import it.vito.blog.db.bean.Item;
import it.vito.blog.web.bean.ItemWeb;
@Configuration
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
			dir = FSDirectory.open(Paths.get(path));

			Analyzer analyzer = new StandardAnalyzer();
			IndexWriterConfig iwc = new IndexWriterConfig(analyzer);
			iwc.setOpenMode(OpenMode.CREATE_OR_APPEND);
			
			writer = new IndexWriter(dir, iwc);
		} catch (IOException e) {		
			e.printStackTrace();	
			throw e;
		}
	}

	public boolean addEntry(Item item) throws IOException{
		if (this.writer==null)creaIndice();
		if (item == null)return false;
        Document doc = new Document();

        Field pathField = new IntPoint("id", item.getId());
        doc.add(pathField);

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        Long d = null;
        if (item.getDataPubblicazione()!=null){
        	d = new Long(sdf.format(item.getDataPubblicazione()));
            Field dataPubblicazioneField = new LongPoint("dataPubblicazione", d);
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
		IndexReader reader = DirectoryReader.open(FSDirectory.open(Paths.get(this.path)));
	    IndexSearcher searcher = new IndexSearcher(reader);

	    Analyzer analyzer = new StandardAnalyzer();
	    QueryParser parser = new QueryParser("contenuto", analyzer);
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
		IndexReader reader = DirectoryReader.open(FSDirectory.open(Paths.get(this.path)));
	    
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
