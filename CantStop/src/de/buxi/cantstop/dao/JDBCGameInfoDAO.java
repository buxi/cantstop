package de.buxi.cantstop.dao;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;

import org.apache.commons.compress.compressors.gzip.GzipCompressorOutputStream;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;

import de.buxi.cantstop.model.GameTransferObject;

public class JDBCGameInfoDAO implements GameInfoDao {
	Logger log = Logger.getLogger(this.getClass());
	
	private JdbcTemplate jdbcTemplate;

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public void insert(final String methodName, final GameTransferObject to) {
		//serializing and packing GameTransferObject
		
		try (ByteArrayOutputStream outZip = new ByteArrayOutputStream();
				GzipCompressorOutputStream gzip = new GzipCompressorOutputStream(outZip);
				ObjectOutputStream objOut = new ObjectOutputStream(gzip);) {
			objOut.writeObject(to);
			gzip.finish();
			log.info("TransferObject size:" + outZip.toByteArray().length);
			
			jdbcTemplate.update(new PreparedStatementCreator() {
				public PreparedStatement createPreparedStatement(Connection conn)
						throws SQLException {
					String sql = "INSERT INTO GAMEINFO (GAME_ID, TS, METHOD_NAME, PLAYER_ID, TRANSFER_OBJECT, DESCRIPTION) "
								+"VALUES (?, ?, ?, ?, ?, ?)";
					PreparedStatement ps = conn.prepareStatement(sql);
					ps.setInt(1, to.getGameId());
					ps.setTimestamp(2, new java.sql.Timestamp(new Date().getTime()));
					ps.setString(3, methodName);
					ps.setInt(4, to.actualPlayer.getOrder());
					ps.setBytes(5, outZip.toByteArray());
					ps.setString(6, to.getDescription());
					return ps;
				}
			});

		} catch (IOException | DataAccessException e) {
			log.error(e);
		}
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
