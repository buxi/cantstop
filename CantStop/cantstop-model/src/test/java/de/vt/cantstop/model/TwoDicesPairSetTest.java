/**
 * 
 */
package de.vt.cantstop.model;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import de.vt.cantstop.model.Dice;
import de.vt.cantstop.model.DiceNotThrownException;
import de.vt.cantstop.model.DicePair;
import de.vt.cantstop.model.TwoDicesPair;
import de.vt.cantstop.model.TwoDicesPairSet;

/**
 * @author buxi
 *
 */
public class TwoDicesPairSetTest {
	private TwoDicesPairSet twoDicePairSet;
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		twoDicePairSet = new TwoDicesPairSet();
	}

	/**
	 * Test method for {@link de.de.vt.cantstop.model.TwoDicesPairSet#add(de.de.vt.cantstop.model.TwoDicesPair)}.
	 * @throws DiceNotThrownException 
	 * @throws InvalidTestParametersException 
	 */
	@Test
	public void testAddOnes() throws DiceNotThrownException, InvalidTestParametersException {
		List<Dice> dices = DiceTestHelper.generateXpreSetNormalDice(Arrays.asList(1,2,3,4));
		TwoDicesPair basePairPair = new TwoDicesPair(
				new DicePair(dices.get(0), dices.get(1)),
				new DicePair(dices.get(2), dices.get(3)));
		
		boolean wasAdded = twoDicePairSet.add(basePairPair);
		assertTrue("should be added", wasAdded);
		assertEquals(1, twoDicePairSet.getResultSet().size());
	}

	/**
	 * Test method for {@link de.de.vt.cantstop.model.TwoDicesPairSet#add(de.de.vt.cantstop.model.TwoDicesPair)}.
	 * @throws DiceNotThrownException 
	 * @throws InvalidTestParametersException 
	 */
	@Test
	public void testAddTwo() throws DiceNotThrownException, InvalidTestParametersException {
		List<Dice> dices = DiceTestHelper.generateXpreSetNormalDice(Arrays.asList(1,2,3,4));
		TwoDicesPair basePairPair = new TwoDicesPair(
				new DicePair(dices.get(0), dices.get(1)),
				new DicePair(dices.get(2), dices.get(3)));

		List<Dice> dices2 = DiceTestHelper.generateXpreSetNormalDice(Arrays.asList(4,5,6,3));
		TwoDicesPair other = new TwoDicesPair(
				new DicePair(dices2.get(0), dices2.get(1)),
				new DicePair(dices2.get(2), dices2.get(3)));

		
		boolean wasAdded = twoDicePairSet.add(basePairPair);
		assertTrue("should be added", wasAdded);
		
		wasAdded = twoDicePairSet.add(other);
		assertTrue("should be added", wasAdded);
		
		
		assertEquals(2, twoDicePairSet.getResultSet().size());
	}

	/**
	 * Test method for {@link de.de.vt.cantstop.model.TwoDicesPairSet#add(de.de.vt.cantstop.model.TwoDicesPair)}.
	 * @throws DiceNotThrownException 
	 * @throws InvalidTestParametersException 
	 */
	@Test
	public void testAddThree() throws DiceNotThrownException, InvalidTestParametersException {
		List<Dice> dices = DiceTestHelper.generateXpreSetNormalDice(Arrays.asList(1,2,3,4));
		TwoDicesPair basePairPair = new TwoDicesPair(
				new DicePair(dices.get(0), dices.get(1)),
				new DicePair(dices.get(2), dices.get(3)));

		List<Dice> dices2 = DiceTestHelper.generateXpreSetNormalDice(Arrays.asList(4,5,6,3));
		TwoDicesPair other = new TwoDicesPair(
				new DicePair(dices2.get(0), dices2.get(1)),
				new DicePair(dices2.get(2), dices2.get(3)));

		
		List<Dice> dices3 = DiceTestHelper.generateXpreSetNormalDice(Arrays.asList(1,1,1,1));
		TwoDicesPair other2 = new TwoDicesPair(
				new DicePair(dices3.get(0), dices3.get(1)),
				new DicePair(dices3.get(2), dices3.get(3)));
		
		boolean wasAdded = twoDicePairSet.add(basePairPair);
		assertTrue("should be added", wasAdded);
		
		wasAdded = twoDicePairSet.add(other);
		assertTrue("should be added", wasAdded);
		
		wasAdded = twoDicePairSet.add(other2);
		assertTrue("should be added", wasAdded);
		
		assertEquals(3, twoDicePairSet.getResultSet().size());
	}
}
