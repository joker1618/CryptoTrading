package com.fede.app.crypto.trading.model;

import com.fede.app.crypto.trading.model.types.OrderDirection;

/**
 * Created by f.barbano on 13/09/2017.
 *
 * Object model for 'Get order book' call
 */
public class MarketOrder {

	private String pairName;
	private OrderDirection orderDirection;
	private Double price;
	private Double volume;
	private Long timestamp;


	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof MarketOrder)) return false;

		MarketOrder that = (MarketOrder) o;

		if (pairName != null ? !pairName.equals(that.pairName) : that.pairName != null) return false;
		if (orderDirection != that.orderDirection) return false;
		if (price != null ? !price.equals(that.price) : that.price != null) return false;
		if (volume != null ? !volume.equals(that.volume) : that.volume != null) return false;
		return timestamp != null ? timestamp.equals(that.timestamp) : that.timestamp == null;
	}

	@Override
	public int hashCode() {
		int result = pairName != null ? pairName.hashCode() : 0;
		result = 31 * result + (orderDirection != null ? orderDirection.hashCode() : 0);
		result = 31 * result + (price != null ? price.hashCode() : 0);
		result = 31 * result + (volume != null ? volume.hashCode() : 0);
		result = 31 * result + (timestamp != null ? timestamp.hashCode() : 0);
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
	public OrderDirection getOrderDirection() {
		return orderDirection;
	}
	public void setOrderDirection(OrderDirection orderDirection) {
		this.orderDirection = orderDirection;
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
	public Long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}
}
