/**
 * 
 */
package de.buxi.cantstop.spielobjekte;

import static org.junit.Assert.*;

import org.junit.Test;

/**
 * @author buxi
 *
 */
public class HutteTest {
	@Test
	public void testHutte() {
		Hutte hutte = new Hutte(0);
		assertNull(hutte.getBergsteiger());
		assertNull(hutte.getMarkierungStein());
	}

	@Test
	public void testMarkierenMitNeueHutte() throws SeilPunktInvalidUsageException {
		Hutte hutte = new Hutte(0);
		MarkierungStein marker = new MarkierungStein(Farbe.BLAU);
		hutte.markieren(marker);
		MarkierungStein markierungSteine = hutte.getMarkierungStein();
		assertTrue("mit BLAU markiert", marker.equals(markierungSteine));
	}
	
	@Test(expected=SeilPunktInvalidUsageException.class)
	public void testMarkierenMitSchonMarkiertSeilpunkt() throws SeilPunktInvalidUsageException {
		Hutte hutte = new Hutte(0);
		MarkierungStein marker = new MarkierungStein(Farbe.BLAU);
		hutte.markieren(marker);
		
		hutte.markieren(marker);
	}
	
	@Test(expected=SeilPunktInvalidUsageException.class)
	public void testUnMarkierenMitNeueHutte() throws SeilPunktInvalidUsageException {
		Hutte hutte = new Hutte(0);
		hutte.unmarkieren();
	}

	@Test()
	public void testUnMarkierenMitMarkierteHutte() throws SeilPunktInvalidUsageException {
		Hutte hutte = new Hutte(0);
		MarkierungStein marker = new MarkierungStein(Farbe.BLAU);
		hutte.markieren(marker);
		hutte.unmarkieren();
		assertNull("schon nicht markiert mit BLAU ", hutte.getMarkierungStein());
	}
	
	@Test()
	public void testIsMarkiertMitBlau() throws SeilPunktInvalidUsageException {
		Hutte hutte = new Hutte(0);
		MarkierungStein marker = new MarkierungStein(Farbe.BLAU);
		hutte.markieren(marker);
		assertTrue("schon markiert mit BLAU ", hutte.isMarkiert(Farbe.BLAU));
	}
	
	@Test()
	public void testIsMarkiertMitAndere() throws SeilPunktInvalidUsageException {
		Hutte hutte = new Hutte(0);
		MarkierungStein marker = new MarkierungStein(Farbe.BLAU);
		hutte.markieren(marker);
		assertFalse("schon markiert mit BLAU ", hutte.isMarkiert(Farbe.GELB));
	}

	@Test()
	public void testIsMarkiertNichtMarkiert() throws SeilPunktInvalidUsageException {
		Hutte hutte = new Hutte(0);
		assertFalse("noch nicht markiert", hutte.isMarkiert(Farbe.GELB));
	}
	
}
