/**
 * Helper Object for the generation of all possible pairs 
 */
package de.buxi.cantstop.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author buxi
 *
 */
public class TwoDicesPairSet {
	private List<TwoDicesPair> resultSet = new ArrayList<TwoDicesPair>(); 
	
	/**
	 * @param newPair
	 * @return true, if the set does not contain the newPair
	 * @throws DiceNotThrownException
	 * 
	 *  TODO REFACTOR: HashSet can be possible used, if the TwoDicesPair equals() and hashCode() are overwritten  
	 */
	public boolean add(TwoDicesPair newPair) throws DiceNotThrownException {
		boolean doubleFound = false;
		if (newPair == null) { 
			return false; 
		}
		
		for (TwoDicesPair twoDicePair : resultSet) {
			if (twoDicePair.isSame(newPair)) {
				doubleFound = true;
				break;
			}
		}
		if (!doubleFound) {
			resultSet.add(newPair);
			return true;
		}
		return false;
	}

	/**
	 * @return the resultSet
	 */
	public List<TwoDicesPair> getResultSet() {
		return resultSet;
	}
}
