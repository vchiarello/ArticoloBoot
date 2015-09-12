package it.vito.blog.aspect;


import it.vito.blog.db.bean.Item;
import it.vito.blog.index.IndexArticolo;

import java.io.IOException;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@Aspect
public class AdviceOperazioni {

	private static final Logger logger = LoggerFactory.getLogger(AdviceOperazioni.class);

	@Autowired
	private IndexArticolo index;

	
	@AfterReturning(pointcut="it.vito.blog.aspect.PointcutOperazioni.operazione1Index()",returning="messaggio")
    public void adviceOperazione1(String messaggio) {
		logger.debug("sei in adviceOperazione1 con messaggio:" + messaggio);
    }
	
	@After(value="it.vito.blog.aspect.PointcutOperazioni.operazione2Index()")
    public void adviceOperazione1() {
		logger.debug("sei in adviceOperazione2");
    }

	@After(value="it.vito.blog.aspect.PointcutOperazioni.removeIndexEntry() && args(articolo)")
    public void deleteIndexEntryAdvice(Item articolo) {
		try {
			if (articolo!=null)
				index.deleteEntry(articolo);
		} catch (IOException e) {
			logger.error("Fallita cancellazione dell'articolo: " + articolo + " nell'indice Lucene.");
			e.printStackTrace();
		}
    }

	@AfterReturning(pointcut="it.vito.blog.aspect.PointcutOperazioni.addIndex()",returning="articolo")
    public void aggiungiNuovo(Item articolo) {
		try {
			index.addEntry(articolo);
		} catch (IOException e) {
			logger.error("Fallito inserimento dell'articolo: " + articolo + " nell'indice Lucene.");
			e.printStackTrace();
		}
    }
	
}
