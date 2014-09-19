/**
 * 
 */
package de.buxi.cantstop.model;

/**
 * @author buxi
 *
 */
public class InvalidPairsException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5770809390066659262L;

	/**
	 * 
	 */
	public InvalidPairsException() {
	}

	/**
	 * @param message
	 */
	public InvalidPairsException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public InvalidPairsException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public InvalidPairsException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public InvalidPairsException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
