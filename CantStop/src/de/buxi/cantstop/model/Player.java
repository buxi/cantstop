/**
 * 
 */
package de.buxi.cantstop.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.lang3.StringUtils;

/**
 * @author buxi
 *
 */
public class Player implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4829560351701437044L;
	private String name;
	private Color color;
	private Collection<Marker>markers;
	private int order;
	
	private Collection<Climber>climbers;

	/**
	 * @param name
	 * @param color
	 * @param steine
	 * @param order
	 * @param climbers
	 */
	public Player(String name, Color color) {
		super();
		this.name = name;
		this.color = color;
		this.markers = new ArrayList<Marker>(10);
		this.climbers = new ArrayList<Climber>(3);
	}

	public Player(int order, String name, Color color) {
		super();
		this.name = name;
		this.color = color;
		this.order = order;
		
		this.markers = new ArrayList<Marker>(10);
		this.climbers = new ArrayList<Climber>(3);
	}

	/**
	 * @return the markers
	 */
	public Collection<Marker> getMarkers() {
		return markers;
	}

	/**
	 * @param markers the markers to set
	 */
	public void addMarkers(Collection<Marker> markers) {
		this.markers.addAll(markers);
	}

	/**
	 * @return the climbers
	 */
	public Collection<Climber> getClimbers() {
		return climbers;
	}

	/**
	 * @param add climbers to ClimberList
	 */
	public void addClimbers(Collection<Climber> newClimbers) {
		this.climbers.addAll(newClimbers) ;
	}
	
	/**
	 * @param add climbers to ClimberList
	 */
	public Collection<Climber> removeClimbers() {
		Collection<Climber> oldClimbers = this.climbers;
		this.climbers = new ArrayList<Climber>(3);
		return oldClimbers;
	}
	
	/**
	 * @return the color
	 */
	public Color getColor() {
		return color;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the order
	 */
	public int getOrder() {
		return order;
	}

	/**
	 * @param order the order to set
	 */
	public void setOrder(int order) {
		this.order = order;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Player [name=");
		builder.append(name);
		builder.append(", color=");
		builder.append(color);
		builder.append(", markers=");
		builder.append(markers);
		builder.append(", order=");
		builder.append(order);
		builder.append(", climbers=");
		builder.append(climbers);
		builder.append("]");
		return builder.toString();
	}

	public String display() {
		StringBuffer builder = new StringBuffer();
		builder.append(StringUtils.rightPad(name, 7));
		builder.append("(");
		builder.append(StringUtils.rightPad(color.toString(), 6));
		builder.append(")");
		
		StringBuffer climbersStr = new StringBuffer();
		for (Climber climber : climbers) {
			climbersStr.append(climber.display());
		}
		builder.append(StringUtils.center(climbersStr.toString(), 6));
		
		StringBuffer markersStr = new StringBuffer();
		for (Marker marker : markers) {
			markersStr.append(marker.display());
		}
		builder.append(StringUtils.leftPad(markersStr.toString(), 6));

		return builder.toString();
	}

	/**
	 * gives a climber, if there is more
	 * @return Climber
	 * @throws NotAvailableClimberException 
	 */
	public Climber getOneClimber() throws NotAvailableClimberException {
		if (climbers == null || climbers.isEmpty()) {
			throw new NotAvailableClimberException("Player have no more climber");
		}
		Climber climber = climbers.iterator().next();
		climbers.remove(climber);
		return climber;
	}

	/**
	 * gives a marker, if there is more
	 * @throws NoMarkerIsAvailableException
	 */
	public Marker getOneMarker() throws NoMarkerIsAvailableException {
		if (markers == null || markers.isEmpty()) {
			throw new NoMarkerIsAvailableException("Player have no more marker:" + this.getName());
		}
		
		Marker marker = markers.iterator().next();
		markers.remove(marker);
		return marker;
	}
}
