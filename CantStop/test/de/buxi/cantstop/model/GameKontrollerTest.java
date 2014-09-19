package de.buxi.cantstop.model;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import de.buxi.cantstop.model.Color;
import de.buxi.cantstop.model.Dice;
import de.buxi.cantstop.model.DiceNotGivenException;
import de.buxi.cantstop.model.DiceNotThrownException;
import de.buxi.cantstop.model.DicePair;
import de.buxi.cantstop.model.GameController;
import de.buxi.cantstop.model.GameFactory;
import de.buxi.cantstop.model.GameState;
import de.buxi.cantstop.model.InvalidClimberMovementException;
import de.buxi.cantstop.model.InvalidGameStateException;
import de.buxi.cantstop.model.InvalidPairsException;
import de.buxi.cantstop.model.InvalidWayNumberException;
import de.buxi.cantstop.model.Marker;
import de.buxi.cantstop.model.NoClimberOnWayException;
import de.buxi.cantstop.model.NoMarkerIsAvailableException;
import de.buxi.cantstop.model.NotAvailableClimberException;
import de.buxi.cantstop.model.NullClimberException;
import de.buxi.cantstop.model.Player;
import de.buxi.cantstop.model.PlayerNotFoundException;
import de.buxi.cantstop.model.RopePointInvalidUsageException;
import de.buxi.cantstop.model.TwoDicesPair;
import de.buxi.cantstop.model.Way;

public class GameKontrollerTest extends SpringLoaderSuperClass{

	@Before
	public void setUp() throws Exception {
	}

	@Test(expected=PlayerNotFoundException.class)
	public void testDistributeMarkersNoPlayerWithColor() {
		GameController gameController = (GameController)ac.getBean("testGameController");
		// distribute Markers
		Map<Color, Collection<Marker>> playerMarkers = new HashMap<Color, Collection<Marker>>();
		playerMarkers.put(Color.GREEN, GameFactory.createMarkersStatic(3, Color.GREEN));
		gameController.distributeMarkers(playerMarkers);
	}

	@Test()
	public void testDistributeMarkersPositiveWith2Colors() {
		GameController gameController = (GameController)ac.getBean("testGameController");
		// distribute Markers
		Map<Color, Collection<Marker>> playerMarkers = new HashMap<Color, Collection<Marker>>();
		playerMarkers.put(Color.BLUE, GameFactory.createMarkersStatic(3, Color.BLUE));
		playerMarkers.put(Color.RED, GameFactory.createMarkersStatic(3, Color.RED));
		gameController.distributeMarkers(playerMarkers);
		
		Player playerBLAU = gameController.getPlayer(Color.BLUE);
		assertEquals("BLUE Player should have 3 Markers", 13, playerBLAU.getMarkers().size());
		Player playerRED = gameController.getPlayer(Color.RED);
		assertEquals("RED Player should hvae 3 Markers", 13, playerRED.getMarkers().size());
	}

	@Test
	public void testActualPlayer() {
		GameController gameController = (GameController)ac.getBean("testGameController");
		Player aktuellePlayer = gameController.getActualPlayer();
		assertEquals("actual Player should be actualPlayerNumber have", gameController.getActualPlayerNumber(), aktuellePlayer.getOrder());
	}

	@Test
	public void testNextPlayer() {
		GameController gameController = (GameController)ac.getBean("testGameController");
		gameController.doGameStarten();
		gameController.doStartGameRound(); // distribute Climbers too 
		
		Player aktuellePlayer = gameController.getActualPlayer();
		assertEquals("actual Player should be actualPlayerNumber have", gameController.getActualPlayerNumber(), aktuellePlayer.getOrder());
		
		if (Color.BLUE.equals(aktuellePlayer.getColor())) {
			gameController.nextPlayer();
			assertEquals("next Player should be RED", Color.RED, gameController.getActualPlayer().getColor());
			
		}
		if (Color.RED.equals(aktuellePlayer.getColor())) {
			gameController.nextPlayer();
			assertEquals("next Player should be BLUE", Color.BLUE, gameController.getActualPlayer().getColor());
		}
		assertEquals("previous Player should have no Climbers", 0, aktuellePlayer.getClimbers().size());
		assertEquals("actual Player should have all 3 Climbers", 3, gameController.getActualPlayer().getClimbers().size());
		assertEquals("actual Player should have the DiceManager", gameController.getActualPlayer(), gameController.getDiceManager().getOwner());
	}
	
	@Test
	public void testDistributeClimber() {
		GameController gameController = (GameController)ac.getBean("testGameController");
		gameController.distributeFreeClimbers();
		assertEquals("actual Player should be have all 3 Climbers", 3, gameController.getActualPlayer().getClimbers().size());
		assertEquals("gameController should have 0 Climber", 0, gameController.getClimbers().size());
	}

	@Test(expected=InvalidGameStateException.class)
	public void testStartGameRoundWithInvalidState() {
		GameController gameController = (GameController)ac.getBean("testGameController");
		gameController.distributeFreeClimbers();
		gameController.doStartGameRound();
	}

	@Test
	public void testStartGameRound() {
		GameController gameController = (GameController)ac.getBean("testGameController");
		gameController.doGameStarten();
		gameController.distributeFreeClimbers();
		gameController.doStartGameRound();
		assertEquals("actual Player should be have all 3 Climbers", 3, gameController.getActualPlayer().getClimbers().size());
		assertEquals("gameController should have 0 Climber", 0, gameController.getClimbers().size());
		assertEquals("INIT State", GameState.IN_ROUND, gameController.getGameStatus());
	}
	@Test(expected=InvalidGameStateException.class)
	public void testDoStartGameRoundInvalidState() {
		GameController gameController = (GameController)ac.getBean("testGameController");
		gameController.distributeFreeClimbers();
		gameController.doStartGameRound();
		assertEquals("actual Player should be have all 3 Climbers", 3, gameController.getActualPlayer().getClimbers().size());
		assertEquals("gameController should have 0 Climber", 0, gameController.getClimbers().size());
		assertEquals("IN_ROUND State expected", GameState.IN_ROUND, gameController.getGameStatus());
	}

	@Test(expected=InvalidGameStateException.class)
	public void testDoStartGameRoundWithInvalidState() {
		GameController gameController = (GameController)ac.getBean("testGameController");
		gameController.distributeFreeClimbers();
		gameController.doStartGameRound();
	}
	
	@Test
	public void testDoThrowPositiv() throws DiceNotThrownException, InvalidWayNumberException, NoMarkerIsAvailableException, RopePointInvalidUsageException, NoClimberOnWayException, InvalidClimberMovementException {
		GameController gameController = (GameController)ac.getBean("testGameController");
		gameController.doGameStarten();
		gameController.distributeFreeClimbers();
		gameController.doStartGameRound();
		gameController.doThrow();
		assertEquals("DICES_THROWN State expected", GameState.DICES_THROWN, gameController.getGameStatus());
		List<Dice> dices = gameController.getDiceManager().getDices();
		for (Dice dice : dices) {
			assertTrue("Dice must be thrown", dice.isThrown());
		}
		assertNotNull(gameController.getWrongPairs());
	}
	
	@Test(expected=InvalidGameStateException.class)
	public void testDoThrowInvalidState() throws DiceNotThrownException, InvalidWayNumberException, NoMarkerIsAvailableException, RopePointInvalidUsageException, NoClimberOnWayException, InvalidClimberMovementException {
		GameController gameController = (GameController)ac.getBean("testGameController");
		gameController.distributeFreeClimbers();
		gameController.doThrow();
	}
	
	@Test
	public void testDoThrowNegativ() throws DiceNotThrownException, InvalidWayNumberException, NoMarkerIsAvailableException, RopePointInvalidUsageException, NoClimberOnWayException, InvalidClimberMovementException, InvalidTestParametersException, NullClimberException, NotAvailableClimberException {
		GameController gameController = (GameController)ac.getBean("testGameController");
		gameController.doGameStarten();
		gameController.doStartGameRound();
		// fake the board
		//place Climber W2BS, W3BS
		gameController.testDoThrow(new HackedDiceManager(DiceTestHelper.generateXpreSetNormalDice(Arrays.asList(1,1,1,2))));
		gameController.doExecutePairs(gameController.getPossiblePairs().get(0), -1);
		//place Climber W4BS
		gameController.testDoThrow(new HackedDiceManager(DiceTestHelper.generateXpreSetNormalDice(Arrays.asList(2,2,2,3))));
		gameController.doExecutePairs(gameController.getPossiblePairs().get(0), 4);
		//wrong pairs -> Gameround finished 
		gameController.testDoThrow(new HackedDiceManager(DiceTestHelper.generateXpreSetNormalDice(Arrays.asList(6,6,6,6))));
		
		assertNotNull(gameController.getWrongPairs());
		assertEquals("IN_ROUND State expected", GameState.IN_ROUND, gameController.getGameStatus());
		assertNotNull("errorMessage should be", gameController.getErrorMessage());
	}

	@Test
	public void testDoGameRoundFinishedWaysNoOtherWahl() throws DiceNotGivenException, DiceNotThrownException, InvalidWayNumberException, NoMarkerIsAvailableException, RopePointInvalidUsageException, NoClimberOnWayException, InvalidClimberMovementException, InvalidTestParametersException, NotAvailableClimberException, NullClimberException {
		GameController gameController = (GameController)ac.getBean("testGameController");
		gameController.doGameStarten();
		gameController.doStartGameRound();
		Player oldPlayer = gameController.getActualPlayer();
		// fake the board
		//place Climber W2BS, W3BS
		gameController.testDoThrow(new HackedDiceManager(DiceTestHelper.generateXpreSetNormalDice(Arrays.asList(1,1,1,2))));
		gameController.doExecutePairs(gameController.getPossiblePairs().get(0), -1);
		//place Climber W4BS
		gameController.testDoThrow(new HackedDiceManager(DiceTestHelper.generateXpreSetNormalDice(Arrays.asList(2,2,2,3))));
		gameController.doExecutePairs(gameController.getPossiblePairs().get(0), 4);
		//wrong pairs -> Gameround finished 
		gameController.testDoThrow(new HackedDiceManager(DiceTestHelper.generateXpreSetNormalDice(Arrays.asList(6,6,6,6))));
		
		assertEquals("IN_ROUND State expected", GameState.IN_ROUND, gameController.getGameStatus());
		assertNotNull("errorMessage should be", gameController.getErrorMessage());
		Player actualPlayer = gameController.getActualPlayer();
		assertEquals("old Player should have no Climber", 0, oldPlayer.getClimbers().size());
		assertEquals("old Player should have all Markers", 10, oldPlayer.getMarkers().size());
		
		assertEquals("actual Player should have 3 Climber", 3, actualPlayer.getClimbers().size());
		assertEquals("actual Player should have all Markers", 10, actualPlayer.getMarkers().size());
	}
	
	@Test
	public void testDoGameRoundBeendenPlayerGemacht() throws DiceNotGivenException, DiceNotThrownException, InvalidWayNumberException, NoMarkerIsAvailableException, RopePointInvalidUsageException, NoClimberOnWayException, InvalidClimberMovementException, InvalidTestParametersException, NotAvailableClimberException, NullClimberException {
		GameController gameController = (GameController)ac.getBean("testGameController");
		gameController.doGameStarten();
		gameController.doStartGameRound();
		Player oldPlayer = gameController.getActualPlayer();
		// fake the board
		//place Climber W2BS, W3BS
		gameController.testDoThrow(new HackedDiceManager(DiceTestHelper.generateXpreSetNormalDice(Arrays.asList(1,1,1,2))));
		gameController.doExecutePairs(gameController.getPossiblePairs().get(0), -1);
		//place Climber W4BS
		gameController.testDoThrow(new HackedDiceManager(DiceTestHelper.generateXpreSetNormalDice(Arrays.asList(2,2,2,3))));
		gameController.doExecutePairs(gameController.getPossiblePairs().get(0), 4);
		gameController.doEndGameRound();
		
		assertEquals("IN_ROUND State expected", GameState.IN_ROUND, gameController.getGameStatus());
		assertNotNull("errorMessage should be", gameController.getErrorMessage());
		Player actualPlayer = gameController.getActualPlayer();
		assertNotEquals("actual Player should be the next", oldPlayer, actualPlayer );
		Way way2 = gameController.getBoard().getWayByNumber(2);
		assertEquals("way2 down should be marked with "+oldPlayer.getColor(), 0, way2.whereIsMarkerForColor(oldPlayer.getColor()));
		Way way3 = gameController.getBoard().getWayByNumber(3);
		assertEquals("way2 down should be marked with "+oldPlayer.getColor(), 0, way3.whereIsMarkerForColor(oldPlayer.getColor()));
		Way way4 = gameController.getBoard().getWayByNumber(4);
		assertEquals("way2 down should be marked with "+oldPlayer.getColor(), 0, way4.whereIsMarkerForColor(oldPlayer.getColor()));
		
		assertEquals("old Player should not have Climbers", 0, oldPlayer.getClimbers().size());
		assertEquals("old Player should have all Markers", 7, oldPlayer.getMarkers().size());
		
		assertEquals("actual Player should have 3 Climbers", 3, actualPlayer.getClimbers().size());
		assertEquals("actual Player should have all Markers", 10, actualPlayer.getMarkers().size());
	}
	
	@Test
	public void testDoGameRoundBeendenGameWON() throws DiceNotGivenException, DiceNotThrownException, InvalidWayNumberException, NoMarkerIsAvailableException, RopePointInvalidUsageException, NoClimberOnWayException, InvalidClimberMovementException, InvalidTestParametersException, NotAvailableClimberException, NullClimberException {
		GameController gameController = (GameController)ac.getBean("testGameController");
		gameController.doGameStarten();
		gameController.doStartGameRound();
		Player oldPlayer = gameController.getActualPlayer();
		// fake the board
		//place Climber W2BS, W3BS
		gameController.testDoThrow(new HackedDiceManager(DiceTestHelper.generateXpreSetNormalDice(Arrays.asList(1,1,1,2))));
		gameController.doExecutePairs(gameController.getPossiblePairs().get(0), -1);
		//place Climber W4BS
		gameController.testDoThrow(new HackedDiceManager(DiceTestHelper.generateXpreSetNormalDice(Arrays.asList(2,2,2,3))));
		gameController.doExecutePairs(gameController.getPossiblePairs().get(0), 4);

		// move W2BS, W3BS
		gameController.testDoThrow(new HackedDiceManager(DiceTestHelper.generateXpreSetNormalDice(Arrays.asList(1,1,1,2))));
		gameController.doExecutePairs(gameController.getPossiblePairs().get(0), -1);
		// move W4BS
		gameController.testDoThrow(new HackedDiceManager(DiceTestHelper.generateXpreSetNormalDice(Arrays.asList(2,2,2,3))));
		gameController.doExecutePairs(gameController.getPossiblePairs().get(0), -1);
		gameController.doEndGameRound();
		
		assertEquals("GAME_WIN State expected", GameState.GAME_WIN, gameController.getGameStatus());
		assertNotNull("errorMessage should be", gameController.getErrorMessage());
		Player actualPlayer = gameController.getActualPlayer();
		assertEquals("Player should remain", oldPlayer, actualPlayer );
		
		Way way2 = gameController.getBoard().getWayByNumber(2);
		assertTrue("way2 Hut should be marked  with "+oldPlayer.getColor(), way2.isBlockedByPlayer(oldPlayer.getColor()));
		Way way3 = gameController.getBoard().getWayByNumber(3);
		assertTrue("way3 Hut should be marked  with "+oldPlayer.getColor(), way3.isBlockedByPlayer(oldPlayer.getColor()));
		Way way4 = gameController.getBoard().getWayByNumber(4);
		assertTrue("way4 Hut should be marked  with "+oldPlayer.getColor(), way4.isBlockedByPlayer(oldPlayer.getColor()));
		
		assertEquals("old Player should have no Climber", 0, oldPlayer.getClimbers().size());
		assertEquals("old Player should have all Markers", 7, oldPlayer.getMarkers().size());
		assertEquals("GameController should have all Climber", 3, gameController.getClimbers().size());
		
	}

	
	@Test
	public void testDoGameStarten() {
		GameController gameController = (GameController)ac.getBean("testGameController");
		gameController.doGameStarten();
		assertEquals("First Player should be determined", GameController.DEFAULT_FIRST_PLAYER_NUM, gameController.getActualPlayerNumber());
		assertEquals("IN_GAME State", GameState.IN_GAME, gameController.getGameStatus());
	}

	@Test(expected=InvalidGameStateException.class)
	public void testDoGameStartenInvalidState() {
		GameController gameController = (GameController)ac.getBean("testGameController");
		gameController.doGameStarten();
		gameController.doGameStarten();
	}
	
	@Test
	public void testDoPairungInputCheckWithValidWaehlbarPairung() throws DiceNotGivenException, DiceNotThrownException, InvalidWayNumberException, NoMarkerIsAvailableException, RopePointInvalidUsageException, NoClimberOnWayException, InvalidClimberMovementException, InvalidTestParametersException, NotAvailableClimberException, NullClimberException {
		GameController gameController = (GameController)ac.getBean("testGameController");
		gameController.doGameStarten();
		gameController.doStartGameRound();
		// fake the board
		//place Climber W2BS, W3BS
		gameController.testDoThrow(new HackedDiceManager(DiceTestHelper.generateXpreSetNormalDice(Arrays.asList(1,1,1,2))));
		gameController.doExecutePairs(gameController.getPossiblePairs().get(0), -1);
	}
	
	@Test(expected=InvalidWayNumberException.class)
	public void testDoPairungInputCheckWithInValidWayNumber() throws DiceNotGivenException, DiceNotThrownException, InvalidWayNumberException, NoMarkerIsAvailableException, RopePointInvalidUsageException, NoClimberOnWayException, InvalidClimberMovementException, InvalidTestParametersException, NotAvailableClimberException, NullClimberException {
		GameController gameController = (GameController)ac.getBean("testGameController");
		gameController.doGameStarten();
		gameController.doStartGameRound();
		// fake the board
		//place Climber W2BS, W3BS
		gameController.testDoThrow(new HackedDiceManager(DiceTestHelper.generateXpreSetNormalDice(Arrays.asList(1,1,1,2))));
		gameController.doExecutePairs(gameController.getPossiblePairs().get(0), -1);
		gameController.testDoThrow(new HackedDiceManager(DiceTestHelper.generateXpreSetNormalDice(Arrays.asList(2,2,2,3))));
		gameController.doExecutePairs(gameController.getPossiblePairs().get(0), -1);
		
		gameController.testDoThrow(new HackedDiceManager(DiceTestHelper.generateXpreSetNormalDice(Arrays.asList(6,6,6,4))));
		gameController.doExecutePairs(gameController.getPossiblePairs().get(0), 11);
	}

	@Test(expected=InvalidPairsException.class)
	public void testDoPairungInputCheckWithInValidPairung() throws DiceNotGivenException, DiceNotThrownException, InvalidWayNumberException, NoMarkerIsAvailableException, RopePointInvalidUsageException, NoClimberOnWayException, InvalidClimberMovementException, InvalidTestParametersException, NotAvailableClimberException, NullClimberException {
		GameController gameController = (GameController)ac.getBean("testGameController");
		gameController.doGameStarten();
		gameController.doStartGameRound();
		// fake the board
		//place Climber W2BS, W3BS
		gameController.testDoThrow(new HackedDiceManager(DiceTestHelper.generateXpreSetNormalDice(Arrays.asList(1,1,1,2))));
		List<Dice> dices = DiceTestHelper.generateXpreSetNormalDice(Arrays.asList(1,2,3,4));
		TwoDicesPair basePairPair = new TwoDicesPair(
				new DicePair(dices.get(0), dices.get(1)),
				new DicePair(dices.get(2), dices.get(3)));
		gameController.doExecutePairs(basePairPair, -1);
	}
	
	@Test()
	public void testDoPairungInputCheckWithValidAberNeuPairung() throws DiceNotGivenException, DiceNotThrownException, InvalidWayNumberException, NoMarkerIsAvailableException, RopePointInvalidUsageException, NoClimberOnWayException, InvalidClimberMovementException, InvalidTestParametersException, NotAvailableClimberException, NullClimberException {
		GameController gameController = (GameController)ac.getBean("testGameController");
		gameController.doGameStarten();
		gameController.doStartGameRound();
		// fake the board
		//place Climber W2BS, W3BS
		gameController.testDoThrow(new HackedDiceManager(DiceTestHelper.generateXpreSetNormalDice(Arrays.asList(1,1,1,2))));
		List<Dice> dices = DiceTestHelper.generateXpreSetNormalDice(Arrays.asList(1,2,1,1));
		TwoDicesPair basePairPair = new TwoDicesPair(
				new DicePair(dices.get(0), dices.get(1)),
				new DicePair(dices.get(2), dices.get(3)));
		gameController.doExecutePairs(basePairPair, -1);
	}
	
	@Test
	public void testDoGetTransferObject() {
		fail("Not yet implemented");
	}

}
