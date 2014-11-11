package de.vt.cantstop.dao;

import java.sql.Timestamp;

import de.vt.cantstop.model.GameTransferObject;

/**
 * TransferObject to hold all database information about the game
 * @author buxi
 *
 */
public class GameInfoFullTO {
	private int gameId;
	private Timestamp ts;
	private String methodName;
	private int playerId;
	private GameTransferObject transferObject;
	private String description;
	
	
	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}
	/**
	 * @return the gameId
	 */
	public int getGameId() {
		return gameId;
	}
	/**
	 * @return the ts
	 */
	public Timestamp getTs() {
		return ts;
	}
	/**
	 * @param ts the ts to set
	 */
	public void setTs(Timestamp ts) {
		this.ts = ts;
	}
	/**
	 * @return the methodName
	 */
	public String getMethodName() {
		return methodName;
	}
	/**
	 * @param methodName the methodName to set
	 */
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}
	/**
	 * @return the playerId
	 */
	public int getPlayerId() {
		return playerId;
	}
	/**
	 * @param playerId the playerId to set
	 */
	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}
	/**
	 * @return the to
	 */
	public GameTransferObject getTransferObject() {
		return transferObject;
	}
	/**
	 * @param to the to to set
	 */
	public void setTransferObject(GameTransferObject to) {
		this.transferObject = to;
	}
	/**
	 * @param gameId the gameId to set
	 */
	public void setGameId(int gameId) {
		this.gameId = gameId;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}
	
	
}
