package com.fede.app.crypto.trading.model;

import com.fede.app.crypto.trading.model.types.OrderAction;
import com.fede.app.crypto.trading.model.types.OrderType;

/**
 * Created by f.barbano on 13/09/2017.
 */
public class RecentTrade {
	
	private String pairName;
	private Double price;
	private Double volume;
	private Long time;
	private OrderAction orderAction;
	private OrderType orderType;
	private String miscellaneous;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof RecentTrade)) return false;

		RecentTrade recentTrade = (RecentTrade) o;

		if (pairName != null ? !pairName.equals(recentTrade.pairName) : recentTrade.pairName != null) return false;
		if (price != null ? !price.equals(recentTrade.price) : recentTrade.price != null) return false;
		if (volume != null ? !volume.equals(recentTrade.volume) : recentTrade.volume != null) return false;
		if (time != null ? !time.equals(recentTrade.time) : recentTrade.time != null) return false;
		if (orderAction != recentTrade.orderAction) return false;
		if (orderType != recentTrade.orderType) return false;
		return miscellaneous != null ? miscellaneous.equals(recentTrade.miscellaneous) : recentTrade.miscellaneous == null;
	}

	@Override
	public int hashCode() {
		int result = pairName != null ? pairName.hashCode() : 0;
		result = 31 * result + (price != null ? price.hashCode() : 0);
		result = 31 * result + (volume != null ? volume.hashCode() : 0);
		result = 31 * result + (time != null ? time.hashCode() : 0);
		result = 31 * result + (orderAction != null ? orderAction.hashCode() : 0);
		result = 31 * result + (orderType != null ? orderType.hashCode() : 0);
		result = 31 * result + (miscellaneous != null ? miscellaneous.hashCode() : 0);
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
	public Double getPrice() {
		return price;
	} 
	public void setPrice(Double price) {
		this.price = price;
	} 
	public Double getVolume() {
		return volume;
	} 
	public void setVolume(Double volume) {
		this.volume = volume;
	} 
	public Long getTime() {
		return time;
	} 
	public void setTime(Long time) {
		this.time = time;
	} 
	public OrderAction getOrderAction() {
		return orderAction;
	} 
	public void setOrderAction(OrderAction orderAction) {
		this.orderAction = orderAction;
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
}
