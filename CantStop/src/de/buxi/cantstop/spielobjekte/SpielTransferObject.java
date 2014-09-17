/**
 * 
 */
package de.buxi.cantstop.spielobjekte;

import java.util.Collection;
import java.util.List;

/**
 * @author buxi
 * Transfer Object von SpielKontroller
 * ein Grosses
 * ist notig http://www.oracle.com/technetwork/articles/javaee/transferobject-139757.html
 * 
 * TODO UNITTEST ?
 */

public class SpielTransferObject {
	public SpielState spielStatus;
	public Spieler actuelleSpieler;
	public List<ZweiWurfelPaar> moeglichePaarungen;
	public Collection<ZweiWurfelPaar> falschePaarungen;
	public String brettDisplay;
	public int actuelleSpielerNummer;
	public List<Spieler> spielerList;
	public Collection<Wurfel> wurfels;
	public String errorMessage;
	
}
