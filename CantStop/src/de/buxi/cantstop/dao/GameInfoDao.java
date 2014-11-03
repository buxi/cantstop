package de.buxi.cantstop.dao;

import de.buxi.cantstop.model.GameTransferObject;

/**
 * Simple DAO interface to store GameTransferObject objects in the database
 * @author buxi
 *
 */
public interface GameInfoDao {
	public void insert(String methodName, GameTransferObject to); 
	
}
