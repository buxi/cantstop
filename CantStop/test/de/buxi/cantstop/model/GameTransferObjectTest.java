package de.buxi.cantstop.model;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import org.junit.Before;
import org.junit.Test;

import de.buxi.cantstop.model.DiceNotThrownException;
import de.buxi.cantstop.model.GameController;
import de.buxi.cantstop.model.GameState;
import de.buxi.cantstop.model.InvalidWayNumberException;
import de.buxi.cantstop.service.TooManyPlayerException;

public class GameTransferObjectTest extends SpringLoaderSuperClassWebUITests{

	@Before
	public void setUp() throws Exception {
	}

	
	@Test
	public void testGetJoinedPlayersListAJAX() throws TooManyPlayerException, DiceNotThrownException, InvalidWayNumberException {
		GameController gameController = (GameController)ac.getBean("gameController");
		GameTransferObject to = gameController.doGetTransferObject();
		
		String joinedPlayers = to.getJoinedPlayersList();
		assertEquals("nobody joined yet", "", joinedPlayers);
		
		gameController.doAddPlayer("aaa");
		to = gameController.doGetTransferObject();
		joinedPlayers = to.getJoinedPlayersList();
		assertEquals("only aaa joined", "aaa", joinedPlayers);
		
		gameController.doAddPlayer("bbb");
		to = gameController.doGetTransferObject();
		joinedPlayers = to.getJoinedPlayersList();
		assertEquals("aaa and bbb joined", "aaa, bbb", joinedPlayers);
		
		assertEquals("gameStatus should be changed to ENOUGH_PLAYER", GameState.ENOUGH_PLAYER, gameController.getGameStatus());
	}
	
	@Test
	public void testGetDescription() throws TooManyPlayerException, DiceNotThrownException, InvalidWayNumberException {
		GameController gameController = (GameController)ac.getBean("gameController");
		gameController.doAddPlayer("aaa");
		gameController.doAddPlayer("bbb");
		GameTransferObject to = gameController.doGameStart();
		assertNotNull("description must be filled", to.getDescription());
	}
	
	@Test
	public void testSerializable() throws TooManyPlayerException, DiceNotThrownException, InvalidWayNumberException, IOException  {
		GameController gameController = (GameController)ac.getBean("gameController");
		gameController.doAddPlayer("aaa");
		gameController.doAddPlayer("bbb");
		GameTransferObject to = gameController.doGameStart();
		
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ObjectOutputStream outObj = new ObjectOutputStream(out);
		outObj.writeObject(to);
	}
}
