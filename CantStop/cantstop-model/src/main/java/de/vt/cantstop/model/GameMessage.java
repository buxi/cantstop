package de.vt.cantstop.model;
/**
 * 
 */


import java.io.Serializable;

/**
 * POJO class to store game related messages
 * @author buxi
 *
 */
public class GameMessage implements Serializable{
	private static final long serialVersionUID = -7277176376706642421L;
	private String playerName;
	private String message;
	private GameMessageType type;
	private String localizedMessage;
	
	/**
	 * @param playerName
	 * @param message
	 * @param type
	 */
	public GameMessage(String playerName, String message, GameMessageType type) {
		super();
		this.playerName = playerName;
		this.message = message;
		this.type = type;
	}
	
	/**
	 * @return the playerName
	 */
	public String getPlayerName() {
		return playerName;
	}
	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @return the type
	 */
	public GameMessageType getType() {
		return type;
	}

	/**
	 * @return the localizedMessage
	 */
	public String getLocalizedMessage() {
		return localizedMessage;
	}

	public void setLocalizedMessage(String message2) {
		this.localizedMessage = message2;
	}
	
}
