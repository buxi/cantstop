/**
 * 
 */
package de.buxi.cantstop.model;

/**
 * @author buxi
 *
 */
public enum PairChoiceInfo {
	/*
	 * default, choosable
	 */
	CHOOSABLE, 
	/*
	 * if the way blocked
	 */
	NOTCHOOSABLE, 
	/*
	 *  if already 2 climbers are place and
	 *  the pairs results no way, where the 2 climber is
	 */
	WITHWAYINFO  
}
