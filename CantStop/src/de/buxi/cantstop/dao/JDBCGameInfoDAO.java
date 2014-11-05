package de.buxi.cantstop.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;

/**
 * JDBC implementation of GameInfoDao
 * @author buxi
 *
 */
public class JDBCGameInfoDAO implements GameInfoDao {
	Logger log = Logger.getLogger(this.getClass());
	
	private JdbcTemplate jdbcTemplate;

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public void insert(final int gameId, final Timestamp timestamp, final String methodName,
			final int playerId, final byte[] zippedTransferObject, final String description) {
		// TODO Auto-generated method stub
		jdbcTemplate.update(new PreparedStatementCreator() {
			public PreparedStatement createPreparedStatement(Connection conn)
					throws SQLException {
				String sql = "INSERT INTO GAMEINFO (GAME_ID, TS, METHOD_NAME, PLAYER_ID, TRANSFER_OBJECT, DESCRIPTION) "
							+"VALUES (?, ?, ?, ?, ?, ?)";
				PreparedStatement ps = conn.prepareStatement(sql);
				ps.setInt(1, gameId);
				ps.setTimestamp(2, timestamp);
				ps.setString(3, methodName);
				ps.setInt(4, playerId);
				ps.setBytes(5, zippedTransferObject);
				ps.setString(6, description);
				return ps;
			}
		});
		
	}

	/* (non-Javadoc)
	 * @see de.buxi.cantstop.dao.GameInfoDao#readAllShortGameInfo()
	 */
	public List<GameInfoShortTO> readAllShortGameInfo() {
		String sql = "SELECT GAME_ID, DESCRIPTION FROM GAMEINFO";
		List<GameInfoShortTO> result = jdbcTemplate.query(sql,
				ParameterizedBeanPropertyRowMapper.newInstance(GameInfoShortTO.class));
		
		return result;
	}

	/*public GameTransferObject read(int id ) {
		// unzip the data
		ByteArrayInputStream unzipInput = new ByteArrayInputStream(decodedBase64);
		GzipCompressorInputStream unzipIS = new GzipCompressorInputStream(unzipInput);

		//deserialize the data
		ObjectInputStream objInputStream = new ObjectInputStream(unzipIS);
		Object decodedObject = objInputStream.readObject();
		
		//checking decoded object
		assertTrue("decodedObject must be TestPOJO", decodedObject instanceof TestPOJO);
		TestPOJO decodedTestObject = (TestPOJO)decodedObject;
	}  */
}
