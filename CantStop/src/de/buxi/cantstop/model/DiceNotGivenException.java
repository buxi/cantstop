/**
 * If empty Dicelist was passed to DiceManager
 */
package de.buxi.cantstop.model;

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
