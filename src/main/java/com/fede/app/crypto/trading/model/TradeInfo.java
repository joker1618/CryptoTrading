package com.fede.app.crypto.trading.model;

import com.fede.app.crypto.trading.model.types.OrderAction;
import com.fede.app.crypto.trading.model.types.OrderType;
import com.fede.app.crypto.trading.model.types.PositionStatus;

import java.util.List;

/**
 * Created by f.barbano on 14/09/2017.
 */
public class TradeInfo {

	private String tradeID;
//	ordertxid = order responsible for execution of trade
	private String orderTxID;
//	pair = asset pair
	private String pairName;
//	time = unix timestamp of trade
	private Long time;
//	type = type of order (buy/sell)
	private OrderAction orderAction;
//	ordertype = order type
	private OrderType orderType;
//	price = average price order was executed at (quote currency)
	private Double price;
//	cost = total cost of order (quote currency)
	private Double cost;
//	fee = total fee (quote currency)
	private Double fee;
//	vol = volume (base currency)
	private Double vol;
//	margin = initial margin (quote currency)
	private Double margin;
//	misc = comma delimited list of miscellaneous info
	private String misc;

	/* If the trade opened a position, the follow fields are also present in the trade info */
	//	posstatus = position status (open/closed)
	private PositionStatus posStatus;
	//	cprice = average price of closed portion of position (quote currency)
	private Double cprice;
	//	ccost = total cost of closed portion of position (quote currency)
	private Double ccost;
	//	cfee = total fee of closed portion of position (quote currency)
	private Double cfee;
	//	cvol = total fee of closed portion of position (quote currency)
	private Double cvol;
	//	cmargin = total margin freed in closed portion of position (quote currency)
	private Double cmargin;
	//	net = net profit/loss of closed portion of position (quote currency, quote currency scale)
	private Double net;
	//	trades = list of closing trades for position (if available)
	private List<String> trades;


	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof TradeInfo)) return false;

		TradeInfo that = (TradeInfo) o;

		if (tradeID != null ? !tradeID.equals(that.tradeID) : that.tradeID != null) return false;
		if (orderTxID != null ? !orderTxID.equals(that.orderTxID) : that.orderTxID != null) return false;
		if (pairName != null ? !pairName.equals(that.pairName) : that.pairName != null) return false;
		if (time != null ? !time.equals(that.time) : that.time != null) return false;
		if (orderAction != that.orderAction) return false;
		if (orderType != that.orderType) return false;
		if (price != null ? !price.equals(that.price) : that.price != null) return false;
		if (cost != null ? !cost.equals(that.cost) : that.cost != null) return false;
		if (fee != null ? !fee.equals(that.fee) : that.fee != null) return false;
		if (vol != null ? !vol.equals(that.vol) : that.vol != null) return false;
		if (margin != null ? !margin.equals(that.margin) : that.margin != null) return false;
		if (misc != null ? !misc.equals(that.misc) : that.misc != null) return false;
		if (posStatus != that.posStatus) return false;
		if (cprice != null ? !cprice.equals(that.cprice) : that.cprice != null) return false;
		if (ccost != null ? !ccost.equals(that.ccost) : that.ccost != null) return false;
		if (cfee != null ? !cfee.equals(that.cfee) : that.cfee != null) return false;
		if (cvol != null ? !cvol.equals(that.cvol) : that.cvol != null) return false;
		if (cmargin != null ? !cmargin.equals(that.cmargin) : that.cmargin != null) return false;
		if (net != null ? !net.equals(that.net) : that.net != null) return false;
		return trades != null ? trades.equals(that.trades) : that.trades == null;
	}

	@Override
	public int hashCode() {
		int result = tradeID != null ? tradeID.hashCode() : 0;
		result = 31 * result + (orderTxID != null ? orderTxID.hashCode() : 0);
		result = 31 * result + (pairName != null ? pairName.hashCode() : 0);
		result = 31 * result + (time != null ? time.hashCode() : 0);
		result = 31 * result + (orderAction != null ? orderAction.hashCode() : 0);
		result = 31 * result + (orderType != null ? orderType.hashCode() : 0);
		result = 31 * result + (price != null ? price.hashCode() : 0);
		result = 31 * result + (cost != null ? cost.hashCode() : 0);
		result = 31 * result + (fee != null ? fee.hashCode() : 0);
		result = 31 * result + (vol != null ? vol.hashCode() : 0);
		result = 31 * result + (margin != null ? margin.hashCode() : 0);
		result = 31 * result + (misc != null ? misc.hashCode() : 0);
		result = 31 * result + (posStatus != null ? posStatus.hashCode() : 0);
		result = 31 * result + (cprice != null ? cprice.hashCode() : 0);
		result = 31 * result + (ccost != null ? ccost.hashCode() : 0);
		result = 31 * result + (cfee != null ? cfee.hashCode() : 0);
		result = 31 * result + (cvol != null ? cvol.hashCode() : 0);
		result = 31 * result + (cmargin != null ? cmargin.hashCode() : 0);
		result = 31 * result + (net != null ? net.hashCode() : 0);
		result = 31 * result + (trades != null ? trades.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return tradeID;
	}

	
	public String getTradeID() {
		return tradeID;
	} 
	public void setTradeID(String tradeID) {
		this.tradeID = tradeID;
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
	public Double getPrice() {
		return price;
	} 
	public void setPrice(Double price) {
		this.price = price;
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
	public Double getMargin() {
		return margin;
	} 
	public void setMargin(Double margin) {
		this.margin = margin;
	} 
	public String getMisc() {
		return misc;
	} 
	public void setMisc(String misc) {
		this.misc = misc;
	} 
	public PositionStatus getPosStatus() {
		return posStatus;
	} 
	public void setPosStatus(PositionStatus posStatus) {
		this.posStatus = posStatus;
	} 
	public Double getCprice() {
		return cprice;
	} 
	public void setCprice(Double cprice) {
		this.cprice = cprice;
	} 
	public Double getCcost() {
		return ccost;
	} 
	public void setCcost(Double ccost) {
		this.ccost = ccost;
	} 
	public Double getCfee() {
		return cfee;
	} 
	public void setCfee(Double cfee) {
		this.cfee = cfee;
	} 
	public Double getCvol() {
		return cvol;
	} 
	public void setCvol(Double cvol) {
		this.cvol = cvol;
	} 
	public Double getCmargin() {
		return cmargin;
	} 
	public void setCmargin(Double cmargin) {
		this.cmargin = cmargin;
	} 
	public Double getNet() {
		return net;
	} 
	public void setNet(Double net) {
		this.net = net;
	} 
	public List<String> getTrades() {
		return trades;
	} 
	public void setTrades(List<String> trades) {
		this.trades = trades;
	}
}
