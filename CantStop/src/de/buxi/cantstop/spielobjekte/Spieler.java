/**
 * 
 */
package de.buxi.cantstop.spielobjekte;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author buxi
 *
 */
public class Spieler {
	private String name;
	private Farbe farbe;
	private Collection<MarkierungStein>markierungSteine;
	private int reihe;
	
	private Collection<BergSteiger>bergSteigers;

	/**
	 * @param name
	 * @param farbe
	 * @param steine
	 * @param reihe
	 * @param bergSteigers
	 */
	public Spieler(String name, Farbe farbe) {
		super();
		this.name = name;
		this.farbe = farbe;
		this.markierungSteine = new ArrayList<MarkierungStein>(10);
		this.bergSteigers = new ArrayList<BergSteiger>(3);
	}

	public Spieler(int reihe, String name, Farbe farbe) {
		super();
		this.name = name;
		this.farbe = farbe;
		this.reihe = reihe;
		
		this.markierungSteine = new ArrayList<MarkierungStein>(10);
		this.bergSteigers = new ArrayList<BergSteiger>(3);
	}

	/**
	 * @return the markierungSteine
	 */
	public Collection<MarkierungStein> getMarkierungSteine() {
		return markierungSteine;
	}

	/**
	 * @param markierungSteine the markierungSteine to set
	 */
	public void addMarkierungSteine(Collection<MarkierungStein> markierungSteine) {
		this.markierungSteine.addAll(markierungSteine);
	}

	/**
	 * @return the bergSteigers
	 */
	public Collection<BergSteiger> getBergSteigers() {
		return bergSteigers;
	}

	/**
	 * @param add bergSteigers zu BergSteigerList
	 */
	public void addBergSteigers(Collection<BergSteiger> neuBergSteigers) {
		this.bergSteigers.addAll(neuBergSteigers) ;
	}
	
	/**
	 * @param add bergSteigers zu BergSteigerList
	 */
	public Collection<BergSteiger> entnimmBergSteigers() {
		Collection<BergSteiger> alteBergsteBergSteigers = this.bergSteigers;
		this.bergSteigers = new ArrayList<BergSteiger>(3);
		return alteBergsteBergSteigers;
	}
	
	/**
	 * @return the farbe
	 */
	public Farbe getFarbe() {
		return farbe;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the reihe
	 */
	public int getReihe() {
		return reihe;
	}

	/**
	 * @param reihe the reihe to set
	 */
	public void setReihe(int reihe) {
		this.reihe = reihe;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Spieler [name=");
		builder.append(name);
		builder.append(", farbe=");
		builder.append(farbe);
		builder.append(", markierungSteine=");
		builder.append(markierungSteine);
		builder.append(", reihe=");
		builder.append(reihe);
		builder.append(", bergSteigers=");
		builder.append(bergSteigers);
		builder.append("]");
		return builder.toString();
	}

	public String display() {
		StringBuffer builder = new StringBuffer();
		builder.append(name);
		builder.append("(");
		builder.append(farbe);
		builder.append(")");
		builder.append(bergSteigers==null?"            ":bergSteigers);
		builder.append(markierungSteine);
		return builder.toString();
	}

	/**
	 * gib zurück einen Bergsteiger, wenn ein vorhanden ist
	 * @return BergSteiger
	 * @throws KeinBergSteigerIstVorhandenException 
	 */
	public BergSteiger getEinBergSteiger() throws KeinBergSteigerIstVorhandenException {
		if (bergSteigers == null || bergSteigers.isEmpty()) {
			throw new KeinBergSteigerIstVorhandenException("Spieler hat keinen BergSteiger mehr");
		}
		BergSteiger bergSteiger = bergSteigers.iterator().next();
		bergSteigers.remove(bergSteiger);
		return bergSteiger;
	}

	/**
	 * gib zurück einen MarkierungStein, wenn ein vorhanden ist
	 * @throws KeinMarkierungSteinIstVorhandenException
	 */
	public MarkierungStein getEinMarkierungStein() throws KeinMarkierungSteinIstVorhandenException {
		if (markierungSteine == null || markierungSteine.isEmpty()) {
			throw new KeinMarkierungSteinIstVorhandenException("Spieler hat keinen MarkirungStein mehr:" + this.getName());
		}
		
		MarkierungStein markierungStein = markierungSteine.iterator().next();
		markierungSteine.remove(markierungStein);
		return markierungStein;
	}
}
