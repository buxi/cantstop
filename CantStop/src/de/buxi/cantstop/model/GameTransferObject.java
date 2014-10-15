/**
 * 
 */
package de.buxi.cantstop.model;

import java.util.Collection;
import java.util.List;
import java.util.Map;

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
	public Map<String, TwoDicesPair> choosablePairsWithId;
	public Board board;
	public List<Dice> lastThrow;
	public UsedPairInfoTO lastUsedPairInfo;

	/**
	 * @return the lastUsedPairInfo
	 */
	public UsedPairInfoTO getLastUsedPairInfo() {
		return lastUsedPairInfo;
	}

	/**
	 * @return the lastThrow
	 */
	public List<Dice> getLastThrow() {
		return lastThrow;
	}

	/**
	 * @return the board
	 */
	public Board getBoard() {
		return board;
	}

	/**
	 * @return the choosablePairsWithId
	 */
	public Map<String, TwoDicesPair> getChoosablePairsWithId() {
		return choosablePairsWithId;
	}
	
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
	public String getActualPlayerId() {
		return Integer.toString(actualPlayerNumber);
	}
	
	public String getJoinedPlayersListAJAX() {
		StringBuffer result = new StringBuffer();
		for (Player player : getPlayerList()) {
			result.append(player.getName());
			result.append(", ");
		}
		
		// deleting the last, unnecessary comma and space
		if (getPlayerList() != null && getPlayerList().size() > 0) {
			result.deleteCharAt(result.length()-1);
			result.deleteCharAt(result.length()-1);
		}

		return result.toString();
	}
}
