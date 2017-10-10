package com.fede.app.crypto.trading.dao.impl.db;

import com.fede.app.crypto.trading.exception.TechnicalException;
import com.fede.app.crypto.trading.logger.ISimpleLog;
import com.fede.app.crypto.trading.logger.LogService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Created by f.barbano on 30/09/2017.
 */
abstract class AbstractDBDao {

	protected final Connection connection;

	public AbstractDBDao(Connection connection) {
		this.connection = connection;
	}

}
