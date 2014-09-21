package de.buxi.cantstop.model;

import java.io.Serializable;

public class Marker implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6012552362823161046L;
	private Color color;

	/**
	 * @param color
	 */
	public Marker(Color color) {
		super();
		this.color = color;
	}

	/**
	 * @return the color
	 */
	public Color getColor() {
		return color;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((color == null) ? 0 : color.hashCode());
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
		Marker other = (Marker) obj;
		if (color != other.color)
			return false;
		return true;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Marker [color=");
		builder.append(color);
		builder.append("]");
		return builder.toString();
	}

	public String display() {
		StringBuilder builder = new StringBuilder();
		builder.append(color.toString().charAt(0));
		return builder.toString();
	} 
}
