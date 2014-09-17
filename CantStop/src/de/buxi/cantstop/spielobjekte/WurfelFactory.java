/**
 * 
 */
package de.buxi.cantstop.spielobjekte;

import java.util.ArrayList;
import java.util.List;

/**
 * @author buxi
 * Factory Objekt um verschiedenen Wurfel zu generieren
 */
public class WurfelFactory {
	/**
	 * @param seite mit wieviel Seite muss der Wurfel haben 
	 * @return seite seitiger Wurfel
	 */
	public List<Wurfel> createNormalWurfelSet(int seite, int anzahl) {
		List<Wurfel> wurfelSet = new ArrayList<Wurfel>(anzahl);
		for (int i = 0; i < anzahl; i++) {
			wurfelSet.add(new WurfelImpl(seite));
		}
		return wurfelSet;
	}
	
	public Wurfel createNormalWurfel(int seite) {
		return new WurfelImpl(seite);
	}
}
