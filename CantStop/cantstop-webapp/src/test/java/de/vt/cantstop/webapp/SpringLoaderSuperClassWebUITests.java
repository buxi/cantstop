package de.vt.cantstop.webapp;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author buxi
 * loads Spring Context and makes it visible for the tests which are related to
 * the web UI and model
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:test-webapp-context.xml")
public class SpringLoaderSuperClassWebUITests {
	@Autowired
	protected ApplicationContext ac;
	
	@Before
	public void setUp() throws Exception {
	}
	@Test
	public void testSelf() {
		
	}
}
