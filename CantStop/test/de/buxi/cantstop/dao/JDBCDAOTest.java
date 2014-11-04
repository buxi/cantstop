/**
 * 
 */
package de.buxi.cantstop.dao;


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
import static org.junit.Assert.*;

/**
 * @author buxi
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:test/test-database-context.xml")
@TestExecutionListeners({ DependencyInjectionTestExecutionListener.class, DbUnitTestExecutionListener.class })
public class JDBCDAOTest {
	@Autowired
	protected ApplicationContext ac;
		
	@Test
	public void testInsert() throws DiceNotThrownException, InvalidWayNumberException {
		JDBCGameInfoDAO dao = (JDBCGameInfoDAO)ac.getBean("gameInfoDao");
		GameController gameController = (GameController)ac.getBean("gameController");
		GameTransferObject to = gameController.doGameStart();
		dao.insert("testMethod",to);
	}
	
	@Test
	public void testReadAllShortGameInfo() throws DiceNotThrownException, InvalidWayNumberException {
		JDBCGameInfoDAO dao = (JDBCGameInfoDAO)ac.getBean("gameInfoDao");
		GameController gameController = (GameController)ac.getBean("gameController");
		GameTransferObject to = gameController.doGameStart();
		dao.insert("testMethod",to);
		
		List<GameInfoShortTO> result = dao.readAllShortGameInfo();
		assertNotNull("can't be null" , result);
		assertEquals("1 row expected" , 1, result.size());
	}
	
}
