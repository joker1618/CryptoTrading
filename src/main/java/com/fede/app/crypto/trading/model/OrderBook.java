package com.fede.app.crypto.trading.model;

import java.util.List;

/**
 * Created by f.barbano on 13/09/2017.
 */
public class OrderBook {

	private String pairName;
	private List<Order> askList;
	private List<Order> bidList;

	public String getPairName() {
		return pairName;
	}
	public void setPairName(String pairName) {
		this.pairName = pairName;
	}
	public List<Order> getAskList() {
		return askList;
	}
	public void setAskList(List<Order> askList) {
		this.askList = askList;
	}
	public List<Order> getBidList() {
		return bidList;
	}
	public void setBidList(List<Order> bidList) {
		this.bidList = bidList;
	}

	public class Order {
		private double price;
		private double volume;
		private long timestamp;

		public double getPrice() {
			return price;
		}
		public void setPrice(double price) {
			this.price = price;
		}
		public double getVolume() {
			return volume;
		}
		public void setVolume(double volume) {
			this.volume = volume;
		}
		public long getTimestamp() {
			return timestamp;
		}
		public void setTimestamp(long timestamp) {
			this.timestamp = timestamp;
		}
	}
}
