package de.buxi.cantstop.model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import de.buxi.cantstop.model.Board;
import de.buxi.cantstop.model.Climber;
import de.buxi.cantstop.model.Color;
import de.buxi.cantstop.model.Dice;
import de.buxi.cantstop.model.DiceManager;
import de.buxi.cantstop.model.GameController;
import de.buxi.cantstop.model.GameState;
import de.buxi.cantstop.model.Marker;
import de.buxi.cantstop.model.Player;
import de.buxi.cantstop.model.Way;

public class GameControllerSetupTest extends SpringLoaderSuperClassModelTests{

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}
	
	/**
	 * Test Game preparation Board, Climbers, Players, Markers, etc
	 */
	@Test
	public void testGameController() {
		GameController gameController = (GameController)ac.getBean("gameController");
	
		List<Player> players = new ArrayList<>(gameController.getPlayerMap().values());
		assertNotNull("Players is null", players);
		assertTrue("Playernumber must be between 2 and 4", players.size() >= GameController.MINIMUM_PLAYER_NUMBER && players.size() <= GameController.MAXIMUM_PLAYER_NUMBER);
		
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
		
		Map<Color, Collection<Marker>> markersCollection = gameController.getAllMarkers();
		assertNotNull("Marker is null", markersCollection);
		assertEquals("10 BLUE marker missing", 10, markersCollection.get(Color.BLUE).size());
		assertEquals("10 RED marker missing", 10, markersCollection.get(Color.RED).size());
		assertEquals("10 GREEN marker missing", 10, markersCollection.get(Color.GREEN).size());
		assertEquals("10 YELLOW marker missing", 10, markersCollection.get(Color.YELLOW).size());
		
		List<Climber> climbers = gameController.getClimbers();
		assertNotNull("Climber is null", climbers);
		assertEquals("3 Climbers missing", 3, climbers.size());
		assertEquals("ENOUGH_PLAYER Status", GameState.ENOUGH_PLAYER, gameController.getGameStatus());
	}
}
