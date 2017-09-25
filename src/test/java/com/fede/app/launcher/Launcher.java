package com.fede.app.launcher;

import com.fede.app.crypto.trading.manager.IKrakenManager;
import com.fede.app.crypto.trading.manager.KrakenManagerImpl;
import com.fede.app.crypto.trading.model.ClosedOrder;
import com.fede.app.crypto.trading.model.OpenOrder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;
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
		out.println("Downloading Tickers...");
		krakenManager.downloadTickers();
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
		out.println("Downloading Ohlc...");
		assetPairs.parallelStream().forEach(krakenManager::downloadOHLCData);
		out.println("Downloading RecentTrades...");
		assetPairs.parallelStream().forEach(krakenManager::downloadTradesData);
		out.println("Downloading Spreads...");
		assetPairs.parallelStream().forEach(krakenManager::downloadSpreadsData);
		long end = System.currentTimeMillis();
		out.println(String.format("Elapsed: %d sec", ((end-start)/1000)));
	}

	@Test
	public void download_AccountBalance_TradeBalance() {
		long start = System.currentTimeMillis();
//		Set<String> assetPairs = krakenManager.synchronizeAssetPairs().keySet();
		out.println("Downloading AccountBalance...");
		krakenManager.getAccountBalance();
		out.println("Downloading TradeBalance...");
		krakenManager.getTradeBalance("ZEUR");
		long end = System.currentTimeMillis();
		out.println(String.format("Elapsed: %d sec", ((end-start)/1000)));
	}

	@Test
	public void download_Open_Orders() {
		long start = System.currentTimeMillis();
		List<OpenOrder> openOrders = krakenManager.getOpenOrders();
		long end = System.currentTimeMillis();
		openOrders.forEach(openOrder -> {
			String str = ToStringBuilder.reflectionToString(openOrder, ToStringStyle.MULTI_LINE_STYLE);
			out.println(str);
		});
		out.println(String.format("Elapsed: %d sec", ((end-start)/1000)));
	}

	@Test
	public void download_Closed_Orders() {
		long start = System.currentTimeMillis();
		List<ClosedOrder> closedOrders = krakenManager.getClosedOrders();
		long end = System.currentTimeMillis();
		closedOrders.forEach(openOrder -> {
			String str = ToStringBuilder.reflectionToString(openOrder, ToStringStyle.MULTI_LINE_STYLE);
			out.println(str);
		});
		out.println(String.format("Elapsed: %d sec", ((end-start)/1000)));
	}
}
