/**
 * 
 */
package de.buxi.cantstop.spielobjekte;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * @author buxi
 * Damit werden Stellungen mit Wurfen getestet (addWaehlbarInformation()
 * Test methods for {@link de.buxi.cantstop.spielobjekte.SpielKontroller#addWaehlbarInformation}.
 *  
 *  HxM  : Hutte x markiert
 *  HxBS : Bergsteiger in der Hutte x
 *  WxM  : Weg x markiert
 *  WxBS : Bergsteiger auf dem Weg x
 */
public class SpielKontrollerPaarungDekoratorTest extends SpringLoaderSuperClass{

	
	private class WurfelWertePaarWahlbarContainter {
		protected List<Integer> werte;
		protected PaarWahlInfo erwarteteErgebnis;
		/**
		 * @param werte
		 * @param erwarteteErgebnis
		 */
		public WurfelWertePaarWahlbarContainter(List<Integer> werte,
				PaarWahlInfo erwarteteErgebnis) {
			super();
			this.werte = werte;
			this.erwarteteErgebnis = erwarteteErgebnis;
		}
		
	}
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testNICHTWAEHLBAR_H2M_H3M_H4M_1_1_1_2() throws InvalidWegNummerException, SeilPunktInvalidUsageException, NullBergsteigerException, InvalidTestParametersException, WurfelNichtGeworfenException {
		SpielKontroller spielKontroller = (SpielKontroller)ac.getBean("testSpielKontroller");
		Brett brett = spielKontroller.getBrett();
		Collection<Integer> wegNummerList = new ArrayList<Integer>(Arrays.asList(2,3,4));
		//3 BS beim Spieler
		spielKontroller.getActuelleSpieler().addBergSteigers(new ArrayList<BergSteiger>(Arrays.asList(new BergSteiger(), new BergSteiger(), new BergSteiger())));
				
		// Markieren die Hutten mit BLAU
		BrettTestHelper.markierenHutten(brett, wegNummerList, Farbe.BLAU);
		List<Wurfel> wurfels = WurfelTestHelper.generateXeingestellteNormalWurfel(Arrays.asList(1,1,1,2));
		WurfelManager wurfelManager = new WurfelManager(wurfels);
		wurfelManager.allWerfen();
		List<ZweiWurfelPaar> paaren = wurfelManager.getMoglichePaarungen();
		List<ZweiWurfelPaar> dekoratedPaaren = spielKontroller.addWaehlbarInformation(paaren);
		assertEquals("Wurf nicht waehlbar", PaarWahlInfo.NICHTWAEHLBAR, dekoratedPaaren.get(0).getPaarungWaehlbar());
	}
	
	@Test
	public void testNICHTWAEHLBAR_W2BS_W3BS_W4BS_2_3_3_3() throws InvalidWegNummerException, SeilPunktInvalidUsageException, NullBergsteigerException, InvalidTestParametersException, WurfelNichtGeworfenException {
		SpielKontroller spielKontroller = (SpielKontroller)ac.getBean("testSpielKontroller");
		Brett brett = spielKontroller.getBrett();
		Collection<Integer> wegNummerList = new ArrayList<Integer>(Arrays.asList(2,3,4));
		
		BrettTestHelper.stellBergsteigerAufDemWegeAlleNebenHutte(brett, wegNummerList);
		List<Wurfel> wurfels = WurfelTestHelper.generateXeingestellteNormalWurfel(Arrays.asList(2,3,3,3));
		WurfelManager wurfelManager = new WurfelManager(wurfels);
		wurfelManager.allWerfen();
		List<ZweiWurfelPaar> paaren = wurfelManager.getMoglichePaarungen();
		List<ZweiWurfelPaar> dekoratedPaaren = spielKontroller.addWaehlbarInformation(paaren);
		assertEquals("Wurf nicht waehlbar", PaarWahlInfo.NICHTWAEHLBAR, dekoratedPaaren.get(0).getPaarungWaehlbar());
	}
	
	@Test
	public void testNICHTWAEHLBAR_H2BS_W3BS_W4BS_1_1_1_2() throws InvalidWegNummerException, SeilPunktInvalidUsageException, NullBergsteigerException, InvalidTestParametersException, WurfelNichtGeworfenException {
		SpielKontroller spielKontroller = (SpielKontroller)ac.getBean("testSpielKontroller");
		Brett brett = spielKontroller.getBrett();
		BrettTestHelper.stellBergsteigerAufDemWegeAlleNebenHutte(brett, new ArrayList<Integer>(Arrays.asList(4,5)));
		BrettTestHelper.markierenHutten(brett, new ArrayList<Integer>(Arrays.asList(2)), Farbe.BLAU);
		
		List<Wurfel> wurfels = WurfelTestHelper.generateXeingestellteNormalWurfel(Arrays.asList(1,1,1,2));
		WurfelManager wurfelManager = new WurfelManager(wurfels);
		wurfelManager.allWerfen();
		List<ZweiWurfelPaar> paaren = wurfelManager.getMoglichePaarungen();
		List<ZweiWurfelPaar> dekoratedPaaren = spielKontroller.addWaehlbarInformation(paaren);
		assertEquals("Wurf nicht waehlbar", PaarWahlInfo.NICHTWAEHLBAR, dekoratedPaaren.get(0).getPaarungWaehlbar());
	}
	
	@Test
	public void testMITWEGINFO_W2BS_W3BS_2_2_2_3() throws InvalidWegNummerException, SeilPunktInvalidUsageException, NullBergsteigerException, InvalidTestParametersException, WurfelNichtGeworfenException {
		SpielKontroller spielKontroller = (SpielKontroller)ac.getBean("testSpielKontroller");
		Brett brett = spielKontroller.getBrett();
		BrettTestHelper.stellBergsteigerAufDemWegeAlleNebenHutte(brett, new ArrayList<Integer>(Arrays.asList(2,3)));
		//1 BS beim Spieler
		spielKontroller.getActuelleSpieler().addBergSteigers(new ArrayList<BergSteiger>(Arrays.asList(new BergSteiger())));
				
		List<Wurfel> wurfels = WurfelTestHelper.generateXeingestellteNormalWurfel(Arrays.asList(2,2,2,3));
		WurfelManager wurfelManager = new WurfelManager(wurfels);
		wurfelManager.allWerfen();
		List<ZweiWurfelPaar> paaren = wurfelManager.getMoglichePaarungen();
		List<ZweiWurfelPaar> dekoratedPaaren = spielKontroller.addWaehlbarInformation(paaren);
		assertEquals( PaarWahlInfo.MITWEGINFO, dekoratedPaaren.get(0).getPaarungWaehlbar());
	}
	
	/**
	 * Specielles Fall, wenn gleiche Wegnummer ergibt sich fur 3. Bergsteiger
	 */
	@Test
	public void testWAEHLBAR_W2BS_W3BS_2_2_1_3() throws InvalidWegNummerException, SeilPunktInvalidUsageException, NullBergsteigerException, InvalidTestParametersException, WurfelNichtGeworfenException { SpielKontroller spielKontroller = (SpielKontroller)ac.getBean("testSpielKontroller");
		Brett brett = spielKontroller.getBrett();
		BrettTestHelper.stellBergsteigerAufDemWegeAlleNebenHutte(brett, new ArrayList<Integer>(Arrays.asList(2,3)));
		//1 BS beim Spieler
		spielKontroller.getActuelleSpieler().addBergSteigers(new ArrayList<BergSteiger>(Arrays.asList(new BergSteiger())));
				
		List<Wurfel> wurfels = WurfelTestHelper.generateXeingestellteNormalWurfel(Arrays.asList(2,2,1,3));
		WurfelManager wurfelManager = new WurfelManager(wurfels);
		wurfelManager.allWerfen();
		List<ZweiWurfelPaar> paaren = wurfelManager.getMoglichePaarungen();
		List<ZweiWurfelPaar> dekoratedPaaren = spielKontroller.addWaehlbarInformation(paaren);
		assertEquals( PaarWahlInfo.WAEHLBAR, dekoratedPaaren.get(0).getPaarungWaehlbar());
	}
	
	@Test
	public void testGibtEsWaehlbarePaarung_W2BS_W3BS_W4BS_2_3_4_5() throws InvalidWegNummerException, SeilPunktInvalidUsageException, NullBergsteigerException, InvalidTestParametersException, WurfelNichtGeworfenException {
		SpielKontroller spielKontroller = (SpielKontroller)ac.getBean("testSpielKontroller");
		Brett brett = spielKontroller.getBrett();
		BrettTestHelper.stellBergsteigerAufDemWegeAlleNebenHutte(brett, new ArrayList<Integer>(Arrays.asList(2,3,4)));
				
		List<Wurfel> wurfels = WurfelTestHelper.generateXeingestellteNormalWurfel(Arrays.asList(2,3,4,5));
		WurfelManager wurfelManager = new WurfelManager(wurfels);
		wurfelManager.allWerfen();
		List<ZweiWurfelPaar> paaren = wurfelManager.getMoglichePaarungen();
		List<ZweiWurfelPaar> dekoratedPaaren = spielKontroller.addWaehlbarInformation(paaren);
		assertFalse("keine waehlbare Paarung", spielKontroller.gibtEsWaehlbarePaarung(dekoratedPaaren));
	}
	
	@Test
	public void testGibtEsWaehlbarePaarung() throws InvalidWegNummerException, SeilPunktInvalidUsageException, NullBergsteigerException, InvalidTestParametersException, WurfelNichtGeworfenException {
		SpielKontroller spielKontroller = (SpielKontroller)ac.getBean("testSpielKontroller");
		Brett brett = spielKontroller.getBrett();
		BrettTestHelper.stellBergsteigerAufDemWegeAlleNebenHutte(brett, new ArrayList<Integer>(Arrays.asList(2,3)));
		//1 BS beim Spieler
		spielKontroller.getActuelleSpieler().addBergSteigers(new ArrayList<BergSteiger>(Arrays.asList(new BergSteiger())));
				
		List<Wurfel> wurfels = WurfelTestHelper.generateXeingestellteNormalWurfel(Arrays.asList(2,2,2,3));
		WurfelManager wurfelManager = new WurfelManager(wurfels);
		wurfelManager.allWerfen();
		List<ZweiWurfelPaar> paaren = wurfelManager.getMoglichePaarungen();
		List<ZweiWurfelPaar> dekoratedPaaren = spielKontroller.addWaehlbarInformation(paaren);
		assertEquals( PaarWahlInfo.MITWEGINFO, dekoratedPaaren.get(0).getPaarungWaehlbar());
		assertTrue(spielKontroller.gibtEsWaehlbarePaarung(dekoratedPaaren));
	}
	
	
	/**
	 * @param spielKontroller
	 * @param testMusterList
	 * @throws InvalidTestParametersException
	 * @throws WurfelNichtGeworfenException
	 * @throws InvalidWegNummerException
	 */
	protected void prufenTestmuster(SpielKontroller spielKontroller,
			List<WurfelWertePaarWahlbarContainter> testMusterList)
			throws InvalidTestParametersException,
			WurfelNichtGeworfenException, InvalidWegNummerException {
		for (WurfelWertePaarWahlbarContainter testMuster : testMusterList) {
			List<Wurfel> wurfels = WurfelTestHelper.generateXeingestellteNormalWurfel(testMuster.werte);
			WurfelManager wurfelManager = new WurfelManager(wurfels);
			wurfelManager.allWerfen();	
			List<ZweiWurfelPaar> paaren = wurfelManager.getMoglichePaarungen();
			List<ZweiWurfelPaar> dekoratedPaaren = spielKontroller.addWaehlbarInformation(paaren);
			assertEquals("Wurf("+testMuster.werte+") nicht "+testMuster.erwarteteErgebnis, testMuster.erwarteteErgebnis, dekoratedPaaren.get(0).getPaarungWaehlbar());
		}
	}
	
	@Test
	public void test_H2M_H3M_H6BS_H7BS_Massentest() throws InvalidWegNummerException, SeilPunktInvalidUsageException, NullBergsteigerException, InvalidTestParametersException, WurfelNichtGeworfenException {
		SpielKontroller spielKontroller = (SpielKontroller)ac.getBean("testSpielKontroller");
		Brett brett = spielKontroller.getBrett();
		BrettTestHelper.markierenHutten(brett, new ArrayList<Integer>(Arrays.asList(2,3)), Farbe.BLAU);
		BrettTestHelper.stellBergsteigerAufDemWegeAlleInHutte(brett, new ArrayList<Integer>(Arrays.asList(6,7)));
		//1 BS beim Spieler
		spielKontroller.getActuelleSpieler().addBergSteigers(new ArrayList<BergSteiger>(Arrays.asList(new BergSteiger())));
		
		List<WurfelWertePaarWahlbarContainter> testMusterList = new ArrayList<SpielKontrollerPaarungDekoratorTest.WurfelWertePaarWahlbarContainter>();
		testMusterList.add(new WurfelWertePaarWahlbarContainter(Arrays.asList(1,1,1,2), PaarWahlInfo.NICHTWAEHLBAR));
		testMusterList.add(new WurfelWertePaarWahlbarContainter(Arrays.asList(1,2,2,2), PaarWahlInfo.WAEHLBAR));
		testMusterList.add(new WurfelWertePaarWahlbarContainter(Arrays.asList(2,2,2,3), PaarWahlInfo.MITWEGINFO));
		testMusterList.add(new WurfelWertePaarWahlbarContainter(Arrays.asList(2,3,3,3), PaarWahlInfo.WAEHLBAR));
		testMusterList.add(new WurfelWertePaarWahlbarContainter(Arrays.asList(3,3,3,4), PaarWahlInfo.NICHTWAEHLBAR));
		testMusterList.add(new WurfelWertePaarWahlbarContainter(Arrays.asList(3,4,4,4), PaarWahlInfo.WAEHLBAR));
		testMusterList.add(new WurfelWertePaarWahlbarContainter(Arrays.asList(4,4,4,5), PaarWahlInfo.MITWEGINFO));
		testMusterList.add(new WurfelWertePaarWahlbarContainter(Arrays.asList(4,5,5,5), PaarWahlInfo.MITWEGINFO));
		testMusterList.add(new WurfelWertePaarWahlbarContainter(Arrays.asList(5,5,5,6), PaarWahlInfo.MITWEGINFO));
		testMusterList.add(new WurfelWertePaarWahlbarContainter(Arrays.asList(5,6,6,6), PaarWahlInfo.MITWEGINFO));
		prufenTestmuster(spielKontroller, testMusterList);
	}
	
	@Test
	public void test_H2M_H3M_H6BS_W7BS_Massentest() throws InvalidWegNummerException, SeilPunktInvalidUsageException, NullBergsteigerException, InvalidTestParametersException, WurfelNichtGeworfenException {
		SpielKontroller spielKontroller = (SpielKontroller)ac.getBean("testSpielKontroller");
		Brett brett = spielKontroller.getBrett();
		BrettTestHelper.markierenHutten(brett, new ArrayList<Integer>(Arrays.asList(2,3)), Farbe.BLAU);
		BrettTestHelper.stellBergsteigerAufDemWegeAlleInHutte(brett, new ArrayList<Integer>(Arrays.asList(6)));
		BrettTestHelper.stellBergsteigerAufDemWegeAlleNebenHutte(brett, new ArrayList<Integer>(Arrays.asList(7)));
		//1 BS beim Spieler
		spielKontroller.getActuelleSpieler().addBergSteigers(new ArrayList<BergSteiger>(Arrays.asList(new BergSteiger())));
				
		List<WurfelWertePaarWahlbarContainter> testMusterList = new ArrayList<SpielKontrollerPaarungDekoratorTest.WurfelWertePaarWahlbarContainter>();
		testMusterList.add(new WurfelWertePaarWahlbarContainter(Arrays.asList(1,1,1,2), PaarWahlInfo.NICHTWAEHLBAR));
		testMusterList.add(new WurfelWertePaarWahlbarContainter(Arrays.asList(1,2,2,2), PaarWahlInfo.WAEHLBAR));
		testMusterList.add(new WurfelWertePaarWahlbarContainter(Arrays.asList(2,2,2,3), PaarWahlInfo.MITWEGINFO));
		testMusterList.add(new WurfelWertePaarWahlbarContainter(Arrays.asList(2,3,3,3), PaarWahlInfo.WAEHLBAR));
		testMusterList.add(new WurfelWertePaarWahlbarContainter(Arrays.asList(3,3,3,4), PaarWahlInfo.WAEHLBAR));
		testMusterList.add(new WurfelWertePaarWahlbarContainter(Arrays.asList(3,4,4,4), PaarWahlInfo.WAEHLBAR));
		testMusterList.add(new WurfelWertePaarWahlbarContainter(Arrays.asList(4,4,4,5), PaarWahlInfo.MITWEGINFO));
		testMusterList.add(new WurfelWertePaarWahlbarContainter(Arrays.asList(4,5,5,5), PaarWahlInfo.MITWEGINFO));
		testMusterList.add(new WurfelWertePaarWahlbarContainter(Arrays.asList(5,5,5,6), PaarWahlInfo.MITWEGINFO));
		testMusterList.add(new WurfelWertePaarWahlbarContainter(Arrays.asList(5,6,6,6), PaarWahlInfo.MITWEGINFO));
		prufenTestmuster(spielKontroller, testMusterList);
	}
	
	@Test
	public void test_LeerBrett_Massentest() throws InvalidWegNummerException, SeilPunktInvalidUsageException, NullBergsteigerException, InvalidTestParametersException, WurfelNichtGeworfenException {
		SpielKontroller spielKontroller = (SpielKontroller)ac.getBean("testSpielKontroller");
		//3 BS beim Spieler
		spielKontroller.getActuelleSpieler().addBergSteigers(new ArrayList<BergSteiger>(Arrays.asList(new BergSteiger(), new BergSteiger(), new BergSteiger())));
				
		List<WurfelWertePaarWahlbarContainter> testMusterList = new ArrayList<SpielKontrollerPaarungDekoratorTest.WurfelWertePaarWahlbarContainter>();
		testMusterList.add(new WurfelWertePaarWahlbarContainter(Arrays.asList(1,1,1,2), PaarWahlInfo.WAEHLBAR));
		testMusterList.add(new WurfelWertePaarWahlbarContainter(Arrays.asList(1,2,2,2), PaarWahlInfo.WAEHLBAR));
		testMusterList.add(new WurfelWertePaarWahlbarContainter(Arrays.asList(2,2,2,3), PaarWahlInfo.WAEHLBAR));
		testMusterList.add(new WurfelWertePaarWahlbarContainter(Arrays.asList(2,3,3,3), PaarWahlInfo.WAEHLBAR));
		testMusterList.add(new WurfelWertePaarWahlbarContainter(Arrays.asList(3,3,3,4), PaarWahlInfo.WAEHLBAR));
		testMusterList.add(new WurfelWertePaarWahlbarContainter(Arrays.asList(3,4,4,4), PaarWahlInfo.WAEHLBAR));
		testMusterList.add(new WurfelWertePaarWahlbarContainter(Arrays.asList(4,4,4,5), PaarWahlInfo.WAEHLBAR));
		testMusterList.add(new WurfelWertePaarWahlbarContainter(Arrays.asList(4,5,5,5), PaarWahlInfo.WAEHLBAR));
		testMusterList.add(new WurfelWertePaarWahlbarContainter(Arrays.asList(5,5,5,6), PaarWahlInfo.WAEHLBAR));
		testMusterList.add(new WurfelWertePaarWahlbarContainter(Arrays.asList(5,6,6,6), PaarWahlInfo.WAEHLBAR));
		prufenTestmuster(spielKontroller, testMusterList);
	}

	@Test
	public void test_EinBS_AufDemBrett_W2BS_Massentest() throws InvalidWegNummerException, SeilPunktInvalidUsageException, NullBergsteigerException, InvalidTestParametersException, WurfelNichtGeworfenException {
		SpielKontroller spielKontroller = (SpielKontroller)ac.getBean("testSpielKontroller");
		Brett brett = spielKontroller.getBrett();
		BrettTestHelper.stellBergsteigerAufDemWegeAlleNebenHutte(brett, new ArrayList<Integer>(Arrays.asList(2)));
		//2 BS beim Spieler
		spielKontroller.getActuelleSpieler().addBergSteigers(new ArrayList<BergSteiger>(Arrays.asList(new BergSteiger(), new BergSteiger())));
				
		List<WurfelWertePaarWahlbarContainter> testMusterList = new ArrayList<SpielKontrollerPaarungDekoratorTest.WurfelWertePaarWahlbarContainter>();
		testMusterList.add(new WurfelWertePaarWahlbarContainter(Arrays.asList(1,1,1,2), PaarWahlInfo.WAEHLBAR));
		testMusterList.add(new WurfelWertePaarWahlbarContainter(Arrays.asList(1,2,2,2), PaarWahlInfo.WAEHLBAR));
		testMusterList.add(new WurfelWertePaarWahlbarContainter(Arrays.asList(2,2,2,3), PaarWahlInfo.WAEHLBAR));
		testMusterList.add(new WurfelWertePaarWahlbarContainter(Arrays.asList(2,3,3,3), PaarWahlInfo.WAEHLBAR));
		testMusterList.add(new WurfelWertePaarWahlbarContainter(Arrays.asList(3,3,3,4), PaarWahlInfo.WAEHLBAR));
		testMusterList.add(new WurfelWertePaarWahlbarContainter(Arrays.asList(3,4,4,4), PaarWahlInfo.WAEHLBAR));
		testMusterList.add(new WurfelWertePaarWahlbarContainter(Arrays.asList(4,4,4,5), PaarWahlInfo.WAEHLBAR));
		testMusterList.add(new WurfelWertePaarWahlbarContainter(Arrays.asList(4,5,5,5), PaarWahlInfo.WAEHLBAR));
		testMusterList.add(new WurfelWertePaarWahlbarContainter(Arrays.asList(5,5,5,6), PaarWahlInfo.WAEHLBAR));
		testMusterList.add(new WurfelWertePaarWahlbarContainter(Arrays.asList(5,6,6,6), PaarWahlInfo.WAEHLBAR));
		prufenTestmuster(spielKontroller, testMusterList);
	}

	@Test
	public void test_2BS_AufDemBrett_W2BS_W3BS_Massentest() throws InvalidWegNummerException, SeilPunktInvalidUsageException, NullBergsteigerException, InvalidTestParametersException, WurfelNichtGeworfenException {
		SpielKontroller spielKontroller = (SpielKontroller)ac.getBean("testSpielKontroller");
		Brett brett = spielKontroller.getBrett();
		BrettTestHelper.stellBergsteigerAufDemWegeAlleNebenHutte(brett, new ArrayList<Integer>(Arrays.asList(2,3)));
		//1 BS beim Spieler
		spielKontroller.getActuelleSpieler().addBergSteigers(new ArrayList<BergSteiger>(Arrays.asList( new BergSteiger())));
				
		List<WurfelWertePaarWahlbarContainter> testMusterList = new ArrayList<SpielKontrollerPaarungDekoratorTest.WurfelWertePaarWahlbarContainter>();
		// Wurf 2 x
		testMusterList.add(new WurfelWertePaarWahlbarContainter(Arrays.asList(1,1,1,2), PaarWahlInfo.WAEHLBAR));
		testMusterList.add(new WurfelWertePaarWahlbarContainter(Arrays.asList(1,1,3,2), PaarWahlInfo.WAEHLBAR));
		testMusterList.add(new WurfelWertePaarWahlbarContainter(Arrays.asList(1,1,4,2), PaarWahlInfo.WAEHLBAR));
		// Wurf 2 3
		testMusterList.add(new WurfelWertePaarWahlbarContainter(Arrays.asList(1,2,2,2), PaarWahlInfo.WAEHLBAR));
		// Wurf 3 x
		testMusterList.add(new WurfelWertePaarWahlbarContainter(Arrays.asList(2,2,2,1), PaarWahlInfo.WAEHLBAR));
		testMusterList.add(new WurfelWertePaarWahlbarContainter(Arrays.asList(1,2,4,2), PaarWahlInfo.WAEHLBAR));
		testMusterList.add(new WurfelWertePaarWahlbarContainter(Arrays.asList(1,2,5,2), PaarWahlInfo.WAEHLBAR));
		//Wurf: andere
		testMusterList.add(new WurfelWertePaarWahlbarContainter(Arrays.asList(2,2,2,3), PaarWahlInfo.MITWEGINFO));
		testMusterList.add(new WurfelWertePaarWahlbarContainter(Arrays.asList(2,3,3,3), PaarWahlInfo.MITWEGINFO));
		testMusterList.add(new WurfelWertePaarWahlbarContainter(Arrays.asList(3,3,3,4), PaarWahlInfo.MITWEGINFO));
		testMusterList.add(new WurfelWertePaarWahlbarContainter(Arrays.asList(3,4,4,4), PaarWahlInfo.MITWEGINFO));
		testMusterList.add(new WurfelWertePaarWahlbarContainter(Arrays.asList(4,4,4,5), PaarWahlInfo.MITWEGINFO));
		testMusterList.add(new WurfelWertePaarWahlbarContainter(Arrays.asList(4,5,5,5), PaarWahlInfo.MITWEGINFO));
		testMusterList.add(new WurfelWertePaarWahlbarContainter(Arrays.asList(5,5,5,6), PaarWahlInfo.MITWEGINFO));
		testMusterList.add(new WurfelWertePaarWahlbarContainter(Arrays.asList(5,6,6,6), PaarWahlInfo.MITWEGINFO));
		prufenTestmuster(spielKontroller, testMusterList);
	}

	@Test
	public void test_3BS_AufDemBrett_W2BS_W3BS_W4BS_Massentest() throws InvalidWegNummerException, SeilPunktInvalidUsageException, NullBergsteigerException, InvalidTestParametersException, WurfelNichtGeworfenException {
		SpielKontroller spielKontroller = (SpielKontroller)ac.getBean("testSpielKontroller");
		Brett brett = spielKontroller.getBrett();
		BrettTestHelper.stellBergsteigerAufDemWegeAlleNebenHutte(brett, new ArrayList<Integer>(Arrays.asList(2,3,4)));
				
		List<WurfelWertePaarWahlbarContainter> testMusterList = new ArrayList<SpielKontrollerPaarungDekoratorTest.WurfelWertePaarWahlbarContainter>();
		// Wurf 2 x
		testMusterList.add(new WurfelWertePaarWahlbarContainter(Arrays.asList(1,1,1,2), PaarWahlInfo.WAEHLBAR));
		testMusterList.add(new WurfelWertePaarWahlbarContainter(Arrays.asList(1,1,3,2), PaarWahlInfo.WAEHLBAR));
		testMusterList.add(new WurfelWertePaarWahlbarContainter(Arrays.asList(1,1,4,2), PaarWahlInfo.WAEHLBAR));
		// Wurf 2 3
		testMusterList.add(new WurfelWertePaarWahlbarContainter(Arrays.asList(1,2,2,2), PaarWahlInfo.WAEHLBAR));
		// Wurf 3 x
		testMusterList.add(new WurfelWertePaarWahlbarContainter(Arrays.asList(2,2,2,1), PaarWahlInfo.WAEHLBAR));
		testMusterList.add(new WurfelWertePaarWahlbarContainter(Arrays.asList(1,2,4,2), PaarWahlInfo.WAEHLBAR));
		testMusterList.add(new WurfelWertePaarWahlbarContainter(Arrays.asList(1,2,5,2), PaarWahlInfo.WAEHLBAR));
		// Wurf 4 x
		testMusterList.add(new WurfelWertePaarWahlbarContainter(Arrays.asList(2,2,6,1), PaarWahlInfo.WAEHLBAR));
		testMusterList.add(new WurfelWertePaarWahlbarContainter(Arrays.asList(2,2,4,6), PaarWahlInfo.WAEHLBAR));
		testMusterList.add(new WurfelWertePaarWahlbarContainter(Arrays.asList(2,2,6,6), PaarWahlInfo.WAEHLBAR));
		testMusterList.add(new WurfelWertePaarWahlbarContainter(Arrays.asList(2,2,2,3), PaarWahlInfo.WAEHLBAR));
				
		//Wurf: andere
		testMusterList.add(new WurfelWertePaarWahlbarContainter(Arrays.asList(2,3,3,3), PaarWahlInfo.NICHTWAEHLBAR));
		testMusterList.add(new WurfelWertePaarWahlbarContainter(Arrays.asList(3,3,3,4), PaarWahlInfo.NICHTWAEHLBAR));
		testMusterList.add(new WurfelWertePaarWahlbarContainter(Arrays.asList(3,4,4,4), PaarWahlInfo.NICHTWAEHLBAR));
		testMusterList.add(new WurfelWertePaarWahlbarContainter(Arrays.asList(4,4,4,5), PaarWahlInfo.NICHTWAEHLBAR));
		testMusterList.add(new WurfelWertePaarWahlbarContainter(Arrays.asList(4,5,5,5), PaarWahlInfo.NICHTWAEHLBAR));
		testMusterList.add(new WurfelWertePaarWahlbarContainter(Arrays.asList(5,5,5,6), PaarWahlInfo.NICHTWAEHLBAR));
		testMusterList.add(new WurfelWertePaarWahlbarContainter(Arrays.asList(5,6,6,6), PaarWahlInfo.NICHTWAEHLBAR));
		prufenTestmuster(spielKontroller, testMusterList);
	}

}
