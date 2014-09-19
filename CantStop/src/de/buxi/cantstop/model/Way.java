/**
 * 
 */
package de.buxi.cantstop.model;

import java.util.List;

/**
 * @author buxi
 *
 */
public class Way {
	/**
	 * for numbering: from 2 to 12
	 */
	private int number;
	
	private Hut hut;
	
	private List<RopePoint> ropePoints;

	/**
	 * @param number
	 * @param hut
	 * @param ropePoints
	 */
	public Way(int number, Hut hut, List<RopePoint> ropePoints) {
		super();
		this.number = number;
		this.hut = hut;
		this.ropePoints = ropePoints;
	}

	/**
	 * @return the number
	 */
	public int getNumber() {
		return number;
	}

	/**
	 * @return the hut
	 */
	public Hut getHut() {
		return hut;
	}

	/**
	 * @return the ropePoints
	 */
	public List<RopePoint> getRopePoints() {
		return ropePoints;
	} 
	
	/**
	 * a Way is blocked, if there is a marker in hut
	 *   
	 * @return true, if the hut is marked
	 */
	public boolean isBlocked() {
		return !isFree();
	}
	
	/**
	 * One Way is free, if there is no marker in the hut
	 * @return true, is the hut is not marked
	 */
	public boolean isFree() {
		return hut.getMarker() == null ? true : false; 
	}
	
	/**
	 * @return if there is a Climber on the rope
	 */
	public boolean isClimberOnRope() {
		return whereIsTheClimberOnTheRope() < 0 ? false : true;
	}
	
	/**
	 * @return Coordinate of the climbers in the rope (0 is the first RopePoint)
	 * @return -1000 if there is no climber on the rope
	 */
	public int whereIsTheClimberOnTheRope() {
		for (int i = 0; i <= ropePoints.size()-1; i++) {
			RopePoint ropePoint = ropePoints.get(i);
			if (ropePoint.getClimber() != null) {
				return i;
			}
		}
		return -1000;
	}

	/**
	 * @return if there is a marker on the rope
	 */
	public boolean isMarkerOnRope(Color color) {
		return whereIsMarkerForColor(color) < 0 ? false : true;
	}
	
	/**
	 * Search for a marker with color on the rope
	 * @param color 
	 * @return Coordinate of the marker (0 is the first RopePoint)
	 * @return -1000 no marker found
	 */
	public int whereIsMarkerForColor(Color color) {
		for (int i = 0; i <= ropePoints.size()-1; i++) {
			RopePoint ropePoint = ropePoints.get(i);
			if (ropePoint.isMarkerForColor(color)) {
				return i;
			}
		}
		return -1000;
	}
	
	/**
	 * @return first RopePoint 
	 */
	public RopePoint getFirstRopePoint() {
		return ropePoints.get(0);
	}
	
	/**
	 * @return RopePoint by the hut
	 */
	public RopePoint getUpperRopePoint() {
		return ropePoints.get(ropePoints.size()-1);
	}
	
	/**
	 * TODO it must by SYNCHRONIZED
	 * @param newClimber
	 * @throws RopePointInvalidUsageException
	 * @throws NullClimberException 
	 */
	public void placeClimberOntoFirstRopePoint(Climber newClimber) throws RopePointInvalidUsageException, NullClimberException {
		RopePoint firstRopePoint = getFirstRopePoint();
		firstRopePoint.placeClimber(newClimber);
	}

	/**
	 * place climber after the marker
	 * @param climber 
	 * @param color
	 * @throws RopePointInvalidUsageException
	 * @throws NullClimberException 
	 * @throws InvalidClimberMovementException 
	 */
	public void placeClimberAfterMarker(Climber climber, Color color) throws RopePointInvalidUsageException, NullClimberException, InvalidClimberMovementException {
		int markerKoord = whereIsMarkerForColor(color);
		if (markerKoord < 0) {
			// kein Marker auf dem Way
			throw new InvalidClimberMovementException("no marker on the way:" + color);
		}
		if (markerKoord == lastRopePointKoord()) {
			hut.placeClimber(climber);
		} else {
			placeClimberOnTheRopePoint(markerKoord + 1, climber);
		}
	}
	/**
	 * @param koord where should be the climber placed on Rope
	 * @param climber
	 * @throws RopePointInvalidUsageException
	 * @throws NullClimberException
	 * @throws InvalidClimberMovementException
	 */
	private void placeClimberOnTheRopePoint(int koord, Climber climber) throws RopePointInvalidUsageException, NullClimberException, InvalidClimberMovementException {
		if (koord < 0 || koord > ropePoints.size()-1) {
			throw new InvalidClimberMovementException("Invalid RopePoint Coordinate" + koord);
		}
		if (this.isBlocked()) {
			throw new InvalidClimberMovementException("Way is blocked, placing a climber is not allowed");
		}
		RopePoint ropePoint = ropePoints.get(koord);
		ropePoint.placeClimber(climber);
	}

	/**
	 * Climber moves up, if he arrives at last RopePoint moves into hut
	 * @throws RopePointInvalidUsageException
	 * @throws InvalidClimberMovementException no Climber on the rope
	 * @throws NullClimberException
	 */
	public void moveClimber() throws RopePointInvalidUsageException, InvalidClimberMovementException, NullClimberException {
		int climberKoord = whereIsTheClimberOnTheRope();
		if (climberKoord < 0) {
			throw new InvalidClimberMovementException("no climber on the rope");
		}
		RopePoint climberRopePoint = ropePoints.get(climberKoord);
		if (climberKoord == lastRopePointKoord()) {
			// moves climber in the hut
			hut.placeClimber(climberRopePoint.removeClimber());
		} else {
			// moves Climber up
			placeClimberOnTheRopePoint(climberKoord + 1 , climberRopePoint.removeClimber());
		}
	}

	/**
	 * Places a new marker on the position of climber
	 * No marker on the way
	 * @param marker
	 * @return freer Climber
	 * @throws RopePointInvalidUsageException 
	 * @throws NoClimberOnWayException 
	 */
	public Climber markClimberNewMarker(Marker marker) throws RopePointInvalidUsageException, NoClimberOnWayException {
		return markClimber(marker);
	}
	
	/**
	 * move playerColor Marker on the way
	 * @param playerColor
	 * @return free Climber
	 * @throws RopePointInvalidUsageException
	 * @throws NoClimberOnWayException 
	 */
	public Climber markClimberMoveMarker(Color playerColor) throws RopePointInvalidUsageException, NoClimberOnWayException {
		int markerKoord = whereIsMarkerForColor(playerColor);
		RopePoint ropePointMarker = getRopePoints().get(markerKoord); 
		Marker marker = ropePointMarker.unmark(playerColor);
		return markClimber(marker);
	}

	/**
	 * @param marker
	 * @return freer Climber
	 * @throws RopePointInvalidUsageException
	 * @throws NoClimberOnWayException 
	 */
	protected Climber markClimber(Marker marker)
			throws RopePointInvalidUsageException, NoClimberOnWayException {
		// is climber in the hut?
		if (isClimberInHut()) {
			hut.mark(marker);
			return hut.removeClimber();
		}
		
		// is climber on the rope?
		if (isClimberOnRope()) {
			int climberKoord = whereIsTheClimberOnTheRope();
			RopePoint ropePoint = ropePoints.get(climberKoord);
			ropePoint.mark(marker);
			return ropePoint.removeClimber();
		}
		throw new NoClimberOnWayException("there is no climber in the hut and on the rope");
	}

	/**
	 * @return true if there is a climber in the Hut 
	 */
	public boolean isClimberInHut() {
		if (hut.getClimber() != null) {
			return true;
		}
		return false;
	}
	
	/**
	 * @return Coordinate of the RopePoint by the hut
	 */
	public int lastRopePointKoord() {
		return ropePoints.size()-1;
	}
	
	/**
	 * @return entNomender Climber
	 * @throws RopePointInvalidUsageException kein Climber in the hut
	 */
	public Climber removeClimberFromHut() throws RopePointInvalidUsageException {
		return hut.removeClimber();
	}
	
	/**
	 * @return removed climber
	 * @throws InvalidClimberMovementException no climber on the rope
	 */
	public Climber removeClimberFromRope() throws RopePointInvalidUsageException, InvalidClimberMovementException {
		int climberKoord = whereIsTheClimberOnTheRope();
		if (climberKoord < 0) {
			throw new InvalidClimberMovementException("no climber on the rope");
		}
		RopePoint ropePoint = ropePoints.get(climberKoord);
		return ropePoint.removeClimber();
	}

	/**
	 * @param color of the player
	 * @return  true, if the way is blocked by the player (color Marker is in the hut)
	 */
	public boolean isBlockedByPlayer(Color color) {
		if (hut.getMarker() != null && color.equals(hut.getMarker().getColor())) {
			return true;
		}
		return false;
	}
}
