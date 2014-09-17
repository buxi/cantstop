package de.buxi.cantstop.spielobjekte;

import static org.junit.Assert.*;

import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

public class SeilPunktTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testSeilPunkt() {
		SeilPunkt seilPunkt = new SeilPunkt();
		assertNull(seilPunkt.getBergsteiger());
		assertNotNull(seilPunkt.getMarkierungSteine());
		assertEquals("soll leer sein", 0, seilPunkt.getMarkierungSteine().size());
	}

	@Test
	public void testMarkierenMitNeueSeilpunkt() throws SeilPunktInvalidUsageException {
		SeilPunkt seilPunkt = new SeilPunkt();
		MarkierungStein marker = new MarkierungStein(Farbe.BLAU);
		seilPunkt.markieren(marker);
		Collection<MarkierungStein> markierungSteine = seilPunkt.getMarkierungSteine();
		assertTrue(markierungSteine.contains(marker));
	}
	
	@Test(expected=SeilPunktInvalidUsageException.class)
	public void testMarkierenMitSchonMarkiertSeilpunkt() throws SeilPunktInvalidUsageException {
		SeilPunkt seilPunkt = new SeilPunkt();
		MarkierungStein marker = new MarkierungStein(Farbe.BLAU);
		seilPunkt.markieren(marker);
		
		seilPunkt.markieren(marker);
	}
	
	@Test()
	public void testMarkierenMitAndereFarbe() throws SeilPunktInvalidUsageException {
		SeilPunkt seilPunkt = new SeilPunkt();
		MarkierungStein marker = new MarkierungStein(Farbe.BLAU);
		seilPunkt.markieren(marker);
		MarkierungStein marker2 = new MarkierungStein(Farbe.GELB);
		seilPunkt.markieren(marker2);
	}
	
	@Test(expected=SeilPunktInvalidUsageException.class)
	public void testUnmarkierenMitNeueSeilpunkt() throws SeilPunktInvalidUsageException {
		SeilPunkt seilPunkt = new SeilPunkt();
		seilPunkt.unmarkieren(null);
	}

	@Test()
	public void testUnMarkierenMitZweiFarben() throws SeilPunktInvalidUsageException {
		SeilPunkt seilPunkt = new SeilPunkt();
		MarkierungStein marker = new MarkierungStein(Farbe.BLAU);
		seilPunkt.markieren(marker);
		MarkierungStein marker2 = new MarkierungStein(Farbe.GELB);
		seilPunkt.markieren(marker2);
		
		assertTrue("mit BLAU ist markiert", seilPunkt.isMarkierungsteinFurFarbe(marker.getFarbe()));
		
		seilPunkt.unmarkieren(marker2.getFarbe());
		assertFalse("mit GELB ist schon nicht markiert", seilPunkt.isMarkierungsteinFurFarbe(marker2.getFarbe()));
	}
	
	@Test
	public void testBergSteigerStellenMitNeuSeilpunkt() throws SeilPunktInvalidUsageException, NullBergsteigerException {
		SeilPunkt seilPunkt = new SeilPunkt();
		BergSteiger bergSteiger = new BergSteiger();
		seilPunkt.bergSteigerStellen(bergSteiger);
		assertEquals(bergSteiger, seilPunkt.getBergsteiger());
	}
	
	@Test(expected=NullBergsteigerException.class)
	public void testBergSteigerStellenNullBergsteiger() throws SeilPunktInvalidUsageException, NullBergsteigerException {
		SeilPunkt seilPunkt = new SeilPunkt();
		seilPunkt.bergSteigerStellen(null);
	}

	@Test(expected=SeilPunktInvalidUsageException.class )
	public void testBergSteigerStellenMitSchonGestellteSeilpunkt() throws SeilPunktInvalidUsageException, NullBergsteigerException {
		SeilPunkt seilPunkt = new SeilPunkt();
		BergSteiger bergSteiger = new BergSteiger();
		seilPunkt.bergSteigerStellen(bergSteiger);
		
		BergSteiger bergSteiger2 = new BergSteiger();
		seilPunkt.bergSteigerStellen(bergSteiger2);
	}

	@Test(expected=SeilPunktInvalidUsageException.class)
	public void testBergSteigerBefreienMitNeuSeilpunkt() throws SeilPunktInvalidUsageException {
		SeilPunkt seilPunkt = new SeilPunkt();
		seilPunkt.bergSteigerBefreien();
	}
	
	@Test()
	public void testBergSteigerBefreienMitBesetzteSeilpunkt() throws SeilPunktInvalidUsageException, NullBergsteigerException {
		SeilPunkt seilPunkt = new SeilPunkt();
		BergSteiger bergSteiger = new BergSteiger();
		seilPunkt.bergSteigerStellen(bergSteiger);
		seilPunkt.bergSteigerBefreien();
		assertEquals(null, seilPunkt.getBergsteiger());
	}
	
	@Test()
	public void testIsMarkierungsteinFurFarbeMitNeuSeilPunkt() throws SeilPunktInvalidUsageException {
		SeilPunkt seilPunkt = new SeilPunkt();
		assertFalse("Neuer Seilpunkt ist nicht markiert",seilPunkt.isMarkierungsteinFurFarbe(Farbe.BLAU));
		
		seilPunkt.markieren(new MarkierungStein(Farbe.BLAU));
		assertTrue("Markiert mit Blau",seilPunkt.isMarkierungsteinFurFarbe(Farbe.BLAU));
		
		assertFalse("Markiert mit Blau",seilPunkt.isMarkierungsteinFurFarbe(Farbe.SCHWARTZ));
	}
}
