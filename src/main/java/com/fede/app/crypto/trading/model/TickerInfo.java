package com.fede.app.crypto.trading.model;

import java.util.List;

/**
 * Created by f.barbano on 13/09/2017.
 */
public class TickerInfo {

	private TickerPrice ask;
	private TickerPrice bid;
	private TickerPrice lastTradeClosed;
	private TickerVolume volume;
	private TickerVolume weightedAverageVolume;
	private TickerVolume tradesNumber;
	private TickerVolume low;
	private TickerVolume high;
	private double todayOpeningPrice;

	public TickerPrice getAsk() {
		return ask;
	}
	public void setAsk(TickerPrice ask) {
		this.ask = ask;
	}
	public TickerPrice getBid() {
		return bid;
	}
	public void setBid(TickerPrice bid) {
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

	public class TickerPrice {

		private double price;
		private int wholeLotVolume;		// not used for "lastTradeClosed"
		private double lotVolume;

		public TickerPrice(double price, int wholeLotVolume, double lotVolume) {
			this.price = price;
			this.wholeLotVolume = wholeLotVolume;
			this.lotVolume = lotVolume;
		}

		public double getPrice() {
			return price;
		}
		public void setPrice(double price) {
			this.price = price;
		}
		public int getWholeLotVolume() {
			return wholeLotVolume;
		}
		public void setWholeLotVolume(int wholeLotVolume) {
			this.wholeLotVolume = wholeLotVolume;
		}
		public double getLotVolume() {
			return lotVolume;
		}
		public void setLotVolume(double lotVolume) {
			this.lotVolume = lotVolume;
		}
	}

	public class TickerVolume {
		
		private double today;
		private double last24Hours;

		public TickerVolume(double today, double last24Hours) {
			this.today = today;
			this.last24Hours = last24Hours;
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
