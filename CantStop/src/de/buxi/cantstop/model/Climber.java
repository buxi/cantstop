package de.buxi.cantstop.model;

import java.io.Serializable;

public class Climber implements Serializable{
	private static final long serialVersionUID = 4637305808600310379L;

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Climber []");
		return builder.toString();
	}

	public String display() {
		return "X";
	}
}
