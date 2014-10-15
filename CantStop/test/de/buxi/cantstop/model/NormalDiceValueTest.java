/**
 * 
 */
package de.buxi.cantstop.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import de.buxi.cantstop.model.Dice;
import de.buxi.cantstop.model.DiceNotThrownException;

/**
 * @author buxi
 *
 */
public class NormalDiceValueTest  extends SpringLoaderSuperClassModelTests{
	// TODO not nice, rarely can fail
	private int maxthrows = 100; // endless loop to avoid

	private static Dice dice; 
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		dice = (Dice) ac.getBean("normalDice");
	}

	
	/**
	 * Test method for {@link de.buxi.cantstop.model.DiceImpl#throwDice()}.
	 * @throws DiceNotThrownException 
	 */
	@Test
	public void testThrowOntoOne() throws DiceNotThrownException {
		assertEquals("Value 1 should be", 1, throwDiceOntoX(1));
	}

	/**
	 * Test method for {@link de.buxi.cantstop.model.DiceImpl#throwDice()}.
	 * @throws DiceNotThrownException 
	 */
	@Test
	public void testThrowOntoTwo() throws DiceNotThrownException {
		assertEquals("Value 2 should be", 2, throwDiceOntoX(2));
	}
	
	/**
	 * Test method for {@link de.buxi.cantstop.model.DiceImpl#throwDice()}.
	 * @throws DiceNotThrownException 
	 */
	@Test
	public void testThrowOntoThree() throws DiceNotThrownException {
		assertEquals("Value 3 should be", 3, throwDiceOntoX(3));
	}
	
	/**
	 * Test method for {@link de.buxi.cantstop.model.DiceImpl#throwDice()}.
	 * @throws DiceNotThrownException 
	 */
	@Test
	public void testThrowOntoFour() throws DiceNotThrownException {
		assertEquals("Value 4 should be", 4, throwDiceOntoX(4));
	}
	
	/**
	 * Test method for {@link de.buxi.cantstop.model.DiceImpl#throwDice()}.
	 * @throws DiceNotThrownException 
	 */
	@Test
	public void testThrowOntoFive() throws DiceNotThrownException {
		assertEquals("Value 5 should be", 5, throwDiceOntoX(5));
	}

	/**
	 * Test method for {@link de.buxi.cantstop.model.DiceImpl#throwDice()}.
	 * @throws DiceNotThrownException 
	 */
	@Test
	public void testThrowOntoSix() throws DiceNotThrownException {
		assertEquals("Value 6 should be", 6, throwDiceOntoX(6));
	}

	/**
	 * @param targetValue 
	 * @return
	 */
	private int throwDiceOntoX(int targetValue) {
		dice.reset();
		int number = 0;
		int i = maxthrows; 
		do {
			dice.throwDice();
			number = dice.getDiceValue(); 
			i--;
			
		} while( number != targetValue && i > 0);
		return number;
	}

}
