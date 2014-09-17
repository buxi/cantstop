package de.buxi.cantstop.spielobjekte;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class DiceManagerPairungTest {

	/**
	 *	possible pairs: 3		Sum
	 *				1 2 : 3 4 -> 3 7
	 *				1 3 : 2 4 -> 4 6
 	 *				1 4 : 2 3 -> 5 5
 	 *  controllString : 345567	
	 * @throws InvalidTestParametersException 
	 * @throws DiceNotThrownException 
	 */
	@Test
	public void testGetPossiblePairs_1_2_3_4() throws InvalidTestParametersException, DiceNotThrownException {
		List<Dice> dices = DiceTestHelper.generateXpreSetNormalDice(Arrays.asList(1,2,3,4));
		DiceManager diceManager = new DiceManager(dices);
		diceManager.throwAllDices();
		List<TwoDicesPair> paaren = diceManager.getAllPossiblePairs();
		assertEquals("possible pairs", 3, paaren.size());
		String controllString = "345567";
		assertEquals("wrong pairs", controllString, DiceTestHelper.generatePairControllString(paaren));
		System.out.println(paaren);
	}

	/**
	 *	possible pairs: 2		Sum
	 *				1 1 : 3 4 -> 2 7
	 *				1 3 : 1 4 -> 4 5
 	 *  controllString : 2457
	 * @throws DiceNotThrownException 
	 * @throws InvalidTestParametersException 
	 */
	@Test
	public void testGetPossiblePairs_1_1_3_4() throws InvalidTestParametersException, DiceNotThrownException {
		List<Dice> dices = DiceTestHelper.generateXpreSetNormalDice(Arrays.asList(1,1,3,4));
		DiceManager diceManager = new DiceManager(dices);
		diceManager.throwAllDices();
		List<TwoDicesPair> paaren = diceManager.getAllPossiblePairs();
		assertEquals("possible pairs", 2, paaren.size());
		String controllString = "2457";
		assertEquals("wrong pairs", controllString, DiceTestHelper.generatePairControllString(paaren));
		System.out.println(paaren);
	}

	/**
	 *	possible pairs: 2  Sum
	 *			1 1 : 3 3 -> 2 6
	 *			1 3 : 1 3 -> 4 4
 	 *  controllString : 2446	
	 * @throws DiceNotThrownException 
	 * @throws InvalidTestParametersException 
	 */
	@Test
	public void testGetPossiblePairs_1_1_3_3() throws InvalidTestParametersException, DiceNotThrownException {
		List<Dice> dices = DiceTestHelper.generateXpreSetNormalDice(Arrays.asList(1,1,3,3));
		DiceManager diceManager = new DiceManager(dices);
		diceManager.throwAllDices();
		List<TwoDicesPair> paaren = diceManager.getAllPossiblePairs();
		assertEquals("possible pairs", 2, paaren.size());
		String controllString = "2446";
		assertEquals("wrong pairs", controllString, DiceTestHelper.generatePairControllString(paaren));
		System.out.println(paaren);
	}

	/**
	 *	possible pairs: 1	Sum
	 *			1 1 : 1 4 -> 2 5
 	 *  controllString : 25	
	 * @throws DiceNotThrownException 
	 * @throws InvalidTestParametersException 
	 */
	@Test
	public void testGetPossiblePairs_1_1_1_4() throws InvalidTestParametersException, DiceNotThrownException {
		List<Dice> dices = DiceTestHelper.generateXpreSetNormalDice(Arrays.asList(1,1,1,4));
		DiceManager diceManager = new DiceManager(dices);
		diceManager.throwAllDices();
		List<TwoDicesPair> paaren = diceManager.getAllPossiblePairs();
		assertEquals("possible pairs", 1, paaren.size());
		String controllString = "25";
		assertEquals("wrong pairs", controllString, DiceTestHelper.generatePairControllString(paaren));
		System.out.println(paaren);
	}

	/**
	 *	possible pairs: 1	Sum
	 *			1 1 : 1 1 -> 2 2
 	 *  controllString : 22	
	 * @throws DiceNotThrownException 
	 * @throws InvalidTestParametersException 
	 */
	@Test
	public void testGetPossiblePairs_1_1_1_1() throws InvalidTestParametersException, DiceNotThrownException {
		List<Dice> dices = DiceTestHelper.generateXpreSetNormalDice(Arrays.asList(1,1,1,1));
		DiceManager diceManager = new DiceManager(dices);
		diceManager.throwAllDices();
		List<TwoDicesPair> paaren = diceManager.getAllPossiblePairs();
		assertEquals("possible pairs", 1, paaren.size());
		String controllString = "22";
		assertEquals("wrong pairs", controllString, DiceTestHelper.generatePairControllString(paaren));
		System.out.println(paaren);
	}
	
	/**
	 *	possible pairs: 3	Sum
	 *			2 4 : 5 6 -> 6 11
	 *			2 5 : 4 6 -> 7 10
	 *			2 6 : 4 5 -> 8 9
 	 *  controllString : 67891011
	 * @throws DiceNotThrownException 
	 * @throws InvalidTestParametersException 
	 */
	@Test
	public void testGetPossiblePairs_2_4_5_6() throws InvalidTestParametersException, DiceNotThrownException {
		List<Dice> dices = DiceTestHelper.generateXpreSetNormalDice(Arrays.asList(2,4,5,6));
		DiceManager diceManager = new DiceManager(dices);
		diceManager.throwAllDices();
		List<TwoDicesPair> paaren = diceManager.getAllPossiblePairs();
		assertEquals("possible pairs", 3, paaren.size());
		String controllString = "67891011";
		assertEquals("wrong pairs", controllString, DiceTestHelper.generatePairControllString(paaren));
		System.out.println(paaren);
	}
	
	/**
	 *	possible pairs: 2	Sum
	 *			2 3 : 3 4 -> 5 7
	 *			2 4 : 3 3 -> 6 6
 	 *  controllString : 5667
	 * @throws DiceNotThrownException 
	 * @throws InvalidTestParametersException 
	 */
	@Test
	public void testGetPossiblePairs_2_3_3_4() throws InvalidTestParametersException, DiceNotThrownException {
		List<Dice> dices = DiceTestHelper.generateXpreSetNormalDice(Arrays.asList(2,3,3,4));
		DiceManager diceManager = new DiceManager(dices);
		diceManager.throwAllDices();
		List<TwoDicesPair> paaren = diceManager.getAllPossiblePairs();
		assertEquals("possible pairs", 2, paaren.size());
		String controllString = "5667";
		assertEquals("wrong pairs", controllString, DiceTestHelper.generatePairControllString(paaren));
		System.out.println(paaren);
	}
	
	/**
	 *	possible pairs: 2	Sum
	 *			3 5 : 3 6 -> 8 9
	 *		    3 3 : 5 6 -> 6 11
 	 *  controllString : 68911
	 * @throws DiceNotThrownException 
	 * @throws InvalidTestParametersException 
	 */
	@Test
	public void testGetPossiblePairs_3_3_5_6() throws InvalidTestParametersException, DiceNotThrownException {
		List<Dice> dices = DiceTestHelper.generateXpreSetNormalDice(Arrays.asList(3,3,5,6));
		DiceManager diceManager = new DiceManager(dices);
		diceManager.throwAllDices();
		List<TwoDicesPair> paaren = diceManager.getAllPossiblePairs();
		assertEquals("possible pairs", 2, paaren.size());
		String controllString = "68911";
		assertEquals("wrong pairs", controllString, DiceTestHelper.generatePairControllString(paaren));
		System.out.println(paaren);
	}

	/**
	 *	possible pairs: 1	Sum
	 *			1 2 : 2 2 -> 3 4
 	 *  controllString : 34
	 * @throws DiceNotThrownException 
	 * @throws InvalidTestParametersException 
	 */
	@Test
	public void testGetPossiblePairs_1_2_2_2() throws InvalidTestParametersException, DiceNotThrownException {
		List<Dice> dices = DiceTestHelper.generateXpreSetNormalDice(Arrays.asList(1,2,2,2));
		DiceManager diceManager = new DiceManager(dices);
		diceManager.throwAllDices();
		List<TwoDicesPair> paaren = diceManager.getAllPossiblePairs();
		assertEquals("possible pairs", 1, paaren.size());
		String controllString = "34";
		assertEquals("wrong pairs", controllString, DiceTestHelper.generatePairControllString(paaren));
		System.out.println(paaren);
	}	
}
