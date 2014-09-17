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
public class NormalWurfelWertTest  extends SpringLoaderSuperClass{
	// TODO nicht schön, selten kann mit Fehler scheitern
	private int maxWerfen = 100; // Endlosschleife zu meiden 

	private static Wurfel wurfel; 
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		wurfel = (Wurfel) ac.getBean("normalWurfel");
	}

	
	/**
	 * Test method for {@link de.buxi.cantstop.spielobjekte.WurfelImpl#werfen()}.
	 * @throws WurfelNichtGeworfenException 
	 */
	@Test
	public void testWerfenAufEins() throws WurfelNichtGeworfenException {
		assertEquals("Wert 1 muss sein", 1, werfenAufX(1));
	}

	/**
	 * Test method for {@link de.buxi.cantstop.spielobjekte.WurfelImpl#werfen()}.
	 * @throws WurfelNichtGeworfenException 
	 */
	@Test
	public void testWerfenAufZwei() throws WurfelNichtGeworfenException {
		assertEquals("Wert 2 muss sein", 2, werfenAufX(2));
	}
	
	/**
	 * Test method for {@link de.buxi.cantstop.spielobjekte.WurfelImpl#werfen()}.
	 * @throws WurfelNichtGeworfenException 
	 */
	@Test
	public void testWerfenAufDrei() throws WurfelNichtGeworfenException {
		assertEquals("Wert 3 muss sein", 3, werfenAufX(3));
	}
	
	/**
	 * Test method for {@link de.buxi.cantstop.spielobjekte.WurfelImpl#werfen()}.
	 * @throws WurfelNichtGeworfenException 
	 */
	@Test
	public void testWerfenAufVier() throws WurfelNichtGeworfenException {
		assertEquals("Wert 4 muss sein", 4, werfenAufX(4));
	}
	
	/**
	 * Test method for {@link de.buxi.cantstop.spielobjekte.WurfelImpl#werfen()}.
	 * @throws WurfelNichtGeworfenException 
	 */
	@Test
	public void testWerfenAufFunf() throws WurfelNichtGeworfenException {
		assertEquals("Wert 5 muss sein", 5, werfenAufX(5));
	}

	/**
	 * Test method for {@link de.buxi.cantstop.spielobjekte.WurfelImpl#werfen()}.
	 * @throws WurfelNichtGeworfenException 
	 */
	@Test
	public void testWerfenAufSechs() throws WurfelNichtGeworfenException {
		assertEquals("Wert 6 muss sein", 6, werfenAufX(6));
	}

	/**
	 * @param zielWert 
	 * @return
	 * @throws WurfelNichtGeworfenException
	 */
	private int werfenAufX(int zielWert) throws WurfelNichtGeworfenException {
		wurfel.reset();
		int zahl = 0;
		int i = maxWerfen; 
		do {
			wurfel.werfen();
			zahl = wurfel.getWert(); 
			i--;
			
		} while( zahl != zielWert && i > 0);
		return zahl;
	}

}
