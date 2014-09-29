package de.buxi.cantstop.model;

public interface Dice extends Cloneable {

	/**
	 * @return the wasThrown
	 */
	public boolean isThrown();

	/**
	 * @return the value of dice
	 * @throws DiceNotThrownException 
	 */
	public int getDiceValue() throws DiceNotThrownException;

	/**
	 * 
	 */
	public void throwDice();

	/**
	 * reset wasThrown = false
	 * TODO how can we force reset?  In DiceManager.throwAllDice
	 */
	public void reset();
	
	/**
	 * @return number of sides
	 */
	public int getSideNumber();
	
	public boolean equals(Object obj);
	public int hashCode();

	public Object clone() throws CloneNotSupportedException;

}