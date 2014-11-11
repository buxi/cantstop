/**
 * 
 */
package de.vt.cantstop.model;

import java.util.List;

import de.vt.cantstop.model.Dice;
import de.vt.cantstop.model.DiceManager;
import de.vt.cantstop.model.DiceNotGivenException;


/**
 * @author buxi
 *
 */
public class HackedDiceManager extends DiceManager {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8774801520742909944L;

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
