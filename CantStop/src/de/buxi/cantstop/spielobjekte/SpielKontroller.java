/**
 * 
 */
package de.buxi.cantstop.spielobjekte;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * Dieses Objekt kontrolliert das gesamte Spiel
 * Regeln sind hier geprüft und durchgesetzt
 * Es ist ein Singleton
 * @author buxi
 *
 */
public class SpielKontroller {
			
	public static final int DEFAULT_ERSTE_SPIELER_NUM = 0;
	private Map<Farbe, Spieler> spielerMap;
	private List<Spieler> spielerReihe;  
	private Brett brett;
	private WurfelManager wurfelManager;
	private Map<Farbe, Collection<MarkierungStein>> alleMarkierungSteine;
	private List<BergSteiger> bergSteigers;
	private int actuelleSpielerNummer;
	private SpielState spielStatus;
	private Collection<ZweiWurfelPaar> falschePaarungen;
	private String errorMessage;
	
	/**
	 * @return the errorMessage
	 */
	public String getErrorMessage() {
		return errorMessage;
	}

	public SpielKontroller(Map<Farbe, Spieler>spielers, Brett brett,
			WurfelManager wurfelManager,
			Map<Farbe, Collection<MarkierungStein>> markierungSteine,
			List<BergSteiger> bergSteigers) {
		super();
		this.spielerMap = spielers;
		this.spielerReihe = new ArrayList<Spieler>(4);
		bestimmErsteSpieler();
		bestimmSpielerReiheStandard();  
		//this.spielerFactory = spielerFactory;
		this.brett = brett;
		this.wurfelManager = wurfelManager;
		this.alleMarkierungSteine = markierungSteine;
		this.bergSteigers = bergSteigers;
		
		// verteil Markierungsteine
		Map<Farbe, Collection<MarkierungStein>> spielerMarkierungsteine = new HashMap<Farbe, Collection<MarkierungStein>>();
		for (Spieler spieler: spielerReihe) {
			spielerMarkierungsteine.put(spieler.getFarbe(), this.alleMarkierungSteine.get(spieler.getFarbe()));
			this.alleMarkierungSteine.remove(spieler.getFarbe());
		}
		verteilMarkierungsteine(spielerMarkierungsteine);

		this.spielStatus = SpielState.INIT;
		this.falschePaarungen = new ArrayList<ZweiWurfelPaar>(3);
	}

	/**
	 * @return the falschePaarungen
	 */
	public Collection<ZweiWurfelPaar> getFalschePaarungen() {
		return falschePaarungen;
	}
	
	/**
	 * @return the spielStatus
	 */
	public SpielState getSpielStatus() {
		return spielStatus;
	}

	/**
	 * Setzt die Spieler in eine standard Reihe
	 */
	private void bestimmSpielerReiheStandard() {
		//getestet in SpielKontrollerSetupTest
		Set<Entry<Farbe, Spieler>> spielers = spielerMap.entrySet();
		int i = 0;
		for (Entry<Farbe, Spieler> entry : spielers) {
			spielerReihe.add(entry.getValue());
			entry.getValue().setReihe(i);
			i++; 
		}
	}

	/**
	 * Verteilt MarkierungSteine an Spielers je nachdem seine Farbe
	 * @param markierungSteine die verteilt wurden
	 */
	protected void verteilMarkierungsteine(Map<Farbe, Collection<MarkierungStein>> markierungSteine) {
		for (Farbe markierungSteinFarbe : markierungSteine.keySet()) {
			Spieler spieler = this.spielerMap.get(markierungSteinFarbe);
			if (spieler == null ) {
				throw new SpielerNotFoundException(markierungSteinFarbe + " Spieler nicht gefunden.");
			}
			spieler.addMarkierungSteine(markierungSteine.get(spieler.getFarbe()));
		}
	}
	
	/**
	 * @return the spielerMap
	 */
	public Map<Farbe, Spieler> getSpielerMap() {
		return spielerMap;
	}

	/**
	 * @return the brett
	 */
	public Brett getBrett() {
		return brett;
	}

	/**
	 * @return the wurfelManager
	 */
	public WurfelManager getWurfelManager() {
		return wurfelManager;
	}

	/**
	 * @return the alleMarkierungSteine
	 */
	public Map<Farbe, Collection<MarkierungStein>> getAlleMarkierungSteine() {
		return alleMarkierungSteine;
	}

	/**
	 * @return the bergSteigers
	 */
	public List<BergSteiger> getBergSteigers() {
		return bergSteigers;
	}
	
	/**
	 * @return the spielerReihe
	 */
	public List<Spieler> getSpielerInReihe() {
		return spielerReihe;
	}
	
	/**
	 * @return the actuelleSpielerNummer
	 */
	public int getActuelleSpielerNummer() {
		return actuelleSpielerNummer;
	}
	
	public Spieler getSpieler(Farbe farbe) {
		Spieler spieler = this.spielerMap.get(farbe);
		if (spieler == null ) {
			throw new SpielerNotFoundException(farbe + " Spieler nicht gefunden.");
		}
		return spieler;
	}
	
	/**
	 * Gib die freie Bergsteiger an aktuellen Spieler
	 */
	protected void verteilBergsteiger() {
		this.getActuelleSpieler().addBergSteigers(this.getBergSteigers());
		this.bergSteigers.clear();
	}
	
	/**
	 * Startet einen neune Spielzug
	 * Gib die freie Bergsteiger an aktuellen Spieler
	 */
	public void doSpielzugStarten() {
		checkSpielStatus(Arrays.asList(SpielState.IN_SPIEL));
		verteilBergsteiger();
		this.spielStatus = SpielState.IN_ZUG;
	}
	
	/**
	 * @throws SeilPunktInvalidUsageException 
	 * @throws KeinMarkierungSteinIstVorhandenException 
	 * @throws KeinBergsteigerAufDemWegException 
	 * @throws InvalidBergsteigerBewegungException 
	 * @throws InvalidWegNummerException 
	 */
	public void doSpielzugBeenden() throws KeinMarkierungSteinIstVorhandenException, SeilPunktInvalidUsageException, KeinBergsteigerAufDemWegException, InvalidBergsteigerBewegungException, InvalidWegNummerException {
		checkSpielStatus(Arrays.asList(SpielState.IN_ZUG, SpielState.KEIN_ANDERE_WAHL_ZUG_ENDE));
		// Markieren Bergsteiger
		Spieler aktuellerSpieler = getActuelleSpieler();
		if (SpielState.KEIN_ANDERE_WAHL_ZUG_ENDE.equals(spielStatus)) {
			this.bergSteigers = brett.entnimmBergsteigers();
		}
		else {
			this.bergSteigers = brett.markierenBergsteigers(aktuellerSpieler);
			Collection<Hutte> besetzteHutten = brett.getBesetzteHutten(aktuellerSpieler);
			//entnimm andere MarkierungSteine von Seile 
			Map<Farbe,Collection<MarkierungStein>> freieMarkierungSteine = brett.entnimmMarkierungSteineVonBesetztenWege(besetzteHutten);
			// freieMarkierungSteine sollen zuruck zu den Spieler
			verteilMarkierungsteine(freieMarkierungSteine);
			// TODO SpielEnde Kondition
			if (besetzteHutten.size() == 3) {
				this.spielStatus = SpielState.SPIEL_GEWONNEN;
				this.errorMessage="Spiel gewonnen";
				return;
			}
		}
		
		this.nextSpieler();
		verteilBergsteiger();
		this.spielStatus = SpielState.IN_ZUG;
	}
	
	/**
	 * Waehlt den naechsten Spieler und gib wurfelManager und Bergsteiger ihm
	 */
	protected void nextSpieler() {
		Spieler oldSpieler = getActuelleSpieler(); 
		this.actuelleSpielerNummer++;
		if (actuelleSpielerNummer > spielerReihe.size()-1) {
			actuelleSpielerNummer = 0;
		}
		wurfelManager.erwerbWurfels(getActuelleSpieler());
		getActuelleSpieler().addBergSteigers(oldSpieler.entnimmBergSteigers());
	}

	/**
	 * @return actueller Spieler in der Reihe
	 */
	public Spieler getActuelleSpieler() {
		return spielerReihe.get(this.actuelleSpielerNummer);
	}
	
	/**
	 * @param erwarteteStatuses muss entweder oder in diesen Statuses sein
	 */
	private void checkSpielStatus(List<SpielState> erwarteteStatuses) {
		if (!erwarteteStatuses.contains(this.getSpielStatus())) {
			throw new InvalidSpielStateException("Aktuelles Status:"+this.getSpielStatus()+" erwartete Statuses:"+erwarteteStatuses);
		}
	}
	/**
	 * Bestimmt erster Spieler und gibt ihm den WurfelManager
	 */
	public void doSpielStarten() {
		checkSpielStatus(Arrays.asList(SpielState.INIT));
		this.spielStatus = SpielState.IN_SPIEL;
		bestimmErsteSpieler();
		wurfelManager.erwerbWurfels(getActuelleSpieler());
	}

	

	/**
	 * irgendwie bestimmt die Nummer des ersten Spielers
	 * zurzeit : DEFAULT_ERSTE_SPIELER_NUM = 0
	 */
	protected void bestimmErsteSpieler() {
		this.actuelleSpielerNummer = DEFAULT_ERSTE_SPIELER_NUM;
	}
	
	public Collection<Wurfel> getWurfels() {
		WurfelManager wurfelManager = getWurfelManager();
		return wurfelManager.getWurfels();
	}

	public List<ZweiWurfelPaar> getMoglichePaarungen() throws WurfelNichtGeworfenException, InvalidWegNummerException {
		List<ZweiWurfelPaar> moeglichePaarungen = wurfelManager.getMoglichePaarungen();
		// dekorate mit wahlbar Information
		return addWaehlbarInformation(moeglichePaarungen);
	}
	
	/**
	 * Dekorate eine Paarung mit Waehlbar Information
	 * @param moeglichePaarungen
	 * @return
	 * @throws InvalidWegNummerException
	 * @throws WurfelNichtGeworfenException
	 */
	protected List<ZweiWurfelPaar> addWaehlbarInformation(
			List<ZweiWurfelPaar> moeglichePaarungen) throws InvalidWegNummerException, WurfelNichtGeworfenException {
		Spieler spieler = this.getActuelleSpieler(); 
		for (ZweiWurfelPaar zweiWurfelPaar : moeglichePaarungen) {
			Weg weg1 = brett.getWegByNummer(zweiWurfelPaar.getErsteSumme());
			Weg weg2 = brett.getWegByNummer(zweiWurfelPaar.getZweiteSumme());
			
			// gesperrte Wege konnte nicht gewahlt werden
			if (weg1.isGesperrt() && weg2.isGesperrt() ||
				weg1.isGesperrt() && weg2.isBergsteigerInHutte() ||
				weg2.isGesperrt() && weg1.isBergsteigerInHutte() ||
				weg1.isBergsteigerInHutte() && weg2.isBergsteigerInHutte()
				) {
				zweiWurfelPaar.setPaarungWaehlbar(PaarWahlInfo.NICHTWAEHLBAR);
				continue;
			} 
			// alle Bergsteiger auf dem Brett
			if (spieler.getBergSteigers().size() == 0) {
				if ( !weg1.isBergsteigerAufSeil() && !weg2.isBergsteigerAufSeil()) {
					zweiWurfelPaar.setPaarungWaehlbar(PaarWahlInfo.NICHTWAEHLBAR);
					continue;
				}
				if (weg1.isBergsteigerInHutte() && !weg2.isBergsteigerAufSeil()) {
					zweiWurfelPaar.setPaarungWaehlbar(PaarWahlInfo.NICHTWAEHLBAR);
					continue;
				}
				if (weg2.isBergsteigerInHutte() && !weg1.isBergsteigerAufSeil()) {
					zweiWurfelPaar.setPaarungWaehlbar(PaarWahlInfo.NICHTWAEHLBAR);
					continue;
				}
			}
			// ein Bergsteiger im Hand
			if (spieler.getBergSteigers().size() == 1) {
					// MITWEGINFO Fall
					/*  wenn schon 2 Bergsteiger bestellt sind und
					   die Paarung ergibt keine Wege wo sie stehen */
					if (weg1.isFrei() && weg2.isFrei() && 
						!weg1.isBergsteigerAufSeil() && !weg2.isBergsteigerAufSeil() &&
						!weg1.isBergsteigerInHutte() && !weg2.isBergsteigerInHutte()) {
						if (weg1.getNummer() == weg2.getNummer()) {
							zweiWurfelPaar.setPaarungWaehlbar(PaarWahlInfo.WAEHLBAR);
						}else {
							zweiWurfelPaar.setPaarungWaehlbar(PaarWahlInfo.MITWEGINFO);
						}
						continue;
					}
			}
			zweiWurfelPaar.setPaarungWaehlbar(PaarWahlInfo.WAEHLBAR);
		}
		return moeglichePaarungen;
	}

	/**
	 * @throws InvalidWegNummerException 
	 * @throws WurfelNichtGeworfenException 
	 * @throws InvalidBergsteigerBewegungException 
	 * @throws KeinBergsteigerAufDemWegException 
	 * @throws SeilPunktInvalidUsageException 
	 * @throws KeinMarkierungSteinIstVorhandenException 
	 */
	public void doWerfen() throws WurfelNichtGeworfenException, InvalidWegNummerException, KeinMarkierungSteinIstVorhandenException, SeilPunktInvalidUsageException, KeinBergsteigerAufDemWegException, InvalidBergsteigerBewegungException {
		checkSpielStatus(Arrays.asList(SpielState.IN_ZUG));
		this.errorMessage = "";
		wurfelManager.allWerfen();
		this.spielStatus=SpielState.GEWORFEN;
		this.falschePaarungen = new ArrayList<ZweiWurfelPaar>(3);
		// pruf Paarungen, ob es waehlbar ist
		if (!gibtEsWaehlbarePaarung(this.getMoglichePaarungen())) {
			// SpielZug muss beendet sein
			this.spielStatus=SpielState.KEIN_ANDERE_WAHL_ZUG_ENDE;
			this.errorMessage = "Keine gewaehlbare Paarung";
			doSpielzugBeenden();
		}
	}
	
	/**
	 * NUR FUR TESTING
	 * gleich wie doWerfen() aber wir konnen ein WurfelManager geben
	 * @param wurfelManager 
	 */
	protected void testDoWerfen(WurfelManager wurfelManager) throws WurfelNichtGeworfenException, InvalidWegNummerException, KeinMarkierungSteinIstVorhandenException, SeilPunktInvalidUsageException, KeinBergsteigerAufDemWegException, InvalidBergsteigerBewegungException {
		this.setWurfelManager(wurfelManager);
		doWerfen();
	}
	/**
	 * NUR FUR TESTING
	 * @param wurfelManager the wurfelManager to set
	 */
	private void setWurfelManager(WurfelManager wurfelManager) {
		this.wurfelManager = wurfelManager;
	}

	/**
	 * @return
	 * @throws WurfelNichtGeworfenException
	 * @throws InvalidWegNummerException
	 */
	protected boolean gibtEsWaehlbarePaarung(List<ZweiWurfelPaar> moeglichePaarungen)
			throws WurfelNichtGeworfenException, InvalidWegNummerException {
		boolean gibtEsWaehlbar = false;;
		for (ZweiWurfelPaar zweiWurfelPaar : moeglichePaarungen) {
			if (PaarWahlInfo.WAEHLBAR.equals(zweiWurfelPaar.getPaarungWaehlbar()) ||
				PaarWahlInfo.MITWEGINFO.equals(zweiWurfelPaar.getPaarungWaehlbar())) {
				gibtEsWaehlbar = true;
			}
		}
		return gibtEsWaehlbar;
	}
	
	/**
	 * TODO UNITTEST
	 * @param wegNummer 
	 */
	public void doPaarung(ZweiWurfelPaar gewahltePaarung, int wegNummer) throws WurfelNichtGeworfenException, SeilPunktInvalidUsageException, KeinBergSteigerIstVorhandenException, InvalidWegNummerException, InvalidBergsteigerBewegungException, KeinMarkierungSteinIstVorhandenException, NullBergsteigerException, KeinBergsteigerAufDemWegException {
		checkSpielStatus(Arrays.asList(SpielState.GEWORFEN));
		this.errorMessage = "";
		if (!this.getMoglichePaarungen().contains(gewahltePaarung)) {
			throw new InvalidPaarungException("Paar:" + gewahltePaarung + ", wegNummber:" + wegNummer);
		}
		if (PaarWahlInfo.NICHTWAEHLBAR.equals(gewahltePaarung.getPaarungWaehlbar())) {
			throw new InvalidPaarungException("Paar nicht waehlbar:" + gewahltePaarung + ", wegNummber:" + wegNummer);
		}  
		if (PaarWahlInfo.MITWEGINFO.equals(gewahltePaarung.getPaarungWaehlbar()) &&
			(wegNummer != gewahltePaarung.getErsteSumme() && wegNummer != gewahltePaarung.getZweiteSumme())) {
				throw new InvalidWegNummerException("Paar:" + gewahltePaarung + ", wegNummber:" + wegNummer);
		}

		paarungDurchsetzen(gewahltePaarung, wegNummer, getActuelleSpieler());
	}
	/**
	 * BergSteiger bewegen
	 * Weg könnte ausgewählt werden, wenn
	 * - frei (Hütte ist nicht markiert) ist und 
	 * -   (Spieler ist dran und es gibt einen BergSteiger darauf oder
	 * -    es gibt einen passenden MarkierungsStein darauf oder
	 * -    Spieler hat noch freie BergSteiger in seinem Hand)
	 * @param geWaehlteWegNummer 
	 * 
	 * @param spieler
	 * @throws KeinBergSteigerIstVorhandenException 
	 * @throws InvalidWegNummerException 
	 * @throws InvalidBergsteigerBewegungException 
	 * @throws KeinMarkierungSteinIstVorhandenException 
	 * @throws NullBergsteigerException 
	 * @throws KeinBergsteigerAufDemWegException 
	 * @throws IllegalWegWahlenException
	 */
	protected void paarungDurchsetzen(ZweiWurfelPaar gewahltePaarung, int geWaehlteWegNummer, Spieler spieler) throws WurfelNichtGeworfenException, SeilPunktInvalidUsageException, InvalidWegNummerException, InvalidBergsteigerBewegungException, KeinMarkierungSteinIstVorhandenException, NullBergsteigerException, KeinBergsteigerAufDemWegException {
		boolean ersteStellungOK = true;
		boolean stellungOK = true;
		boolean zweiteStellungOK = true;
		if (geWaehlteWegNummer>0) {
			Weg weg = brett.getWegByNummer(geWaehlteWegNummer);
			try {
				wegWahlen(spieler, weg);
			} catch (KeinBergSteigerIstVorhandenException e) {
				System.out.println(e.toString());
				stellungOK = false;
			}
		}
		else {
			int wegNummer1 = gewahltePaarung.getErsteSumme();
			int wegNummer2 = gewahltePaarung.getZweiteSumme();
			Weg weg1 = brett.getWegByNummer(wegNummer1);
			Weg weg2= brett.getWegByNummer(wegNummer2);
			try {
				wegWahlen(spieler, weg1);
			} catch (KeinBergSteigerIstVorhandenException e) {
				System.out.println(e.toString());
				ersteStellungOK = false;
			}
			try {
				wegWahlen(spieler, weg2);
			} catch (KeinBergSteigerIstVorhandenException e) {
				System.out.println(e.toString());
				zweiteStellungOK = false;
			}
		}
		
		if (!stellungOK && !ersteStellungOK && !zweiteStellungOK) {
			// diese Paarung konnte nicht gestellt werden, Input ist falsche
			this.errorMessage = "GEWAHLTE_PAARUNG_FALSCH";

			/*//  falsche Paarung system muss entfert werden von hier aus
			this.falschePaarungen.add(gewahltePaarung);
			Collection<ZweiWurfelPaar> mp = getMoglichePaarungen();
			mp.removeAll(falschePaarungen);
			if (mp.size() == 0) {
				this.spielStatus = SpielState.KEIN_ANDERE_WAHL_ZUG_ENDE;
				doSpielzugBeenden();
			}
			//  bis hier
			*/
		} else {
			// minimum eine Paarung erfolgte
			this.spielStatus=SpielState.IN_ZUG;
			errorMessage = "Paar benutzt:" + gewahltePaarung;
		}
	
	}

	/**
	 * Stell oder beweg Bergsteiger auf dem Weg
	 * Wenn es keinen Markierungstein auf dem Weg gibt, stell den Bergsteiger unten
	 * Wenn es Markierungstein auf dem Weg gibt, stell den Bergsteiger nach ihm 
	 * Wenn der Bergsteiger schon auf dem Weg ist, beweg ihn weiter (auch in die Hutte)
	 * @param spieler
	 * @param weg1
	 * @throws KeinBergSteigerIstVorhandenException
	 */
	protected void wegWahlen(Spieler spieler, Weg weg1)
			throws SeilPunktInvalidUsageException,
			KeinBergSteigerIstVorhandenException, InvalidBergsteigerBewegungException, NullBergsteigerException {
		if (weg1.isFrei()) {
			if (!weg1.isBergsteigerAufSeil()) {
				// kein Bergsteiger auf dem Weg
				if (weg1.isMarkierungSteinAufSeil(spieler.getFarbe())) {
					weg1.stellBergsteigerNachMarkierungStein(spieler.getEinBergSteiger(), spieler.getFarbe());
				} else {
					weg1.stellBergsteigerUnten(spieler.getEinBergSteiger());	
				}
				
			} else {
				// Bergsteiger auf dem Weg
				weg1.bewegBergsteiger();
			}
		}
	}
	
	/**
	 * Generiert ein Transfer Objekt fur Klients
	 * @return
	 * @throws WurfelNichtGeworfenException
	 * @throws InvalidWegNummerException 
	 */
	public SpielTransferObject doGetTransferObject() throws WurfelNichtGeworfenException, InvalidWegNummerException {
		SpielTransferObject to = new SpielTransferObject();
		to.spielStatus = this.spielStatus;
		to.actuelleSpieler = this.getActuelleSpieler();
		to.brettDisplay = this.getBrett().display();
		to.actuelleSpielerNummer = this.getActuelleSpielerNummer();
		to.spielerList = this.getSpielerInReihe();
		to.errorMessage = this.errorMessage;
		to.moeglichePaarungen = null;
		to.falschePaarungen = null;
		to.wurfels = null;
		if (SpielState.GEWORFEN.equals(spielStatus) || 
			SpielState.GEWAHLTE_PAARUNG_FALSCH.equals(spielStatus)	) {
			to.moeglichePaarungen = this.getMoglichePaarungen();
			if (SpielState.GEWAHLTE_PAARUNG_FALSCH.equals(spielStatus) && 
				to.moeglichePaarungen != null && this.falschePaarungen!=null ) {
				// TODO lehet megis mukodik, ellenorizni!!!! nicht funktioniert, removeAll soll deep check machen
				to.moeglichePaarungen.removeAll(this.falschePaarungen);
			}
			to.falschePaarungen = this.falschePaarungen;
			to.wurfels = this.getWurfels();
		}
		return to;
	}
}
