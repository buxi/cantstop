/**
 * 
 */
package de.buxi.cantstop.model;


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
@ContextConfiguration("file:test/test-aop-robot-context.xml")
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DbUnitTestExecutionListener.class })
public class AutoPlayerRobotTest {
	@Autowired
	protected ApplicationContext ac;
		
	@Test
	public void testAutoplayerStartInAspect() throws GameException {
		GameService gameService = (GameService)ac.getBean("gameService");
		gameService.addAutoPlayer();
		
		GameTransferObject to = gameService.startGame();
		gameService.startTurn();
		gameService.finishTurn(Integer.toString(to.actualPlayer.getOrder()));
		gameService.finishTurn(Integer.toString(to.actualPlayer.getOrder()));
		
		// 3. player is autoplayer, at the end of its activity the 1. player should be in turn
		GameTransferObject to4 = gameService.getAllGameInformation();
		assertEquals("Player 0 should be in turn after autoplayer", "0", to4.getActualPlayerId());
	}
	
	
}
