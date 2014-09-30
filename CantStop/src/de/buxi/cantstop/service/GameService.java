package de.buxi.cantstop.service;

import de.buxi.cantstop.model.GameTransferObject;
import de.buxi.cantstop.model.NotEnoughPlayerException;

public interface GameService {
	public GameTransferObject startGame() throws GameException, NotEnoughPlayerException;
	public GameTransferObject startTurn() throws GameException;
	public GameTransferObject finishGame();
	public GameTransferObject finishTurn() throws GameException;
	
	public GameTransferObject getAllGameInformation() throws GameException;
	
	public GameTransferObject throwDices() throws GameException;
	public GameTransferObject executePairs(String chosenPairId, int wayNumber) throws GameException;
	
	public void saveState();
	public void loadState();
	
	public String addPlayer(String playerName) throws GameException;
}
