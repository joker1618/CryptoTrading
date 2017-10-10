package com.fede.app.crypto.trading.parser;

import com.fede.app.crypto.trading.model._private.AccountBalance;
import com.fede.app.crypto.trading.model._private.TradeBalance;
import com.fede.app.crypto.trading.model._public.*;
import com.fede.app.crypto.trading.model._public.Ticker.TickerPrice;
import com.fede.app.crypto.trading.model._public.Ticker.TickerVolume;
import com.fede.app.crypto.trading.model._public.Ticker.TickerWholePrice;
import com.fede.app.crypto.trading.model.types.OrderAction;
import com.fede.app.crypto.trading.model.types.OrderDirection;
import com.fede.app.crypto.trading.model.types.OrderType;
import com.fede.app.crypto.trading.util.StrUtils;
import com.fede.app.crypto.trading.util.Utils;

import java.util.List;
import java.util.stream.Collectors;

import static com.fede.app.crypto.trading.model._public.AssetPair.FeeSchedule;

/**
 * Created by f.barbano on 15/09/2017.
 */
public class ModelConverter {
	
	public static String assetToString(Asset asset) {
		return String.format("%s|%s|%s|%d|%d",
			asset.getAssetName(),
			asset.getAClass(),
			asset.getAltName(),
			asset.getDecimals(),
			asset.getDisplayDecimals()
		);
	}
	public static Asset stringToAsset(String csvLine) {
		String[] fields = StrUtils.splitAllFields(csvLine, "|");
		Asset asset = new Asset();
		asset.setAssetName(fields[0]);
		asset.setAClass(fields[1]);
		asset.setAltName(fields[2]);
		asset.setDecimals(Integer.parseInt(fields[3]));
		asset.setDisplayDecimals(Integer.parseInt(fields[4]));
		return asset;
	}

	
	public static String assetPairToString(AssetPair assetPair) {
		return String.format("%s|%s|%s|%s|%s|%s|%s|%d|%d|%d|%s|%s|%s|%s|%s|%d|%d",
			assetPair.getPairName(),
			assetPair.getAltName(),
//			assetPair.getAClassBase(),
//			assetPair.getBase(),
//			assetPair.getAClassQuote(),
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
//		ap.setAClassBase(fields[2]);
//		ap.setBase(fields[3]);
//		ap.setAClassQuote(fields[4]);
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
		return String.format("%d|%s|%s|%s|%s|%s|%s|%s|%s|%s|%s",
			ticker.getCallTime(),
			ticker.getPairName(),
			tickerWholePriceToString(ticker.getAsk()),
			tickerWholePriceToString(ticker.getBid()),
			tickerPriceToString(ticker.getLastTradeClosed()),
			tickerVolumeToString(ticker.getVolume()),
			tickerVolumeToString(ticker.getWeightedAverageVolume()),
			tickerVolumeToString(ticker.getTradesNumber()),
			tickerVolumeToString(ticker.getLow()),
			tickerVolumeToString(ticker.getHigh()),
			Utils.toString(ticker.getOpeningPrice())
		);
	}
	public static Ticker stringToTicker(String csvLine) {
		String[] split = StrUtils.splitAllFields(csvLine, "|", true);
		Ticker ticker = new Ticker();
		ticker.setCallTime(Long.parseLong(split[0]));
		ticker.setPairName(split[1]);
		ticker.setAsk(stringToTickerWholePrice(split[2]));
		ticker.setBid(stringToTickerWholePrice(split[3]));
		ticker.setLastTradeClosed(stringToTickerPrice(split[4]));
		ticker.setVolume(stringToTickerVolume(split[5]));
		ticker.setWeightedAverageVolume(stringToTickerVolume(split[6]));
		ticker.setTradesNumber(stringToTickerVolume(split[7]));
		ticker.setLow(stringToTickerVolume(split[8]));
		ticker.setHigh(stringToTickerVolume(split[9]));
		ticker.setOpeningPrice(Utils.toDouble(split[10]));
		return ticker;
	}


	public static String ohlcToString(Ohlc ohlc) {
		return String.format("%s|%d|%s|%s|%s|%s|%s|%s|%d",
			ohlc.getPairName(),
			ohlc.getTime(),
			Utils.toString(ohlc.getOpen()),
			Utils.toString(ohlc.getHigh()),
			Utils.toString(ohlc.getLow()),
			Utils.toString(ohlc.getClose()),
			Utils.toString(ohlc.getVwrap()),
			Utils.toString(ohlc.getVolume()),
			ohlc.getCount()
		);
	}
	public static Ohlc stringToOHLC(String csvLine) {
		String[] split = StrUtils.splitAllFields(csvLine, "|", true);
		Ohlc ohlc = new Ohlc();
		ohlc.setPairName(split[0]);
		ohlc.setTime(Long.parseLong(split[1]));
		ohlc.setOpen(Utils.toDouble(split[2]));
		ohlc.setHigh(Utils.toDouble(split[3]));
		ohlc.setLow(Utils.toDouble(split[4]));
		ohlc.setClose(Utils.toDouble(split[5]));
		ohlc.setVwrap(Utils.toDouble(split[6]));
		ohlc.setVolume(Utils.toDouble(split[7]));
		ohlc.setCount(Long.parseLong(split[8]));
		return ohlc;
	}

	public static String orderToString(MarketOrder marketOrder) {
		return String.format("%s|%s|%s|%s|%d",
			marketOrder.getPairName(),
			marketOrder.getOrderDirection().label(),
			Utils.toString(marketOrder.getPrice()),
			Utils.toString(marketOrder.getVolume()),
			marketOrder.getTimestamp()
		);
	}
	public static MarketOrder stringToOrder(String csvLine) {
		String[] split = StrUtils.splitAllFields(csvLine, "|", true);
		MarketOrder order = new MarketOrder();
		order.setPairName(split[0]);
		order.setOrderDirection(OrderDirection.getByLabel(split[1]));
		order.setPrice(Utils.toDouble(split[2]));
		order.setVolume(Utils.toDouble(split[3]));
		order.setTimestamp(Long.parseLong(split[4]));
		return order;
	}

	public static String tradeToString(RecentTrade recentTrade) {
		return String.format("%s|%s|%s|%d|%s|%s|%s",
			recentTrade.getPairName(),
			Utils.toString(recentTrade.getPrice()),
			Utils.toString(recentTrade.getVolume()),
			recentTrade.getTime(),
			recentTrade.getOrderAction().label(),
			recentTrade.getOrderType().label(),
			recentTrade.getMiscellaneous()
		);
	}
	public static RecentTrade stringToTrade(String csvLine) {
		String[] split = StrUtils.splitAllFields(csvLine, "|", true);
		RecentTrade recentTrade = new RecentTrade();
		recentTrade.setPairName(split[0]);
		recentTrade.setPrice(Utils.toDouble(split[1]));
		recentTrade.setVolume(Utils.toDouble(split[2]));
		recentTrade.setTime(Long.parseLong(split[3]));
		recentTrade.setOrderAction(OrderAction.getByLabel(split[4]));
		recentTrade.setOrderType(OrderType.getByLabel(split[5]));
		recentTrade.setMiscellaneous(split[6]);
		return recentTrade;
	}

	public static String spreadToString(SpreadData spreadData) {
		return String.format("%s|%d|%s|%s",
			spreadData.getPairName(),
			spreadData.getTime(),
			Utils.toString(spreadData.getBid()),
			Utils.toString(spreadData.getAsk())
		);
	}
	public static SpreadData stringToSpread(String csvLine) {
		String[] split = StrUtils.splitAllFields(csvLine, "|", true);
		SpreadData spreadData = new SpreadData();
		spreadData.setPairName(split[0]);
		spreadData.setTime(Long.parseLong(split[1]));
		spreadData.setBid(Utils.toDouble(split[2]));
		spreadData.setAsk(Utils.toDouble(split[3]));
		return spreadData;
	}

	public static String accountBalanceToString(AccountBalance accountBalance) {
		return String.format("%d|%s|%s",
			accountBalance.getCallTime(),
			accountBalance.getAssetName(),
			Utils.toString(accountBalance.getBalance())
		);
	}
	public static AccountBalance stringToAccountBalance(String csvLine) {
		String[] split = StrUtils.splitAllFields(csvLine, "|", true);
		AccountBalance accountBalance = new AccountBalance();
		accountBalance.setCallTime(Long.parseLong(split[0]));
		accountBalance.setAssetName(split[1]);
		accountBalance.setBalance(Utils.toDouble(split[2]));
		return accountBalance;
	}

	public static String tradeBalanceToString(TradeBalance tradeBalance) {
		return String.format("%d|%s|%s|%s|%s|%s|%s|%s|%s|%s",
			tradeBalance.getCallTime(),
			Utils.toString(tradeBalance.getEquivBalance()),
			Utils.toString(tradeBalance.getTradeBalance()),
			Utils.toString(tradeBalance.getMarginAmount()),
			Utils.toString(tradeBalance.getUnrealizedProfitLoss()),
			Utils.toString(tradeBalance.getBasisCost()),
			Utils.toString(tradeBalance.getCurrentValuation()),
			Utils.toString(tradeBalance.getEquity()),
			Utils.toString(tradeBalance.getFreeMargin()),
			Utils.toString(tradeBalance.getMarginLevel())
		);
	}
	public static TradeBalance stringToTradeBalance(String csvLine) {
		String[] split = StrUtils.splitAllFields(csvLine, "|", true);
		TradeBalance tradeBalance = new TradeBalance();
		tradeBalance.setCallTime(Long.parseLong(split[0]));
		tradeBalance.setEquivBalance(Utils.toDouble(split[1]));
		tradeBalance.setTradeBalance(Utils.toDouble(split[2]));
		tradeBalance.setMarginAmount(Utils.toDouble(split[3]));
		tradeBalance.setUnrealizedProfitLoss(Utils.toDouble(split[4]));
		tradeBalance.setBasisCost(Utils.toDouble(split[5]));
		tradeBalance.setCurrentValuation(Utils.toDouble(split[6]));
		tradeBalance.setEquity(Utils.toDouble(split[7]));
		tradeBalance.setFreeMargin(Utils.toDouble(split[8]));
		tradeBalance.setMarginLevel(Utils.toDouble(split[9]));
		return tradeBalance;
	}

	// REVIEW BEFORE HERE




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
