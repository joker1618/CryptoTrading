package com.fede.app.crypto.trading.dao.impl.db;

import com.fede.app.crypto.trading.dao.ILastCallTimeDao;
import com.fede.app.crypto.trading.exception.TechnicalException;
import com.fede.app.crypto.trading.logger.ISimpleLog;
import com.fede.app.crypto.trading.logger.LogService;
import com.fede.app.crypto.trading.util.Utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by f.barbano on 11/10/2017.
 */
public class LastCallTimeDBDao extends AbstractDBDao implements ILastCallTimeDao {

	private static final ISimpleLog logger = LogService.getLogger(LastCallTimeDBDao.class);

	private static final String Q_SELECT_LAST_TIMES = "SELECT * FROM LAST_CALL_TIME WHERE METHOD_NAME = ?";
	private static final String Q_INSERT_LAST_TIMES = "REPLACE INTO LAST_CALL_TIME (ELEM_KEY, METHOD_NAME, LAST_TIME) VALUES @LAST_DESCR_VALUES@";

	private static final String PH_LAST_DESCR_VALUES = "@LAST_DESCR_VALUES@";


	public LastCallTimeDBDao(Connection connection) {
		super(connection);
	}

	@Override
	public Map<String, Long> getLastCallTimes(String methodName) {
		PreparedStatement ps = null;
		ResultSet rs = null;

		try {
			ps = connection.prepareStatement(Q_SELECT_LAST_TIMES);
			ps.setString(1, methodName);
			rs = ps.executeQuery();

			Map<String, Long> toRet = new HashMap<>();
			if(rs != null) {
				while(rs.next()) {
					String el = rs.getString("ELEM_KEY");
					long last = rs.getLong("LAST_TIME");
					toRet.put(el, last);
				}
			}
			return toRet;

		} catch (SQLException e) {
			logger.error(e);
			throw new TechnicalException(e);
		} finally {
			try {
				if(rs != null)	rs.close();
				if(ps != null) 	ps.close();
			} catch (SQLException e) {
				logger.error(e, "Unable to close JDBC statement");
				throw new TechnicalException(e, "Unable to close JDBC statements");
			}
		}
	}

	@Override
	public void persistLastCallTimes(Collection<LastCallDescr> lastCallDescrs) {
		String values = Utils.join(lastCallDescrs, ",", this::lastCallDescrToString);
		String query = Q_INSERT_LAST_TIMES.replace(PH_LAST_DESCR_VALUES, values);

		try (PreparedStatement ps = connection.prepareStatement(query)) {
			int numInsert = ps.executeUpdate();
			logger.info("Persisted %d last call time for method", numInsert);

		} catch (SQLException e) {
			logger.error(e);
			throw new TechnicalException(e);
		}
	}

	private String lastCallDescrToString(LastCallDescr lcd) {
		return String.format("('%s', '%s', %d)", lcd.getElemKey(), lcd.getMethodName(), lcd.getLastTime());
	}

}
