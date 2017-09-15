package com.fede.app.crypto.trading.parser;

import com.fede.app.crypto.trading.model.Asset;
import com.fede.app.crypto.trading.model.AssetPair;
import com.fede.app.crypto.trading.model.Ticker;
import com.fede.app.crypto.trading.model.Ticker.TickerPrice;
import com.fede.app.crypto.trading.model.Ticker.TickerVolume;
import com.fede.app.crypto.trading.model.Ticker.TickerWholePrice;
import com.fede.app.crypto.trading.util.StrUtils;
import com.fede.app.crypto.trading.util.Utils;

import java.util.List;
import java.util.stream.Collectors;

import static com.fede.app.crypto.trading.model.AssetPair.FeeSchedule;

/**
 * Created by f.barbano on 15/09/2017.
 */
public class ModelConverter {
	
	public static String assetToString(Asset asset) {
		return String.format("%s|%s|%s|%d|%d",
			asset.getName(),
			asset.getAltName(),
			asset.getAClass(),
			asset.getDecimals(),
			asset.getDisplayDecimals()
		);
	}
	public static Asset stringToAsset(String csvLine) {
		String[] fields = StrUtils.splitAllFields(csvLine, "|");
		Asset asset = new Asset();
		asset.setName(fields[0]);
		asset.setAltName(fields[1]);
		asset.setAClass(fields[2]);
		asset.setDecimals(Integer.parseInt(fields[3]));
		asset.setDisplayDecimals(Integer.parseInt(fields[4]));
		return asset;
	}

	
	public static String assetPairToString(AssetPair assetPair) {
		return String.format("%s|%s|%s|%s|%s|%s|%s|%d|%d|%d|%s|%s|%s|%s|%s|%d|%d",
			assetPair.getPairName(),
			assetPair.getAltName(),
			assetPair.getAClassBase(),
			assetPair.getBase(),
			assetPair.getAClassQuote(),
			assetPair.getQuote(),
			assetPair.getLot(),
			assetPair.getPairDecimals(),
			assetPair.getLotDecimals(),
			assetPair.getLotMultiplier(),
			Utils.join(assetPair.getLeverageBuy()), 
			Utils.join(assetPair.getLeverageSell()),
			feeSchedulesToString(assetPair.getFees()),
			feeSchedulesToString(assetPair.getFeesMaker()),
			assetPair.getFeeVolumeCurrency(),
			assetPair.getMarginCall(),
			assetPair.getMarginStop()
		);
	}
	public static AssetPair stringToAssetPair(String csvLine) {
		String[] fields = StrUtils.splitAllFields(csvLine, "|");
		AssetPair ap = new AssetPair();
		ap.setPairName(fields[0]);
		ap.setAltName(fields[1]);
		ap.setAClassBase(fields[2]);
		ap.setBase(fields[3]);
		ap.setAClassQuote(fields[4]);
		ap.setQuote(fields[5]);
		ap.setLot(fields[6]);
		ap.setPairDecimals(Integer.parseInt(fields[7]));
		ap.setLotDecimals(Integer.parseInt(fields[8]));
		ap.setLotMultiplier(Integer.parseInt(fields[9]));
		ap.setLeverageBuy(stringToIntegers(fields[10]));
		ap.setLeverageSell(stringToIntegers(fields[11]));
		ap.setFees(stringToFeeSchedules(fields[12]));
		ap.setFeesMaker(stringToFeeSchedules(fields[13]));
		ap.setFeeVolumeCurrency(fields[14]);
		ap.setMarginCall(Integer.parseInt(fields[15]));
		ap.setMarginStop(Integer.parseInt(fields[16]));
		return ap;
	}


	public static String tickerToString(Ticker ticker) {
		return String.format("%s|%s|%s|%s|%s|%s|%s|%s|%s|%s",
			ticker.getPairName(),
			tickerWholePriceToString(ticker.getAsk()),
			tickerWholePriceToString(ticker.getBid()),
			tickerPriceToString(ticker.getLastTradeClosed()),
			tickerVolumeToString(ticker.getVolume()),
			tickerVolumeToString(ticker.getWeightedAverageVolume()),
			tickerVolumeToString(ticker.getTradesNumber()),
			tickerVolumeToString(ticker.getLow()),
			tickerVolumeToString(ticker.getHigh()),
			Utils.toString(ticker.getTodayOpeningPrice())
		);
	}
	public static Ticker stringToTicker(String csvLine) {
		String[] split = StrUtils.splitAllFields(csvLine, "|", true);
		Ticker ticker = new Ticker();
		ticker.setPairName(split[0]);
		ticker.setAsk(stringToTickerWholePrice(split[1]));
		ticker.setBid(stringToTickerWholePrice(split[2]));
		ticker.setLastTradeClosed(stringToTickerPrice(split[3]));
		ticker.setVolume(stringToTickerVolume(split[4]));
		ticker.setWeightedAverageVolume(stringToTickerVolume(split[5]));
		ticker.setTradesNumber(stringToTickerVolume(split[6]));
		ticker.setLow(stringToTickerVolume(split[7]));
		ticker.setHigh(stringToTickerVolume(split[8]));
		ticker.setTodayOpeningPrice(Utils.toDouble(split[9]));
		return ticker;
	}





	private static String feeSchedulesToString(List<FeeSchedule> feeSchedule) {
		List<String> strings = Utils.map(feeSchedule, fs -> String.format("[%d,%s]", fs.getVolume(), Utils.toString(fs.getPercentFee())));
		return Utils.join(strings, ";");
	}
	private static List<FeeSchedule> stringToFeeSchedules(String str) {
		return StrUtils.splitFieldsList(str, ";", true).stream()
				   .map(s -> s.substring(1, s.length()-1))
				   .map(s -> {
					   String[] split = s.split(",");
					   return new FeeSchedule(Integer.parseInt(split[0]), Utils.toDouble(split[1]));
				   }).collect(Collectors.toList());
	}

	private static List<Integer> stringToIntegers(String str) {
		return Utils.map(StrUtils.splitFieldsList(str, ",", true), Integer::parseInt);
	}

	private static String tickerPriceToString(TickerPrice tp) {
		return String.format("%s,%s",
			Utils.toString(tp.getPrice()),
			Utils.toString(tp.getLotVolume())
		);
	}
	private static TickerPrice stringToTickerPrice(String str) {
		String[] split = StrUtils.splitAllFields(str, ",", true);
		TickerPrice tp = new TickerPrice();
		tp.setPrice(Utils.toDouble(split[0]));
		tp.setLotVolume(Utils.toDouble(split[1]));
		return tp;
	}

	private static String tickerWholePriceToString(TickerWholePrice twp) {
		return String.format("%s,%d,%s",
			Utils.toString(twp.getPrice()),
			twp.getWholeLotVolume(),
			Utils.toString(twp.getLotVolume())
		);
	}
	private static TickerWholePrice stringToTickerWholePrice(String str) {
		String[] split = StrUtils.splitAllFields(str, ",", true);
		TickerWholePrice twp = new TickerWholePrice();
		twp.setPrice(Utils.toDouble(split[0]));
		twp.setWholeLotVolume(Integer.parseInt(split[1]));
		twp.setLotVolume(Utils.toDouble(split[2]));
		return twp;
	}

	private static String tickerVolumeToString(TickerVolume tv) {
		return String.format("%s,%s",
			Utils.toString(tv.getToday()),
			Utils.toString(tv.getLast24Hours())
		);
	}
	private static TickerVolume stringToTickerVolume(String str) {
		String[] split = StrUtils.splitAllFields(str, ",", true);
		TickerVolume tv = new TickerVolume();
		tv.setToday(Utils.toDouble(split[0]));
		tv.setLast24Hours(Utils.toDouble(split[1]));
		return tv;
	}
}
