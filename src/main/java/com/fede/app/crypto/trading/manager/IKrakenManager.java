package com.fede.app.crypto.trading.manager;

import com.fede.app.crypto.trading.model.Asset;
import com.fede.app.crypto.trading.model.AssetPair;
import com.fede.app.crypto.trading.model.Ticker;

import java.util.Map;

/**
 * Created by f.barbano on 15/09/2017.
 */
public interface IKrakenManager {

	long getServerTime();

	Map<String, Asset> synchronizeAssets();
	Map<String, AssetPair> synchronizeAssetPairs();
	Map<String, Ticker> downloadTickers();

}
