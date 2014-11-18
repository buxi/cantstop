/**
 * 
 */
package de.vt.cantstop.model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;

import de.vt.cantstop.model.Climber;
import de.vt.cantstop.model.Color;
import de.vt.cantstop.model.Marker;
import de.vt.cantstop.model.NoMarkerIsAvailableException;
import de.vt.cantstop.model.NotAvailableClimberException;
import de.vt.cantstop.model.Player;

/**
 * @author buxi
 *
 */
public class PlayerTest {
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * Test method for {@link de.de.vt.cantstop.model.Player#aquireOneClimber()}.
	 * @throws NotAvailableClimberException 
	 */
	@Test(expected=NotAvailableClimberException.class)
	public void testGetOneClimberHasNo() throws NotAvailableClimberException {
		Player player = new Player(1, "testPlayer", Color.BLUE);
		player.aquireOneClimber();
	}

	/**
	 * Test method for {@link de.de.vt.cantstop.model.Player#aquireOneClimber()}.
	 */
	@Test(expected=NotAvailableClimberException.class)
	public void testGetOneClimberWith2Climber() throws NotAvailableClimberException {
		Player player = new Player(1, "testPlayer", Color.BLUE);
		Collection<Climber> climbers = new ArrayList<>(2);
		climbers.add(new Climber());
		climbers.add(new Climber());
		player.addClimbers(climbers);
		
		assertNotNull("he has Climber",player.aquireOneClimber());
		assertNotNull("he has Climber",player.aquireOneClimber());
		assertNull("he has no Climber",player.aquireOneClimber());
	}
	
	/**
	 * Test method for {@link de.de.vt.cantstop.model.Player#aquireOneMarker()}.
	 * @throws NoMarkerIsAvailableException 
	 */
	@Test(expected=NoMarkerIsAvailableException.class)
	public void testGetOneMarkerHasNo() throws NoMarkerIsAvailableException {
		Player player = new Player(1, "testPlayer", Color.BLUE);
		player.aquireOneMarker();
	}
	
	/**
	 * Test method for {@link de.de.vt.cantstop.model.Player#aquireOneMarker()}.
	 * @throws NoMarkerIsAvailableException 
	 */
	@Test(expected=NoMarkerIsAvailableException.class)
	public void testGetOneMarkerWith2Markers() throws NoMarkerIsAvailableException {
		Player player = new Player(1, "testPlayer", Color.BLUE);
		Collection<Marker> markers = new ArrayList<>(2);
		markers.add(new Marker(Color.BLUE));
		markers.add(new Marker(Color.BLUE));
		player.addMarkers(markers);
		
		assertNotNull("he has Marker",player.aquireOneMarker());
		assertNotNull("he has Marker",player.aquireOneMarker());
		assertNull("he has keinen Marker",player.aquireOneMarker());
	}
	
	@Test
	public void testRemoveAllMarkers(){
		Player player = new Player(1, "testPlayer", Color.BLUE);
		Collection<Marker> markers = new ArrayList<>(2);
		markers.add(new Marker(Color.BLUE));
		markers.add(new Marker(Color.BLUE));
		player.addMarkers(markers);
		
		Collection<Marker> removedMarkers = player.removeAllMarkers();
		assertEquals("player should have 0 markers", 0, player.getMarkers().size());
		assertEquals("2 markers should be in removed collection", 2, removedMarkers.size());
	}
}
