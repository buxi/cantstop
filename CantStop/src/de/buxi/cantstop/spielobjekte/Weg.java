/**
 * 
 */
package de.buxi.cantstop.spielobjekte;

import java.util.List;

/**
 * @author buxi
 *
 */
public class Weg {
	/**
	 * fuer Nummerierung, kann 2-12 sein
	 */
	private int nummer;
	
	private Hutte hutte;
	
	private List<SeilPunkt> seilPunkte;

	/**
	 * @param nummer
	 * @param hutte
	 * @param seilPunkte
	 */
	public Weg(int nummer, Hutte hutte, List<SeilPunkt> seilPunkte) {
		super();
		this.nummer = nummer;
		this.hutte = hutte;
		this.seilPunkte = seilPunkte;
	}

	/**
	 * @return the nummer
	 */
	public int getNummer() {
		return nummer;
	}

	/**
	 * @return the hutte
	 */
	public Hutte getHutte() {
		return hutte;
	}

	/**
	 * @return the seilPunkte
	 */
	public List<SeilPunkt> getSeilPunkte() {
		return seilPunkte;
	} 
	
	/**
	 * Ein Weg ist gesperrt, wenn es einen MarkierungStein in der Hütte  gibt
	 *   
	 * @return true, wenn die Hütte markiert ist
	 */
	public boolean isGesperrt() {
		return !isFrei();
	}
	
	/**
	 * Ein Weg ist frei, wenn es keinen MarkierungSteing in der Hütte gibt
	 * @return true, wenn die Hütte nicht markiert ist
	 */
	public boolean isFrei() {
		return hutte.getMarkierungStein() == null ? true : false; 
	}
	
	/**
	 * @return wenn es einen Bergsteiger auf dem Seil gibt
	 */
	public boolean isBergsteigerAufSeil() {
		return woIstBergsteigerAufSeil() < 0 ? false : true;
	}
	
	/**
	 * @return Koordinate des Bergsteigers auf dem Seil 0 ist unterster SeilPunkt
	 * @return -1000 wenn keinen Bergsteiger auf dem Seil gibt
	 */
	public int woIstBergsteigerAufSeil() {
		for (int i = 0; i <= seilPunkte.size()-1; i++) {
			SeilPunkt seilPunkt = seilPunkte.get(i);
			if (seilPunkt.getBergsteiger() != null) {
				return i;
			}
		}
		return -1000;
	}

	/**
	 * @return wenn es einen MarkierungStein auf dem Seil gibt
	 */
	public boolean isMarkierungSteinAufSeil(Farbe farbe) {
		return woIstMarkierungsteinFurFarbe(farbe) < 0 ? false : true;
	}
	
	/**
	 * Such nach einem farbe farbige MarkierungStein auf dem Seil
	 * @param farbe 
	 * @return Koordinate des MarkierungSteins 0 ist unterster SeilPunkt
	 * @return -1000 kein MarkierungStein gefunden
	 */
	public int woIstMarkierungsteinFurFarbe(Farbe farbe) {
		for (int i = 0; i <= seilPunkte.size()-1; i++) {
			SeilPunkt seilPunkt = seilPunkte.get(i);
			if (seilPunkt.isMarkierungsteinFurFarbe(farbe)) {
				return i;
			}
		}
		return -1000;
	}
	
	/**
	 * @return unterster SeilPunkt 
	 */
	public SeilPunkt getUntersteSeilPunkt() {
		return seilPunkte.get(0);
	}
	
	/**
	 * @return SeilPunkt neben der Hutte
	 */
	public SeilPunkt getObersteSeilPunkt() {
		return seilPunkte.get(seilPunkte.size()-1);
	}
	
	/**
	 * TODO es muss SYNCHRONIZED werden
	 * @param einBergSteiger
	 * @throws SeilPunktInvalidUsageException
	 * @throws NullBergsteigerException 
	 */
	public void stellBergsteigerUnten(BergSteiger einBergSteiger) throws SeilPunktInvalidUsageException, NullBergsteigerException {
		SeilPunkt untersteSeilPunkt = getUntersteSeilPunkt();
		untersteSeilPunkt.bergSteigerStellen(einBergSteiger);
	}

	/**
	 * Stellt Bergsteiger nach dem MarkierungStein
	 * @param bergSteiger 
	 * @param farbe
	 * @throws SeilPunktInvalidUsageException
	 * @throws NullBergsteigerException 
	 * @throws InvalidBergsteigerBewegungException 
	 */
	public void stellBergsteigerNachMarkierungStein(BergSteiger bergSteiger, Farbe farbe) throws SeilPunktInvalidUsageException, NullBergsteigerException, InvalidBergsteigerBewegungException {
		int markierungSteinKoord = woIstMarkierungsteinFurFarbe(farbe);
		if (markierungSteinKoord < 0) {
			// kein MarkierungStein auf dem Weg
			throw new InvalidBergsteigerBewegungException("kein MarkierungStein auf dem Weg");
		}
		if (markierungSteinKoord == obersteSeilPunktKoord()) {
			hutte.bergSteigerStellen(bergSteiger);
		} else {
			stellBergsteigerAufSeilPunkt(markierungSteinKoord + 1, bergSteiger);
		}
	}
	/**
	 * @param koord wohin soll Bergsteiger gestell werden
	 * @param bergSteiger
	 * @throws SeilPunktInvalidUsageException
	 * @throws NullBergsteigerException
	 * @throws InvalidBergsteigerBewegungException
	 */
	private void stellBergsteigerAufSeilPunkt(int koord, BergSteiger bergSteiger) throws SeilPunktInvalidUsageException, NullBergsteigerException, InvalidBergsteigerBewegungException {
		if (koord < 0 || koord > seilPunkte.size()-1) {
			throw new InvalidBergsteigerBewegungException("Invalid SeilPunkt Koordinate" + koord);
		}
		if (this.isGesperrt()) {
			throw new InvalidBergsteigerBewegungException("Weg is gesperrt, Bergsteiger konnte nicht gestellt werden");
		}
		SeilPunkt seilPunkt = seilPunkte.get(koord);
		seilPunkt.bergSteigerStellen(bergSteiger);
	}

	/**
	 * Bergsteiger beweg sich einen Schritt oben, wenn oberster SeilPunkt erreicht ist, geht weiter in der Hutte
	 * 
	 * @throws SeilPunktInvalidUsageException
	 * @throws InvalidBergsteigerBewegungException kein Bergsteiger auf dem Seil
	 * @throws NullBergsteigerException
	 */
	public void bewegBergsteiger() throws SeilPunktInvalidUsageException, InvalidBergsteigerBewegungException, NullBergsteigerException {
		int bergSteigerKoord = woIstBergsteigerAufSeil();
		if (bergSteigerKoord < 0) {
			throw new InvalidBergsteigerBewegungException("kein Bergsteiger auf dem Seil");
		}
		SeilPunkt bergSteigerSeilPunkt = seilPunkte.get(bergSteigerKoord);
		if (bergSteigerKoord == obersteSeilPunktKoord()) {
			// beweg BergSteiger in der Hutte
			hutte.bergSteigerStellen(bergSteigerSeilPunkt.bergSteigerBefreien());
		} else {
			// beweg BergSteiger weiter
			stellBergsteigerAufSeilPunkt(bergSteigerKoord + 1 , bergSteigerSeilPunkt.bergSteigerBefreien());
		}
	}

	/**
	 * Neuen markierungStein setzen auf dem Bergsteigers Position
	 * Es gibt noch nicht markierungStein afu dem Weg
	 * @param markierungStein
	 * @return freier Bergsteiger
	 * @throws SeilPunktInvalidUsageException 
	 * @throws KeinBergsteigerAufDemWegException 
	 */
	public BergSteiger markierenBergsteigerAufDemWegNeuSetzen(MarkierungStein markierungStein) throws SeilPunktInvalidUsageException, KeinBergsteigerAufDemWegException {
		return markierenBergSteiger(markierungStein);
	}
	
	/**
	 * Beweg spielerFarbe MarkierungStein auf dem Weg
	 * @param spielerFarbe
	 * @return freier Bergsteiger
	 * @throws SeilPunktInvalidUsageException
	 * @throws KeinBergsteigerAufDemWegException 
	 */
	public BergSteiger markierenBergsteigerAufDemWegBeweg(Farbe spielerFarbe) throws SeilPunktInvalidUsageException, KeinBergsteigerAufDemWegException {
		int markierungSteinKoord = woIstMarkierungsteinFurFarbe(spielerFarbe);
		SeilPunkt seilPunktMarkierungStein = getSeilPunkte().get(markierungSteinKoord); 
		MarkierungStein markierungStein = seilPunktMarkierungStein.unmarkieren(spielerFarbe);
		
		return markierenBergSteiger(markierungStein);
	}

	/**
	 * @param markierungStein
	 * @return freier Bergsteiger
	 * @throws SeilPunktInvalidUsageException
	 * @throws KeinBergsteigerAufDemWegException 
	 */
	protected BergSteiger markierenBergSteiger(MarkierungStein markierungStein)
			throws SeilPunktInvalidUsageException, KeinBergsteigerAufDemWegException {
		// ist Bergsteiger in der Hutte?
		if (isBergsteigerInHutte()) {
			hutte.markieren(markierungStein);
			return hutte.bergSteigerBefreien();
		}
		
		// ist Bergsteiger auf dem Seil?
		if (isBergsteigerAufSeil()) {
			int bergSteigerKoord = woIstBergsteigerAufSeil();
			SeilPunkt seilPunkt = seilPunkte.get(bergSteigerKoord);
			seilPunkt.markieren(markierungStein);
			return seilPunkt.bergSteigerBefreien();
		}
		throw new KeinBergsteigerAufDemWegException("Bergsteiger ist weder in der Hutte noch auf dem Seil");
	}

	/**
	 * @return true wenn Bergsteiger in der Hutte ist 
	 */
	public boolean isBergsteigerInHutte() {
		if (hutte.getBergsteiger() != null) {
			return true;
		}
		return false;
	}
	
	public int obersteSeilPunktKoord() {
		return seilPunkte.size()-1;
	}
	
	/**
	 * @return entNomender Bergsteiger
	 * @throws SeilPunktInvalidUsageException kein Bergsteiger in der Hutte
	 */
	public BergSteiger entnimmBergsteigerVonHutte() throws SeilPunktInvalidUsageException {
		return hutte.bergSteigerBefreien();
	}
	
	/**
	 * @return entNomender Bergsteiger
	 * @throws InvalidBergsteigerBewegungException kein Bergsteiger auf dem Seil
	 */
	public BergSteiger entnimmBergsteigerVonSeil() throws SeilPunktInvalidUsageException, InvalidBergsteigerBewegungException {
		int bergSteigerKoord = woIstBergsteigerAufSeil();
		if (bergSteigerKoord < 0) {
			throw new InvalidBergsteigerBewegungException("kein Bergsteiger auf dem Seil");
		}
		SeilPunkt seilPunkt = seilPunkte.get(bergSteigerKoord);
		return seilPunkt.bergSteigerBefreien();
	}

	/**
	 * @param farbe des Spielers
	 * @return  true, wenn der Weg mit diesem Spieler gesperrt ist (farbe MarkierungStein ist in der Hutte)
	 */
	public boolean isGesperrtVonSpieler(Farbe farbe) {
		if (hutte.getMarkierungStein() != null && farbe.equals(hutte.getMarkierungStein().getFarbe())) {
			return true;
		}
		return false;
	}
}
