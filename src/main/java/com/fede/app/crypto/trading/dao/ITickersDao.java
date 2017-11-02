package com.fede.app.crypto.trading.dao;

import com.fede.app.crypto.trading.model._public.Ticker;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * Created by f.barbano on 10/10/2017.
 */
public interface ITickersDao {

	List<Ticker> getTickers();
	
	void persistNewTickers(Long callTime, Collection<Ticker> tickers);

	Ticker retrieveAskPriceAndAverage(String tickerName);

}
