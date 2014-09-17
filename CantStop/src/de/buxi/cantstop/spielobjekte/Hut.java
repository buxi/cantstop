/**
 * 
 */
package de.buxi.cantstop.spielobjekte;

/**
 * @author buxi
 *
 */
public class Hut extends AbstractRopePoint {
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

	public void unmark() throws RopePointInvalidUsageException {
		if (this.marker == null) {
			throw new RopePointInvalidUsageException("RopePoint not marked");
		}
		this.marker = null;
	}
}
