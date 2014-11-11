package de.vt.cantstop.model;
/**
 * If empty Dicelist was passed to DiceManager
 */


/**
 * @author buxi
 *
 */
public class DiceNotGivenException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8416154706409720352L;

	public DiceNotGivenException(String string) {
		super(string);
	}

}
