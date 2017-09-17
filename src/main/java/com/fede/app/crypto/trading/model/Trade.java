package com.fede.app.crypto.trading.model;

import com.fede.app.crypto.trading.types.ActionType;
import com.fede.app.crypto.trading.types.OrderType;

/**
 * Created by f.barbano on 13/09/2017.
 */
public class Trade implements Comparable<Trade> {
	
	private String pairName;
	private double price;
	private double volume;
	private long time;
	private ActionType actionType;
	private OrderType orderType;
	private String miscellaneous;

	public String getPairName() {
		return pairName;
	}
	public void setPairName(String pairName) {
		this.pairName = pairName;
	}
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
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	public ActionType getActionType() {
		return actionType;
	}
	public void setActionType(ActionType actionType) {
		this.actionType = actionType;
	}
	public OrderType getOrderType() {
		return orderType;
	}
	public void setOrderType(OrderType orderType) {
		this.orderType = orderType;
	}
	public String getMiscellaneous() {
		return miscellaneous;
	}
	public void setMiscellaneous(String miscellaneous) {
		this.miscellaneous = miscellaneous;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Trade)) return false;

		Trade trade = (Trade) o;

		if (Double.compare(trade.price, price) != 0) return false;
		if (Double.compare(trade.volume, volume) != 0) return false;
		if (time != trade.time) return false;
		if (pairName != null ? !pairName.equals(trade.pairName) : trade.pairName != null) return false;
		if (actionType != trade.actionType) return false;
		if (orderType != trade.orderType) return false;
		return miscellaneous != null ? miscellaneous.equals(trade.miscellaneous) : trade.miscellaneous == null;
	}

	@Override
	public int hashCode() {
		int result;
		long temp;
		result = pairName != null ? pairName.hashCode() : 0;
		temp = Double.doubleToLongBits(price);
		result = 31 * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(volume);
		result = 31 * result + (int) (temp ^ (temp >>> 32));
		result = 31 * result + (int) (time ^ (time >>> 32));
		result = 31 * result + (actionType != null ? actionType.hashCode() : 0);
		result = 31 * result + (orderType != null ? orderType.hashCode() : 0);
		result = 31 * result + (miscellaneous != null ? miscellaneous.hashCode() : 0);
		return result;
	}

	@Override
	public int compareTo(Trade o) {
		if(this.equals(o))	return 0;

		int res = pairName.compareTo(o.pairName);
		return res != 0 ? res : (int)(time - o.time);
	}
}
