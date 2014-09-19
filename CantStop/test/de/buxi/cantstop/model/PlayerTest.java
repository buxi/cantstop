/**
 * 
 */
package de.buxi.cantstop.model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import de.buxi.cantstop.model.Climber;
import de.buxi.cantstop.model.Color;
import de.buxi.cantstop.model.Marker;
import de.buxi.cantstop.model.NoMarkerIsAvailableException;
import de.buxi.cantstop.model.NotAvailableClimberException;
import de.buxi.cantstop.model.Player;

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
	 * Test method for {@link de.buxi.cantstop.model.Player#getOneClimber()}.
	 * @throws NotAvailableClimberException 
	 */
	@Test(expected=NotAvailableClimberException.class)
	public void testGetOneClimberHasNo() throws NotAvailableClimberException {
		Player player = new Player(1, "testPlayer", Color.BLUE);
		player.getOneClimber();
	}

	/**
	 * Test method for {@link de.buxi.cantstop.model.Player#getOneClimber()}.
	 */
	@Test(expected=NotAvailableClimberException.class)
	public void testGetOneClimberWith2Climber() throws NotAvailableClimberException {
		Player player = new Player(1, "testPlayer", Color.BLUE);
		Collection<Climber> climbers = new ArrayList<Climber>(2);
		climbers.add(new Climber());
		climbers.add(new Climber());
		player.addClimbers(climbers);
		
		assertNotNull("he has Climber",player.getOneClimber());
		assertNotNull("he has Climber",player.getOneClimber());
		assertNull("he has no Climber",player.getOneClimber());
	}
	
	/**
	 * Test method for {@link de.buxi.cantstop.model.Player#getOneMarker()}.
	 * @throws NoMarkerIsAvailableException 
	 */
	@Test(expected=NoMarkerIsAvailableException.class)
	public void testGetOneMarkerHasNo() throws NoMarkerIsAvailableException {
		Player player = new Player(1, "testPlayer", Color.BLUE);
		player.getOneMarker();
	}
	
	/**
	 * Test method for {@link de.buxi.cantstop.model.Player#getOneMarker()}.
	 * @throws NoMarkerIsAvailableException 
	 */
	@Test(expected=NoMarkerIsAvailableException.class)
	public void testGetOneMarkerWith2Markers() throws NoMarkerIsAvailableException {
		Player player = new Player(1, "testPlayer", Color.BLUE);
		Collection<Marker> steine = new ArrayList<Marker>(2);
		steine.add(new Marker(Color.BLUE));
		steine.add(new Marker(Color.BLUE));
		player.addMarkers(steine);
		
		assertNotNull("he has Marker",player.getOneMarker());
		assertNotNull("he has Marker",player.getOneMarker());
		assertNull("he has keinen Marker",player.getOneMarker());
	}
}
