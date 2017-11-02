package com.fede.app.crypto.trading.dao.impl;

import com.fede.app.crypto.trading.dao.ITickersDao;
import com.fede.app.crypto.trading.exception.TechnicalException;
import com.fede.app.crypto.trading.logger.ISimpleLog;
import com.fede.app.crypto.trading.logger.LogService;
import com.fede.app.crypto.trading.model._public.Ticker;
import com.fede.app.crypto.trading.util.DateUtils;
import com.fede.app.crypto.trading.util.Utils;
import org.apache.commons.lang3.StringUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import static com.fede.app.crypto.trading.model._public.Ticker.*;

/**
 * Created by f.barbano on 30/09/2017.
 */
public class TickersDBDao extends AbstractDBDao implements ITickersDao {

	private static final ISimpleLog logger = LogService.getLogger(TickersDBDao.class);

	private static final String Q_SELECT_ALL = "SELECT * FROM TICKERS";
	private static final String Q_INSERT_NEW = "INSERT INTO TICKERS (CALL_TIME, PAIR_NAME, ASK_PRICE, ASK_WHOLE_LOT_VOLUME, ASK_LOT_VOLUME, BID_PRICE, " +
												   "BID_WHOLE_LOT_VOLUME, BID_LOT_VOLUME, LAST_CLOSED_PRICE, LAST_CLOSED_LOT_VOLUME, VOLUME_TODAY, VOLUME_LAST_24, " +
												   "VOLUME_WEIGHTED_AVERAGE_TODAY, VOLUME_WEIGHTED_AVERAGE_LAST_24, NUMBER_TRADES_TODAY, NUMBER_TRADES_LAST_24, LOW_TODAY, " +
												   "LOW_LAST_24, HIGH_TODAY, HIGH_LAST_24, OPENING_PRICE, GRAFANA_TIME) VALUES @TICKER_VALUES@";
	private static final String Q_INSERT_NEW_SINGLE = "INSERT INTO TICKERS (CALL_TIME, PAIR_NAME, ASK_PRICE, ASK_WHOLE_LOT_VOLUME, ASK_LOT_VOLUME, BID_PRICE, " +
												   "BID_WHOLE_LOT_VOLUME, BID_LOT_VOLUME, LAST_CLOSED_PRICE, LAST_CLOSED_LOT_VOLUME, VOLUME_TODAY, VOLUME_LAST_24, " +
												   "VOLUME_WEIGHTED_AVERAGE_TODAY, VOLUME_WEIGHTED_AVERAGE_LAST_24, NUMBER_TRADES_TODAY, NUMBER_TRADES_LAST_24, LOW_TODAY, " +
												   "LOW_LAST_24, HIGH_TODAY, HIGH_LAST_24, OPENING_PRICE, GRAFANA_TIME) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private static final String Q_SELECT_ASK_PRICE_AND_AVERAGE = "SELECT PAIR_NAME, ASK_PRICE, VOLUME_WEIGHTED_AVERAGE_LAST_24 FROM TICKERS WHERE PAIR_NAME = ? AND CALL_TIME = (SELECT MAX(CALL_TIME) FROM TICKERS)";

	private static final String PH_TICKER_VALUES = "@TICKER_VALUES@";

	public TickersDBDao(Connection connection) {
		super(connection);
	}

	@Override
	public List<Ticker> getTickers() {
		try (PreparedStatement ps = connection.prepareStatement(Q_SELECT_ALL);
			 ResultSet rs = ps.executeQuery()) {

			List<Ticker> tickers = new ArrayList<>();
			if (rs != null) {
				while (rs.next()) {
					Ticker t = parseTicker(rs, Q_SELECT_ALL);
					tickers.add(t);
				}
			}

			tickers.sort((o1, o2) -> {
				if (o1.getCallTime() == o2.getCallTime()) {
					return o1.getPairName().compareTo(o2.getPairName());
				} else {
					return (int) (o1.getCallTime() - o2.getCallTime());
				}
			});

			return tickers;

		} catch (SQLException e) {
			logger.error(e);
			throw new TechnicalException(e);
		}
	}

	@Override
	public void persistNewTickers(Long callTime, Collection<Ticker> tickers) {
		String values = Utils.join(tickers, ",", t -> tickerToValues(callTime, t));
		String query = Q_INSERT_NEW.replace(PH_TICKER_VALUES, values);
		int numInsert = super.performUpdate(query);
		logger.info("Insert %d new tickers", numInsert);
	}

	@Override
	public Ticker retrieveAskPriceAndAverage(String tickerName) {
		String query = Q_SELECT_ASK_PRICE_AND_AVERAGE;

		try (PreparedStatement ps = connection.prepareStatement(query)) {
			ps.setString(1, tickerName);
			ResultSet rs = ps.executeQuery();
			Ticker t = null;
			if (rs != null) {
				if (rs.next()) {
					t = parseTicker(rs, query);
				}
			}
			return t;

		} catch (SQLException e) {
			logger.error(e);
			throw new TechnicalException(e);
		}
	}

	private String tickerToValues(Long callTime, Ticker ticker) {
		return String.format("(%d, '%s', %s, %d, %s, %s, %d, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s)",
			callTime,
			ticker.getPairName(),
			Utils.toString(ticker.getAsk().getPrice()),
			ticker.getAsk().getWholeLotVolume(),
			Utils.toString(ticker.getAsk().getLotVolume()),
			Utils.toString(ticker.getBid().getPrice()),
			ticker.getBid().getWholeLotVolume(),
			Utils.toString(ticker.getBid().getLotVolume()),
			Utils.toString(ticker.getLastTradeClosed().getPrice()),
			Utils.toString(ticker.getLastTradeClosed().getLotVolume()),
			Utils.toString(ticker.getVolume().getToday()),
			Utils.toString(ticker.getVolume().getLast24Hours()),
			Utils.toString(ticker.getWeightedAverageVolume().getToday()),
			Utils.toString(ticker.getWeightedAverageVolume().getLast24Hours()),
			Utils.toString(ticker.getTradesNumber().getToday()),
			Utils.toString(ticker.getTradesNumber().getLast24Hours()),
			Utils.toString(ticker.getLow().getToday()),
			Utils.toString(ticker.getLow().getLast24Hours()),
			Utils.toString(ticker.getHigh().getToday()),
			Utils.toString(ticker.getHigh().getLast24Hours()),
			Utils.toString(ticker.getOpeningPrice()),
			DateUtils.toString(callTime, "yyyyMMddHHmmss")
		);
	}

	private Ticker parseTicker(ResultSet rs, String querySelect) {
		try {
			String strFields = StringUtils.substringBetween(querySelect.toUpperCase(), "SELECT", "FROM");

			Ticker t = new Ticker();
			if(strFields.contains("CALL_TIME"))		t.setCallTime(rs.getLong("CALL_TIME"));
			if(strFields.contains("PAIR_NAME"))		t.setPairName(rs.getString("PAIR_NAME"));
			if(strFields.contains("OPENING_PRICE"))		t.setOpeningPrice(rs.getDouble("OPENING_PRICE"));

			TickerWholePrice ask = new TickerWholePrice();
			if(strFields.contains("ASK_PRICE"))		ask.setPrice(rs.getDouble("ASK_PRICE"));
			if(strFields.contains("ASK_WHOLE_LOT_VOLUME"))		ask.setWholeLotVolume(rs.getInt("ASK_WHOLE_LOT_VOLUME"));
			if(strFields.contains("ASK_LOT_VOLUME"))		ask.setLotVolume(rs.getDouble("ASK_LOT_VOLUME"));
			t.setAsk(ask);

			TickerWholePrice bid = new TickerWholePrice();
			if(strFields.contains("BID_PRICE"))		bid.setPrice(rs.getDouble("BID_PRICE"));
			if(strFields.contains("BID_WHOLE_LOT_VOLUME"))		bid.setWholeLotVolume(rs.getInt("BID_WHOLE_LOT_VOLUME"));
			if(strFields.contains("BID_LOT_VOLUME"))		bid.setLotVolume(rs.getDouble("BID_LOT_VOLUME"));
			t.setBid(bid);

			TickerPrice last = new TickerPrice();
			if(strFields.contains("LAST_CLOSED_PRICE"))		last.setPrice(rs.getDouble("LAST_CLOSED_PRICE"));
			if(strFields.contains("LAST_CLOSED_LOT_VOLUME"))		last.setLotVolume(rs.getDouble("LAST_CLOSED_LOT_VOLUME"));
			t.setLastTradeClosed(last);

			TickerVolume volume = new TickerVolume();
			if(strFields.contains("VOLUME_TODAY"))		volume.setToday(rs.getDouble("VOLUME_TODAY"));
			if(strFields.contains("VOLUME_LAST_24"))		volume.setLast24Hours(rs.getDouble("VOLUME_LAST_24"));
			t.setVolume(volume);

			TickerVolume volWeighted = new TickerVolume();
			if(strFields.contains("VOLUME_WEIGHTED_AVERAGE_TODAY"))		volWeighted.setToday(rs.getDouble("VOLUME_WEIGHTED_AVERAGE_TODAY"));
			if(strFields.contains("VOLUME_WEIGHTED_AVERAGE_LAST_24"))		volWeighted.setLast24Hours(rs.getDouble("VOLUME_WEIGHTED_AVERAGE_LAST_24"));
			t.setWeightedAverageVolume(volWeighted);

			TickerVolume tradeNum = new TickerVolume();
			if(strFields.contains("NUMBER_TRADES_TODAY"))		tradeNum.setToday(rs.getDouble("NUMBER_TRADES_TODAY"));
			if(strFields.contains("NUMBER_TRADES_LAST_24"))		tradeNum.setLast24Hours(rs.getDouble("NUMBER_TRADES_LAST_24"));
			t.setTradesNumber(tradeNum);

			TickerVolume low = new TickerVolume();
			if(strFields.contains("LOW_TODAY"))		low.setToday(rs.getDouble("LOW_TODAY"));
			if(strFields.contains("LOW_LAST_24"))		low.setLast24Hours(rs.getDouble("LOW_LAST_24"));
			t.setLow(low);

			TickerVolume high = new TickerVolume();
			if(strFields.contains("HIGH_TODAY"))		high.setToday(rs.getDouble("HIGH_TODAY"));
			if(strFields.contains("HIGH_LAST_24"))		high.setLast24Hours(rs.getDouble("HIGH_LAST_24"));
			t.setHigh(high);

			return t;

		} catch (SQLException e) {
			logger.error(e, "Unable to parse ticker");
			throw new TechnicalException(e, "Unable to parse ticker");
		}
	}

}