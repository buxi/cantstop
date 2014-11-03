package de.buxi.cantstop.dao;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import de.buxi.cantstop.model.GameTransferObject;

public class JDBCGameInfoDAO implements GameInfoDao {
	Logger log = Logger.getLogger(this.getClass());
	
	private JdbcTemplate jdbcTemplate;

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public void insert(final String methodName, final GameTransferObject to) {
		//generating JSON from GameTransferObject
		ObjectMapper mapper = new ObjectMapper();
		 
		try (OutputStream out = new ByteArrayOutputStream();) {
			mapper.writeValue(out, to);
			String sql = "INSERT INTO GAMEINFO (TS,METHOD_NAME, PLAYER_ID, TRANSFER_OBJECT) "
					+ "VALUES (?, ?, ?, ?)";
			jdbcTemplate.update(sql,
					new Object[] { 
					new java.sql.Timestamp(new Date().getTime()), methodName, to.actualPlayer.getOrder(), out.toString() });
		} catch (IOException | DataAccessException e) {
			log.error(e.getLocalizedMessage());
		}

	}

}
