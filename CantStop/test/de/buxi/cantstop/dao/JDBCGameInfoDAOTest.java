/**
 * 
 */
package de.buxi.cantstop.dao;


import java.util.Date;
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

import de.buxi.cantstop.model.DiceNotThrownException;
import de.buxi.cantstop.model.GameController;
import de.buxi.cantstop.model.GameTransferObject;
import de.buxi.cantstop.model.InvalidWayNumberException;
import de.buxi.cantstop.utils.ObjectManipulationHelper;
import static org.junit.Assert.*;

/**
 * @author buxi
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:test/test-database-context.xml")
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DbUnitTestExecutionListener.class })
public class JDBCGameInfoDAOTest {
	@Autowired
	protected ApplicationContext ac;
		
	@Test
	public void testInsert() throws DiceNotThrownException, InvalidWayNumberException {
		JDBCGameInfoDAO dao = (JDBCGameInfoDAO)ac.getBean("gameInfoDao");
		GameController gameController = (GameController)ac.getBean("gameController");
		GameTransferObject to = gameController.doGameStart();
		dao.insert(1, new java.sql.Timestamp(new Date().getTime()), "testMethod",1, ObjectManipulationHelper.serializeAndPack(to), to.description);
	}
	
	@Test
	public void testReadAllShortGameInfo() throws DiceNotThrownException, InvalidWayNumberException {
		JDBCGameInfoDAO dao = (JDBCGameInfoDAO)ac.getBean("gameInfoDao");
		GameController gameController = (GameController)ac.getBean("gameController");
		GameTransferObject to = gameController.doGameStart();
		dao.insert(1, new java.sql.Timestamp(new Date().getTime()), "testMethod",1, ObjectManipulationHelper.serializeAndPack(to), to.description);
		
		List<GameInfoShortTO> result = dao.readAllShortGameInfo();
		assertNotNull("can't be null" , result);
		assertTrue("minimum one row expected" , result.size() > 0);
	}
	
	@Test
	public void testReadFullInfoByGameId() throws DiceNotThrownException, InvalidWayNumberException {
		JDBCGameInfoDAO dao = (JDBCGameInfoDAO)ac.getBean("gameInfoDao");
		GameController gameController = (GameController)ac.getBean("gameController");
		GameTransferObject to = gameController.doGameStart();
		dao.insert(to.gameId, new java.sql.Timestamp(new Date().getTime()), "testMethod",-1, ObjectManipulationHelper.serializeAndPack(to), to.description);
		
		List<GameInfoFullTO> result = dao.readFullInfoByGameId(to.gameId);
		assertNotNull("can't be null" , result);
		assertEquals("1 row expected" , 1, result.size());
		
		GameInfoFullTO gameInfoFull = result.get(0);
		assertEquals("gameId check" , to.gameId, gameInfoFull.getGameId());
		assertEquals("gameId check2" , to.gameId, gameInfoFull.getTransferObject().gameId);
		assertEquals("methodName check" , "testMethod", gameInfoFull.getMethodName());
		assertEquals("description check" , to.description, gameInfoFull.getDescription());
		assertEquals("description check2" , to.description, gameInfoFull.getTransferObject().description);
		assertEquals("playerId check" , -1, gameInfoFull.getPlayerId());
	}
}
