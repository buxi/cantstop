package de.vt.cantstop.aop;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Date;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import de.vt.cantstop.dao.GameInfoDao;
import de.vt.cantstop.model.GameTransferObject;
import de.vt.cantstop.utils.ObjectManipulationHelper;

/**
 * AOP Aspect to record game activities
 * Attaching to GameService methods
 * Activities are stored in database
 * @author buxi
 *
 */
//@Component
@Aspect
public class GameRecordAspect {
	private Log log = LogFactory.getLog(GameRecordAspect.class);
	
	@Autowired
	private GameInfoDao dao;

	
	@Around("execution(* de.vt.cantstop.service.GameService.finishTurn(..))")
	public void recordFinishTurn(ProceedingJoinPoint joinPoint) throws Throwable {
		//fix for bug 17: Wrong playerId when finishTurn called
		Object[] args = joinPoint.getArgs();
		int playerId = Integer.valueOf((String)args[0]);
		
		Object returnValue = joinPoint.proceed();
		GameTransferObject to = (GameTransferObject)returnValue;
		storeGameInfoCommon(joinPoint, playerId, to);
	}

	@AfterReturning(pointcut="execution(* de.vt.cantstop.service.GameService.throwDices(..))", returning="returnValue")
	public void recordThrowDices(JoinPoint joinPoint, Object returnValue) {
		log.info("recordThrowDices called");
		GameTransferObject to = (GameTransferObject)returnValue;
		storeGameInfoCommon(joinPoint, to.actualPlayer.getOrder(), to);
	}
	
	@AfterReturning(pointcut="execution(* de.vt.cantstop.service.GameService.executePairs(..))", returning="returnValue")
	public void recordExecutePairs(JoinPoint joinPoint, Object returnValue) {
		log.info("recordExecutePairs called");
		GameTransferObject to = (GameTransferObject)returnValue;
		storeGameInfoCommon(joinPoint, to.actualPlayer.getOrder(), to);
	}

	/**
	 * @param joinPoint
	 * @param returnValue
	 * @param to 
	 */
	protected void storeGameInfoCommon(JoinPoint joinPoint, int playerId, GameTransferObject to) {
		byte[] packedTransferObject = ObjectManipulationHelper.serializeAndPack(to);
		try {
			dao.insert(to.getGameId(), new java.sql.Timestamp(new Date().getTime()), 
				joinPoint.getSignature().getName(), 
				playerId, 
				packedTransferObject, to.description
			);
		} catch (DataAccessException e) {
			log.warn("Problem mit database in Aspect:" + e);
		}
	}

	/**
	 * @param dao the dao to set
	 */
	public void setDao(GameInfoDao dao) {
		this.dao = dao;
	}
}