package de.buxi.cantstop.spielobjekte;

import static org.junit.Assert.*;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class SpielKontrollerSetupTest extends SpringLoaderSuperClass{

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	/**
	 * Test Spielvorbereitung, Bett, Bergsteiger, Spieler, Markierungsteine, usw.
	 */
	@Test
	public void testSpielKontroller() {
		SpielKontroller spielKontroller = (SpielKontroller)ac.getBean("spielKontroller");
	
		List<Spieler> spielers = spielKontroller.getSpielerInReihe();
		assertNotNull("Spieler ist null", spielers);
		assertTrue("Spieleranzahl muss zwischen 2 und 4 sein", spielers.size()>=2 && spielers.size()<=4);
		//test bestimmSpielerReiheStandard()
		for (Spieler spieler : spielers) {
			int spielerReihe = spieler.getReihe();
			assertEquals("Spieler mit "+spielerReihe+" soll in richtiger Position in der SpielerReihe sein", spieler, spielKontroller.getSpielerInReihe().get(spielerReihe));
		}
		
		//test bestimmErsterSpieler()
		assertEquals("aktueller SpielerNummber muss 0 sein", 0, spielKontroller.getActuelleSpielerNummer());
		
		// ROT und BLAU Steine sind verteilt
		assertEquals("10 Stein bei Spieler ", 10, spielers.get(0).getMarkierungSteine().size());
		assertEquals("10 Stein bei Spieler ", 10, spielers.get(1).getMarkierungSteine().size());
		
		Brett brett = spielKontroller.getBrett();
		assertNotNull("Brett ist null", brett);

		List<Weg> wege = brett.getWege();
		assertEquals("Wege 2, Hutte + 2 SeilPunkt", 2, wege.get(0).getNummer());
		assertEquals("Wege 2, Hutte + 2 SeilPunkt", 2, wege.get(0).getSeilPunkte().size());
		assertEquals("Wege 3, Hutte + 4 SeilPunkt", 3, wege.get(1).getNummer());
		assertEquals("Wege 3, Hutte + 4 SeilPunkt", 4, wege.get(1).getSeilPunkte().size());
		assertEquals("Wege 4, Hutte + 6 SeilPunkt", 4, wege.get(2).getNummer());
		assertEquals("Wege 4, Hutte + 6 SeilPunkt", 6, wege.get(2).getSeilPunkte().size());
		assertEquals("Wege 5, Hutte + 8 SeilPunkt", 5, wege.get(3).getNummer());
		assertEquals("Wege 5, Hutte + 8 SeilPunkt", 8, wege.get(3).getSeilPunkte().size());
		assertEquals("Wege 6, Hutte + 10 SeilPunkt", 6, wege.get(4).getNummer());
		assertEquals("Wege 6, Hutte + 10 SeilPunkt", 10, wege.get(4).getSeilPunkte().size());
		assertEquals("Wege 7, Hutte + 12 SeilPunkt", 7, wege.get(5).getNummer());
		assertEquals("Wege 7, Hutte + 12 SeilPunkt", 12, wege.get(5).getSeilPunkte().size());
		assertEquals("Wege 8, Hutte + 10 SeilPunkt", 8, wege.get(6).getNummer());
		assertEquals("Wege 8, Hutte + 10 SeilPunkt", 10, wege.get(6).getSeilPunkte().size());
		assertEquals("Wege 9, Hutte + 8 SeilPunkt", 9, wege.get(7).getNummer());
		assertEquals("Wege 9, Hutte + 8 SeilPunkt", 8, wege.get(7).getSeilPunkte().size());
		assertEquals("Wege 10, Hutte + 6 SeilPunkt", 10, wege.get(8).getNummer());
		assertEquals("Wege 10, Hutte + 6 SeilPunkt", 6, wege.get(8).getSeilPunkte().size());
		assertEquals("Wege 11, Hutte + 4 SeilPunkt", 11, wege.get(9).getNummer());
		assertEquals("Wege 11, Hutte + 4 SeilPunkt", 4, wege.get(9).getSeilPunkte().size());
		assertEquals("Wege 12, Hutte + 2 SeilPunkt", 12, wege.get(10).getNummer());
		assertEquals("Wege 12, Hutte + 2 SeilPunkt", 2, wege.get(10).getSeilPunkte().size());
		
		
		WurfelManager wurfelManager = spielKontroller.getWurfelManager();
		assertNotNull("WurfelManager ist null", wurfelManager);
		List<Wurfel>wurfels = wurfelManager.getWurfels();
		assertEquals("4 Wurfel fahlt", 4, wurfels.size());
		for (Wurfel wurfel : wurfels) {
			assertEquals(6,  wurfel.getSeite());
		}
		
		Map<Farbe, Collection<MarkierungStein>> markierungSteineCollection = spielKontroller.getAlleMarkierungSteine();
		assertNotNull("MarkierungStein ist null", markierungSteineCollection);
		assertNull("10 BLAU Stein faehlt", markierungSteineCollection.get(Farbe.BLAU));
		assertEquals("10 SCHWARTZ Stein faehlt", 10, markierungSteineCollection.get(Farbe.SCHWARTZ).size());
		assertEquals("10 GELB Stein faehlt", 10, markierungSteineCollection.get(Farbe.GELB).size());
		assertNull("10 ROT Stein faehlt", markierungSteineCollection.get(Farbe.ROT));
		
		List<BergSteiger> bergSteigers = spielKontroller.getBergSteigers();
		assertNotNull("BergSteiger ist null", bergSteigers);
		assertEquals("3 Bergsteiger faehlt", 3, bergSteigers.size());
		assertEquals("INIT Status", SpielState.INIT, spielKontroller.getSpielStatus());
	}
}
