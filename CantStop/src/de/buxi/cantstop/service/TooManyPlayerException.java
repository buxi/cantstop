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
		// TODO Auto-generated constructor stub
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
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 * @param cause
	 */
	public TooManyPlayerException(String message, Throwable cause) {
		super(message, cause);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param message
	 */
	public TooManyPlayerException(String message) {
		super(message);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param cause
	 */
	public TooManyPlayerException(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

}
