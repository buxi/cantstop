package de.buxi.cantstop.service;

import de.buxi.cantstop.model.GameTransferObject;

public interface GameService {
	public GameTransferObject startGame() throws GameException;
	public GameTransferObject startTurn() throws GameException;
	public GameTransferObject finishGame(String playerId) throws GameException;
	public GameTransferObject finishTurn(String playerId) throws GameException;
	
	public GameTransferObject getAllGameInformation() throws GameException;
	
	public GameTransferObject throwDices() throws GameException;
	public GameTransferObject executePairs(String chosenPairId, int wayNumber) throws GameException;
	
	public void saveState();
	public void loadState();
	
	/**
	 * @param playerName
	 * @return playerId
	 * @throws GameException
	 */
	public String addPlayer(String playerName) throws GameException;
	
	/**
	 * @param playerName
	 * @return playerId
	 * @throws GameException
	 */
	public String addAutoPlayer() throws GameException;
	
	public GameTransferObject reinitializeGame() throws GameException;
}
