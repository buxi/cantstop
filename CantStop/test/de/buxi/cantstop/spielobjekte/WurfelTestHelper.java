/**
 * 
 */
package de.buxi.cantstop.spielobjekte;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author buxi
 *
 */
public class WurfelTestHelper {
	/**
	 * @param wurfelZahl wieviel eingestellte NormalWurfel wurde generiert
	 * @return List der generierten NormalWurfel 
	 * @throws InvalidTestParametersException 
	 * @throws WurfelNichtGeworfenException 
	 */
	public static List<Wurfel> generateXeingestellteNormalWurfel(List<Integer> werte) throws InvalidTestParametersException, WurfelNichtGeworfenException {
		if (werte == null) {
			throw new InvalidTestParametersException();
		}
		List<Wurfel> wurfels = new ArrayList<Wurfel>(werte.size());

		for (int i = 0; i<werte.size(); i++ ) {
			// schaffen ein MockObjekt mit Werten
			Wurfel mockWurfel = mock(Wurfel.class);
			when(mockWurfel.getWert()).thenReturn(werte.get(i));
			when(mockWurfel.isGeworfen()).thenReturn(true);
			when(mockWurfel.toString()).thenReturn(werte.get(i).toString());
			wurfels.add(mockWurfel);
		}

		return wurfels;
	}

	/**
	 *	mögliche Paaren: 2	Summe
	 *			3 5 : 3 6 -> 8 9
	 *		    3 3 : 5 6 -> 6 11
 	 *  kontrollString : 68911
	 * @param paaren WurfelPaaren
	 * @return KontrollString
	 * @throws WurfelNichtGeworfenException
	 */
	public static String generatePaarungKontrollString(List<ZweiWurfelPaar> paaren) throws WurfelNichtGeworfenException {
		List<Integer> paarSummen = new ArrayList<Integer>();
		
		for (ZweiWurfelPaar zweiWurfelPaar : paaren) {
			WurfelPaar wurfelPaar1 = zweiWurfelPaar.getErstePaar();
			WurfelPaar wurfelPaar2 = zweiWurfelPaar.getZweitePaar();
			paarSummen.add(wurfelPaar1.getSumme());
			paarSummen.add(wurfelPaar2.getSumme());
		}
		Collections.sort(paarSummen);
		StringBuilder result = new StringBuilder();
		for (Integer summe : paarSummen) {
			result.append(summe.toString());	
		}
		return result.toString();
	}
}
