package de.buxi.cantstop.aop;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
@Component
@Aspect
public class TestLoggingAspect {
	private Log log = LogFactory.getLog(this.getClass());

	/*@Before("execution(* GameServicesWeb.addPlayer(..))")
	public void logBefore() {
	log.info("The method addPlayer() begins");
	}
	*/
	@AfterReturning(pointcut="execution(* *.addPlayer(..))", returning="returnValue")
	public void logAfterReturning(JoinPoint joinPoint, Object returnValue) {
		log.info("The method " + joinPoint.getSignature().getName()
		+ "() ends with return value:" + returnValue);
		
		}
}