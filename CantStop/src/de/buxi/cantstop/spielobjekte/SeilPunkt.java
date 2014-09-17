/**
 * 
 */
package de.buxi.cantstop.spielobjekte;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author buxi
 * Ein SeilPunkt kann einen BergSteiger oder mehrere MarkierungStein behalten
 *
 */
public class SeilPunkt extends AbstractSeilPunkt {
	
	private Collection<MarkierungStein> markierungSteine;
	
	public SeilPunkt() {
		super();
		this.markierungSteine = new ArrayList<MarkierungStein>();
	}

	/**
	 * @return the markierungSteine
	 */
	public Collection<MarkierungStein> getMarkierungSteine() {
		return markierungSteine;
	}
	
	/**
	 * Markieren mit Marker den SeilPunkt
	 * @param marker
	 * @throws SeilPunktInvalidUsageException SeilPunkt ist schon markiert mit dieser Farbe
	 */
	public void markieren(MarkierungStein marker) throws SeilPunktInvalidUsageException{
		if (markierungSteine.contains(marker)) {
			throw new SeilPunktInvalidUsageException("SeilPunkt schon markiert");
		}
		markierungSteine.add(marker);
	}
	
	/**
	 * Unmarkieren mit Marker den SeilPunkt
	 * @param marker
	 * @throws SeilPunktInvalidUsageException SeilPunkt war noch nicht markiert mit dieser Farbe
	 */
	public MarkierungStein unmarkieren(Farbe farbe) throws SeilPunktInvalidUsageException {
		for (MarkierungStein markierungStein : markierungSteine) {
			if (markierungStein.getFarbe().equals(farbe)) {
				markierungSteine.remove(markierungStein);
				return markierungStein;
				
			}
		}
		throw new SeilPunktInvalidUsageException("SeilPunkt noch nicht markiert");	
	}
	
	/**
	 * @param farbe
	 * @return true wenn diesen SeilPunkt mit farbe markiert ist
	 */
	public boolean isMarkierungsteinFurFarbe(Farbe farbe) {
		for (MarkierungStein markierungStein : markierungSteine) {
			if (markierungStein.getFarbe().equals(farbe)) {
				return true;
			}
		}
		return false;
	}
}
