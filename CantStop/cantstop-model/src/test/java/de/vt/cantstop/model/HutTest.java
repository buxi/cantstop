/**
 * 
 */
package de.vt.cantstop.model;

import static org.junit.Assert.*;

import org.junit.Test;

import de.vt.cantstop.model.Color;
import de.vt.cantstop.model.Hut;
import de.vt.cantstop.model.Marker;
import de.vt.cantstop.model.RopePointInvalidUsageException;

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
		Marker result = hut.unmark();
		assertNotNull("returned marker can't be null", result);
		assertNull("yet not marked with BLUE ", hut.getMarker());
		assertEquals("returned marker should be the same as original", marker, result );
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
