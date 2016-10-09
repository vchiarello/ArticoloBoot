package it.vito.blog.aspect;


import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class PointcutOperazioni {

	@Pointcut("execution(@it.vito.blog.aspect.Operazione1 * *.*(..))")
    public void operazione1Index() {
    }

	@Pointcut("execution(@it.vito.blog.aspect.Operazione2 * *.*(..))")
    public void operazione2Index() {
    }

	@Pointcut("execution(@it.vito.blog.aspect.RemoveIndexEntryAnnotation * *.*(..))")
    public void removeIndexEntry() {
    }

	@Pointcut("execution(@it.vito.blog.aspect.AddIndexEntryAnnotation * *.*(..))")
    public void addIndex() {
    }
	
//	@Pointcut("execution(@it.vito.blog.business.GestioneBlog.saveItem(..))")
//    public void addIndex2() {
//    }
	
}
