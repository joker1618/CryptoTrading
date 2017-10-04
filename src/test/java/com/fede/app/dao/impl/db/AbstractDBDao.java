package com.fede.app.dao.impl.db;

import java.sql.Connection;

/**
 * Created by f.barbano on 30/09/2017.
 */
public class AbstractDBDao {

	protected final Connection connection;

	public AbstractDBDao(Connection connection) {
		this.connection = connection;
	}
}
