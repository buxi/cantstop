package de.vt.cantstop.model;
/**
 *      <DicePair, DicePair>
 *   <Dice,Dice> <Dice,Dice> 
 */


import java.io.Serializable;

/**
 * @author buxi
 */
public class TwoDicesPair implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -430115296077172544L;
	private DicePair firstPair;
	private DicePair secondPair;
	private PairChoiceInfo pairChoiceInfo;
	
	/**
	 * @param first
	 * @param second
	 */
	public TwoDicesPair(DicePair firstPair, DicePair secondPair) {
		super();
		this.firstPair = firstPair;
		this.secondPair = secondPair;
		this.pairChoiceInfo = PairChoiceInfo.CHOOSABLE;
	}


	/**
	 * @return the firstPair
	 */
	public DicePair getFirstPair() {
		return firstPair;
	}

	/**
	 * @return the secondPair
	 */
	public DicePair getSecondPair() {
		return secondPair;
	}
	
	/**
	 * @return the sum of the first pair
	 * @throws DiceNotThrownException
	 */
	public int getSecondSum() throws DiceNotThrownException {
		return this.secondPair.getSum();
	}

	/**
	 * @return the sum of the first pair
	 * @throws DiceNotThrownException
	 */
	public int getFirstSum() throws DiceNotThrownException {
		return this.firstPair.getSum();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		//builder.append("TwoDicesPair [firstPair=");
		builder.append("(");
		builder.append(firstPair);
		builder.append(",");
		builder.append(secondPair);
		builder.append(" ");
		builder.append(pairChoiceInfo);
		builder.append(")");
		return builder.toString();
	}
	
	public String display() {
		StringBuilder builder = new StringBuilder();
		builder.append("(");
		builder.append(firstPair);
		builder.append(",");
		builder.append(secondPair);
		builder.append(")");
		return builder.toString();
	}
	

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((firstPair == null) ? 0 : firstPair.hashCode());
		result = prime * result
				+ ((secondPair == null) ? 0 : secondPair.hashCode());
		return result;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		//TODO  CHECK!
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		try {
			int actualFirstSum = this.getFirstSum();
			int actualSecondSum;
			actualSecondSum = this.getSecondSum();
			int otherFirstSum = ((TwoDicesPair) obj).getFirstSum();
			int otherSecondSum = ((TwoDicesPair) obj).getSecondSum();
			
			if ((actualFirstSum == otherFirstSum && actualSecondSum == otherSecondSum) || 
					(actualFirstSum == otherSecondSum && actualSecondSum == otherFirstSum) ) {
				return true;
			}
		} catch (DiceNotThrownException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public boolean isSame(TwoDicesPair other) throws DiceNotThrownException {
		int actualFirstSum = this.getFirstSum();
		int actualSecondSum = this.getSecondSum();
		
		int otherFirstSum = other.getFirstSum();
		int otherSecondSum = other.getSecondSum();
		
		if ((actualFirstSum == otherFirstSum && actualSecondSum == otherSecondSum) || 
			(actualFirstSum == otherSecondSum && actualSecondSum == otherFirstSum) ) {
			return true;
		}
		return false;
	}


	/**
	 * @return the pairChoiceInfo
	 */
	public PairChoiceInfo getPairChoiceInfo() {
		return pairChoiceInfo;
	}

	/**
	 * @param pairChoiceInfo the pairChoiceInfo to set
	 */
	public void setPairChoiceInfo(PairChoiceInfo pairChoiceInfo) {
		this.pairChoiceInfo = pairChoiceInfo;
	}


	public String generateId() {
		StringBuffer id = new StringBuffer();
		id.append(this.getFirstPair().getFirst().getDiceValue());
		id.append(this.getFirstPair().getSecond().getDiceValue());
		id.append(this.getSecondPair().getFirst().getDiceValue());
		id.append(this.getSecondPair().getSecond().getDiceValue());
		return id.toString();
	}

}
