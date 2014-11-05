/**
 * 
 */
package de.buxi.cantstop.model;


import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.github.springtestdbunit.DbUnitTestExecutionListener;

import de.buxi.cantstop.model.GameTransferObject;
import de.buxi.cantstop.service.GameException;
import de.buxi.cantstop.service.GameService;
import static org.junit.Assert.*;

/**
 * @author buxi
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:test/test-aop-context.xml")
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DbUnitTestExecutionListener.class })
public class AutoPlayerTest {
	@Autowired
	protected ApplicationContext ac;
		
	@Test
	public void testDoAddAutoplayer() throws GameException {
		GameService gameService = (GameService)ac.getBean("gameService");
		String autoplayerId = gameService.addAutoPlayer();
		assertNotNull("can't be null", autoplayerId);
		GameTransferObject to = gameService.getAllGameInformation();
		List<Player> players = to.getPlayerList();
		for (Player player : players) {
			if (player.getOrder() == Integer.valueOf(autoplayerId)) {
				assertTrue("must be autoplayer", player.getAutoPlayer());
			}
			else {
				assertFalse("can't be autoplayer", player.getAutoPlayer());
			}
		}
	}	
}
