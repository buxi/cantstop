/**
 * 
 */
package de.buxi.cantstop.spielobjekte;

/**
 * @author buxi
 *
 */
public class Hutte extends AbstractSeilPunkt {
	private MarkierungStein markierungStein;
	private int wegNummer;
	
	public Hutte(int newWegNummer) {
		super();
		markierungStein = null;
		this.wegNummer = newWegNummer;
	}

	/**
	 * @return the wegNummer
	 */
	public int getWegNummer() {
		return wegNummer;
	}

	/**
	 * @return the markierungStein
	 */
	public MarkierungStein getMarkierungStein() {
		return markierungStein;
	}

	public boolean isMarkiert(Farbe farbe) {
		return getMarkierungStein() != null && farbe.equals(getMarkierungStein().getFarbe());
	}
	
	@Override
	public void markieren(MarkierungStein marker) throws SeilPunktInvalidUsageException {
		if (markierungStein != null) {
			throw new SeilPunktInvalidUsageException("SeilPunkt schon markiert");
		}
		markierungStein = marker;
	}

	public void unmarkieren() throws SeilPunktInvalidUsageException {
		if (markierungStein == null) {
			throw new SeilPunktInvalidUsageException("SeilPunkt noch nicht markiert");
		}
		markierungStein = null;
	}
}
