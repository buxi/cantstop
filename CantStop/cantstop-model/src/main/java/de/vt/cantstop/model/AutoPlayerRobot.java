/**
 * 
 */
package de.vt.cantstop.model;

import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author buxi
 * Simple implementation of an auto-player
 */
public class AutoPlayerRobot implements Runnable{
	Log log = LogFactory.getLog(AutoPlayerRobot.class);
	
	private String playerId;
	
	@Autowired
	private GameController gameController;

	/**
	 * @param gameService
	 */
	public AutoPlayerRobot(GameController gameController) {
		super();
		this.gameController = gameController;
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
		try {
			gameController.doThrowDices();
			try {
				Thread.sleep(1000l);
			} catch (InterruptedException e) {
				log.warn(e);
			}
			
			List<TwoDicesPair> pairs = gameController.getPairsToChoose();
			gameController.doExecutePairs(pairs.get(0), -1);
			try {
				Thread.sleep(1000l);
			} catch (InterruptedException e) {
				log.warn(e);
			}

			gameController.doEndGameTurn();
		} catch ( DiceNotThrownException | RopePointInvalidUsageException | NotAvailableClimberException | InvalidWayNumberException | InvalidClimberMovementException | NoMarkerIsAvailableException | NullClimberException | NoClimberOnWayException e) {
			log.error("Robot player failed:" +e);
		}
	}

	@Override
	public void run() {
		this.play();
	}
}
