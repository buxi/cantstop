/**
 * 
 */
package de.buxi.cantstop;

import java.util.Collection;
import java.util.List;
import java.util.Scanner;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import de.buxi.cantstop.spielobjekte.InvalidBergsteigerBewegungException;
import de.buxi.cantstop.spielobjekte.InvalidWegNummerException;
import de.buxi.cantstop.spielobjekte.KeinBergSteigerIstVorhandenException;
import de.buxi.cantstop.spielobjekte.KeinBergsteigerAufDemWegException;
import de.buxi.cantstop.spielobjekte.KeinMarkierungSteinIstVorhandenException;
import de.buxi.cantstop.spielobjekte.NullBergsteigerException;
import de.buxi.cantstop.spielobjekte.PaarWahlInfo;
import de.buxi.cantstop.spielobjekte.SeilPunktInvalidUsageException;
import de.buxi.cantstop.spielobjekte.SpielKontroller;
import de.buxi.cantstop.spielobjekte.SpielState;
import de.buxi.cantstop.spielobjekte.SpielTransferObject;
import de.buxi.cantstop.spielobjekte.Spieler;
import de.buxi.cantstop.spielobjekte.Wurfel;
import de.buxi.cantstop.spielobjekte.WurfelNichtGeworfenException;
import de.buxi.cantstop.spielobjekte.ZweiWurfelPaar;
/**
 * @author buxi
 *
 */
public class CantStopMain {

	/**
	 * @param args
	 * @throws WurfelNichtGeworfenException 
	 * @throws SeilPunktInvalidUsageException 
	 * @throws KeinMarkierungSteinIstVorhandenException 
	 * @throws KeinBergSteigerIstVorhandenException 
	 * @throws InvalidWegNummerException 
	 * @throws InvalidBergsteigerBewegungException 
	 * @throws NullBergsteigerException 
	 * @throws KeinBergsteigerAufDemWegException 
	 */
	public static void main(String[] args) throws WurfelNichtGeworfenException, SeilPunktInvalidUsageException, KeinMarkierungSteinIstVorhandenException, KeinBergSteigerIstVorhandenException, InvalidWegNummerException, InvalidBergsteigerBewegungException, NullBergsteigerException, KeinBergsteigerAufDemWegException {
		// TODO log exceptions
		ApplicationContext context =
				new ClassPathXmlApplicationContext("cantstopSpielBeans.xml");
		//TODO !!!!! zuruck zum normalBrett
		SpielKontroller spielKontroller = (SpielKontroller)context.getBean("spielKontroller");
		//SpielKontroller spielKontroller = (SpielKontroller)context.getBean("testSpielKontroller");
		String action = "";
		SpielTransferObject spielKontrollerTO = null;
		spielKontroller.doSpielStarten();
		spielKontroller.doSpielzugStarten();
	    do {
	    	spielKontrollerTO = spielKontroller.doGetTransferObject();
	    	System.out.println("---------------------------------------------------------");
	    	
	    	switch (action) {
			case "1": 
				if (SpielState.IN_ZUG.equals(spielKontrollerTO.spielStatus)) {
					spielKontroller.doSpielzugBeenden();
					spielKontrollerTO = spielKontroller.doGetTransferObject();
				}
				else {
					System.out.println("Spielzug beenden ist nicht erlaubt");
				}
				break;
			case "2":
				spielKontroller.doWerfen();
				spielKontrollerTO = spielKontroller.doGetTransferObject();
				break;
			case "A" :
			case "a" :
				if (SpielState.GEWORFEN.equals(spielKontrollerTO.spielStatus) && spielKontrollerTO.moeglichePaarungen.size()>0) {
					ZweiWurfelPaar gewaehltePaarung = spielKontrollerTO.moeglichePaarungen.get(0);
					if (!PaarWahlInfo.NICHTWAEHLBAR.equals(gewaehltePaarung.getPaarungWaehlbar())) {
						int wegNummer = getWegNummerVonUser(gewaehltePaarung);
						spielKontroller.doPaarung(gewaehltePaarung, wegNummer);
						spielKontrollerTO = spielKontroller.doGetTransferObject();
					}
					else {
						System.out.println("Paar ist nicht waehlbar!");
					}
				}
				break;
			case "B" :
			case "b" :
				if (SpielState.GEWORFEN.equals(spielKontrollerTO.spielStatus) && spielKontrollerTO.moeglichePaarungen.size()>1) {
					ZweiWurfelPaar gewaehltePaarung = spielKontrollerTO.moeglichePaarungen.get(1);
					if (!PaarWahlInfo.NICHTWAEHLBAR.equals(gewaehltePaarung.getPaarungWaehlbar())) {
						int wegNummer = getWegNummerVonUser(gewaehltePaarung);
						spielKontroller.doPaarung(gewaehltePaarung, wegNummer);
						spielKontrollerTO = spielKontroller.doGetTransferObject();
					}
					else {
						System.out.println("Paar ist nicht waehlbar!");
					}
				}
				break;
			case "C" :
			case "c" :
				if (SpielState.GEWORFEN.equals(spielKontrollerTO.spielStatus) && spielKontrollerTO.moeglichePaarungen.size()>2) {
					ZweiWurfelPaar gewaehltePaarung = spielKontrollerTO.moeglichePaarungen.get(2);
					if (!PaarWahlInfo.NICHTWAEHLBAR.equals(gewaehltePaarung.getPaarungWaehlbar())) {
						int wegNummer = getWegNummerVonUser(gewaehltePaarung);
						spielKontroller.doPaarung(gewaehltePaarung, wegNummer);
						spielKontrollerTO = spielKontroller.doGetTransferObject();
					}
					else {
						System.out.println("Kommando oder Paar ist nicht waehlbar!");
					}
				}
				break;
			default:
				break;
			} 
	    	
	    	System.out.println(spielKontrollerTO.brettDisplay);;
	    	int actuelleSpieler = spielKontrollerTO.actuelleSpielerNummer;
	    	List<Spieler> spielerList = spielKontrollerTO.spielerList;
	    	for (int i = 0; i < spielerList.size(); i++) {
				if (i == actuelleSpieler) {
					System.out.print("DRAN ----> ");
				}
				else {
					System.out.print("           ");
				}
				System.out.println(spielerList.get(i).display());
			}
	    	
	    	// warten auf Spieler
	    	if (SpielState.GEWORFEN.equals(spielKontrollerTO.spielStatus) || 
	    		SpielState.GEWAHLTE_PAARUNG_FALSCH.equals(spielKontrollerTO.spielStatus)) {
		    	Collection<Wurfel> wurfels = spielKontrollerTO.wurfels;
		    	System.out.println(spielKontrollerTO.actuelleSpieler.getName() + " hat geworfen:" + wurfels);
		    	spielKontrollerTO = spielKontroller.doGetTransferObject();
				System.out.println("Mögliche Paarungen:" + spielKontrollerTO.moeglichePaarungen);
				if (spielKontrollerTO.moeglichePaarungen.size()>0) {
					System.out.print("                          A              ");
				}
				if (spielKontrollerTO.moeglichePaarungen.size()>1) {
					System.out.print("B              ");
				}
				if (spielKontrollerTO.moeglichePaarungen.size()>2) {
					System.out.print("C");
				}
	    	}
			System.out.println();
			System.out.println("Error message:"+spielKontrollerTO.errorMessage);
			System.out.println(spielKontrollerTO.falschePaarungen);
	    	System.out.println("Spiel status:"+spielKontrollerTO.spielStatus);
	    	System.out.println("MENU 0:Spiel Ende, 1:Spielzug beenden 2:Werfen");
	    	if (spielKontrollerTO.moeglichePaarungen != null && spielKontrollerTO.moeglichePaarungen.size() > 0) {
	    		System.out.print("Paaren Auswahl: A");
	    		if (spielKontrollerTO.moeglichePaarungen.size()>1) {
	    			System.out.print(" B");
	    		}
	    		if (spielKontrollerTO.moeglichePaarungen.size()>2) {
	    			System.out.print(" C");
	    		}
	    		System.out.println();
	    	}
	    	
	    	if (SpielState.SPIEL_GEWONNEN.equals(spielKontrollerTO.spielStatus)) {
	    		System.out.println("Spiel gewonnen:" + spielKontroller.getActuelleSpieler().getName() + " Gratulation!");
	    		break;
	    	}
	    	Scanner in = new Scanner(System.in);
		    System.out.print("Please enter an action number: ");
		    action = in.next();      
		    System.out.println("You entered : " + action);
		    spielKontrollerTO = null;
	    } while (!"0".equals(action));
	    System.out.println("Bye bye!");
	}

	/**
	 * @param gewaehltePaarung
	 * @return
	 * @throws WurfelNichtGeworfenException
	 */
	protected static int getWegNummerVonUser(ZweiWurfelPaar gewaehltePaarung)
			throws WurfelNichtGeworfenException {
		int wegNummer = -1;
		if (PaarWahlInfo.MITWEGINFO.equals(gewaehltePaarung.getPaarungWaehlbar())) {
			Scanner in = new Scanner(System.in);
		    System.out.print("Please enter a wegnummer(" + gewaehltePaarung.getErstePaar().getSumme()+","+ gewaehltePaarung.getZweitePaar().getSumme()  + "): ");
		    wegNummer = in.nextInt();      
		    System.out.println("You entered : " + wegNummer);
		}
		return wegNummer;
	}

}
