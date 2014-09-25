package de.buxi.cantstop.service;

import de.buxi.cantstop.model.DiceNotThrownException;
import de.buxi.cantstop.model.GameTransferObject;
import de.buxi.cantstop.model.InvalidClimberMovementException;
import de.buxi.cantstop.model.InvalidWayNumberException;
import de.buxi.cantstop.model.NoClimberOnWayException;
import de.buxi.cantstop.model.NoMarkerIsAvailableException;
import de.buxi.cantstop.model.NotAvailableClimberException;
import de.buxi.cantstop.model.NotEnoughPlayerException;
import de.buxi.cantstop.model.NullClimberException;
import de.buxi.cantstop.model.RopePointInvalidUsageException;
import de.buxi.cantstop.model.TwoDicesPair;

public interface GameService {
	public GameTransferObject startGame() throws DiceNotThrownException, InvalidWayNumberException, NotEnoughPlayerException;
	public GameTransferObject startTurn() throws DiceNotThrownException, InvalidWayNumberException;
	public GameTransferObject finishGame();
	public GameTransferObject finishTurn() throws NoMarkerIsAvailableException, RopePointInvalidUsageException, NoClimberOnWayException, InvalidClimberMovementException, InvalidWayNumberException, DiceNotThrownException;
	
	public GameTransferObject getAllGameInformation() throws DiceNotThrownException, InvalidWayNumberException;
	
	public GameTransferObject throwDices() throws DiceNotThrownException, InvalidWayNumberException, NoMarkerIsAvailableException, RopePointInvalidUsageException, NoClimberOnWayException, InvalidClimberMovementException;
	public GameTransferObject executePairs(TwoDicesPair chosenPair, int wayNumber) throws DiceNotThrownException, RopePointInvalidUsageException, NotAvailableClimberException, InvalidWayNumberException, InvalidClimberMovementException, NoMarkerIsAvailableException, NullClimberException, NoClimberOnWayException;
	
	public void saveState();
	public void loadState();
	
	public String addPlayer(String playerName) throws TooManyPlayerException, DiceNotThrownException, InvalidWayNumberException;
}
