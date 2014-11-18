package de.vt.cantstop.selenium;

/*import java.util.concurrent.TimeUnit;

import org.junit.*;

import static org.junit.Assert.*;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
*/
public class WelcomePageTest {
	/*private WebDriver driver;
	private String baseUrl;

	private StringBuffer verificationErrors = new StringBuffer();

		@Before
	public void setUp() throws Exception {
		driver = new FirefoxDriver();
		baseUrl = "http://cantstop-refs.rhcloud.com/";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		
		
	}
 TODO production UI tests are removed
	@Test
	public void testAddplayer() throws Exception {
		driver.get(baseUrl + "CantStop/");
		driver.findElement(By.id("playerName")).clear();
		driver.findElement(By.id("playerName")).sendKeys("asdasdasd");
		driver.findElement(By.name("addPlayerButton")).click();
		assertTrue(driver.getPageSource().contains("asdasdasd"));
		assertTrue(driver.getPageSource().contains("Einladung einer Roboter Spieler"));
	}
	@Test
	public void testAdd2players() throws Exception {
		driver.get(baseUrl + "CantStop/");
		driver.findElement(By.id("playerName")).clear();
		driver.findElement(By.id("playerName")).sendKeys("asdasdasd");
		driver.findElement(By.name("addPlayerButton")).click();
		assertTrue(driver.getPageSource().contains("asdasdasd"));
		assertTrue(driver.getPageSource().contains("Einladung einer Roboter Spieler"));
		
		WebDriver driver2 = new FirefoxDriver();
		driver2.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver2 .get(baseUrl + "CantStop/");
		driver2.findElement(By.id("playerName")).clear();
		driver2.findElement(By.id("playerName")).sendKeys("aaa");
		driver2.findElement(By.name("addPlayerButton")).click();
		assertTrue(driver2.getPageSource().contains("aaa"));
		assertTrue(driver2.getPageSource().contains("asdasdasd"));
		assertTrue(driver2.getPageSource().contains("Einladung einer Roboter Spieler"));
		driver2.quit();
	}
	
	@After
	public void tearDown() throws Exception {
		// game finish
		driver.get(baseUrl + "CantStop/playAJAX?playerId=0");
		driver.findElement(By.name("finishGameButton")).click();
		driver.quit();
		
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}
*/
}
