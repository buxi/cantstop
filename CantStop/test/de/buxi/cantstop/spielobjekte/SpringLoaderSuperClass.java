package de.buxi.cantstop.spielobjekte;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author buxi
 * einfach laedt die Spring Context und mach sie sichtbar fur weitere Tests
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("file:src/cantstopSpielBeans.xml")
public class SpringLoaderSuperClass {
	@Autowired
	protected ApplicationContext ac;
	
	@Before
	public void setUp() throws Exception {
	}
	@Test
	public void testSelf() {
		
	}
}
