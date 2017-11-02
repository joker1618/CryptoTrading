package com.fede.app.crypto.trading.datalayer;

import com.fede.app.crypto.trading.dao.IAssetPairsDao;
import com.fede.app.crypto.trading.dao.IAssetsDao;
import com.fede.app.crypto.trading.dao.ITickersDao;
import com.fede.app.crypto.trading.model._public.Asset;
import com.fede.app.crypto.trading.model._public.AssetPair;
import com.fede.app.crypto.trading.model._public.Ticker;

import java.util.*;
import java.util.stream.Collectors;


/**
 * Created by f.barbano on 01/10/2017.
 */
class CryptoModelImpl implements ICryptoModel {

	private IAssetsDao assetDao;
	private IAssetPairsDao assetPairsDao;
	private ITickersDao tickersDao;
//	private ILastCallTimeDao lastCallTimeDao;
//	private ISpreadDataDao spreadDataDao;

	@Override
	public List<Asset> getAssets() {
		return assetDao.getAssets();
	}

	@Override
	public boolean updateAssets(Long callTime, List<Asset> assets) {
		List<Asset> other = assets.stream().sorted(Comparator.comparing(Asset::getAssetName)).collect(Collectors.toList());
		if(getAssets().equals(other))	return false;
		assetDao.persistAssets(callTime, other);
		return true;
	}

	@Override
	public List<AssetPair> getAssetPairs(boolean discardDotD) {
		return assetPairsDao.getAssetPairs(discardDotD);
	}

	@Override
	public boolean updateAssetPairs(Long callTime, List<AssetPair> assetPairs) {
		List<AssetPair> other = assetPairs.stream().sorted(Comparator.comparing(AssetPair::getPairName)).collect(Collectors.toList());
		if(getAssetPairs(false).equals(other))	return false;
		assetPairsDao.persistAssetPairs(callTime, other);
		return true;
	}

	@Override
	public void persistTickers(Long callTime, Collection<Ticker> tickers) {
		tickersDao.persistNewTickers(callTime, tickers);
	}

	@Override
	public Ticker retrieveAskPriceAndAverage(String tickerName) {
		return tickersDao.retrieveAskPriceAndAverage(tickerName);
	}


//	@Override
//	public Map<String, Long> getLastCallTimes(KrakenMethod method) {
//		return lastCallTimeDao.getLastCallTimes(method.getName());
//	}
//
//	@Override
//	public void persistLastCallTimes(Collection<LastCallDescr> lastCallTimes) {
//		lastCallTimeDao.persistLastCallTimes(lastCallTimes);
//	}
//
//	@Override
//	public List<SpreadData> getSpreadData() {
//		return spreadDataDao.getSpreadData();
//	}
//
//	@Override
//	public void persistSpreadData(Collection<SpreadData> spreadData) {
//		spreadDataDao.persistNewSpreadData(spreadData);
//	}


	void setAssetDao(IAssetsDao assetDao) {
		this.assetDao = assetDao;
	}
	void setAssetPairsDao(IAssetPairsDao assetPairsDao) {
		this.assetPairsDao = assetPairsDao;
	}
	void setTickersDao(ITickersDao tickersDao) {
		this.tickersDao = tickersDao;
	}
//	void setLastCallTimeDao(ILastCallTimeDao lastCallTimeDao) {
//		this.lastCallTimeDao = lastCallTimeDao;
//	}
//	void setSpreadDataDao(ISpreadDataDao spreadDataDao) {
//		this.spreadDataDao = spreadDataDao;
//	}
}
