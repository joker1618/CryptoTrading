package com.fede.app.crypto.trading.model;

import com.fede.app.crypto.trading.util.ModelCompare;

import java.util.List;

/**
 * Created by f.barbano on 13/09/2017.
 */
public class AssetPair {

	private String pairName;
	private String altName;
	private String aClassBase;
	private String base;
	private String aClassQuote;
	private String quote;
	private String lot;
	private int pairDecimals;
	private int lotDecimals;
	private int lotMultiplier;
	private List<Integer> leverageBuy;     	// es. "leverage_buy":[2,3]
	private List<Integer> leverageSell;		// es. "leverage_sell":[2,3]
	private List<FeeSchedule> fees;       	// es. "fees":[[0,0.26],[50000,0.24],[100000,0.22],...]
	private List<FeeSchedule> feesMaker;   	// es. "fees_maker":[[0,0.16],[50000,0.14],[100000,0.12],...]
	private String feeVolumeCurrency;
	private int marginCall;
	private int marginStop;


	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof AssetPair)) return false;

		AssetPair that = (AssetPair) o;

		if (pairDecimals != that.pairDecimals) return false;
		if (lotDecimals != that.lotDecimals) return false;
		if (lotMultiplier != that.lotMultiplier) return false;
		if (marginCall != that.marginCall) return false;
		if (marginStop != that.marginStop) return false;
		if (pairName != null ? !pairName.equals(that.pairName) : that.pairName != null) return false;
		if (altName != null ? !altName.equals(that.altName) : that.altName != null) return false;
		if (aClassBase != null ? !aClassBase.equals(that.aClassBase) : that.aClassBase != null) return false;
		if (base != null ? !base.equals(that.base) : that.base != null) return false;
		if (aClassQuote != null ? !aClassQuote.equals(that.aClassQuote) : that.aClassQuote != null) return false;
		if (quote != null ? !quote.equals(that.quote) : that.quote != null) return false;
		if (lot != null ? !lot.equals(that.lot) : that.lot != null) return false;
		if (leverageBuy != null ? !leverageBuy.equals(that.leverageBuy) : that.leverageBuy != null) return false;
		if (leverageSell != null ? !leverageSell.equals(that.leverageSell) : that.leverageSell != null) return false;
		if (fees != null ? !fees.equals(that.fees) : that.fees != null) return false;
		if (feesMaker != null ? !feesMaker.equals(that.feesMaker) : that.feesMaker != null) return false;
		return feeVolumeCurrency != null ? feeVolumeCurrency.equals(that.feeVolumeCurrency) : that.feeVolumeCurrency == null;
	}

	@Override
	public int hashCode() {
		int result = pairName != null ? pairName.hashCode() : 0;
		result = 31 * result + (altName != null ? altName.hashCode() : 0);
		result = 31 * result + (aClassBase != null ? aClassBase.hashCode() : 0);
		result = 31 * result + (base != null ? base.hashCode() : 0);
		result = 31 * result + (aClassQuote != null ? aClassQuote.hashCode() : 0);
		result = 31 * result + (quote != null ? quote.hashCode() : 0);
		result = 31 * result + (lot != null ? lot.hashCode() : 0);
		result = 31 * result + pairDecimals;
		result = 31 * result + lotDecimals;
		result = 31 * result + lotMultiplier;
		result = 31 * result + (leverageBuy != null ? leverageBuy.hashCode() : 0);
		result = 31 * result + (leverageSell != null ? leverageSell.hashCode() : 0);
		result = 31 * result + (fees != null ? fees.hashCode() : 0);
		result = 31 * result + (feesMaker != null ? feesMaker.hashCode() : 0);
		result = 31 * result + (feeVolumeCurrency != null ? feeVolumeCurrency.hashCode() : 0);
		result = 31 * result + marginCall;
		result = 31 * result + marginStop;
		return result;
	}

	@Override
	public String toString() {
		return pairName;
	}


	public String getPairName() {
		return pairName;
	}
	public void setPairName(String pairName) {
		this.pairName = pairName;
	}
	public String getAltName() {
		return altName;
	}
	public void setAltName(String altName) {
		this.altName = altName;
	}
	public String getAClassBase() {
		return aClassBase;
	}
	public void setAClassBase(String aClassBase) {
		this.aClassBase = aClassBase;
	}
	public String getBase() {
		return base;
	}
	public void setBase(String base) {
		this.base = base;
	}
	public String getAClassQuote() {
		return aClassQuote;
	}
	public void setAClassQuote(String aClassQuote) {
		this.aClassQuote = aClassQuote;
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
	public int getPairDecimals() {
		return pairDecimals;
	}
	public void setPairDecimals(int pairDecimals) {
		this.pairDecimals = pairDecimals;
	}
	public int getLotDecimals() {
		return lotDecimals;
	}
	public void setLotDecimals(int lotDecimals) {
		this.lotDecimals = lotDecimals;
	}
	public int getLotMultiplier() {
		return lotMultiplier;
	}
	public void setLotMultiplier(int lotMultiplier) {
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
	public void addFee(FeeSchedule fee) {
		this.fees.add(fee);
	}
	public void setFees(List<FeeSchedule> fees) {
		this.fees = fees;
	}
	public List<FeeSchedule> getFeesMaker() {
		return feesMaker;
	}
	public void addFeeMaker(FeeSchedule feeMaker) {
		this.feesMaker.add(feeMaker);
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
	public int getMarginCall() {
		return marginCall;
	}
	public void setMarginCall(int marginCall) {
		this.marginCall = marginCall;
	}
	public int getMarginStop() {
		return marginStop;
	}
	public void setMarginStop(int marginStop) {
		this.marginStop = marginStop;
	}

	public static class FeeSchedule implements Comparable<FeeSchedule> {
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

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (!(o instanceof FeeSchedule)) return false;

			FeeSchedule that = (FeeSchedule) o;

			if (volume != that.volume) return false;
			return Double.compare(that.percentFee, percentFee) == 0;
		}

		@Override
		public int hashCode() {
			int result;
			long temp;
			result = volume;
			temp = Double.doubleToLongBits(percentFee);
			result = 31 * result + (int) (temp ^ (temp >>> 32));
			return result;
		}

		@Override
		public int compareTo(FeeSchedule o) {
			return volume - o.volume;
		}
	}
}
