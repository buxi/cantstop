/**
 * 
 */
package de.buxi.cantstop.model;

/**
 * @author buxi
 *
 */
public class InvalidGameStateException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 515949508401257673L;

	/**
	 * 
	 */
	public InvalidGameStateException() {
	}

	/**
	 * @param message
	 */
	public InvalidGameStateException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public InvalidGameStateException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public InvalidGameStateException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public InvalidGameStateException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
