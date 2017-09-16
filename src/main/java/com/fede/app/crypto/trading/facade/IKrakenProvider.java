package com.fede.app.crypto.trading.facade;

import com.fede.app.crypto.trading.model.Asset;
import com.fede.app.crypto.trading.model.AssetPair;
import com.fede.app.crypto.trading.model.Ticker;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by f.barbano on 15/09/2017.
 */
public interface IKrakenProvider {

	void persistAssets(List<Asset> assetsInfo) throws IOException;
	List<Asset> readAssets() throws IOException;
	
	void persistAssetPairs(List<AssetPair> assetPairs) throws IOException;
	List<AssetPair> readAssetPairs() throws IOException;

	void persistTickers(List<Ticker> tickers) throws IOException;
	Map<String, List<Ticker>> readTickers(List<String> pairNames) throws IOException;

}
