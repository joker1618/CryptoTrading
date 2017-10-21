package com.fede.app.crypto.trading.dao.impl;

import com.fede.app.crypto.trading.dao.ISpreadDataDao;
import com.fede.app.crypto.trading.exception.TechnicalException;
import com.fede.app.crypto.trading.logger.ISimpleLog;
import com.fede.app.crypto.trading.logger.LogService;
import com.fede.app.crypto.trading.model._public.SpreadData;
import com.fede.app.crypto.trading.util.Utils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by f.barbano on 10/10/2017.
 */
public class SpreadDataDBDao extends AbstractDBDao implements ISpreadDataDao {

	// review all


	private static final ISimpleLog logger = LogService.getLogger(SpreadDataDBDao.class);

	private static final String Q_SELECT_ALL = "SELECT * FROM SPREAD_DATA";
	private static final String Q_INSERT_NEW = "REPLACE INTO SPREAD_DATA (SPREAD_TIME, PAIR_NAME, BID, ASK) VALUES @SPREAD_DATA@";

	private static final String PH_SPREAD_DATA = "@SPREAD_DATA@";

	public SpreadDataDBDao(Connection connection) {
		super(connection);
	}

	@Override
	public List<SpreadData> getSpreadData() {
		try (PreparedStatement ps = connection.prepareStatement(Q_SELECT_ALL);
			 ResultSet rs = ps.executeQuery()){

			List<SpreadData> spreadData = new ArrayList<>();
			if(rs != null) {
				while(rs.next()) {
					SpreadData sd = new SpreadData();
					sd.setTime(rs.getLong("SPREAD_TIME"));
					sd.setPairName(rs.getString("PAIR_NAME"));
					sd.setBid(rs.getDouble("BID"));
					sd.setAsk(rs.getDouble("ASK"));
					spreadData.add(sd);
				}
			}

			spreadData.sort((o1, o2) -> {
				if(o1.getTime() == o2.getTime()) {
					return o1.getPairName().compareTo(o2.getPairName());
				} else {
					return (int)(o1.getTime() - o2.getTime());
				}
			});

			return spreadData;

		} catch (SQLException e) {
			logger.error(e);
			throw new TechnicalException(e);
		}
	}

	@Override
	public void persistNewSpreadData(Collection<SpreadData> spreadData) {
		String values = Utils.join(spreadData, ",", this::spreadDataToValues);
		String query = Q_INSERT_NEW.replace(PH_SPREAD_DATA, values);
		PreparedStatement ps = null;

		try {
			ps = connection.prepareStatement(query);
			int numInsert = ps.executeUpdate();
			logger.info("%d new spread data inserted", numInsert);

		} catch (SQLException e) {
			logger.error(e);
			throw new TechnicalException(e);
		} finally {
			try {
				if (ps != null && !ps.isClosed()) 	ps.close();
			} catch (SQLException e) {
				logger.error(e, "Unable to close JDBC statement");
				throw new TechnicalException(e, "Unable to close JDBC statements");
			}
		}
	}

	private String spreadDataToValues(SpreadData spreadData) {
		return String.format("(%d, '%s', %s, %s)",
			spreadData.getTime(),
			spreadData.getPairName(),
			Utils.toString(spreadData.getBid()),
			Utils.toString(spreadData.getAsk())
		);
	}
}
