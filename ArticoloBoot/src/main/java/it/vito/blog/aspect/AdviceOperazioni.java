package it.vito.blog.aspect;


import it.vito.blog.db.bean.Item;
import it.vito.blog.index.IndexArticolo;

import java.io.IOException;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Aspect
@Component("adviceOperazioni")
public class AdviceOperazioni {

	private static final Logger logger = LoggerFactory.getLogger(AdviceOperazioni.class);

	@Autowired
	private IndexArticolo indexArticolo;

	
	@AfterReturning(pointcut="it.vito.blog.aspect.PointcutOperazioni.operazione1Index()",returning="messaggio")
    public void adviceOperazione1(String messaggio) {
		logger.debug("sei in adviceOperazione1 con messaggio:" + messaggio);
    }
	
	@After(value="it.vito.blog.aspect.PointcutOperazioni.operazione2Index()")
    public void adviceOperazione1() {
		logger.debug("sei in adviceOperazione2");
    }

	@After(value="it.vito.blog.aspect.PointcutOperazioni.removeIndexEntry() && args(item)")
    public void deleteIndexEntryAdvice(Item item) {
		try {
			if (item!=null)
				indexArticolo.deleteEntry(item);
		} catch (IOException e) {
			logger.error("Fallita cancellazione dell'item: " + item + " nell'indice Lucene.");
			e.printStackTrace();
		}
    }

	@AfterReturning(pointcut="it.vito.blog.aspect.PointcutOperazioni.addIndex()",returning="item")
    public void aggiungiNuovo(Item item) {
		try {
			logger.debug("Si sta per aggiungere un item: " + item + " nell'indice Lucene.");
			//indexArticolo.addEntry(item);
		} catch (Exception e) {
			logger.error("Fallito inserimento dell'item: " + item + " nell'indice Lucene.");
			e.printStackTrace();
		}
    }
	
//	@Before(value="it.vito.blog.aspect.PointcutOperazioni.addIndex2()")
    public void aggiungiNuovo() {
		try {
			logger.debug("Prima di aggiungi nuovo 2");
		} catch (Exception e) {
			logger.error("Fallito prima di aggiungi nuovo");
			e.printStackTrace();
		}
    }
	
}
