package com.fede.app.crypto.trading.main;

import com.fede.app.crypto.trading.config.IKrakenConfig;
import com.fede.app.crypto.trading.config.KrakenConfigImpl;
import com.fede.app.crypto.trading.datalayer.IKrakenRepo;
import com.fede.app.crypto.trading.datalayer.KrakenRepoFactory;
import com.fede.app.crypto.trading.exception.KrakenCallException;
import com.fede.app.crypto.trading.exception.KrakenResponseError;
import com.fede.app.crypto.trading.exception.TechnicalException;
import com.fede.app.crypto.trading.kraken.IKrakenFacade;
import com.fede.app.crypto.trading.kraken.KrakenFacadeImpl;
import com.fede.app.crypto.trading.logger.ISimpleLog;
import com.fede.app.crypto.trading.logger.LogService;
import com.fede.app.crypto.trading.model._public.Asset;
import com.fede.app.crypto.trading.model._public.AssetPair;
import com.fede.app.crypto.trading.model._public.Ticker;
import com.fede.app.crypto.trading.util.DateUtils;
import com.fede.app.crypto.trading.util.Utils;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by f.barbano on 01/10/2017.
 */
public class KrakenEngine {

	private static final ISimpleLog logger = LogService.getLogger(KrakenEngine.class);

	private final IKrakenConfig config;

	private IKrakenRepo krakenRepo;
	private IKrakenFacade krakenFacade;

	private List<Asset> assetList;
	private List<AssetPair> assetPairList;

	public KrakenEngine() {
		this.config = KrakenConfigImpl.getInstance();
		this.krakenRepo = KrakenRepoFactory.getRepo();
		this.krakenFacade = new KrakenFacadeImpl(config.getKrakenApiKey(), config.getKrakenApiSecret());
		this.assetList = Collections.synchronizedList(new ArrayList<>());
		this.assetPairList = Collections.synchronizedList(new ArrayList<>());
	}

	public void startKrakenEngine() {
		logger.info("Start kraken engine");

		// Before start, assets and assetPairs must be downloaded
		// If kraken call fails, fallback to database data, if any
//		retrieveAssetList();
		retrieveAssetPairList();

		if(1 == 1)	return ;
		
		// Now schedule fixed rate tasks (i.e. public calls to repeat)
		LocalDateTime nextDayStart = LocalDateTime.of(LocalDate.now().plusDays(1), LocalTime.MIDNIGHT);
		long delay = (DateUtils.toMillis(nextDayStart) - System.currentTimeMillis()) / 1000L;

		ScheduledExecutorService executorPublic = Executors.newScheduledThreadPool(4);
		executorPublic.scheduleAtFixedRate(this::retrieveAssetList, delay, config.getCallSecondsRateAssets(), TimeUnit.SECONDS);
		executorPublic.scheduleAtFixedRate(this::retrieveAssetPairList, delay, config.getCallSecondsRateAssetPairs(), TimeUnit.SECONDS);

		logger.info("Public Kraken calls scheduled");

		while(true) {
			try {
				Thread.sleep(60000);
			} catch (InterruptedException e) {
				throw new TechnicalException(e);
			}
		}
	}


	private void retrieveAssetList() {
		synchronized (assetList) {
			this.assetList.clear();

			try {
				long callTime = System.currentTimeMillis();
				this.assetList.addAll(krakenFacade.getAssets());
				krakenRepo.persistNewAssets(callTime, assetList);

			} catch (KrakenResponseError ex) {
				logger.error(ex);
				throw new TechnicalException(ex);
			} catch (KrakenCallException e) {
				logger.warning("Kraken call exception received [%s]. Fallback to DB...", e.getMessage());
				this.assetList.addAll(krakenRepo.getAssets());
				if (assetList.isEmpty()) {
					logger.error(e);
					throw new TechnicalException(e);
				}
			}
		}
	}

	private void retrieveAssetPairList() {
		synchronized (assetPairList) {
			this.assetPairList.clear();

			try {
				long callTime = System.currentTimeMillis();
				this.assetPairList.addAll(krakenFacade.getAssetPairs());
				krakenRepo.persistNewAssetPairs(callTime, assetPairList);

			} catch (KrakenResponseError ex) {
				logger.error(ex);
				throw new TechnicalException(ex);
			} catch (KrakenCallException e) {
				logger.warning("Kraken call exception received [%s]. Fallback to DB...", e.getMessage());
				this.assetPairList.addAll(krakenRepo.getAssetPairs());
				if (assetPairList.isEmpty()) {
					logger.error(e);
					throw new TechnicalException(e);
				}
			}
		}
	}

	private void downloadTickers() throws KrakenResponseError, KrakenCallException {
		List<String> assetPairNames = getAssetPairNames();
		List<Ticker> tickers = krakenFacade.getTickers(assetPairNames);

		synchronized (assetPairList) {
			this.assetPairList.clear();

			try {
				long callTime = System.currentTimeMillis();
				this.assetPairList.addAll(krakenFacade.getAssetPairs());
				krakenRepo.persistNewAssetPairs(callTime, assetPairList);

			} catch (KrakenResponseError ex) {
				logger.error(ex);
				throw new TechnicalException(ex);
			} catch (KrakenCallException e) {
				logger.warning("Kraken call exception received [%s]. Fallback to DB...", e.getMessage());
				this.assetPairList.addAll(krakenRepo.getAssetPairs());
				if (assetPairList.isEmpty()) {
					logger.error(e);
					throw new TechnicalException(e);
				}
			}
		}
	}

	private List<String> getAssetPairNames() {
		synchronized (assetPairList) {
			return Utils.map(assetPairList, AssetPair::getPairName);
		}
	}

}
