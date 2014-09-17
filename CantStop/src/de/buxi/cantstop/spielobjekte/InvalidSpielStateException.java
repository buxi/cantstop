/**
 * 
 */
package de.buxi.cantstop.spielobjekte;

/**
 * @author buxi
 *
 */
public class InvalidSpielStateException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 515949508401257673L;

	/**
	 * 
	 */
	public InvalidSpielStateException() {
	}

	/**
	 * @param message
	 */
	public InvalidSpielStateException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public InvalidSpielStateException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public InvalidSpielStateException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public InvalidSpielStateException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
