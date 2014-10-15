package de.buxi.cantstop.model;

import static org.junit.Assert.*;

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
	public void getJoinedPlayersListAJAX() throws TooManyPlayerException, DiceNotThrownException, InvalidWayNumberException {
		GameController gameController = (GameController)ac.getBean("gameController");
		GameTransferObject to = gameController.doGetTransferObject();
		
		String joinedPlayers = to.getJoinedPlayersListAJAX();
		assertEquals("nobody joined yet", "", joinedPlayers);
		
		gameController.doAddPlayer("aaa");
		to = gameController.doGetTransferObject();
		joinedPlayers = to.getJoinedPlayersListAJAX();
		assertEquals("only aaa joined", "aaa", joinedPlayers);
		
		gameController.doAddPlayer("bbb");
		to = gameController.doGetTransferObject();
		joinedPlayers = to.getJoinedPlayersListAJAX();
		assertEquals("aaa and bbb joined", "aaa, bbb", joinedPlayers);
		
		assertEquals("gameStatus should be changed to ENOUGH_PLAYER", GameState.ENOUGH_PLAYER, gameController.getGameStatus());
	}
}
