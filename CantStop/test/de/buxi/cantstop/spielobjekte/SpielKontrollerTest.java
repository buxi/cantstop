package de.buxi.cantstop.spielobjekte;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class SpielKontrollerTest extends SpringLoaderSuperClass{

	@Before
	public void setUp() throws Exception {
	}

	@Test(expected=SpielerNotFoundException.class)
	public void testVerteilMarkierungsteineKeinSpielerMitFarbe() {
		SpielKontroller spielKontroller = (SpielKontroller)ac.getBean("testSpielKontroller");
		// verteil Markierungsteine
		Map<Farbe, Collection<MarkierungStein>> spielerMarkierungsteine = new HashMap<Farbe, Collection<MarkierungStein>>();
		spielerMarkierungsteine.put(Farbe.SCHWARTZ, SpielFactory.createMarkierungSteineStatic(3, Farbe.SCHWARTZ));
		spielKontroller.verteilMarkierungsteine(spielerMarkierungsteine);
	}

	@Test()
	public void testVerteilMarkierungsteinePositiveMit2Farben() {
		SpielKontroller spielKontroller = (SpielKontroller)ac.getBean("testSpielKontroller");
		// verteil Markierungsteine
		Map<Farbe, Collection<MarkierungStein>> spielerMarkierungsteine = new HashMap<Farbe, Collection<MarkierungStein>>();
		spielerMarkierungsteine.put(Farbe.BLAU, SpielFactory.createMarkierungSteineStatic(3, Farbe.BLAU));
		spielerMarkierungsteine.put(Farbe.ROT, SpielFactory.createMarkierungSteineStatic(3, Farbe.ROT));
		spielKontroller.verteilMarkierungsteine(spielerMarkierungsteine);
		
		Spieler spielerBLAU = spielKontroller.getSpieler(Farbe.BLAU);
		assertEquals("BLAU Spieler soll 3 Markierungsteine behalten", 13, spielerBLAU.getMarkierungSteine().size());
		Spieler spielerROT = spielKontroller.getSpieler(Farbe.ROT);
		assertEquals("ROT Spieler soll 3 Markierungsteine behalten", 13, spielerROT.getMarkierungSteine().size());
	}

	@Test
	public void testActuellerSpieler() {
		SpielKontroller spielKontroller = (SpielKontroller)ac.getBean("testSpielKontroller");
		Spieler aktuelleSpieler = spielKontroller.getActuelleSpieler();
		assertEquals("Aktueller Spieler soll actuelleSpielerNummer haben", spielKontroller.getActuelleSpielerNummer(), aktuelleSpieler.getReihe());
	}

	@Test
	public void testNextSpieler() {
		SpielKontroller spielKontroller = (SpielKontroller)ac.getBean("testSpielKontroller");
		spielKontroller.doSpielStarten();
		spielKontroller.doSpielzugStarten(); // verteilt Bergsteiger auch
		
		Spieler aktuelleSpieler = spielKontroller.getActuelleSpieler();
		assertEquals("Aktueller Spieler soll actuelleSpielerNummer haben", spielKontroller.getActuelleSpielerNummer(), aktuelleSpieler.getReihe());
		
		if (Farbe.BLAU.equals(aktuelleSpieler.getFarbe())) {
			spielKontroller.nextSpieler();
			assertEquals("naechste Spieler soll ROT sein", Farbe.ROT, spielKontroller.getActuelleSpieler().getFarbe());
			
		}
		if (Farbe.ROT.equals(aktuelleSpieler.getFarbe())) {
			spielKontroller.nextSpieler();
			assertEquals("naechste Spieler soll BLAU sein", Farbe.BLAU, spielKontroller.getActuelleSpieler().getFarbe());
		}
		assertEquals("vorheriger Spieler soll keinen Bergsteiger haben", 0, aktuelleSpieler.getBergSteigers().size());
		assertEquals("aktueller Spieler soll alle 3 Bergsteiger haben", 3, spielKontroller.getActuelleSpieler().getBergSteigers().size());
		assertEquals("aktueller Spieller soll den WurfelManager besitzen", spielKontroller.getActuelleSpieler(), spielKontroller.getWurfelManager().getBesitzer());
	}
	
	@Test
	public void testVerteilBergsteiger() {
		SpielKontroller spielKontroller = (SpielKontroller)ac.getBean("testSpielKontroller");
		spielKontroller.verteilBergsteiger();
		assertEquals("aktueller Spieler soll alle 3 Bergsteiger haben", 3, spielKontroller.getActuelleSpieler().getBergSteigers().size());
		assertEquals("spielKontroller soll 0 Bergsteiger haben", 0, spielKontroller.getBergSteigers().size());
	}

	@Test(expected=InvalidSpielStateException.class)
	public void testStartSpielZugMitInvalidState() {
		SpielKontroller spielKontroller = (SpielKontroller)ac.getBean("testSpielKontroller");
		spielKontroller.verteilBergsteiger();
		spielKontroller.doSpielzugStarten();
	}

	@Test
	public void testStartSpielZug() {
		SpielKontroller spielKontroller = (SpielKontroller)ac.getBean("testSpielKontroller");
		spielKontroller.doSpielStarten();
		spielKontroller.verteilBergsteiger();
		spielKontroller.doSpielzugStarten();
		assertEquals("aktueller Spieler soll alle 3 Bergsteiger haben", 3, spielKontroller.getActuelleSpieler().getBergSteigers().size());
		assertEquals("spielKontroller soll 0 Bergsteiger haben", 0, spielKontroller.getBergSteigers().size());
		assertEquals("INIT State", SpielState.IN_ZUG, spielKontroller.getSpielStatus());
	}
	@Test(expected=InvalidSpielStateException.class)
	public void testDoStartSpielZugInvalidState() {
		SpielKontroller spielKontroller = (SpielKontroller)ac.getBean("testSpielKontroller");
		spielKontroller.verteilBergsteiger();
		spielKontroller.doSpielzugStarten();
		assertEquals("aktueller Spieler soll alle 3 Bergsteiger haben", 3, spielKontroller.getActuelleSpieler().getBergSteigers().size());
		assertEquals("spielKontroller soll 0 Bergsteiger haben", 0, spielKontroller.getBergSteigers().size());
		assertEquals("IN_ZUG State erwartet", SpielState.IN_ZUG, spielKontroller.getSpielStatus());
	}

	@Test(expected=InvalidSpielStateException.class)
	public void testDoStartSpielZugMitInvalidState() {
		SpielKontroller spielKontroller = (SpielKontroller)ac.getBean("testSpielKontroller");
		spielKontroller.verteilBergsteiger();
		spielKontroller.doSpielzugStarten();
	}
	
	@Test
	public void testDoWerfenPositiv() throws WurfelNichtGeworfenException, InvalidWegNummerException, KeinMarkierungSteinIstVorhandenException, SeilPunktInvalidUsageException, KeinBergsteigerAufDemWegException, InvalidBergsteigerBewegungException {
		SpielKontroller spielKontroller = (SpielKontroller)ac.getBean("testSpielKontroller");
		spielKontroller.doSpielStarten();
		spielKontroller.verteilBergsteiger();
		spielKontroller.doSpielzugStarten();
		spielKontroller.doWerfen();
		assertEquals("GEWORFEN State erwartet", SpielState.GEWORFEN, spielKontroller.getSpielStatus());
		List<Wurfel> wurfels = spielKontroller.getWurfelManager().getWurfels();
		for (Wurfel wurfel : wurfels) {
			assertTrue("Wurfel muss geworfen sein", wurfel.isGeworfen());
		}
		assertNotNull(spielKontroller.getFalschePaarungen());
	}
	
	@Test(expected=InvalidSpielStateException.class)
	public void testDoWerfenInvalidState() throws WurfelNichtGeworfenException, InvalidWegNummerException, KeinMarkierungSteinIstVorhandenException, SeilPunktInvalidUsageException, KeinBergsteigerAufDemWegException, InvalidBergsteigerBewegungException {
		SpielKontroller spielKontroller = (SpielKontroller)ac.getBean("testSpielKontroller");
		spielKontroller.verteilBergsteiger();
		spielKontroller.doWerfen();
	}
	
	@Test
	public void testDoWerfenNegativ() throws WurfelNichtGeworfenException, InvalidWegNummerException, KeinMarkierungSteinIstVorhandenException, SeilPunktInvalidUsageException, KeinBergsteigerAufDemWegException, InvalidBergsteigerBewegungException, InvalidTestParametersException, NullBergsteigerException, KeinBergSteigerIstVorhandenException {
		SpielKontroller spielKontroller = (SpielKontroller)ac.getBean("testSpielKontroller");
		spielKontroller.doSpielStarten();
		spielKontroller.doSpielzugStarten();
		// Faelschen eine Stellung
		//stell Bergsteiger W2BS, W3BS
		spielKontroller.testDoWerfen(new HackedWurfelManager(WurfelTestHelper.generateXeingestellteNormalWurfel(Arrays.asList(1,1,1,2))));
		spielKontroller.doPaarung(spielKontroller.getMoglichePaarungen().get(0), -1);
		//stell Bergsteiger W4BS
		spielKontroller.testDoWerfen(new HackedWurfelManager(WurfelTestHelper.generateXeingestellteNormalWurfel(Arrays.asList(2,2,2,3))));
		spielKontroller.doPaarung(spielKontroller.getMoglichePaarungen().get(0), 4);
		//falsche Paarung -> Spielzug endet 
		spielKontroller.testDoWerfen(new HackedWurfelManager(WurfelTestHelper.generateXeingestellteNormalWurfel(Arrays.asList(6,6,6,6))));
		
		assertNotNull(spielKontroller.getFalschePaarungen());
		assertEquals("IN_ZUG State erwartet", SpielState.IN_ZUG, spielKontroller.getSpielStatus());
		assertNotNull("errorMessage muss sein", spielKontroller.getErrorMessage());
	}

	@Test
	public void testDoSpielZugBeendenWegenKeinAndereWahl() throws NichtWurfelGegebenException, WurfelNichtGeworfenException, InvalidWegNummerException, KeinMarkierungSteinIstVorhandenException, SeilPunktInvalidUsageException, KeinBergsteigerAufDemWegException, InvalidBergsteigerBewegungException, InvalidTestParametersException, KeinBergSteigerIstVorhandenException, NullBergsteigerException {
		SpielKontroller spielKontroller = (SpielKontroller)ac.getBean("testSpielKontroller");
		spielKontroller.doSpielStarten();
		spielKontroller.doSpielzugStarten();
		Spieler alterSpieler = spielKontroller.getActuelleSpieler();
		// Faelschen eine Stellung
		//stell Bergsteiger W2BS, W3BS
		spielKontroller.testDoWerfen(new HackedWurfelManager(WurfelTestHelper.generateXeingestellteNormalWurfel(Arrays.asList(1,1,1,2))));
		spielKontroller.doPaarung(spielKontroller.getMoglichePaarungen().get(0), -1);
		//stell Bergsteiger W4BS
		spielKontroller.testDoWerfen(new HackedWurfelManager(WurfelTestHelper.generateXeingestellteNormalWurfel(Arrays.asList(2,2,2,3))));
		spielKontroller.doPaarung(spielKontroller.getMoglichePaarungen().get(0), 4);
		//falsche Paarung -> Spielzug endet 
		spielKontroller.testDoWerfen(new HackedWurfelManager(WurfelTestHelper.generateXeingestellteNormalWurfel(Arrays.asList(6,6,6,6))));
		
		assertEquals("IN_ZUG State erwartet", SpielState.IN_ZUG, spielKontroller.getSpielStatus());
		assertNotNull("errorMessage muss sein", spielKontroller.getErrorMessage());
		Spieler aktuellerSpieler = spielKontroller.getActuelleSpieler();
		assertEquals("alter Spieler soll keine Bergsteiger besitzen", 0, alterSpieler.getBergSteigers().size());
		assertEquals("alter Spieler soll alle MarkierungSteine besitzen", 10, alterSpieler.getMarkierungSteine().size());
		
		assertEquals("aktueller Spieler soll 3 Bergsteiger besitzen", 3, aktuellerSpieler.getBergSteigers().size());
		assertEquals("aktueller Spieler soll alle MarkierungSteine besitzen", 10, aktuellerSpieler.getMarkierungSteine().size());
	}
	
	@Test
	public void testDoSpielZugBeendenSpielerGemacht() throws NichtWurfelGegebenException, WurfelNichtGeworfenException, InvalidWegNummerException, KeinMarkierungSteinIstVorhandenException, SeilPunktInvalidUsageException, KeinBergsteigerAufDemWegException, InvalidBergsteigerBewegungException, InvalidTestParametersException, KeinBergSteigerIstVorhandenException, NullBergsteigerException {
		SpielKontroller spielKontroller = (SpielKontroller)ac.getBean("testSpielKontroller");
		spielKontroller.doSpielStarten();
		spielKontroller.doSpielzugStarten();
		Spieler alterSpieler = spielKontroller.getActuelleSpieler();
		// Faelschen eine Stellung
		//stell Bergsteiger W2BS, W3BS
		spielKontroller.testDoWerfen(new HackedWurfelManager(WurfelTestHelper.generateXeingestellteNormalWurfel(Arrays.asList(1,1,1,2))));
		spielKontroller.doPaarung(spielKontroller.getMoglichePaarungen().get(0), -1);
		//stell Bergsteiger W4BS
		spielKontroller.testDoWerfen(new HackedWurfelManager(WurfelTestHelper.generateXeingestellteNormalWurfel(Arrays.asList(2,2,2,3))));
		spielKontroller.doPaarung(spielKontroller.getMoglichePaarungen().get(0), 4);
		spielKontroller.doSpielzugBeenden();
		
		assertEquals("IN_ZUG State erwartet", SpielState.IN_ZUG, spielKontroller.getSpielStatus());
		assertNotNull("errorMessage muss sein", spielKontroller.getErrorMessage());
		Spieler aktuellerSpieler = spielKontroller.getActuelleSpieler();
		assertNotEquals("aktueller Spieler soll der naechste sein", alterSpieler, aktuellerSpieler );
		Weg weg2 = spielKontroller.getBrett().getWegByNummer(2);
		assertEquals("weg2 unten soll markiert sein mit "+alterSpieler.getFarbe(), 0, weg2.woIstMarkierungsteinFurFarbe(alterSpieler.getFarbe()));
		Weg weg3 = spielKontroller.getBrett().getWegByNummer(3);
		assertEquals("weg2 unten soll markiert sein mit "+alterSpieler.getFarbe(), 0, weg3.woIstMarkierungsteinFurFarbe(alterSpieler.getFarbe()));
		Weg weg4 = spielKontroller.getBrett().getWegByNummer(4);
		assertEquals("weg2 unten soll markiert sein mit "+alterSpieler.getFarbe(), 0, weg4.woIstMarkierungsteinFurFarbe(alterSpieler.getFarbe()));
		
		assertEquals("alter Spieler soll keine Bergsteiger besitzen", 0, alterSpieler.getBergSteigers().size());
		assertEquals("alter Spieler soll alle MarkierungSteine besitzen", 7, alterSpieler.getMarkierungSteine().size());
		
		assertEquals("aktueller Spieler soll 3 Bergsteiger besitzen", 3, aktuellerSpieler.getBergSteigers().size());
		assertEquals("aktueller Spieler soll alle MarkierungSteine besitzen", 10, aktuellerSpieler.getMarkierungSteine().size());
	}
	
	@Test
	public void testDoSpielZugBeendenSpielGEWONNEN() throws NichtWurfelGegebenException, WurfelNichtGeworfenException, InvalidWegNummerException, KeinMarkierungSteinIstVorhandenException, SeilPunktInvalidUsageException, KeinBergsteigerAufDemWegException, InvalidBergsteigerBewegungException, InvalidTestParametersException, KeinBergSteigerIstVorhandenException, NullBergsteigerException {
		SpielKontroller spielKontroller = (SpielKontroller)ac.getBean("testSpielKontroller");
		spielKontroller.doSpielStarten();
		spielKontroller.doSpielzugStarten();
		Spieler alterSpieler = spielKontroller.getActuelleSpieler();
		// Faelschen eine Stellung
		//stell Bergsteiger W2BS, W3BS
		spielKontroller.testDoWerfen(new HackedWurfelManager(WurfelTestHelper.generateXeingestellteNormalWurfel(Arrays.asList(1,1,1,2))));
		spielKontroller.doPaarung(spielKontroller.getMoglichePaarungen().get(0), -1);
		//stell Bergsteiger W4BS
		spielKontroller.testDoWerfen(new HackedWurfelManager(WurfelTestHelper.generateXeingestellteNormalWurfel(Arrays.asList(2,2,2,3))));
		spielKontroller.doPaarung(spielKontroller.getMoglichePaarungen().get(0), 4);

		// beweg W2BS, W3BS
		spielKontroller.testDoWerfen(new HackedWurfelManager(WurfelTestHelper.generateXeingestellteNormalWurfel(Arrays.asList(1,1,1,2))));
		spielKontroller.doPaarung(spielKontroller.getMoglichePaarungen().get(0), -1);
		// beweg W4BS
		spielKontroller.testDoWerfen(new HackedWurfelManager(WurfelTestHelper.generateXeingestellteNormalWurfel(Arrays.asList(2,2,2,3))));
		spielKontroller.doPaarung(spielKontroller.getMoglichePaarungen().get(0), -1);
		spielKontroller.doSpielzugBeenden();
		
		assertEquals("SPIEL_GEWONNEN State erwartet", SpielState.SPIEL_GEWONNEN, spielKontroller.getSpielStatus());
		assertNotNull("errorMessage muss sein", spielKontroller.getErrorMessage());
		Spieler aktuellerSpieler = spielKontroller.getActuelleSpieler();
		assertEquals("Spieler soll bleiben", alterSpieler, aktuellerSpieler );
		
		Weg weg2 = spielKontroller.getBrett().getWegByNummer(2);
		assertTrue("weg2 Hutte soll markiert sein mit "+alterSpieler.getFarbe(), weg2.isGesperrtVonSpieler(alterSpieler.getFarbe()));
		Weg weg3 = spielKontroller.getBrett().getWegByNummer(3);
		assertTrue("weg3 Hutte soll markiert sein mit "+alterSpieler.getFarbe(), weg3.isGesperrtVonSpieler(alterSpieler.getFarbe()));
		Weg weg4 = spielKontroller.getBrett().getWegByNummer(4);
		assertTrue("weg4 Hutte soll markiert sein mit "+alterSpieler.getFarbe(), weg4.isGesperrtVonSpieler(alterSpieler.getFarbe()));
		
		assertEquals("alter Spieler soll keine Bergsteiger besitzen", 0, alterSpieler.getBergSteigers().size());
		assertEquals("alter Spieler soll alle MarkierungSteine besitzen", 7, alterSpieler.getMarkierungSteine().size());
		assertEquals("SpielKontroller soll alle Bergsteiger besitzen", 3, spielKontroller.getBergSteigers().size());
		
	}

	
	@Test
	public void testDoSpielStarten() {
		SpielKontroller spielKontroller = (SpielKontroller)ac.getBean("testSpielKontroller");
		spielKontroller.doSpielStarten();
		assertEquals("Erster Spieler soll bestimmt sein", SpielKontroller.DEFAULT_ERSTE_SPIELER_NUM, spielKontroller.getActuelleSpielerNummer());
		assertEquals("IN_SPIEL State", SpielState.IN_SPIEL, spielKontroller.getSpielStatus());
	}

	@Test(expected=InvalidSpielStateException.class)
	public void testDoSpielStartenInvalidState() {
		SpielKontroller spielKontroller = (SpielKontroller)ac.getBean("testSpielKontroller");
		spielKontroller.doSpielStarten();
		spielKontroller.doSpielStarten();
	}
	
	@Test
	public void testDoPaarungInputCheckMitValidWaehlbarPaarung() throws NichtWurfelGegebenException, WurfelNichtGeworfenException, InvalidWegNummerException, KeinMarkierungSteinIstVorhandenException, SeilPunktInvalidUsageException, KeinBergsteigerAufDemWegException, InvalidBergsteigerBewegungException, InvalidTestParametersException, KeinBergSteigerIstVorhandenException, NullBergsteigerException {
		SpielKontroller spielKontroller = (SpielKontroller)ac.getBean("testSpielKontroller");
		spielKontroller.doSpielStarten();
		spielKontroller.doSpielzugStarten();
		// Faelschen eine Stellung
		//stell Bergsteiger W2BS, W3BS
		spielKontroller.testDoWerfen(new HackedWurfelManager(WurfelTestHelper.generateXeingestellteNormalWurfel(Arrays.asList(1,1,1,2))));
		spielKontroller.doPaarung(spielKontroller.getMoglichePaarungen().get(0), -1);
	}
	
	@Test(expected=InvalidWegNummerException.class)
	public void testDoPaarungInputCheckMitInValidWegNummer() throws NichtWurfelGegebenException, WurfelNichtGeworfenException, InvalidWegNummerException, KeinMarkierungSteinIstVorhandenException, SeilPunktInvalidUsageException, KeinBergsteigerAufDemWegException, InvalidBergsteigerBewegungException, InvalidTestParametersException, KeinBergSteigerIstVorhandenException, NullBergsteigerException {
		SpielKontroller spielKontroller = (SpielKontroller)ac.getBean("testSpielKontroller");
		spielKontroller.doSpielStarten();
		spielKontroller.doSpielzugStarten();
		// Faelschen eine Stellung
		//stell Bergsteiger W2BS, W3BS
		spielKontroller.testDoWerfen(new HackedWurfelManager(WurfelTestHelper.generateXeingestellteNormalWurfel(Arrays.asList(1,1,1,2))));
		spielKontroller.doPaarung(spielKontroller.getMoglichePaarungen().get(0), -1);
		spielKontroller.testDoWerfen(new HackedWurfelManager(WurfelTestHelper.generateXeingestellteNormalWurfel(Arrays.asList(2,2,2,3))));
		spielKontroller.doPaarung(spielKontroller.getMoglichePaarungen().get(0), -1);
		
		spielKontroller.testDoWerfen(new HackedWurfelManager(WurfelTestHelper.generateXeingestellteNormalWurfel(Arrays.asList(6,6,6,4))));
		spielKontroller.doPaarung(spielKontroller.getMoglichePaarungen().get(0), 11);
	}

	@Test(expected=InvalidPaarungException.class)
	public void testDoPaarungInputCheckMitInValidPaarung() throws NichtWurfelGegebenException, WurfelNichtGeworfenException, InvalidWegNummerException, KeinMarkierungSteinIstVorhandenException, SeilPunktInvalidUsageException, KeinBergsteigerAufDemWegException, InvalidBergsteigerBewegungException, InvalidTestParametersException, KeinBergSteigerIstVorhandenException, NullBergsteigerException {
		SpielKontroller spielKontroller = (SpielKontroller)ac.getBean("testSpielKontroller");
		spielKontroller.doSpielStarten();
		spielKontroller.doSpielzugStarten();
		// Faelschen eine Stellung
		//stell Bergsteiger W2BS, W3BS
		spielKontroller.testDoWerfen(new HackedWurfelManager(WurfelTestHelper.generateXeingestellteNormalWurfel(Arrays.asList(1,1,1,2))));
		List<Wurfel> wurfels = WurfelTestHelper.generateXeingestellteNormalWurfel(Arrays.asList(1,2,3,4));
		ZweiWurfelPaar basisZweier = new ZweiWurfelPaar(
				new WurfelPaar(wurfels.get(0), wurfels.get(1)),
				new WurfelPaar(wurfels.get(2), wurfels.get(3)));
		spielKontroller.doPaarung(basisZweier, -1);
	}
	
	@Test()
	public void testDoPaarungInputCheckMitValidAberNeuPaarung() throws NichtWurfelGegebenException, WurfelNichtGeworfenException, InvalidWegNummerException, KeinMarkierungSteinIstVorhandenException, SeilPunktInvalidUsageException, KeinBergsteigerAufDemWegException, InvalidBergsteigerBewegungException, InvalidTestParametersException, KeinBergSteigerIstVorhandenException, NullBergsteigerException {
		SpielKontroller spielKontroller = (SpielKontroller)ac.getBean("testSpielKontroller");
		spielKontroller.doSpielStarten();
		spielKontroller.doSpielzugStarten();
		// Faelschen eine Stellung
		//stell Bergsteiger W2BS, W3BS
		spielKontroller.testDoWerfen(new HackedWurfelManager(WurfelTestHelper.generateXeingestellteNormalWurfel(Arrays.asList(1,1,1,2))));
		List<Wurfel> wurfels = WurfelTestHelper.generateXeingestellteNormalWurfel(Arrays.asList(1,2,1,1));
		ZweiWurfelPaar basisZweier = new ZweiWurfelPaar(
				new WurfelPaar(wurfels.get(0), wurfels.get(1)),
				new WurfelPaar(wurfels.get(2), wurfels.get(3)));
		spielKontroller.doPaarung(basisZweier, -1);
	}
	
	@Test
	public void testDoGetTransferObject() {
		fail("Not yet implemented");
	}

}
