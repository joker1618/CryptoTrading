package com.fede.app.crypto.trading.kraken;

import com.fede.app.crypto.trading.exception.KrakenCallException;
import com.fede.app.crypto.trading.exception.KrakenResponseError;
import com.fede.app.crypto.trading.logger.ISimpleLog;
import com.fede.app.crypto.trading.logger.LogService;
import com.fede.app.crypto.trading.model._private.*;
import com.fede.app.crypto.trading.model._public.*;
import com.fede.app.crypto.trading.model._trading.AddOrderIn;
import com.fede.app.crypto.trading.model._trading.AddOrderOut;
import com.fede.app.crypto.trading.util.Utils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

/**
 * Created by f.barbano on 15/09/2017.
 */
public class KrakenFacadeImpl implements IKrakenFacade {

	private static final ISimpleLog logger = LogService.getLogger(KrakenFacadeImpl.class);

	private final KrakenApi krakenApi;

	public KrakenFacadeImpl(String krakenKey, String krakenSecret) {
		this.krakenApi = new KrakenApi();
		this.krakenApi.setKey(krakenKey);
		this.krakenApi.setSecret(krakenSecret);
	}

	@Override
	public Long getServerTime() throws KrakenResponseError, KrakenCallException {
		JsonToModel jm = performPublicCall(KrakenMethod.TIME, null);
		return jm.parseServerTime();
	}

	@Override
	public List<Asset> getAssets() throws KrakenResponseError, KrakenCallException {
		JsonToModel jm = performPublicCall(KrakenMethod.ASSETS, null);
		return jm.parseAssets();
	}

	@Override
	public List<AssetPair> getAssetPairs() throws KrakenResponseError, KrakenCallException {
		JsonToModel jm = performPublicCall(KrakenMethod.ASSET_PAIRS, null);
		return jm.parseAssetPairs();
	}

	@Override
	public List<Ticker> getTickers(Collection<String> pairNames) throws KrakenResponseError, KrakenCallException {
		Map<String, String> apiParams = new HashMap<>();
		apiParams.put("pair", Utils.join(pairNames));
		long callTime = System.currentTimeMillis();
		JsonToModel jm = performPublicCall(KrakenMethod.TICKER, apiParams);
		return jm.parseTickers(callTime);
	}

	@Override
	public Pair<Long, List<SpreadData>> getSpreadData(String pairName, Long since) throws KrakenResponseError, KrakenCallException {
		Map<String, String> apiParams = new HashMap<>();
		apiParams.put("pair", pairName);
		if(since != null && since > 0) {
			apiParams.put("since", String.valueOf(since));
		}
		JsonToModel jm = performPublicCall(KrakenMethod.SPREAD, apiParams);
		return jm.parseSpreadData(pairName);
	}


	////////////////////////////////////// Deprecated methods below /////////////////////////////////////////////////////////////////////


	@Override
	public Pair<Long, List<Ohlc>> getOhlcs(String pairName, long since) throws IOException {
		Map<String, String> apiParams = new HashMap<>();
		apiParams.put("pair", pairName);
		if(since > 0) {
			apiParams.put("since", String.valueOf(since));
		}
		String json = krakenApi.queryPublic(KrakenMethod.OHLC, apiParams);
		JsonToModel jm = new JsonToModel(json);
		return jm.parseOhlcs(pairName);
	}

	@Override
	public List<MarketOrder> getOrderBook(String pairName) throws IOException {
		Map<String, String> apiParams = new HashMap<>();
		apiParams.put("pair", pairName);
		String json = krakenApi.queryPublic(KrakenMethod.DEPTH, apiParams);
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
		String json = krakenApi.queryPublic(KrakenMethod.TRADES, apiParams);
		JsonToModel jm = new JsonToModel(json);
		return jm.parseRecentTrades(pairName);
	}

	@Override
	public List<AccountBalance> getAccountBalance() throws IOException, NoSuchAlgorithmException, InvalidKeyException {
		long callTime = System.currentTimeMillis();
		String json = krakenApi.queryPrivate(KrakenMethod.BALANCE);
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
		String json = krakenApi.queryPrivate(KrakenMethod.TRADE_BALANCE, apiParams);
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
		String json = krakenApi.queryPrivate(KrakenMethod.OPEN_ORDERS, apiParams);
		JsonToModel jm = new JsonToModel(json);
		return jm.parseOpenOrders();
	}

	@Override
	public List<ClosedOrder> getClosedOrders(boolean includeTrades) throws NoSuchAlgorithmException, InvalidKeyException, IOException {
		Map<String, String> apiParams = new HashMap<>();
		if(includeTrades) {
			apiParams.put("trades", "true");	// include related trade IDs (default is 'false')
		}
		String json = krakenApi.queryPrivate(KrakenMethod.CLOSED_ORDERS, apiParams);
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
		String json = krakenApi.queryPrivate(KrakenMethod.QUERY_ORDERS, apiParams);
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
		KrakenMethod kmethod;
		if(!ledgerIDs.isEmpty()) {
			apiParams.put("id", Utils.join(ledgerIDs, ","));
			kmethod = KrakenMethod.QUERY_LEDGERS;
		} else {
			kmethod = KrakenMethod.LEDGERS;
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
		String json = krakenApi.queryPrivate(KrakenMethod.TRADE_VOLUME, apiParams);
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
		String json = krakenApi.queryPrivate(KrakenMethod.ADD_ORDER, apiParams);
		JsonToModel jm = new JsonToModel(json);
		return jm.parseOrderOut();
	}


	private JsonToModel performPublicCall(KrakenMethod method, Map<String, String> apiParams) throws KrakenResponseError, KrakenCallException {
		try {
			String subMex = String.format("Kraken call, method %s", method.getName());
			long start = System.currentTimeMillis();
			logger.info("%s: start", subMex);
			String json = krakenApi.queryPublic(method, apiParams);
			logger.info("%s: elapsed %d ms", subMex, (System.currentTimeMillis()-start));
			logger.fine("JSON received --> %s", json);
			JsonToModel jm = new JsonToModel(json);
			if(jm.containsErrors()) {
				logger.error("Kraken public call for method %s return errors: %s", method.getName(), jm.getErrors());
				throw new KrakenResponseError(method.getName(), jm.getErrors());
			}
			return jm;

		} catch (IOException e) {
			logger.error(e);
			throw new KrakenCallException(e, method.getName());
		}
	}
}
