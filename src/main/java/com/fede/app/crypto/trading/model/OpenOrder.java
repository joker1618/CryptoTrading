package com.fede.app.crypto.trading.model;

import com.fede.app.crypto.trading.types.OrderStatus;

/**
 * Created by f.barbano on 14/09/2017.
 */
public class OpenOrder {
	/*
	refid = Referral order transaction id that created this order
	userref = user reference id
	status = status of order:
		pending = order pending book entry
		open = open order
		closed = closed order
		canceled = order canceled
		expired = order expired
	opentm = unix timestamp of when order was placed
	starttm = unix timestamp of order start time (or 0 if not set)
	expiretm = unix timestamp of order end time (or 0 if not set)
	descr = order description info
	pair = asset pair
	type = type of order (buy/sell)
		ordertype = order type (See Add standard order)
		price = primary price
		price2 = secondary price
		leverage = amount of leverage
		order = order description
		close = conditional close order description (if conditional close set)
	vol = volume of order (base currency unless viqc set in oflags)
	vol_exec = volume executed (base currency unless viqc set in oflags)
	cost = total cost (quote currency unless unless viqc set in oflags)
	fee = total fee (quote currency)
	price = average price (quote currency unless viqc set in oflags)
	stopprice = stop price (quote currency, for trailing stops)
	limitprice = triggered limit price (quote currency, when limit based order type triggered)
	misc = comma delimited list of miscellaneous info
		stopped = triggered by stop price
		touched = triggered by touch price
		liquidated = liquidation
		partial = partial fill
	oflags = comma delimited list of order flags
		viqc = volume in quote currency
		fcib = prefer fee in base currency (default if selling)
		fciq = prefer fee in quote currency (default if buying)
		nompp = no market price protection
	trades = array of trade ids related to order (if trades info requested and data available)
	 */


	private String refId;
	private String userRef;
	private OrderStatus status;

	// TODO
	// {"error":[],"result":{"open":{"OPLWH7-BK5PO-KLKIW6":{"refid":null,"userref":null,"status":"open","opentm":1505415933.7843,"starttm":0,"expiretm":0,"descr":{"pair":"LTCEUR","type":"buy","ordertype":"limit","price":"1.00","price2":"0","leverage":"none","order":"buy 1.00000000 LTCEUR @ limit 1.00"},"vol":"1.00000000","vol_exec":"0.00000000","cost":"0.00000","fee":"0.00000","price":"0.00000","misc":"","oflags":"fciq"}}}}

}
