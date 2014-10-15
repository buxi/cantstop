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

import de.buxi.cantstop.model.Board;
import de.buxi.cantstop.model.Climber;
import de.buxi.cantstop.model.Color;
import de.buxi.cantstop.model.Dice;
import de.buxi.cantstop.model.DiceManager;
import de.buxi.cantstop.model.DiceNotThrownException;
import de.buxi.cantstop.model.GameController;
import de.buxi.cantstop.model.InvalidWayNumberException;
import de.buxi.cantstop.model.NullClimberException;
import de.buxi.cantstop.model.PairChoiceInfo;
import de.buxi.cantstop.model.RopePointInvalidUsageException;
import de.buxi.cantstop.model.TwoDicesPair;

/**
 * @author buxi
 * Test methods for {@link de.buxi.cantstop.model.GameController#addWaehlbarInformation}.
 *  
 *  HxM  : Hut x marked
 *  HxBS : Climber in the hut x
 *  WxM  : Way x marked
 *  WxBS : Climber on the Way x
 */
public class GameControllerPairsDekoratorTest extends SpringLoaderSuperClassModelTests{

	
	private class DiceValuesPairChoosableContainter {
		protected List<Integer> values;
		protected PairChoiceInfo expectedResult;
		/**
		 * @param values
		 * @param expectedResult
		 */
		public DiceValuesPairChoosableContainter(List<Integer> valuee,
				PairChoiceInfo expectedeErgebnis) {
			super();
			this.values = valuee;
			this.expectedResult = expectedeErgebnis;
		}
		
	}
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testNOTCHOOSABLE_H2M_H3M_H4M_1_1_1_2() throws InvalidWayNumberException, RopePointInvalidUsageException, NullClimberException, InvalidTestParametersException, DiceNotThrownException {
		GameController gameController = (GameController)ac.getBean("testGameController");
		gameController.determinePlayerOrderStandard();
		gameController.determineFirstPlayer();

		Board board = gameController.getBoard();
		Collection<Integer> wayNumberList = new ArrayList<Integer>(Arrays.asList(2,3,4));
		//Player has 3 BS
		gameController.getActualPlayer().addClimbers(new ArrayList<Climber>(Arrays.asList(new Climber(), new Climber(), new Climber())));
				
		// Mark the Huts with BLUE
		BoardTestHelper.markHuts(board, wayNumberList, Color.BLUE);
		List<Dice> dices = DiceTestHelper.generateXpreSetNormalDice(Arrays.asList(1,1,1,2));
		DiceManager diceManager = new DiceManager(dices);
		diceManager.throwAllDices();
		List<TwoDicesPair> paaren = diceManager.getAllPossiblePairs();
		List<TwoDicesPair> decoratedPairs = gameController.addChoosableInformation(paaren);
		assertEquals("Throw not choosable", PairChoiceInfo.NOTCHOOSABLE, decoratedPairs.get(0).getPairChoiceInfo());
	}
	
	@Test
	public void testNOTCHOOSABLE_W2BS_W3BS_W4BS_2_3_3_3() throws InvalidWayNumberException, RopePointInvalidUsageException, NullClimberException, InvalidTestParametersException, DiceNotThrownException {
		GameController gameController = (GameController)ac.getBean("testGameController");
		gameController.determinePlayerOrderStandard();
		gameController.determineFirstPlayer();

		Board board = gameController.getBoard();
		Collection<Integer> wayNumberList = new ArrayList<Integer>(Arrays.asList(2,3,4));
		
		BoardTestHelper.placeClimbersOnTheWayAllNextToTheHut(board, wayNumberList);
		List<Dice> dices = DiceTestHelper.generateXpreSetNormalDice(Arrays.asList(2,3,3,3));
		DiceManager diceManager = new DiceManager(dices);
		diceManager.throwAllDices();
		List<TwoDicesPair> paaren = diceManager.getAllPossiblePairs();
		List<TwoDicesPair> decoratedPairs = gameController.addChoosableInformation(paaren);
		assertEquals("Throw not choosable", PairChoiceInfo.NOTCHOOSABLE, decoratedPairs.get(0).getPairChoiceInfo());
	}
	
	@Test
	public void testNOTCHOOSABLE_H2BS_W3BS_W4BS_1_1_1_2() throws InvalidWayNumberException, RopePointInvalidUsageException, NullClimberException, InvalidTestParametersException, DiceNotThrownException {
		GameController gameController = (GameController)ac.getBean("testGameController");
		gameController.determinePlayerOrderStandard();
		gameController.determineFirstPlayer();

		Board board = gameController.getBoard();
		BoardTestHelper.placeClimbersOnTheWayAllNextToTheHut(board, new ArrayList<Integer>(Arrays.asList(4,5)));
		BoardTestHelper.markHuts(board, new ArrayList<Integer>(Arrays.asList(2)), Color.BLUE);
		
		List<Dice> dices = DiceTestHelper.generateXpreSetNormalDice(Arrays.asList(1,1,1,2));
		DiceManager diceManager = new DiceManager(dices);
		diceManager.throwAllDices();
		List<TwoDicesPair> paaren = diceManager.getAllPossiblePairs();
		List<TwoDicesPair> decoratedPairs = gameController.addChoosableInformation(paaren);
		assertEquals("Throw not choosable", PairChoiceInfo.NOTCHOOSABLE, decoratedPairs.get(0).getPairChoiceInfo());
	}
	
	@Test
	public void testWITHWAYINFO_W2BS_W3BS_2_2_2_3() throws InvalidWayNumberException, RopePointInvalidUsageException, NullClimberException, InvalidTestParametersException, DiceNotThrownException {
		GameController gameController = (GameController)ac.getBean("testGameController");
		gameController.determinePlayerOrderStandard();
		gameController.determineFirstPlayer();

		Board board = gameController.getBoard();
		BoardTestHelper.placeClimbersOnTheWayAllNextToTheHut(board, new ArrayList<Integer>(Arrays.asList(2,3)));
		//Player have 1 BS
		gameController.getActualPlayer().addClimbers(new ArrayList<Climber>(Arrays.asList(new Climber())));
				
		List<Dice> dices = DiceTestHelper.generateXpreSetNormalDice(Arrays.asList(2,2,2,3));
		DiceManager diceManager = new DiceManager(dices);
		diceManager.throwAllDices();
		List<TwoDicesPair> paaren = diceManager.getAllPossiblePairs();
		List<TwoDicesPair> decoratedPairs = gameController.addChoosableInformation(paaren);
		assertEquals( PairChoiceInfo.WITHWAYINFO, decoratedPairs.get(0).getPairChoiceInfo());
	}
	
	/**
	 * speciel case,if the same Waynumber is and 3. Climber is in hand
	 */
	@Test
	public void testCHOOSABLE_W2BS_W3BS_2_2_1_3() throws InvalidWayNumberException, RopePointInvalidUsageException, NullClimberException, InvalidTestParametersException, DiceNotThrownException { GameController gameController = (GameController)ac.getBean("testGameController");
		Board board = gameController.getBoard();
		gameController.determinePlayerOrderStandard();
		gameController.determineFirstPlayer();

		BoardTestHelper.placeClimbersOnTheWayAllNextToTheHut(board, new ArrayList<Integer>(Arrays.asList(2,3)));
		//Player have 1 BS
		gameController.getActualPlayer().addClimbers(new ArrayList<Climber>(Arrays.asList(new Climber())));
				
		List<Dice> dices = DiceTestHelper.generateXpreSetNormalDice(Arrays.asList(2,2,1,3));
		DiceManager diceManager = new DiceManager(dices);
		diceManager.throwAllDices();
		List<TwoDicesPair> paaren = diceManager.getAllPossiblePairs();
		List<TwoDicesPair> decoratedPairs = gameController.addChoosableInformation(paaren);
		assertEquals( PairChoiceInfo.CHOOSABLE, decoratedPairs.get(0).getPairChoiceInfo());
	}
	
	@Test
	public void testIsThereChoosablePairs_W2BS_W3BS_W4BS_2_3_4_5() throws InvalidWayNumberException, RopePointInvalidUsageException, NullClimberException, InvalidTestParametersException, DiceNotThrownException {
		GameController gameController = (GameController)ac.getBean("testGameController");
		gameController.determinePlayerOrderStandard();
		gameController.determineFirstPlayer();

		Board board = gameController.getBoard();
		BoardTestHelper.placeClimbersOnTheWayAllNextToTheHut(board, new ArrayList<Integer>(Arrays.asList(2,3,4)));
				
		List<Dice> dices = DiceTestHelper.generateXpreSetNormalDice(Arrays.asList(2,3,4,5));
		DiceManager diceManager = new DiceManager(dices);
		diceManager.throwAllDices();
		List<TwoDicesPair> paaren = diceManager.getAllPossiblePairs();
		List<TwoDicesPair> decoratedPairs = gameController.addChoosableInformation(paaren);
		assertFalse("keine choosablee Pair", gameController.isTherePossiblePair(decoratedPairs));
	}
	
	@Test
	public void testIsThereChoosablePairs() throws InvalidWayNumberException, RopePointInvalidUsageException, NullClimberException, InvalidTestParametersException, DiceNotThrownException {
		GameController gameController = (GameController)ac.getBean("testGameController");
		gameController.determinePlayerOrderStandard();
		gameController.determineFirstPlayer();

		Board board = gameController.getBoard();
		BoardTestHelper.placeClimbersOnTheWayAllNextToTheHut(board, new ArrayList<Integer>(Arrays.asList(2,3)));
		//Player have 1 BS
		gameController.getActualPlayer().addClimbers(new ArrayList<Climber>(Arrays.asList(new Climber())));
				
		List<Dice> dices = DiceTestHelper.generateXpreSetNormalDice(Arrays.asList(2,2,2,3));
		DiceManager diceManager = new DiceManager(dices);
		diceManager.throwAllDices();
		List<TwoDicesPair> paaren = diceManager.getAllPossiblePairs();
		List<TwoDicesPair> decoratedPairs = gameController.addChoosableInformation(paaren);
		assertEquals( PairChoiceInfo.WITHWAYINFO, decoratedPairs.get(0).getPairChoiceInfo());
		assertTrue(gameController.isTherePossiblePair(decoratedPairs));
	}
	
	
	/**
	 * @param gameController
	 * @param testPatternList
	 * @throws InvalidTestParametersException
	 * @throws DiceNotThrownException
	 * @throws InvalidWayNumberException
	 */
	protected void checkTestPattern(GameController gameController,
			List<DiceValuesPairChoosableContainter> testPatternList)
			throws InvalidTestParametersException,
			DiceNotThrownException, InvalidWayNumberException {
		for (DiceValuesPairChoosableContainter testPattern : testPatternList) {
			List<Dice> dices = DiceTestHelper.generateXpreSetNormalDice(testPattern.values);
			DiceManager diceManager = new DiceManager(dices);
			diceManager.throwAllDices();	
			List<TwoDicesPair> paaren = diceManager.getAllPossiblePairs();
			List<TwoDicesPair> decoratedPairs = gameController.addChoosableInformation(paaren);
			assertEquals("Throw("+testPattern.values+") not "+testPattern.expectedResult, testPattern.expectedResult, decoratedPairs.get(0).getPairChoiceInfo());
		}
	}
	
	@Test
	public void test_H2M_H3M_H6BS_H7BS_MassTest() throws InvalidWayNumberException, RopePointInvalidUsageException, NullClimberException, InvalidTestParametersException, DiceNotThrownException {
		GameController gameController = (GameController)ac.getBean("testGameController");
		gameController.determinePlayerOrderStandard();
		gameController.determineFirstPlayer();

		Board board = gameController.getBoard();
		BoardTestHelper.markHuts(board, new ArrayList<Integer>(Arrays.asList(2,3)), Color.BLUE);
		BoardTestHelper.placeClimbersOnTheWayAllInHut(board, new ArrayList<Integer>(Arrays.asList(6,7)));
		//Player have 1 BS
		gameController.getActualPlayer().addClimbers(new ArrayList<Climber>(Arrays.asList(new Climber())));
		
		List<DiceValuesPairChoosableContainter> testPatternList = new ArrayList<GameControllerPairsDekoratorTest.DiceValuesPairChoosableContainter>();
		testPatternList.add(new DiceValuesPairChoosableContainter(Arrays.asList(1,1,1,2), PairChoiceInfo.NOTCHOOSABLE));
		testPatternList.add(new DiceValuesPairChoosableContainter(Arrays.asList(1,2,2,2), PairChoiceInfo.CHOOSABLE));
		testPatternList.add(new DiceValuesPairChoosableContainter(Arrays.asList(2,2,2,3), PairChoiceInfo.WITHWAYINFO));
		testPatternList.add(new DiceValuesPairChoosableContainter(Arrays.asList(2,3,3,3), PairChoiceInfo.CHOOSABLE));
		testPatternList.add(new DiceValuesPairChoosableContainter(Arrays.asList(3,3,3,4), PairChoiceInfo.NOTCHOOSABLE));
		testPatternList.add(new DiceValuesPairChoosableContainter(Arrays.asList(3,4,4,4), PairChoiceInfo.CHOOSABLE));
		testPatternList.add(new DiceValuesPairChoosableContainter(Arrays.asList(4,4,4,5), PairChoiceInfo.WITHWAYINFO));
		testPatternList.add(new DiceValuesPairChoosableContainter(Arrays.asList(4,5,5,5), PairChoiceInfo.WITHWAYINFO));
		testPatternList.add(new DiceValuesPairChoosableContainter(Arrays.asList(5,5,5,6), PairChoiceInfo.WITHWAYINFO));
		testPatternList.add(new DiceValuesPairChoosableContainter(Arrays.asList(5,6,6,6), PairChoiceInfo.WITHWAYINFO));
		checkTestPattern(gameController, testPatternList);
	}
	
	@Test
	public void test_H2M_H3M_H6BS_W7BS_MassTest() throws InvalidWayNumberException, RopePointInvalidUsageException, NullClimberException, InvalidTestParametersException, DiceNotThrownException {
		GameController gameController = (GameController)ac.getBean("testGameController");
		gameController.determinePlayerOrderStandard();
		gameController.determineFirstPlayer();

		Board board = gameController.getBoard();
		BoardTestHelper.markHuts(board, new ArrayList<Integer>(Arrays.asList(2,3)), Color.BLUE);
		BoardTestHelper.placeClimbersOnTheWayAllInHut(board, new ArrayList<Integer>(Arrays.asList(6)));
		BoardTestHelper.placeClimbersOnTheWayAllNextToTheHut(board, new ArrayList<Integer>(Arrays.asList(7)));
		//Player has 1 BS
		gameController.getActualPlayer().addClimbers(new ArrayList<Climber>(Arrays.asList(new Climber())));
				
		List<DiceValuesPairChoosableContainter> testPatternList = new ArrayList<GameControllerPairsDekoratorTest.DiceValuesPairChoosableContainter>();
		testPatternList.add(new DiceValuesPairChoosableContainter(Arrays.asList(1,1,1,2), PairChoiceInfo.NOTCHOOSABLE));
		testPatternList.add(new DiceValuesPairChoosableContainter(Arrays.asList(1,2,2,2), PairChoiceInfo.CHOOSABLE));
		testPatternList.add(new DiceValuesPairChoosableContainter(Arrays.asList(2,2,2,3), PairChoiceInfo.WITHWAYINFO));
		testPatternList.add(new DiceValuesPairChoosableContainter(Arrays.asList(2,3,3,3), PairChoiceInfo.CHOOSABLE));
		testPatternList.add(new DiceValuesPairChoosableContainter(Arrays.asList(3,3,3,4), PairChoiceInfo.CHOOSABLE));
		testPatternList.add(new DiceValuesPairChoosableContainter(Arrays.asList(3,4,4,4), PairChoiceInfo.CHOOSABLE));
		testPatternList.add(new DiceValuesPairChoosableContainter(Arrays.asList(4,4,4,5), PairChoiceInfo.WITHWAYINFO));
		testPatternList.add(new DiceValuesPairChoosableContainter(Arrays.asList(4,5,5,5), PairChoiceInfo.WITHWAYINFO));
		testPatternList.add(new DiceValuesPairChoosableContainter(Arrays.asList(5,5,5,6), PairChoiceInfo.WITHWAYINFO));
		testPatternList.add(new DiceValuesPairChoosableContainter(Arrays.asList(5,6,6,6), PairChoiceInfo.WITHWAYINFO));
		checkTestPattern(gameController, testPatternList);
	}
	
	@Test
	public void test_EmptyBoard_MassTest() throws InvalidWayNumberException, RopePointInvalidUsageException, NullClimberException, InvalidTestParametersException, DiceNotThrownException {
		GameController gameController = (GameController)ac.getBean("testGameController");
		gameController.determinePlayerOrderStandard();
		gameController.determineFirstPlayer();

		//Player has 3 BS
		gameController.getActualPlayer().addClimbers(new ArrayList<Climber>(Arrays.asList(new Climber(), new Climber(), new Climber())));
				
		List<DiceValuesPairChoosableContainter> testPatternList = new ArrayList<GameControllerPairsDekoratorTest.DiceValuesPairChoosableContainter>();
		testPatternList.add(new DiceValuesPairChoosableContainter(Arrays.asList(1,1,1,2), PairChoiceInfo.CHOOSABLE));
		testPatternList.add(new DiceValuesPairChoosableContainter(Arrays.asList(1,2,2,2), PairChoiceInfo.CHOOSABLE));
		testPatternList.add(new DiceValuesPairChoosableContainter(Arrays.asList(2,2,2,3), PairChoiceInfo.CHOOSABLE));
		testPatternList.add(new DiceValuesPairChoosableContainter(Arrays.asList(2,3,3,3), PairChoiceInfo.CHOOSABLE));
		testPatternList.add(new DiceValuesPairChoosableContainter(Arrays.asList(3,3,3,4), PairChoiceInfo.CHOOSABLE));
		testPatternList.add(new DiceValuesPairChoosableContainter(Arrays.asList(3,4,4,4), PairChoiceInfo.CHOOSABLE));
		testPatternList.add(new DiceValuesPairChoosableContainter(Arrays.asList(4,4,4,5), PairChoiceInfo.CHOOSABLE));
		testPatternList.add(new DiceValuesPairChoosableContainter(Arrays.asList(4,5,5,5), PairChoiceInfo.CHOOSABLE));
		testPatternList.add(new DiceValuesPairChoosableContainter(Arrays.asList(5,5,5,6), PairChoiceInfo.CHOOSABLE));
		testPatternList.add(new DiceValuesPairChoosableContainter(Arrays.asList(5,6,6,6), PairChoiceInfo.CHOOSABLE));
		checkTestPattern(gameController, testPatternList);
	}

	@Test
	public void test_OneBS_OnTheBoard_W2BS_MassTest() throws InvalidWayNumberException, RopePointInvalidUsageException, NullClimberException, InvalidTestParametersException, DiceNotThrownException {
		GameController gameController = (GameController)ac.getBean("testGameController");
		gameController.determinePlayerOrderStandard();
		gameController.determineFirstPlayer();

		Board board = gameController.getBoard();
		BoardTestHelper.placeClimbersOnTheWayAllNextToTheHut(board, new ArrayList<Integer>(Arrays.asList(2)));
		//Player has 2 BS
		gameController.getActualPlayer().addClimbers(new ArrayList<Climber>(Arrays.asList(new Climber(), new Climber())));
				
		List<DiceValuesPairChoosableContainter> testPatternList = new ArrayList<GameControllerPairsDekoratorTest.DiceValuesPairChoosableContainter>();
		testPatternList.add(new DiceValuesPairChoosableContainter(Arrays.asList(1,1,1,2), PairChoiceInfo.CHOOSABLE));
		testPatternList.add(new DiceValuesPairChoosableContainter(Arrays.asList(1,2,2,2), PairChoiceInfo.CHOOSABLE));
		testPatternList.add(new DiceValuesPairChoosableContainter(Arrays.asList(2,2,2,3), PairChoiceInfo.CHOOSABLE));
		testPatternList.add(new DiceValuesPairChoosableContainter(Arrays.asList(2,3,3,3), PairChoiceInfo.CHOOSABLE));
		testPatternList.add(new DiceValuesPairChoosableContainter(Arrays.asList(3,3,3,4), PairChoiceInfo.CHOOSABLE));
		testPatternList.add(new DiceValuesPairChoosableContainter(Arrays.asList(3,4,4,4), PairChoiceInfo.CHOOSABLE));
		testPatternList.add(new DiceValuesPairChoosableContainter(Arrays.asList(4,4,4,5), PairChoiceInfo.CHOOSABLE));
		testPatternList.add(new DiceValuesPairChoosableContainter(Arrays.asList(4,5,5,5), PairChoiceInfo.CHOOSABLE));
		testPatternList.add(new DiceValuesPairChoosableContainter(Arrays.asList(5,5,5,6), PairChoiceInfo.CHOOSABLE));
		testPatternList.add(new DiceValuesPairChoosableContainter(Arrays.asList(5,6,6,6), PairChoiceInfo.CHOOSABLE));
		checkTestPattern(gameController, testPatternList);
	}

	@Test
	public void test_2BS_OnTheBoard_W2BS_W3BS_MassTest() throws InvalidWayNumberException, RopePointInvalidUsageException, NullClimberException, InvalidTestParametersException, DiceNotThrownException {
		GameController gameController = (GameController)ac.getBean("testGameController");
		gameController.determinePlayerOrderStandard();
		gameController.determineFirstPlayer();
		Board board = gameController.getBoard();
		BoardTestHelper.placeClimbersOnTheWayAllNextToTheHut(board, new ArrayList<Integer>(Arrays.asList(2,3)));
		//Player have 1 BS 
		gameController.getActualPlayer().addClimbers(new ArrayList<Climber>(Arrays.asList( new Climber())));
				
		List<DiceValuesPairChoosableContainter> testPatternList = new ArrayList<GameControllerPairsDekoratorTest.DiceValuesPairChoosableContainter>();
		// Throw 2 x
		testPatternList.add(new DiceValuesPairChoosableContainter(Arrays.asList(1,1,1,2), PairChoiceInfo.CHOOSABLE));
		testPatternList.add(new DiceValuesPairChoosableContainter(Arrays.asList(1,1,3,2), PairChoiceInfo.CHOOSABLE));
		testPatternList.add(new DiceValuesPairChoosableContainter(Arrays.asList(1,1,4,2), PairChoiceInfo.CHOOSABLE));
		// Throw 2 3
		testPatternList.add(new DiceValuesPairChoosableContainter(Arrays.asList(1,2,2,2), PairChoiceInfo.CHOOSABLE));
		// Throw 3 x
		testPatternList.add(new DiceValuesPairChoosableContainter(Arrays.asList(2,2,2,1), PairChoiceInfo.CHOOSABLE));
		testPatternList.add(new DiceValuesPairChoosableContainter(Arrays.asList(1,2,4,2), PairChoiceInfo.CHOOSABLE));
		testPatternList.add(new DiceValuesPairChoosableContainter(Arrays.asList(1,2,5,2), PairChoiceInfo.CHOOSABLE));
		//Throw: other
		testPatternList.add(new DiceValuesPairChoosableContainter(Arrays.asList(2,2,2,3), PairChoiceInfo.WITHWAYINFO));
		testPatternList.add(new DiceValuesPairChoosableContainter(Arrays.asList(2,3,3,3), PairChoiceInfo.WITHWAYINFO));
		testPatternList.add(new DiceValuesPairChoosableContainter(Arrays.asList(3,3,3,4), PairChoiceInfo.WITHWAYINFO));
		testPatternList.add(new DiceValuesPairChoosableContainter(Arrays.asList(3,4,4,4), PairChoiceInfo.WITHWAYINFO));
		testPatternList.add(new DiceValuesPairChoosableContainter(Arrays.asList(4,4,4,5), PairChoiceInfo.WITHWAYINFO));
		testPatternList.add(new DiceValuesPairChoosableContainter(Arrays.asList(4,5,5,5), PairChoiceInfo.WITHWAYINFO));
		testPatternList.add(new DiceValuesPairChoosableContainter(Arrays.asList(5,5,5,6), PairChoiceInfo.WITHWAYINFO));
		testPatternList.add(new DiceValuesPairChoosableContainter(Arrays.asList(5,6,6,6), PairChoiceInfo.WITHWAYINFO));
		checkTestPattern(gameController, testPatternList);
	}

	@Test
	public void test_3BS_OnTheBoard_W2BS_W3BS_W4BS_MassTest() throws InvalidWayNumberException, RopePointInvalidUsageException, NullClimberException, InvalidTestParametersException, DiceNotThrownException {
		GameController gameController = (GameController)ac.getBean("testGameController");
		gameController.determinePlayerOrderStandard();
		gameController.determineFirstPlayer();

		gameController.determinePlayerOrderStandard();
		gameController.determineFirstPlayer();

		Board board = gameController.getBoard();
		BoardTestHelper.placeClimbersOnTheWayAllNextToTheHut(board, new ArrayList<Integer>(Arrays.asList(2,3,4)));
				
		List<DiceValuesPairChoosableContainter> testPatternList = new ArrayList<GameControllerPairsDekoratorTest.DiceValuesPairChoosableContainter>();
		// Throw 2 x
		testPatternList.add(new DiceValuesPairChoosableContainter(Arrays.asList(1,1,1,2), PairChoiceInfo.CHOOSABLE));
		testPatternList.add(new DiceValuesPairChoosableContainter(Arrays.asList(1,1,3,2), PairChoiceInfo.CHOOSABLE));
		testPatternList.add(new DiceValuesPairChoosableContainter(Arrays.asList(1,1,4,2), PairChoiceInfo.CHOOSABLE));
		// Throw 2 3
		testPatternList.add(new DiceValuesPairChoosableContainter(Arrays.asList(1,2,2,2), PairChoiceInfo.CHOOSABLE));
		// Throw 3 x
		testPatternList.add(new DiceValuesPairChoosableContainter(Arrays.asList(2,2,2,1), PairChoiceInfo.CHOOSABLE));
		testPatternList.add(new DiceValuesPairChoosableContainter(Arrays.asList(1,2,4,2), PairChoiceInfo.CHOOSABLE));
		testPatternList.add(new DiceValuesPairChoosableContainter(Arrays.asList(1,2,5,2), PairChoiceInfo.CHOOSABLE));
		// Throw 4 x
		testPatternList.add(new DiceValuesPairChoosableContainter(Arrays.asList(2,2,6,1), PairChoiceInfo.CHOOSABLE));
		testPatternList.add(new DiceValuesPairChoosableContainter(Arrays.asList(2,2,4,6), PairChoiceInfo.CHOOSABLE));
		testPatternList.add(new DiceValuesPairChoosableContainter(Arrays.asList(2,2,6,6), PairChoiceInfo.CHOOSABLE));
		testPatternList.add(new DiceValuesPairChoosableContainter(Arrays.asList(2,2,2,3), PairChoiceInfo.CHOOSABLE));
				
		//Throw: other
		testPatternList.add(new DiceValuesPairChoosableContainter(Arrays.asList(2,3,3,3), PairChoiceInfo.NOTCHOOSABLE));
		testPatternList.add(new DiceValuesPairChoosableContainter(Arrays.asList(3,3,3,4), PairChoiceInfo.NOTCHOOSABLE));
		testPatternList.add(new DiceValuesPairChoosableContainter(Arrays.asList(3,4,4,4), PairChoiceInfo.NOTCHOOSABLE));
		testPatternList.add(new DiceValuesPairChoosableContainter(Arrays.asList(4,4,4,5), PairChoiceInfo.NOTCHOOSABLE));
		testPatternList.add(new DiceValuesPairChoosableContainter(Arrays.asList(4,5,5,5), PairChoiceInfo.NOTCHOOSABLE));
		testPatternList.add(new DiceValuesPairChoosableContainter(Arrays.asList(5,5,5,6), PairChoiceInfo.NOTCHOOSABLE));
		testPatternList.add(new DiceValuesPairChoosableContainter(Arrays.asList(5,6,6,6), PairChoiceInfo.NOTCHOOSABLE));
		checkTestPattern(gameController, testPatternList);
	}
}
