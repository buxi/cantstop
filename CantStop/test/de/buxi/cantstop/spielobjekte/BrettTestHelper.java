package de.buxi.cantstop.spielobjekte;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author buxi
 * Helpermethoden fuer Bretttesten 
 */
public class BrettTestHelper {
	
	
	
	public static Collection<BergSteiger> stellBergsteigerAufDemWegeAlleNebenHutte(Brett brett, Collection<Integer>wegNummers ) throws InvalidWegNummerException, SeilPunktInvalidUsageException, NullBergsteigerException {
		Collection<BergSteiger> bergSteigers = new ArrayList<BergSteiger>();
		for (Integer wegNummer : wegNummers) {
			Weg weg = brett.getWegByNummer(wegNummer);
			BergSteiger bergSteiger = new BergSteiger();
			bergSteigers.add(bergSteiger);
			weg.getObersteSeilPunkt().bergSteigerStellen(bergSteiger);
		}
		return bergSteigers;
	}
	
	public static Collection<BergSteiger> stellBergsteigerAufDemWegeAlleInHutte(Brett brett, Collection<Integer>wegNummers ) throws InvalidWegNummerException, SeilPunktInvalidUsageException, NullBergsteigerException {
		Collection<BergSteiger> bergSteigers = new ArrayList<BergSteiger>();
		for (Integer wegNummer : wegNummers) {
			Weg weg = brett.getWegByNummer(wegNummer);
			BergSteiger bergSteiger = new BergSteiger();
			bergSteigers.add(bergSteiger);
			weg.getHutte().bergSteigerStellen(bergSteiger);
		}
		return bergSteigers;
	}

	public static Collection<BergSteiger> stellBergsteigerAufDemWegeAlleUnter(Brett brett, Collection<Integer>wegNummers ) throws InvalidWegNummerException, SeilPunktInvalidUsageException, NullBergsteigerException {
		Collection<BergSteiger> bergSteigers = new ArrayList<BergSteiger>();
		for (Integer wegNummer : wegNummers) {
			Weg weg = brett.getWegByNummer(wegNummer);
			BergSteiger bergSteiger = new BergSteiger();
			bergSteigers.add(bergSteiger);
			weg.getUntersteSeilPunkt().bergSteigerStellen(bergSteiger);
		}
		return bergSteigers;
	}

	
	public static Collection<MarkierungStein> markierenSeilPunkteAufDemWegeAlleNebenHutte(Brett brett, Collection<Integer>wegNummers, Farbe farbe ) throws InvalidWegNummerException, SeilPunktInvalidUsageException, NullBergsteigerException {
		Collection<MarkierungStein> markers = new ArrayList<MarkierungStein>();
		for (Integer wegNummer : wegNummers) {
			Weg weg = brett.getWegByNummer(wegNummer);
			MarkierungStein marker = new MarkierungStein(farbe);
			markers.add(marker);
			weg.getObersteSeilPunkt().markieren(marker);
		}
		return markers;
	}

	public static Collection<MarkierungStein> markierenSeilPunkteAufDemWegeAlleUnten(Brett brett, Collection<Integer>wegNummers, Farbe farbe ) throws InvalidWegNummerException, SeilPunktInvalidUsageException, NullBergsteigerException {
		Collection<MarkierungStein> markers = new ArrayList<MarkierungStein>();
		for (Integer wegNummer : wegNummers) {
			Weg weg = brett.getWegByNummer(wegNummer);
			MarkierungStein marker = new MarkierungStein(farbe);
			markers.add(marker);
			weg.getUntersteSeilPunkt().markieren(marker);
		}
		return markers;
	}
	
	public static Collection<MarkierungStein> markierenHutten(Brett brett, Collection<Integer>wegNummers, Farbe farbe ) throws InvalidWegNummerException, SeilPunktInvalidUsageException, NullBergsteigerException {
		Collection<MarkierungStein> markers = new ArrayList<MarkierungStein>();
		for (Integer wegNummer : wegNummers) {
			Weg weg = brett.getWegByNummer(wegNummer);
			MarkierungStein marker = new MarkierungStein(farbe);
			markers.add(marker);
			weg.getHutte().markieren(marker);
		}
		return markers;
	}

}
