/**
 * 
 */
package de.buxi.cantstop.spielobjekte;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

/**
 * @author buxi
 * Enthält alle Factory-methoden für das Spiel
 */
public class SpielFactory {
	
	
	/**
	 * generiert das Spielbrett
	 * @return kompletter Brett, mit allen Wegen
	 */
	public Brett createBrett(Map<Integer,Integer> wegeDefinition) {
		Set<Entry<Integer, Integer>> wegEinstellungsEntries = wegeDefinition.entrySet();
		List<Weg> wege = new ArrayList<Weg>(11);
		
		for (Entry<Integer, Integer> entry : wegEinstellungsEntries) {
			int nummer = entry.getKey();
			int lange = entry.getValue();
			Weg weg = createWeg(nummer, lange);
			wege.add(weg);
		}
		Brett brett = new Brett(wege);
		return brett;
	}

	/**
	 * @param nummer
	 * @param lange
	 * @return
	 */
	protected Weg createWeg(int nummer, int lange) {
		Hutte hutte = new Hutte(nummer);
		
		List<SeilPunkt> seilPunkte = new ArrayList<SeilPunkt>();
		for (int i = 0; i < lange ; i++) {
			seilPunkte.add(new SeilPunkt());
		}
		Weg weg = new Weg(nummer, hutte , seilPunkte);
		return weg;
	}
	
	/**
	 * @param anzahl wieviel Steine sollen generiert werden
	 * @param farbe in welchem Farbe sollen die Steine haben
	 * @return anzahl Stuck MarkierungSteine in farbe 
	 */
	public Collection<MarkierungStein> createMarkierungSteine(int anzahl, Farbe farbe){
		List<MarkierungStein> steine = new ArrayList<MarkierungStein>(anzahl);
		for (int i = 0; i < anzahl; i++) {
			steine.add(new MarkierungStein(farbe));
		}		
		return steine;
	}
	
	public static Collection<MarkierungStein> createMarkierungSteineStatic (int anzahl, Farbe farbe){
		return new SpielFactory().createMarkierungSteine(anzahl, farbe);
	}
	/**
	 * @param anzahl wieviel Bergsteiger sollen generiert werden
	 * @return anzahl Stuck Bergsteiger 
	 */
	public Collection<BergSteiger> createBergsteigers(int anzahl){
		List<BergSteiger> bergSteigerList = new ArrayList<BergSteiger>(anzahl);
		for (int i = 0; i < anzahl; i++) {
			bergSteigerList.add(new BergSteiger());
		}		
		return bergSteigerList;
	}
	
	
}
