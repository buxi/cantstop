package de.buxi.cantstop.spielobjekte;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class WurfelManagerPaarungTest {

	/**
	 *	mögliche Paaren: 3		Summe
	 *				1 2 : 3 4 -> 3 7
	 *				1 3 : 2 4 -> 4 6
 	 *				1 4 : 2 3 -> 5 5
 	 *  kontrollString : 345567	
	 * @throws InvalidTestParametersException 
	 * @throws WurfelNichtGeworfenException 
	 */
	@Test
	public void testGetMoglichePaarungen_1_2_3_4() throws InvalidTestParametersException, WurfelNichtGeworfenException {
		List<Wurfel> wurfels = WurfelTestHelper.generateXeingestellteNormalWurfel(Arrays.asList(1,2,3,4));
		WurfelManager wurfelManager = new WurfelManager(wurfels);
		wurfelManager.allWerfen();
		List<ZweiWurfelPaar> paaren = wurfelManager.getMoglichePaarungen();
		assertEquals("Moegliche Paarungen", 3, paaren.size());
		String kontrollString = "345567";
		assertEquals("Falsche Paarungen", kontrollString, WurfelTestHelper.generatePaarungKontrollString(paaren));
		System.out.println(paaren);
	}

	/**
	 *	mögliche Paaren: 2		Summe
	 *				1 1 : 3 4 -> 2 7
	 *				1 3 : 1 4 -> 4 5
 	 *  kontrollString : 2457
	 * @throws WurfelNichtGeworfenException 
	 * @throws InvalidTestParametersException 
	 */
	@Test
	public void testGetMoglichePaarungen_1_1_3_4() throws InvalidTestParametersException, WurfelNichtGeworfenException {
		List<Wurfel> wurfels = WurfelTestHelper.generateXeingestellteNormalWurfel(Arrays.asList(1,1,3,4));
		WurfelManager wurfelManager = new WurfelManager(wurfels);
		wurfelManager.allWerfen();
		List<ZweiWurfelPaar> paaren = wurfelManager.getMoglichePaarungen();
		assertEquals("Moegliche Paarungen", 2, paaren.size());
		String kontrollString = "2457";
		assertEquals("Falsche Paarungen", kontrollString, WurfelTestHelper.generatePaarungKontrollString(paaren));
		System.out.println(paaren);
	}

	/**
	 *	mögliche Paaren: 2  Summe
	 *			1 1 : 3 3 -> 2 6
	 *			1 3 : 1 3 -> 4 4
 	 *  kontrollString : 2446	
	 * @throws WurfelNichtGeworfenException 
	 * @throws InvalidTestParametersException 
	 */
	@Test
	public void testGetMoglichePaarungen_1_1_3_3() throws InvalidTestParametersException, WurfelNichtGeworfenException {
		List<Wurfel> wurfels = WurfelTestHelper.generateXeingestellteNormalWurfel(Arrays.asList(1,1,3,3));
		WurfelManager wurfelManager = new WurfelManager(wurfels);
		wurfelManager.allWerfen();
		List<ZweiWurfelPaar> paaren = wurfelManager.getMoglichePaarungen();
		assertEquals("Moegliche Paarungen", 2, paaren.size());
		String kontrollString = "2446";
		assertEquals("Falsche Paarungen", kontrollString, WurfelTestHelper.generatePaarungKontrollString(paaren));
		System.out.println(paaren);
	}

	/**
	 *	mögliche Paaren: 1	Summe
	 *			1 1 : 1 4 -> 2 5
 	 *  kontrollString : 25	
	 * @throws WurfelNichtGeworfenException 
	 * @throws InvalidTestParametersException 
	 */
	@Test
	public void testGetMoglichePaarungen_1_1_1_4() throws InvalidTestParametersException, WurfelNichtGeworfenException {
		List<Wurfel> wurfels = WurfelTestHelper.generateXeingestellteNormalWurfel(Arrays.asList(1,1,1,4));
		WurfelManager wurfelManager = new WurfelManager(wurfels);
		wurfelManager.allWerfen();
		List<ZweiWurfelPaar> paaren = wurfelManager.getMoglichePaarungen();
		assertEquals("Moegliche Paarungen", 1, paaren.size());
		String kontrollString = "25";
		assertEquals("Falsche Paarungen", kontrollString, WurfelTestHelper.generatePaarungKontrollString(paaren));
		System.out.println(paaren);
	}

	/**
	 *	mögliche Paaren: 1	Summe
	 *			1 1 : 1 1 -> 2 2
 	 *  kontrollString : 22	
	 * @throws WurfelNichtGeworfenException 
	 * @throws InvalidTestParametersException 
	 */
	@Test
	public void testGetMoglichePaarungen_1_1_1_1() throws InvalidTestParametersException, WurfelNichtGeworfenException {
		List<Wurfel> wurfels = WurfelTestHelper.generateXeingestellteNormalWurfel(Arrays.asList(1,1,1,1));
		WurfelManager wurfelManager = new WurfelManager(wurfels);
		wurfelManager.allWerfen();
		List<ZweiWurfelPaar> paaren = wurfelManager.getMoglichePaarungen();
		assertEquals("Moegliche Paarungen", 1, paaren.size());
		String kontrollString = "22";
		assertEquals("Falsche Paarungen", kontrollString, WurfelTestHelper.generatePaarungKontrollString(paaren));
		System.out.println(paaren);
	}
	
	/**
	 *	mögliche Paaren: 3	Summe
	 *			2 4 : 5 6 -> 6 11
	 *			2 5 : 4 6 -> 7 10
	 *			2 6 : 4 5 -> 8 9
 	 *  kontrollString : 67891011
	 * @throws WurfelNichtGeworfenException 
	 * @throws InvalidTestParametersException 
	 */
	@Test
	public void testGetMoglichePaarungen_2_4_5_6() throws InvalidTestParametersException, WurfelNichtGeworfenException {
		List<Wurfel> wurfels = WurfelTestHelper.generateXeingestellteNormalWurfel(Arrays.asList(2,4,5,6));
		WurfelManager wurfelManager = new WurfelManager(wurfels);
		wurfelManager.allWerfen();
		List<ZweiWurfelPaar> paaren = wurfelManager.getMoglichePaarungen();
		assertEquals("Moegliche Paarungen", 3, paaren.size());
		String kontrollString = "67891011";
		assertEquals("Falsche Paarungen", kontrollString, WurfelTestHelper.generatePaarungKontrollString(paaren));
		System.out.println(paaren);
	}
	
	/**
	 *	mögliche Paaren: 2	Summe
	 *			2 3 : 3 4 -> 5 7
	 *			2 4 : 3 3 -> 6 6
 	 *  kontrollString : 5667
	 * @throws WurfelNichtGeworfenException 
	 * @throws InvalidTestParametersException 
	 */
	@Test
	public void testGetMoglichePaarungen_2_3_3_4() throws InvalidTestParametersException, WurfelNichtGeworfenException {
		List<Wurfel> wurfels = WurfelTestHelper.generateXeingestellteNormalWurfel(Arrays.asList(2,3,3,4));
		WurfelManager wurfelManager = new WurfelManager(wurfels);
		wurfelManager.allWerfen();
		List<ZweiWurfelPaar> paaren = wurfelManager.getMoglichePaarungen();
		assertEquals("Moegliche Paarungen", 2, paaren.size());
		String kontrollString = "5667";
		assertEquals("Falsche Paarungen", kontrollString, WurfelTestHelper.generatePaarungKontrollString(paaren));
		System.out.println(paaren);
	}
	
	/**
	 *	mögliche Paaren: 2	Summe
	 *			3 5 : 3 6 -> 8 9
	 *		    3 3 : 5 6 -> 6 11
 	 *  kontrollString : 68911
	 * @throws WurfelNichtGeworfenException 
	 * @throws InvalidTestParametersException 
	 */
	@Test
	public void testGetMoglichePaarungen_3_3_5_6() throws InvalidTestParametersException, WurfelNichtGeworfenException {
		List<Wurfel> wurfels = WurfelTestHelper.generateXeingestellteNormalWurfel(Arrays.asList(3,3,5,6));
		WurfelManager wurfelManager = new WurfelManager(wurfels);
		wurfelManager.allWerfen();
		List<ZweiWurfelPaar> paaren = wurfelManager.getMoglichePaarungen();
		assertEquals("Moegliche Paarungen", 2, paaren.size());
		String kontrollString = "68911";
		assertEquals("Falsche Paarungen", kontrollString, WurfelTestHelper.generatePaarungKontrollString(paaren));
		System.out.println(paaren);
	}

	/**
	 *	mögliche Paaren: 1	Summe
	 *			1 2 : 2 2 -> 3 4
 	 *  kontrollString : 34
	 * @throws WurfelNichtGeworfenException 
	 * @throws InvalidTestParametersException 
	 */
	@Test
	public void testGetMoglichePaarungen_1_2_2_2() throws InvalidTestParametersException, WurfelNichtGeworfenException {
		List<Wurfel> wurfels = WurfelTestHelper.generateXeingestellteNormalWurfel(Arrays.asList(1,2,2,2));
		WurfelManager wurfelManager = new WurfelManager(wurfels);
		wurfelManager.allWerfen();
		List<ZweiWurfelPaar> paaren = wurfelManager.getMoglichePaarungen();
		assertEquals("Moegliche Paarungen", 1, paaren.size());
		String kontrollString = "34";
		assertEquals("Falsche Paarungen", kontrollString, WurfelTestHelper.generatePaarungKontrollString(paaren));
		System.out.println(paaren);
	}	
}
