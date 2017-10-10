package com.fede.app.crypto.trading.datalayer;

import com.fede.app.crypto.trading.dao.IAssetPairsDao;
import com.fede.app.crypto.trading.dao.IAssetsDao;
import com.fede.app.crypto.trading.dao.ITickersDao;
import com.fede.app.crypto.trading.model._public.Asset;
import com.fede.app.crypto.trading.model._public.AssetPair;
import com.fede.app.crypto.trading.model._public.Ticker;

import java.util.Collection;
import java.util.List;

/**
 * Created by f.barbano on 01/10/2017.
 */
class KrakenRepoImpl implements IKrakenRepo {

	private IAssetsDao assetDao;
	private IAssetPairsDao assetPairsDao;
	private ITickersDao tickersDao;

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


	void setAssetDao(IAssetsDao assetDao) {
		this.assetDao = assetDao;
	}
	void setAssetPairsDao(IAssetPairsDao assetPairsDao) {
		this.assetPairsDao = assetPairsDao;
	}
	public void setTickersDao(ITickersDao tickersDao) {
		this.tickersDao = tickersDao;
	}
}
