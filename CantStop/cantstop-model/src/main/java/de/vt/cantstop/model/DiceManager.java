package de.vt.cantstop.model;
/**
 * Manager of dices. It has to be always in one hand or free to be 
 */


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @author buxi
 *
 */
public class DiceManager implements Serializable{

	Log log = LogFactory.getLog(DiceManager.class);
	
	private static final long serialVersionUID = -2229098432462786519L;
	private List<Dice> dices;
	private Player owner;

	private List<Dice> lastThrow; 

	/**
	 * @param dices
	 * @throws DiceNotGivenException no input dices 
	 */
	public DiceManager(List<Dice> dices) throws DiceNotGivenException {
		super();
		if (dices == null) {
			throw new DiceNotGivenException("No dices given to DiceManager");
		}
		this.dices = dices;
		setOwner(null);
	}

	/**
	 * @return the dices
	 */
	/**
	 * @return
	 */
	public List<Dice> getDices() {
		return dices;
	}

	/**
	 * @return the owner
	 */
	public Player getOwner() {
		return owner;
	}

	/**
	 * @param owner the owner to set
	 */
	private void setOwner(Player owner) {
		this.owner = owner;
	}
	
	/**
	 * change the owner of dices
	 * @param newOwner
	 * @return true, if the change was successful
	 */
	public boolean giveDices(Player newOwner) {
		setOwner(newOwner);
		lastThrow = null;
		return true;
	}
	
	
	/**
	 * Throw all dices
	 */
	public void throwAllDices() {
		//TODO in Multithreaded Env reset and throwDice needs to be atomic
		for (Dice dice : dices) {
			dice.reset();
			dice.throwDice();
		}
		log.info("dices were thrown:"+dices);
	}

	/**
	 * 
	 */
	protected List<Dice> getDicesClone() {
		List<Dice> lastThrow = new ArrayList<>(4);
		for (Dice dice : dices) {
			try {
				if (dice.isThrown()) {
					lastThrow.add((Dice) dice.clone());
				}
			} catch (CloneNotSupportedException e) {
				//this can not happen
				log.error(e.getMessage());
			}
		}
		return lastThrow;
	}
	
	/**
	 * @return generates all possible pairs of 4 NormalDice
	 * @throws DiceNotThrownException
	 */
	public List<TwoDicesPair> getAllPossiblePairs() throws DiceNotThrownException {
		List<Dice> dices = getDices();

		TwoDicesPairSet paarSet = new TwoDicesPairSet();
		DicePair dicePair01 = new DicePair(dices.get(0), dices.get(1));
		DicePair dicePair23= new DicePair(dices.get(2), dices.get(3));
		TwoDicesPair firstKombination = new TwoDicesPair(dicePair01, dicePair23);
		paarSet.add(firstKombination);
		
		DicePair dicePair02 = new DicePair(dices.get(0), dices.get(2));
		DicePair dicePair13 = new DicePair(dices.get(1), dices.get(3));
		TwoDicesPair secondKombination = new TwoDicesPair(dicePair02, dicePair13);
		paarSet.add(secondKombination);
	
		DicePair dicePair03 = new DicePair(dices.get(0), dices.get(3));
		DicePair dicePair12 = new DicePair(dices.get(1), dices.get(2));
		TwoDicesPair thirdKombination = new TwoDicesPair(dicePair03, dicePair12);
		paarSet.add(thirdKombination);
		return paarSet.getResultSet();
	}

	/**
	 * @return previous throw 
	 */
	public List<Dice> getLastThrow() {
		return this.lastThrow;
	}

	/**
	 * reset diceManager, sets dices to notThrown and stores last throw
	 */
	public void reset() {
		// storing last throw
		this.lastThrow = getDicesClone();
		log.debug("storing last throw:" + this.lastThrow);
		for (Dice dice : dices) {
			dice.reset();
		}
	}
}
