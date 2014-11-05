/**
 * 
 */
package de.buxi.cantstop.aop;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;

import de.buxi.cantstop.model.GameTransferObject;
import de.buxi.cantstop.service.GameException;
import de.buxi.cantstop.service.GameService;

/**
 * @author buxi
 * Simple implementation of an auto-player
 */
public class AutoPlayerRobot {
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

	public void play() throws GameException {
		GameTransferObject toThrow = gameService.throwDices();
		Set<String> pairIds = toThrow.getChoosablePairsWithId().keySet();
		
		gameService.executePairs(pairIds.iterator().next(), -1);
		gameService.finishTurn(playerId);
	}
}
