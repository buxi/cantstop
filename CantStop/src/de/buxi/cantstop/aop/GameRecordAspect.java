package de.buxi.cantstop.aop;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Date;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import de.buxi.cantstop.dao.GameInfoDao;
import de.buxi.cantstop.model.GameTransferObject;
import de.buxi.cantstop.utils.ObjectManipulationHelper;

/**
 * AOP Aspect to record game activities
 * Attaching to GameService methods
 * Activities are stored in database
 * @author buxi
 *
 */
@Component
@Aspect
public class GameRecordAspect implements ApplicationContextAware {
	private Log log = LogFactory.getLog(GameRecordAspect.class);
	
	@Autowired
	private GameInfoDao dao;

	private ApplicationContext ac; 
	
	@Around("execution(* de.buxi.cantstop.service.GameService.finishTurn(..))")
	public void recordFinishTurn(ProceedingJoinPoint joinPoint) throws Throwable {
		//fix for bug 17: Wrong playerId when finishTurn called
		Object[] args = joinPoint.getArgs();
		int playerId = Integer.valueOf((String)args[0]);
		
		Object returnValue = joinPoint.proceed();
		GameTransferObject to = (GameTransferObject)returnValue;
		storeGameInfoCommon(joinPoint, playerId, to);
		
		// launch auto-player if it is needed
		if (to.actualPlayer.getAutoPlayer()) {
			log.info("Starting Autoplayer: " + to.actualPlayerNumber);
			AutoPlayerRobot robot = (AutoPlayerRobot)ac.getBean("autoplayerRobot");
			robot.setPlayerId(Integer.toString(to.actualPlayerNumber));
			robot.play();
		}
	}

	@AfterReturning(pointcut="execution(* de.buxi.cantstop.service.GameService.throwDices(..))", returning="returnValue")
	public void recordThrowDices(JoinPoint joinPoint, Object returnValue) {
		GameTransferObject to = (GameTransferObject)returnValue;
		storeGameInfoCommon(joinPoint, to.actualPlayer.getOrder(), to);
	}
	
	@AfterReturning(pointcut="execution(* de.buxi.cantstop.service.GameService.executePairs(..))", returning="returnValue")
	public void recordExecutePairs(JoinPoint joinPoint, Object returnValue) {
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

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		ac = applicationContext;
	}
}