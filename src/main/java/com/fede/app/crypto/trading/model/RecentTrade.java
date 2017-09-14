package com.fede.app.crypto.trading.model;

import com.fede.app.crypto.trading.types.ActionType;
import com.fede.app.crypto.trading.types.OrderType;

import java.util.List;

/**
 * Created by f.barbano on 13/09/2017.
 */
public class RecentTrade {
	
	private String pairName;
	private List<TradeInfo> tradeInfoList;
	private long last;		// id to be used as since when polling for new trade data

	public String getPairName() {
		return pairName;
	}
	public void setPairName(String pairName) {
		this.pairName = pairName;
	}
	public List<TradeInfo> getTradeInfoList() {
		return tradeInfoList;
	}
	public void setTradeInfoList(List<TradeInfo> tradeInfoList) {
		this.tradeInfoList = tradeInfoList;
	}
	public long getLast() {
		return last;
	}
	public void setLast(long last) {
		this.last = last;
	}

	public class TradeInfo {
		private double price;
		private double volume;
		private double time;
		private ActionType buySell;
		private OrderType marketLimit;
		private String miscellaneous;

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
		public double getTime() {
			return time;
		}
		public void setTime(double time) {
			this.time = time;
		}
		public ActionType getBuySell() {
			return buySell;
		}
		public void setBuySell(ActionType buySell) {
			this.buySell = buySell;
		}
		public OrderType getMarketLimit() {
			return marketLimit;
		}
		public void setMarketLimit(OrderType marketLimit) {
			this.marketLimit = marketLimit;
		}
		public String getMiscellaneous() {
			return miscellaneous;
		}
		public void setMiscellaneous(String miscellaneous) {
			this.miscellaneous = miscellaneous;
		}
	}

	
}
