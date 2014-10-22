package de.buxi.cantstop.model;

import static org.junit.Assert.*;

import java.util.ArrayList;
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
import de.buxi.cantstop.service.TooManyPlayerException;

public class GameControllerTest extends SpringLoaderSuperClassModelTests{

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
		assertEquals("BLUE Player should have 3 Markers", 3, playerBLAU.getMarkers().size());
		Player playerRED = gameController.getPlayer(Color.RED);
		assertEquals("RED Player should have 3 Markers", 3, playerRED.getMarkers().size());
	}

	@Test
	public void testActualPlayer() throws Exception {
		GameController gameController = (GameController)ac.getBean("testGameController");
		gameController.doGameStart();
		Player aktuellePlayer = gameController.getActualPlayer();
		assertEquals("actual Player should be actualPlayerNumber have", gameController.getActualPlayerNumber(), aktuellePlayer.getOrder());
	}

	@Test
	public void testNextPlayer() throws Exception {
		GameController gameController = (GameController)ac.getBean("testGameController");
		gameController.doGameStart();
		gameController.doStartGameTurn(); // distribute Climbers too 
		
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
	public void testDistributeClimber() throws Exception{
		GameController gameController = (GameController)ac.getBean("testGameController");
		gameController.doGameStart();
		gameController.distributeFreeClimbers();
		assertEquals("actual Player should be have all 3 Climbers", 3, gameController.getActualPlayer().getClimbers().size());
		assertEquals("gameController should have 0 Climber", 0, gameController.getClimbers().size());
	}

	@Test
	public void testStartGameRound() throws Exception {
		GameController gameController = (GameController)ac.getBean("testGameController");
		gameController.doGameStart();
		gameController.distributeFreeClimbers();
		gameController.doStartGameTurn();
		assertEquals("actual Player should be have all 3 Climbers", 3, gameController.getActualPlayer().getClimbers().size());
		assertEquals("gameController should have 0 Climber", 0, gameController.getClimbers().size());
		assertEquals("INIT State", GameState.IN_ROUND, gameController.getGameStatus());
	}
	@Test(expected=InvalidGameStateException.class)
	public void testDoStartGameRoundInvalidState() throws Exception {
		GameController gameController = (GameController)ac.getBean("testGameController");
		gameController.doStartGameTurn();
		assertEquals("IN_ROUND State expected", GameState.IN_ROUND, gameController.getGameStatus());
	}
	
	@Test
	public void testDoThrowPositiv() throws Exception {
		GameController gameController = (GameController)ac.getBean("testGameController");
		gameController.doGameStart();
		gameController.distributeFreeClimbers();
		gameController.doStartGameTurn();
		gameController.doThrowDices();
		assertEquals("DICES_THROWN State expected", GameState.DICES_THROWN, gameController.getGameStatus());
		List<Dice> dices = gameController.getDiceManager().getDices();
		for (Dice dice : dices) {
			assertTrue("Dice must be thrown", dice.isThrown());
		}
		assertNotNull(gameController.getWrongPairs());
	}
	
	@Test(expected=InvalidGameStateException.class)
	public void testDoThrowInvalidState() throws Exception {
		GameController gameController = (GameController)ac.getBean("testGameController");
		gameController.doGameStart();
		gameController.distributeFreeClimbers();
		gameController.doThrowDices();
	}
	
	@Test
	public void testDoThrowNegativ() throws Exception {
		GameController gameController = (GameController)ac.getBean("testGameController");
		gameController.doGameStart();
		gameController.doStartGameTurn();
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
	public void testDoThrowLastThrow() throws Exception {
		GameController gameController = (GameController)ac.getBean("testGameController");
		gameController.doGameStart();
		gameController.doStartGameTurn();
		GameTransferObject to = gameController.doThrowDices();
		Collection<Dice> dices = to.getDices();
		gameController.doExecutePairs(gameController.getPossiblePairs().get(0), -1);
		gameController.doThrowDices();
		List<Dice> previousThrow = gameController.getLastThrow();
		assertEquals("must be same: ", dices,  previousThrow);
	}
	
	@Test
	public void testDoExecutePairs() throws Exception {
		GameController gameController = (GameController)ac.getBean("testGameController");
		gameController.doGameStart();
		gameController.doStartGameTurn();
		GameTransferObject to = gameController.doThrowDices();
		to = gameController.doExecutePairs(gameController.getPossiblePairs().get(0), -1);
		Collection<Dice> dices = to.getLastThrow();
		for (Dice dice : dices) {
			assertNotNull("dice value must be visible", dice.getDiceValue());
		}
		UsedPairInfoTO lastUsedPairInfo = to.getLastUsedPairInfo();
		assertNotNull("first pair first dice value", lastUsedPairInfo.getChosenPair().getFirstPair().getFirst().getDiceValue());
		assertNotNull("first pair second dice value", lastUsedPairInfo.getChosenPair().getFirstPair().getSecond().getDiceValue());
		assertNotNull("first pair first dice value", lastUsedPairInfo.getChosenPair().getSecondPair().getFirst().getDiceValue());
		assertNotNull("secondpair second dice value", lastUsedPairInfo.getChosenPair().getSecondPair().getSecond().getDiceValue());
	}	
	
	@Test
	public void testDoGameRoundFinishedWaysNoOtherWahl() throws Exception {
		GameController gameController = (GameController)ac.getBean("testGameController");
		gameController.doGameStart();
		gameController.doStartGameTurn();
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
	public void testDoGameRoundBeendenPlayerGemacht() throws Exception {
		GameController gameController = (GameController)ac.getBean("testGameController");
		gameController.doGameStart();
		gameController.doStartGameTurn();
		Player oldPlayer = gameController.getActualPlayer();
		// fake the board
		//place Climber W2BS, W3BS
		gameController.testDoThrow(new HackedDiceManager(DiceTestHelper.generateXpreSetNormalDice(Arrays.asList(1,1,1,2))));
		gameController.doExecutePairs(gameController.getPossiblePairs().get(0), -1);
		//place Climber W4BS
		gameController.testDoThrow(new HackedDiceManager(DiceTestHelper.generateXpreSetNormalDice(Arrays.asList(2,2,2,3))));
		gameController.doExecutePairs(gameController.getPossiblePairs().get(0), 4);
		gameController.doEndGameTurn();
		
		assertEquals("IN_ROUND State expected", GameState.IN_ROUND, gameController.getGameStatus());
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
	public void testDoGameRoundBeendenGameWON() throws Exception {
		GameController gameController = (GameController)ac.getBean("testGameController");
		gameController.doGameStart();
		gameController.doStartGameTurn();
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
		gameController.doEndGameTurn();
		
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
	public void testDoGameStart() throws DiceNotThrownException, InvalidWayNumberException {
		GameController gameController = (GameController)ac.getBean("testGameController");
		gameController.doGameStart();
		assertEquals("First Player should be determined", GameController.DEFAULT_FIRST_PLAYER_NUM, gameController.getActualPlayerNumber());
		assertEquals("IN_GAME State", GameState.IN_GAME, gameController.getGameStatus());
		
		List<Player> players = new ArrayList<Player>(gameController.getPlayerMap().values());
		// RED and BLUE Markers are distributed
		assertEquals("10 markers with Player ", 10, players.get(0).getMarkers().size());
		assertEquals("10 markers with Player ", 10, players.get(1).getMarkers().size());
			
		// test player order
		for (Player player : players) {
			int playerOrder = player.getOrder();
			assertEquals("Player with " + playerOrder
					+ " should be in proper Position in PlayerOrder", player,
					gameController.getPlayersInOrder().get(playerOrder));
		}
	}

	@Test(expected=InvalidGameStateException.class)
	public void testDoGameStartInvalidState() throws DiceNotThrownException, InvalidWayNumberException {
		GameController gameController = (GameController)ac.getBean("testGameController");
		gameController.doGameStart();
		gameController.doGameStart();
	}
	
	@Test
	public void testDoPairInputCheckWithValidWaehlbarPair() throws DiceNotGivenException, DiceNotThrownException, InvalidWayNumberException, NoMarkerIsAvailableException, RopePointInvalidUsageException, NoClimberOnWayException, InvalidClimberMovementException, InvalidTestParametersException, NotAvailableClimberException, NullClimberException {
		GameController gameController = (GameController)ac.getBean("testGameController");
		gameController.doGameStart();
		gameController.doStartGameTurn();
		// fake the board
		//place Climber W2BS, W3BS
		gameController.testDoThrow(new HackedDiceManager(DiceTestHelper.generateXpreSetNormalDice(Arrays.asList(1,1,1,2))));
		gameController.doExecutePairs(gameController.getPossiblePairs().get(0), -1);
	}
	
	@Test(expected=InvalidWayNumberException.class)
	public void testDoPairInputCheckWithInValidWayNumber() throws DiceNotGivenException, DiceNotThrownException, InvalidWayNumberException, NoMarkerIsAvailableException, RopePointInvalidUsageException, NoClimberOnWayException, InvalidClimberMovementException, InvalidTestParametersException, NotAvailableClimberException, NullClimberException {
		GameController gameController = (GameController)ac.getBean("testGameController");
		gameController.doGameStart();
		gameController.doStartGameTurn();
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
	public void testDoPairInputCheckWithInValidPair() throws DiceNotGivenException, DiceNotThrownException, InvalidWayNumberException, NoMarkerIsAvailableException, RopePointInvalidUsageException, NoClimberOnWayException, InvalidClimberMovementException, InvalidTestParametersException, NotAvailableClimberException, NullClimberException {
		GameController gameController = (GameController)ac.getBean("testGameController");
		gameController.doGameStart();
		gameController.doStartGameTurn();
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
	public void testDoPairInputCheckWithValidAberNeuPair() throws DiceNotGivenException, DiceNotThrownException, InvalidWayNumberException, NoMarkerIsAvailableException, RopePointInvalidUsageException, NoClimberOnWayException, InvalidClimberMovementException, InvalidTestParametersException, NotAvailableClimberException, NullClimberException {
		GameController gameController = (GameController)ac.getBean("testGameController");
		gameController.doGameStart();
		gameController.doStartGameTurn();
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
	public void doAddPlayer() throws TooManyPlayerException {
		GameController gameController = (GameController)ac.getBean("testGameController");
		//assertEquals("new PlayerId must be generated and should be 3", "3", playerId);
		gameController.doAddPlayer("aaa");
		List<Player> players = gameController.getPlayersInOrder();
		assertEquals("3 players should be there (2 was preloaded)", 3, players.size());
		assertEquals("markers should stay intact", 4, gameController.getAllMarkers().keySet().size());
		
	}
	
	@Test(expected=TooManyPlayerException.class)
	public void doAddPlayerTooManyPlayer() throws TooManyPlayerException {
		GameController gameController = (GameController)ac.getBean("testGameController");
		gameController.doAddPlayer("Joska");
		gameController.doAddPlayer("Joska2");
		List<Player> players = gameController.getPlayersInOrder();
		assertEquals("3 players should be there (2 was preloaded)", 4, players.size());
		gameController.doAddPlayer("too many");
	}

	@Test
	public void testGameFull() throws TooManyPlayerException  {
		GameController gameController = (GameController)ac.getBean("testGameController");
		assertFalse("Game is not full", gameController.isGameFull());
		gameController.doAddPlayer("Joska");
		assertFalse("Game is not full", gameController.isGameFull());
		gameController.doAddPlayer("Joska2");
		assertTrue("Game is full", gameController.isGameFull());
	}
	
	@Test
	public void testDeterminePlayerOrderStandard() {
		fail("too simple to test, no real logic in it");
	}	

	@Test
	public void testDetermineFirstPlayer() {
		fail("too simple to test, no real logic in it");
	}	

	/**
	 * from 8.way red marker was not removed
	 */
	@Test
	public void testBug1() throws Exception {
		GameController gameController = (GameController)ac.getBean("testGameController");
		gameController.doGameStart();
		gameController.doStartGameTurn();
		Player oldPlayer = gameController.getActualPlayer();
		// fake the board
		// player2 places climbers on the ways 2,3,4
		//place Climber W2BS, W3BS
		gameController.testDoThrow(new HackedDiceManager(DiceTestHelper.generateXpreSetNormalDice(Arrays.asList(1,1,1,2))));
		gameController.doExecutePairs(gameController.getPossiblePairs().get(0), -1);
		//place Climber W4BS
		gameController.testDoThrow(new HackedDiceManager(DiceTestHelper.generateXpreSetNormalDice(Arrays.asList(2,2,2,3))));
		gameController.doExecutePairs(gameController.getPossiblePairs().get(0), 4);
		// player1 marking lowest ropePointon way 2,3,4 
		gameController.doEndGameTurn();
		
		//------ changing player
		//player2 places climbers in the huts on way 2,3,4 
		//place Climber W2BS, W3BS
		gameController.testDoThrow(new HackedDiceManager(DiceTestHelper.generateXpreSetNormalDice(Arrays.asList(1,1,1,2))));
		gameController.doExecutePairs(gameController.getPossiblePairs().get(0), -1);
		//place Climber W4BS
		gameController.testDoThrow(new HackedDiceManager(DiceTestHelper.generateXpreSetNormalDice(Arrays.asList(2,2,2,3))));
		gameController.doExecutePairs(gameController.getPossiblePairs().get(0), 4);
		
		// player2 moving W2BS, W3BS into huts
		gameController.testDoThrow(new HackedDiceManager(DiceTestHelper.generateXpreSetNormalDice(Arrays.asList(1,1,1,2))));
		gameController.doExecutePairs(gameController.getPossiblePairs().get(0), -1);
		Player player2 = gameController.getActualPlayer();
		gameController.doEndGameTurn();
		// way 2,3 is blocked by player2
		// all other markers should be removed 
		assertNotEquals("Player should be different, because 2 players are playing", oldPlayer, player2 );
		
		Way way2 = gameController.getBoard().getWayByNumber(2);
		assertTrue("way2 Hut should be marked  with "+player2.getColor(), way2.isBlockedByPlayer(player2.getColor()));
		Way way3 = gameController.getBoard().getWayByNumber(3);
		assertTrue("way3 Hut should be marked  with "+player2.getColor(), way3.isBlockedByPlayer(player2.getColor()));
		assertEquals("Player2 should have no Climber", 0, player2.getClimbers().size());
		assertEquals("Player2 should have 7 Markers", 7, player2.getMarkers().size());
		Way way4 = gameController.getBoard().getWayByNumber(4);
		assertTrue("Player2 marker should be on way4", way4.isMarkerOnRope(player2.getColor()));
		
		assertFalse("oldplayer marker should have been removed from way2", way2.isMarkerOnRope(oldPlayer.getColor()));
		assertFalse("oldplayer marker should have been removed from way3", way3.isMarkerOnRope(oldPlayer.getColor()));
		
		assertEquals("old Player should have 9 Markers, because player2 was blocking way 2,3", 9, oldPlayer.getMarkers().size());
	}
	
	/**
	 * 1. we have a marker on way 3 at the bottom
	 * 2. the same player has a figure next to the hut
	 * 3. throws 3,3 
	 * only one figure should be in the hut 
	 */
	@Test
	public void testBug2() throws Exception {
		GameController gameController = (GameController)ac.getBean("gameController");
		gameController.doGameStart();
		gameController.doStartGameTurn();
		Player player = gameController.getActualPlayer();
		// fake the board
		// player2 places climbers on the ways 2,3,4
		//place Climber W2BS, W3BS
		gameController.testDoThrow(new HackedDiceManager(DiceTestHelper.generateXpreSetNormalDice(Arrays.asList(1,1,1,2))));
		gameController.doExecutePairs(gameController.getPossiblePairs().get(0), -1);
		//place Climber W4BS
		gameController.testDoThrow(new HackedDiceManager(DiceTestHelper.generateXpreSetNormalDice(Arrays.asList(2,2,2,3))));
		gameController.doExecutePairs(gameController.getPossiblePairs().get(0), 4);
		// player1 marking lowest ropePointon way 2,3,4 
		gameController.doEndGameTurn();
		gameController.doEndGameTurn();
	
		//place Climber W2BS, W12BS
		gameController.testDoThrow(new HackedDiceManager(DiceTestHelper.generateXpreSetNormalDice(Arrays.asList(1,1,6,6))));
		gameController.doExecutePairs(gameController.getPossiblePairs().get(0), -1);
		
		// player2 moving W2BS into hut
		gameController.testDoThrow(new HackedDiceManager(DiceTestHelper.generateXpreSetNormalDice(Arrays.asList(1,1,1,1))));
		gameController.doExecutePairs(gameController.getPossiblePairs().get(0), -1);
		
		Way way2 = gameController.getBoard().getWayByNumber(2);
		assertTrue("1 climber should be in the hut",way2.isClimberInHut());
		assertEquals("Player should have 1 Climber", 1, player.getClimbers().size());
		assertTrue("player marker should be on way2", way2.isMarkerOnRope(player.getColor()));
	
	}
}
