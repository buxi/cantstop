package de.buxi.cantstop.spielobjekte;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class NormalWurfelTest extends SpringLoaderSuperClass {
	
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testWurfel() {
		Wurfel wurfel = (Wurfel) ac.getBean("normalWurfel");
		assertEquals("neuer NormalWurfel soll nicht geworfen sein", wurfel.isGeworfen(), false);
	}

	@Test(expected=WurfelNichtGeworfenException.class)
	public void testGetZahlOhneWurf() throws WurfelNichtGeworfenException {
		Wurfel wurfel =(Wurfel) ac.getBean("normalWurfel");
		wurfel.getWert();
	}

	@Test
	public void testWerfen() throws WurfelNichtGeworfenException {
		Wurfel wurfel = (Wurfel) ac.getBean("normalWurfel");
		wurfel.werfen();
		assertEquals("Nach dem Wurf soll geworfen sein", wurfel.isGeworfen(), true);
		int zahl = wurfel.getWert();
		assertTrue("Wert muss zwischen 1 und 6 sein", zahl >= 1 && zahl <= 6 );
		//TODO mit Harmcrest waere besser
	}

	@Test
	public void testReset() {
		Wurfel wurfel = (Wurfel) ac.getBean("normalWurfel");
		wurfel.werfen();
		wurfel.reset();
		assertEquals("Nach dem Reset soll nicht geworfen sein", wurfel.isGeworfen(), false);
	}

}
