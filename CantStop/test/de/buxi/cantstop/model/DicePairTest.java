package de.buxi.cantstop.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import de.buxi.cantstop.model.Dice;
import de.buxi.cantstop.model.DiceFactory;
import de.buxi.cantstop.model.DiceNotThrownException;

public class DicePairTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testEqualsObject() throws DiceNotThrownException {
		Dice dice1 = DiceFactory.createNormalDiceStatic(6);
		Dice dice2 = DiceFactory.createNormalDiceStatic(6);
		
		assertEquals("unthrown dices are equals", dice1, dice2);
		dice1.throwDice();
		do {
			dice2.throwDice();
		} while (dice2.getDiceValue() != dice1.getDiceValue());
		assertEquals("with same sides, values and thrown status they are same", dice1, dice2);
		
		do {
			dice2.throwDice();
		} while (dice2.getDiceValue() == dice1.getDiceValue());
		assertNotEquals("with different values they are different", dice1, dice2);
		
		
	}
}
