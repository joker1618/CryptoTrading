package com.fede.app.crypto.trading.kraken;

import com.fede.app.crypto.trading.exception.KrakenCallException;
import com.fede.app.crypto.trading.exception.KrakenResponseError;
import com.fede.app.crypto.trading.model._private.*;
import com.fede.app.crypto.trading.model._public.*;
import com.fede.app.crypto.trading.model._trading.AddOrderIn;
import com.fede.app.crypto.trading.model._trading.AddOrderOut;
import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.List;

/**
 * Created by f.barbano on 15/09/2017.
 */
public interface IKrakenFacade {

	Long getServerTime() throws KrakenResponseError, KrakenCallException;

	List<Asset> getAssets() throws KrakenResponseError, KrakenCallException;

	List<AssetPair> getAssetPairs() throws KrakenResponseError, KrakenCallException;

	List<Ticker> getTickers(Collection<String> pairNames) throws KrakenResponseError, KrakenCallException;

	Pair<Long, List<Ohlc>> getOhlcs(String pairName, long since) throws IOException;

	List<MarketOrder> getOrderBook(String pairName) throws IOException;

	Pair<Long, List<RecentTrade>> getRecentTrades(String pairName, long since) throws IOException;

	Pair<Long, List<SpreadData>> getSpreadData(String pairName, Long since) throws KrakenResponseError, KrakenCallException;

	List<AccountBalance> getAccountBalance() throws IOException, NoSuchAlgorithmException, InvalidKeyException;

	TradeBalance getTradeBalance(String baseAsset) throws IOException, NoSuchAlgorithmException, InvalidKeyException;

	List<OpenOrder> getOpenOrders(boolean includeTrades) throws NoSuchAlgorithmException, InvalidKeyException, IOException;

	List<ClosedOrder> getClosedOrders(boolean includeTrades) throws NoSuchAlgorithmException, InvalidKeyException, IOException;

	List<OrderInfo> getOrdersInfo(Collection<String> tradeIDs, boolean includeTrades) throws NoSuchAlgorithmException, InvalidKeyException, IOException;

	List<OpenPosition> getOpenPositions(Collection<String> tradeIDs) throws NoSuchAlgorithmException, InvalidKeyException, IOException;

	List<LedgerInfo> getLedgersInfo() throws NoSuchAlgorithmException, InvalidKeyException, IOException;

	List<LedgerInfo> getLedgersInfo(Collection<String> ledgerIDs) throws NoSuchAlgorithmException, InvalidKeyException, IOException;

	TradeVolume getTradeVolume(Collection<String> assetPairs) throws NoSuchAlgorithmException, InvalidKeyException, IOException;

	AddOrderOut addOrder(AddOrderIn orderRequest) throws NoSuchAlgorithmException, InvalidKeyException, IOException;
}
