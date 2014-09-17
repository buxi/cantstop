package de.buxi.cantstop.spielobjekte;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author buxi
 * Helpermethoden fuer Boardtesten 
 */
public class BoardTestHelper {
	
	
	
	public static Collection<Climber> placeClimbersOnTheWayAllNextToTheHut(Board board, Collection<Integer>wayNumbers ) throws InvalidWayNumberException, RopePointInvalidUsageException, NullClimberException {
		Collection<Climber> climbers = new ArrayList<Climber>();
		for (Integer wayNumber : wayNumbers) {
			Way way = board.getWayByNumber(wayNumber);
			Climber climber = new Climber();
			climbers.add(climber);
			way.getUpperRopePoint().placeClimber(climber);
		}
		return climbers;
	}
	
	public static Collection<Climber> placeClimbersOnTheWayAllInHut(Board board, Collection<Integer>wayNumbers ) throws InvalidWayNumberException, RopePointInvalidUsageException, NullClimberException {
		Collection<Climber> climbers = new ArrayList<Climber>();
		for (Integer wayNumber : wayNumbers) {
			Way way = board.getWayByNumber(wayNumber);
			Climber climber = new Climber();
			climbers.add(climber);
			way.getHut().placeClimber(climber);
		}
		return climbers;
	}

	public static Collection<Climber> placeClimberOnTheWayAllDown(Board board, Collection<Integer>wayNumbers ) throws InvalidWayNumberException, RopePointInvalidUsageException, NullClimberException {
		Collection<Climber> climbers = new ArrayList<Climber>();
		for (Integer wayNumber : wayNumbers) {
			Way way = board.getWayByNumber(wayNumber);
			Climber climber = new Climber();
			climbers.add(climber);
			way.getFirstRopePoint().placeClimber(climber);
		}
		return climbers;
	}

	
	public static Collection<Marker> markRopePointeOnTheWaysAllNextToTheHut(Board board, Collection<Integer>wayNumbers, Color color ) throws InvalidWayNumberException, RopePointInvalidUsageException, NullClimberException {
		Collection<Marker> markers = new ArrayList<Marker>();
		for (Integer wayNumber : wayNumbers) {
			Way way = board.getWayByNumber(wayNumber);
			Marker marker = new Marker(color);
			markers.add(marker);
			way.getUpperRopePoint().mark(marker);
		}
		return markers;
	}

	public static Collection<Marker> markRopePointsOnTheWaysAllDown(Board board, Collection<Integer>wayNumbers, Color color ) throws InvalidWayNumberException, RopePointInvalidUsageException, NullClimberException {
		Collection<Marker> markers = new ArrayList<Marker>();
		for (Integer wayNumber : wayNumbers) {
			Way way = board.getWayByNumber(wayNumber);
			Marker marker = new Marker(color);
			markers.add(marker);
			way.getFirstRopePoint().mark(marker);
		}
		return markers;
	}
	
	public static Collection<Marker> markHuts(Board board, Collection<Integer>wayNumbers, Color color ) throws InvalidWayNumberException, RopePointInvalidUsageException, NullClimberException {
		Collection<Marker> markers = new ArrayList<Marker>();
		for (Integer wayNumber : wayNumbers) {
			Way way = board.getWayByNumber(wayNumber);
			Marker marker = new Marker(color);
			markers.add(marker);
			way.getHut().mark(marker);
		}
		return markers;
	}

}
