package com.fede.app.crypto.trading.model;

import java.util.List;

/**
 * Created by f.barbano on 13/09/2017.
 */
public class TradableAssetPair {

	private String altname;
	private String aclassBase;
	private String base;
	private String aclassQuote;
	private String quote;
	private String lot;
	private String pairDecimals;
	private String lotDecimals;
	private String lotMultiplier;
	private List<Integer> leverageBuy;     	// es. "leverage_buy":[2,3]
	private List<Integer> leverageSell;		// es. "leverage_sell":[2,3]
	private List<FeeSchedule> fees;       	// es. "fees":[[0,0.26],[50000,0.24],[100000,0.22],...]
	private List<FeeSchedule> feesMaker;   	// es. "fees_maker":[[0,0.16],[50000,0.14],[100000,0.12],...]
	private String feeVolumeCurrency;
	private String marginCall;
	private String marginStop;

	public String getAltname() {
		return altname;
	}
	public void setAltname(String altname) {
		this.altname = altname;
	}
	public String getAclassBase() {
		return aclassBase;
	}
	public void setAclassBase(String aclassBase) {
		this.aclassBase = aclassBase;
	}
	public String getBase() {
		return base;
	}
	public void setBase(String base) {
		this.base = base;
	}
	public String getAclassQuote() {
		return aclassQuote;
	}
	public void setAclassQuote(String aclassQuote) {
		this.aclassQuote = aclassQuote;
	}
	public String getQuote() {
		return quote;
	}
	public void setQuote(String quote) {
		this.quote = quote;
	}
	public String getLot() {
		return lot;
	}
	public void setLot(String lot) {
		this.lot = lot;
	}
	public String getPairDecimals() {
		return pairDecimals;
	}
	public void setPairDecimals(String pairDecimals) {
		this.pairDecimals = pairDecimals;
	}
	public String getLotDecimals() {
		return lotDecimals;
	}
	public void setLotDecimals(String lotDecimals) {
		this.lotDecimals = lotDecimals;
	}
	public String getLotMultiplier() {
		return lotMultiplier;
	}
	public void setLotMultiplier(String lotMultiplier) {
		this.lotMultiplier = lotMultiplier;
	}
	public List<Integer> getLeverageBuy() {
		return leverageBuy;
	}
	public void setLeverageBuy(List<Integer> leverageBuy) {
		this.leverageBuy = leverageBuy;
	}
	public List<Integer> getLeverageSell() {
		return leverageSell;
	}
	public void setLeverageSell(List<Integer> leverageSell) {
		this.leverageSell = leverageSell;
	}
	public List<FeeSchedule> getFees() {
		return fees;
	}
	public void setFees(List<FeeSchedule> fees) {
		this.fees = fees;
	}
	public List<FeeSchedule> getFeesMaker() {
		return feesMaker;
	}
	public void setFeesMaker(List<FeeSchedule> feesMaker) {
		this.feesMaker = feesMaker;
	}
	public String getFeeVolumeCurrency() {
		return feeVolumeCurrency;
	}
	public void setFeeVolumeCurrency(String feeVolumeCurrency) {
		this.feeVolumeCurrency = feeVolumeCurrency;
	}
	public String getMarginCall() {
		return marginCall;
	}
	public void setMarginCall(String marginCall) {
		this.marginCall = marginCall;
	}
	public String getMarginStop() {
		return marginStop;
	}
	public void setMarginStop(String marginStop) {
		this.marginStop = marginStop;
	}

	public class FeeSchedule {

		private int volume;
		private double percentFee;

		public FeeSchedule(int volume, double percentFee) {
			this.volume = volume;
			this.percentFee = percentFee;
		}

		public int getVolume() {
			return volume;
		}
		public void setVolume(int volume) {
			this.volume = volume;
		}
		public double getPercentFee() {
			return percentFee;
		}
		public void setPercentFee(double percentFee) {
			this.percentFee = percentFee;
		}
	}
}
