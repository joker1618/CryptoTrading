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
import java.util.HashMap;
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
	private static final String FILENAME_OHLC = "OHLC/OHLC_@PAIR_NAME@.csv";
	private static final String FILENAME_OHLC_LAST = "OHLC/OHLC_@PAIR_NAME@.last";
	private static final String FILENAME_TRADES = "trades/trades_@PAIR_NAME@.csv";
	private static final String FILENAME_TRADES_LAST = "trades/trades_@PAIR_NAME@.last";
	private static final String FILENAME_SPREADS = "spread/spread_@PAIR_NAME@.csv";
	private static final String FILENAME_SPREADS_LAST = "spread/spread_@PAIR_NAME@.last";

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
		FileUtils.writeFile(outPath, lines, ENCODING, true);
	}

	@Override
	public List<Asset> readAssets() throws IOException {
		List<Asset> toRet = new ArrayList<>();
		Path outPath = baseFolder.resolve(FILENAME_ASSETS);
		if(Files.exists(outPath)) {
			List<String> lines = Files.readAllLines(outPath, ENCODING);
			toRet = Utils.map(lines, ModelConverter::stringToAsset);
		}
		return toRet;
	}

	@Override
	public void persistAssetPairs(List<AssetPair> assetPairs) throws IOException {
		Path outPath = baseFolder.resolve(FILENAME_ASSET_PAIRS);
		List<String> lines = Utils.map(assetPairs, ModelConverter::assetPairToString);
		FileUtils.backupIfExists(outPath);
		FileUtils.writeFile(outPath, lines, ENCODING, true);
	}

	@Override
	public List<AssetPair> readAssetPairs() throws IOException {
		List<AssetPair> toRet = new ArrayList<>();
		Path outPath = baseFolder.resolve(FILENAME_ASSET_PAIRS);
		if(Files.exists(outPath)) {
			List<String> lines = Files.readAllLines(outPath, ENCODING);
			toRet = Utils.map(lines, ModelConverter::stringToAssetPair);
		}
		return toRet;
	}

	@Override
	public void persistTickers(List<Ticker> tickers) throws IOException {
		for(Ticker ticker : tickers) {
			Path outPath = getPath(FILENAME_TICKERS, ticker.getPairName());
			String line = ModelConverter.tickerToString(ticker);
			FileUtils.appendToFile(outPath, line, ENCODING, true);
		}
	}

	@Override
	public Map<String, List<Ticker>> readTickers(List<String> pairNames) throws IOException {
		Map<String, List<Ticker>> toRet = new HashMap<>();
		for(String pairName : pairNames) {
			Path path = getPath(FILENAME_TICKERS, pairName);
			List<Ticker> list = new ArrayList<>();
			if(Files.exists(path)) {
				List<String> lines = Files.readAllLines(path, ENCODING);
				list = Utils.map(lines, ModelConverter::stringToTicker);
			}
			toRet.put(pairName, list);
		}
		return toRet;
	}

	@Override
	public void persistOHLCs(List<OHLC> ohlcList) throws IOException {
		Map<String, List<OHLC>> ohlcMap = Utils.toMap(ohlcList, OHLC::getPairName);
		for(Map.Entry<String, List<OHLC>> entry : ohlcMap.entrySet()) {
			Path outPath = getPath(FILENAME_OHLC, entry.getKey());
			List<String> lines = Utils.map(entry.getValue(), ModelConverter::ohlcToString);
			FileUtils.appendToFile(outPath, lines, ENCODING);
		}
	}

	@Override
	public void persistOHLCLast(String pairName, long last) throws IOException {
		Path lastPath = getPath(FILENAME_OHLC_LAST, pairName);
		FileUtils.writeFile(lastPath, String.valueOf(last), ENCODING, true);
	}

	@Override
	public List<OHLC> readOHLCs(String pairName) throws IOException {
		List<OHLC> toRet = new ArrayList<>();
		Path outPath = getPath(FILENAME_OHLC, pairName);
		if(Files.exists(outPath)) {
			List<String> lines = Files.readAllLines(outPath);
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
	public void persistTrades(List<Trade> tradeList) throws IOException {
		Map<String, List<Trade>> tradeMap = Utils.toMap(tradeList, Trade::getPairName);
		for(Map.Entry<String, List<Trade>> entry : tradeMap.entrySet()) {
			Path outPath = getPath(FILENAME_TRADES, entry.getKey());
			List<String> lines = Utils.map(entry.getValue(), ModelConverter::tradeToString);
			FileUtils.appendToFile(outPath, lines, ENCODING);
		}
	}

	@Override
	public void persistTradeLast(String pairName, long last) throws IOException {
		Path lastPath = getPath(FILENAME_TRADES_LAST, pairName);
		FileUtils.writeFile(lastPath, String.valueOf(last), ENCODING, true);
	}

	@Override
	public List<Trade> readTrades(String pairName) throws IOException {
		List<Trade> toRet = new ArrayList<>();
		Path outPath = getPath(FILENAME_TRADES, pairName);
		if(Files.exists(outPath)) {
			List<String> lines = Files.readAllLines(outPath);
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
	public void persistSpreads(List<Spread> spreadList) throws IOException {
		Map<String, List<Spread>> tradeMap = Utils.toMap(spreadList, Spread::getPairName);
		for(Map.Entry<String, List<Spread>> entry : tradeMap.entrySet()) {
			Path outPath = getPath(FILENAME_SPREADS, entry.getKey());
			List<String> lines = Utils.map(entry.getValue(), ModelConverter::spreadToString);
			FileUtils.appendToFile(outPath, lines, ENCODING);
		}
	}

	@Override
	public void persistSpreadLast(String pairName, long last) throws IOException {
		Path lastPath = getPath(FILENAME_SPREADS_LAST, pairName);
		FileUtils.writeFile(lastPath, String.valueOf(last), ENCODING, true);
	}

	@Override
	public List<Spread> readSpreads(String pairName) throws IOException {
		List<Spread> toRet = new ArrayList<>();
		Path outPath = getPath(FILENAME_SPREADS, pairName);
		if(Files.exists(outPath)) {
			List<String> lines = Files.readAllLines(outPath);
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


	private Path getPath(String filename, String pairName) {
		filename = filename.replace(PH_PAIR_NAME, pairName);
		return baseFolder.resolve(filename);
	}
}
