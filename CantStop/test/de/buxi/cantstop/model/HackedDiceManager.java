/**
 * 
 */
package de.buxi.cantstop.model;

import java.util.List;

import de.buxi.cantstop.model.Dice;
import de.buxi.cantstop.model.DiceManager;
import de.buxi.cantstop.model.DiceNotGivenException;


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
	 * @see de.buxi.cantstop.model.DiceManager#throwAllDices()
	 * disable the throw
	 */
	@Override
	public void throwAllDices() {
		//super.throwAllDices();
	}
	
}
