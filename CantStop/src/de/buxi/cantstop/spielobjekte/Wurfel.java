package de.buxi.cantstop.spielobjekte;

public interface Wurfel {

	/**
	 * @return the geworfen
	 */
	public boolean isGeworfen();

	/**
	 * @return the wert
	 * @throws WurfelNichtGeworfenException 
	 */
	public int getWert() throws WurfelNichtGeworfenException;

	/**
	 * 
	 */
	public void werfen();

	/**
	 * reset WurfelImpl, geworfen = false
	 * TODO wie und wann zwingt man das Reset?  In WurfelManager.allWerfen
	 */
	public void reset();
	
	/**
	 * @return Zahl der Seiten
	 */
	public int getSeite();
	
}