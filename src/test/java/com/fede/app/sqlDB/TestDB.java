package com.fede.app.sqlDB;

import com.fede.app.crypto.trading.common.Const;
import com.fede.app.crypto.trading.dao.ITickersDao;
import com.fede.app.crypto.trading.dao.impl.db.TickersDBDao;
import com.fede.app.crypto.trading.exception.KrakenCallException;
import com.fede.app.crypto.trading.exception.KrakenResponseError;
import com.fede.app.crypto.trading.exception.TechnicalException;
import com.fede.app.crypto.trading.kraken.IKrakenFacade;
import com.fede.app.crypto.trading.kraken.KrakenFacadeImpl;
import com.fede.app.crypto.trading.main.KrakenEngine;
import com.fede.app.crypto.trading.model._public.Ticker;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static java.lang.System.out;

/**
 * Created by f.barbano on 10/10/2017.
 */
public class TestDB {

	private static final IKrakenFacade krakenFacade = new KrakenFacadeImpl("VRQ4Oe8ZNPbLEyjv26SlU7F5O8zR9DjXr07Q6EHf95ooaSszvOkISQTc", "E07VIMtiE1x+0m1ef67ERAB8b3Xrk3Z1hxOlWbEUEB0dqKxghci7a+bnPeEmDrBigdYQ1My24fpPeMW9fOplIw==");

	private static final Connection CONN = createDBConnection();


	@Test
	public void testTickersDao() throws KrakenResponseError, KrakenCallException {
		try {
			List<Ticker> fromWeb = krakenFacade.getTickers(Arrays.asList("BCHEUR"));
//			List<Ticker> fromWeb = krakenFacade.getTickers(Arrays.asList("BCHEUR", "BCHUSD", "BCHXBT", "DASHEUR", "DASHUSD", "DASHXBT", "EOSETH", "EOSXBT", "GNOETH", "GNOXBT", "USDTZUSD", "XETCXETH", "XETCXXBT", "XETCZEUR", "XETCZUSD", "XETHXXBT", "XETHZCAD", "XETHZEUR", "XETHZGBP", "XETHZJPY", "XETHZUSD", "XICNXETH", "XICNXXBT", "XLTCXXBT", "XLTCZEUR", "XLTCZUSD", "XMLNXETH", "XMLNXXBT", "XREPXETH", "XREPXXBT", "XREPZEUR", "XXBTZCAD", "XXBTZEUR", "XXBTZGBP", "XXBTZJPY", "XXBTZUSD", "XXDGXXBT", "XXLMXXBT", "XXMRXXBT", "XXMRZEUR", "XXMRZUSD", "XXRPXXBT", "XXRPZEUR", "XXRPZUSD", "XZECXXBT", "XZECZEUR", "XZECZUSD"));
			ITickersDao tickersDao = new TickersDBDao(CONN);
			tickersDao.persistNewTickers(5L, fromWeb);
			List<Ticker> fromDB = tickersDao.getTickers();
			out.println(fromWeb.equals(fromDB));
		} catch (KrakenResponseError krakenResponseError) {
			krakenResponseError.printStackTrace();
		} catch (KrakenCallException e) {
			e.printStackTrace();
		}
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
