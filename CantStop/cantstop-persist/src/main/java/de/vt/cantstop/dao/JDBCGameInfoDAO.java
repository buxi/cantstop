package de.vt.cantstop.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;

import de.vt.cantstop.model.GameTransferObject;
import de.vt.cantstop.utils.ObjectManipulationHelper;

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
		String sql = "SELECT distinct GAME_ID, DESCRIPTION FROM GAMEINFO";
		List<GameInfoShortTO> result = jdbcTemplate.query(sql,
				ParameterizedBeanPropertyRowMapper.newInstance(GameInfoShortTO.class));
		
		return result;
	}

	/**
	 * @author buxi
	 * Inner class to map GameInfoFullTO - GAMEINFO table row
	 */
	public class GameInfoFullRowMapper implements ParameterizedRowMapper<GameInfoFullTO> {
		public GameInfoFullTO mapRow(ResultSet rs, int rowNum) throws SQLException {
			GameInfoFullTO gameInfoFullTO = new GameInfoFullTO();
			gameInfoFullTO.setGameId(rs.getInt("GAME_ID"));
			gameInfoFullTO.setTs(rs.getTimestamp("TS"));
			gameInfoFullTO.setMethodName(rs.getString("METHOD_NAME"));
			gameInfoFullTO.setPlayerId(rs.getInt("PLAYER_ID"));
			GameTransferObject to = (GameTransferObject)ObjectManipulationHelper.unpackAndDeserialize(rs.getBytes("TRANSFER_OBJECT"));
			gameInfoFullTO.setTransferObject(to);
			gameInfoFullTO.setDescription(rs.getString("DESCRIPTION"));
			
		return gameInfoFullTO;
		}
	}
	
	@Override
	public List<GameInfoFullTO> readFullInfoByGameId(int gameId) {
		String sql = "SELECT * FROM GAMEINFO WHERE GAME_ID='" + gameId +"' order by ts";
		List<GameInfoFullTO> result = jdbcTemplate.query(sql, new GameInfoFullRowMapper());
		return result;
	}
}
