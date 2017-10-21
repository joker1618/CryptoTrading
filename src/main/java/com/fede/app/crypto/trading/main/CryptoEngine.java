package com.fede.app.crypto.trading.main;

import com.fede.app.crypto.trading.config.ICryptoConfig;
import com.fede.app.crypto.trading.config.CryptoConfigImpl;
import com.fede.app.crypto.trading.datalayer.CryptoModelFactory;
import com.fede.app.crypto.trading.datalayer.ICryptoModel;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


/**
 * Created by f.barbano on 01/10/2017.
 */
public class CryptoEngine {

	private static final ISimpleLog logger = LogService.getLogger(CryptoEngine.class);

	private final ICryptoConfig config;

	private ICryptoModel model;
	private IKrakenFacade krakenFacade;
//
//	private final List<Asset> assetList;
	private final List<String> assetPairNames;

	public CryptoEngine() {
		this.config = CryptoConfigImpl.getInstance();
		this.model = CryptoModelFactory.getModel();
		this.krakenFacade = new KrakenFacadeImpl(config.getKrakenApiKey(), config.getKrakenApiSecret());
//		this.assetList = Collections.synchronizedList(new ArrayList<>());
		this.assetPairNames = Collections.synchronizedList(new ArrayList<>());
	}

	public void startKrakenEngine() {
		logger.info("Start kraken engine");

		// Before start, assets and assetPairs must be downloaded
		// If kraken call fails, fallback to database data, if any
		updateAssets();
		updateAssetPairs();

		// Now schedule fixed rate tasks (i.e. public calls to repeat)
		LocalDateTime nextDayStart = LocalDateTime.of(LocalDate.now().plusDays(1), LocalTime.MIDNIGHT);
		long delayUntilMidnight = (DateUtils.toMillis(nextDayStart) - System.currentTimeMillis()) / 1000L;

		ScheduledExecutorService executorPublic = Executors.newScheduledThreadPool(4);
		executorPublic.scheduleAtFixedRate(this::updateAssets, 5, config.getCallSecondsRateAssets(), TimeUnit.SECONDS);
		executorPublic.scheduleAtFixedRate(this::updateAssetPairs, 5, config.getCallSecondsRateAssetPairs(), TimeUnit.SECONDS);
		executorPublic.scheduleAtFixedRate(this::downloadTickers, 5, config.getCallSecondsRateTickers(), TimeUnit.SECONDS);
//		executorPublic.scheduleAtFixedRate(this::downloadSpreadData, delayDownload, config.getCallSecondsRateSpreadData(), TimeUnit.SECONDS);

		logger.info("Public Kraken calls scheduled");

		while(true) {
			try {
				Thread.sleep(60000);
			} catch (InterruptedException e) {
				throw new TechnicalException(e);
			}
		}
	}


	private void updateAssets() {
		try {
			long callTime = System.currentTimeMillis();
			List<Asset> fromCall = krakenFacade.getAssets();
			boolean changed = model.updateAssets(callTime, fromCall);
			logger.info("Assets %s", (changed ? "updated" : "not changed"));

		} catch (KrakenResponseError | KrakenCallException ex) {
			logger.error(ex);
			// review
//			throw new TechnicalException(ex);
		}
	}

	private void updateAssetPairs() {
		try {
			long callTime = System.currentTimeMillis();
			List<AssetPair> fromCall = krakenFacade.getAssetPairs();
			boolean changed = model.updateAssetPairs(callTime, fromCall);
			logger.info("Asset pairs %s", (changed ? "updated" : "not changed"));
			if(changed || assetPairNames.isEmpty()) {
				synchronized (assetPairNames) {
					List<String> names = Utils.filterAndMap(model.getAssetPairs(), ap -> !ap.getPairName().endsWith(".d"), AssetPair::getPairName);
					assetPairNames.clear();
					assetPairNames.addAll(names);
				}
			}

		} catch (KrakenResponseError | KrakenCallException ex) {
			logger.error(ex);
			// review
//			throw new TechnicalException(ex);
		}
	}

	private List<String> getAssetPairNames() {
		synchronized (assetPairNames) {
			return new ArrayList<>(assetPairNames);
		}
	}

	private void downloadTickers() {
		try {
			List<Ticker> tickers = krakenFacade.getTickers(getAssetPairNames());
			long callTime = System.currentTimeMillis();
			model.persistTickers(callTime, tickers);
			logger.info("%d new tickers downloaded", tickers.size());

		} catch (KrakenResponseError | KrakenCallException ex) {
			logger.error(ex, "Error received while calling Tickers method");
			// REVIEW at the moment exceptions received are logged as a warning and then ignored: the engine must go on
			//throw new TechnicalException(ex);
		}
	}

//	private void downloadSpreadData() {
//		List<String> assetPairNames = getAssetPairNames(false);
//		Map<String, Long> lastMap = Collections.synchronizedMap(model.getLastCallTimes(KrakenMethod.SPREAD));
//
//		List<LastCallDescr> lastTimeList = Collections.synchronizedList(new ArrayList<>());
//		List<SpreadData> spreadDataList = Collections.synchronizedList(new ArrayList<>());
//
//		// Call Kraken
//		assetPairNames.parallelStream().forEach(ap -> {
//			try {
//				Pair<Long, List<SpreadData>> sd = krakenFacade.getSpreadData(ap, lastMap.get(ap));
//				lastTimeList.add(new LastCallDescr(ap, KrakenMethod.SPREAD.getName(), sd.getKey()));
//				spreadDataList.addAll(sd.getValue());
//			} catch (KrakenResponseError | KrakenCallException e) {
//				logger.error(e, "Error while downloading spread data for %s", ap);
//			}
//		});
//
//		// Persist data
//		model.persistLastCallTimes(lastTimeList);
//		model.persistSpreadData(spreadDataList);
//	}

}
