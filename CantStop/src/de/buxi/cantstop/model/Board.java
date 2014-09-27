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

	public String displayHTMLOld() {
		// TODO customTag should be implemented to create Board HTML version
		StringBuilder result = new StringBuilder();
		int longestWayLength = findLongestWay();
		result.append("<table border=1>");
		for (int ropePointNum = -1; ropePointNum < longestWayLength ; ropePointNum++ ) {
			result.append("\n<tr id="+ropePointNum +">");
			for (int wayNum = 0; wayNum < ways.size(); wayNum++ ) {
				Way way = ways.get(wayNum);
				
				// displaying Hut
				if (ropePointNum == -1) {
					// displaying Hut
					Hut hut = way.getHut();
					result.append("\n<td id="+wayNum+" rowspan="+ Math.abs(way.getNumber()-7)*2 +" align=center> "+Integer.toString(way.getNumber()) +"<br>" +hut.display()+"</td>");
				}

				// displaying RopePoints
				if (ropePointNum >= 0 ) {
					List<RopePoint> ropePoints = way.getRopePoints();
					int ropePointIndex = ropePoints.size() - 1 - ropePointNum ; 
					
					if ( ropePointIndex < ropePoints.size() && ropePointIndex >= 0) {
						result.append("\n<td id="+wayNum +" align=center>");
						RopePoint ropePoint = ropePoints.get(ropePointIndex);
						result.append(ropePoint.display());
						result.append("</td>");
					}
					
				}
			}
			result.append("</tr>");
		}
		result.append("</table>");
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
	public String displayHTML() {
		StringBuilder result = new StringBuilder();
		result.append("<table ><tr>");
		for (int i =0 ; i< ways.size(); i++) {
			result.append("<td><table border=1>");
			
			// displaying Hut
			result.append("\n<tr><td>");
			Way way = ways.get(i);
			result.append(way.getNumber());
			Climber climber = way.getHut().getClimber();
			if (climber!=null) {
				result.append("X");
			}
			Marker marker = way.getHut().getMarker();
			if (marker!=null) {
				result.append(marker.getColor());
			}
			result.append("</td></tr>");
			// displaying RopePoints
			
			List<RopePoint> ropePoints = way.getRopePoints();
			for (int j = ropePoints.size()-1; j >= 0; j--) {
				result.append("<tr><td>&nbsp;");
				RopePoint ropePoint = ropePoints.get(j);
				climber = ropePoint.getClimber();
				if (climber!=null) {
					result.append("X");
				}
				Collection<Marker> markers = ropePoint.getMarkers();
				for (Marker marker2 : markers) {
					result.append(marker2.getColor());
				}
				
				result.append("</td></tr>");
			}

			result.append("</table></td>");
			result.append("\n");
		}
		result.append("</tr></table>");

		return result.toString();
	}
	
	public String displayOld() {
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
				result.append("X");
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
					result.append("X");
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
