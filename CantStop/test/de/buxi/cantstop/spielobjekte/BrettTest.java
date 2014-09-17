/**
 * 
 */
package de.buxi.cantstop.spielobjekte;


import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author buxi
 *
 */
public class BrettTest extends SpringLoaderSuperClass{

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		
	}

	/**
	 * Test method for {@link de.buxi.cantstop.spielobjekte.Brett#getWegByNummer(int)}.
	 * @throws InvalidWegNummerException 
	 */
	@Test
	public void testGetWegByNummer() throws InvalidWegNummerException {
		Weg weg = mock(Weg.class);
		List<Weg> wege = new ArrayList<Weg>();
		wege.add(weg);
		Brett brett = new Brett(wege);
		Weg resultWeg = brett.getWegByNummer(2);
		assertEquals("Weg 2 soll da sein", weg, resultWeg);
	}

	/**
	 * Test method for {@link de.buxi.cantstop.spielobjekte.Brett#getWegByNummer(int)}.
	 * @throws InvalidWegNummerException 
	 */
	@Test(expected=InvalidWegNummerException.class)
	public void testGetWegByNummerNegativ1() throws InvalidWegNummerException {
		Brett brett = new Brett(null);
		brett.getWegByNummer(1);
	}
	
	/**
	 * Test method for {@link de.buxi.cantstop.spielobjekte.Brett#getWegByNummer(int)}.
	 * @throws InvalidWegNummerException 
	 */
	@Test(expected=InvalidWegNummerException.class)
	public void testGetWegByNummerNegativ2() throws InvalidWegNummerException {
		List<Weg> wege = new ArrayList<Weg>();
		Brett brett = new Brett(wege);
		brett.getWegByNummer(1);
	}
	
	/**
	 * Test method for {@link de.buxi.cantstop.spielobjekte.Brett#markierenBergsteigers(de.buxi.cantstop.spielobjekte.Spieler)}.
	 * @throws NullBergsteigerException 
	 * @throws SeilPunktInvalidUsageException 
	 * @throws InvalidWegNummerException 
	 * @throws KeinBergsteigerAufDemWegException 
	 * @throws KeinMarkierungSteinIstVorhandenException 
	 */
	@Test
	public void testMarkierenBergsteigersAlleUnten() throws InvalidWegNummerException, SeilPunktInvalidUsageException, NullBergsteigerException, KeinMarkierungSteinIstVorhandenException, KeinBergsteigerAufDemWegException {
		Brett brett = (Brett)ac.getBean("kleinBrett");
		Collection<Integer> wegNummerList = new ArrayList<Integer>(Arrays.asList(2,3,4));
		Collection<BergSteiger> gestellteBergSteigers = BrettTestHelper.stellBergsteigerAufDemWegeAlleUnter(brett, wegNummerList);
		Spieler spieler = new Spieler(1, "1", Farbe.BLAU);
		spieler.addMarkierungSteine(SpielFactory.createMarkierungSteineStatic(3, Farbe.BLAU));
		Collection<BergSteiger> freieBergsteigers = brett.markierenBergsteigers(spieler);
		for (BergSteiger freierBergSteiger : freieBergsteigers) {
			assertTrue("Bergsteiger nicht gefunden", gestellteBergSteigers.remove(freierBergSteiger));
		}
		assertTrue("Alle gestellte Bergsteiger nicht gefunden", gestellteBergSteigers.isEmpty());
		
		for (int wegNummer : wegNummerList) {
			Weg weg = brett.getWegByNummer(wegNummer);
			assertTrue("Weg ist nicht markiert", weg.getUntersteSeilPunkt().isMarkierungsteinFurFarbe(Farbe.BLAU));
		}
	}

	/**
	 * Test method for {@link de.buxi.cantstop.spielobjekte.Brett#markierenBergsteigers(de.buxi.cantstop.spielobjekte.Spieler)}.
	 * @throws NullBergsteigerException 
	 * @throws SeilPunktInvalidUsageException 
	 * @throws InvalidWegNummerException 
	 * @throws KeinBergsteigerAufDemWegException 
	 * @throws KeinMarkierungSteinIstVorhandenException 
	 */
	@Test
	public void testMarkierenBergsteigersAlleInDerHutte() throws InvalidWegNummerException, SeilPunktInvalidUsageException, NullBergsteigerException, KeinMarkierungSteinIstVorhandenException, KeinBergsteigerAufDemWegException {
		Brett brett = (Brett)ac.getBean("kleinBrett");
		Collection<Integer> wegNummerList = new ArrayList<Integer>(Arrays.asList(2,3,4));
		Collection<BergSteiger> gestellteBergSteigers = BrettTestHelper.stellBergsteigerAufDemWegeAlleInHutte(brett, wegNummerList);
		Spieler spieler = new Spieler(1, "1", Farbe.BLAU);
		spieler.addMarkierungSteine(SpielFactory.createMarkierungSteineStatic(3, Farbe.BLAU));
		Collection<BergSteiger> freieBergsteigers = brett.markierenBergsteigers(spieler);
		for (BergSteiger freierBergSteiger : freieBergsteigers) {
			assertTrue("Bergsteiger nicht gefunden", gestellteBergSteigers.remove(freierBergSteiger));
		}
		assertTrue("Alle gestellte Bergsteiger nicht gefunden", gestellteBergSteigers.isEmpty());
		
		for (int wegNummer : wegNummerList) {
			Weg weg = brett.getWegByNummer(wegNummer);
			assertTrue("Hutte ist nicht markiert", weg.getHutte().isMarkiert(Farbe.BLAU));
		}
	}

	/**
	 * Test method for {@link de.buxi.cantstop.spielobjekte.Brett#entnimmBergsteigers()}.
	 * @throws NullBergsteigerException 
	 * @throws SeilPunktInvalidUsageException 
	 * @throws InvalidWegNummerException 
	 * @throws InvalidBergsteigerBewegungException 
	 * @throws KeinMarkierungSteinIstVorhandenException 
	 */
	@Test
	public void testEntnimmBergsteigersVonHutten() throws InvalidWegNummerException, SeilPunktInvalidUsageException, NullBergsteigerException, KeinMarkierungSteinIstVorhandenException, InvalidBergsteigerBewegungException {
		//testen mit kleinem Brett
		Brett brett = (Brett)ac.getBean("kleinBrett");
		Collection<Integer> wegNummerList = new ArrayList<Integer>(Arrays.asList(2,3,4));
		Collection<BergSteiger> gestellteBergSteigers = BrettTestHelper.stellBergsteigerAufDemWegeAlleInHutte(brett, wegNummerList);
		
		Collection<BergSteiger> freieBergsteigers = brett.entnimmBergsteigers();
		for (BergSteiger freierBergSteiger : freieBergsteigers) {
			assertTrue("Bergsteiger nicht gefunden", gestellteBergSteigers.remove(freierBergSteiger));
		}
		assertTrue("Alle gestellte Bergsteiger nicht gefunden", gestellteBergSteigers.isEmpty());
		
		for (int wegNummer : wegNummerList) {
			Weg weg = brett.getWegByNummer(wegNummer);
			assertFalse("Kein Bergsteiger soll in der Hutte sein", weg.isBergsteigerInHutte());
		}
	}

	@Test
	public void testEntnimmBergsteigersVomSeil() throws InvalidWegNummerException, SeilPunktInvalidUsageException, NullBergsteigerException, KeinMarkierungSteinIstVorhandenException, InvalidBergsteigerBewegungException {
		//testen mit kleinem Brett
		Brett brett = (Brett)ac.getBean("kleinBrett");
		Collection<Integer> wegNummerList = new ArrayList<Integer>(Arrays.asList(2,3,4));
		Collection<BergSteiger> gestellteBergSteigers = BrettTestHelper.stellBergsteigerAufDemWegeAlleNebenHutte(brett, wegNummerList);
		
		Collection<BergSteiger> freieBergsteigers = brett.entnimmBergsteigers();
		for (BergSteiger freierBergSteiger : freieBergsteigers) {
			assertTrue("Bergsteiger nicht gefunden", gestellteBergSteigers.remove(freierBergSteiger));
		}
		assertTrue("Alle gestellte Bergsteiger nicht gefunden", gestellteBergSteigers.isEmpty());
		
		for (int wegNummer : wegNummerList) {
			Weg weg = brett.getWegByNummer(wegNummer);
			assertFalse("Kein Bergsteiger soll auf dem Seil sein", weg.isBergsteigerAufSeil());
		}
	}
	
	/**
	 * Test method for {@link de.buxi.cantstop.spielobjekte.Brett#getBesetzteHuttenZahl(de.buxi.cantstop.spielobjekte.Spieler)}.
	 * @throws InvalidWegNummerException 
	 * @throws NullBergsteigerException 
	 * @throws SeilPunktInvalidUsageException 
	 */
	@Test
	public void testGetBesetzteHuttenDreiHutten() throws InvalidWegNummerException, SeilPunktInvalidUsageException, NullBergsteigerException {
		//testen mit kleinem Brett
		Brett brett = (Brett)ac.getBean("kleinBrett");
		Collection<MarkierungStein> markers = BrettTestHelper.markierenHutten(brett, new ArrayList<Integer>(Arrays.asList(2,3,4)), Farbe.BLAU);
		Collection<Hutte> hutten = brett.getBesetzteHutten(new Spieler(1, "1", Farbe.BLAU));
		assertEquals("Drei Hutten sollen besetzt werden mit BLAUen Steine", 3, hutten.size());
		for (Hutte hutte : hutten) {
			assertTrue("MarkierungStein nicht gefunden", markers.remove(hutte.getMarkierungStein()));
		}
		assertTrue("Alle Markierungsteine nicht gefunden", markers.isEmpty());
	}
	
	/**
	 * Test method for {@link de.buxi.cantstop.spielobjekte.Brett#getBesetzteHuttenZahl(de.buxi.cantstop.spielobjekte.Spieler)}.
	 * @throws InvalidWegNummerException 
	 * @throws NullBergsteigerException 
	 * @throws SeilPunktInvalidUsageException 
	 */
	@Test
	public void testGetBesetzteHuttenKeinBesetzt() throws InvalidWegNummerException, SeilPunktInvalidUsageException, NullBergsteigerException {
		//testen mit kleinem Brett
		Brett brett = (Brett)ac.getBean("kleinBrett");
		Collection<Hutte> hutten = brett.getBesetzteHutten(new Spieler(1, "1", Farbe.BLAU));
		assertTrue("Keine Hutte soll besetzt werden ", hutten.isEmpty());
	}
	/**
	 * @throws KeinBergsteigerAufDemWegException 
	 * @throws SeilPunktInvalidUsageException 
	 * @throws KeinMarkierungSteinIstVorhandenException 
	 * @throws NullBergsteigerException 
	 * @throws InvalidWegNummerException 
	 * 
	 */
	@Test
	public void testEntnimmMarkierungSteineVonBesetztenWege() throws KeinMarkierungSteinIstVorhandenException, SeilPunktInvalidUsageException, KeinBergsteigerAufDemWegException, InvalidWegNummerException, NullBergsteigerException {
		Brett brett = (Brett)ac.getBean("kleinBrett");
		Collection<Integer> wegNummerList = new ArrayList<Integer>(Arrays.asList(2,3,4));
		
		// Markieren die Wege neben der Hutte mit BLAU
		BrettTestHelper.stellBergsteigerAufDemWegeAlleNebenHutte(brett, wegNummerList);
		Spieler spielerBLAU = new Spieler(1, "1", Farbe.BLAU);
		spielerBLAU.addMarkierungSteine(SpielFactory.createMarkierungSteineStatic(3, Farbe.BLAU));
		brett.markierenBergsteigers(spielerBLAU);
		
		// Markieren die Wege unten mit ROT
		BrettTestHelper.stellBergsteigerAufDemWegeAlleUnter(brett, wegNummerList);
		Spieler spielerROT = new Spieler(2, "2", Farbe.ROT);
		spielerROT.addMarkierungSteine(SpielFactory.createMarkierungSteineStatic(3, Farbe.ROT));
		brett.markierenBergsteigers(spielerROT);
		
		// Markieren die Hutten mit GELB
		BrettTestHelper.stellBergsteigerAufDemWegeAlleInHutte(brett, wegNummerList);
		Spieler spielerGELB = new Spieler(3, "3", Farbe.GELB);
		spielerGELB.addMarkierungSteine(SpielFactory.createMarkierungSteineStatic(3, Farbe.GELB));
		brett.markierenBergsteigers(spielerGELB);

		// alle ROTe und BLAUe Markierungsteine sollen enfernt werden
		Collection<Hutte> besetzteHutten = brett.getBesetzteHutten(spielerGELB);
		Map<Farbe, Collection<MarkierungStein>> entferteMarkierungSteine = brett.entnimmMarkierungSteineVonBesetztenWege(besetzteHutten);
		assertEquals("3 ROTe Markierungsteine sollen entfert geworden ", 3, entferteMarkierungSteine.get(Farbe.ROT).size());
		assertEquals("3 BLAUe Markierungsteine sollen entfert geworden ", 3, entferteMarkierungSteine.get(Farbe.BLAU).size());
		
		assertFalse("Schwartz spielt nicht", entferteMarkierungSteine.containsKey(Farbe.SCHWARTZ));
	}
}
