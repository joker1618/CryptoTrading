package com.fede.app.caller;

import com.fede.app.crypto.trading.manager.IKrakenManager;
import com.fede.app.crypto.trading.manager.KrakenManagerImpl;
import com.fede.app.crypto.trading.util.Utils;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

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
		long serverTime = krakenManager.getServerTime();
		out.println(String.format("Server time:\t%d", serverTime));
		out.println(String.format("Date:\t\t%s", Utils.toString(serverTime, "yyyyMMddHHmmss")));
	}

	@Test
	public void testSynchAll() throws IOException {
		krakenManager.synchronizeAssets();
		krakenManager.synchronizeAssetPairs();
		krakenManager.downloadTickers();
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

}
