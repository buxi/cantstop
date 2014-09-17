package de.buxi.cantstop.spielobjekte;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class NormalDiceTest extends SpringLoaderSuperClass {
	
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
	public void testThrow() throws DiceNotThrownException {
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
