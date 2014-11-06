/**
 * 
 */
package de.buxi.cantstop.aop;

import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import de.buxi.cantstop.model.GameTransferObject;
import de.buxi.cantstop.service.GameException;
import de.buxi.cantstop.service.GameService;

/**
 * @author buxi
 * Simple implementation of an auto-player
 */
public class AutoPlayerRobot implements Runnable{
	Log log = LogFactory.getLog(AutoPlayerRobot.class);
	
	private String playerId;
	
	@Autowired
	private GameService gameService;

	/**
	 * @param gameService
	 */
	public AutoPlayerRobot(GameService gameService) {
		super();
		this.gameService = gameService;
	}

	/**
	 * @return the playerId
	 */
	public String getPlayerId() {
		return playerId;
	}

	/**
	 * @param playerId the playerId to set
	 */
	public void setPlayerId(String playerId) {
		this.playerId = playerId;
	}

	public void play() {
		GameTransferObject toThrow;
		try {
			toThrow = gameService.throwDices();
			try {
				Thread.sleep(1000l);
			} catch (InterruptedException e) {
				log.warn(e);
			}
			
			Set<String> pairIds = toThrow.getChoosablePairsWithId().keySet();
			gameService.executePairs(pairIds.iterator().next(), -1);
			try {
				Thread.sleep(1000l);
			} catch (InterruptedException e) {
				log.warn(e);
			}

			gameService.finishTurn(playerId);
		} catch (GameException e) {
			log.error("Robot player failed:" +e);
		}
	}

	@Override
	public void run() {
		this.play();
	}
}
