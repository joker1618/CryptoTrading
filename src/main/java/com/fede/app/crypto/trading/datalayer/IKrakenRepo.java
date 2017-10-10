package com.fede.app.crypto.trading.datalayer;

import com.fede.app.crypto.trading.model._public.Asset;
import com.fede.app.crypto.trading.model._public.AssetPair;
import com.fede.app.crypto.trading.model._public.Ticker;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by f.barbano on 01/10/2017.
 */
public interface IKrakenRepo {

	List<Asset> getAssets();
	void persistNewAssets(Long callTime, List<Asset> assets);

	List<AssetPair> getAssetPairs();
	void persistNewAssetPairs(Long callTime, List<AssetPair> assetPairs);

	void persistNewTickers(Long callTime, Collection<Ticker> tickers);

}
