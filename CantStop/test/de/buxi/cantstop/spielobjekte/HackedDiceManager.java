/**
 * 
 */
package de.buxi.cantstop.spielobjekte;

import java.util.List;


/**
 * @author buxi
 *
 */
public class HackedDiceManager extends DiceManager {

	public HackedDiceManager(List<Dice> dices)
			throws DiceNotGivenException {
		super(dices);
	}

	/* (non-Javadoc)
	 * @see de.buxi.cantstop.spielobjekte.DiceManager#throwAllDices()
	 * disable the throw
	 */
	@Override
	public void throwAllDices() {
		//super.throwAllDices();
	}
	
}
