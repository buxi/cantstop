/**
 * 
 */
package de.buxi.cantstop.spielobjekte;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

/**
 * @author buxi
 *
 */
public class ZweiWurfelPaarSetTest {
	private ZweiWurfelPaarSet zweiWurfelPaarSet;
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		zweiWurfelPaarSet = new ZweiWurfelPaarSet();
	}

	/**
	 * Test method for {@link de.buxi.cantstop.spielobjekte.ZweiWurfelPaarSet#add(de.buxi.cantstop.spielobjekte.ZweiWurfelPaar)}.
	 * @throws WurfelNichtGeworfenException 
	 * @throws InvalidTestParametersException 
	 */
	@Test
	public void testAddEins() throws WurfelNichtGeworfenException, InvalidTestParametersException {
		List<Wurfel> wurfels = WurfelTestHelper.generateXeingestellteNormalWurfel(Arrays.asList(1,2,3,4));
		ZweiWurfelPaar basisZweier = new ZweiWurfelPaar(
				new WurfelPaar(wurfels.get(0), wurfels.get(1)),
				new WurfelPaar(wurfels.get(2), wurfels.get(3)));
		
		boolean warAdded = zweiWurfelPaarSet.add(basisZweier);
		assertTrue("soll added sein", warAdded);
		assertEquals(1, zweiWurfelPaarSet.getResultSet().size());
	}

	/**
	 * Test method for {@link de.buxi.cantstop.spielobjekte.ZweiWurfelPaarSet#add(de.buxi.cantstop.spielobjekte.ZweiWurfelPaar)}.
	 * @throws WurfelNichtGeworfenException 
	 * @throws InvalidTestParametersException 
	 */
	@Test
	public void testAddZwei() throws WurfelNichtGeworfenException, InvalidTestParametersException {
		List<Wurfel> wurfels = WurfelTestHelper.generateXeingestellteNormalWurfel(Arrays.asList(1,2,3,4));
		ZweiWurfelPaar basisZweier = new ZweiWurfelPaar(
				new WurfelPaar(wurfels.get(0), wurfels.get(1)),
				new WurfelPaar(wurfels.get(2), wurfels.get(3)));

		List<Wurfel> wurfels2 = WurfelTestHelper.generateXeingestellteNormalWurfel(Arrays.asList(4,5,6,3));
		ZweiWurfelPaar andere = new ZweiWurfelPaar(
				new WurfelPaar(wurfels2.get(0), wurfels2.get(1)),
				new WurfelPaar(wurfels2.get(2), wurfels2.get(3)));

		
		boolean warAdded = zweiWurfelPaarSet.add(basisZweier);
		assertTrue("soll added sein", warAdded);
		
		warAdded = zweiWurfelPaarSet.add(andere);
		assertTrue("soll added sein", warAdded);
		
		
		assertEquals(2, zweiWurfelPaarSet.getResultSet().size());
	}

	/**
	 * Test method for {@link de.buxi.cantstop.spielobjekte.ZweiWurfelPaarSet#add(de.buxi.cantstop.spielobjekte.ZweiWurfelPaar)}.
	 * @throws WurfelNichtGeworfenException 
	 * @throws InvalidTestParametersException 
	 */
	@Test
	public void testAddDrei() throws WurfelNichtGeworfenException, InvalidTestParametersException {
		List<Wurfel> wurfels = WurfelTestHelper.generateXeingestellteNormalWurfel(Arrays.asList(1,2,3,4));
		ZweiWurfelPaar basisZweier = new ZweiWurfelPaar(
				new WurfelPaar(wurfels.get(0), wurfels.get(1)),
				new WurfelPaar(wurfels.get(2), wurfels.get(3)));

		List<Wurfel> wurfels2 = WurfelTestHelper.generateXeingestellteNormalWurfel(Arrays.asList(4,5,6,3));
		ZweiWurfelPaar andere = new ZweiWurfelPaar(
				new WurfelPaar(wurfels2.get(0), wurfels2.get(1)),
				new WurfelPaar(wurfels2.get(2), wurfels2.get(3)));

		
		List<Wurfel> wurfels3 = WurfelTestHelper.generateXeingestellteNormalWurfel(Arrays.asList(1,1,1,1));
		ZweiWurfelPaar andere2 = new ZweiWurfelPaar(
				new WurfelPaar(wurfels3.get(0), wurfels3.get(1)),
				new WurfelPaar(wurfels3.get(2), wurfels3.get(3)));
		
		boolean warAdded = zweiWurfelPaarSet.add(basisZweier);
		assertTrue("soll added sein", warAdded);
		
		warAdded = zweiWurfelPaarSet.add(andere);
		assertTrue("soll added sein", warAdded);
		
		warAdded = zweiWurfelPaarSet.add(andere2);
		assertTrue("soll added sein", warAdded);
		
		assertEquals(3, zweiWurfelPaarSet.getResultSet().size());
	}
}
