package de.vt.cantstop.model;
/**
 * 
 */


import java.io.Serializable;

/**
 * @author buxi
 *
 */
public enum PairChoiceInfo implements Serializable{
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
