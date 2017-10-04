package com.fede.app.crypto.trading.facade;

import com.fede.app.crypto.trading.model._private.*;
import com.fede.app.crypto.trading.model._public.*;
import com.fede.app.crypto.trading.model._trading.AddOrderIn;
import com.fede.app.crypto.trading.model._trading.AddOrderOut;
import com.fede.app.crypto.trading.parser.JsonToModel;
import com.fede.app.crypto.trading.util.Utils;
import edu.self.kraken.api.KrakenApi;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * Created by f.barbano on 15/09/2017.
 */
public class KrakenCallerImpl implements IKrakenCaller {

	private final KrakenApi krakenApi;
	private final String krakenKey;
	private final String krakenSecret;

	public KrakenCallerImpl(String krakenKey, String krakenSecret) {
		this.krakenKey = krakenKey;
		this.krakenSecret = krakenSecret;
		this.krakenApi = new KrakenApi();
		this.krakenApi.setKey(krakenKey);
		this.krakenApi.setSecret(krakenSecret);
	}

	@Override
	public Long getServerTime() throws IOException {
		String json = krakenApi.queryPublic(KrakenApi.Method.TIME);
		JsonToModel jm = new JsonToModel(json);
		return jm.parseServerTime();
	}

	@Override
	public List<Asset> getAssets() throws IOException {
		String json = krakenApi.queryPublic(KrakenApi.Method.ASSETS);
		JsonToModel jm = new JsonToModel(json);
		return jm.parseAssets();
	}

	@Override
	public List<AssetPair> getAssetPairs() throws IOException {
		String json = krakenApi.queryPublic(KrakenApi.Method.ASSET_PAIRS);
		JsonToModel jm = new JsonToModel(json);
		return jm.parseAssetPairs();
	}

	@Override
	public List<Ticker> getTickers(Collection<String> pairNames) throws IOException {
		Map<String, String> apiParams = new HashMap<>();
		apiParams.put("pair", Utils.join(pairNames));
		long callTime = System.currentTimeMillis();
		String json = krakenApi.queryPublic(KrakenApi.Method.TICKER, apiParams);
		JsonToModel jm = new JsonToModel(json);
		return jm.parseTickers(callTime);
	}

	@Override
	public Pair<Long, List<Ohlc>> getOhlcs(String pairName, long since) throws IOException {
		Map<String, String> apiParams = new HashMap<>();
		apiParams.put("pair", pairName);
		if(since > 0) {
			apiParams.put("since", String.valueOf(since));
		}
		String json = krakenApi.queryPublic(KrakenApi.Method.OHLC, apiParams);
		JsonToModel jm = new JsonToModel(json);
		return jm.parseOhlcs(pairName);
	}

	@Override
	public List<MarketOrder> getOrderBook(String pairName) throws IOException {
		Map<String, String> apiParams = new HashMap<>();
		apiParams.put("pair", pairName);
		String json = krakenApi.queryPublic(KrakenApi.Method.DEPTH, apiParams);
		JsonToModel jm = new JsonToModel(json);
		return jm.parseOrderBook(pairName);
	}

	@Override
	public Pair<Long, List<RecentTrade>> getRecentTrades(String pairName, long since) throws IOException {
		Map<String, String> apiParams = new HashMap<>();
		apiParams.put("pair", pairName);
		if(since > 0) {
			apiParams.put("since", String.valueOf(since));
		}
		String json = krakenApi.queryPublic(KrakenApi.Method.TRADES, apiParams);
		JsonToModel jm = new JsonToModel(json);
		return jm.parseRecentTrades(pairName);
	}

	@Override
	public Pair<Long, List<SpreadData>> getSpreadData(String pairName, long since) throws IOException {
		Map<String, String> apiParams = new HashMap<>();
		apiParams.put("pair", pairName);
		if(since > 0) {
			apiParams.put("since", String.valueOf(since));
		}
		String json = krakenApi.queryPublic(KrakenApi.Method.SPREAD, apiParams);
		JsonToModel jm = new JsonToModel(json);
		return jm.parseSpreadData(pairName);
	}

	@Override
	public List<AccountBalance> getAccountBalance() throws IOException, NoSuchAlgorithmException, InvalidKeyException {
		long callTime = System.currentTimeMillis();
		String json = krakenApi.queryPrivate(KrakenApi.Method.BALANCE);
		System.out.println(json);
		JsonToModel jm = new JsonToModel(json);
		return jm.parseAccountBalance(callTime);
	}

	@Override
	public TradeBalance getTradeBalance(String baseAsset) throws IOException, NoSuchAlgorithmException, InvalidKeyException {
		Map<String, String> apiParams = new HashMap<>();
		if(StringUtils.isNotBlank(baseAsset)) {
			apiParams.put("asset", baseAsset);
		}
		long callTime = System.currentTimeMillis();
		String json = krakenApi.queryPrivate(KrakenApi.Method.TRADE_BALANCE, apiParams);
		System.out.println(json);
		JsonToModel jm = new JsonToModel(json);
		return jm.parseTradeBalance(callTime);
	}

	@Override
	public List<OpenOrder> getOpenOrders(boolean includeTrades) throws NoSuchAlgorithmException, InvalidKeyException, IOException {
		Map<String, String> apiParams = new HashMap<>();
		if(includeTrades) {
			apiParams.put("trades", "true");	// include related trade IDs (default is 'false')
		}
		String json = krakenApi.queryPrivate(KrakenApi.Method.OPEN_ORDERS, apiParams);
		JsonToModel jm = new JsonToModel(json);
		return jm.parseOpenOrders();
	}

	@Override
	public List<ClosedOrder> getClosedOrders(boolean includeTrades) throws NoSuchAlgorithmException, InvalidKeyException, IOException {
		Map<String, String> apiParams = new HashMap<>();
		if(includeTrades) {
			apiParams.put("trades", "true");	// include related trade IDs (default is 'false')
		}
		String json = krakenApi.queryPrivate(KrakenApi.Method.CLOSED_ORDERS, apiParams);
		JsonToModel jm = new JsonToModel(json);
		return jm.parseClosedOrders();
	}

	@Override
	public List<OrderInfo> getOrdersInfo(Collection<String> tradeIDs, boolean includeTrades) throws NoSuchAlgorithmException, InvalidKeyException, IOException {
		Map<String, String> apiParams = new HashMap<>();
		apiParams.put("txid", Utils.join(tradeIDs, ","));
		if(includeTrades) {
			apiParams.put("trades", "true");	// include related trade IDs (default is 'false')
		}
		String json = krakenApi.queryPrivate(KrakenApi.Method.QUERY_ORDERS, apiParams);
		JsonToModel jm = new JsonToModel(json);
		return jm.parseOrdersInfo();
	}

	@Override
	public List<OpenPosition> getOpenPositions(Collection<String> tradeIDs) throws NoSuchAlgorithmException, InvalidKeyException, IOException {
		// TODO impl when an example will be found
		return Collections.emptyList();
	}

	@Override
	public List<LedgerInfo> getLedgersInfo() throws NoSuchAlgorithmException, InvalidKeyException, IOException {
		return getLedgersInfo(Collections.emptyList());
	}

	@Override
	public List<LedgerInfo> getLedgersInfo(Collection<String> ledgerIDs) throws NoSuchAlgorithmException, InvalidKeyException, IOException {
		Map<String, String> apiParams = new HashMap<>();
		KrakenApi.Method kmethod;
		if(!ledgerIDs.isEmpty()) {
			apiParams.put("id", Utils.join(ledgerIDs, ","));
			kmethod = KrakenApi.Method.QUERY_LEDGERS;
		} else {
			kmethod = KrakenApi.Method.LEDGERS;
		}
		String json = krakenApi.queryPrivate(kmethod, apiParams);
		JsonToModel jm = new JsonToModel(json);
		return jm.parseLedgersInfo();
	}

	@Override
	public TradeVolume getTradeVolume(Collection<String> assetPairs) throws NoSuchAlgorithmException, InvalidKeyException, IOException {
		Map<String, String> apiParams = new HashMap<>();
		apiParams.put("pair", Utils.join(assetPairs, ","));
		apiParams.put("fee-info", "true");
		String json = krakenApi.queryPrivate(KrakenApi.Method.TRADE_VOLUME, apiParams);
		JsonToModel jm = new JsonToModel(json);
		return jm.parseTradeVolume();
	}

	@Override
	public AddOrderOut addOrder(AddOrderIn orderRequest) throws NoSuchAlgorithmException, InvalidKeyException, IOException {
		Map<String, String> apiParams = new HashMap<>();
		apiParams.put("pair", orderRequest.getPairName());
		apiParams.put("type", orderRequest.getOrderAction().label());
		apiParams.put("ordertype", orderRequest.getOrderType().label());
		apiParams.put("price", Utils.toString(orderRequest.getPrice()));
		apiParams.put("volume", Utils.toString(orderRequest.getVolume()));
		if(orderRequest.getPrice2() != null) {
			apiParams.put("price2", Utils.toString(orderRequest.getPrice2()));
		}
		if(orderRequest.getLeverage() != null) {
			apiParams.put("leverage", String.valueOf(orderRequest.getLeverage()));
		}
		if(!orderRequest.getOflags().isEmpty()) {
			apiParams.put("oflags", Utils.join(orderRequest.getOflags(), ","));
		}
		if(orderRequest.getStarttm() != null) {
			apiParams.put("starttm", orderRequest.getStarttm());
		}
		if(orderRequest.getExpiretm() != null) {
			apiParams.put("expiretm", orderRequest.getExpiretm());
		}
		if(orderRequest.getUserRef() != null) {
			apiParams.put("userref", orderRequest.getUserRef());
		}
		if(orderRequest.isValidate()) {
			apiParams.put("validate", "yes");
		}
		String json = krakenApi.queryPrivate(KrakenApi.Method.ADD_ORDER, apiParams);
		JsonToModel jm = new JsonToModel(json);
		return jm.parseOrderOut();
	}

}
