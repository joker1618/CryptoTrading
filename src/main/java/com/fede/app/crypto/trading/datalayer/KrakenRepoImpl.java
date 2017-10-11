package com.fede.app.crypto.trading.datalayer;

import com.fede.app.crypto.trading.dao.*;
import com.fede.app.crypto.trading.kraken.ApiMethod;
import com.fede.app.crypto.trading.model._public.Asset;
import com.fede.app.crypto.trading.model._public.AssetPair;
import com.fede.app.crypto.trading.model._public.SpreadData;
import com.fede.app.crypto.trading.model._public.Ticker;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import static com.fede.app.crypto.trading.dao.ILastCallTimeDao.LastCallDescr;


/**
 * Created by f.barbano on 01/10/2017.
 */
class KrakenRepoImpl implements IKrakenRepo {

	private IAssetsDao assetDao;
	private IAssetPairsDao assetPairsDao;
	private ITickersDao tickersDao;
	private ILastCallTimeDao lastCallTimeDao;
	private ISpreadDataDao spreadDataDao;

	@Override
	public List<Asset> getAssets() {
		return assetDao.getAssets();
	}

	@Override
	public void persistNewAssets(Long callTime, List<Asset> assets) {
		assetDao.persistNewAssets(callTime, assets);
	}

	@Override
	public List<AssetPair> getAssetPairs() {
		return assetPairsDao.getAssetPairs();
	}

	@Override
	public void persistNewAssetPairs(Long callTime, List<AssetPair> assetPairs) {
		assetPairsDao.persistNewAssetPairs(callTime, assetPairs);
	}

	@Override
	public void persistNewTickers(Long callTime, Collection<Ticker> tickers) {
		tickersDao.persistNewTickers(callTime, tickers);
	}

	@Override
	public Map<String, Long> getLastCallTimes(ApiMethod method) {
		return lastCallTimeDao.getLastCallTimes(method.getName());
	}

	@Override
	public void persistLastCallTimes(Collection<LastCallDescr> lastCallTimes) {
		lastCallTimeDao.persistLastCallTimes(lastCallTimes);
	}

	@Override
	public List<SpreadData> getSpreadData() {
		return spreadDataDao.getSpreadData();
	}

	@Override
	public void persistSpreadData(Collection<SpreadData> spreadData) {
		spreadDataDao.persistNewSpreadData(spreadData);
	}


	void setAssetDao(IAssetsDao assetDao) {
		this.assetDao = assetDao;
	}
	void setAssetPairsDao(IAssetPairsDao assetPairsDao) {
		this.assetPairsDao = assetPairsDao;
	}
	void setTickersDao(ITickersDao tickersDao) {
		this.tickersDao = tickersDao;
	}
	void setLastCallTimeDao(ILastCallTimeDao lastCallTimeDao) {
		this.lastCallTimeDao = lastCallTimeDao;
	}
	void setSpreadDataDao(ISpreadDataDao spreadDataDao) {
		this.spreadDataDao = spreadDataDao;
	}
}
