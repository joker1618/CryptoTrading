package com.fede.app.crypto.trading.model._trading;

import com.fede.app.crypto.trading.model.types.OrderAction;
import com.fede.app.crypto.trading.model.types.OrderType;

/**
 * Created by f.barbano on 02/11/2017.
 */
public class OrderRequest {

	public static AddOrderIn createBuyLimitOrderIn(String pairName, Double price, Double volume) {
		AddOrderIn request = new AddOrderIn(pairName, OrderAction.BUY, OrderType.LIMIT, volume);
		request.setPrice(price);
		return request;
	}

	public static AddOrderIn createSellLimitOrderIn(String pairName, Double price, Double volume) {
		AddOrderIn request = new AddOrderIn(pairName, OrderAction.SELL, OrderType.LIMIT, volume);
		request.setPrice(price);
		return request;
	}


}
