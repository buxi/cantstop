/**
 * Administriert die Würfel. Sie sollen immer nur in einem Hand oder frei sein
 * Würfel können nur durch diese Manager erwerben worden
 * Würfel sind atomic und unique im System
 */
package de.buxi.cantstop.spielobjekte;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author buxi
 *
 */
public class WurfelManager {
	private List<Wurfel> wurfels;
	private Spieler besitzer; 

	/**
	 * @param wurfels
	 * @throws NichtWurfelGegebenException falls wurfels ist null
	 */
	public WurfelManager(List<Wurfel> wurfels) throws NichtWurfelGegebenException {
		super();
		if (wurfels == null) {
			throw new NichtWurfelGegebenException("Kein Wurfel gegeben an WurfelManager");
		}
		this.wurfels = wurfels;
		setBesitzer(null);
	}

	/**
	 * @return the wurfels
	 */
	/**
	 * @return
	 */
	public List<Wurfel> getWurfels() {
		return wurfels;
	}

	/**
	 * @return the besitzer
	 */
	public Spieler getBesitzer() {
		return besitzer;
	}

	/**
	 * @param besitzer the besitzer to set
	 */
	private void setBesitzer(Spieler besitzer) {
		this.besitzer = besitzer;
	}
	
	/**
	 * Umtauscht den Besitzer der Wurfels
	 * @param neuBesitzer
	 * @return true, falls das Erwerb erfolgreich war, ansonsten false
	 */
	public boolean erwerbWurfels(Spieler neuBesitzer) {
		//TODO hier kann man Voraussetzungen des Besitzes implementieren
		setBesitzer(neuBesitzer);
		
		return true;
	}
	
	/**
	 * Werfen mit allen Würfel
	 */
	public void allWerfen() {
		//TODO im Multithreaded Umgebung mussen Reset und Werfen atomic sein
		for (Wurfel wurfel : wurfels) {
			wurfel.reset();
			wurfel.werfen();
		}
	}
	
	/**
	 * @return Generiert alle mögliche Paarungen aus 4 NormalWurfel
	 * @throws WurfelNichtGeworfenException
	 */
	public List<ZweiWurfelPaar> getMoglichePaarungen() throws WurfelNichtGeworfenException {
		List<Wurfel> wurfels = getWurfels();

		ZweiWurfelPaarSet paarSet = new ZweiWurfelPaarSet();
		WurfelPaar wurfelPaar01 = new WurfelPaar(wurfels.get(0), wurfels.get(1));
		WurfelPaar wurfelPaar23= new WurfelPaar(wurfels.get(2), wurfels.get(3));
		ZweiWurfelPaar ersteKombination = new ZweiWurfelPaar(wurfelPaar01, wurfelPaar23);
		paarSet.add(ersteKombination);
		
		WurfelPaar wurfelPaar02 = new WurfelPaar(wurfels.get(0), wurfels.get(2));
		WurfelPaar wurfelPaar13 = new WurfelPaar(wurfels.get(1), wurfels.get(3));
		ZweiWurfelPaar zweiteKombination = new ZweiWurfelPaar(wurfelPaar02, wurfelPaar13);
		paarSet.add(zweiteKombination);
	
		WurfelPaar wurfelPaar03 = new WurfelPaar(wurfels.get(0), wurfels.get(3));
		WurfelPaar wurfelPaar12 = new WurfelPaar(wurfels.get(1), wurfels.get(2));
		ZweiWurfelPaar dritteKombination = new ZweiWurfelPaar(wurfelPaar03, wurfelPaar12);
		paarSet.add(dritteKombination);
		return paarSet.getResultSet();
	}
	
}
