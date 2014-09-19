/**
 * 
 */
package de.buxi.cantstop.model;


import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import de.buxi.cantstop.model.Board;
import de.buxi.cantstop.model.Climber;
import de.buxi.cantstop.model.Color;
import de.buxi.cantstop.model.GameFactory;
import de.buxi.cantstop.model.Hut;
import de.buxi.cantstop.model.InvalidClimberMovementException;
import de.buxi.cantstop.model.InvalidWayNumberException;
import de.buxi.cantstop.model.Marker;
import de.buxi.cantstop.model.NoClimberOnWayException;
import de.buxi.cantstop.model.NoMarkerIsAvailableException;
import de.buxi.cantstop.model.NullClimberException;
import de.buxi.cantstop.model.Player;
import de.buxi.cantstop.model.RopePointInvalidUsageException;
import de.buxi.cantstop.model.Way;

/**
 * @author buxi
 *
 */
public class BoardTest extends SpringLoaderSuperClass{
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		
	}

	/**
	 * Test method for {@link de.buxi.cantstop.model.Board#getWayByNumber(int)}.
	 * @throws InvalidWayNumberException 
	 */
	@Test
	public void testGetWayByNumber() throws InvalidWayNumberException {
		Way way = mock(Way.class);
		List<Way> ways = new ArrayList<Way>();
		ways.add(way);
		Board board = new Board(ways);
		Way resultWay = board.getWayByNumber(2);
		assertEquals("Way 2 should be there", way, resultWay);
	}

	/**
	 * Test method for {@link de.buxi.cantstop.model.Board#getWayByNumber(int)}.
	 * @throws InvalidWayNumberException 
	 */
	@Test(expected=InvalidWayNumberException.class)
	public void testGetWayByNumberNegativ1() throws InvalidWayNumberException {
		Board board = new Board(null);
		board.getWayByNumber(1);
	}
	
	/**
	 * Test method for {@link de.buxi.cantstop.model.Board#getWayByNumber(int)}.
	 * @throws InvalidWayNumberException 
	 */
	@Test(expected=InvalidWayNumberException.class)
	public void testGetWayByNumberNegativ2() throws InvalidWayNumberException {
		List<Way> ways = new ArrayList<Way>();
		Board board = new Board(ways);
		board.getWayByNumber(1);
	}
	
	/**
	 * Test method for {@link de.buxi.cantstop.model.Board#markClimbers(de.buxi.cantstop.model.Player)}.
	 * @throws NullClimberException 
	 * @throws RopePointInvalidUsageException 
	 * @throws InvalidWayNumberException 
	 * @throws NoClimberOnWayException 
	 * @throws NoMarkerIsAvailableException 
	 */
	@Test
	public void testMarkClimbersAllDown() throws InvalidWayNumberException, RopePointInvalidUsageException, NullClimberException, NoMarkerIsAvailableException, NoClimberOnWayException {
		Board board = (Board)ac.getBean("smallBoard");
		Collection<Integer> wayNumberList = new ArrayList<Integer>(Arrays.asList(2,3,4));
		Collection<Climber> placedClimbers = BoardTestHelper.placeClimberOnTheWayAllDown(board, wayNumberList);
		Player player = new Player(1, "1", Color.BLUE);
		player.addMarkers(GameFactory.createMarkersStatic(3, Color.BLUE));
		Collection<Climber> freeClimbers = board.markClimbers(player);
		for (Climber freeClimber : freeClimbers) {
			assertTrue("Climber not found", placedClimbers.remove(freeClimber));
		}
		assertTrue("All placed Climber not found", placedClimbers.isEmpty());
		
		for (int wayNumber : wayNumberList) {
			Way way = board.getWayByNumber(wayNumber);
			assertTrue("Way ist not marked", way.getFirstRopePoint().isMarkerForColor(Color.BLUE));
		}
	}

	/**
	 * Test method for {@link de.buxi.cantstop.model.Board#markClimbers(de.buxi.cantstop.model.Player)}.
	 * @throws NullClimberException 
	 * @throws RopePointInvalidUsageException 
	 * @throws InvalidWayNumberException 
	 * @throws NoClimberOnWayException 
	 * @throws NoMarkerIsAvailableException 
	 */
	@Test
	public void testMarkClimbersAllInHut() throws InvalidWayNumberException, RopePointInvalidUsageException, NullClimberException, NoMarkerIsAvailableException, NoClimberOnWayException {
		Board board = (Board)ac.getBean("smallBoard");
		Collection<Integer> wayNumberList = new ArrayList<Integer>(Arrays.asList(2,3,4));
		Collection<Climber> placedClimbers = BoardTestHelper.placeClimbersOnTheWayAllInHut(board, wayNumberList);
		Player player = new Player(1, "1", Color.BLUE);
		player.addMarkers(GameFactory.createMarkersStatic(3, Color.BLUE));
		Collection<Climber> freeClimbers = board.markClimbers(player);
		for (Climber freeClimber : freeClimbers) {
			assertTrue("Climber not found", placedClimbers.remove(freeClimber));
		}
		assertTrue("All placed climbers not found", placedClimbers.isEmpty());
		
		for (int wayNumber : wayNumberList) {
			Way way = board.getWayByNumber(wayNumber);
			assertTrue("Hut is not marked", way.getHut().isMarked(Color.BLUE));
		}
	}

	/**
	 * Test method for {@link de.buxi.cantstop.model.Board#removeClimbers()}.
	 * @throws NullClimberException 
	 * @throws RopePointInvalidUsageException 
	 * @throws InvalidWayNumberException 
	 * @throws InvalidClimberMovementException 
	 * @throws NoMarkerIsAvailableException 
	 */
	@Test
	public void testRemoveClimbersFromHuts() throws InvalidWayNumberException, RopePointInvalidUsageException, NullClimberException, NoMarkerIsAvailableException, InvalidClimberMovementException {
		//tests with  small Board
		Board board = (Board)ac.getBean("smallBoard");
		Collection<Integer> wayNumberList = new ArrayList<Integer>(Arrays.asList(2,3,4));
		Collection<Climber> placedClimbers = BoardTestHelper.placeClimbersOnTheWayAllInHut(board, wayNumberList);
		
		Collection<Climber> freeClimbers = board.removeClimbers();
		for (Climber freeClimber : freeClimbers) {
			assertTrue("Climber not found", placedClimbers.remove(freeClimber));
		}
		assertTrue("Not all climber found", placedClimbers.isEmpty());
		
		for (int wayNumber : wayNumberList) {
			Way way = board.getWayByNumber(wayNumber);
			assertFalse("No Climber should be in hut", way.isClimberInHut());
		}
	}

	@Test
	public void testRemoveClimbersFromRope() throws InvalidWayNumberException, RopePointInvalidUsageException, NullClimberException, NoMarkerIsAvailableException, InvalidClimberMovementException {
		//test with small board
		Board board = (Board)ac.getBean("smallBoard");
		Collection<Integer> wayNumberList = new ArrayList<Integer>(Arrays.asList(2,3,4));
		Collection<Climber> placedClimbers = BoardTestHelper.placeClimbersOnTheWayAllNextToTheHut(board, wayNumberList);
		
		Collection<Climber> freeClimbers = board.removeClimbers();
		for (Climber freeClimber : freeClimbers) {
			assertTrue("Climber not found", placedClimbers.remove(freeClimber));
		}
		assertTrue("not all placed Climber were found", placedClimbers.isEmpty());
		
		for (int wayNumber : wayNumberList) {
			Way way = board.getWayByNumber(wayNumber);
			assertFalse("no Climber should be on the rope", way.isClimberOnRope());
		}
	}
	
	/**
	 * Test method for {@link de.buxi.cantstop.model.Board#getBesetzteHutnZahl(de.buxi.cantstop.model.Player)}.
	 * @throws InvalidWayNumberException 
	 * @throws NullClimberException 
	 * @throws RopePointInvalidUsageException 
	 */
	@Test
	public void testGetBlockedHuts3Huts() throws InvalidWayNumberException, RopePointInvalidUsageException, NullClimberException {
		//test with small board
		Board board = (Board)ac.getBean("smallBoard");
		Collection<Marker> markers = BoardTestHelper.markHuts(board, new ArrayList<Integer>(Arrays.asList(2,3,4)), Color.BLUE);
		Collection<Hut> huts = board.getBlockedHuts(new Player(1, "1", Color.BLUE));
		assertEquals("3 Huts should be blocked with BLAU", 3, huts.size());
		for (Hut hut : huts) {
			assertTrue("Marker not found", markers.remove(hut.getMarker()));
		}
		assertTrue("not all Markers were found", markers.isEmpty());
	}
	
	/**
	 * Test method for {@link de.buxi.cantstop.model.Board#getBesetzteHutnZahl(de.buxi.cantstop.model.Player)}.
	 * @throws InvalidWayNumberException 
	 * @throws NullClimberException 
	 * @throws RopePointInvalidUsageException 
	 */
	@Test
	public void testGetBlockedeHutsNotBlocked() throws InvalidWayNumberException, RopePointInvalidUsageException, NullClimberException {
		//test with small board
		Board board = (Board)ac.getBean("smallBoard");
		Collection<Hut> huts = board.getBlockedHuts(new Player(1, "1", Color.BLUE));
		assertTrue("No hut should be blocked", huts.isEmpty());
	}
	/**
	 * @throws NoClimberOnWayException 
	 * @throws RopePointInvalidUsageException 
	 * @throws NoMarkerIsAvailableException 
	 * @throws NullClimberException 
	 * @throws InvalidWayNumberException 
	 * 
	 */
	@Test
	public void testRemoveMarkersFromBlockedWays() throws NoMarkerIsAvailableException, RopePointInvalidUsageException, NoClimberOnWayException, InvalidWayNumberException, NullClimberException {
		Board board = (Board)ac.getBean("smallBoard");
		Collection<Integer> wayNumberList = new ArrayList<Integer>(Arrays.asList(2,3,4));
		
		// marks the Ways next to the hut with BLUE
		BoardTestHelper.placeClimbersOnTheWayAllNextToTheHut(board, wayNumberList);
		Player playerBLAU = new Player(1, "1", Color.BLUE);
		playerBLAU.addMarkers(GameFactory.createMarkersStatic(3, Color.BLUE));
		board.markClimbers(playerBLAU);
		
		// marks the Ways down with RED
		BoardTestHelper.placeClimberOnTheWayAllDown(board, wayNumberList);
		Player playerRED = new Player(2, "2", Color.RED);
		playerRED.addMarkers(GameFactory.createMarkersStatic(3, Color.RED));
		board.markClimbers(playerRED);
		
		// marks the huts with YELLOW
		BoardTestHelper.placeClimbersOnTheWayAllInHut(board, wayNumberList);
		Player playerYELLOW = new Player(3, "3", Color.YELLOW);
		playerYELLOW.addMarkers(GameFactory.createMarkersStatic(3, Color.YELLOW));
		board.markClimbers(playerYELLOW);

		// all RED and BLAU Markers should be removed 
		Collection<Hut> usedHuts = board.getBlockedHuts(playerYELLOW);
		Map<Color, Collection<Marker>> removedMarkers = board.removeMarkersFromBlockedWays(usedHuts);
		assertEquals("3 RED Markers should be removed", 3, removedMarkers.get(Color.RED).size());
		assertEquals("3 BLAU Markers should be removed", 3, removedMarkers.get(Color.BLUE).size());
		
		assertFalse("Black not plays", removedMarkers.containsKey(Color.BLACK));
	}
}
