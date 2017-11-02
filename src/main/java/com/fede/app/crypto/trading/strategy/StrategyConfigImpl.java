package com.fede.app.crypto.trading.strategy;

import com.fede.app.crypto.trading.exception.TechnicalException;
import com.fede.app.crypto.trading.logger.ISimpleLog;
import com.fede.app.crypto.trading.logger.LogService;
import com.fede.app.crypto.trading.util.StrUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by f.barbano on 01/11/2017.
 */
public class StrategyConfigImpl extends AbstractConfigService implements IStrategyConfig {

	private static final ISimpleLog logger = LogService.getLogger(StrategyConfigImpl.class);

	private static final String ASSET_PAIR_NAME = "strategy.asset.pair.name";
	private static final String TICKERS_POLLING_FREQUENCY = "strategy.tickers.polling.frequency";
	private static final String PERCENTAGE_BUY_LOWER = "strategy.buy.lower.percentage";
	private static final String PERCENTAGE_SELL_HIGHER = "strategy.sell.higher.percentage";
	private static final String BUY_BUDGET = "strategy.buy.budget";


	public StrategyConfigImpl(Path strategyPath) {
		try {
			loadConfigFile(strategyPath.toString());
		} catch (IOException e) {
			throw new TechnicalException(e, "Unable to init strategy from file [%s]", strategyPath);
		}

		// existence checks
		for(String key : Arrays.asList(ASSET_PAIR_NAME, TICKERS_POLLING_FREQUENCY, PERCENTAGE_BUY_LOWER, PERCENTAGE_SELL_HIGHER, BUY_BUDGET)) {
			if(!existsKey(key)) {
				throw new TechnicalException("Mandatory strategy key \"%s\" is missing", key);
			}
		}

		// type checks
		if(StringUtils.isBlank(getString(ASSET_PAIR_NAME))) {
			throw new TechnicalException("Key=\"%s\" must be not blank", ASSET_PAIR_NAME);
		}
		try {
			getInt(TICKERS_POLLING_FREQUENCY);
		} catch (NumberFormatException ex) {
			throw new TechnicalException("Key=\"%s\",value=\"%s\" must be an int", TICKERS_POLLING_FREQUENCY, getString(TICKERS_POLLING_FREQUENCY));
		}
		for(String key : Arrays.asList(PERCENTAGE_BUY_LOWER, PERCENTAGE_SELL_HIGHER, BUY_BUDGET)) {
			try {
				getDouble(key);
			} catch (NumberFormatException ex) {
				throw new TechnicalException("Key=\"%s\",value=\"%s\" must be a double", key, getString(key));
			}
		}
	}

	@Override
	public String getAssetPairName() {
		return getString(ASSET_PAIR_NAME);
	}

	@Override
	public int getTickersPollingFrequencySeconds() {
		return getInt(TICKERS_POLLING_FREQUENCY);
	}

	@Override
	public double getPercentageBuyLower() {
		return getDouble(PERCENTAGE_BUY_LOWER);
	}

	@Override
	public double getPercentageSellHigher() {
		return getDouble(PERCENTAGE_SELL_HIGHER);
	}

	@Override
	public double getBuyBudget() {
		return getDouble(BUY_BUDGET);
	}


}


