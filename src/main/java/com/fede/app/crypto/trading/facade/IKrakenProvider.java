package com.fede.app.crypto.trading.facade;

import com.fede.app.crypto.trading.model._private.AccountBalance;
import com.fede.app.crypto.trading.model._private.TradeBalance;
import com.fede.app.crypto.trading.model._public.*;

import java.io.IOException;
import java.util.List;

/**
 * Created by f.barbano on 15/09/2017.
 */
public interface IKrakenProvider {

	void persistAssets(List<Asset> assetsInfo) throws IOException;
	List<Asset> readAssets() throws IOException;
	
	void persistAssetPairs(List<AssetPair> assetPairs) throws IOException;
	List<AssetPair> readAssetPairs() throws IOException;

	void persistTickers(List<Ticker> tickers) throws IOException;
	List<Ticker> readTickers(String pairName) throws IOException;

	void persistOrderBook(List<MarketOrder> marketOrders) throws IOException;
	List<MarketOrder> readOrderBook(String pairName) throws IOException;

	void persistOHLCs(List<Ohlc> ohlcList) throws IOException;
	void persistOHLCLast(String pairName, long last) throws IOException;
	List<Ohlc> readOHLCs(String pairName) throws IOException;
	long readOHLCLast(String pairName) throws IOException;

	void persistTrades(List<RecentTrade> recentTradeList) throws IOException;
	void persistTradeLast(String pairName, long last) throws IOException;
	List<RecentTrade> readTrades(String pairName) throws IOException;
	long readTradeLast(String pairName) throws IOException;

	void persistSpreads(List<SpreadData> tradeList) throws IOException;
	void persistSpreadLast(String pairName, long last) throws IOException;
	List<SpreadData> readSpreads(String pairName) throws IOException;
	long readSpreadLast(String pairName) throws IOException;

	void persistAccountBalance(List<AccountBalance> accountBalances) throws IOException;
	List<AccountBalance> readAccountBalance() throws IOException;

	void persistTradeBalance(TradeBalance accountBalance) throws IOException;
	List<TradeBalance> readTradeBalances() throws IOException;



}
