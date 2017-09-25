package com.fede.app.crypto.trading.facade;

import com.fede.app.crypto.trading.model.*;
import com.fede.app.crypto.trading.parser.JsonToModel;
import com.fede.app.crypto.trading.util.Utils;
import edu.self.kraken.api.KrakenApi;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	public List<OpenOrder> getOpenOrders() throws NoSuchAlgorithmException, InvalidKeyException, IOException {
		Map<String, String> apiParams = new HashMap<>();
		apiParams.put("trades", "true");	// include related trade IDs (default is 'false')
		String json = krakenApi.queryPrivate(KrakenApi.Method.OPEN_ORDERS, apiParams);
		JsonToModel jm = new JsonToModel(json);
		return jm.parseOpenOrders();
	}

	@Override
	public List<ClosedOrder> getClosedOrders() throws NoSuchAlgorithmException, InvalidKeyException, IOException {
		Map<String, String> apiParams = new HashMap<>();
		apiParams.put("trades", "true");	// include related trade IDs (default is 'false')
		String json = krakenApi.queryPrivate(KrakenApi.Method.CLOSED_ORDERS, apiParams);
		JsonToModel jm = new JsonToModel(json);
		return jm.parseClosedOrders();
	}
}
