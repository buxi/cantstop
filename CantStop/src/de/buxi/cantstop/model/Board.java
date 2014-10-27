package de.buxi.cantstop.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

public class Board implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -115852410389547183L;
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
		StringBuilder result = new StringBuilder();
		int longestWayLength = findLongestWay();
		for (int ropePointNum = -2; ropePointNum < longestWayLength ; ropePointNum++ ) {
			for (int wayNum = 0; wayNum < ways.size(); wayNum++ ) {
				//result.append("--------------------------------------------\n");
				Way way = ways.get(wayNum);
				if (ropePointNum == -2) {
					// displaying Hut/Weg number
					result.append(StringUtils.center(Integer.toString(way.getNumber()), 6));
				}
				
				// displaying Hut
				if (ropePointNum == -1) {
					// displaying Hut
					Hut hut = way.getHut();
					result.append(hut.display());
				}

				// displaying RopePoints
				if (ropePointNum >= 0 ) {
					List<RopePoint> ropePoints = way.getRopePoints();
					int ropePointIndex = ropePoints.size() - 1 - ropePointNum ; 
					if ( ropePointIndex < ropePoints.size() && ropePointIndex >= 0) {
						RopePoint ropePoint = ropePoints.get(ropePointIndex);
						result.append(ropePoint.display());
					}
					else {
						result.append(StringUtils.center("", 6));
					}
				}
			}
			result.append("\n");
		}
		return result.toString();
	}

	protected int findLongestWay() {
		int maxLength = ways.get(0).getRopePoints().size();
		for (int i = 1; i < ways.size(); i++ ) {
			int actualLength = ways.get(i).getRopePoints().size();
			if (actualLength > maxLength) {
				maxLength = actualLength;
			}
		}
		return maxLength;
	}
	
	/**
	 * @param wayNumber thrown with two dices, sum between 2 and 12
	 * @return Way 
	 * @throws InvalidWayNumberException 
	 */
	public Way getWayByNumber(int wayNumber) throws InvalidWayNumberException {
		// TODO simple Transformation of WayNumber to WayID
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
					climber = way.markClimberNewMarker(player.aquireOneMarker());
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
	 * @throws RopePointInvalidUsageException 
	 */
	public Map<Color, Collection<Marker>> removeMarkersFromBlockedWays(
			Collection<Hut> usedHuts) throws InvalidWayNumberException, RopePointInvalidUsageException {
		// Initialize return Map
		Map<Color, Collection<Marker>> freeMarkers = new HashMap<Color, Collection<Marker>>();
		for (Hut hut : usedHuts) {
			Way way = this.getWayByNumber(hut.getWayNumber());
			for (RopePoint ropePoint : way.getRopePoints()) {
				List<Marker> markersToRemove = new ArrayList<Marker>(4);
				for (Marker marker : ropePoint.getMarkers()) {
					if (!freeMarkers.containsKey(marker.getColor())) {
						freeMarkers.put(marker.getColor(), new ArrayList<Marker>());
					}
					freeMarkers.get(marker.getColor()).add(marker);
					// collecting removed markers to remove them from ropepoint and avoid concurrent modification exception
					markersToRemove.add(marker);
				}
				// fixing bug1
				for (Marker removedMarker : markersToRemove) {
					ropePoint.unmark(removedMarker.getColor());	
				}
			}
		}
		return freeMarkers;
	}
}
