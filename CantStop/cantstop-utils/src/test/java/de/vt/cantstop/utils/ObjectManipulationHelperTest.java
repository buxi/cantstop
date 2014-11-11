package de.vt.cantstop.utils;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import de.vt.cantstop.model.Color;
import de.vt.cantstop.model.Player;
import de.vt.cantstop.utils.ObjectManipulationHelper;

public class ObjectManipulationHelperTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testEncodeAndDecode() {
		Player player = new Player("a", Color.BLUE);

		// encoding
		byte[] encodedPlayer = ObjectManipulationHelper.serializeAndPack(player);
		assertNotNull("can't be null", encodedPlayer);
		assertTrue("must have values", encodedPlayer.length > 0);
		
		//decoding
		Player decodedPlayer = (Player)ObjectManipulationHelper.unpackAndDeserialize(encodedPlayer);
		assertNotNull("can't be null", decodedPlayer);
		
		//checking decoded object
		assertEquals("must be same", player.getName(), decodedPlayer.getName());
		assertEquals("must be same", player.getColor(), decodedPlayer.getColor());
	}
}
