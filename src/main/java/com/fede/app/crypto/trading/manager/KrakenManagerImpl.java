package com.fede.app.crypto.trading.manager;

import com.fede.app.crypto.trading.common.Const;
import com.fede.app.crypto.trading.facade.IKrakenCaller;
import com.fede.app.crypto.trading.facade.IKrakenProvider;
import com.fede.app.crypto.trading.facade.KrakenCallerImpl;
import com.fede.app.crypto.trading.facade.KrakenProviderImpl;
import com.fede.app.crypto.trading.model.Asset;
import com.fede.app.crypto.trading.model.AssetPair;
import com.fede.app.crypto.trading.model.Ticker;
import com.fede.app.crypto.trading.util.CheckUtils;
import com.fede.app.crypto.trading.util.Utils;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by f.barbano on 15/09/2017.
 */
public class KrakenManagerImpl implements IKrakenManager {

	private static final IKrakenManager INSTANCE = new KrakenManagerImpl();

	private final IKrakenCaller krakenCaller;
	private final IKrakenProvider krakenProvider;

	private Map<String, Asset> assetMap;
	private Map<String, AssetPair> assetPairMap;



	private KrakenManagerImpl() {
		this.krakenCaller = new KrakenCallerImpl(Const.KRAKEN_KEY, Const.KRAKEN_SECRET);
		this.krakenProvider = new KrakenProviderImpl(Const.CSV_FOLDER);
	}

	public static IKrakenManager getInstance() {
		return INSTANCE;
	}


	@Override
	public long getServerTime() {
		try {
			return krakenCaller.getServerTime();
		} catch (IOException ex) {
			// TODO manage
			throw new RuntimeException(ex);
		}
	}

	@Override
	public Map<String, Asset> synchronizeAssets() {
		try {
			// read from file
			if (assetMap == null) {
				List<Asset> assetList = krakenProvider.readAssets();
				assetMap = Utils.toMapSingle(assetList, Asset::getName);
			}

			// download from kraken website
			List<Asset> newAssets = krakenCaller.getAssets();
			Map<String, Asset> newAssetMap = Utils.toMapSingle(newAssets, Asset::getName);

			// persist if assets changed
			if(!CheckUtils.areEquals(assetMap, newAssetMap)) {
				assetMap = newAssetMap;
				krakenProvider.persistAssets(newAssets);
			}

			return assetMap;

		} catch(IOException ex) {
			// TODO manage
			throw new RuntimeException(ex);
		}
	}

	@Override
	public Map<String, AssetPair> synchronizeAssetPairs() {
		try {
			// read from file
			if (assetPairMap == null) {
				List<AssetPair> assetPairs = krakenProvider.readAssetPairs();
				assetPairMap = Utils.toMapSingle(assetPairs, AssetPair::getPairName);
			}

			// download from kraken website
			List<AssetPair> newAssetPairs = krakenCaller.getAssetPairs();
			Map<String, AssetPair> newAssetPairMap = Utils.toMapSingle(newAssetPairs, AssetPair::getPairName);

			// persist if assets changed
			if(!CheckUtils.areEquals(assetPairMap, newAssetPairMap)) {
				assetPairMap = newAssetPairMap;
				krakenProvider.persistAssetPairs(newAssetPairs);
			}

			return assetPairMap;

		} catch(IOException ex) {
			// TODO manage
			throw new RuntimeException(ex);
		}
	}

	@Override
	public Map<String, Ticker> downloadTickers() {
		try {
			// synch AssetPair names if needed
			if(assetPairMap == null) {
				synchronizeAssetPairs();
			}
			Set<String> pairNames = assetPairMap.keySet();

			// download from kraken website
			List<Ticker> tickers = krakenCaller.getTickers(pairNames);
			krakenProvider.persistTickers(tickers);
			return Utils.toMapSingle(tickers, Ticker::getPairName);

		} catch(IOException ex) {
			// TODO manage
			throw new RuntimeException(ex);
		}
	}
}
