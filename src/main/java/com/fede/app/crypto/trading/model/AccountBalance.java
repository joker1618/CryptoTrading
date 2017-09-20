package com.fede.app.crypto.trading.model;

import java.util.Map;

/**
 * Created by f.barbano on 14/09/2017.
 */
public class AccountBalance {

	private long callTime;
	private String assetClass;
	private double balance;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof AccountBalance)) return false;

		AccountBalance that = (AccountBalance) o;

		if (callTime != that.callTime) return false;
		if (Double.compare(that.balance, balance) != 0) return false;
		return assetClass != null ? assetClass.equals(that.assetClass) : that.assetClass == null;
	}

	@Override
	public int hashCode() {
		int result;
		long temp;
		result = (int) (callTime ^ (callTime >>> 32));
		result = 31 * result + (assetClass != null ? assetClass.hashCode() : 0);
		temp = Double.doubleToLongBits(balance);
		result = 31 * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	public long getCallTime() {
		return callTime;
	}
	public void setCallTime(long callTime) {
		this.callTime = callTime;
	}
	public String getAssetClass() {
		return assetClass;
	}
	public void setAssetClass(String assetClass) {
		this.assetClass = assetClass;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}

}
