package com.fede.app.crypto.trading.model._public;

/**
 * Created by f.barbano on 13/09/2017.
 */
public class Ticker  {

	private Long callTime;	// not server timestamp, but system timestamp just before call
	private String pairName;
	private TickerWholePrice ask;
	private TickerWholePrice bid;
	private TickerPrice lastTradeClosed;
	private TickerVolume volume;
	private TickerVolume weightedAverageVolume;
	private TickerVolume tradesNumber;
	private TickerVolume low;
	private TickerVolume high;
	private Double todayOpeningPrice;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Ticker)) return false;

		Ticker ticker = (Ticker) o;

		if (callTime != null ? !callTime.equals(ticker.callTime) : ticker.callTime != null) return false;
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
		if (high != null ? !high.equals(ticker.high) : ticker.high != null) return false;
		return todayOpeningPrice != null ? todayOpeningPrice.equals(ticker.todayOpeningPrice) : ticker.todayOpeningPrice == null;
	}

	@Override
	public int hashCode() {
		int result = callTime != null ? callTime.hashCode() : 0;
		result = 31 * result + (pairName != null ? pairName.hashCode() : 0);
		result = 31 * result + (ask != null ? ask.hashCode() : 0);
		result = 31 * result + (bid != null ? bid.hashCode() : 0);
		result = 31 * result + (lastTradeClosed != null ? lastTradeClosed.hashCode() : 0);
		result = 31 * result + (volume != null ? volume.hashCode() : 0);
		result = 31 * result + (weightedAverageVolume != null ? weightedAverageVolume.hashCode() : 0);
		result = 31 * result + (tradesNumber != null ? tradesNumber.hashCode() : 0);
		result = 31 * result + (low != null ? low.hashCode() : 0);
		result = 31 * result + (high != null ? high.hashCode() : 0);
		result = 31 * result + (todayOpeningPrice != null ? todayOpeningPrice.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return pairName;
	}


	public Long getCallTime() {
		return callTime;
	}
	public void setCallTime(Long callTime) {
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
	public Double getTodayOpeningPrice() {
		return todayOpeningPrice;
	}
	public void setTodayOpeningPrice(Double todayOpeningPrice) {
		this.todayOpeningPrice = todayOpeningPrice;
	}

	public static class TickerPrice {
		protected Double price;
		protected Double lotVolume;

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (!(o instanceof TickerPrice)) return false;

			TickerPrice that = (TickerPrice) o;

			if (price != null ? !price.equals(that.price) : that.price != null) return false;
			return lotVolume != null ? lotVolume.equals(that.lotVolume) : that.lotVolume == null;
		}

		@Override
		public int hashCode() {
			int result = price != null ? price.hashCode() : 0;
			result = 31 * result + (lotVolume != null ? lotVolume.hashCode() : 0);
			return result;
		}

		@Override
		public String toString() {
			return "TickerPrice{" +
					   "price=" + price +
					   ", lotVolume=" + lotVolume +
					   '}';
		}

		public Double getPrice() {
			return price;
		} 
		public void setPrice(Double price) {
			this.price = price;
		} 
		public Double getLotVolume() {
			return lotVolume;
		} 
		public void setLotVolume(Double lotVolume) {
			this.lotVolume = lotVolume;
		}
	}

	public static class TickerWholePrice extends TickerPrice {
		protected Integer wholeLotVolume;

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (!(o instanceof TickerWholePrice)) return false;
			if (!super.equals(o)) return false;

			TickerWholePrice that = (TickerWholePrice) o;

			return wholeLotVolume != null ? wholeLotVolume.equals(that.wholeLotVolume) : that.wholeLotVolume == null;
		}

		@Override
		public int hashCode() {
			int result = super.hashCode();
			result = 31 * result + (wholeLotVolume != null ? wholeLotVolume.hashCode() : 0);
			return result;
		}

		@Override
		public String toString() {
			return "TickerWholePrice{" +
					   "price=" + price +
					   ", lotVolume=" + lotVolume +
					   ", wholeLotVolume=" + wholeLotVolume +
					   '}';
		}

		public Integer getWholeLotVolume() {
			return wholeLotVolume;
		}
		public void setWholeLotVolume(Integer wholeLotVolume) {
			this.wholeLotVolume = wholeLotVolume;
		}
	}

	public static class TickerVolume {
		private Double today;
		private Double last24Hours;

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (!(o instanceof TickerVolume)) return false;

			TickerVolume that = (TickerVolume) o;

			if (today != null ? !today.equals(that.today) : that.today != null) return false;
			return last24Hours != null ? last24Hours.equals(that.last24Hours) : that.last24Hours == null;
		}

		@Override
		public int hashCode() {
			int result = today != null ? today.hashCode() : 0;
			result = 31 * result + (last24Hours != null ? last24Hours.hashCode() : 0);
			return result;
		}

		@Override
		public String toString() {
			return "TickerVolume{" +
					   "today=" + today +
					   ", last24Hours=" + last24Hours +
					   '}';
		}

		public Double getToday() {
			return today;
		} 
		public void setToday(Double today) {
			this.today = today;
		} 
		public Double getLast24Hours() {
			return last24Hours;
		} 
		public void setLast24Hours(Double last24Hours) {
			this.last24Hours = last24Hours;
		}
	}

}
