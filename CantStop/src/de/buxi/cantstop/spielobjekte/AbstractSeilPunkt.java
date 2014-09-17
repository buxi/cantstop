/**
 * 
 */
package de.buxi.cantstop.spielobjekte;

/**
 * @author buxi
 *
 */
public abstract class AbstractSeilPunkt {
	private BergSteiger bergsteiger;

	public AbstractSeilPunkt() {
		super();
		this.bergsteiger = null;
	}
	
	/**
	 * @return the bergsteiger
	 */
	public BergSteiger getBergsteiger() {
		return bergsteiger;
	}
	
	/**
	 * Auf diesen SeilPunkt einen Bergsteiger stellen
	 * @param bergSteiger
	 * @throws SeilPunktInvalidUsageException SeilPunkt ist schon besetzt
	 * @throws NullBergsteigerException 
	 */
	public void bergSteigerStellen(BergSteiger bergSteiger) throws SeilPunktInvalidUsageException, NullBergsteigerException {
		if (bergSteiger == null) {
			throw new NullBergsteigerException("Input bergsteiger is null");
		}
		if (this.bergsteiger != null) {
			throw new SeilPunktInvalidUsageException("SeilPunkt schon besetzt");
		}
		this.bergsteiger = bergSteiger;
	}
	
	/**
	 * einen SeilPunkt befreien von BergSteiger
	 * @return befreirter Bergsteiger
	 * @throws SeilPunktInvalidUsageException kein BergSteiger ist auf dem SeilPunkt
	 */
	public BergSteiger bergSteigerBefreien() throws SeilPunktInvalidUsageException {
		if (this.bergsteiger == null) {
			throw new  SeilPunktInvalidUsageException("SeilPunkt noch nicht besetzt");
		}
		BergSteiger bergSteiger = this.bergsteiger;
		this.bergsteiger = null;
		return bergSteiger;
	}
	
	/**
	 * Markieren mit Marker den SeilPunkt
	 * @param marker
	 * @throws SeilPunktInvalidUsageException SeilPunkt ist schon markiert mit dieser Farbe
	 */
	public abstract void markieren(MarkierungStein marker) throws SeilPunktInvalidUsageException;
}
