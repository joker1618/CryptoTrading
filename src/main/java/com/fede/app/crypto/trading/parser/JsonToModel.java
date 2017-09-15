package com.fede.app.crypto.trading.parser;

import com.fede.app.crypto.trading.model.Asset;
import com.fede.app.crypto.trading.model.AssetPair;
import com.fede.app.crypto.trading.model.Ticker;
import static com.fede.app.crypto.trading.model.Ticker.*;
import com.fede.app.crypto.trading.util.Utils;

import static com.fede.app.crypto.trading.model.AssetPair.FeeSchedule;

import javax.json.*;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
			asset.setName(entry.getKey());
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

	public List<Ticker> parseTickers() {
		if(containsErrors())	return null;

		List<Ticker> toRet = new ArrayList<>();
		for(Map.Entry<String, JsonValue> entry : result.entrySet()) {
			JsonObject jt = entry.getValue().asJsonObject();
			Ticker ticker = new Ticker();
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

	private static List<String> jsonArrayToList(JsonObject jsonObj, String key) {
		List<String> toRet = new ArrayList<>();
		JsonArray jarr = jsonObj.getJsonArray(key);
		if(jarr != null) {
			jarr.forEach(jv -> toRet.add(jv.toString().replaceAll("^\"", "").replaceAll("\"$", "")));
		}
		return toRet;
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
