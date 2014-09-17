package de.buxi.cantstop.spielobjekte;

import java.util.Random;

/**
 * @author buxi
 * Representation ein abstracter Würfel
 */
public class WurfelImpl implements Wurfel {
	/**
	 * der Wert nach einem Wurf 
	 */
	private int wert; 

	/**
	 * true, wenn der Würfel geworfen war, ansonsten false
	 */
	private boolean geworfen;
	
	/**
	 * wieviel Seite hat der Würfel
	 */
	private int seite;
	
	
	/**
	 * @param newSeite
	 */
	public WurfelImpl(int newSeite) {
		super();
		this.geworfen = false;
		this.seite = newSeite;
	}

	/**
	 * @param geworfen the geworfen to set
	 */
	private void setGeworfen(boolean geworfen) {
		this.geworfen = geworfen;
	}

	/**
	 * @param wert the wert to set
	 */
	private void setWert(int zahl) {
		this.wert = zahl;
	}

	/* (non-Javadoc)
	 * @see de.buxi.cantstop.spielobjekte.Wurfel#isGeworfen()
	 */
	@Override
	public boolean isGeworfen() {
		return geworfen;
	}
	
	/* (non-Javadoc)
	 * @see de.buxi.cantstop.spielobjekte.Wurfel#getWert()
	 */
	@Override
	public int getWert() throws WurfelNichtGeworfenException {
		if (!isGeworfen()) { 
			throw new WurfelNichtGeworfenException(); 
		}
		return wert;
	}

	/* (non-Javadoc)
	 * @see de.buxi.cantstop.spielobjekte.Wurfel#werfen()
	 */
	@Override
	public void werfen() {
		Random rnd = new Random();
		// Generierung der Zufallnummer von 1 bis Seite
		int randomValue = rnd.nextInt(seite) + 1 ;
		setWert(randomValue);
		setGeworfen(true);
	}

	/* (non-Javadoc)
	 * @see de.buxi.cantstop.spielobjekte.Wurfel#reset()
	 */
	@Override
	public void reset() {
		setGeworfen(false);
	}

	/**
	 * @return the seite
	 */
	public int getSeite() {
		return seite;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return Integer.toString(wert);
	}
	
	
}
