/**
 * 
 */
package de.buxi.cantstop.spielobjekte;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

/**
 * @author buxi
 *
 */
public class WegTest extends SpringLoaderSuperClass {

	private SpielFactory spielFactory;
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		spielFactory = (SpielFactory)ac.getBean("spielFactory");
	}

	/**
	 * Test method for {@link de.buxi.cantstop.spielobjekte.Weg#isGesperrt()}.
	 * @throws InvalidWegNummerException 
	 * @throws SeilPunktInvalidUsageException 
	 */
	@Test
	public void testIsGesperrtVonSpielerPositive() throws InvalidWegNummerException, SeilPunktInvalidUsageException {
		Weg weg3 = spielFactory.createWeg(2, 2);
		assertFalse("Weg soll frei sein", weg3.isGesperrtVonSpieler(null));
		assertFalse("Weg soll frei sein", weg3.isGesperrtVonSpieler(Farbe.BLAU));
		
		
		weg3.getHutte().markieren(new MarkierungStein(Farbe.BLAU));
		assertTrue("Weg ist markiert", weg3.isGesperrtVonSpieler(Farbe.BLAU));
		
		assertFalse("Weg ist markiert mit BLAU", weg3.isGesperrtVonSpieler(Farbe.SCHWARTZ));
	}

	/**
	 * Test method for {@link de.buxi.cantstop.spielobjekte.Weg#isGesperrt()}.
	 * @throws InvalidWegNummerException 
	 * @throws SeilPunktInvalidUsageException 
	 */
	@Test
	public void testIsGesperrt() throws InvalidWegNummerException, SeilPunktInvalidUsageException {
		Weg weg3 = spielFactory.createWeg(2, 2);
		
		assertFalse("Weg soll frei sein", weg3.isGesperrt());
		
		weg3.getHutte().markieren(new MarkierungStein(Farbe.BLAU));
		assertTrue("Weg ist markiert", weg3.isGesperrt());
	}
	/**
	 * Test method for {@link de.buxi.cantstop.spielobjekte.Weg#isFrei()}.
	 * @throws InvalidWegNummerException 
	 */
	@Test
	public void testIsFrei() throws InvalidWegNummerException {
		Weg weg3 = spielFactory.createWeg(3, 4);
		assertTrue("Weg soll frei sein", weg3.isFrei());
		
	}

	/**
	 * Test method for {@link de.buxi.cantstop.spielobjekte.Weg#isBergsteigerAufSeil()}.
	 * @throws SeilPunktInvalidUsageException 
	 * @throws NullBergsteigerException 
	 */
	@Test
	public void testIsBergsteigerAufSeil() throws SeilPunktInvalidUsageException, NullBergsteigerException {
		Weg weg2 = spielFactory.createWeg(2, 2);
		assertFalse("kein Bergsteiger auf dem Seil", weg2.isBergsteigerAufSeil());
		weg2.getSeilPunkte().get(0).bergSteigerStellen(new BergSteiger());
		assertTrue("Bergsteiger auf dem Seil Koord 0", weg2.isBergsteigerAufSeil());
	}

	/**
	 * Test method for {@link de.buxi.cantstop.spielobjekte.Weg#woIstBergsteigerAufSeil()}.
	 * @throws SeilPunktInvalidUsageException 
	 */
	@Test
	public void testWoIstBergsteigerAufSeilKeinBergsteiger() throws SeilPunktInvalidUsageException {
		Weg weg2 = spielFactory.createWeg(2, 2);
		assertTrue("Kein Bergsteiger auf dem Seil", weg2.woIstBergsteigerAufSeil() < 0);
	}
	
	/**
	 * Test method for {@link de.buxi.cantstop.spielobjekte.Weg#woIstBergsteigerAufSeil()}.
	 * @throws SeilPunktInvalidUsageException 
	 * @throws NullBergsteigerException 
	 */
	@Test
	public void testWoIstBergsteigerAufSeilKoord0() throws SeilPunktInvalidUsageException, NullBergsteigerException {
		Weg weg2 = spielFactory.createWeg(2, 2);
		weg2.getSeilPunkte().get(0).bergSteigerStellen(new BergSteiger());
		assertEquals("Bergsteiger auf dem Seil Koord 0", 0, weg2.woIstBergsteigerAufSeil());
	}
	
	/**
	 * Test method for {@link de.buxi.cantstop.spielobjekte.Weg#woIstBergsteigerAufSeil()}.
	 * @throws SeilPunktInvalidUsageException 
	 * @throws NullBergsteigerException 
	 */
	@Test
	public void testWoIstBergsteigerAufSeilNebenHutte() throws SeilPunktInvalidUsageException, NullBergsteigerException {
		Weg weg2 = spielFactory.createWeg(2, 2);
		weg2.getObersteSeilPunkt().bergSteigerStellen(new BergSteiger());
		assertEquals("Bergsteiger auf dem Seil Koord 1", 1, weg2.woIstBergsteigerAufSeil());
	}
	
	/**
	 * Test method for {@link de.buxi.cantstop.spielobjekte.Weg#isMarkierungSteinAufSeil(de.buxi.cantstop.spielobjekte.Farbe)}.
	 * @throws SeilPunktInvalidUsageException 
	 */
	@Test
	public void testIsMarkierungSteinAufSeil() throws SeilPunktInvalidUsageException {
		Weg weg2 = spielFactory.createWeg(2, 2);
		assertFalse("kein MarkierungStein auf dem Seil", weg2.isMarkierungSteinAufSeil(Farbe.BLAU));
		weg2.getSeilPunkte().get(0).markieren(new MarkierungStein(Farbe.BLAU));
		assertTrue("MarkierungStein auf dem Seil Koord 0", weg2.isMarkierungSteinAufSeil(Farbe.BLAU));
	}

	/**
	 * Test method for {@link de.buxi.cantstop.spielobjekte.Weg#isMarkierungSteinAufSeil(de.buxi.cantstop.spielobjekte.Farbe)}.
	 * @throws SeilPunktInvalidUsageException 
	 */
	@Test
	public void testIsMarkierungSteinAufSeilNebenHutte() throws SeilPunktInvalidUsageException {
		Weg weg2 = spielFactory.createWeg(2, 2);
		assertFalse("kein MarkierungStein auf dem Seil", weg2.isMarkierungSteinAufSeil(Farbe.BLAU));
		weg2.getObersteSeilPunkt().markieren(new MarkierungStein(Farbe.BLAU));
		assertTrue("MarkierungStein auf dem Seil Koord 1", weg2.isMarkierungSteinAufSeil(Farbe.BLAU));
	}

	/**
	 * Test method for {@link de.buxi.cantstop.spielobjekte.Weg#woIstMarkierungsteinFurFarbe(de.buxi.cantstop.spielobjekte.Farbe)}.
	 * @throws SeilPunktInvalidUsageException 
	 */
	@Test
	public void testWoIstMarkierungsteinFurFarbe() throws SeilPunktInvalidUsageException {
		Weg weg2 = spielFactory.createWeg(2, 2);
		assertFalse("kein MarkierungStein auf dem Seil", weg2.isMarkierungSteinAufSeil(Farbe.BLAU));
		weg2.getSeilPunkte().get(1).markieren(new MarkierungStein(Farbe.BLAU));
		assertEquals("MarkierungStein auf dem Seil Koord 1", 1, weg2.woIstMarkierungsteinFurFarbe(Farbe.BLAU));
	}

	/**
	 * Test method for {@link de.buxi.cantstop.spielobjekte.Weg#getUntersteSeilPunkt()}.
	 */
	@Test
	public void testGetUntersteSeilPunkt() {
		Weg weg2 = spielFactory.createWeg(2, 2);
		assertEquals("Unterste Seilpunkt soll [0] sein", weg2.getSeilPunkte().get(0), weg2.getUntersteSeilPunkt());
	}

	/**
	 * Test method for {@link de.buxi.cantstop.spielobjekte.Weg#getObersteSeilPunkt()}.
	 */
	@Test
	public void testGetObersteSeilPunkt() {
		Weg weg2 = spielFactory.createWeg(2, 2);
		assertEquals("Unterste Seilpunkt soll [1] sein", weg2.getSeilPunkte().get(1), weg2.getObersteSeilPunkt());
	}
	
	/**
	 * Test method for {@link de.buxi.cantstop.spielobjekte.Weg#stellBergsteigerUnten(de.buxi.cantstop.spielobjekte.BergSteiger)}.
	 * @throws SeilPunktInvalidUsageException 
	 * @throws NullBergsteigerException 
	 */
	@Test
	public void testStellBergsteigerUnten() throws SeilPunktInvalidUsageException, NullBergsteigerException {
		Weg weg2 = spielFactory.createWeg(2, 2);
		weg2.stellBergsteigerUnten(new BergSteiger());
		assertEquals("Bergsteiger auf dem Seil Koord 0", 0, weg2.woIstBergsteigerAufSeil());
	}

	/**
	 * Test method for {@link de.buxi.cantstop.spielobjekte.Weg#stellBergsteigerNachMarkierungStein(de.buxi.cantstop.spielobjekte.BergSteiger, de.buxi.cantstop.spielobjekte.Farbe)}.
	 * @throws SeilPunktInvalidUsageException 
	 * @throws NullBergsteigerException 
	 * @throws InvalidBergsteigerBewegungException 
	 */
	@Test(expected=InvalidBergsteigerBewegungException.class)
	public void testStellBergsteigerNachMarkierungSteinNullMarkierungStein() throws SeilPunktInvalidUsageException, NullBergsteigerException, InvalidBergsteigerBewegungException {
		Weg weg2 = spielFactory.createWeg(4, 6);
		weg2.stellBergsteigerNachMarkierungStein(new BergSteiger(), null);
	}

	/**
	 * Test method for {@link de.buxi.cantstop.spielobjekte.Weg#stellBergsteigerNachMarkierungStein(de.buxi.cantstop.spielobjekte.BergSteiger, de.buxi.cantstop.spielobjekte.Farbe)}.
	 * @throws SeilPunktInvalidUsageException 
	 * @throws NullBergsteigerException 
	 * @throws InvalidBergsteigerBewegungException 
	 */
	@Test(expected=NullBergsteigerException.class)
	public void testStellBergsteigerNachMarkierungSteinNullBergsteiger() throws SeilPunktInvalidUsageException, NullBergsteigerException, InvalidBergsteigerBewegungException {
		Weg weg2 = spielFactory.createWeg(4, 6);
		weg2.getSeilPunkte().get(1).markieren(new MarkierungStein(Farbe.BLAU));
		weg2.stellBergsteigerNachMarkierungStein(null, Farbe.BLAU);
	}

	/**
	 * Test method for {@link de.buxi.cantstop.spielobjekte.Weg#stellBergsteigerNachMarkierungStein(de.buxi.cantstop.spielobjekte.BergSteiger, de.buxi.cantstop.spielobjekte.Farbe)}.
	 * @throws SeilPunktInvalidUsageException 
	 * @throws NullBergsteigerException 
	 * @throws InvalidBergsteigerBewegungException 
	 */
	@Test
	public void testStellBergsteigerNachMarkierungSteinInMitte() throws SeilPunktInvalidUsageException, NullBergsteigerException, InvalidBergsteigerBewegungException {
		Weg weg2 = spielFactory.createWeg(4, 6);
		weg2.getSeilPunkte().get(3).markieren(new MarkierungStein(Farbe.BLAU));
		weg2.stellBergsteigerNachMarkierungStein(new BergSteiger(), Farbe.BLAU);
		assertEquals("BergSteiger soll auf dem 4. SeilPunkt", 4, weg2.woIstBergsteigerAufSeil());
	}

	/**
	 * Test method for {@link de.buxi.cantstop.spielobjekte.Weg#stellBergsteigerNachMarkierungStein(de.buxi.cantstop.spielobjekte.BergSteiger, de.buxi.cantstop.spielobjekte.Farbe)}.
	 * @throws SeilPunktInvalidUsageException 
	 * @throws NullBergsteigerException 
	 * @throws InvalidBergsteigerBewegungException 
	 */
	@Test
	public void testStellBergsteigerNachMarkierungSteinNebenHutte() throws SeilPunktInvalidUsageException, NullBergsteigerException, InvalidBergsteigerBewegungException {
		Weg weg2 = spielFactory.createWeg(4, 6);
		weg2.getObersteSeilPunkt().markieren(new MarkierungStein(Farbe.BLAU));
		weg2.stellBergsteigerNachMarkierungStein(new BergSteiger(), Farbe.BLAU);
		assertEquals("BergSteiger soll in der Hutte", -1000, weg2.woIstBergsteigerAufSeil());
		assertTrue("BergSteiger soll in der Hutte2", weg2.isBergsteigerInHutte());
		assertFalse("BergSteiger soll nicht auf dem Seil", weg2.isBergsteigerAufSeil());
	}
	
	/**
	 * Test method for {@link de.buxi.cantstop.spielobjekte.Weg#bewegBergsteiger()}.
	 * @throws SeilPunktInvalidUsageException 
	 * @throws InvalidBergsteigerBewegungException 
	 * @throws NullBergsteigerException 
	 */
	@Test
	public void testBewegBergsteiger() throws SeilPunktInvalidUsageException, InvalidBergsteigerBewegungException, NullBergsteigerException {
		Weg weg2 = spielFactory.createWeg(4, 6);
		weg2.getSeilPunkte().get(4).bergSteigerStellen(new BergSteiger());
		weg2.bewegBergsteiger();
		assertEquals("BergSteiger soll in der Hutte", 5, weg2.woIstBergsteigerAufSeil());
		weg2.bewegBergsteiger();
		assertTrue("BergSteiger soll in der Hutte2", weg2.isBergsteigerInHutte());
		assertFalse("BergSteiger soll nicht auf dem Seil", weg2.isBergsteigerAufSeil());
	}

	/**
	 * Test method for {@link de.buxi.cantstop.spielobjekte.Weg#bewegBergsteiger()}.
	 * @throws SeilPunktInvalidUsageException 
	 * @throws InvalidBergsteigerBewegungException 
	 * @throws NullBergsteigerException 
	 */
	@Test(expected=InvalidBergsteigerBewegungException.class)
	public void testBewegBergsteigerKeinBergsteiger() throws SeilPunktInvalidUsageException, InvalidBergsteigerBewegungException, NullBergsteigerException {
		Weg weg2 = spielFactory.createWeg(4, 6);
		weg2.bewegBergsteiger();
	}

	/**
	 * Test method for {@link de.buxi.cantstop.spielobjekte.Weg#markierenBergsteigerAufDemWeg(de.buxi.cantstop.spielobjekte.MarkierungStein)}.
	 * @throws SeilPunktInvalidUsageException 
	 * @throws KeinBergsteigerAufDemWegException 
	 */
	@Test(expected=KeinBergsteigerAufDemWegException.class)
	public void testMarkierenBergsteigerAufDemWegKeinBergsteigerAufDemWeg() throws SeilPunktInvalidUsageException, KeinBergsteigerAufDemWegException {
		Weg weg2 = spielFactory.createWeg(4, 6);
		assertEquals("Kein Freie Bergsteiger zuruckbekommen", null, weg2.markierenBergsteigerAufDemWegNeuSetzen(new MarkierungStein(Farbe.BLAU)));
	}

	/**
	 * Test method for {@link de.buxi.cantstop.spielobjekte.Weg#markierenBergsteigerAufDemWeg(de.buxi.cantstop.spielobjekte.MarkierungStein)}.
	 * @throws SeilPunktInvalidUsageException 
	 * @throws NullBergsteigerException 
	 * @throws KeinBergsteigerAufDemWegException 
	 */
	@Test
	public void testMarkierenBergsteigerAufDemWegKoord2() throws SeilPunktInvalidUsageException, NullBergsteigerException, KeinBergsteigerAufDemWegException {
		Weg weg2 = spielFactory.createWeg(4, 6);
		BergSteiger bergSteiger = new BergSteiger();
		weg2.getSeilPunkte().get(1).bergSteigerStellen(bergSteiger);
		
		BergSteiger resultBergsteiger = weg2.markierenBergsteigerAufDemWegNeuSetzen(new MarkierungStein(Farbe.BLAU));
		assertEquals("BergSteiger soll gleich sein", bergSteiger, resultBergsteiger);
		assertFalse("kein Bergsteiger soll auf dem Seil sein", weg2.isBergsteigerAufSeil());
		assertFalse("kein Bergsteiger soll in der Hutte sein", weg2.isBergsteigerInHutte());
		assertEquals("MarkierungStein soll Koord2 sein", 1, weg2.woIstMarkierungsteinFurFarbe(Farbe.BLAU));
	}
	
	/**
	 * Test method for {@link de.buxi.cantstop.spielobjekte.Weg#markierenBergsteigerAufDemWeg(de.buxi.cantstop.spielobjekte.MarkierungStein)}.
	 * @throws SeilPunktInvalidUsageException 
	 * @throws NullBergsteigerException 
	 * @throws InvalidBergsteigerBewegungException 
	 * @throws KeinBergsteigerAufDemWegException 
	 */
	@Test
	public void testMarkierenBergsteigerAufDemWegKoord2SchonMarkiert() throws SeilPunktInvalidUsageException, NullBergsteigerException, InvalidBergsteigerBewegungException, KeinBergsteigerAufDemWegException {
		Weg weg2 = spielFactory.createWeg(4, 6);
		BergSteiger bergSteiger = new BergSteiger();
		weg2.getSeilPunkte().get(1).bergSteigerStellen(bergSteiger);
		
		BergSteiger resultBergsteiger = weg2.markierenBergsteigerAufDemWegNeuSetzen(new MarkierungStein(Farbe.BLAU));
		assertEquals("BergSteiger sollen gleich sein", bergSteiger, resultBergsteiger);
		assertFalse("kein Bergsteiger soll auf dem Seil sein", weg2.isBergsteigerAufSeil());
		assertFalse("kein Bergsteiger soll in der Hutte sein", weg2.isBergsteigerInHutte());
		assertEquals("MarkierungStein soll Koord2 sein", 1, weg2.woIstMarkierungsteinFurFarbe(Farbe.BLAU));
		
		//zweite Rund
		weg2.stellBergsteigerNachMarkierungStein(bergSteiger, Farbe.BLAU);
		resultBergsteiger = weg2.markierenBergsteigerAufDemWegBeweg(Farbe.BLAU);
		assertEquals("BergSteiger sollen gleich sein", bergSteiger, resultBergsteiger);
		assertFalse("kein Bergsteiger soll auf dem Seil sein", weg2.isBergsteigerAufSeil());
		assertFalse("kein Bergsteiger soll in der Hutte sein", weg2.isBergsteigerInHutte());
		assertEquals("MarkierungStein soll Koord2 sein", 2, weg2.woIstMarkierungsteinFurFarbe(Farbe.BLAU));
	}

	/**
	 * Test method for {@link de.buxi.cantstop.spielobjekte.Weg#markierenBergsteigerAufDemWeg(de.buxi.cantstop.spielobjekte.MarkierungStein)}.
	 * @throws SeilPunktInvalidUsageException 
	 * @throws NullBergsteigerException 
	 * @throws KeinBergsteigerAufDemWegException 
	 */
	@Test
	public void testMarkierenBergsteigerAufDemWegInDerHutte() throws SeilPunktInvalidUsageException, NullBergsteigerException, KeinBergsteigerAufDemWegException {
		Weg weg2 = spielFactory.createWeg(4, 6);
		BergSteiger bergSteiger = new BergSteiger();
		weg2.getHutte().bergSteigerStellen(bergSteiger);
		
		BergSteiger resultBergsteiger = weg2.markierenBergsteigerAufDemWegNeuSetzen(new MarkierungStein(Farbe.BLAU));
		assertEquals("BergSteiger soll gleich sein", bergSteiger, resultBergsteiger);
		assertFalse("kein Bergsteiger soll auf dem Seil sein", weg2.isBergsteigerAufSeil());
		assertFalse("kein Bergsteiger soll in der Hutte sein", weg2.isBergsteigerInHutte());
		assertTrue("MarkierungStein soll In der Hutte sein", weg2.isGesperrt());
		assertFalse("MarkierungStein soll In der Hutte sein", weg2.isMarkierungSteinAufSeil(Farbe.BLAU));
	}
	
	/**
	 * Test method for {@link de.buxi.cantstop.spielobjekte.Weg#isBergsteigerInHutte()}.
	 * @throws SeilPunktInvalidUsageException 
	 * @throws NullBergsteigerException 
	 */
	@Test
	public void testIsBergsteigerInHutte() throws SeilPunktInvalidUsageException, NullBergsteigerException {
		Weg weg2 = spielFactory.createWeg(2, 2);
		assertFalse("Hutte ist leer", weg2.isBergsteigerInHutte());
		weg2.getHutte().bergSteigerStellen(new BergSteiger());
		assertTrue("Hutte ist besetzt", weg2.isBergsteigerInHutte());
	}
	
	@Test
	public void testEntnimmBergsteigerVonHutte() throws SeilPunktInvalidUsageException, NullBergsteigerException {
		Weg weg2 = spielFactory.createWeg(4, 6);
		BergSteiger bergSteiger = new BergSteiger();
		weg2.getHutte().bergSteigerStellen(bergSteiger);
		
		BergSteiger resultBergsteiger = weg2.entnimmBergsteigerVonHutte();
		assertEquals("BergSteiger soll gleich sein", bergSteiger, resultBergsteiger);
		assertFalse("kein Bergsteiger soll auf dem Seil sein", weg2.isBergsteigerAufSeil());
		assertFalse("kein Bergsteiger soll in der Hutte sein", weg2.isBergsteigerInHutte());
		assertTrue("MarkierungStein soll nicht In der Hutte sein", weg2.isFrei());
	}
	
	@Test(expected=SeilPunktInvalidUsageException.class)
	public void testEntnimmBergsteigerVonHutteLeereHutte() throws SeilPunktInvalidUsageException {
		Weg weg2 = spielFactory.createWeg(4, 6);
		BergSteiger resultBergsteiger = weg2.entnimmBergsteigerVonHutte();
		assertNull("kein BergSteiger ist in der Hutte", resultBergsteiger);
	}

	@Test
	public void testEntnimmBergsteigerVonSeil() throws SeilPunktInvalidUsageException, NullBergsteigerException, InvalidBergsteigerBewegungException {
		Weg weg2 = spielFactory.createWeg(4, 6);
		BergSteiger bergSteiger = new BergSteiger();
		weg2.getUntersteSeilPunkt().bergSteigerStellen(bergSteiger);
		BergSteiger resultBergsteiger = weg2.entnimmBergsteigerVonSeil();
		assertEquals("BergSteiger soll gleich sein", bergSteiger, resultBergsteiger);
	}
	
	@Test(expected=InvalidBergsteigerBewegungException.class)
	public void testEntnimmBergsteigerVonLeereSeil() throws InvalidBergsteigerBewegungException, SeilPunktInvalidUsageException {
		Weg weg2 = spielFactory.createWeg(4, 6);
		BergSteiger resultBergsteiger = weg2.entnimmBergsteigerVonSeil();
		assertNull("kein BergSteiger ist in der Hutte", resultBergsteiger);
	}

	
}
