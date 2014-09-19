package de.buxi.cantstop.model;

/**
 * @author buxi
 *
 */
public abstract class AbstractRopePoint {
	private Climber climber;

	public AbstractRopePoint() {
		super();
		this.climber = null;
	}
	
	/**
	 * @return the climber
	 */
	public Climber getClimber() {
		return climber;
	}
	
	/**
	 * Place a Climber on RopePoint
	 * @param climber
	 * @throws RopePointInvalidUsageException RopePoint already used
	 * @throws NullClimberException 
	 */
	public void placeClimber(Climber climber) throws RopePointInvalidUsageException, NullClimberException {
		if (climber == null) {
			throw new NullClimberException("Input climber is null");
		}
		if (this.climber != null) {
			throw new RopePointInvalidUsageException("RopePoint already used");
		}
		this.climber = climber;
	}
	
	/**
	 * removes climber from RopePoint
	 * @return removed Climber
	 * @throws RopePointInvalidUsageException no climber is on RopePoint
	 */
	public Climber removeClimber() throws RopePointInvalidUsageException {
		if (this.climber == null) {
			throw new  RopePointInvalidUsageException("RopePoint not used");
		}
		Climber climber = this.climber;
		this.climber = null;
		return climber;
	}
	
	/**
	 * mark with Marker the RopePoint
	 * @param marker
	 * @throws RopePointInvalidUsageException RopePoint is already marke with this color
	 */
	public abstract void mark(Marker marker) throws RopePointInvalidUsageException;
}
