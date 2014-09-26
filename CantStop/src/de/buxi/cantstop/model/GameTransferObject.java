/**
 * 
 */
package de.buxi.cantstop.model;

import java.util.Collection;
import java.util.List;

/**
 * @author buxi
 * Transfer Object for GameController
 * is a must http://www.oracle.com/technetwork/articles/javaee/transferobject-139757.html
 * 
 * TODO UNITTEST ?
 */

public class GameTransferObject {
	public GameState gameState;
	public Player actualPlayer;
	public List<TwoDicesPair> possiblePairs;
	public String boardDisplay;
	public int actualPlayerNumber;
	public List<Player> playerList;
	public Collection<Dice> dices;
	public String errorMessage;
	public List<TwoDicesPair> choosablePairs;
	/**
	 * @return the gameState
	 */
	public GameState getGameState() {
		return gameState;
	}
	/**
	 * @param gameState the gameState to set
	 */
	public void setGameState(GameState gameState) {
		this.gameState = gameState;
	}
	/**
	 * @return the actualPlayer
	 */
	public Player getActualPlayer() {
		return actualPlayer;
	}
	/**
	 * @param actualPlayer the actualPlayer to set
	 */
	public void setActualPlayer(Player actualPlayer) {
		this.actualPlayer = actualPlayer;
	}
	/**
	 * @return the possiblePairs
	 */
	public List<TwoDicesPair> getPossiblePairs() {
		return possiblePairs;
	}
	/**
	 * @param possiblePairs the possiblePairs to set
	 */
	public void setPossiblePairs(List<TwoDicesPair> possiblePairs) {
		this.possiblePairs = possiblePairs;
	}
	/**
	 * @return the boardDisplay
	 */
	public String getBoardDisplay() {
		return boardDisplay;
	}
	/**
	 * @param boardDisplay the boardDisplay to set
	 */
	public void setBoardDisplay(String boardDisplay) {
		this.boardDisplay = boardDisplay;
	}
	/**
	 * @return the actualPlayerNumber
	 */
	public int getActualPlayerNumber() {
		return actualPlayerNumber;
	}
	/**
	 * @param actualPlayerNumber the actualPlayerNumber to set
	 */
	public void setActualPlayerNumber(int actualPlayerNumber) {
		this.actualPlayerNumber = actualPlayerNumber;
	}
	/**
	 * @return the playerList
	 */
	public List<Player> getPlayerList() {
		return playerList;
	}
	/**
	 * @param playerList the playerList to set
	 */
	public void setPlayerList(List<Player> playerList) {
		this.playerList = playerList;
	}
	/**
	 * @return the dices
	 */
	public Collection<Dice> getDices() {
		return dices;
	}
	/**
	 * @param dices the dices to set
	 */
	public void setDices(Collection<Dice> dices) {
		this.dices = dices;
	}
	/**
	 * @return the errorMessage
	 */
	public String getErrorMessage() {
		return errorMessage;
	}
	/**
	 * @param errorMessage the errorMessage to set
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	/**
	 * @return the choosablePairs
	 */
	public List<TwoDicesPair> getChoosablePairs() {
		return choosablePairs;
	}
	/**
	 * @param choosablePairs the choosablePairs to set
	 */
	public void setChoosablePairs(List<TwoDicesPair> choosablePairs) {
		this.choosablePairs = choosablePairs;
	}
	public String getActualPlayerId() {
		return Integer.toString(actualPlayerNumber);
	}
	
	
}
