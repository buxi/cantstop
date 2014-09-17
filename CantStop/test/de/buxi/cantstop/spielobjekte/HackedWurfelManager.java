/**
 * 
 */
package de.buxi.cantstop.spielobjekte;

import java.util.List;


/**
 * @author buxi
 *
 */
public class HackedWurfelManager extends WurfelManager {

	public HackedWurfelManager(List<Wurfel> wurfels)
			throws NichtWurfelGegebenException {
		super(wurfels);
	}

	/* (non-Javadoc)
	 * @see de.buxi.cantstop.spielobjekte.WurfelManager#allWerfen()
	 * unerlauben das Werfen
	 */
	@Override
	public void allWerfen() {
		//super.allWerfen();
	}
	
}
