package com.fede.app.crypto.trading.manager;

import com.fede.app.crypto.trading.model.*;

import java.util.List;
import java.util.Map;

/**
 * Created by f.barbano on 15/09/2017.
 */
public interface IKrakenManager {

	long getServerTime();

	Map<String, Asset> synchronizeAssets();
	Map<String, AssetPair> synchronizeAssetPairs();

	Map<String, Ticker> downloadTickers();
	List<MarketOrder> downloadOrderBook(String pairName);

	List<Ohlc> downloadOHLCData(String pairName);
	List<RecentTrade> downloadTradesData(String pairName);
	List<SpreadData> downloadSpreadsData(String pairName);

	List<AccountBalance> getAccountBalance();
	TradeBalance getTradeBalance(String pairName);

	List<OpenOrder> getOpenOrders();
	List<ClosedOrder> getClosedOrders();

}
