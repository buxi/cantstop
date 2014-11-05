package de.buxi.cantstop.dao;

import java.sql.Timestamp;
import java.util.List;

/**
 * Simple DAO interface to store Game related information in a database
 * @author buxi
 *
 */
public interface GameInfoDao {
	/**
	 * inserting one line into GAMEINFO table
	 * @param gameId
	 * @param timestamp
	 * @param methodName called method name coming from the record aspect
	 * @param playerId
	 * @param zippedTransferObject
	 * @param description
	 */
	public void insert(int gameId, Timestamp timestamp, String methodName, int playerId, byte[] zippedTransferObject, String description); 

	/**
	 * @return list of <gameId, description> info from GAMEINFO table
	 */
	public List<GameInfoShortTO> readAllShortGameInfo();
}
