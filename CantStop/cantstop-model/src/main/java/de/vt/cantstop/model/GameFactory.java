package de.vt.cantstop.model;
/**
 * 
 */


import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

/**
 * @author buxi
 * contains all Factory-methods for the Game
 */
public class GameFactory {
	
	
	/**
	 * generates the board
	 * @return full board with all ways
	 */
	public Board createBoard(Map<Integer,Integer> wayDefinitions) {
		Set<Entry<Integer, Integer>> wayDefinitionsEntries = wayDefinitions.entrySet();
		List<Way> ways = new ArrayList<>(11);
		
		for (Entry<Integer, Integer> entry : wayDefinitionsEntries) {
			int number = entry.getKey();
			int lange = entry.getValue();
			Way way = createWay(number, lange);
			ways.add(way);
		}
		Board board = new Board(ways);
		return board;
	}

	/**
	 * @param number
	 * @param length
	 * @return
	 */
	protected Way createWay(int number, int length) {
		Hut hut = new Hut(number);
		
		List<RopePoint> ropePoints = new ArrayList<>();
		for (int i = 0; i < length ; i++) {
			ropePoints.add(new RopePoint());
		}
		Way way = new Way(number, hut , ropePoints);
		return way;
	}
	
	/**
	 * @param how much marker to generate
	 * @param color of marker
	 * @return new markers with color 
	 */
	public Collection<Marker> createMarkers(int number, Color color){
		List<Marker> markers = new ArrayList<>(number);
		for (int i = 0; i < number; i++) {
			markers.add(new Marker(color));
		}		
		return markers;
	}
	
	public static Collection<Marker> createMarkersStatic (int number, Color color){
		return new GameFactory().createMarkers(number, color);
	}
	/**
	 * @param number of climbers to generate
	 * @return new climbers 
	 */
	public Collection<Climber> createClimbers(int number){
		List<Climber> climberList = new ArrayList<>(number);
		for (int i = 0; i < number; i++) {
			climberList.add(new Climber());
		}		
		return climberList;
	}
}
