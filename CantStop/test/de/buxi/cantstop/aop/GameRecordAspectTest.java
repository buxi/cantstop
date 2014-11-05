/**
 * 
 */
package de.buxi.cantstop.aop;


import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import com.github.springtestdbunit.DbUnitTestExecutionListener;

import de.buxi.cantstop.dao.GameInfoFullTO;
import de.buxi.cantstop.dao.JDBCGameInfoDAO;
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
public class GameRecordAspectTest {
	@Autowired
	protected ApplicationContext ac;
		
	@Test
	public void testRecordFinishTurn() throws GameException  {
		JDBCGameInfoDAO dao = (JDBCGameInfoDAO)ac.getBean("gameInfoDao");
		GameService gameService = (GameService)ac.getBean("gameService");
		GameTransferObject to = gameService.startGame();
		gameService.startTurn();
		gameService.finishTurn("0");
		
		List<GameInfoFullTO> result = dao.readFullInfoByGameId(to.gameId);
		assertNotNull("can't be null" , result);
		assertEquals("1 row expected" , 1, result.size());

		GameInfoFullTO row = result.get(0);
		assertEquals("methodName check", "finishTurn", row.getMethodName());
		assertEquals("playerId check", 0, row.getPlayerId());
		assertEquals("description check", to.description, row.getDescription());
		assertNotNull("to can't be null", row.getTransferObject());
	}
	
	@Test
	public void testRecordThrowDices() throws GameException  {
		JDBCGameInfoDAO dao = (JDBCGameInfoDAO)ac.getBean("gameInfoDao");
		GameService gameService = (GameService)ac.getBean("gameService");
		GameTransferObject to = gameService.startGame();
		gameService.startTurn();
		gameService.throwDices();
		
		List<GameInfoFullTO> result = dao.readFullInfoByGameId(to.gameId);
		assertNotNull("can't be null" , result);
		assertEquals("1 row expected" , 1, result.size());

		GameInfoFullTO row = result.get(0);
		assertEquals("methodName check", "throwDices", row.getMethodName());
		assertEquals("playerId check", 0, row.getPlayerId());
		assertEquals("description check", to.description, row.getDescription());
		assertNotNull("to can't be null", row.getTransferObject());
	}

	@Test
	public void testRecordExecutePairs() throws GameException {
		JDBCGameInfoDAO dao = (JDBCGameInfoDAO)ac.getBean("gameInfoDao");
		GameService gameService = (GameService)ac.getBean("gameService");
		GameTransferObject to = gameService.startGame();
		gameService.startTurn();
		GameTransferObject toThrow = gameService.throwDices();
		Set<String> pairIds = toThrow.getChoosablePairsWithId().keySet();
		
		gameService.executePairs(pairIds.iterator().next(), -1);
		
		List<GameInfoFullTO> result = dao.readFullInfoByGameId(to.gameId);
		assertNotNull("can't be null" , result);
		assertEquals("2 row expected" , 2, result.size());

		GameInfoFullTO row = result.get(0);
		assertEquals("methodName check", "throwDices", row.getMethodName());
		assertEquals("playerId check", 0, row.getPlayerId());
		assertEquals("description check", to.description, row.getDescription());
		assertNotNull("to can't be null", row.getTransferObject());
		
		GameInfoFullTO row2 = result.get(1);
		assertEquals("methodName check", "executePairs", row2.getMethodName());
		assertEquals("playerId check", 0, row2.getPlayerId());
		assertEquals("description check", to.description, row2.getDescription());
		assertNotNull("to can't be null", row2.getTransferObject());
	}
}
