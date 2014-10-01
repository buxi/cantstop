package de.buxi.cantstop.service;

public class TooManyPlayerException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3796943390787148024L;

	/**
	 * 
	 */
	public TooManyPlayerException() {
		super();
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public TooManyPlayerException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public TooManyPlayerException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 */
	public TooManyPlayerException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public TooManyPlayerException(Throwable cause) {
		super(cause);
	}

}
