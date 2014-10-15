package de.buxi.cantstop.model;


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
 * the application model
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/cantstopGameBeans.xml")
public class SpringLoaderSuperClassModelTests {
	@Autowired
	protected ApplicationContext ac;
	
	@Before
	public void setUp() throws Exception {
	}
	@Test
	public void testSelf() {
		
	}
}
