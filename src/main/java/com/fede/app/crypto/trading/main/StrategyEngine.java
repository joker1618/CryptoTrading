package com.fede.app.crypto.trading.main;

import com.fede.app.crypto.trading.config.CryptoConfigImpl;
import com.fede.app.crypto.trading.config.ICryptoConfig;
import com.fede.app.crypto.trading.datalayer.CryptoModelFactory;
import com.fede.app.crypto.trading.datalayer.ICryptoModel;
import com.fede.app.crypto.trading.exception.KrakenCallException;
import com.fede.app.crypto.trading.exception.KrakenResponseError;
import com.fede.app.crypto.trading.exception.TechnicalException;
import com.fede.app.crypto.trading.kraken.IKrakenFacade;
import com.fede.app.crypto.trading.kraken.KrakenFacadeImpl;
import com.fede.app.crypto.trading.logger.ISimpleLog;
import com.fede.app.crypto.trading.logger.LogService;
import com.fede.app.crypto.trading.model._public.AssetPair;
import com.fede.app.crypto.trading.model._public.Ticker;
import com.fede.app.crypto.trading.model._trading.AddOrderIn;
import com.fede.app.crypto.trading.model._trading.AddOrderOut;
import com.fede.app.crypto.trading.model._trading.OrderRequest;
import com.fede.app.crypto.trading.strategy.IStrategyConfig;
import com.fede.app.crypto.trading.strategy.StrategyConfigImpl;
import com.fede.app.crypto.trading.util.Utils;
import javafx.beans.binding.DoubleBinding;

import java.nio.file.Path;
import java.util.List;

/**
 * Created by f.barbano on 01/11/2017.
 */
class StrategyEngine {

	private static final ISimpleLog logger = LogService.getLogger(StrategyEngine.class);

	private final ICryptoConfig config = CryptoConfigImpl.getInstance();

	private final Path strategyPath;
	private final IStrategyConfig strategy;
	private final ICryptoModel model;
	private final IKrakenFacade krakenFacade;

	private final String assetPairName;


	StrategyEngine(Path strategyPath) {
		this.strategyPath = strategyPath;
		this.model = CryptoModelFactory.getModel();
		this.krakenFacade = new KrakenFacadeImpl(config.getKrakenApiKey(), config.getKrakenApiSecret());
		this.strategy = new StrategyConfigImpl(strategyPath);

		List<AssetPair> apList = model.getAssetPairs(true);
		if(apList.isEmpty()) {
			throw new TechnicalException("No asset pairs to work on: cannot start strategy");
		}
		List<String> allNames = Utils.map(apList, AssetPair::getPairName);
		assetPairName = strategy.getAssetPairName();
		if(!allNames.contains(assetPairName)) {
			throw new TechnicalException("Strategy asset pair \"%s\" does not exists", assetPairName);
		}
	}

	void startEngine() {
		logger.info("Start strategy engine for asset pair %s", assetPairName);
		logger.info("Strategy path: %s", strategy);

	}

	private void performStrategy() {
		Ticker ticker = model.retrieveAskPriceAndAverage(assetPairName);

		Double buyPrice = getBuyPrice(ticker);
		if(buyPrice != null) {
			double volume = strategy.getBuyBudget() / buyPrice;
			AddOrderIn orderIn = OrderRequest.createBuyLimitOrderIn(ticker.getPairName(), buyPrice, volume);
			AddOrderOut orderOut = null;
			try {
				orderOut = krakenFacade.addOrder(orderIn);

			} catch (KrakenResponseError krakenResponseError) {
				logger.error("Error while add order [%s]", krakenResponseError.getMessage());
			} catch (KrakenCallException e) {
				orderOut = retrieveOrderAfterException();
			}

			if(orderOut != null) {

			}
		}


	}

	private Double getBuyPrice(Ticker ticker) {
		Double last24Hours = ticker.getWeightedAverageVolume().getLast24Hours();
		Double delta = last24Hours * strategy.getPercentageBuyLower() / 100d;
		Double thresold = last24Hours - delta;
		Double price = null;
		if(ticker.getAsk().getPrice() <= thresold) {
			price = thresold;
		}
		return price;
	}

	private AddOrderOut retrieveOrderAfterException() {
		return null;
	}

	private void doEmitBuyOrder(String assetPairName) {
		// review how much buy?
		Double volume = 0d;
		Double price = 0d;


	}


}
