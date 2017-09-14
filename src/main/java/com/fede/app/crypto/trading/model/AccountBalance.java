package com.fede.app.crypto.trading.model;

import java.util.Map;

/**
 * Created by f.barbano on 14/09/2017.
 */
public class AccountBalance {

	private Map<String, Double> accountBalanceMap;


	public Map<String, Double> getAccountBalanceMap() {
		return accountBalanceMap;
	}
	public void setAccountBalanceMap(Map<String, Double> accountBalanceMap) {
		this.accountBalanceMap = accountBalanceMap;
	}
}
