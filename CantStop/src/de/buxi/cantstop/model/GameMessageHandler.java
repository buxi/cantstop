/**
 * 
 */
package de.buxi.cantstop.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * Handles game related messages
 * @author buxi
 *
 */
public class GameMessageHandler {
	private int maxMessages;
	
	private Queue<GameMessage> messages;
	
	/**
	 * @param maxMessages
	 */
	public GameMessageHandler(int maxMessages) {
		super();
		this.maxMessages = maxMessages;
		this.messages = new LinkedList<>();
	}
	
	public void reset() {
		messages.clear();
	};
	
	public List<GameMessage> listMessages() {
		return new ArrayList<>(messages);
	}
	
	public void addMessage(GameMessage message) {
		if (messages.size() == maxMessages) {
			messages.poll();
		}
		messages.add(message);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("GameMessageHandler [maxMessages=");
		builder.append(maxMessages);
		builder.append(", messages=");
		builder.append(messages);
		builder.append("]");
		return builder.toString();
	}
}
