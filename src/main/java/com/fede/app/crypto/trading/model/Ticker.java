package com.fede.app.crypto.trading.model;

/**
 * Created by f.barbano on 13/09/2017.
 */
public class Ticker  {

	private long callTime;	// not server timestamp, but system timestamp just before call
	private String pairName;
	private TickerWholePrice ask;
	private TickerWholePrice bid;
	private TickerPrice lastTradeClosed;
	private TickerVolume volume;
	private TickerVolume weightedAverageVolume;
	private TickerVolume tradesNumber;
	private TickerVolume low;
	private TickerVolume high;
	private double todayOpeningPrice;


	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Ticker)) return false;

		Ticker ticker = (Ticker) o;

		if (callTime != ticker.callTime) return false;
		if (Double.compare(ticker.todayOpeningPrice, todayOpeningPrice) != 0) return false;
		if (pairName != null ? !pairName.equals(ticker.pairName) : ticker.pairName != null) return false;
		if (ask != null ? !ask.equals(ticker.ask) : ticker.ask != null) return false;
		if (bid != null ? !bid.equals(ticker.bid) : ticker.bid != null) return false;
		if (lastTradeClosed != null ? !lastTradeClosed.equals(ticker.lastTradeClosed) : ticker.lastTradeClosed != null)
			return false;
		if (volume != null ? !volume.equals(ticker.volume) : ticker.volume != null) return false;
		if (weightedAverageVolume != null ? !weightedAverageVolume.equals(ticker.weightedAverageVolume) : ticker.weightedAverageVolume != null)
			return false;
		if (tradesNumber != null ? !tradesNumber.equals(ticker.tradesNumber) : ticker.tradesNumber != null)
			return false;
		if (low != null ? !low.equals(ticker.low) : ticker.low != null) return false;
		return high != null ? high.equals(ticker.high) : ticker.high == null;
	}

	@Override
	public int hashCode() {
		int result;
		long temp;
		result = (int) (callTime ^ (callTime >>> 32));
		result = 31 * result + (pairName != null ? pairName.hashCode() : 0);
		result = 31 * result + (ask != null ? ask.hashCode() : 0);
		result = 31 * result + (bid != null ? bid.hashCode() : 0);
		result = 31 * result + (lastTradeClosed != null ? lastTradeClosed.hashCode() : 0);
		result = 31 * result + (volume != null ? volume.hashCode() : 0);
		result = 31 * result + (weightedAverageVolume != null ? weightedAverageVolume.hashCode() : 0);
		result = 31 * result + (tradesNumber != null ? tradesNumber.hashCode() : 0);
		result = 31 * result + (low != null ? low.hashCode() : 0);
		result = 31 * result + (high != null ? high.hashCode() : 0);
		temp = Double.doubleToLongBits(todayOpeningPrice);
		result = 31 * result + (int) (temp ^ (temp >>> 32));
		return result;
	}


	public long getCallTime() {
		return callTime;
	}
	public void setCallTime(long callTime) {
		this.callTime = callTime;
	}
	public String getPairName() {
		return pairName;
	}
	public void setPairName(String pairName) {
		this.pairName = pairName;
	}
	public TickerWholePrice getAsk() {
		return ask;
	}
	public void setAsk(TickerWholePrice ask) {
		this.ask = ask;
	}
	public TickerWholePrice getBid() {
		return bid;
	}
	public void setBid(TickerWholePrice bid) {
		this.bid = bid;
	}
	public TickerPrice getLastTradeClosed() {
		return lastTradeClosed;
	}
	public void setLastTradeClosed(TickerPrice lastTradeClosed) {
		this.lastTradeClosed = lastTradeClosed;
	}
	public TickerVolume getVolume() {
		return volume;
	}
	public void setVolume(TickerVolume volume) {
		this.volume = volume;
	}
	public TickerVolume getWeightedAverageVolume() {
		return weightedAverageVolume;
	}
	public void setWeightedAverageVolume(TickerVolume weightedAverageVolume) {
		this.weightedAverageVolume = weightedAverageVolume;
	}
	public TickerVolume getTradesNumber() {
		return tradesNumber;
	}
	public void setTradesNumber(TickerVolume tradesNumber) {
		this.tradesNumber = tradesNumber;
	}
	public TickerVolume getLow() {
		return low;
	}
	public void setLow(TickerVolume low) {
		this.low = low;
	}
	public TickerVolume getHigh() {
		return high;
	}
	public void setHigh(TickerVolume high) {
		this.high = high;
	}
	public double getTodayOpeningPrice() {
		return todayOpeningPrice;
	}
	public void setTodayOpeningPrice(double todayOpeningPrice) {
		this.todayOpeningPrice = todayOpeningPrice;
	}

	public static class TickerPrice {
		protected double price;
		protected double lotVolume;

		public TickerPrice() {

		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (!(o instanceof TickerPrice)) return false;

			TickerPrice that = (TickerPrice) o;

			if (Double.compare(that.price, price) != 0) return false;
			return Double.compare(that.lotVolume, lotVolume) == 0;
		}

		@Override
		public int hashCode() {
			int result;
			long temp;
			temp = Double.doubleToLongBits(price);
			result = (int) (temp ^ (temp >>> 32));
			temp = Double.doubleToLongBits(lotVolume);
			result = 31 * result + (int) (temp ^ (temp >>> 32));
			return result;
		}

		public double getPrice() {
			return price;
		}
		public void setPrice(double price) {
			this.price = price;
		}
		public double getLotVolume() {
			return lotVolume;
		}
		public void setLotVolume(double lotVolume) {
			this.lotVolume = lotVolume;
		}
	}

	public static class TickerWholePrice extends TickerPrice {
		protected int wholeLotVolume;

		public TickerWholePrice() {
			super();
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (!(o instanceof TickerWholePrice)) return false;
			if (!super.equals(o)) return false;

			TickerWholePrice that = (TickerWholePrice) o;

			return wholeLotVolume == that.wholeLotVolume;
		}

		@Override
		public int hashCode() {
			int result = super.hashCode();
			result = 31 * result + wholeLotVolume;
			return result;
		}

		public int getWholeLotVolume() {
			return wholeLotVolume;
		}
		public void setWholeLotVolume(int wholeLotVolume) {
			this.wholeLotVolume = wholeLotVolume;
		}
	}

	public static class TickerVolume {
		
		private double today;
		private double last24Hours;

		public TickerVolume() {

		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (!(o instanceof TickerVolume)) return false;

			TickerVolume that = (TickerVolume) o;

			if (Double.compare(that.today, today) != 0) return false;
			return Double.compare(that.last24Hours, last24Hours) == 0;
		}

		@Override
		public int hashCode() {
			int result;
			long temp;
			temp = Double.doubleToLongBits(today);
			result = (int) (temp ^ (temp >>> 32));
			temp = Double.doubleToLongBits(last24Hours);
			result = 31 * result + (int) (temp ^ (temp >>> 32));
			return result;
		}

		public double getToday() {
			return today;
		}
		public void setToday(double today) {
			this.today = today;
		}
		public double getLast24Hours() {
			return last24Hours;
		}
		public void setLast24Hours(double last24Hours) {
			this.last24Hours = last24Hours;
		}
	}

}
