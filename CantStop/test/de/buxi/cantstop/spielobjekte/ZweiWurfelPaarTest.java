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
 *
 */
public class ZweiWurfelPaarTest {

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * Test method for {@link de.buxi.cantstop.spielobjekte.ZweiWurfelPaar#isGleich(de.buxi.cantstop.spielobjekte.ZweiWurfelPaar)}.
	 * @throws WurfelNichtGeworfenException 
	 * @throws InvalidTestParametersException 
	 */
	@Test
	public void testIsGleichPositive() throws InvalidTestParametersException, WurfelNichtGeworfenException {
		List<Wurfel> wurfels = WurfelTestHelper.generateXeingestellteNormalWurfel(Arrays.asList(1,2,3,4));
		ZweiWurfelPaar basisZweier = new ZweiWurfelPaar(
				new WurfelPaar(wurfels.get(0), wurfels.get(1)),
				new WurfelPaar(wurfels.get(2), wurfels.get(3)));
		assertTrue("gleich", basisZweier.isGleich(basisZweier));
		
		ZweiWurfelPaar andere1  = new ZweiWurfelPaar(
				new WurfelPaar(wurfels.get(0), wurfels.get(1)),
				new WurfelPaar(wurfels.get(3), wurfels.get(2)));
 		assertTrue("gleich", basisZweier.isGleich(andere1));

		ZweiWurfelPaar andere2  = new ZweiWurfelPaar(
				new WurfelPaar(wurfels.get(1), wurfels.get(0)),
				new WurfelPaar(wurfels.get(2), wurfels.get(3)));
 		assertTrue("gleich", basisZweier.isGleich(andere2));

		ZweiWurfelPaar andere3  = new ZweiWurfelPaar(
				new WurfelPaar(wurfels.get(1), wurfels.get(0)),
				new WurfelPaar(wurfels.get(3), wurfels.get(2)));
 		assertTrue("gleich", basisZweier.isGleich(andere3));

		ZweiWurfelPaar andere4  = new ZweiWurfelPaar(
				new WurfelPaar(wurfels.get(2), wurfels.get(3)),
				new WurfelPaar(wurfels.get(0), wurfels.get(1)));
 		assertTrue("gleich", basisZweier.isGleich(andere4));
 		
 		ZweiWurfelPaar andere5  = new ZweiWurfelPaar(
				new WurfelPaar(wurfels.get(2), wurfels.get(3)),
				new WurfelPaar(wurfels.get(1), wurfels.get(0)));
 		assertTrue("gleich", basisZweier.isGleich(andere5));
 		
 		ZweiWurfelPaar andere6  = new ZweiWurfelPaar(
				new WurfelPaar(wurfels.get(3), wurfels.get(2)),
				new WurfelPaar(wurfels.get(0), wurfels.get(1)));
 		assertTrue("gleich", basisZweier.isGleich(andere6));

 		ZweiWurfelPaar andere7  = new ZweiWurfelPaar(
				new WurfelPaar(wurfels.get(3), wurfels.get(2)),
				new WurfelPaar(wurfels.get(1), wurfels.get(0)));
 		assertTrue("gleich", basisZweier.isGleich(andere7));
	}
	
	@Test
	public void testEqualsPositive() throws InvalidTestParametersException, WurfelNichtGeworfenException {
		List<Wurfel> wurfels = WurfelTestHelper.generateXeingestellteNormalWurfel(Arrays.asList(1,2,3,4));
		ZweiWurfelPaar basisZweier = new ZweiWurfelPaar(
				new WurfelPaar(wurfels.get(0), wurfels.get(1)),
				new WurfelPaar(wurfels.get(2), wurfels.get(3)));
		assertTrue("gleich", basisZweier.equals(basisZweier));
		
		ZweiWurfelPaar andere1  = new ZweiWurfelPaar(
				new WurfelPaar(wurfels.get(0), wurfels.get(1)),
				new WurfelPaar(wurfels.get(3), wurfels.get(2)));
 		assertTrue("gleich", basisZweier.equals(andere1));

		ZweiWurfelPaar andere2  = new ZweiWurfelPaar(
				new WurfelPaar(wurfels.get(1), wurfels.get(0)),
				new WurfelPaar(wurfels.get(2), wurfels.get(3)));
 		assertTrue("gleich", basisZweier.equals(andere2));

		ZweiWurfelPaar andere3  = new ZweiWurfelPaar(
				new WurfelPaar(wurfels.get(1), wurfels.get(0)),
				new WurfelPaar(wurfels.get(3), wurfels.get(2)));
 		assertTrue("gleich", basisZweier.equals(andere3));

		ZweiWurfelPaar andere4  = new ZweiWurfelPaar(
				new WurfelPaar(wurfels.get(2), wurfels.get(3)),
				new WurfelPaar(wurfels.get(0), wurfels.get(1)));
 		assertTrue("gleich", basisZweier.equals(andere4));
 		
 		ZweiWurfelPaar andere5  = new ZweiWurfelPaar(
				new WurfelPaar(wurfels.get(2), wurfels.get(3)),
				new WurfelPaar(wurfels.get(1), wurfels.get(0)));
 		assertTrue("gleich", basisZweier.equals(andere5));
 		
 		ZweiWurfelPaar andere6  = new ZweiWurfelPaar(
				new WurfelPaar(wurfels.get(3), wurfels.get(2)),
				new WurfelPaar(wurfels.get(0), wurfels.get(1)));
 		assertTrue("gleich", basisZweier.equals(andere6));

 		ZweiWurfelPaar andere7  = new ZweiWurfelPaar(
				new WurfelPaar(wurfels.get(3), wurfels.get(2)),
				new WurfelPaar(wurfels.get(1), wurfels.get(0)));
 		assertTrue("gleich", basisZweier.equals(andere7));
	}

	/**
	 * Test method for {@link de.buxi.cantstop.spielobjekte.ZweiWurfelPaar#isGleich(de.buxi.cantstop.spielobjekte.ZweiWurfelPaar)}.
	 * @throws WurfelNichtGeworfenException 
	 * @throws InvalidTestParametersException 
	 */
	@Test
	public void testIsGleichNegative() throws InvalidTestParametersException, WurfelNichtGeworfenException {
		List<Wurfel> wurfels = WurfelTestHelper.generateXeingestellteNormalWurfel(Arrays.asList(1,2,3,4));
		ZweiWurfelPaar basisZweier = new ZweiWurfelPaar(
				new WurfelPaar(wurfels.get(0), wurfels.get(1)),
				new WurfelPaar(wurfels.get(2), wurfels.get(3)));
		
		List<Wurfel> wurfels2 = WurfelTestHelper.generateXeingestellteNormalWurfel(Arrays.asList(3,4,5,6));
		ZweiWurfelPaar andere = new ZweiWurfelPaar(
				new WurfelPaar(wurfels2.get(0), wurfels2.get(1)),
				new WurfelPaar(wurfels2.get(2), wurfels2.get(3)));
		
		
		assertFalse("not gleich", basisZweier.isGleich(andere));
	}
	
	/**
	 * Test method for {@link de.buxi.cantstop.spielobjekte.ZweiWurfelPaar#isGleich(de.buxi.cantstop.spielobjekte.ZweiWurfelPaar)}.
	 * @throws WurfelNichtGeworfenException 
	 * @throws InvalidTestParametersException 
	 */
	@Test
	public void testEqualsNegative() throws InvalidTestParametersException, WurfelNichtGeworfenException {
		List<Wurfel> wurfels = WurfelTestHelper.generateXeingestellteNormalWurfel(Arrays.asList(1,2,3,4));
		ZweiWurfelPaar basisZweier = new ZweiWurfelPaar(
				new WurfelPaar(wurfels.get(0), wurfels.get(1)),
				new WurfelPaar(wurfels.get(2), wurfels.get(3)));
		
		List<Wurfel> wurfels2 = WurfelTestHelper.generateXeingestellteNormalWurfel(Arrays.asList(3,4,5,6));
		ZweiWurfelPaar andere = new ZweiWurfelPaar(
				new WurfelPaar(wurfels2.get(0), wurfels2.get(1)),
				new WurfelPaar(wurfels2.get(2), wurfels2.get(3)));
		
		
		assertFalse("not gleich", basisZweier.equals(andere));
	}
	
	@Test
	public void testCollectionRemove() throws InvalidTestParametersException, WurfelNichtGeworfenException {
		List<Wurfel> wurfels = WurfelTestHelper.generateXeingestellteNormalWurfel(Arrays.asList(1,2,3,4));
		ZweiWurfelPaar basisZweier = new ZweiWurfelPaar(
				new WurfelPaar(wurfels.get(0), wurfels.get(1)),
				new WurfelPaar(wurfels.get(2), wurfels.get(3)));
		
		List<Wurfel> wurfels2 = WurfelTestHelper.generateXeingestellteNormalWurfel(Arrays.asList(3,4,5,6));
		ZweiWurfelPaar andere = new ZweiWurfelPaar(
				new WurfelPaar(wurfels2.get(0), wurfels2.get(1)),
				new WurfelPaar(wurfels2.get(2), wurfels2.get(3)));
		
		Collection<ZweiWurfelPaar> collA = new ArrayList<ZweiWurfelPaar>();
		collA.add(basisZweier);
		collA.add(andere);
		
		Collection<ZweiWurfelPaar> collB = new ArrayList<ZweiWurfelPaar>();
		ZweiWurfelPaar basisZweier2 = new ZweiWurfelPaar(
				new WurfelPaar(wurfels.get(0), wurfels.get(1)),
				new WurfelPaar(wurfels.get(2), wurfels.get(3)));
		collB.add(basisZweier2);
		
		collA.removeAll(collB);
		assertEquals(collA.size(),1);
	}
	
}
