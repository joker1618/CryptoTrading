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

	/* Public methods */

	Long getServerTime() throws KrakenResponseError, KrakenCallException;

	List<Asset> getAssets() throws KrakenResponseError, KrakenCallException;

	List<AssetPair> getAssetPairs() throws KrakenResponseError, KrakenCallException;

	List<Ticker> getTickers(Collection<String> pairNames) throws KrakenResponseError, KrakenCallException;

	Pair<Long, List<SpreadData>> getSpreadData(String pairName, Long since) throws KrakenResponseError, KrakenCallException;

	/* Private methods */

	AddOrderOut addOrder(AddOrderIn orderRequest) throws KrakenResponseError, KrakenCallException;

	List<OpenOrder> getOpenOrders(boolean includeTrades) throws KrakenResponseError, KrakenCallException;

	List<ClosedOrder> getClosedOrders(boolean includeTrades) throws KrakenResponseError, KrakenCallException;

	List<OrderInfo> getOrdersInfo(Collection<String> tradeIDs, boolean includeTrades) throws KrakenResponseError, KrakenCallException;


	/* Deprecated, to review */

	@Deprecated
	Pair<Long, List<Ohlc>> getOhlcs(String pairName, long since) throws IOException;

	@Deprecated
	List<MarketOrder> getOrderBook(String pairName) throws IOException;

	@Deprecated
	Pair<Long, List<RecentTrade>> getRecentTrades(String pairName, long since) throws IOException;

	@Deprecated
	List<AccountBalance> getAccountBalance() throws IOException, NoSuchAlgorithmException, InvalidKeyException;

	@Deprecated
	TradeBalance getTradeBalance(String baseAsset) throws IOException, NoSuchAlgorithmException, InvalidKeyException;

	@Deprecated
	List<OpenPosition> getOpenPositions(Collection<String> tradeIDs) throws NoSuchAlgorithmException, InvalidKeyException, IOException;

	@Deprecated
	List<LedgerInfo> getLedgersInfo() throws NoSuchAlgorithmException, InvalidKeyException, IOException;

	@Deprecated
	List<LedgerInfo> getLedgersInfo(Collection<String> ledgerIDs) throws NoSuchAlgorithmException, InvalidKeyException, IOException;

	@Deprecated
	TradeVolume getTradeVolume(Collection<String> assetPairs) throws NoSuchAlgorithmException, InvalidKeyException, IOException;

}
