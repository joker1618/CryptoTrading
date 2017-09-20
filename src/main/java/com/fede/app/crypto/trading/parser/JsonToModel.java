package com.fede.app.crypto.trading.parser;

import com.fede.app.crypto.trading.model.*;
import com.fede.app.crypto.trading.types.ActionType;
import com.fede.app.crypto.trading.types.OrderDirection;
import com.fede.app.crypto.trading.types.OrderType;
import com.fede.app.crypto.trading.util.Utils;
import org.apache.commons.lang3.tuple.Pair;

import javax.json.*;
import java.io.StringReader;
import java.util.*;

import static com.fede.app.crypto.trading.model.AssetPair.FeeSchedule;
import static com.fede.app.crypto.trading.model.Ticker.*;

/**
 * Created by f.barbano on 13/09/2017.
 */
public class JsonToModel {

	private List<String> errors;
	private JsonObject result;

	public JsonToModel(String jsonString) {
		JsonReader reader = Json.createReader(new StringReader(jsonString));
		JsonObject jsonObject = reader.readObject();
		reader.close();

		this.errors = jsonArrayToList(jsonObject, "error");
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
		if(!containsErrors()) {
			JsonNumber jnum = result.getJsonNumber("unixtime");
			if(jnum != null) {
				return jnum.longValue();
			}
		}
		return null;
	}

	public List<Asset> parseAssets() {
		if(containsErrors())	return null;

		List<Asset> assetList = new ArrayList<>();
		for(Map.Entry<String, JsonValue> entry : result.entrySet()) {
			JsonObject jsonAsset = entry.getValue().asJsonObject();
			Asset asset = new Asset();
			asset.setAssetName(entry.getKey());
			asset.setAClass(jsonAsset.getString("aclass"));
			asset.setAltName(jsonAsset.getString("altname"));
			asset.setDecimals(jsonAsset.getInt("decimals"));
			asset.setDisplayDecimals(jsonAsset.getInt("display_decimals"));
			assetList.add(asset);
		}

		return assetList;
	}

	public List<AssetPair> parseAssetPairs() {
		if(containsErrors())	return null;

		List<AssetPair> assetPairs = new ArrayList<>();
		for(Map.Entry<String, JsonValue> entry : result.entrySet()) {
			AssetPair pair = new AssetPair();
			JsonObject jsonPair = entry.getValue().asJsonObject();
			pair.setPairName(entry.getKey());
			pair.setAltName(jsonPair.getString("altname"));
			pair.setAClassBase(jsonPair.getString("aclass_base"));
			pair.setBase(jsonPair.getString("base"));
			pair.setAClassQuote(jsonPair.getString("aclass_quote"));
			pair.setQuote(jsonPair.getString("quote"));
			pair.setLot(jsonPair.getString("lot"));
			pair.setPairDecimals(jsonPair.getInt("pair_decimals"));
			pair.setLotDecimals(jsonPair.getInt("lot_decimals"));
			pair.setLotMultiplier(jsonPair.getInt("lot_multiplier"));
			pair.setLeverageBuy(parseJsonIntArray(jsonPair, "leverage_buy"));
			pair.setLeverageSell(parseJsonIntArray(jsonPair, "leverage_sell"));
			pair.setFees(parseJsonFeeScheduleArray(jsonPair, "fees"));
			pair.setFeesMaker(parseJsonFeeScheduleArray(jsonPair, "fees_maker"));
			pair.setFeeVolumeCurrency(jsonPair.getString("fee_volume_currency"));
			pair.setMarginCall(jsonPair.getInt("margin_call"));
			pair.setMarginStop(jsonPair.getInt("margin_stop"));
			assetPairs.add(pair);
		}
		return assetPairs;
	}

	public List<Ticker> parseTickers(long callTime) {
		if(containsErrors())	return null;

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
			ticker.setTodayOpeningPrice(Utils.toDouble(jt.getString("o")));
			toRet.add(ticker);
		}
		return toRet;
	}

	public Pair<Long, List<OHLC>> parseOHLCs(String pairName) {
		if(containsErrors())	return null;

		long last = result.getJsonNumber("last").longValue();

		List<OHLC> ohlcList = new ArrayList<>();
		result.getJsonArray(pairName).forEach(jv -> {
			List<String> fields = jsonArrayToList(jv.asJsonArray());
			long time = Long.parseLong(fields.get(0));
			if(time <= last) {
				OHLC ohlc = new OHLC();
				ohlc.setPairName(pairName);
				ohlc.setTime(time * 1000);
				ohlc.setOpen(Utils.toDouble(fields.get(1)));
				ohlc.setHigh(Utils.toDouble(fields.get(2)));
				ohlc.setLow(Utils.toDouble(fields.get(3)));
				ohlc.setClose(Utils.toDouble(fields.get(4)));
				ohlc.setVwrap(Utils.toDouble(fields.get(5)));
				ohlc.setVolume(Utils.toDouble(fields.get(6)));
				ohlc.setCount(Long.parseLong(fields.get(7)));
				ohlcList.add(ohlc);
			}
		});
		return Pair.of(last, ohlcList);
	}

	public List<Order> parseOrderBook(String pairName) {
		if(containsErrors())	return null;

		JsonObject jobj = result.getJsonObject(pairName);
		JsonArray asks = jobj.getJsonArray("asks");
		JsonArray bids = jobj.getJsonArray("bids");

		List<Order> orderList = new ArrayList<>();
		orderList.addAll(parseOrders(asks, pairName, OrderDirection.ASK));
		orderList.addAll(parseOrders(bids, pairName, OrderDirection.BID));

		return orderList;
	}
	private List<Order> parseOrders(JsonArray jarr, String pairName, OrderDirection orderDirection) {
		List<Order> orderList = new ArrayList<>();
		jarr.forEach(jv -> {
			List<String> fields = jsonArrayToList(jv.asJsonArray());
			Order order = new Order();
			order.setPairName(pairName);
			order.setOrderDirection(orderDirection);
			order.setPrice(Utils.toDouble(fields.get(0)));
			order.setVolume(Utils.toDouble(fields.get(1)));
			order.setTimestamp(Long.parseLong(fields.get(2)) * 1000L);
			orderList.add(order);
		});
		return orderList;
	}

	public Pair<Long, List<Trade>> parseTrades(String pairName) {
		if(containsErrors())	return null;

		long last = Long.parseLong(result.getString("last"));

		List<Trade> tradeList = new ArrayList<>();
		result.getJsonArray(pairName).forEach(jv -> {
			List<String> fields = jsonArrayToList(jv.asJsonArray());
			double time = Utils.toDouble(fields.get(2)) * 1000;
			long compareTime = (long)(time * Math.pow(10, 6));
			if(compareTime <= last) {
				Trade trade = new Trade();
				trade.setPairName(pairName);
				trade.setPrice(Utils.toDouble(fields.get(0)));
				trade.setVolume(Utils.toDouble(fields.get(1)));
				trade.setTime((long) time);
				trade.setActionType(ActionType.getByLabel(fields.get(3)));
				trade.setOrderType(OrderType.getByLabel(fields.get(4)));
				trade.setMiscellaneous(fields.get(5));
				tradeList.add(trade);
			}
		});
		return Pair.of(last, tradeList);
	}

	public Pair<Long, List<Spread>> parseSpreads(String pairName) {
		if(containsErrors())	return null;

		long last = result.getJsonNumber("last").longValue();

		List<Spread> spreadList = new ArrayList<>();
		result.getJsonArray(pairName).forEach(jv -> {
			List<String> fields = jsonArrayToList(jv.asJsonArray());
			long time = Long.parseLong(fields.get(0));
			if(time <= last) {
				Spread spread = new Spread();
				spread.setPairName(pairName);
				spread.setTime(time * 1000L);
				spread.setBid(Utils.toDouble(fields.get(1)));
				spread.setAsk(Utils.toDouble(fields.get(2)));
				spreadList.add(spread);
			}
		});
		return Pair.of(last, spreadList);
	}

	public List<AccountBalance> parseAccountBalance(long callTime) {
		if(containsErrors())	return null;

		List<AccountBalance> abList = new ArrayList<>();
		result.entrySet().forEach(entry -> {
			String assetClass = entry.getKey();
			double balance = Utils.toDouble(result.getString(assetClass));
			AccountBalance ab = new AccountBalance();
			ab.setCallTime(callTime);
			ab.setAssetClass(assetClass);
			ab.setBalance(balance);
			abList.add(ab);
		});

		return abList;
	}

	public TradeBalance parseTradeBalance(long callTime) {
		if(containsErrors())	return null;

		TradeBalance tb = new TradeBalance();
		tb.setCallTime(callTime);
		tb.setEquivBalance(Utils.toDouble(result.getString("eb")));
		tb.setTradeBalance(Utils.toDouble(result.getString("tb")));
		tb.setMarginAmount(Utils.toDouble(result.getString("m")));
		tb.setUnrealizedProfitLoss(Utils.toDouble(result.getString("n")));
		tb.setBasisCost(Utils.toDouble(result.getString("c")));
		tb.setCurrentValuation(Utils.toDouble(result.getString("v")));
		tb.setEquity(Utils.toDouble(result.getString("e")));
		tb.setFreeMargin(Utils.toDouble(result.getString("mf")));

		// "ml" sometimes is not present
		if(result.containsKey("ml")) {
			tb.setMarginLevel(Utils.toDouble(result.getString("ml")));
		}

		return tb;
	}






	private static List<Integer> parseJsonIntArray(JsonObject jsonObj, String key) {
		return Utils.map(jsonArrayToList(jsonObj, key), Integer::parseInt);
	}

	private static List<FeeSchedule> parseJsonFeeScheduleArray(JsonObject jsonObj, String key) {
		List<FeeSchedule> toRet = new ArrayList<>();
		JsonArray jsonArray = jsonObj.getJsonArray(key);
		if(jsonArray != null) {
			jsonArray.forEach(jv -> {
				int vol = jv.asJsonArray().getInt(0);
				double perc = jv.asJsonArray().getJsonNumber(1).doubleValue();
				toRet.add(new FeeSchedule(vol, perc));
			});
		}
		return toRet;
	}

	private static List<String> jsonArrayToList(JsonArray jsonArr) {
		List<String> toRet = new ArrayList<>();
		if(jsonArr != null) {
			jsonArr.forEach(jv -> {
				String value = jv.toString();
				if(jv.getValueType() == JsonValue.ValueType.STRING) {
					value = value.replaceAll("^\"", "").replaceAll("\"$", "");
				}
				toRet.add(value);
			});
		}
		return toRet;
	}
	private static List<String> jsonArrayToList(JsonObject jsonObj, String key) {
		return jsonArrayToList(jsonObj.getJsonArray(key));
	}

	private static TickerPrice parseTickerPrice(JsonObject jsonObj, String key) {
		List<String> values = jsonArrayToList(jsonObj, key);
		if(values.size() != 2) 	return null;

		TickerPrice tp = new TickerPrice();
		tp.setPrice(Utils.toDouble(values.get(0)));
		tp.setLotVolume(Utils.toDouble(values.get(1)));
		return tp;
	}
	private static TickerWholePrice parseTickerWholePrice(JsonObject jsonObj, String key) {
		List<String> values = jsonArrayToList(jsonObj, key);
		if(values.size() != 3) 	return null;

		TickerWholePrice twp = new TickerWholePrice();
		twp.setPrice(Utils.toDouble(values.get(0)));
		twp.setWholeLotVolume(Integer.parseInt(values.get(1)));
		twp.setLotVolume(Utils.toDouble(values.get(2)));
		return twp;
	}
	private static TickerVolume parseTickerVolume(JsonObject jsonObj, String key) {
		List<String> values = jsonArrayToList(jsonObj, key);
		if(values.size() != 2) 	return null;

		TickerVolume tv = new TickerVolume();
		tv.setToday(Utils.toDouble(values.get(0)));
		tv.setLast24Hours(Utils.toDouble(values.get(1)));
		return tv;
	}

}
