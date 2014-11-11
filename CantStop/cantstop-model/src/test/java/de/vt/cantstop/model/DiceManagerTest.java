/**
 * 
 */
package de.vt.cantstop.model;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import de.vt.cantstop.model.Dice;
import de.vt.cantstop.model.DiceManager;
import de.vt.cantstop.model.DiceNotGivenException;
import de.vt.cantstop.model.Player;
import static org.mockito.Mockito.*;

/**
 * @author buxi
 *
 */
public class DiceManagerTest extends SpringLoaderSuperClassModelTests{
	private static List<Dice> emptyDicesList = new ArrayList<>();
	
	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * Test method for {@link de.de.vt.cantstop.model.DiceManager#getOwner()}.
	 * @throws DiceNotGivenException 
	 */
	@Test(expected=DiceNotGivenException.class)
	public void testConstructorWithNull() {
		new DiceManager(null);
	}
	
	/**
	 * Test method for {@link de.de.vt.cantstop.model.DiceManager#getOwner()}.
	 */
	@Test
	public void testGetOwnerWithNull() {
		DiceManager manager = new DiceManager(emptyDicesList);
		assertNull("Null Owner", manager.getOwner());
	}

	/**
	 * Test method for {@link de.de.vt.cantstop.model.DiceManager#giveDices(de.de.vt.cantstop.model.Player)}.
	 */
	@Test
	public void testGrabDices() {
		DiceManager manager = new DiceManager(emptyDicesList);
		Player newOwner = mock(Player.class);
		assertTrue("grab should be successful", manager.giveDices(newOwner));
		assertSame("gra should owner modify", newOwner, manager.getOwner());
	}

	/**
	 * Test method for {@link de.de.vt.cantstop.model.DiceManager#getOwner()}.
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
	
	@Test
	public void testLastThrow() {
		DiceManager manager = (DiceManager)ac.getBean("diceManager");
		manager.throwAllDices();
		List<Dice> dicesFirstThrow = manager.getDicesClone();
		manager.reset();
		List<Dice> previousThrow = manager.getLastThrow();
		assertEquals("must be same: ", dicesFirstThrow,  previousThrow);
	}
}
