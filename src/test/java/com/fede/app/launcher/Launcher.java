package com.fede.app.launcher;

import com.fede.app.crypto.trading.manager.IKrakenManager;
import com.fede.app.crypto.trading.manager.KrakenManagerImpl;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.Set;

import static java.lang.System.out;

/**
 * Created by f.barbano on 18/09/2017.
 */
public class Launcher {

	private static IKrakenManager krakenManager;

	@BeforeClass
	public static void beforeClass() {
		krakenManager = KrakenManagerImpl.getInstance();
	}

	@Test
	public void synchronize_Assets_AssetPairs() {
		krakenManager.synchronizeAssets();
		krakenManager.synchronizeAssetPairs();
	}

	@Test
	public void download_Tickers_OrderBook() {
		long start = System.currentTimeMillis();
		Set<String> assetPairs = krakenManager.synchronizeAssetPairs().keySet();
		assetPairs.removeIf(s -> s.endsWith(".d"));
//		out.println("Downloading Tickers...");
//		krakenManager.downloadTickers();
		out.println("Downloading OrderBooks...");
		assetPairs.parallelStream().forEach(krakenManager::downloadOrderBook);
		long end = System.currentTimeMillis();
		out.println(String.format("Elapsed: %d sec", ((end-start)/1000)));
	}

	@Test
	public void download_OHLC_RecentTrades_Spreads() {
		long start = System.currentTimeMillis();
		Set<String> assetPairs = krakenManager.synchronizeAssetPairs().keySet();
		assetPairs.removeIf(s -> s.endsWith(".d"));
		out.println("Downloading OHLC...");
		assetPairs.parallelStream().forEach(krakenManager::downloadOHLCData);
		out.println("Downloading RecentTrades...");
		assetPairs.parallelStream().forEach(krakenManager::downloadTradesData);
		out.println("Downloading Spreads...");
		assetPairs.parallelStream().forEach(krakenManager::downloadSpreadsData);
		long end = System.currentTimeMillis();
		out.println(String.format("Elapsed: %d sec", ((end-start)/1000)));
	}
}
