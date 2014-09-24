/**
 * 
 */
package de.buxi.cantstop.service;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import org.springframework.context.ApplicationContext;
import de.buxi.cantstop.model.DiceNotThrownException;
import de.buxi.cantstop.model.GameController;
import de.buxi.cantstop.model.GameTransferObject;
import de.buxi.cantstop.model.InvalidClimberMovementException;
import de.buxi.cantstop.model.InvalidWayNumberException;
import de.buxi.cantstop.model.NoClimberOnWayException;
import de.buxi.cantstop.model.NoMarkerIsAvailableException;
import de.buxi.cantstop.model.NotAvailableClimberException;
import de.buxi.cantstop.model.NullClimberException;
import de.buxi.cantstop.model.RopePointInvalidUsageException;
import de.buxi.cantstop.model.TwoDicesPair;
/**
 * @author buxi
 *
 */
public class GameServicesWeb implements GameService {
	private GameController gameController;
	
	public GameServicesWeb(ApplicationContext context) {
		gameController = (GameController)context.getBean("gameController");
	}
	
	@Override
	public GameTransferObject startGame() throws DiceNotThrownException, InvalidWayNumberException {
		return gameController.doGameStart();
	}

	@Override
	public GameTransferObject startTurn() throws DiceNotThrownException, InvalidWayNumberException {
		return gameController.doStartGameTurn();
	}

	@Override
	public GameTransferObject finishGame() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GameTransferObject finishTurn() throws NoMarkerIsAvailableException, RopePointInvalidUsageException, NoClimberOnWayException, InvalidClimberMovementException, InvalidWayNumberException, DiceNotThrownException {
		return gameController.doEndGameTurn();
	}

	@Override
	public GameTransferObject getAllGameInformation() throws DiceNotThrownException, InvalidWayNumberException {
		return gameController.doGetTransferObject();
	}

	@Override
	public GameTransferObject throwDices() throws DiceNotThrownException, InvalidWayNumberException, NoMarkerIsAvailableException, RopePointInvalidUsageException, NoClimberOnWayException, InvalidClimberMovementException {
		return gameController.doThrowDices();
	}

	@Override
	public GameTransferObject executePairs(TwoDicesPair chosenPair,
			int wayNumber) throws DiceNotThrownException,
			RopePointInvalidUsageException, NotAvailableClimberException,
			InvalidWayNumberException, InvalidClimberMovementException,
			NoMarkerIsAvailableException, NullClimberException,
			NoClimberOnWayException {
		return gameController.doExecutePairs(chosenPair, wayNumber);
	}
	public void loadState() {
		InputStream fis = null;
		ObjectInputStream o = null;
		try {
			fis = new FileInputStream("saved.dat");
			o = new ObjectInputStream(fis);
			GameController savedGameController = (GameController) o.readObject();
			this.gameController = savedGameController;
		} catch (IOException e) {
			System.err.println(e);
		} catch (ClassNotFoundException e) {
			System.err.println(e);
		} finally {
			try {
				fis.close();
				o.close();
			} catch (Exception e) {
			}
		}
	}

	public void saveState() {
		OutputStream fos = null;
		ObjectOutputStream o = null;

		try {
			fos = new FileOutputStream("saved.dat");
			o = new ObjectOutputStream(fos);
			o.writeObject( this.gameController);
		} catch (IOException e) {
			System.err.println(e);
		} finally {
			try {
				fos.close();
				o.close();
			} catch (Exception e) {
			}
		}
	}
}
