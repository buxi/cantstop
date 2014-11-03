package de.buxi.cantstop.aop;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import de.buxi.cantstop.dao.GameInfoDao;
import de.buxi.cantstop.model.GameTransferObject;
@Component
@Aspect
public class TestLoggingAspect {
	private Log log = LogFactory.getLog(this.getClass());
	
	private GameInfoDao dao; 
	
	@AfterReturning(pointcut="execution(* de.buxi.cantstop.service.GameService.finishTurn(..))", returning="returnValue")
	public void logFinishTurn(JoinPoint joinPoint, Object returnValue) {
		storeGameInfoCommon(joinPoint, returnValue);
	}

	@AfterReturning(pointcut="execution(* de.buxi.cantstop.service.GameService.throwDices(..))", returning="returnValue")
	public void logThrowDices(JoinPoint joinPoint, Object returnValue) {
		storeGameInfoCommon(joinPoint, returnValue);
	}
	
	@AfterReturning(pointcut="execution(* de.buxi.cantstop.service.GameService.executePairs(..))", returning="returnValue")
	public void logExecutePairs(JoinPoint joinPoint, Object returnValue) {
		storeGameInfoCommon(joinPoint, returnValue);
	}

	/**
	 * @param joinPoint
	 * @param returnValue
	 */
	protected void storeGameInfoCommon(JoinPoint joinPoint, Object returnValue) {
		log.info("The method " + joinPoint.getSignature().getName()
		+ "() ends with return value:" + returnValue);

		GameTransferObject to = (GameTransferObject)returnValue;
		dao.insert(joinPoint.getSignature().getName(), to);
	}

	/**
	 * @param dao the dao to set
	 */
	public void setDao(GameInfoDao dao) {
		this.dao = dao;
	}
}