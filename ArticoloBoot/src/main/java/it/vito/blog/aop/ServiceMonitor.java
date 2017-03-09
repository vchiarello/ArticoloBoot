package it.vito.blog.aop;

import java.io.IOException;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import it.vito.blog.index.IndexArticolo;
import it.vito.blog.web.bean.ItemWeb;

@Aspect
@Component
public class ServiceMonitor {
	
	@Autowired
	IndexArticolo indexArticolo;

	private Logger logger = LoggerFactory.getLogger(ServiceMonitor.class);
	
//	@AfterReturning("execution(* it.vito..*GestioneBlog*.*(..))")
//	public void logServiceAccess(JoinPoint joinPoint) {
//		System.out.println("Completed Ciao Vito: " + joinPoint);
//	}
	@AfterReturning(value="execution(* it.vito..*GestioneBlog.saveItem(..))", returning = "returnValue")
	public void updateLuceneIndex(JoinPoint joinPoint, Object returnValue) {
		logger.info("dopo il saveItem: " + joinPoint);
		try {
			indexArticolo.addEntry(((ItemWeb)returnValue).toItem());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@AfterReturning("execution(* it.vito..*GestioneBlog*.soloPerAdvice(..))")
	public void testAop(JoinPoint joinPoint) {
		logger.info("Dopo solo per Advice: " + joinPoint);
	}

	@Before("execution(* it.vito..*GestioneBlog*.getAllItemAttivi())")
	public void primaGetAllItemAttivi(JoinPoint joinPoint) {
		logger.info("Prima di tutti gli item attivi");
	}

}
