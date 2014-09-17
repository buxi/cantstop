/**
 * 
 */
package de.buxi.cantstop.spielobjekte;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;

/**
 * @author buxi
 *
 */
public class WurfelManagerTest extends SpringLoaderSuperClass{
	private static List<Wurfel> leerWurfelsList = new ArrayList<Wurfel>();
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * Test method for {@link de.buxi.cantstop.spielobjekte.WurfelManager#getBesitzer()}.
	 * @throws NichtWurfelGegebenException 
	 */
	@Test(expected=NichtWurfelGegebenException.class)
	public void testConstructorMitNull() {
		new WurfelManager(null);
	}
	
	/**
	 * Test method for {@link de.buxi.cantstop.spielobjekte.WurfelManager#getBesitzer()}.
	 */
	@Test
	public void testGetBesitzerWithNull() {
		WurfelManager manager = new WurfelManager(leerWurfelsList);
		assertNull("Null Besitzer", manager.getBesitzer());
	}

	/**
	 * Test method for {@link de.buxi.cantstop.spielobjekte.WurfelManager#erwerbWurfels(de.buxi.cantstop.spielobjekte.Spieler)}.
	 */
	@Test
	public void testErwerbWurfels() {
		WurfelManager manager = new WurfelManager(leerWurfelsList);
		Spieler neuBesitzer = mock(Spieler.class);
		assertTrue("Erwerb soll erfolgen", manager.erwerbWurfels(neuBesitzer));
		assertSame("Erwerb soll Besitzer modifizieren", neuBesitzer, manager.getBesitzer());
	}

	/**
	 * Test method for {@link de.buxi.cantstop.spielobjekte.WurfelManager#getBesitzer()}.
	 */
	@Test
	public void testAllWerfenMit4Wurfels() {
		WurfelManager manager = (WurfelManager)ac.getBean("wurfelManager");
		manager.allWerfen();
		List<Wurfel> wurfelsNachWurf = manager.getWurfels();
		for (Wurfel wurfel : wurfelsNachWurf) {
			System.out.println(wurfel);
			assertTrue(wurfel.isGeworfen());
		}
	}
}
