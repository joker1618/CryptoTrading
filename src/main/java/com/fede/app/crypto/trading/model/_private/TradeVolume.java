package com.fede.app.crypto.trading.model._private;

import java.util.List;

/**
 * Created by f.barbano on 14/09/2017.
 */
public class TradeVolume {
	                  
	// currency = volume currency
	private String currency;
	// volume = current discount volume
	private Double volume;
	// fees = array of asset pairs and fee tier info (if requested)
	private List<FeeInfo> fees;
	// fees_maker = array of asset pairs and maker fee tier info (if requested) for any pairs on maker/taker schedule
	private List<FeeInfo> feesMaker;


	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof TradeVolume)) return false;

		TradeVolume that = (TradeVolume) o;

		if (currency != null ? !currency.equals(that.currency) : that.currency != null) return false;
		if (volume != null ? !volume.equals(that.volume) : that.volume != null) return false;
		if (fees != null ? !fees.equals(that.fees) : that.fees != null) return false;
		return feesMaker != null ? feesMaker.equals(that.feesMaker) : that.feesMaker == null;
	}

	@Override
	public int hashCode() {
		int result = currency != null ? currency.hashCode() : 0;
		result = 31 * result + (volume != null ? volume.hashCode() : 0);
		result = 31 * result + (fees != null ? fees.hashCode() : 0);
		result = 31 * result + (feesMaker != null ? feesMaker.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return "TradeVolume{" +
				   "currency='" + currency + '\'' +
				   ", volume=" + volume +
				   ", fees=" + fees +
				   ", feesMaker=" + feesMaker +
				   '}';
	}


	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public Double getVolume() {
		return volume;
	}
	public void setVolume(Double volume) {
		this.volume = volume;
	}
	public List<FeeInfo> getFees() {
		return fees;
	}
	public void setFees(List<FeeInfo> fees) {
		this.fees = fees;
	}
	public List<FeeInfo> getFeesMaker() {
		return feesMaker;
	}
	public void setFeesMaker(List<FeeInfo> feesMaker) {
		this.feesMaker = feesMaker;
	}

	public static class FeeInfo {
		private String pairName;
		// fee = current fee in percent
		private Double fee;
		// minfee = minimum fee for pair (if not fixed fee)
		private Double minFee;
		// maxfee = maximum fee for pair (if not fixed fee)
		private Double maxFee;
		// nextfee = next tier's fee for pair (if not fixed fee. nil if at lowest fee tier)
		private Double nextFee;
		// nextvolume = volume level of next tier (if not fixed fee. nil if at lowest fee tier)
		private Double nextVolume;
		// tiervolume = volume level of current tier (if not fixed fee. nil if at lowest fee tier)
		private Double tierVolume;

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (!(o instanceof FeeInfo)) return false;

			FeeInfo feeInfo = (FeeInfo) o;

			if (pairName != null ? !pairName.equals(feeInfo.pairName) : feeInfo.pairName != null) return false;
			if (fee != null ? !fee.equals(feeInfo.fee) : feeInfo.fee != null) return false;
			if (minFee != null ? !minFee.equals(feeInfo.minFee) : feeInfo.minFee != null) return false;
			if (maxFee != null ? !maxFee.equals(feeInfo.maxFee) : feeInfo.maxFee != null) return false;
			if (nextFee != null ? !nextFee.equals(feeInfo.nextFee) : feeInfo.nextFee != null) return false;
			if (nextVolume != null ? !nextVolume.equals(feeInfo.nextVolume) : feeInfo.nextVolume != null) return false;
			return tierVolume != null ? tierVolume.equals(feeInfo.tierVolume) : feeInfo.tierVolume == null;
		}

		@Override
		public int hashCode() {
			int result = pairName != null ? pairName.hashCode() : 0;
			result = 31 * result + (fee != null ? fee.hashCode() : 0);
			result = 31 * result + (minFee != null ? minFee.hashCode() : 0);
			result = 31 * result + (maxFee != null ? maxFee.hashCode() : 0);
			result = 31 * result + (nextFee != null ? nextFee.hashCode() : 0);
			result = 31 * result + (nextVolume != null ? nextVolume.hashCode() : 0);
			result = 31 * result + (tierVolume != null ? tierVolume.hashCode() : 0);
			return result;
		}

		@Override
		public String toString() {
			return "FeeInfo{" +
					   "pairName='" + pairName + '\'' +
					   ", fee=" + fee +
					   ", minFee=" + minFee +
					   ", maxFee=" + maxFee +
					   ", nextFee=" + nextFee +
					   ", nextVolume=" + nextVolume +
					   ", tierVolume=" + tierVolume +
					   '}';
		}

		public String getPairName() {
			return pairName;
		}
		public void setPairName(String pairName) {
			this.pairName = pairName;
		}
		public Double getFee() {
			return fee;
		}
		public void setFee(Double fee) {
			this.fee = fee;
		}
		public Double getMinFee() {
			return minFee;
		}
		public void setMinFee(Double minFee) {
			this.minFee = minFee;
		}
		public Double getMaxFee() {
			return maxFee;
		}
		public void setMaxFee(Double maxFee) {
			this.maxFee = maxFee;
		}
		public Double getNextFee() {
			return nextFee;
		}
		public void setNextFee(Double nextFee) {
			this.nextFee = nextFee;
		}
		public Double getNextVolume() {
			return nextVolume;
		}
		public void setNextVolume(Double nextVolume) {
			this.nextVolume = nextVolume;
		}
		public Double getTierVolume() {
			return tierVolume;
		}
		public void setTierVolume(Double tierVolume) {
			this.tierVolume = tierVolume;
		}
	}

}
