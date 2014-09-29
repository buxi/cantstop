package de.buxi.cantstop.model;

import java.io.Serializable;
import java.util.Random;

/**
 * @author buxi
 * Representation of an abstract Dice
 */
public class DiceImpl implements Dice, Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4259022213227837523L;

	/**
	 * the diceValue after a throw 
	 */
	private int diceValue; 

	/**
	 * true, if the dice was thrown
	 */
	private boolean thrown;
	
	/**
	 * how much sideNumber has the dice
	 */
	private int sideNumber;
	
	
	/**
	 * @param newSides
	 */
	public DiceImpl(int newSides) {
		super();
		this.thrown = false;
		this.sideNumber = newSides;
	}

	/**
	 * @param thrown the thrown to set
	 */
	private void setThrown(boolean thrown) {
		this.thrown = thrown;
	}

	/**
	 * @param diceValue the diceValue to set
	 */
	private void setDiceValue(int number) {
		this.diceValue = number;
	}

	/* (non-Javadoc)
	 * @see de.buxi.cantstop.model.Dice#isThrown()
	 */
	@Override
	public boolean isThrown() {
		return thrown;
	}
	
	/* (non-Javadoc)
	 * @see de.buxi.cantstop.model.Dice#getDiceValue()
	 */
	@Override
	public int getDiceValue() {
		if (!isThrown()) { 
			throw new DiceNotThrownException(); 
		}
		return diceValue;
	}

	/* (non-Javadoc)
	 * @see de.buxi.cantstop.model.Dice#throwDice()
	 */
	@Override
	public void throwDice() {
		Random rnd = new Random();
		// get a random value from 1 to sides
		int randomValue = rnd.nextInt(sideNumber) + 1 ;
		setDiceValue(randomValue);
		setThrown(true);
	}

	/* (non-Javadoc)
	 * @see de.buxi.cantstop.model.Dice#reset()
	 */
	@Override
	public void reset() {
		setThrown(false);
	}

	/**
	 * @return the sideNumber
	 */
	public int getSideNumber() {
		return sideNumber;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return Integer.toString(diceValue);
	}

	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#clone()
	 */
	@Override
	public Object clone() throws CloneNotSupportedException {
		DiceImpl newDice = new DiceImpl(this.getSideNumber());
		newDice.setThrown(this.thrown);
		newDice.setDiceValue(this.diceValue);
		return newDice;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (thrown ? 1231 : 1237);
		result = prime * result + sideNumber;
		result = prime * result + diceValue;
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DiceImpl other = (DiceImpl) obj;
		if (thrown != other.thrown)
			return false;
		if (sideNumber != other.sideNumber)
			return false;
		if (diceValue != other.diceValue)
			return false;
		return true;
	}
}
