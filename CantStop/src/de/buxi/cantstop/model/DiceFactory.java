package de.buxi.cantstop.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @author buxi
 * Factory object to generate different dice with different side number
 */
public class DiceFactory {
	/**
	 * @param sides how much side has the dice
	 * @return Dice with side sides
	 */
	public List<Dice> createNormalDiceSet(int sides, int number) {
		List<Dice> diceSet = new ArrayList<>(number);
		for (int i = 0; i < number; i++) {
			diceSet.add(new DiceImpl(sides));
		}
		return diceSet;
	}
	
	public Dice createNormalDice(int sides) {
		return new DiceImpl(sides);
	}
	
	public static Dice createNormalDiceStatic(int sides) {
		return new DiceImpl(sides);
	}
}
