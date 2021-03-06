/**
 * 
 */
package de.vt.cantstop.service;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

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
/**
 * @author buxi
 *
 */
public class GameServicesConsole implements GameService {
	private GameController gameController;
	private Log log = LogFactory.getLog(GameServicesConsole.class);
	
	/**
	 * @param gameController
	 */
	public GameServicesConsole(GameController gameController) {
		super();
		this.gameController = gameController;
	}

	@Override
	public GameTransferObject reinitializeGame() throws GameException {
		return null;
		// TODO Auto-generated method stub
		
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
		} catch (DiceNotThrownException | InvalidWayNumberException e) {
			log.error(e.toString());
			throw new GameException(e);
		}
	}


	@Override
	public GameTransferObject finishTurn(String playerId) throws GameException {
		try {
			return gameController.doEndGameTurn();
		} catch (NoMarkerIsAvailableException | RopePointInvalidUsageException
				| NoClimberOnWayException | InvalidClimberMovementException
				| InvalidWayNumberException | DiceNotThrownException e) {
			log.error(e.toString());
			throw new GameException(e);
		}
	}

	@Override
	public GameTransferObject getAllGameInformation() throws GameException {
		try {
			return gameController.doGetTransferObject();
		} catch (DiceNotThrownException | InvalidWayNumberException e) {
			log.error(e.toString());
			throw new GameException(e);
		}
	}

	@Override
	public GameTransferObject throwDices() throws GameException {
		try {
			return gameController.doThrowDices();
		} catch (DiceNotThrownException | InvalidWayNumberException
				| NoMarkerIsAvailableException | RopePointInvalidUsageException
				| NoClimberOnWayException | InvalidClimberMovementException e) {
			log.error(e.toString());
			throw new GameException(e);
		}
	}

	@Override
	public String addPlayer(String playerName) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public void loadState() {
		// Java 7 feature: ARM
		try (
			InputStream fis = new FileInputStream("saved.dat");
			ObjectInputStream o = new ObjectInputStream(fis);){
			
			GameController savedGameController = (GameController) o.readObject();
			this.gameController = savedGameController;
		} catch (ClassNotFoundException | IOException e) {
			System.err.println(e);
		}
	}

	public void saveState() {
		// Java 7 feature: ARM
		try (
			OutputStream fos = new FileOutputStream("saved.dat");
			ObjectOutputStream o = new ObjectOutputStream(fos);){
			o.writeObject( this.gameController);
		} catch (IOException e) {
			System.err.println(e);
		}
	}

	@Override
	public GameTransferObject executePairs(String chosenPairId, int wayNumber)
			throws GameException {
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
	public GameTransferObject finishGame(String playerId) throws GameException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String addAutoPlayer() throws GameException {
		// TODO Auto-generated method stub
		return null;
	}
}
