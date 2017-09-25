package com.fede.app.crypto.trading.model;

/**
 * Created by f.barbano on 14/09/2017.
 */
public class AccountBalance {

	private Long callTime;
	private String assetClass;
	private Double balance;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof AccountBalance)) return false;

		AccountBalance that = (AccountBalance) o;

		if (callTime != null ? !callTime.equals(that.callTime) : that.callTime != null) return false;
		if (assetClass != null ? !assetClass.equals(that.assetClass) : that.assetClass != null) return false;
		return balance != null ? balance.equals(that.balance) : that.balance == null;
	}

	@Override
	public int hashCode() {
		int result = callTime != null ? callTime.hashCode() : 0;
		result = 31 * result + (assetClass != null ? assetClass.hashCode() : 0);
		result = 31 * result + (balance != null ? balance.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "AccountBalance{" +
				   "callTime=" + callTime +
				   ", assetClass='" + assetClass + '\'' +
				   ", balance=" + balance +
				   '}';
	}


	public Long getCallTime() {
		return callTime;
	}
	public void setCallTime(Long callTime) {
		this.callTime = callTime;
	}
	public String getAssetClass() {
		return assetClass;
	}
	public void setAssetClass(String assetClass) {
		this.assetClass = assetClass;
	}
	public Double getBalance() {
		return balance;
	}
	public void setBalance(Double balance) {
		this.balance = balance;
	}
}
