/**
 * 
 */
package de.buxi.cantstop.spielobjekte;

/**
 * @author buxi
 *
 */
public class InvalidPaarungException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5770809390066659262L;

	/**
	 * 
	 */
	public InvalidPaarungException() {
	}

	/**
	 * @param message
	 */
	public InvalidPaarungException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public InvalidPaarungException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public InvalidPaarungException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public InvalidPaarungException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
