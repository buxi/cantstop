/**
 * Helper Objekt für generierung alle möglichen Paarungen 
 */
package de.buxi.cantstop.spielobjekte;

import java.util.ArrayList;
import java.util.List;

/**
 * @author buxi
 *
 */
public class ZweiWurfelPaarSet {
	private List<ZweiWurfelPaar> resultSet = new ArrayList<ZweiWurfelPaar>(); 
	
	/**
	 * @param neuPaar
	 * @return true, falls das Set nicht neuPaar enthaelt
	 * @throws WurfelNichtGeworfenException
	 * 
	 *  TODO nicht schoen Name
	 *  TODO REFACTOR: HashSet koennte benutzt werden, wenn in ZweiWurfelPaar equals() und hashCode() uberschreiben wuerden  
	 */
	public boolean add(ZweiWurfelPaar neuPaar) throws WurfelNichtGeworfenException {
		boolean doppelGefunden = false;
		if (neuPaar == null) { 
			return false; 
		}
		
		for (ZweiWurfelPaar zweiWurfelPaar : resultSet) {
			if (zweiWurfelPaar.isGleich(neuPaar)) {
				doppelGefunden = true;
				break;
			}
		}
		if (!doppelGefunden) {
			resultSet.add(neuPaar);
			return true;
		}
		return false;
	}

	/**
	 * @return the resultSet
	 */
	public List<ZweiWurfelPaar> getResultSet() {
		return resultSet;
	}
}
