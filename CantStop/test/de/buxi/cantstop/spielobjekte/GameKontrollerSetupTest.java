package de.buxi.cantstop.spielobjekte;

import static org.junit.Assert.*;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class GameKontrollerSetupTest extends SpringLoaderSuperClass{

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	/**
	 * Test Gamepreparation Boeard, Climbers, Players, Markers, etc
	 */
	@Test
	public void testGameController() {
		GameController gameController = (GameController)ac.getBean("gameController");
	
		List<Player> players = gameController.getPlayerInOrder();
		assertNotNull("Player is null", players);
		assertTrue("Playernumber must be between 2 and 4", players.size()>=2 && players.size()<=4);
		//test determinePlayerOrderStandard()
		for (Player player : players) {
			int playerOrder = player.getOrder();
			assertEquals("Player with "+playerOrder+" should be in proper Position in PlayerOrder", player, gameController.getPlayerInOrder().get(playerOrder));
		}
		
		//test determineFirstrPlayer()
		assertEquals("actual PlayerNumber must be 0", 0, gameController.getActualPlayerNumber());
		
		// RED und BLUE Steine sind distributet
		assertEquals("10 markers with Player ", 10, players.get(0).getMarkers().size());
		assertEquals("10 markers with Player ", 10, players.get(1).getMarkers().size());
		
		Board board = gameController.getBoard();
		assertNotNull("Board is null", board);

		List<Way> ways = board.getWays();
		assertEquals("Way 2, Hut + 2 RopePoint", 2, ways.get(0).getNumber());
		assertEquals("Way 2, Hut + 2 RopePoint", 2, ways.get(0).getRopePoints().size());
		assertEquals("Way 3, Hut + 4 RopePoint", 3, ways.get(1).getNumber());
		assertEquals("Way 3, Hut + 4 RopePoint", 4, ways.get(1).getRopePoints().size());
		assertEquals("Way 4, Hut + 6 RopePoint", 4, ways.get(2).getNumber());
		assertEquals("Way 4, Hut + 6 RopePoint", 6, ways.get(2).getRopePoints().size());
		assertEquals("Way 5, Hut + 8 RopePoint", 5, ways.get(3).getNumber());
		assertEquals("Way 5, Hut + 8 RopePoint", 8, ways.get(3).getRopePoints().size());
		assertEquals("Way 6, Hut + 10 RopePoint", 6, ways.get(4).getNumber());
		assertEquals("Way 6, Hut + 10 RopePoint", 10, ways.get(4).getRopePoints().size());
		assertEquals("Way 7, Hut + 12 RopePoint", 7, ways.get(5).getNumber());
		assertEquals("Way 7, Hut + 12 RopePoint", 12, ways.get(5).getRopePoints().size());
		assertEquals("Way 8, Hut + 10 RopePoint", 8, ways.get(6).getNumber());
		assertEquals("Way 8, Hut + 10 RopePoint", 10, ways.get(6).getRopePoints().size());
		assertEquals("Way 9, Hut + 8 RopePoint", 9, ways.get(7).getNumber());
		assertEquals("Way 9, Hut + 8 RopePoint", 8, ways.get(7).getRopePoints().size());
		assertEquals("Way 10, Hut + 6 RopePoint", 10, ways.get(8).getNumber());
		assertEquals("Way 10, Hut + 6 RopePoint", 6, ways.get(8).getRopePoints().size());
		assertEquals("Way 11, Hut + 4 RopePoint", 11, ways.get(9).getNumber());
		assertEquals("Way 11, Hut + 4 RopePoint", 4, ways.get(9).getRopePoints().size());
		assertEquals("Way 12, Hut + 2 RopePoint", 12, ways.get(10).getNumber());
		assertEquals("Way 12, Hut + 2 RopePoint", 2, ways.get(10).getRopePoints().size());
		
		
		DiceManager diceManager = gameController.getDiceManager();
		assertNotNull("DiceManager is null", diceManager);
		List<Dice>dices = diceManager.getDices();
		assertEquals("4 Dice missing", 4, dices.size());
		for (Dice dice : dices) {
			assertEquals(6,  dice.getSideNumber());
		}
		
		Map<Color, Collection<Marker>> markereCollection = gameController.getAllMarkers();
		assertNotNull("Marker is null", markereCollection);
		assertNull("10 BLUE marker missing", markereCollection.get(Color.BLUE));
		assertEquals("10 BLACK marker missing", 10, markereCollection.get(Color.BLACK).size());
		assertEquals("10 YELLOW marker missing", 10, markereCollection.get(Color.YELLOW).size());
		assertNull("10 RED marker missing", markereCollection.get(Color.RED));
		
		List<Climber> climbers = gameController.getClimbers();
		assertNotNull("Climber is null", climbers);
		assertEquals("3 Climbers missing", 3, climbers.size());
		assertEquals("INIT Status", GameState.INIT, gameController.getGameStatus());
	}
}
