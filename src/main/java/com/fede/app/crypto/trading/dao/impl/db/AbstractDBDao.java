package com.fede.app.crypto.trading.dao.impl.db;

import java.sql.Connection;

/**
 * Created by f.barbano on 30/09/2017.
 */
abstract class AbstractDBDao {

	protected final Connection connection;

	public AbstractDBDao(Connection connection) {
		this.connection = connection;
	}

}
