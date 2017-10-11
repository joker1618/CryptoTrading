package com.fede.app.crypto.trading.kraken;

import com.fede.app.crypto.trading.model._private.*;
import com.fede.app.crypto.trading.model._public.*;
import com.fede.app.crypto.trading.model._trading.AddOrderOut;
import com.fede.app.crypto.trading.model.types.*;
import com.fede.app.crypto.trading.util.StrUtils;
import com.fede.app.crypto.trading.util.Utils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import javax.json.*;
import java.io.StringReader;
import java.util.*;
import java.util.function.Supplier;

import static com.fede.app.crypto.trading.model._public.AssetPair.FeeSchedule;
import static com.fede.app.crypto.trading.model._private.OpenOrder.OrderDescr;
import static com.fede.app.crypto.trading.model._public.Ticker.*;
import static com.fede.app.crypto.trading.model._private.TradeVolume.FeeInfo;

/**
 * Created by f.barbano on 13/09/2017.
 */
class JsonToModel {

	private List<String> errors;
	private JsonObject result;

	public JsonToModel(String jsonString) {
		JsonReader reader = Json.createReader(new StringReader(jsonString));
		JsonObject jsonObject = reader.readObject();
		reader.close();

		this.errors = getArrayString(jsonObject, "error");
		if(errors.isEmpty()) {
			this.result = jsonObject.getJsonObject("result");
		}
	}

	public boolean containsErrors() {
		return !errors.isEmpty();
	}

	public List<String> getErrors() {
		return errors;
	}


	public Long parseServerTime() {
		if(containsErrors()) 	return null;
		return getTimestamp(result, "unixtime", 1000L);
	}

	public List<Asset> parseAssets() {
		List<Asset> assetList = new ArrayList<>();
		for(Map.Entry<String, JsonValue> entry : result.entrySet()) {
			JsonObject jsonAsset = entry.getValue().asJsonObject();
			Asset asset = new Asset();
			asset.setAssetName(entry.getKey());
			asset.setAClass(getString(jsonAsset, "aclass"));
			asset.setAltName(getString(jsonAsset, "altname"));
			asset.setDecimals(getInt(jsonAsset, "decimals"));
			asset.setDisplayDecimals(getInt(jsonAsset, "display_decimals"));
			assetList.add(asset);
		}
		Collections.sort(assetList, Comparator.comparing(Asset::getAssetName));
		return assetList;
	}

	public List<AssetPair> parseAssetPairs() {
		List<AssetPair> assetPairs = new ArrayList<>();
		for(Map.Entry<String, JsonValue> entry : result.entrySet()) {
			AssetPair pair = new AssetPair();
			JsonObject jsonPair = entry.getValue().asJsonObject();
			pair.setPairName(entry.getKey());
			pair.setAltName(getString(jsonPair, "altname"));
			pair.setAClassBase(getString(jsonPair, "aclass_base"));
			pair.setBase(getString(jsonPair, "base"));
			pair.setAClassQuote(getString(jsonPair, "aclass_quote"));
			pair.setQuote(getString(jsonPair, "quote"));
			pair.setLot(getString(jsonPair, "lot"));
			pair.setPairDecimals(getInt(jsonPair, "pair_decimals"));
			pair.setLotDecimals(getInt(jsonPair, "lot_decimals"));
			pair.setLotMultiplier(getInt(jsonPair, "lot_multiplier"));
			pair.setLeverageBuy(getArrayInt(jsonPair, "leverage_buy"));
			pair.setLeverageSell(getArrayInt(jsonPair, "leverage_sell"));
			pair.setFees(parseJsonFeeScheduleArray(jsonPair, "fees"));
			pair.setFeesMaker(parseJsonFeeScheduleArray(jsonPair, "fees_maker"));
			pair.setFeeVolumeCurrency(getString(jsonPair, "fee_volume_currency"));
			pair.setMarginCall(getInt(jsonPair, "margin_call"));
			pair.setMarginStop(getInt(jsonPair, "margin_stop"));
			assetPairs.add(pair);
		}
		Collections.sort(assetPairs, Comparator.comparing(AssetPair::getPairName));
		return assetPairs;
	}

	public List<Ticker> parseTickers(long callTime) {
		List<Ticker> toRet = new ArrayList<>();
		for(Map.Entry<String, JsonValue> entry : result.entrySet()) {
			JsonObject jt = entry.getValue().asJsonObject();
			Ticker ticker = new Ticker();
			ticker.setCallTime(callTime);
			ticker.setPairName(entry.getKey());
			ticker.setAsk(parseTickerWholePrice(jt, "a"));
			ticker.setBid(parseTickerWholePrice(jt, "b"));
			ticker.setLastTradeClosed(parseTickerPrice(jt, "c"));
			ticker.setVolume(parseTickerVolume(jt, "v"));
			ticker.setWeightedAverageVolume(parseTickerVolume(jt, "p"));
			ticker.setTradesNumber(parseTickerVolume(jt, "t"));
			ticker.setLow(parseTickerVolume(jt, "l"));
			ticker.setHigh(parseTickerVolume(jt, "h"));
			ticker.setOpeningPrice(getDouble(jt, "o"));
			toRet.add(ticker);
		}
		toRet.sort(Comparator.comparing(Ticker::getPairName));
		return toRet;
	}

	public Pair<Long, List<Ohlc>> parseOhlcs(String pairName) {
		Long last = getLong(result, "last");

		List<Ohlc> ohlcList = new ArrayList<>();
		result.getJsonArray(pairName).forEach(jv -> {
			List<String> fields = getArrayString(jv.asJsonArray());
			long time = Long.parseLong(fields.get(0));
			if(time <= last) {
				Ohlc ohlc = new Ohlc();
				ohlc.setPairName(pairName);
				ohlc.setTime(time * 1000L);
				ohlc.setOpen(Double.parseDouble(fields.get(1)));
				ohlc.setHigh(Double.parseDouble(fields.get(2)));
				ohlc.setLow(Double.parseDouble(fields.get(3)));
				ohlc.setClose(Double.parseDouble(fields.get(4)));
				ohlc.setVwrap(Double.parseDouble(fields.get(5)));
				ohlc.setVolume(Double.parseDouble(fields.get(6)));
				ohlc.setCount(Long.parseLong(fields.get(7)));
				ohlcList.add(ohlc);
			}
		});
		return Pair.of(last, ohlcList);
	}

	public List<MarketOrder> parseOrderBook(String pairName) {
		JsonObject jobj = result.getJsonObject(pairName);
		JsonArray asks = jobj.getJsonArray("asks");
		JsonArray bids = jobj.getJsonArray("bids");

		List<MarketOrder> orderList = new ArrayList<>();
		orderList.addAll(parseMarketOrders(asks, pairName, OrderDirection.ASK));
		orderList.addAll(parseMarketOrders(bids, pairName, OrderDirection.BID));

		return orderList;
	}

	public Pair<Long, List<RecentTrade>> parseRecentTrades(String pairName) {
		Long last = getLong(result, "last");

		List<RecentTrade> tradeList = new ArrayList<>();
		result.getJsonArray(pairName).forEach(jv -> {
			List<String> fields = getArrayString(jv.asJsonArray());
			double time = Double.parseDouble(fields.get(2)) * 1000;
			long compareTime = (long)(time * Math.pow(10, 6));
			if(compareTime <= last) {
				RecentTrade recentTrade = new RecentTrade();
				recentTrade.setPairName(pairName);
				recentTrade.setPrice(Double.parseDouble(fields.get(0)));
				recentTrade.setVolume(Double.parseDouble(fields.get(1)));
				recentTrade.setTime((long) time);
				recentTrade.setOrderAction(OrderAction.getByLabel(fields.get(3)));
				recentTrade.setOrderType(OrderType.getByLabel(fields.get(4)));
				recentTrade.setMiscellaneous(fields.get(5));
				tradeList.add(recentTrade);
			}
		});
		return Pair.of(last, tradeList);
	}

	public Pair<Long, List<SpreadData>> parseSpreadData(String pairName) {
		Long last = getLong(result, "last");

		/*
		Sometimes differents spread data have the same time, because it is expressed
		in seconds and the spread can change more rapidly. When I found a duplicate time,
		I add 1ms to the second.
		 */
		Set<Long> usedTimes = new TreeSet<>();

		List<SpreadData> spreadDataList = new ArrayList<>();
		result.getJsonArray(pairName).forEach(jv -> {
			List<String> fields = getArrayString(jv.asJsonArray());
			long time = Long.parseLong(fields.get(0));
			if(time <= last) {
				long ts = time * 1000L;
				while(usedTimes.contains(ts)) {
					ts += 1;
				}
				usedTimes.add(ts);

				SpreadData spreadData = new SpreadData();
				spreadData.setPairName(pairName);
				spreadData.setTime(ts);
				spreadData.setBid(Double.parseDouble(fields.get(1)));
				spreadData.setAsk(Double.parseDouble(fields.get(2)));
				spreadDataList.add(spreadData);
			}
		});
		spreadDataList.sort(Comparator.comparing(SpreadData::getPairName));
		return Pair.of(last, spreadDataList);
	}

	public List<AccountBalance> parseAccountBalance(long callTime) {
		List<AccountBalance> abList = new ArrayList<>();
		result.entrySet().forEach(entry -> {
			String assetClass = entry.getKey();
			Double balance = Double.parseDouble(jsonValueToString(entry.getValue()));
			AccountBalance ab = new AccountBalance();
			ab.setCallTime(callTime);
			ab.setAssetName(assetClass);
			ab.setBalance(balance);
			abList.add(ab);
		});

		return abList;
	}

	public TradeBalance parseTradeBalance(long callTime) {
		TradeBalance tb = new TradeBalance();
		tb.setCallTime(callTime);
		tb.setEquivBalance(getDouble(result, "eb"));
		tb.setTradeBalance(getDouble(result, "tb"));
		tb.setMarginAmount(getDouble(result, "m"));
		tb.setUnrealizedProfitLoss(getDouble(result, "n"));
		tb.setBasisCost(getDouble(result, "c"));
		tb.setCurrentValuation(getDouble(result, "v"));
		tb.setEquity(getDouble(result, "e"));
		tb.setFreeMargin(getDouble(result, "mf"));
		tb.setMarginLevel(getDouble(result, "ml"));

		return tb;
	}

	public List<OpenOrder> parseOpenOrders() {
		List<OpenOrder> toRet = new ArrayList<>();
		for(Map.Entry<String, JsonValue> entry : result.getJsonObject("open").entrySet()) {
			JsonObject jtx = entry.getValue().asJsonObject();
			OpenOrder oo = (OpenOrder) parseOrderInfo(jtx, OpenOrder::new);
			oo.setOrderTxID(entry.getKey());
			toRet.add(oo);
		}
		return toRet;
	}

	public List<ClosedOrder> parseClosedOrders() {
		List<ClosedOrder> toRet = new ArrayList<>();
		for(Map.Entry<String, JsonValue> entry : result.getJsonObject("closed").entrySet()) {
			JsonObject jtx = entry.getValue().asJsonObject();
			ClosedOrder co = (ClosedOrder) parseOrderInfo(jtx, ClosedOrder::new);
			co.setOrderTxID(entry.getKey());
			co.setCloseTimestamp(getTimestamp(jtx, "closetm", 1000L));
			co.setReason(getString(jtx, "reason"));
			toRet.add(co);
		}
		return toRet;
	}

	public List<OrderInfo> parseOrdersInfo() {
		List<OrderInfo> toRet = new ArrayList<>();
		for(Map.Entry<String, JsonValue> entry : result.entrySet()) {
			JsonObject jtx = entry.getValue().asJsonObject();
			OrderInfo oi = parseOrderInfo(jtx, null);
			oi.setOrderTxID(entry.getKey());
			toRet.add(oi);
		}
		return toRet;
	}

	public List<OpenPosition> parseOpenPositions() {
		List<OpenPosition> toRet = new ArrayList<>();
		// TODO impl when an example will be found
		return toRet;
	}

	public List<LedgerInfo> parseLedgersInfo() {
		List<LedgerInfo> toRet = new ArrayList<>();
		JsonObject jledger = result.containsKey("ledger") ? result.getJsonObject("ledger") : result;
		if(jledger != null) {
			for (Map.Entry<String, JsonValue> entry : jledger.entrySet()) {
				JsonObject jtx = entry.getValue().asJsonObject();
				LedgerInfo li = parseLedgerInfo(jtx);
				li.setLedgerID(entry.getKey());
				toRet.add(li);
			}
		}
		return toRet;
	}

	public TradeVolume parseTradeVolume() {
		TradeVolume tv = new TradeVolume();
		tv.setCurrency(getString(result, "currency"));
		tv.setVolume(getDouble(result, "volume"));
		tv.setFees(parseFeeInfos(result.getJsonObject("fees")));
		tv.setFeesMaker(parseFeeInfos(result.getJsonObject("fees_maker")));
		return tv;
	}

	public AddOrderOut parseOrderOut() {
		AddOrderOut out = new AddOrderOut();
		JsonObject jdescr = result.getJsonObject("descr");
		if(jdescr != null) {
			out.setOrderDescr(getString(jdescr, "order"));
			out.setCloseDescr(getString(jdescr, "close"));
		}
		out.setTxIDs(getArrayString(jdescr, "txid"));
		return out;
	}





	private List<MarketOrder> parseMarketOrders(JsonArray jarr, String pairName, OrderDirection orderDirection) {
		List<MarketOrder> orderList = new ArrayList<>();
		jarr.forEach(jv -> {
			List<String> fields = getArrayString(jv.asJsonArray());
			MarketOrder order = new MarketOrder();
			order.setPairName(pairName);
			order.setOrderDirection(orderDirection);
			order.setPrice(Double.parseDouble(fields.get(0)));
			order.setVolume(Double.parseDouble(fields.get(1)));
			order.setTimestamp(Long.parseLong(fields.get(2)) * 1000L);
			orderList.add(order);
		});
		return orderList;
	}

	private List<FeeSchedule> parseJsonFeeScheduleArray(JsonObject jsonObj, String key) {
		List<FeeSchedule> toRet = new ArrayList<>();
		JsonArray jsonArray = jsonObj.getJsonArray(key);
		if(jsonArray != null) {
			jsonArray.forEach(jv -> {
				int vol = jv.asJsonArray().getInt(0);
				double perc = jv.asJsonArray().getJsonNumber(1).doubleValue();
				toRet.add(new FeeSchedule(vol, perc));
			});
		}
		Collections.sort(toRet, Comparator.comparingInt(FeeSchedule::getVolume));
		return toRet;
	}

	private TickerPrice parseTickerPrice(JsonObject jsonObj, String key) {
		List<String> values = getArrayString(jsonObj, key);
		TickerPrice tp = new TickerPrice();
		tp.setPrice(Double.parseDouble(values.get(0)));
		tp.setLotVolume(Double.parseDouble(values.get(1)));
		return tp;
	}
	private TickerWholePrice parseTickerWholePrice(JsonObject jsonObj, String key) {
		List<String> values = getArrayString(jsonObj, key);
		TickerWholePrice twp = new TickerWholePrice();
		twp.setPrice(Double.parseDouble(values.get(0)));
		twp.setWholeLotVolume(Integer.parseInt(values.get(1)));
		twp.setLotVolume(Double.parseDouble(values.get(2)));
		return twp;
	}
	private TickerVolume parseTickerVolume(JsonObject jsonObj, String key) {
		List<String> values = getArrayString(jsonObj, key);
		TickerVolume tv = new TickerVolume();
		tv.setToday(Double.parseDouble(values.get(0)));
		tv.setLast24Hours(Double.parseDouble(values.get(1)));
		return tv;
	}

  	private OrderInfo parseOrderInfo(JsonObject jtx, Supplier<OpenOrder> create) {
		JsonObject jdescr = jtx.getJsonObject("descr");

		OrderDescr od = new OrderDescr();
		od.setPairName(getString(jdescr, "pair"));
		od.setOrderAction(OrderAction.getByLabel(getString(jdescr, "type")));
		od.setOrderType(OrderType.getByLabel(getString(jdescr, "ordertype")));
		od.setPrimaryPrime(getDouble(jdescr, "price"));
		od.setSecondaryPrice(getDouble(jdescr, "price2"));
		String strLeverage = getString(jdescr, "leverage");
		if(strLeverage != null && !strLeverage.equals("none")) {
			od.setLeverage(Integer.parseInt(strLeverage));
		}
		od.setOrderDescription(getString(jdescr, "order"));
		od.setCloseDescription(getString(jdescr, "close"));

		OrderInfo oi = create == null ? new OrderInfo() : create.get();
		oi.setRefId(getString(jtx, "refid"));
		oi.setUserRef(getString(jtx, "userref"));
		oi.setStatus(OrderStatus.getByLabel(getStringValue(jtx, "status")));
		oi.setOpenTimestamp(getTimestamp(jtx, "opentm", 1000L));
		oi.setStartTimestamp(getTimestamp(jtx, "starttm", 1000L));
		oi.setExpireTimestamp(getTimestamp(jtx, "expiretm", 1000L));
		oi.setDescr(od);
		oi.setVolume(getDouble(jtx, "vol"));
		oi.setVolumeExecuted(getDouble(jtx, "vol_exec"));
		oi.setCost(getDouble(jtx, "cost"));
		oi.setFee(getDouble(jtx, "fee"));
		oi.setAveragePrice(getDouble(jtx, "price"));
		oi.setStopPrice(getDouble(jtx, "stopprice"));
		oi.setLimitPrice(getDouble(jtx, "limitprice"));
		oi.setMisc(Utils.map(getCommaDelimitedList(jtx, "misc"), OrderMisc::getByLabel));
		oi.setFlags(Utils.map(getCommaDelimitedList(jtx, "oflags"), OrderFlag::getByLabel));
		oi.setTrades(getArrayString(jtx, "trades"));

		return oi;
	}

	private LedgerInfo parseLedgerInfo(JsonObject jobj) {
		LedgerInfo li = new LedgerInfo();
		li.setRefID(getString(jobj, "refid"));
		li.setTime(getTimestamp(jobj, "time", 1000L));
		li.setLedgerType(LedgerType.getByLabel(getString(jobj, "type")));
		li.setAssetClass(getStringValue(jobj, "aclass"));
		li.setAssetName(getString(jobj, "asset"));
		li.setAmount(getDouble(jobj, "amount"));
		li.setFee(getDouble(jobj, "fee"));
		li.setBalance(getDouble(jobj, "balance"));
		return li;
	}

	private List<FeeInfo> parseFeeInfos(JsonObject jfee) {
		List<FeeInfo> fees = new ArrayList<>();
		if(jfee != null) {
			for (Map.Entry<String, JsonValue> entry : jfee.entrySet()) {
				String pair = entry.getKey();
				JsonObject jobj = jfee.getJsonObject(pair);
				FeeInfo fi = new FeeInfo();
				fi.setPairName(pair);
				fi.setFee(getDouble(jobj, "fee"));
				fi.setMinFee(getDouble(jobj, "minfee"));
				fi.setMaxFee(getDouble(jobj, "maxfee"));
				fi.setNextFee(getDouble(jobj, "nextfee"));
				fi.setNextVolume(getDouble(jobj, "nextvolume"));
				fi.setTierVolume(getDouble(jobj, "tiervolume"));
				fees.add(fi);
			}
		}
		return fees;
	}


	private String jsonValueToString(JsonValue jv) {
		if(jv.getValueType() == JsonValue.ValueType.STRING) {
			return ((JsonString)jv).getString();
		} else {
			return jv.toString();
		}
	}
	private String getStringValue(JsonObject jsonObject, String key) {
		JsonValue jv = jsonObject.get(key);
		String value = null;
		if(jv != null && !jsonObject.isNull(key)) {
			value = jsonValueToString(jv);
		}
		return value;
	}
	private String getString(JsonObject jsonObject, String key) {
		JsonValue jv = jsonObject.get(key);
		if(jv != null && !jsonObject.isNull(key)) {
			if(jv.getValueType() == JsonValue.ValueType.STRING) {
				return ((JsonString)jv).getString();
			}
		}
		return null;
	}
	private Long getTimestamp(JsonObject jsonObject, String key, long multiplier) {
		Double dnum = getDouble(jsonObject, key);
		if(dnum == null) 	return null;
		return (long)(dnum * multiplier);
	}
	private Long getLong(JsonObject jsonObject, String key) {
		String value = getStringValue(jsonObject, key);
		if(value == null) 	return null;
		return Long.parseLong(value);
	}
	private Double getDouble(JsonObject jsonObject, String key) {
		String value = getStringValue(jsonObject, key);
		if(value == null) 	return null;
		return Double.parseDouble(value);
	}
	private Integer getInt(JsonObject jsonObject, String key) {
		String value = getStringValue(jsonObject, key);
		if(value == null) 	return null;
		return Integer.parseInt(value);
	}
	private List<String> getCommaDelimitedList(JsonObject jObj, String key) {
		String strValue = getStringValue(jObj, key);
		List<String> toRet = new ArrayList<>();
		if(StringUtils.isNotBlank(strValue)) {
			toRet = StrUtils.splitFieldsList(strValue, ",", true);
		}
		return toRet;
	}
	private List<String> getArrayString(JsonObject jObj, String key) {
		List<String> toRet = new ArrayList<>();
		JsonArray jsonArr = jObj.getJsonArray(key);
		if(jsonArr != null) {
			jsonArr.forEach(jv -> toRet.add(jsonValueToString(jv)));
		}
		return toRet;
	}
	private List<String> getArrayString(JsonArray jsonArr) {
		List<String> toRet = new ArrayList<>();
		jsonArr.forEach(jv -> toRet.add(jsonValueToString(jv)));
		return toRet;
	}
	private List<Integer> getArrayInt(JsonObject jObj, String key) {
		List<Integer> intList = Utils.map(getArrayString(jObj, key), Integer::parseInt);
		Collections.sort(intList);
		return intList;
	}
}
