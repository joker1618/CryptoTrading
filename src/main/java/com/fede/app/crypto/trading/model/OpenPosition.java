package com.fede.app.crypto.trading.model;

import com.fede.app.crypto.trading.model.types.OrderAction;
import com.fede.app.crypto.trading.model.types.OrderFlag;
import com.fede.app.crypto.trading.model.types.OrderType;

import java.util.List;

/**
 * Created by f.barbano on 25/09/2017.
 */
public class OpenPosition {

	// <position_txid> = open position info
	private String positionTxID;
	// ordertxid = order responsible for execution of trade
	private String orderTxID;
	// pair = asset pair
	private String pairName;
	// time = unix timestamp of trade
	private Long time;
	// type = type of order used to open position (buy/sell)
	private OrderAction orderAction;
	// ordertype = order type used to open position
	private OrderType orderType;
	// cost = opening cost of position (quote currency unless viqc set in oflags)
	private Double cost;
	// fee = opening fee of position (quote currency)
	private Double fee;
	// vol = position volume (base currency unless viqc set in oflags)
	private Double vol;
	// vol_closed = position volume closed (base currency unless viqc set in oflags)
	private Double volClosed;
	// margin = initial margin (quote currency)
	private Double margin;
	// value = current value of remaining position (if docalcs requested. quote currency)
	private Double value;
	// net = unrealized profit/loss of remaining position (if docalcs requested. quote currency, quote currency misc = comma delimited list of miscellaneous info
	private Double net;
	// oflags = comma delimited list of order flags
	private List<OrderFlag> oflags;


	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof OpenPosition)) return false;

		OpenPosition that = (OpenPosition) o;

		if (positionTxID != null ? !positionTxID.equals(that.positionTxID) : that.positionTxID != null) return false;
		if (orderTxID != null ? !orderTxID.equals(that.orderTxID) : that.orderTxID != null) return false;
		if (pairName != null ? !pairName.equals(that.pairName) : that.pairName != null) return false;
		if (time != null ? !time.equals(that.time) : that.time != null) return false;
		if (orderAction != that.orderAction) return false;
		if (orderType != that.orderType) return false;
		if (cost != null ? !cost.equals(that.cost) : that.cost != null) return false;
		if (fee != null ? !fee.equals(that.fee) : that.fee != null) return false;
		if (vol != null ? !vol.equals(that.vol) : that.vol != null) return false;
		if (volClosed != null ? !volClosed.equals(that.volClosed) : that.volClosed != null) return false;
		if (margin != null ? !margin.equals(that.margin) : that.margin != null) return false;
		if (value != null ? !value.equals(that.value) : that.value != null) return false;
		if (net != null ? !net.equals(that.net) : that.net != null) return false;
		return oflags != null ? oflags.equals(that.oflags) : that.oflags == null;
	}

	@Override
	public int hashCode() {
		int result = positionTxID != null ? positionTxID.hashCode() : 0;
		result = 31 * result + (orderTxID != null ? orderTxID.hashCode() : 0);
		result = 31 * result + (pairName != null ? pairName.hashCode() : 0);
		result = 31 * result + (time != null ? time.hashCode() : 0);
		result = 31 * result + (orderAction != null ? orderAction.hashCode() : 0);
		result = 31 * result + (orderType != null ? orderType.hashCode() : 0);
		result = 31 * result + (cost != null ? cost.hashCode() : 0);
		result = 31 * result + (fee != null ? fee.hashCode() : 0);
		result = 31 * result + (vol != null ? vol.hashCode() : 0);
		result = 31 * result + (volClosed != null ? volClosed.hashCode() : 0);
		result = 31 * result + (margin != null ? margin.hashCode() : 0);
		result = 31 * result + (value != null ? value.hashCode() : 0);
		result = 31 * result + (net != null ? net.hashCode() : 0);
		result = 31 * result + (oflags != null ? oflags.hashCode() : 0);
		return result;
	}

	
	public String getPositionTxID() {
		return positionTxID;
	}
	public void setPositionTxID(String positionTxID) {
		this.positionTxID = positionTxID;
	}
	public String getOrderTxID() {
		return orderTxID;
	}
	public void setOrderTxID(String orderTxID) {
		this.orderTxID = orderTxID;
	}
	public String getPairName() {
		return pairName;
	}
	public void setPairName(String pairName) {
		this.pairName = pairName;
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
	public Double getCost() {
		return cost;
	}
	public void setCost(Double cost) {
		this.cost = cost;
	}
	public Double getFee() {
		return fee;
	}
	public void setFee(Double fee) {
		this.fee = fee;
	}
	public Double getVol() {
		return vol;
	}
	public void setVol(Double vol) {
		this.vol = vol;
	}
	public Double getVolClosed() {
		return volClosed;
	}
	public void setVolClosed(Double volClosed) {
		this.volClosed = volClosed;
	}
	public Double getMargin() {
		return margin;
	}
	public void setMargin(Double margin) {
		this.margin = margin;
	}
	public Double getValue() {
		return value;
	}
	public void setValue(Double value) {
		this.value = value;
	}
	public Double getNet() {
		return net;
	}
	public void setNet(Double net) {
		this.net = net;
	}
	public List<OrderFlag> getOflags() {
		return oflags;
	}
	public void setOflags(List<OrderFlag> oflags) {
		this.oflags = oflags;
	}
}
