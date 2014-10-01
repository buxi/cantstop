package de.buxi.cantstop.model;

public class NotEnoughPlayerException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2039043145116970184L;

	public NotEnoughPlayerException() {
	}

	public NotEnoughPlayerException(String message) {
		super(message);
	}

	public NotEnoughPlayerException(Throwable cause) {
		super(cause);
	}

	public NotEnoughPlayerException(String message, Throwable cause) {
		super(message, cause);
	}

	public NotEnoughPlayerException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
