package com.fede.app.crypto.trading.strategy;

import java.util.List;

/**
 * Created by f.barbano on 01/11/2017.
 */
public interface IStrategyConfig {

	String getAssetPairName();

	int getTickersPollingFrequencySeconds();

	double getPercentageBuyLower();
	double getPercentageSellHigher();

	double getBuyBudget();

}
