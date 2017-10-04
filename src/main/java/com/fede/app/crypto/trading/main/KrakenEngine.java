package com.fede.app.crypto.trading.main;

import com.fede.app.crypto.trading.config.IKrakenConfig;
import com.fede.app.crypto.trading.config.KrakenConfigImpl;
import com.fede.app.crypto.trading.logger.ISimpleLog;
import com.fede.app.crypto.trading.logger.LogService;
import com.fede.app.crypto.trading.model._public.Asset;
import com.fede.app.crypto.trading.model._public.AssetPair;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Created by f.barbano on 01/10/2017.
 */
public class KrakenEngine {

	private static final ISimpleLog logger = LogService.getLogger(KrakenEngine.class);

	private final IKrakenConfig config;
	private List<Asset> assetList;
	private List<AssetPair> assetPairList;

	public KrakenEngine() {
		this.config = KrakenConfigImpl.getInstance();
		this.assetList = retrieveAssetList();
		this.assetPairList = retrieveAssetPairList();
	}

	public void startKrakenEngine() {

		ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
//		executorService.scheduleAtFixedRate()

	}


	private List<Asset> retrieveAssetList() {
		// TODO impl
		return null;
	}

	private List<AssetPair> retrieveAssetPairList() {
		// TODO impl
		return null;
	}

}
