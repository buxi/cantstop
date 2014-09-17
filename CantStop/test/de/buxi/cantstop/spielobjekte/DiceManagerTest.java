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
public class DiceManagerTest extends SpringLoaderSuperClass{
	private static List<Dice> emptyDicesList = new ArrayList<Dice>();
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * Test method for {@link de.buxi.cantstop.spielobjekte.DiceManager#getOwner()}.
	 * @throws DiceNotGivenException 
	 */
	@Test(expected=DiceNotGivenException.class)
	public void testConstructorWithNull() {
		new DiceManager(null);
	}
	
	/**
	 * Test method for {@link de.buxi.cantstop.spielobjekte.DiceManager#getOwner()}.
	 */
	@Test
	public void testGetOwnerWithNull() {
		DiceManager manager = new DiceManager(emptyDicesList);
		assertNull("Null Owner", manager.getOwner());
	}

	/**
	 * Test method for {@link de.buxi.cantstop.spielobjekte.DiceManager#giveDices(de.buxi.cantstop.spielobjekte.Player)}.
	 */
	@Test
	public void testGrabDices() {
		DiceManager manager = new DiceManager(emptyDicesList);
		Player newOwner = mock(Player.class);
		assertTrue("grab should be successful", manager.giveDices(newOwner));
		assertSame("gra should owner modify", newOwner, manager.getOwner());
	}

	/**
	 * Test method for {@link de.buxi.cantstop.spielobjekte.DiceManager#getOwner()}.
	 */
	@Test
	public void testAllThrowWith4Dices() {
		DiceManager manager = (DiceManager)ac.getBean("diceManager");
		manager.throwAllDices();
		List<Dice> dicesAfterThrow = manager.getDices();
		for (Dice dice : dicesAfterThrow) {
			System.out.println(dice);
			assertTrue(dice.isThrown());
		}
	}
}
