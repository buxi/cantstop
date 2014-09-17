/**
 * Falls leere Würfellist an WürfelManager gegeben wurde
 */
package de.buxi.cantstop.spielobjekte;

/**
 * @author buxi
 *
 */
public class NichtWurfelGegebenException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8416154706409720352L;

	public NichtWurfelGegebenException(String string) {
		super(string);
	}

}
