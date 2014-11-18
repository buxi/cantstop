/**
 * 
 */
package de.vt.cantstop.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import de.vt.cantstop.model.DiceNotThrownException;
import de.vt.cantstop.model.GameController;
import de.vt.cantstop.model.GameTransferObject;
import de.vt.cantstop.model.InvalidClimberMovementException;
import de.vt.cantstop.model.InvalidWayNumberException;
import de.vt.cantstop.model.NoClimberOnWayException;
import de.vt.cantstop.model.NoMarkerIsAvailableException;
import de.vt.cantstop.model.NotAvailableClimberException;
import de.vt.cantstop.model.NullClimberException;
import de.vt.cantstop.model.RopePointInvalidUsageException;
import de.vt.cantstop.model.TooManyPlayerException;
/**
 * @author buxi
 *
 */
public class GameServicesWeb implements GameService {
	private GameController gameController;
	private Log log = LogFactory.getLog(GameServicesWeb.class);
	
	@Autowired
	public GameServicesWeb(GameController gameController) {
		super();
		this.gameController = gameController;
	}
	
	@Override
	public GameTransferObject reinitializeGame() throws GameException {
		try {
			return gameController.doReinitialize();
		} catch (DiceNotThrownException | RopePointInvalidUsageException | NoMarkerIsAvailableException | InvalidClimberMovementException | InvalidWayNumberException e) {
			log.error(e.toString());
			throw new GameException(e);
		}
	}
	
	@Override
	public GameTransferObject startGame() throws GameException {
		try {
			return gameController.doGameStart();
		} catch (InvalidWayNumberException | DiceNotThrownException e) {
			log.error(e.toString());
			throw new GameException(e);
		}
	}

	@Override
	public GameTransferObject startTurn() throws GameException {
		try {
			return gameController.doStartGameTurn();
		} catch (InvalidWayNumberException | DiceNotThrownException e) {
			log.error(e.toString());
			throw new GameException(e);
		}
	}

	@Override
	public GameTransferObject finishTurn(String playerId) throws GameException {
		try {
			return gameController.doEndGameTurn();
		} catch (InvalidWayNumberException | DiceNotThrownException | NoMarkerIsAvailableException | RopePointInvalidUsageException | NoClimberOnWayException | InvalidClimberMovementException e) {
			log.error(e.toString());
			throw new GameException(e);
		}
	}

	@Override
	public GameTransferObject getAllGameInformation() throws GameException {
		try {
			return gameController.doGetTransferObject();
		} catch (InvalidWayNumberException | DiceNotThrownException e) {
			log.error(e.toString());
			throw new GameException(e);
		}
	}

	@Override
	public GameTransferObject throwDices() throws GameException {
		try {
			return gameController.doThrowDices();
		} catch (InvalidWayNumberException | DiceNotThrownException | NoMarkerIsAvailableException | RopePointInvalidUsageException | NoClimberOnWayException | InvalidClimberMovementException e) {
			log.error(e.toString());
			throw new GameException(e);
		}
	}

	@Override
	public GameTransferObject executePairs(String chosenPairId,	int wayNumber) throws GameException {
		try {
			if (gameController.getPairsToChooseWithId().containsKey(chosenPairId)) {
				return gameController.doExecutePairs(gameController.getPairsToChooseWithId().get(chosenPairId), wayNumber);	
			} 
			else {
				throw new GameException("Invalid pairId:" +chosenPairId);
			}
			
		} catch (InvalidWayNumberException | DiceNotThrownException | RopePointInvalidUsageException | NotAvailableClimberException | InvalidClimberMovementException | NoMarkerIsAvailableException | NullClimberException | NoClimberOnWayException e) {
			log.error(e.toString());
			throw new GameException(e);
		}
	}
	
	@Override
	public String addPlayer(String playerName) throws GameException {
		try {	
			return gameController.doAddPlayer(playerName);
		} catch (TooManyPlayerException e) {
			log.error(e.toString());
			throw new GameException(e);
		}
	}
	
	@Override
	public GameTransferObject finishGame(String playerId) throws GameException {
		try {	
			gameController.doEndGame(playerId);
			// reinitialize the game after it was finished
			return this.reinitializeGame();
		} catch ( DiceNotThrownException | InvalidWayNumberException e) {
			log.error(e.toString());
			throw new GameException(e);
		}
		
	}

	@Override
	public void saveState() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void loadState() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String addAutoPlayer() throws GameException {
		try {	
			return gameController.doAddAutoPlayer();
		} catch (TooManyPlayerException e) {
			log.error(e.toString());
			throw new GameException(e);
		}
	}
}
