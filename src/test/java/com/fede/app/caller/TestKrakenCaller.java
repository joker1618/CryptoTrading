package com.fede.app.caller;

import com.fede.app.crypto.trading.manager.IKrakenManager;
import com.fede.app.crypto.trading.manager.KrakenManagerImpl;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.System.out;

/**
 * Created by f.barbano on 15/09/2017.
 */
public class TestKrakenCaller {

	private static final Path BASE_FOLDER = Paths.get("files/data/csv");
	private static IKrakenManager krakenManager;

	@BeforeClass
	public static void beforeClass() {
		krakenManager = KrakenManagerImpl.getInstance();
	}

	@Test
	public void getServerTime() throws IOException {
		long before = System.currentTimeMillis();
		long serverTime = krakenManager.getServerTime() * 1000L;
		long after = System.currentTimeMillis();
		out.println(String.format("Before call:\t%d", before));
		out.println(String.format("Server time:\t%d\t%d", serverTime, (serverTime - before)));
		out.println(String.format("After call: \t%d\t%d", after, (after - serverTime)));
	}

	@Test
	public void testSynchAll() throws IOException {
		krakenManager.synchronizeAssets();
		krakenManager.synchronizeAssetPairs();
//		krakenManager.downloadTickers();
	}

	@Test
	public void testSynchAssets() throws IOException {
		krakenManager.synchronizeAssets();
	}

	@Test
	public void testSynchAssetPairs() throws IOException {
		krakenManager.synchronizeAssetPairs();
	}

	@Test
	public void testDownloadTickers() throws IOException {
		krakenManager.downloadTickers();
	}

	@Test
	public void testDownloadOHLC() throws IOException {
		List<String> pairNames = Arrays.asList("BCHUSD");
//		List<String> pairNames = Arrays.asList("BCHEUR", "BCHUSD", "BCHXBT", "DASHEUR", "DASHUSD", "DASHXBT", "EOSETH", "EOSXBT", "GNOETH", "GNOXBT");
//		List<String> pairNames = new ArrayList<>();
//		pairNames.addAll(krakenManager.synchronizeAssetPairs().keySet());

		long start = System.currentTimeMillis();
		AtomicInteger count = new AtomicInteger(0);
		pairNames.parallelStream().forEach(pairName -> {
//		pairNames.forEach(pairName -> {
			if(!pairName.endsWith(".d")) {
				out.println("Getting OHLC for " + pairName);
				krakenManager.downloadOHLCData(pairName);
				count.incrementAndGet();
			}
		});
		long end = System.currentTimeMillis();
		out.println("Asset pairs: " + count.get());
		out.println("Elapsed:     " + ((end - start) / 1000));

	}

	@Test
	public void testDownloadTrades() throws IOException {
		List<String> pairNames = Arrays.asList("BCHUSD");
//		List<String> pairNames = Arrays.asList("BCHEUR", "BCHUSD", "BCHXBT", "DASHEUR", "DASHUSD", "DASHXBT", "EOSETH", "EOSXBT", "GNOETH", "GNOXBT");
//		List<String> pairNames = new ArrayList<>();
//		pairNames.addAll(krakenManager.synchronizeAssetPairs().keySet());

		long start = System.currentTimeMillis();
		AtomicInteger count = new AtomicInteger(0);
		pairNames.parallelStream().forEach(pairName -> {
//		pairNames.forEach(pairName -> {
			if(!pairName.endsWith(".d")) {
				out.println("Getting Trades for " + pairName);
				krakenManager.downloadTradesData(pairName);
				count.incrementAndGet();
			}
		});
		long end = System.currentTimeMillis();
		out.println("Asset pairs: " + count.get());
		out.println("Elapsed:     " + ((end - start) / 1000));

	}

	@Test
	public void testDownloadSpreads() throws IOException {
		List<String> pairNames = Arrays.asList("BCHUSD");
//		List<String> pairNames = Arrays.asList("BCHEUR", "BCHUSD", "BCHXBT", "DASHEUR", "DASHUSD", "DASHXBT", "EOSETH", "EOSXBT", "GNOETH", "GNOXBT");
//		List<String> pairNames = new ArrayList<>();
//		pairNames.addAll(krakenManager.synchronizeAssetPairs().keySet());

		long start = System.currentTimeMillis();
		AtomicInteger count = new AtomicInteger(0);
		pairNames.parallelStream().forEach(pairName -> {
//		pairNames.forEach(pairName -> {
			if(!pairName.endsWith(".d")) {
				out.println("Getting Spreads for " + pairName);
				krakenManager.downloadSpreadsData(pairName);
				count.incrementAndGet();
			}
		});
		long end = System.currentTimeMillis();
		out.println("Asset pairs: " + count.get());
		out.println("Elapsed:     " + ((end - start) / 1000));

	}


}
