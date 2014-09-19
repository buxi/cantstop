/**
 * 
 */
package de.buxi.cantstop.model;

import static org.junit.Assert.*;

import org.junit.Test;

import de.buxi.cantstop.model.Color;
import de.buxi.cantstop.model.Hut;
import de.buxi.cantstop.model.Marker;
import de.buxi.cantstop.model.RopePointInvalidUsageException;

/**
 * @author buxi
 *
 */
public class HutTest {
	@Test
	public void testHut() {
		Hut hut = new Hut(0);
		assertNull(hut.getClimber());
		assertNull(hut.getMarker());
	}

	@Test
	public void testMarkWithNeueHut() throws RopePointInvalidUsageException {
		Hut hut = new Hut(0);
		Marker marker = new Marker(Color.BLUE);
		hut.mark(marker);
		Marker markers = hut.getMarker();
		assertTrue("with BLUE marked", marker.equals(markers));
	}
	
	@Test(expected=RopePointInvalidUsageException.class)
	public void testMarkWithSchonMarkedRopePoint() throws RopePointInvalidUsageException {
		Hut hut = new Hut(0);
		Marker marker = new Marker(Color.BLUE);
		hut.mark(marker);
		
		hut.mark(marker);
	}
	
	@Test(expected=RopePointInvalidUsageException.class)
	public void testUnMarkWithNeueHut() throws RopePointInvalidUsageException {
		Hut hut = new Hut(0);
		hut.unmark();
	}

	@Test()
	public void testUnMarkWithMarkedeHut() throws RopePointInvalidUsageException {
		Hut hut = new Hut(0);
		Marker marker = new Marker(Color.BLUE);
		hut.mark(marker);
		hut.unmark();
		assertNull("yet not marked with BLUE ", hut.getMarker());
	}
	
	@Test()
	public void testIsMarkedWithBlau() throws RopePointInvalidUsageException {
		Hut hut = new Hut(0);
		Marker marker = new Marker(Color.BLUE);
		hut.mark(marker);
		assertTrue("already marked with BLUE ", hut.isMarked(Color.BLUE));
	}
	
	@Test()
	public void testIsMarkedWithOther() throws RopePointInvalidUsageException {
		Hut hut = new Hut(0);
		Marker marker = new Marker(Color.BLUE);
		hut.mark(marker);
		assertFalse("already marked with BLUE ", hut.isMarked(Color.YELLOW));
	}

	@Test()
	public void testIsMarkedNichtMarked() throws RopePointInvalidUsageException {
		Hut hut = new Hut(0);
		assertFalse("already not marked", hut.isMarked(Color.YELLOW));
	}
	
}
