package de.vt.cantstop.messageutils;

import static org.junit.Assert.*;

import java.util.Locale;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import de.vt.cantstop.messageutils.MessageHelper;
import de.vt.cantstop.model.DiceNotThrownException;
import de.vt.cantstop.model.GameController;
import de.vt.cantstop.model.GameTransferObject;
import de.vt.cantstop.model.InvalidWayNumberException;
import de.vt.cantstop.model.TooManyPlayerException;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test-webapp-context.xml")
public class MessageHelperTest {
	@Autowired
	protected ApplicationContext ac;

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
