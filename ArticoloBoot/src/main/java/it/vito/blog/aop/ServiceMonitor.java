package it.vito.blog.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ServiceMonitor {

	@AfterReturning("execution(* it.vito..*GestioneBlog*.*(..))")
	public void logServiceAccess(JoinPoint joinPoint) {
		System.out.println("Completed Ciao Vito: " + joinPoint);
	}

	@Before("execution(* it.vito..*GestioneBlog*.getAllItemAttivi())")
	public void primaGetAllItemAttivi(JoinPoint joinPoint) {
		System.out.println("Ciao Vito prima degli item attivi");
	}

}
