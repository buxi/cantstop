/**
 * 
 */
package de.buxi.cantstop.spielobjekte;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author buxi
 * RopePoint can hold a climber and more marker
 *
 */
public class RopePoint extends AbstractRopePoint {
	
	private Collection<Marker> markers;
	
	public RopePoint() {
		super();
		this.markers = new ArrayList<Marker>();
	}

	/**
	 * @return the markers
	 */
	public Collection<Marker> getMarkers() {
		return markers;
	}
	
	/**
	 * marks the RopePoint with the marker
	 * @param marker
	 * @throws RopePointInvalidUsageException RopePoint is already marked with Color
	 */
	public void mark(Marker marker) throws RopePointInvalidUsageException{
		if (markers.contains(marker)) {
			throw new RopePointInvalidUsageException("RopePoint already marked");
		}
		markers.add(marker);
	}
	
	/**
	 * Unmark the RopePoint with  Marker
	 * @param marker
	 * @throws RopePointInvalidUsageException RopePoint was not marked with this color
	 */
	public Marker unmark(Color color) throws RopePointInvalidUsageException {
		for (Marker marker : markers) {
			if (marker.getColor().equals(color)) {
				markers.remove(marker);
				return marker;
				
			}
		}
		throw new RopePointInvalidUsageException("RopePoint was not marked");	
	}
	
	/**
	 * @param color
	 * @return true if this RopePoint marked with color
	 */
	public boolean isMarkerForColor(Color color) {
		for (Marker marker : markers) {
			if (marker.getColor().equals(color)) {
				return true;
			}
		}
		return false;
	}
}
