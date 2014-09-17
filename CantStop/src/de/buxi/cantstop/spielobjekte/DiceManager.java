/**
 * Manager of dices. It has to be always in one hand or free to be 
 */
package de.buxi.cantstop.spielobjekte;

import java.util.List;

/**
 * @author buxi
 *
 */
public class DiceManager {
	private List<Dice> dices;
	private Player owner; 

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
}
