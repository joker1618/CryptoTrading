package com.fede.app.crypto.trading.model;

import com.fede.app.crypto.trading.types.OrderDirection;

/**
 * Created by f.barbano on 13/09/2017.
 */
public class Order {

	private String pairName;
	private OrderDirection orderDirection;
	private double price;
	private double volume;
	private long timestamp;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Order)) return false;

		Order order = (Order) o;

		if (Double.compare(order.price, price) != 0) return false;
		if (Double.compare(order.volume, volume) != 0) return false;
		if (timestamp != order.timestamp) return false;
		if (pairName != null ? !pairName.equals(order.pairName) : order.pairName != null) return false;
		return orderDirection == order.orderDirection;
	}

	@Override
	public int hashCode() {
		int result;
		long temp;
		result = pairName != null ? pairName.hashCode() : 0;
		result = 31 * result + (orderDirection != null ? orderDirection.hashCode() : 0);
		temp = Double.doubleToLongBits(price);
		result = 31 * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(volume);
		result = 31 * result + (int) (temp ^ (temp >>> 32));
		result = 31 * result + (int) (timestamp ^ (timestamp >>> 32));
		return result;
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
