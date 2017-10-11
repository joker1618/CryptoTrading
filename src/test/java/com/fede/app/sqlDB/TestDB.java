package com.fede.app.sqlDB;

import com.fede.app.crypto.trading.common.Const;
import com.fede.app.crypto.trading.dao.ILastCallTimeDao;
import com.fede.app.crypto.trading.dao.impl.db.LastCallTimeDBDao;
import com.fede.app.crypto.trading.exception.KrakenCallException;
import com.fede.app.crypto.trading.exception.KrakenResponseError;
import com.fede.app.crypto.trading.exception.TechnicalException;
import com.fede.app.crypto.trading.kraken.IKrakenFacade;
import com.fede.app.crypto.trading.kraken.KrakenFacadeImpl;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static java.lang.System.out;

/**
 * Created by f.barbano on 10/10/2017.
 */
public class TestDB {

	private static final IKrakenFacade krakenFacade = new KrakenFacadeImpl("VRQ4Oe8ZNPbLEyjv26SlU7F5O8zR9DjXr07Q6EHf95ooaSszvOkISQTc", "E07VIMtiE1x+0m1ef67ERAB8b3Xrk3Z1hxOlWbEUEB0dqKxghci7a+bnPeEmDrBigdYQ1My24fpPeMW9fOplIw==");

	private static final Connection CONN = createDBConnection();


	@Test
	public void testDao() throws KrakenResponseError, KrakenCallException {
//		ILastCallTimeDao dao = new LastCallTimeDBDao(CONN);
//		dao.persistLastCallTimes("elem1", "m1", 34L);
//		dao.persistLastCallTimes("elem2", "m1", 60L);
//		dao.persistLastCallTimes("elem1", "m1", 666L);
//		out.println(dao.getLastCallTime("elem1", "m1"));
//		out.println(dao.getLastCallTime("elem2", "m1"));
//		out.println(dao.getLastCallTime("elem3", "m1"));
	}

	private static Connection createDBConnection() {
		try {
			// Load JDBC driver class
			Class.forName(Const.JDBC_DRIVER_CLASS);
			// Create DB connection
			return DriverManager.getConnection("jdbc:mysql://localhost:3306/CRYPTO_TRADING", "user", "user");
		} catch (ClassNotFoundException | SQLException ex) {
			throw new TechnicalException(ex, "Unable to create DB connection");
		}
	}
}
