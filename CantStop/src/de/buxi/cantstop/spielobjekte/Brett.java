package de.buxi.cantstop.spielobjekte;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Brett {
	/**
	 * Alle 11 Wege auf dem Brett
	 */
	private List<Weg> wege;
	
	public Brett(List<Weg> wege) {
		this.wege = wege;
	}

	/**
	 * @return the wege
	 */
	public List<Weg> getWege() {
		return wege;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Brett [wege=");
		builder.append(wege);
		builder.append("]");
		return builder.toString();
	}
	
	public String display() {
		String spaces = "                                                                ";
		StringBuilder result = new StringBuilder();
		for (int i = wege.size()-1; i >= 0; i--) {
			result.append("--------------------------------------------\n");
			Weg weg = wege.get(i);
			if (weg.getNummer()<10) {
				result.append(" ");
			}
			result.append(weg.getNummer());
			
			// displaying Hutte
			result.append(spaces.substring(0, Math.abs(weg.getNummer()-7)*3   ));
			result.append(" H(");
			BergSteiger bergSteiger = weg.getHutte().getBergsteiger();
			if (bergSteiger!=null) {
				result.append("BS");
			}
			MarkierungStein markierungStein = weg.getHutte().getMarkierungStein();
			if (markierungStein!=null) {
				result.append(markierungStein.getFarbe());
			}
			result.append(")");
			
			// displaying SeilPunkte
			List<SeilPunkt> seilPunkte = weg.getSeilPunkte();
			for (int j = seilPunkte.size()-1; j >= 0; j--) {
				SeilPunkt seilPunkt = seilPunkte.get(j);
				result.append(" (");
				bergSteiger = seilPunkt.getBergsteiger();
				if (bergSteiger!=null) {
					result.append("BS");
				}
				Collection<MarkierungStein> markierungSteine = seilPunkt.getMarkierungSteine();
				for (MarkierungStein markierungStein2 : markierungSteine) {
					result.append(markierungStein2.getFarbe());
					result.append("|");
				}
				
				result.append(")");
			}
			result.append("\n");
		}
		return result.toString();
	}

	/**
	 * @param wegNummer gewurfelt Wert von 2 bis 12
	 * @return Weg 
	 * @throws InvalidWegNummerException 
	 */
	public Weg getWegByNummer(int wegNummer) throws InvalidWegNummerException {
		// TODO einfache Transformation des WegNummer zu WegID
		if (wegNummer < 2 || wegNummer > 12) {
			throw new InvalidWegNummerException("Invalid wegnummer" + wegNummer +", Wegnummer zwischen 2 und 12 ist gultig");
		}
		if (wegNummer - 2 < 0 || wegNummer - 2 > wege.size()-1) {
			throw new InvalidWegNummerException("Invalid wegnummer" + wegNummer +" ausser Brett");
		}
		return wege.get(wegNummer - 2);
	}

	/**
	 * Markiert die Bergsteiger auf den Wege 
	 * @param aktuelleSpieler 
	 * @return freie Bergsteiger
	 * @throws KeinMarkierungSteinIstVorhandenException 
	 * @throws SeilPunktInvalidUsageException 
	 * @throws KeinBergsteigerAufDemWegException 
	 */
	public List<BergSteiger> markierenBergsteigers(Spieler spieler) throws KeinMarkierungSteinIstVorhandenException, SeilPunktInvalidUsageException, KeinBergsteigerAufDemWegException {
		List<BergSteiger> freieBergsteiger = new ArrayList<BergSteiger>(3);
		for (Weg weg : wege) {
			if (weg.isBergsteigerAufSeil() || weg.isBergsteigerInHutte()) {
				BergSteiger bergSteiger;
				if (weg.isMarkierungSteinAufSeil(spieler.getFarbe())) {
					//beweg Markierungstein
					bergSteiger = weg.markierenBergsteigerAufDemWegBeweg(spieler.getFarbe());
				}
				else {
					// setz einen neuen Markierungstein
					bergSteiger = weg.markierenBergsteigerAufDemWegNeuSetzen(spieler.getEinMarkierungStein());
				}
				freieBergsteiger.add(bergSteiger);
			}
		}
		return freieBergsteiger;
	}
	
	/**
	 * Entnehmen der Bergsteiger von den Wege 
	 * @param spieler 
	 * @return freie Bergsteiger
	 * @throws KeinMarkierungSteinIstVorhandenException 
	 * @throws SeilPunktInvalidUsageException 
	 * @throws InvalidBergsteigerBewegungException 
	 */
	public List<BergSteiger> entnimmBergsteigers() throws KeinMarkierungSteinIstVorhandenException, SeilPunktInvalidUsageException, InvalidBergsteigerBewegungException {
		List<BergSteiger> freieBergsteiger = new ArrayList<BergSteiger>(3);
		for (Weg weg : wege) {
			BergSteiger bergSteiger;
			if (weg.isBergsteigerAufSeil()) {
				bergSteiger = weg.entnimmBergsteigerVonSeil();
				freieBergsteiger.add(bergSteiger);
			}
			if (weg.isBergsteigerInHutte()) {
				bergSteiger = weg.entnimmBergsteigerVonHutte();
				freieBergsteiger.add(bergSteiger);
			}
		}
		return freieBergsteiger;
	}

	public Collection<Hutte>getBesetzteHutten(Spieler aktuellerSpieler) {
		Collection<Hutte> besetzteHutten = new ArrayList<Hutte>();
		for (Weg weg : wege) {
			if (weg.isGesperrtVonSpieler(aktuellerSpieler.getFarbe())) {
				besetzteHutten.add(weg.getHutte());	
			}
		}
		return besetzteHutten;
	}

	/**
	 * Entfernt alle MarkierungStein vom Seile von markierten Wege
	 * @param besetzteHutten
	 * @return entfernte MarkierungSteine
	 * @throws InvalidWegNummerException
	 */
	public Map<Farbe, Collection<MarkierungStein>> entnimmMarkierungSteineVonBesetztenWege(
			Collection<Hutte> besetzteHutten) throws InvalidWegNummerException {
		// Initialize return Map
		Map<Farbe, Collection<MarkierungStein>> freieMarkierungSteine = new HashMap<Farbe, Collection<MarkierungStein>>();
		for (Hutte hutte : besetzteHutten) {
			Weg weg = this.getWegByNummer(hutte.getWegNummer());
			for (SeilPunkt seilPunkt : weg.getSeilPunkte()) {
				for (MarkierungStein markierungStein : seilPunkt.getMarkierungSteine()) {
					if (!freieMarkierungSteine.containsKey(markierungStein.getFarbe())) {
						freieMarkierungSteine.put(markierungStein.getFarbe(), new ArrayList<MarkierungStein>());
					}
					freieMarkierungSteine.get(markierungStein.getFarbe()).add(markierungStein);
				}
			}
		}
		return freieMarkierungSteine;
	}
}
