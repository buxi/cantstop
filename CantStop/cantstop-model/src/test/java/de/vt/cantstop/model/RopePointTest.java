package de.vt.cantstop.model;

import static org.junit.Assert.*;

import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import de.vt.cantstop.model.Climber;
import de.vt.cantstop.model.Color;
import de.vt.cantstop.model.Marker;
import de.vt.cantstop.model.NullClimberException;
import de.vt.cantstop.model.RopePoint;
import de.vt.cantstop.model.RopePointInvalidUsageException;

public class RopePointTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testRopePoint() {
		RopePoint ropePoint = new RopePoint();
		assertNull(ropePoint.getClimber());
		assertNotNull(ropePoint.getMarkers());
		assertEquals("should be empty", 0, ropePoint.getMarkers().size());
	}

	@Test
	public void testMarkWithNeueRopePoint() throws RopePointInvalidUsageException {
		RopePoint ropePoint = new RopePoint();
		Marker marker = new Marker(Color.BLUE);
		ropePoint.mark(marker);
		Collection<Marker> markers = ropePoint.getMarkers();
		assertTrue(markers.contains(marker));
	}
	
	@Test(expected=RopePointInvalidUsageException.class)
	public void testMarkWithAlreadyMarkedRopePoint() throws RopePointInvalidUsageException {
		RopePoint ropePoint = new RopePoint();
		Marker marker = new Marker(Color.BLUE);
		ropePoint.mark(marker);
		
		ropePoint.mark(marker);
	}
	
	@Test()
	public void testMarkWithOtherColor() throws RopePointInvalidUsageException {
		RopePoint ropePoint = new RopePoint();
		Marker marker = new Marker(Color.BLUE);
		ropePoint.mark(marker);
		Marker marker2 = new Marker(Color.YELLOW);
		ropePoint.mark(marker2);
	}
	
	@Test(expected=RopePointInvalidUsageException.class)
	public void testUnmarkWithNewRopePoint() throws RopePointInvalidUsageException {
		RopePoint ropePoint = new RopePoint();
		ropePoint.unmark(null);
	}

	@Test()
	public void testUnMarkWithTwoColors() throws RopePointInvalidUsageException {
		RopePoint ropePoint = new RopePoint();
		Marker marker = new Marker(Color.BLUE);
		ropePoint.mark(marker);
		Marker marker2 = new Marker(Color.YELLOW);
		ropePoint.mark(marker2);
		
		assertTrue("with BLUE is marked", ropePoint.isMarkerForColor(marker.getColor()));
		
		ropePoint.unmark(marker2.getColor());
		assertFalse("with YELLOW is not marked", ropePoint.isMarkerForColor(marker2.getColor()));
	}
	
	@Test
	public void testClimberPlacementWithNewRopePoint() throws RopePointInvalidUsageException, NullClimberException {
		RopePoint ropePoint = new RopePoint();
		Climber climber = new Climber();
		ropePoint.placeClimber(climber);
		assertEquals(climber, ropePoint.getClimber());
	}
	
	@Test(expected=NullClimberException.class)
	public void testClimberPlacementNullClimber() throws RopePointInvalidUsageException, NullClimberException {
		RopePoint ropePoint = new RopePoint();
		ropePoint.placeClimber(null);
	}

	@Test(expected=RopePointInvalidUsageException.class )
	public void testClimberPlacementWithAlreadyPlacedRopePoint() throws RopePointInvalidUsageException, NullClimberException {
		RopePoint ropePoint = new RopePoint();
		Climber climber = new Climber();
		ropePoint.placeClimber(climber);
		
		Climber climber2 = new Climber();
		ropePoint.placeClimber(climber2);
	}

	@Test(expected=RopePointInvalidUsageException.class)
	public void testClimberRemoveWithNewRopePoint() throws RopePointInvalidUsageException {
		RopePoint ropePoint = new RopePoint();
		ropePoint.removeClimber();
	}
	
	@Test()
	public void testClimberRemoveWithBlockedRopePoint() throws RopePointInvalidUsageException, NullClimberException {
		RopePoint ropePoint = new RopePoint();
		Climber climber = new Climber();
		ropePoint.placeClimber(climber);
		ropePoint.removeClimber();
		assertEquals(null, ropePoint.getClimber());
	}
	
	@Test()
	public void testIsMarkerForColorWithNewRopePoint() throws RopePointInvalidUsageException {
		RopePoint ropePoint = new RopePoint();
		assertFalse("New RopePoint is not marked",ropePoint.isMarkerForColor(Color.BLUE));
		
		ropePoint.mark(new Marker(Color.BLUE));
		assertTrue("marked with blue",ropePoint.isMarkerForColor(Color.BLUE));
		
		assertFalse("marked with blue",ropePoint.isMarkerForColor(Color.GREEN));
	}
}
