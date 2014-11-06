package de.buxi.cantstop.model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class GameMessageHandlerTest {

	GameMessageHandler msgHandler;
	
	@Before
	public void setUp() throws Exception {
		msgHandler = new GameMessageHandler(3);
	}

	@Test
	public void testReset() {
		GameMessage msg1 = new GameMessage("p1", "msg1", GameMessageType.INFO);
		msgHandler.addMessage(msg1);
		msgHandler.reset();
		assertEquals("must be empty", 0, msgHandler.listMessages().size());
	}

	@Test
	public void testListMessages() {
		assertNotNull("should be never null", msgHandler.listMessages());

		GameMessage msg1 = new GameMessage("p1", "msg1", GameMessageType.INFO);
		msgHandler.addMessage(msg1);
		assertEquals("1 message in queue", 1, msgHandler.listMessages().size());
	}

	@Test
	public void testAddMessage() {
		GameMessage msg1 = new GameMessage("p1", "msg1", GameMessageType.INFO);
		msgHandler.addMessage(msg1);
		assertEquals("1 message in queue", 1, msgHandler.listMessages().size());

		GameMessage msg2 = new GameMessage("p2", "msg2", GameMessageType.INFO);
		msgHandler.addMessage(msg2);
		assertEquals("2 message in queue", 2, msgHandler.listMessages().size());

		GameMessage msg3 = new GameMessage("p3", "msg3", GameMessageType.INFO);
		msgHandler.addMessage(msg3);
		assertEquals("3 message in queue", 3, msgHandler.listMessages().size());

		GameMessage msg4 = new GameMessage("p4", "msg4", GameMessageType.INFO);
		msgHandler.addMessage(msg4);
		assertEquals("3 message must be in queue after message 4", 3, msgHandler.listMessages().size());

		// checking max message limitation
		assertEquals("0. element must be msg2", msg2, msgHandler.listMessages().get(0));
		assertEquals("1. element must be msg3", msg3, msgHandler.listMessages().get(1));
		assertEquals("2. element must be msg4", msg4, msgHandler.listMessages().get(2));
		
	}

}
