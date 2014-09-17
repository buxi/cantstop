/**
 * Objekt für 2 Würfel
 */
package de.buxi.cantstop.spielobjekte;

/**
 * @author buxi
 *
 */
public class WurfelPaar {
	private Wurfel erste;
	private Wurfel zweite;
	
	
	/**
	 * @param erste
	 * @param zweite
	 */
	public WurfelPaar(Wurfel erste, Wurfel zweite) {
		super();
		this.erste = erste;
		this.zweite = zweite;
	}


	/**
	 * @return Summe der Werte der Würfel
	 * @throws WurfelNichtGeworfenException
	 */
	public int getSumme() throws WurfelNichtGeworfenException{
		return erste.getWert() + zweite.getWert();
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((erste == null) ? 0 : erste.hashCode());
		result = prime * result + ((zweite == null) ? 0 : zweite.hashCode());
		return result;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		//TODO Check
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WurfelPaar other = (WurfelPaar) obj;
		if (erste == null) {
			if (other.erste != null)
				return false;
		} else if (!erste.equals(other.erste))
			return false;
		if (zweite == null) {
			if (other.zweite != null)
				return false;
		} else if (!zweite.equals(other.zweite))
			return false;
		return true;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("[");
		builder.append(erste);
		builder.append(",");
		builder.append(zweite);
		builder.append("]");
		return builder.toString();
	}
}
