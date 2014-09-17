package de.buxi.cantstop.spielobjekte;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Board {
	/**
	 * All 11 Way of the Board
	 */
	private List<Way> ways;
	
	public Board(List<Way> ways) {
		this.ways = ways;
	}

	/**
	 * @return the ways
	 */
	public List<Way> getWays() {
		return ways;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Board [ways=");
		builder.append(ways);
		builder.append("]");
		return builder.toString();
	}
	
	public String display() {
		String spaces = "                                                                ";
		StringBuilder result = new StringBuilder();
		for (int i = ways.size()-1; i >= 0; i--) {
			result.append("--------------------------------------------\n");
			Way way = ways.get(i);
			if (way.getNumber()<10) {
				result.append(" ");
			}
			result.append(way.getNumber());
			
			// displaying Hut
			result.append(spaces.substring(0, Math.abs(way.getNumber()-7)*3   ));
			result.append(" H(");
			Climber climber = way.getHut().getClimber();
			if (climber!=null) {
				result.append("BS");
			}
			Marker marker = way.getHut().getMarker();
			if (marker!=null) {
				result.append(marker.getColor());
			}
			result.append(")");
			
			// displaying RopePoints
			List<RopePoint> ropePoints = way.getRopePoints();
			for (int j = ropePoints.size()-1; j >= 0; j--) {
				RopePoint ropePoint = ropePoints.get(j);
				result.append(" (");
				climber = ropePoint.getClimber();
				if (climber!=null) {
					result.append("BS");
				}
				Collection<Marker> markers = ropePoint.getMarkers();
				for (Marker marker2 : markers) {
					result.append(marker2.getColor());
					result.append("|");
				}
				
				result.append(")");
			}
			result.append("\n");
		}
		return result.toString();
	}

	/**
	 * @param wayNumber thrown with two dices, sum between 2 and 12
	 * @return Way 
	 * @throws InvalidWayNumberException 
	 */
	public Way getWayByNumber(int wayNumber) throws InvalidWayNumberException {
		// TODO einfache Transformation des WayNumber zu WayID
		if (wayNumber < 2 || wayNumber > 12) {
			throw new InvalidWayNumberException("Invalid waynumber" + wayNumber +", wayNumber between 2 and 12 is valid");
		}
		if (wayNumber - 2 < 0 || wayNumber - 2 > ways.size()-1) {
			throw new InvalidWayNumberException("Invalid waynumber" + wayNumber +" out of Board");
		}
		return ways.get(wayNumber - 2);
	}

	/**
	 * Mark Climber on the ways 
	 * @param actual Player 
	 * @return free Climber
	 * @throws NoMarkerIsAvailableException 
	 * @throws RopePointInvalidUsageException 
	 * @throws NoClimberOnWayException 
	 */
	public List<Climber> markClimbers(Player player) throws NoMarkerIsAvailableException, RopePointInvalidUsageException, NoClimberOnWayException {
		List<Climber> freeClimber = new ArrayList<Climber>(3);
		for (Way way : ways) {
			if (way.isClimberOnRope() || way.isClimberInHut()) {
				Climber climber;
				if (way.isMarkerOnRope(player.getColor())) {
					//move marker
					climber = way.markClimberMoveMarker(player.getColor());
				}
				else {
					// place new marker
					climber = way.markClimberNewMarker(player.getOneMarker());
				}
				freeClimber.add(climber);
			}
		}
		return freeClimber;
	}
	
	/**
	 * Remove Climber from the ways 
	 * @return free Climbers
	 * @throws NoMarkerIsAvailableException 
	 * @throws RopePointInvalidUsageException 
	 * @throws InvalidClimberMovementException 
	 */
	public List<Climber> removeClimbers() throws NoMarkerIsAvailableException, RopePointInvalidUsageException, InvalidClimberMovementException {
		List<Climber> freeClimbers = new ArrayList<Climber>(3);
		for (Way way : ways) {
			Climber climber;
			if (way.isClimberOnRope()) {
				climber = way.removeClimberFromRope();
				freeClimbers.add(climber);
			}
			if (way.isClimberInHut()) {
				climber = way.removeClimberFromHut();
				freeClimbers.add(climber);
			}
		}
		return freeClimbers;
	}

	public Collection<Hut>getBlockedHuts(Player actualPlayer) {
		Collection<Hut> usedHuts = new ArrayList<Hut>();
		for (Way way : ways) {
			if (way.isBlockedByPlayer(actualPlayer.getColor())) {
				usedHuts.add(way.getHut());	
			}
		}
		return usedHuts;
	}

	/**
	 * Remove all Marker from Rope of the marked ways
	 * @param usedHuts
	 * @return removed Markers
	 * @throws InvalidWayNumberException
	 */
	public Map<Color, Collection<Marker>> removeMarkersFromBlockedWays(
			Collection<Hut> usedHuts) throws InvalidWayNumberException {
		// Initialize return Map
		Map<Color, Collection<Marker>> freeMarkers = new HashMap<Color, Collection<Marker>>();
		for (Hut hut : usedHuts) {
			Way way = this.getWayByNumber(hut.getWayNumber());
			for (RopePoint ropePoint : way.getRopePoints()) {
				for (Marker marker : ropePoint.getMarkers()) {
					if (!freeMarkers.containsKey(marker.getColor())) {
						freeMarkers.put(marker.getColor(), new ArrayList<Marker>());
					}
					freeMarkers.get(marker.getColor()).add(marker);
				}
			}
		}
		return freeMarkers;
	}
}
