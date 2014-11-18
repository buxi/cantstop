package de.vt.cantstop.model;
/**
 * 
 */


import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

/**
 * @author buxi
 *
 */
public class Hut extends AbstractRopePoint implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4498262962137650609L;
	private Marker marker;
	private int wayNumber;
	
	public Hut(int newWayNumber) {
		super();
		this.marker = null;
		this.wayNumber = newWayNumber;
	}

	/**
	 * @return the wayNumber
	 */
	public int getWayNumber() {
		return wayNumber;
	}

	/**
	 * @return the marker
	 */
	public Marker getMarker() {
		return marker;
	}

	public boolean isMarked(Color color) {
		return getMarker() != null && color.equals(getMarker().getColor());
	}
	
	@Override
	public void mark(Marker marker) throws RopePointInvalidUsageException {
		if (this.marker != null) {
			throw new RopePointInvalidUsageException("RopePoint already marked");
		}
		this.marker = marker;
	}

	public Marker unmark() throws RopePointInvalidUsageException {
		Marker result = this.marker;
		if (this.marker == null) {
			throw new RopePointInvalidUsageException("RopePoint not marked");
		}
		this.marker = null;
		return result;
	}
	
	public String display() {
		StringBuffer result = new StringBuffer();
		// displaying Hut
		result.append(" H(");
		if (getClimber() != null) {
			result.append(getClimber().display());
		}
		Marker marker = this.getMarker();
		if (marker!=null) {
			result.append(marker.getColor());
		}
		result.append(") ");
		return StringUtils.center(result.toString(), 6);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Hut [marker=");
		builder.append(marker);
		builder.append(", wayNumber=");
		builder.append(wayNumber);
		builder.append("]");
		return builder.toString();
	}
}
