/**
 * 
 */
package de.buxi.cantstop.model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import de.buxi.cantstop.model.Dice;
import de.buxi.cantstop.model.DiceNotThrownException;
import de.buxi.cantstop.model.DicePair;
import de.buxi.cantstop.model.TwoDicesPair;

/**
 * @author buxi
 *
 */
public class TwoDicesPairTest {

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * Test method for {@link de.buxi.cantstop.model.TwoDicesPair#isSame(de.buxi.cantstop.model.TwoDicesPair)}.
	 * @throws DiceNotThrownException 
	 * @throws InvalidTestParametersException 
	 */
	@Test
	public void testIsSamePositive() throws InvalidTestParametersException, DiceNotThrownException {
		List<Dice> dices = DiceTestHelper.generateXpreSetNormalDice(Arrays.asList(1,2,3,4));
		TwoDicesPair basePairPair = new TwoDicesPair(
				new DicePair(dices.get(0), dices.get(1)),
				new DicePair(dices.get(2), dices.get(3)));
		assertTrue("same", basePairPair.isSame(basePairPair));
		
		TwoDicesPair other1  = new TwoDicesPair(
				new DicePair(dices.get(0), dices.get(1)),
				new DicePair(dices.get(3), dices.get(2)));
 		assertTrue("same", basePairPair.isSame(other1));

		TwoDicesPair other2  = new TwoDicesPair(
				new DicePair(dices.get(1), dices.get(0)),
				new DicePair(dices.get(2), dices.get(3)));
 		assertTrue("same", basePairPair.isSame(other2));

		TwoDicesPair other3  = new TwoDicesPair(
				new DicePair(dices.get(1), dices.get(0)),
				new DicePair(dices.get(3), dices.get(2)));
 		assertTrue("same", basePairPair.isSame(other3));

		TwoDicesPair other4  = new TwoDicesPair(
				new DicePair(dices.get(2), dices.get(3)),
				new DicePair(dices.get(0), dices.get(1)));
 		assertTrue("same", basePairPair.isSame(other4));
 		
 		TwoDicesPair other5  = new TwoDicesPair(
				new DicePair(dices.get(2), dices.get(3)),
				new DicePair(dices.get(1), dices.get(0)));
 		assertTrue("same", basePairPair.isSame(other5));
 		
 		TwoDicesPair other6  = new TwoDicesPair(
				new DicePair(dices.get(3), dices.get(2)),
				new DicePair(dices.get(0), dices.get(1)));
 		assertTrue("same", basePairPair.isSame(other6));

 		TwoDicesPair other7  = new TwoDicesPair(
				new DicePair(dices.get(3), dices.get(2)),
				new DicePair(dices.get(1), dices.get(0)));
 		assertTrue("same", basePairPair.isSame(other7));
	}
	
	@Test
	public void testEqualsPositive() throws InvalidTestParametersException, DiceNotThrownException {
		List<Dice> dices = DiceTestHelper.generateXpreSetNormalDice(Arrays.asList(1,2,3,4));
		TwoDicesPair basePairPair = new TwoDicesPair(
				new DicePair(dices.get(0), dices.get(1)),
				new DicePair(dices.get(2), dices.get(3)));
		assertTrue("same", basePairPair.equals(basePairPair));
		
		TwoDicesPair other1  = new TwoDicesPair(
				new DicePair(dices.get(0), dices.get(1)),
				new DicePair(dices.get(3), dices.get(2)));
 		assertTrue("same", basePairPair.equals(other1));

		TwoDicesPair other2  = new TwoDicesPair(
				new DicePair(dices.get(1), dices.get(0)),
				new DicePair(dices.get(2), dices.get(3)));
 		assertTrue("same", basePairPair.equals(other2));

		TwoDicesPair other3  = new TwoDicesPair(
				new DicePair(dices.get(1), dices.get(0)),
				new DicePair(dices.get(3), dices.get(2)));
 		assertTrue("same", basePairPair.equals(other3));

		TwoDicesPair other4  = new TwoDicesPair(
				new DicePair(dices.get(2), dices.get(3)),
				new DicePair(dices.get(0), dices.get(1)));
 		assertTrue("same", basePairPair.equals(other4));
 		
 		TwoDicesPair other5  = new TwoDicesPair(
				new DicePair(dices.get(2), dices.get(3)),
				new DicePair(dices.get(1), dices.get(0)));
 		assertTrue("same", basePairPair.equals(other5));
 		
 		TwoDicesPair other6  = new TwoDicesPair(
				new DicePair(dices.get(3), dices.get(2)),
				new DicePair(dices.get(0), dices.get(1)));
 		assertTrue("same", basePairPair.equals(other6));

 		TwoDicesPair other7  = new TwoDicesPair(
				new DicePair(dices.get(3), dices.get(2)),
				new DicePair(dices.get(1), dices.get(0)));
 		assertTrue("same", basePairPair.equals(other7));
	}

	/**
	 * Test method for {@link de.buxi.cantstop.model.TwoDicesPair#isSame(de.buxi.cantstop.model.TwoDicesPair)}.
	 * @throws DiceNotThrownException 
	 * @throws InvalidTestParametersException 
	 */
	@Test
	public void testIsSameNegative() throws InvalidTestParametersException, DiceNotThrownException {
		List<Dice> dices = DiceTestHelper.generateXpreSetNormalDice(Arrays.asList(1,2,3,4));
		TwoDicesPair basePairPair = new TwoDicesPair(
				new DicePair(dices.get(0), dices.get(1)),
				new DicePair(dices.get(2), dices.get(3)));
		
		List<Dice> dices2 = DiceTestHelper.generateXpreSetNormalDice(Arrays.asList(3,4,5,6));
		TwoDicesPair other = new TwoDicesPair(
				new DicePair(dices2.get(0), dices2.get(1)),
				new DicePair(dices2.get(2), dices2.get(3)));
		
		
		assertFalse("not same", basePairPair.isSame(other));
	}
	
	/**
	 * Test method for {@link de.buxi.cantstop.model.TwoDicesPair#isSame(de.buxi.cantstop.model.TwoDicesPair)}.
	 * @throws DiceNotThrownException 
	 * @throws InvalidTestParametersException 
	 */
	@Test
	public void testEqualsNegative() throws InvalidTestParametersException, DiceNotThrownException {
		List<Dice> dices = DiceTestHelper.generateXpreSetNormalDice(Arrays.asList(1,2,3,4));
		TwoDicesPair basePairPair = new TwoDicesPair(
				new DicePair(dices.get(0), dices.get(1)),
				new DicePair(dices.get(2), dices.get(3)));
		
		List<Dice> dices2 = DiceTestHelper.generateXpreSetNormalDice(Arrays.asList(3,4,5,6));
		TwoDicesPair other = new TwoDicesPair(
				new DicePair(dices2.get(0), dices2.get(1)),
				new DicePair(dices2.get(2), dices2.get(3)));
		
		
		assertFalse("not same", basePairPair.equals(other));
	}
	
	@Test
	public void testCollectionRemove() throws InvalidTestParametersException, DiceNotThrownException {
		List<Dice> dices = DiceTestHelper.generateXpreSetNormalDice(Arrays.asList(1,2,3,4));
		TwoDicesPair basePairPair = new TwoDicesPair(
				new DicePair(dices.get(0), dices.get(1)),
				new DicePair(dices.get(2), dices.get(3)));
		
		List<Dice> dices2 = DiceTestHelper.generateXpreSetNormalDice(Arrays.asList(3,4,5,6));
		TwoDicesPair other = new TwoDicesPair(
				new DicePair(dices2.get(0), dices2.get(1)),
				new DicePair(dices2.get(2), dices2.get(3)));
		
		Collection<TwoDicesPair> collA = new ArrayList<TwoDicesPair>();
		collA.add(basePairPair);
		collA.add(other);
		
		Collection<TwoDicesPair> collB = new ArrayList<TwoDicesPair>();
		TwoDicesPair basePairPair2 = new TwoDicesPair(
				new DicePair(dices.get(0), dices.get(1)),
				new DicePair(dices.get(2), dices.get(3)));
		collB.add(basePairPair2);
		
		collA.removeAll(collB);
		assertEquals(collA.size(),1);
	}
	
}
