package com.fede.app.crypto.trading.facade;

import com.fede.app.crypto.trading.model.*;
import com.fede.app.crypto.trading.parser.ModelConverter;
import com.fede.app.crypto.trading.util.FileUtils;
import com.fede.app.crypto.trading.util.Utils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Created by f.barbano on 15/09/2017.
 */
public class KrakenProviderImpl implements IKrakenProvider {

	private static final Charset ENCODING = Charset.forName("UTF-8");

	private static final String FILENAME_ASSETS = "assets.csv";
	private static final String FILENAME_ASSET_PAIRS = "assetPairs.csv";
	private static final String FILENAME_TICKERS = "tickers/tickers_@PAIR_NAME@.csv";
	private static final String FILENAME_ORDERS = "orders/orders_@PAIR_NAME@.csv";
	private static final String FILENAME_OHLC = "Ohlc/OHLC_@PAIR_NAME@.csv";
	private static final String FILENAME_OHLC_LAST = "Ohlc/OHLC_@PAIR_NAME@.last";
	private static final String FILENAME_TRADES = "trades/trades_@PAIR_NAME@.csv";
	private static final String FILENAME_TRADES_LAST = "trades/trades_@PAIR_NAME@.last";
	private static final String FILENAME_SPREADS = "spread/spread_@PAIR_NAME@.csv";
	private static final String FILENAME_SPREADS_LAST = "spread/spread_@PAIR_NAME@.last";
	private static final String FILENAME_ACCOUNT_BALANCE = "balance/accountBalance.csv";
	private static final String FILENAME_TRADE_BALANCE = "balance/tradeBalance.csv";

	private static final String HEADER_ASSETS;
	private static final String HEADER_ASSET_PAIRS;
	private static final String HEADER_TICKERS;
	private static final String HEADER_ORDERS;
	private static final String HEADER_OHLC;
	private static final String HEADER_TRADES;
	private static final String HEADER_SPREAD;
	private static final String HEADER_ACCOUNT_BALANCE;
	private static final String HEADER_TRADE_BALANCE;
	static {
		HEADER_ASSETS = "ASSET_NAME|ASSET_CLASS|ALT_NAME|DECIMALS|DISPLAY_DECIMALS";

		StringBuilder sb = new StringBuilder();
		sb.append("PAIR_NAME|ALT_NAME|CLASS_BASE|BASE|CLASS_QUOTE|QUOTE|LOT|");
		sb.append("PAIR_DECIMALS|LOT_DECIMALS|LOT_MULTIPLIER|LEVERAGE_BUY|LEVERAGE_SELL|");
		sb.append("FEES|FEES_MAKER|FEE_VOLUME_CURRENCY|MARGIN_CALL|MARGIN_STOP");
		HEADER_ASSET_PAIRS = sb.toString();

		sb = new StringBuilder();
		sb.append("CALL_TIME|PAIR_NAME|ASK|BID|LAST_TRADE_CLOSED|VOLUME|");
		sb.append("WEIGHTED_AVERAGE_VOLUME|TRADES_NUMBER|LOW|HIGH|TODAY_OPENING_PRICE");
		HEADER_TICKERS = sb.toString();

		HEADER_ORDERS = "PAIR_NAME|ASK/BID|PRICE|VOLUME|TIMESTAMP";

		HEADER_OHLC = "PAIR_NAME|TIME|OPEN|HIGH|LOW|CLOSE|VWRAP|VOLUME|COUNT";

		HEADER_TRADES = "PAIR_NAME|PRICE|VOLUME|TIME|ACTION_TYPE|ORDER_TYPE|MISCELLANEOUS";

		HEADER_SPREAD = "PAIR_NAME|TIME|BID|ASK";

		HEADER_ACCOUNT_BALANCE = "CALL_TIME|ASSET|BALANCE";

		sb = new StringBuilder();
		sb.append("CALL_TIME|EQUIVALENT_BALANCE|TRADE_BALANCE|MARGIN_AMOUNT|UNREALIZED_PROFIT_LOSS|");
		sb.append("BASIS_COST|CURRENT_VALUATION|EQUITY|FREE_MARGIN|MARGIN_LEVEL");
		HEADER_TRADE_BALANCE = sb.toString();

	}

	private static final String PH_PAIR_NAME = "@PAIR_NAME@";

	private final Path baseFolder;

	public KrakenProviderImpl(Path baseFolder) {
		this.baseFolder = baseFolder;
	}

	@Override
	public void persistAssets(List<Asset> assetsInfo) throws IOException {
		Path outPath = baseFolder.resolve(FILENAME_ASSETS);
		List<String> lines = Utils.map(assetsInfo, ModelConverter::assetToString);
		FileUtils.backupIfExists(outPath);
		appendData(outPath, HEADER_ASSETS, lines);
	}

	@Override
	public List<Asset> readAssets() throws IOException {
		List<Asset> toRet = new ArrayList<>();
		Path outPath = baseFolder.resolve(FILENAME_ASSETS);
		if(Files.exists(outPath)) {
			List<String> lines = Files.readAllLines(outPath, ENCODING);
			lines.removeIf(HEADER_ASSETS::equals);
			toRet = Utils.map(lines, ModelConverter::stringToAsset);
		}
		return toRet;
	}

	@Override
	public void persistAssetPairs(List<AssetPair> assetPairs) throws IOException {
		Path outPath = baseFolder.resolve(FILENAME_ASSET_PAIRS);
		List<String> lines = Utils.map(assetPairs, ModelConverter::assetPairToString);
		FileUtils.backupIfExists(outPath);
		appendData(outPath, HEADER_ASSET_PAIRS, lines);
	}

	@Override
	public List<AssetPair> readAssetPairs() throws IOException {
		List<AssetPair> toRet = new ArrayList<>();
		Path outPath = baseFolder.resolve(FILENAME_ASSET_PAIRS);
		if(Files.exists(outPath)) {
			List<String> lines = Files.readAllLines(outPath, ENCODING);
			lines.removeIf(HEADER_ASSET_PAIRS::equals);
			toRet = Utils.map(lines, ModelConverter::stringToAssetPair);
		}
		return toRet;
	}

	@Override
	public void persistTickers(List<Ticker> tickers) throws IOException {
		Map<String, List<Ticker>> map = Utils.toMap(tickers, Ticker::getPairName);
		for(Map.Entry<String, List<Ticker>> entry : map.entrySet()) {
			Path outPath = getPath(FILENAME_TICKERS, entry.getKey());
			List<String> lines = Utils.map(entry.getValue(), ModelConverter::tickerToString);
			appendData(outPath, HEADER_TICKERS, lines);
		}
	}

	@Override
	public List<Ticker> readTickers(String pairName) throws IOException {
		Path path = getPath(FILENAME_TICKERS, pairName);
		List<Ticker> tickerList = new ArrayList<>();
		if(Files.exists(path)) {
			List<String> lines = Files.readAllLines(path, ENCODING);
			lines.removeIf(HEADER_TICKERS::equals);
			tickerList = Utils.map(lines, ModelConverter::stringToTicker);
		}
		return tickerList;
	}

	@Override
	public void persistOrderBook(List<MarketOrder> marketOrders) throws IOException {
		Map<String, List<MarketOrder>> map = Utils.toMap(marketOrders, MarketOrder::getPairName);
		for(Map.Entry<String, List<MarketOrder>> entry : map.entrySet()) {
			Path outPath = getPath(FILENAME_ORDERS, entry.getKey());
			List<String> lines = Utils.map(entry.getValue(), ModelConverter::orderToString);
			appendData(outPath, HEADER_ORDERS, lines);
		}
	}

	@Override
	public List<MarketOrder> readOrderBook(String pairName) throws IOException {
		Path path = getPath(FILENAME_ORDERS, pairName);
		List<MarketOrder> orderList = new ArrayList<>();
		if(Files.exists(path)) {
			List<String> lines = Files.readAllLines(path, ENCODING);
			lines.removeIf(HEADER_ORDERS::equals);
			orderList = Utils.map(lines, ModelConverter::stringToOrder);
		}
		return orderList;
	}

	@Override
	public void persistOHLCs(List<Ohlc> ohlcList) throws IOException {
		Map<String, List<Ohlc>> ohlcMap = Utils.toMap(ohlcList, Ohlc::getPairName);
		for(Map.Entry<String, List<Ohlc>> entry : ohlcMap.entrySet()) {
			Path outPath = getPath(FILENAME_OHLC, entry.getKey());
			List<String> lines = Utils.map(entry.getValue(), ModelConverter::ohlcToString);
			appendData(outPath, HEADER_OHLC, lines);
		}
	}

	@Override
	public void persistOHLCLast(String pairName, long last) throws IOException {
		Path lastPath = getPath(FILENAME_OHLC_LAST, pairName);
		FileUtils.writeFile(lastPath, String.valueOf(last), ENCODING, true);
	}

	@Override
	public List<Ohlc> readOHLCs(String pairName) throws IOException {
		List<Ohlc> toRet = new ArrayList<>();
		Path outPath = getPath(FILENAME_OHLC, pairName);
		if(Files.exists(outPath)) {
			List<String> lines = Files.readAllLines(outPath);
			lines.removeIf(HEADER_OHLC::equals);
			toRet = Utils.map(lines, ModelConverter::stringToOHLC);
		}
		return toRet;
	}

	@Override
	public long readOHLCLast(String pairName) throws IOException {
		Path path = getPath(FILENAME_OHLC_LAST, pairName);
		long last = 0L;
		if(Files.exists(path)) {
			String strLast = Files.readAllLines(path, ENCODING).get(0).trim();
			last = Long.parseLong(strLast);
		}
		return last;
	}

	@Override
	public void persistTrades(List<RecentTrade> recentTradeList) throws IOException {
		Map<String, List<RecentTrade>> tradeMap = Utils.toMap(recentTradeList, RecentTrade::getPairName);
		for(Map.Entry<String, List<RecentTrade>> entry : tradeMap.entrySet()) {
			Path outPath = getPath(FILENAME_TRADES, entry.getKey());
			List<String> lines = Utils.map(entry.getValue(), ModelConverter::tradeToString);
			appendData(outPath, HEADER_TRADES, lines);
		}
	}

	@Override
	public void persistTradeLast(String pairName, long last) throws IOException {
		Path lastPath = getPath(FILENAME_TRADES_LAST, pairName);
		FileUtils.writeFile(lastPath, String.valueOf(last), ENCODING, true);
	}

	@Override
	public List<RecentTrade> readTrades(String pairName) throws IOException {
		List<RecentTrade> toRet = new ArrayList<>();
		Path outPath = getPath(FILENAME_TRADES, pairName);
		if(Files.exists(outPath)) {
			List<String> lines = Files.readAllLines(outPath);
			lines.removeIf(HEADER_TRADES::equals);
			toRet = Utils.map(lines, ModelConverter::stringToTrade);
		}
		return toRet;
	}

	@Override
	public long readTradeLast(String pairName) throws IOException {
		Path path = getPath(FILENAME_TRADES_LAST, pairName);
		long last = 0L;
		if(Files.exists(path)) {
			String strLast = Files.readAllLines(path, ENCODING).get(0).trim();
			last = Long.parseLong(strLast);
		}
		return last;
	}

	@Override
	public void persistSpreads(List<SpreadData> spreadDataList) throws IOException {
		Map<String, List<SpreadData>> tradeMap = Utils.toMap(spreadDataList, SpreadData::getPairName);
		for(Map.Entry<String, List<SpreadData>> entry : tradeMap.entrySet()) {
			Path outPath = getPath(FILENAME_SPREADS, entry.getKey());
			List<String> lines = Utils.map(entry.getValue(), ModelConverter::spreadToString);
			appendData(outPath, HEADER_SPREAD, lines);
		}
	}

	@Override
	public void persistSpreadLast(String pairName, long last) throws IOException {
		Path lastPath = getPath(FILENAME_SPREADS_LAST, pairName);
		FileUtils.writeFile(lastPath, String.valueOf(last), ENCODING, true);
	}

	@Override
	public List<SpreadData> readSpreads(String pairName) throws IOException {
		List<SpreadData> toRet = new ArrayList<>();
		Path outPath = getPath(FILENAME_SPREADS, pairName);
		if(Files.exists(outPath)) {
			List<String> lines = Files.readAllLines(outPath);
			lines.removeIf(HEADER_SPREAD::equals);
			toRet = Utils.map(lines, ModelConverter::stringToSpread);
		}
		return toRet;
	}

	@Override
	public long readSpreadLast(String pairName) throws IOException {
		Path path = getPath(FILENAME_SPREADS_LAST, pairName);
		long last = 0L;
		if(Files.exists(path)) {
			String strLast = Files.readAllLines(path, ENCODING).get(0).trim();
			last = Long.parseLong(strLast);
		}
		return last;
	}

	@Override
	public void persistAccountBalance(List<AccountBalance> accountBalances) throws IOException {
		Path outPath = getPath(FILENAME_ACCOUNT_BALANCE, "");
		List<String> lines = Utils.map(accountBalances, ModelConverter::accountBalanceToString);
		appendData(outPath, HEADER_ACCOUNT_BALANCE, lines);
	}

	@Override
	public List<AccountBalance> readAccountBalance() throws IOException {
		Path path = getPath(FILENAME_ACCOUNT_BALANCE, "");
		List<AccountBalance> abList = new ArrayList<>();
		if(Files.exists(path)) {
			List<String> lines = Files.readAllLines(path, ENCODING);
			lines.removeIf(HEADER_ACCOUNT_BALANCE::equals);
			abList = Utils.map(lines, ModelConverter::stringToAccountBalance);
		}
		return abList;
	}

	@Override
	public void persistTradeBalance(TradeBalance tradeBalance) throws IOException {
		Path outPath = getPath(FILENAME_TRADE_BALANCE, "");
		List<String> lines = Collections.singletonList(ModelConverter.tradeBalanceToString(tradeBalance));
		appendData(outPath, HEADER_TRADE_BALANCE, lines);
	}

	@Override
	public List<TradeBalance> readTradeBalances() throws IOException {
		Path path = getPath(FILENAME_TRADE_BALANCE, "");
		List<TradeBalance> tbList = new ArrayList<>();
		if(Files.exists(path)) {
			List<String> lines = Files.readAllLines(path, ENCODING);
			lines.removeIf(HEADER_TRADE_BALANCE::equals);
			tbList = Utils.map(lines, ModelConverter::stringToTradeBalance);
		}
		return tbList;
	}


	private void appendData(Path filePath, String header, List<String> lines) throws IOException {
		if(!lines.isEmpty()) {
			List<String> toWrite = new ArrayList<>(lines);
			if(!Files.exists(filePath)) {
				toWrite.add(0, header);
			}
			FileUtils.appendToFile(filePath, toWrite, ENCODING);
		}
	}
	private Path getPath(String filename, String pairName) {
		filename = filename.replace(PH_PAIR_NAME, pairName);
		return baseFolder.resolve(filename);
	}
}
