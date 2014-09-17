/**
 * 
 */
package de.buxi.cantstop.spielobjekte;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collection;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author buxi
 *
 */
public class SpielerTest {

	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * Test method for {@link de.buxi.cantstop.spielobjekte.Spieler#getEinBergSteiger()}.
	 * @throws KeinBergSteigerIstVorhandenException 
	 */
	@Test(expected=KeinBergSteigerIstVorhandenException.class)
	public void testGetEinBergSteigerHatKein() throws KeinBergSteigerIstVorhandenException {
		Spieler spieler = new Spieler(1, "testSpieler", Farbe.BLAU);
		spieler.getEinBergSteiger();
	}

	/**
	 * Test method for {@link de.buxi.cantstop.spielobjekte.Spieler#getEinBergSteiger()}.
	 */
	@Test(expected=KeinBergSteigerIstVorhandenException.class)
	public void testGetEinBergSteigerMit2Bergsteiger() throws KeinBergSteigerIstVorhandenException {
		Spieler spieler = new Spieler(1, "testSpieler", Farbe.BLAU);
		Collection<BergSteiger> bergsteigers = new ArrayList<BergSteiger>(2);
		bergsteigers.add(new BergSteiger());
		bergsteigers.add(new BergSteiger());
		spieler.addBergSteigers(bergsteigers);
		
		assertNotNull("er hat Bergsteiger",spieler.getEinBergSteiger());
		assertNotNull("er hat Bergsteiger",spieler.getEinBergSteiger());
		assertNull("er hat keinen Bergsteiger",spieler.getEinBergSteiger());
	}
	
	/**
	 * Test method for {@link de.buxi.cantstop.spielobjekte.Spieler#getEinMarkierungStein()}.
	 * @throws KeinMarkierungSteinIstVorhandenException 
	 */
	@Test(expected=KeinMarkierungSteinIstVorhandenException.class)
	public void testGetEinMarkierungSteinHatKein() throws KeinMarkierungSteinIstVorhandenException {
		Spieler spieler = new Spieler(1, "testSpieler", Farbe.BLAU);
		spieler.getEinMarkierungStein();
	}
	
	/**
	 * Test method for {@link de.buxi.cantstop.spielobjekte.Spieler#getEinMarkierungStein()}.
	 * @throws KeinMarkierungSteinIstVorhandenException 
	 */
	@Test(expected=KeinMarkierungSteinIstVorhandenException.class)
	public void testGetEinMarkierungSteinMit2Stein() throws KeinMarkierungSteinIstVorhandenException {
		Spieler spieler = new Spieler(1, "testSpieler", Farbe.BLAU);
		Collection<MarkierungStein> steine = new ArrayList<MarkierungStein>(2);
		steine.add(new MarkierungStein(Farbe.BLAU));
		steine.add(new MarkierungStein(Farbe.BLAU));
		spieler.addMarkierungSteine(steine);
		
		assertNotNull("er hat MarkierungStein",spieler.getEinMarkierungStein());
		assertNotNull("er hat MarkierungStein",spieler.getEinMarkierungStein());
		assertNull("er hat keinen MarkierungStein",spieler.getEinMarkierungStein());
	}

}
