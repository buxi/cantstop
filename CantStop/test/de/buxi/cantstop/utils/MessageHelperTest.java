package de.buxi.cantstop.utils;

import static org.junit.Assert.*;

import java.util.Locale;

import org.junit.Before;
import org.junit.Test;

import de.buxi.cantstop.model.DiceNotThrownException;
import de.buxi.cantstop.model.GameController;
import de.buxi.cantstop.model.GameTransferObject;
import de.buxi.cantstop.model.InvalidWayNumberException;
import de.buxi.cantstop.model.SpringLoaderSuperClassModelTests;
import de.buxi.cantstop.service.TooManyPlayerException;

public class MessageHelperTest extends SpringLoaderSuperClassModelTests{

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testDecorateWithLocalizedMessage() throws DiceNotThrownException, InvalidWayNumberException, TooManyPlayerException {
		GameController gameController = (GameController)ac.getBean("gameController");
		gameController.doAddPlayer("player");
		
		GameTransferObject to = gameController.doGetTransferObject();
		assertEquals("1 message must be in queue", 1, to.getMessages().size());
		MessageHelper.decorateWithLocalizedMessage(to, new Locale("de"), ac);
		assertNotNull("1. element must be decorated",  to.getMessages().get(0).getLocalizedMessage());
	}

}
