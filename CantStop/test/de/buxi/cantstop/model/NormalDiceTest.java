package de.buxi.cantstop.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import de.buxi.cantstop.model.Dice;
import de.buxi.cantstop.model.DiceNotThrownException;

public class NormalDiceTest extends SpringLoaderSuperClassModelTests {
	
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testDice() {
		Dice dice = (Dice) ac.getBean("normalDice");
		assertEquals("new NormalDice should be not yet thrown", dice.isThrown(), false);
	}

	@Test(expected=DiceNotThrownException.class)
	public void testGetZahlOhneThrow() throws DiceNotThrownException {
		Dice dice =(Dice) ac.getBean("normalDice");
		dice.getDiceValue();
	}

	@Test
	public void testThrow() {
		Dice dice = (Dice) ac.getBean("normalDice");
		dice.throwDice();
		assertEquals("After throw should be thrown", dice.isThrown(), true);
		int number = dice.getDiceValue();
		assertTrue("Value muss zwischen 1 und 6 sein", number >= 1 && number <= 6 );
		//TODO with Harmcrest would be better
	}

	@Test
	public void testReset() {
		Dice dice = (Dice) ac.getBean("normalDice");
		dice.throwDice();
		dice.reset();
		assertEquals("After Reset should be not thrown", dice.isThrown(), false);
	}

}
