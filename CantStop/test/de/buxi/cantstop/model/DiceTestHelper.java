/**
 * 
 */
package de.buxi.cantstop.model;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.buxi.cantstop.model.Dice;
import de.buxi.cantstop.model.DiceNotThrownException;
import de.buxi.cantstop.model.DicePair;
import de.buxi.cantstop.model.TwoDicesPair;

/**
 * @author buxi
 *
 */
public class DiceTestHelper {
	/**
	 * @param diceNumber how much preset NormalDice should be generated
	 * @return List of generated NormalDices 
	 * @throws InvalidTestParametersException 
	 * @throws DiceNotThrownException 
	 */
	public static List<Dice> generateXpreSetNormalDice(List<Integer> valuee) throws InvalidTestParametersException {
		if (valuee == null) {
			throw new InvalidTestParametersException();
		}
		List<Dice> dices = new ArrayList<Dice>(valuee.size());

		for (int i = 0; i<valuee.size(); i++ ) {
			// create a MockObjekt with values
			Dice mockDice = mock(Dice.class);
			when(mockDice.getDiceValue()).thenReturn(valuee.get(i));
			when(mockDice.isThrown()).thenReturn(true);
			when(mockDice.toString()).thenReturn(valuee.get(i).toString());
			dices.add(mockDice);
		}

		return dices;
	}

	/**
	 *	possible pairs: 2	Sum
	 *			3 5 : 3 6 -> 8 9
	 *		    3 3 : 5 6 -> 6 11
 	 *  controllString : 68911
	 * @param pairs DicePairs
	 * @return ControllString
	 * @throws DiceNotThrownException
	 */
	public static String generatePairControllString(List<TwoDicesPair> pairs) throws DiceNotThrownException {
		List<Integer> paarSumn = new ArrayList<Integer>();
		
		for (TwoDicesPair twoDicePair : pairs) {
			DicePair dicePair1 = twoDicePair.getFirstPair();
			DicePair dicePair2 = twoDicePair.getSecondPair();
			paarSumn.add(dicePair1.getSum());
			paarSumn.add(dicePair2.getSum());
		}
		Collections.sort(paarSumn);
		StringBuilder result = new StringBuilder();
		for (Integer summe : paarSumn) {
			result.append(summe.toString());	
		}
		return result.toString();
	}
}
