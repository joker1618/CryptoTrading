package com.fede.app.crypto.trading.dao.impl;

import com.fede.app.crypto.trading.exception.TechnicalException;
import com.fede.app.crypto.trading.logger.ISimpleLog;
import com.fede.app.crypto.trading.logger.LogService;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by f.barbano on 30/09/2017.
 */
abstract class AbstractDBDao {

	private static final ISimpleLog logger = LogService.getLogger(AbstractDBDao.class);

	protected final Connection connection;

	public AbstractDBDao(Connection connection) {
		this.connection = connection;
	}

	protected int performUpdate(String query, Object... params) {
		PreparedStatement ps = null;

		try {
			ps = connection.prepareStatement(query);
			if(params != null) {
				int idx = 1;
				for (Object param : params) {
					if (param instanceof Long) ps.setLong(idx, (Long) param);
					else if (param instanceof Integer) ps.setInt(idx, (Integer) param);
					else if (param instanceof BigDecimal) ps.setBigDecimal(idx, (BigDecimal) param);
					else ps.setString(idx, (String) param);
					idx++;
				}
			}
			int num = ps.executeUpdate();
			logger.finer("Executed update for [%s]: %d rows altered", query, num);
			return num;

		} catch (SQLException e) {
			logger.error(e, "Error performing query [%s]", query);
			throw new TechnicalException(e, "Error performing query [%s]", query);
		} finally {
			try {
				if(ps != null)	ps.close();
			} catch (SQLException e) {
				logger.error(e, "Error closing statement");
				throw new TechnicalException(e, "Error closing statement");
			}
		}
	}
}
