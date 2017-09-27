package com.fede.app.crypto.trading.facade;

import com.fede.app.crypto.trading.model.*;
import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.List;

/**
 * Created by f.barbano on 15/09/2017.
 */
public interface IKrakenCaller {

	Long getServerTime() throws IOException;

	List<Asset> getAssets() throws IOException;

	List<AssetPair> getAssetPairs() throws IOException;

	List<Ticker> getTickers(Collection<String> pairNames) throws IOException;

	Pair<Long, List<Ohlc>> getOhlcs(String pairName, long since) throws IOException;

	List<MarketOrder> getOrderBook(String pairName) throws IOException;

	Pair<Long, List<RecentTrade>> getRecentTrades(String pairName, long since) throws IOException;

	Pair<Long, List<SpreadData>> getSpreadData(String pairName, long since) throws IOException;

	List<AccountBalance> getAccountBalance() throws IOException, NoSuchAlgorithmException, InvalidKeyException;

	TradeBalance getTradeBalance(String baseAsset) throws IOException, NoSuchAlgorithmException, InvalidKeyException;

	List<OpenOrder> getOpenOrders(boolean includeTrades) throws NoSuchAlgorithmException, InvalidKeyException, IOException;

	List<ClosedOrder> getClosedOrders(boolean includeTrades) throws NoSuchAlgorithmException, InvalidKeyException, IOException;

	List<OrderInfo> getOrdersInfo(Collection<String> tradeIDs, boolean includeTrades) throws NoSuchAlgorithmException, InvalidKeyException, IOException;

	List<OpenPosition> getOpenPositions(Collection<String> tradeIDs) throws NoSuchAlgorithmException, InvalidKeyException, IOException;

	List<LedgerInfo> getLedgersInfo() throws NoSuchAlgorithmException, InvalidKeyException, IOException;

	List<LedgerInfo> getLedgersInfo(Collection<String> ledgerIDs) throws NoSuchAlgorithmException, InvalidKeyException, IOException;

	TradeVolume getTradeVolume(Collection<String> assetPairs) throws NoSuchAlgorithmException, InvalidKeyException, IOException;
	
}
