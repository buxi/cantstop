package de.buxi.cantstop.service;

public class GameException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -384657247841151980L;

	public GameException() {
	}

	public GameException(String message) {
		super(message);
	}

	public GameException(Throwable cause) {
		super(cause);
	}

	public GameException(String message, Throwable cause) {
		super(message, cause);
	}

	public GameException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
