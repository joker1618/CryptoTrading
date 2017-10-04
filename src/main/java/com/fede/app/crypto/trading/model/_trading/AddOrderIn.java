package com.fede.app.crypto.trading.model._trading;

import com.fede.app.crypto.trading.model.types.OrderAction;
import com.fede.app.crypto.trading.model.types.OrderFlag;
import com.fede.app.crypto.trading.model.types.OrderType;

import java.util.List;

/**
 * Created by f.barbano on 28/09/2017.
 */
public class AddOrderIn {

 // pair = asset pair
	private String pairName;
 // type = type of order (buy/sell)
	private OrderAction orderAction;
 // ordertype = order type:
	private OrderType orderType;
 // price = price (optional. dependent upon ordertype)
	private Double price;
 // price2 = secondary price (optional. dependent upon ordertype)
	private Double price2;
 // volume = order volume in lots
	private Double volume;
 // leverage = amount of leverage desired (optional. default = none)
	private Integer leverage;
 // oflags = comma delimited list of order flags (optional):
	private List<OrderFlag> oflags;
 // starttm = scheduled start time (optional):
 // 	0 = now (default)
 // 	+<n> = schedule start time <n> seconds from now
 // 	<n> = unix timestamp of start time
	private String starttm;
 // expiretm = expiration time (optional):
 // 	0 = no expiration (default)
 // 	+<n> = expire <n> seconds from now
 // 	<n> = unix timestamp of expiration time
	private String expiretm;
 // userref = user reference id. 32-bit signed number. (optional)
	private String userRef;
 // validate = validate inputs only. do not submit order (optional)
	private boolean validate;
	// REVIEW follow field not managed
 // optional closing order to add to system when order gets filled:
 // 	close[ordertype] = order type
 // 	close[price] = price
 // 	close[price2] = secondary price


	public AddOrderIn(String pairName, OrderAction orderAction, OrderType orderType, Double volume) {
		this(pairName, orderAction, orderType ,volume, null);
	}

	public AddOrderIn(String pairName, OrderAction orderAction, OrderType orderType, Double price, Double volume) {
		this.pairName = pairName;
		this.orderAction = orderAction;
		this.orderType = orderType;
		this.price = price;
		this.volume = volume;
	}

	public String getPairName() {
		return pairName;
	}
	public void setPairName(String pairName) {
		this.pairName = pairName;
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
	public Double getPrice2() {
		return price2;
	}
	public void setPrice2(Double price2) {
		this.price2 = price2;
	}
	public Double getVolume() {
		return volume;
	}
	public void setVolume(Double volume) {
		this.volume = volume;
	}
	public Integer getLeverage() {
		return leverage;
	}
	public void setLeverage(Integer leverage) {
		this.leverage = leverage;
	}
	public List<OrderFlag> getOflags() {
		return oflags;
	}
	public void setOflags(List<OrderFlag> oflags) {
		this.oflags = oflags;
	}
	public String getStarttm() {
		return starttm;
	}
	public void setStarttm(String starttm) {
		this.starttm = starttm;
	}
	public String getExpiretm() {
		return expiretm;
	}
	public void setExpiretm(String expiretm) {
		this.expiretm = expiretm;
	}
	public String getUserRef() {
		return userRef;
	}
	public void setUserRef(String userRef) {
		this.userRef = userRef;
	}
	public boolean isValidate() {
		return validate;
	}
	public void setValidate(boolean validate) {
		this.validate = validate;
	}
}
