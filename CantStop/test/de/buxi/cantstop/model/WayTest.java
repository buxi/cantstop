/**
 * 
 */
package de.buxi.cantstop.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import de.buxi.cantstop.model.Climber;
import de.buxi.cantstop.model.Color;
import de.buxi.cantstop.model.GameFactory;
import de.buxi.cantstop.model.InvalidClimberMovementException;
import de.buxi.cantstop.model.InvalidWayNumberException;
import de.buxi.cantstop.model.Marker;
import de.buxi.cantstop.model.NoClimberOnWayException;
import de.buxi.cantstop.model.NullClimberException;
import de.buxi.cantstop.model.RopePointInvalidUsageException;
import de.buxi.cantstop.model.Way;

/**
 * @author buxi
 *
 */
public class WayTest extends SpringLoaderSuperClassModelTests {

	private GameFactory gameFactory;
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
		gameFactory = (GameFactory)ac.getBean("gameFactory");
	}

	/**
	 * Test method for {@link de.buxi.cantstop.model.Way#isBlocked()}.
	 * @throws InvalidWayNumberException 
	 * @throws RopePointInvalidUsageException 
	 */
	@Test
	public void testIsBlockedFromPlayerPositive() throws InvalidWayNumberException, RopePointInvalidUsageException {
		Way way3 = gameFactory.createWay(2, 2);
		assertFalse("Way should be free", way3.isBlockedByPlayer(null));
		assertFalse("Way should be free", way3.isBlockedByPlayer(Color.BLUE));
		
		
		way3.getHut().mark(new Marker(Color.BLUE));
		assertTrue("Way is marked", way3.isBlockedByPlayer(Color.BLUE));
		
		assertFalse("Way is marked with BLUE", way3.isBlockedByPlayer(Color.GREEN));
	}

	/**
	 * Test method for {@link de.buxi.cantstop.model.Way#isBlocked()}.
	 * @throws InvalidWayNumberException 
	 * @throws RopePointInvalidUsageException 
	 */
	@Test
	public void testIsBlocked() throws InvalidWayNumberException, RopePointInvalidUsageException {
		Way way3 = gameFactory.createWay(2, 2);
		
		assertFalse("Way should be free", way3.isBlocked());
		
		way3.getHut().mark(new Marker(Color.BLUE));
		assertTrue("Way is marked", way3.isBlocked());
	}
	/**
	 * Test method for {@link de.buxi.cantstop.model.Way#isFree()}.
	 * @throws InvalidWayNumberException 
	 */
	@Test
	public void testIsFree() throws InvalidWayNumberException {
		Way way3 = gameFactory.createWay(3, 4);
		assertTrue("Way should be free", way3.isFree());
		
	}

	/**
	 * Test method for {@link de.buxi.cantstop.model.Way#isClimberOnRope()}.
	 * @throws RopePointInvalidUsageException 
	 * @throws NullClimberException 
	 */
	@Test
	public void testIsClimberOnRope() throws RopePointInvalidUsageException, NullClimberException {
		Way way2 = gameFactory.createWay(2, 2);
		assertFalse("no climber on the rope", way2.isClimberOnRope());
		way2.getRopePoints().get(0).placeClimber(new Climber());
		assertTrue("Climber on the Rope Coord 0", way2.isClimberOnRope());
	}

	/**
	 * Test method for {@link de.buxi.cantstop.model.Way#whereIsTheClimberOnTheRope()}.
	 * @throws RopePointInvalidUsageException 
	 */
	@Test
	public void testWhereIsClimberOnRopeNoClimber() throws RopePointInvalidUsageException {
		Way way2 = gameFactory.createWay(2, 2);
		assertTrue("no climber on the rope", way2.whereIsTheClimberOnTheRope() < 0);
	}
	
	/**
	 * Test method for {@link de.buxi.cantstop.model.Way#whereIsTheClimberOnTheRope()}.
	 * @throws RopePointInvalidUsageException 
	 * @throws NullClimberException 
	 */
	@Test
	public void testWhereIsClimberOnRopeKoord0() throws RopePointInvalidUsageException, NullClimberException {
		Way way2 = gameFactory.createWay(2, 2);
		way2.getRopePoints().get(0).placeClimber(new Climber());
		assertEquals("Climber on the Rope Coord 0", 0, way2.whereIsTheClimberOnTheRope());
	}
	
	/**
	 * Test method for {@link de.buxi.cantstop.model.Way#whereIsTheClimberOnTheRope()}.
	 * @throws RopePointInvalidUsageException 
	 * @throws NullClimberException 
	 */
	@Test
	public void testWhereIsClimberOnRopeNextToHut() throws RopePointInvalidUsageException, NullClimberException {
		Way way2 = gameFactory.createWay(2, 2);
		way2.getUpperRopePoint().placeClimber(new Climber());
		assertEquals("Climber on the Rope Coord 1", 1, way2.whereIsTheClimberOnTheRope());
	}
	
	/**
	 * Test method for {@link de.buxi.cantstop.model.Way#isMarkerOnRope(de.buxi.cantstop.model.Color)}.
	 * @throws RopePointInvalidUsageException 
	 */
	@Test
	public void testIsMarkerOnRope() throws RopePointInvalidUsageException {
		Way way2 = gameFactory.createWay(2, 2);
		assertFalse("no marker on the rope", way2.isMarkerOnRope(Color.BLUE));
		way2.getRopePoints().get(0).mark(new Marker(Color.BLUE));
		assertTrue("Marker on the Rope Coord 0", way2.isMarkerOnRope(Color.BLUE));
	}

	/**
	 * Test method for {@link de.buxi.cantstop.model.Way#isMarkerOnRope(de.buxi.cantstop.model.Color)}.
	 * @throws RopePointInvalidUsageException 
	 */
	@Test
	public void testIsMarkerOnRopeNextToHut() throws RopePointInvalidUsageException {
		Way way2 = gameFactory.createWay(2, 2);
		assertFalse("no marker on the rope", way2.isMarkerOnRope(Color.BLUE));
		way2.getUpperRopePoint().mark(new Marker(Color.BLUE));
		assertTrue("Marker on the Rope Coord 1", way2.isMarkerOnRope(Color.BLUE));
	}

	/**
	 * Test method for {@link de.buxi.cantstop.model.Way#whereIsMarkerForColor(de.buxi.cantstop.model.Color)}.
	 * @throws RopePointInvalidUsageException 
	 */
	@Test
	public void testWhereIsMarkerForColor() throws RopePointInvalidUsageException {
		Way way2 = gameFactory.createWay(2, 2);
		assertFalse("no marker on the rope", way2.isMarkerOnRope(Color.BLUE));
		way2.getRopePoints().get(1).mark(new Marker(Color.BLUE));
		assertEquals("Marker on the Rope Coord 1", 1, way2.whereIsMarkerForColor(Color.BLUE));
	}

	/**
	 * Test method for {@link de.buxi.cantstop.model.Way#getFirstRopePoint()}.
	 */
	@Test
	public void testGetFirstRopePoint() {
		Way way2 = gameFactory.createWay(2, 2);
		assertEquals("Untfirst RopePoint should be [0] sein", way2.getRopePoints().get(0), way2.getFirstRopePoint());
	}

	/**
	 * Test method for {@link de.buxi.cantstop.model.Way#getUpperRopePoint()}.
	 */
	@Test
	public void testGetUpperRopePoint() {
		Way way2 = gameFactory.createWay(2, 2);
		assertEquals("Upper RopePoint should be [1]", way2.getRopePoints().get(1), way2.getUpperRopePoint());
	}
	
	/**
	 * Test method for {@link de.buxi.cantstop.model.Way#placeClimberOntoFirstRopePoint(de.buxi.cantstop.model.Climber)}.
	 * @throws RopePointInvalidUsageException 
	 * @throws NullClimberException 
	 */
	@Test
	public void testPlaceClimberDown() throws RopePointInvalidUsageException, NullClimberException {
		Way way2 = gameFactory.createWay(2, 2);
		way2.placeClimberOntoFirstRopePoint(new Climber());
		assertEquals("Climber on the Rope Coord 0", 0, way2.whereIsTheClimberOnTheRope());
	}

	/**
	 * Test method for {@link de.buxi.cantstop.model.Way#placeClimberAfterMarker(de.buxi.cantstop.model.Climber, de.buxi.cantstop.model.Color)}.
	 * @throws RopePointInvalidUsageException 
	 * @throws NullClimberException 
	 * @throws InvalidClimberMovementException 
	 */
	@Test(expected=InvalidClimberMovementException.class)
	public void testPlaceClimberAfterMarkerNullMarker() throws RopePointInvalidUsageException, NullClimberException, InvalidClimberMovementException {
		Way way2 = gameFactory.createWay(4, 6);
		way2.placeClimberAfterMarker(new Climber(), null);
	}

	/**
	 * Test method for {@link de.buxi.cantstop.model.Way#placeClimberAfterMarker(de.buxi.cantstop.model.Climber, de.buxi.cantstop.model.Color)}.
	 * @throws RopePointInvalidUsageException 
	 * @throws NullClimberException 
	 * @throws InvalidClimberMovementException 
	 */
	@Test(expected=NullClimberException.class)
	public void testPlaceClimberAfterMarkerNullClimber() throws RopePointInvalidUsageException, NullClimberException, InvalidClimberMovementException {
		Way way2 = gameFactory.createWay(4, 6);
		way2.getRopePoints().get(1).mark(new Marker(Color.BLUE));
		way2.placeClimberAfterMarker(null, Color.BLUE);
	}

	/**
	 * Test method for {@link de.buxi.cantstop.model.Way#placeClimberAfterMarker(de.buxi.cantstop.model.Climber, de.buxi.cantstop.model.Color)}.
	 * @throws RopePointInvalidUsageException 
	 * @throws NullClimberException 
	 * @throws InvalidClimberMovementException 
	 */
	@Test
	public void testPlaceClimberAfterMarkerInWithte() throws RopePointInvalidUsageException, NullClimberException, InvalidClimberMovementException {
		Way way2 = gameFactory.createWay(4, 6);
		way2.getRopePoints().get(3).mark(new Marker(Color.BLUE));
		way2.placeClimberAfterMarker(new Climber(), Color.BLUE);
		assertEquals("Climber should be on 4. RopePoint", 4, way2.whereIsTheClimberOnTheRope());
	}

	/**
	 * Test method for {@link de.buxi.cantstop.model.Way#placeClimberAfterMarker(de.buxi.cantstop.model.Climber, de.buxi.cantstop.model.Color)}.
	 * @throws RopePointInvalidUsageException 
	 * @throws NullClimberException 
	 * @throws InvalidClimberMovementException 
	 */
	@Test
	public void testPlaceClimberNachMarkerNextToHut() throws RopePointInvalidUsageException, NullClimberException, InvalidClimberMovementException {
		Way way2 = gameFactory.createWay(4, 6);
		way2.getUpperRopePoint().mark(new Marker(Color.BLUE));
		way2.placeClimberAfterMarker(new Climber(), Color.BLUE);
		assertEquals("Climber should be in the hut", -1000, way2.whereIsTheClimberOnTheRope());
		assertTrue("Climber should be in the hut2", way2.isClimberInHut());
		assertFalse("Climber should not be on the rope", way2.isClimberOnRope());
	}
	
	/**
	 * Test method for {@link de.buxi.cantstop.model.Way#moveClimber()}.
	 * @throws RopePointInvalidUsageException 
	 * @throws InvalidClimberMovementException 
	 * @throws NullClimberException 
	 */
	@Test
	public void testBewayClimber() throws RopePointInvalidUsageException, InvalidClimberMovementException, NullClimberException {
		Way way2 = gameFactory.createWay(4, 6);
		way2.getRopePoints().get(4).placeClimber(new Climber());
		way2.moveClimber();
		assertEquals("Climber should be in the hut", 5, way2.whereIsTheClimberOnTheRope());
		way2.moveClimber();
		assertTrue("Climber should be in the hut2", way2.isClimberInHut());
		assertFalse("Climber should be not auf dem Seil", way2.isClimberOnRope());
	}

	/**
	 * Test method for {@link de.buxi.cantstop.model.Way#moveClimber()}.
	 * @throws RopePointInvalidUsageException 
	 * @throws InvalidClimberMovementException 
	 * @throws NullClimberException 
	 */
	@Test(expected=InvalidClimberMovementException.class)
	public void testBewayClimberNoClimber() throws RopePointInvalidUsageException, InvalidClimberMovementException, NullClimberException {
		Way way2 = gameFactory.createWay(4, 6);
		way2.moveClimber();
	}

	/**
	 * Test method for {@link de.buxi.cantstop.model.Way#markClimberOnTheWay(de.buxi.cantstop.model.Marker)}.
	 * @throws RopePointInvalidUsageException 
	 * @throws NoClimberOnWayException 
	 */
	@Test(expected=NoClimberOnWayException.class)
	public void testMarkClimberOnTheWayNoClimberOnTheWay() throws RopePointInvalidUsageException, NoClimberOnWayException {
		Way way2 = gameFactory.createWay(4, 6);
		assertEquals("no free Climber to get back", null, way2.markClimberNewMarker(new Marker(Color.BLUE)));
	}

	/**
	 * Test method for {@link de.buxi.cantstop.model.Way#markClimberOnTheWay(de.buxi.cantstop.model.Marker)}.
	 * @throws RopePointInvalidUsageException 
	 * @throws NullClimberException 
	 * @throws NoClimberOnWayException 
	 */
	@Test
	public void testMarkClimberOnTheWayKoord2() throws RopePointInvalidUsageException, NullClimberException, NoClimberOnWayException {
		Way way2 = gameFactory.createWay(4, 6);
		Climber climber = new Climber();
		way2.getRopePoints().get(1).placeClimber(climber);
		
		Climber resultClimber = way2.markClimberNewMarker(new Marker(Color.BLUE));
		assertEquals("Climbers should be same", climber, resultClimber);
		assertFalse("no Climber should be on the rope", way2.isClimberOnRope());
		assertFalse("no Climber should be in the hut", way2.isClimberInHut());
		assertEquals("Marker should be on Koord2", 1, way2.whereIsMarkerForColor(Color.BLUE));
	}
	
	/**
	 * Test method for {@link de.buxi.cantstop.model.Way#markClimberOnTheWay(de.buxi.cantstop.model.Marker)}.
	 * @throws RopePointInvalidUsageException 
	 * @throws NullClimberException 
	 * @throws InvalidClimberMovementException 
	 * @throws NoClimberOnWayException 
	 */
	@Test
	public void testMarkClimberOnTheWayKoord2AlreadyMarked() throws RopePointInvalidUsageException, NullClimberException, InvalidClimberMovementException, NoClimberOnWayException {
		Way way2 = gameFactory.createWay(4, 6);
		Climber climber = new Climber();
		way2.getRopePoints().get(1).placeClimber(climber);
		
		Climber resultClimber = way2.markClimberNewMarker(new Marker(Color.BLUE));
		assertEquals("Climbers should be same", climber, resultClimber);
		assertFalse("no climber should be on the rope", way2.isClimberOnRope());
		assertFalse("no climber should be in the hut", way2.isClimberInHut());
		assertEquals("Marker should be on Koord2", 1, way2.whereIsMarkerForColor(Color.BLUE));
		
		//second Rund
		way2.placeClimberAfterMarker(climber, Color.BLUE);
		resultClimber = way2.markClimberMoveMarker(Color.BLUE);
		assertEquals("Climber sollen same sein", climber, resultClimber);
		assertFalse("no climber should be on the rope be", way2.isClimberOnRope());
		assertFalse("no Climber should be in the hut", way2.isClimberInHut());
		assertEquals("Marker should be on Koord2", 2, way2.whereIsMarkerForColor(Color.BLUE));
	}

	/**
	 * Test method for {@link de.buxi.cantstop.model.Way#markClimberOnTheWay(de.buxi.cantstop.model.Marker)}.
	 * @throws RopePointInvalidUsageException 
	 * @throws NullClimberException 
	 * @throws NoClimberOnWayException 
	 */
	@Test
	public void testMarkClimberOnTheWayInHut() throws RopePointInvalidUsageException, NullClimberException, NoClimberOnWayException {
		Way way2 = gameFactory.createWay(4, 6);
		Climber climber = new Climber();
		way2.getHut().placeClimber(climber);
		
		Climber resultClimber = way2.markClimberNewMarker(new Marker(Color.BLUE));
		assertEquals("Climber should be the same be", climber, resultClimber);
		assertFalse("no climber should be on the rope be", way2.isClimberOnRope());
		assertFalse("no Climber should be in the hut", way2.isClimberInHut());
		assertTrue("Marker should be in the hut", way2.isBlocked());
		assertFalse("Marker should be in the hut", way2.isMarkerOnRope(Color.BLUE));
	}
	
	/**
	 * Test method for {@link de.buxi.cantstop.model.Way#isClimberInHut()}.
	 * @throws RopePointInvalidUsageException 
	 * @throws NullClimberException 
	 */
	@Test
	public void testIsClimberInHut() throws RopePointInvalidUsageException, NullClimberException {
		Way way2 = gameFactory.createWay(2, 2);
		assertFalse("Hut must be empty", way2.isClimberInHut());
		way2.getHut().placeClimber(new Climber());
		assertTrue("Hut must be blocked", way2.isClimberInHut());
	}
	
	@Test
	public void testRemoveClimberFromHut() throws RopePointInvalidUsageException, NullClimberException {
		Way way2 = gameFactory.createWay(4, 6);
		Climber climber = new Climber();
		way2.getHut().placeClimber(climber);
		
		Climber resultClimber = way2.removeClimberFromHut();
		assertEquals("Climber should be the same be", climber, resultClimber);
		assertFalse("no climber should be on the rope be", way2.isClimberOnRope());
		assertFalse("no Climber should be in the hut", way2.isClimberInHut());
		assertTrue("Marker should not be in the hut", way2.isFree());
	}
	
	@Test(expected=RopePointInvalidUsageException.class)
	public void testRemoveClimberFromHutEmptyHut() throws RopePointInvalidUsageException {
		Way way2 = gameFactory.createWay(4, 6);
		Climber resultClimber = way2.removeClimberFromHut();
		assertNull("no Climber is in the hut", resultClimber);
	}

	@Test
	public void testRemoveClimberFromRope() throws RopePointInvalidUsageException, NullClimberException, InvalidClimberMovementException {
		Way way2 = gameFactory.createWay(4, 6);
		Climber climber = new Climber();
		way2.getFirstRopePoint().placeClimber(climber);
		Climber resultClimber = way2.removeClimberFromRope();
		assertEquals("Climber should be the same be", climber, resultClimber);
	}
	
	@Test(expected=InvalidClimberMovementException.class)
	public void testRemoveClimberFromEmptyRope() throws InvalidClimberMovementException, RopePointInvalidUsageException {
		Way way2 = gameFactory.createWay(4, 6);
		Climber resultClimber = way2.removeClimberFromRope();
		assertNull("no Climber is in the hut", resultClimber);
	}

	
}
