/**
 * Object for 2 Dices
 */
package de.buxi.cantstop.model;

import java.io.Serializable;

/**
 * @author buxi
 *
 */
public class DicePair implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5667577105783332407L;
	private Dice first;
	private Dice second;
	
	
	/**
	 * @param first
	 * @param second
	 */
	public DicePair(Dice first, Dice second) {
		super();
		this.first = first;
		this.second = second;
	}


	/**
	 * @return Sum der Valuee der Würfel
	 * @throws DiceNotThrownException
	 */
	public int getSum() throws DiceNotThrownException{
		return first.getDiceValue() + second.getDiceValue();
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((first == null) ? 0 : first.hashCode());
		result = prime * result + ((second == null) ? 0 : second.hashCode());
		return result;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		//TODO Check
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DicePair other = (DicePair) obj;
		if (first == null) {
			if (other.first != null)
				return false;
		} else if (!first.equals(other.first))
			return false;
		if (second == null) {
			if (other.second != null)
				return false;
		} else if (!second.equals(other.second))
			return false;
		return true;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[");
		builder.append(first);
		builder.append(",");
		builder.append(second);
		builder.append("]");
		return builder.toString();
	}
}