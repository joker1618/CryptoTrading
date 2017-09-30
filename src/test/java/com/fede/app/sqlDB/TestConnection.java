package com.fede.app.sqlDB;

import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import static java.lang.System.out;

/**
 * Created by f.barbano on 30/09/2017.
 */
public class TestConnection {

	private static final String JDBC_DRIVER_CLASS = "com.mysql.jdbc.Driver";
	private static final String DB_URL = "jdbc:mysql://localhost:3306/CRYPTO_TRADING";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "root";

	@Test
	public void testDBConnection() throws Exception {
		Class.forName(JDBC_DRIVER_CLASS);
		Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
		PreparedStatement ps = connection.prepareStatement("insert into assets(asset_name) values(?)");
		ps.setString(1, "ZUSD");
		out.println("Update: " + ps.executeUpdate());

		ps = connection.prepareStatement("SELECT * FROM ASSETS WHERE ID > ?");
		ps.setLong(1, 2);
		ResultSet rs = ps.executeQuery();
		if(rs != null) {
			while(rs.next()) {
				long assetID = rs.getLong(1);
				out.println(assetID);
			}
		}
	}
}
