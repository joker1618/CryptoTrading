package com.fede.app.crypto.trading.datalayer;

import com.fede.app.crypto.trading.model._public.Asset;
import com.fede.app.crypto.trading.model._public.AssetPair;
import com.fede.app.crypto.trading.model._public.Ticker;

import java.util.Collection;
import java.util.List;
import java.util.Map;


/**
 * Created by f.barbano on 01/10/2017.
 */
public interface ICryptoModel {

	List<Asset> getAssets();
	boolean updateAssets(Long callTime, List<Asset> assets);

	List<AssetPair> getAssetPairs(boolean discardDotD);
	boolean updateAssetPairs(Long callTime, List<AssetPair> assetPairs);

	void persistTickers(Long callTime, Collection<Ticker> tickers);
	Ticker retrieveAskPriceAndAverage(String tickerName);

//	Map<String, Long> getLastCallTimes(KrakenMethod method);
//	void persistLastCallTimes(Collection<LastCallDescr> lastCallTimes);
//
//	List<SpreadData> getSpreadData();
//	void persistSpreadData(Collection<SpreadData> spreadData);
}
