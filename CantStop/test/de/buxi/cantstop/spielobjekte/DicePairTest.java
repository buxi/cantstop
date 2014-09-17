package de.buxi.cantstop.spielobjekte;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class DicePairTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testEqualsObject() throws DiceNotThrownException {
		Dice dice1 = DiceFactory.createNormalDiceStatic(6);
		Dice dice2 = DiceFactory.createNormalDiceStatic(6);
		
		assertNotEquals("unthrown dices are different", dice1, dice2);
		dice1.throwDice();
		do {
			dice2.throwDice();
		} while (dice2.getDiceValue() != dice1.getDiceValue());
		
		assertNotEquals("with same sites, value and thrown status they are same", dice1, dice2);
		
		DicePair paar1 = new DicePair(dice1, dice2);
		fail("not implemented yet");
	}

}
