package com.fede.app.crypto.trading.model;

import java.util.Map;

/**
 * Created by f.barbano on 14/09/2017.
 */
public class TradeBalance {

	/*
	eb = equivalent balance (combined balance of all currencies)
	tb = trade balance (combined balance of all equity currencies)
	m = margin amount of open positions
	n = unrealized net profit/loss of open positions
	c = cost basis of open positions
	v = current floating valuation of open positions
	e = equity = trade balance + unrealized net profit/loss
	mf = free margin = equity - initial margin (maximum margin available to open new positions)
	ml = margin level = (equity / initial margin) * 100
	 */

	private Map<String, Double> tradeBalanceMap;


	public Map<String, Double> getTradeBalanceMap() {
		return tradeBalanceMap;
	}
	public void setTradeBalanceMap(Map<String, Double> tradeBalanceMap) {
		this.tradeBalanceMap = tradeBalanceMap;
	}

}
