package de.vt.cantstop.model;
/**
 * 
 */


/**
 * @author buxi
 *
 */
public class PlayerNotFoundException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3551955067089961322L;

	/**
	 * 
	 */
	public PlayerNotFoundException() {
	}

	/**
	 * @param message
	 */
	public PlayerNotFoundException(String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public PlayerNotFoundException(Throwable cause) {
		super(cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public PlayerNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * @param message
	 * @param cause
	 * @param enableSuppression
	 * @param writableStackTrace
	 */
	public PlayerNotFoundException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
