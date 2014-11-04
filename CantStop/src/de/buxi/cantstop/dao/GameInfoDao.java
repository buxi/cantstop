package de.buxi.cantstop.dao;

import java.util.List;

import de.buxi.cantstop.model.GameTransferObject;

/**
 * Simple DAO interface to store GameTransferObject objects in the database
 * @author buxi
 *
 */
public interface GameInfoDao {
	/**
	 * inserting one line into GAMEINFO table
	 * @param methodName called method name coming from the record aspect
	 * @param to GameTransferObject
	 */
	public void insert(String methodName, GameTransferObject to); 

	/**
	 * @return list of <gameId, description> info from GAMEINFO table
	 */
	public List<GameInfoShortTO> readAllShortGameInfo();
}
