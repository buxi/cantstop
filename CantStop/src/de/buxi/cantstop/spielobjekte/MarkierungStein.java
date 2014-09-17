package de.buxi.cantstop.spielobjekte;

public class MarkierungStein {
	private Farbe farbe;

	/**
	 * @param farbe
	 */
	public MarkierungStein(Farbe farbe) {
		super();
		this.farbe = farbe;
	}

	/**
	 * @return the farbe
	 */
	public Farbe getFarbe() {
		return farbe;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((farbe == null) ? 0 : farbe.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MarkierungStein other = (MarkierungStein) obj;
		if (farbe != other.farbe)
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		//builder.append("MarkierungStein [farbe=");
		builder.append(farbe.toString().charAt(0));
		//builder.append("]");
		return builder.toString();
	} 
	
	
}
