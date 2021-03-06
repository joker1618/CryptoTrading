package com.fede.app.launcher;

import com.fede.app.crypto.trading.common.Const;
import com.fede.app.crypto.trading.dao.IAssetsDao;
import com.fede.app.crypto.trading.dao.impl.AssetsDBDao;
import com.fede.app.crypto.trading.exception.KrakenCallException;
import com.fede.app.crypto.trading.exception.KrakenResponseError;
import com.fede.app.crypto.trading.kraken.IKrakenFacade;
import com.fede.app.crypto.trading.kraken.KrakenFacadeImpl;
import com.fede.app.crypto.trading.model._private.*;
import com.fede.app.crypto.trading.model._public.*;
import com.fede.app.crypto.trading.util.DateUtils;
import com.fede.app.crypto.trading.util.Utils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.apache.commons.lang3.tuple.Pair;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

import static java.lang.System.out;

/**
 * Created by f.barbano on 25/09/2017.
 */
public class SingleCall {

	private static final String JDBC_DRIVER_CLASS = "com.mysql.jdbc.Driver";
	private static final String DB_URL = "jdbc:mysql://localhost:3306/CRYPTO_TRADING";
	private static final String USERNAME = "user";
	private static final String PASSWORD = "user";

	private static IKrakenFacade krakenFacade;
	private static IAssetsDao assetsDao;

	@BeforeClass
	public static void beforeClass() {
		krakenFacade = new KrakenFacadeImpl(Const.KRAKEN_KEY, Const.KRAKEN_SECRET);
		try {
			Class.forName(JDBC_DRIVER_CLASS);
			Connection connection = DriverManager.getConnection(DB_URL, USERNAME, PASSWORD);
			assetsDao = new AssetsDBDao(connection);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Test
	public void testGetServerTime() throws Exception {
		long starttm = System.currentTimeMillis();
		Long serverTime = krakenFacade.getServerTime();
		long endtm = System.currentTimeMillis();
		printOut("Call time:   %d", starttm);
		printOut("Server time: %d", serverTime);
		printOut(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss.SSS").format(DateUtils.fromMillis(serverTime)));
		printOut("\nElapsed: %.3f sec", ((double)(endtm-starttm)/1000));
	}

	@Test
	public void testGetAssetsCallOnly() throws Exception {
		long starttm = System.currentTimeMillis();
		List<Asset> assets = krakenFacade.getAssets();
		long endtm = System.currentTimeMillis();
		printOut(assets, "ASSETS", starttm, endtm);

		List<String> anames = Utils.map(assets, Asset::getAssetName);
		anames.addAll(Utils.map(assets, Asset::getAltName));
		out.println("\nAsset names");
		anames.stream().distinct().sorted().forEach(out::println);

	}

	@Test
	public void testGetAssets() throws Exception {
		long starttm = System.currentTimeMillis();

		List<Asset> assets12 = assetsDao.getAssets();
		List<Asset> assets = krakenFacade.getAssets();

//		assetsDao.updateAssets(starttm, assets);
//
//		List<Asset> a2 = assetsDao.getAssets();

		out.println(assets.equals(assets12));

	}

	@Test
	public void testGetAssetPairs() throws Exception {
		long starttm = System.currentTimeMillis();
		List<AssetPair> assetPairs = krakenFacade.getAssetPairs();
		long endtm = System.currentTimeMillis();
		printOut(assetPairs, "ASSET PAIRS", starttm, endtm);

		List<String> apnames = Utils.map(assetPairs, AssetPair::getPairName);
		apnames.addAll(Utils.map(assetPairs, AssetPair::getAltName));
		out.println("\nAsset pairs names");
		apnames.stream().distinct().sorted().forEach(out::println);

		List<String> apbase = Utils.map(assetPairs, AssetPair::getBase);
		out.println("\nAsset pairs base");
		apbase.stream().distinct().sorted().forEach(out::println);

		List<String> apquote = Utils.map(assetPairs, AssetPair::getQuote);
		out.println("\nAsset pairs quote");
		apquote.stream().distinct().sorted().forEach(out::println);
	}

	@Test
	public void testGetTickers() throws Exception {
		long starttm = System.currentTimeMillis();
		List<String> pairs = Arrays.asList("XXBTZEUR,XXBTZUSD,XXDGXXBT");
		List<Ticker> tickers = krakenFacade.getTickers(pairs);
		long endtm = System.currentTimeMillis();
		printOut(tickers, String.format("TICKERS for %s", tickers), starttm, endtm);
	}

	@Test
	public void testGetOhlc() throws IOException {
		long starttm = System.currentTimeMillis();
		String pair = "XXBTZEUR";
		Pair<Long, List<Ohlc>> ohlcs = krakenFacade.getOhlcs(pair, 0L);
		long endtm = System.currentTimeMillis();
		printOut(ohlcs.getValue(), String.format("OHLC for %s\nLast: %d", pair, ohlcs.getKey()), starttm, endtm);
	}

	@Test
	public void testGetOrderBook() throws IOException {
		long starttm = System.currentTimeMillis();
		String pair = "XXBTZEUR";
		List<MarketOrder> orderBook = krakenFacade.getOrderBook(pair);
		long endtm = System.currentTimeMillis();
		printOut(orderBook, String.format("OrderBook for %s", pair), starttm, endtm);
	}

	@Test
	public void testGetRecentTrades() throws IOException {
		long starttm = System.currentTimeMillis();
		String pair = "XXBTZEUR";
		Pair<Long, List<RecentTrade>> trades = krakenFacade.getRecentTrades(pair, 0L);
		long endtm = System.currentTimeMillis();
		printOut(trades.getValue(), String.format("Recent trades for %s\nLast: %d", pair, trades.getKey()), starttm, endtm);
	}

	@Test
	public void testGetSpreadData() throws Exception {
		long starttm = System.currentTimeMillis();
		String pair = "XXBTZEUR";
		Pair<Long, List<SpreadData>> spreads = krakenFacade.getSpreadData(pair, 0L);
		long endtm = System.currentTimeMillis();
		printOut(spreads.getValue(), String.format("Spread data for %s\nLast: %d", pair, spreads.getKey()), starttm, endtm);
	}

	@Test
	public void testGetAccountBalance() throws IOException, InvalidKeyException, NoSuchAlgorithmException {
		long starttm = System.currentTimeMillis();
		List<AccountBalance> ab = krakenFacade.getAccountBalance();
		long endtm = System.currentTimeMillis();
		printOut(ab, "ACCOUNT BALANCE", starttm, endtm);
	}

	@Test
	public void testGetTradesBalance() throws IOException, InvalidKeyException, NoSuchAlgorithmException {
		long starttm = System.currentTimeMillis();
		String pair = "EUR";
		TradeBalance tb = krakenFacade.getTradeBalance(pair);
		long endtm = System.currentTimeMillis();
		printOut("%s\t%s", pair, ToStringBuilder.reflectionToString(tb, ToStringStyle.SHORT_PREFIX_STYLE));
		printOut("\nElapsed: %.3f sec", ((double)(endtm-starttm)/1000));
	}

	@Test
	public void testGetOpenOrders() throws IOException, InvalidKeyException, NoSuchAlgorithmException, KrakenResponseError, KrakenCallException {
		long starttm = System.currentTimeMillis();
		List<OpenOrder> openOrders = krakenFacade.getOpenOrders(true);
		long endtm = System.currentTimeMillis();
		printOut(openOrders, "OPEN ORDERS", starttm, endtm);
	}

	@Test
	public void testGetClosedOrders() throws IOException, InvalidKeyException, NoSuchAlgorithmException, KrakenResponseError, KrakenCallException {
		long starttm = System.currentTimeMillis();
		List<ClosedOrder> closedOrders = krakenFacade.getClosedOrders(true);
		long endtm = System.currentTimeMillis();
		printOut(closedOrders, "CLOSED ORDERS", starttm, endtm);
	}

	@Test
	public void testGetOrdersInfo() throws IOException, InvalidKeyException, NoSuchAlgorithmException, KrakenResponseError, KrakenCallException {
		List<String> tradeIDs = Arrays.asList("O7FEMB-64FHC-UAPV77", "OWXP77-XN33Z-YDODPJ");
		long starttm = System.currentTimeMillis();
		List<OrderInfo> ordersInfo = krakenFacade.getOrdersInfo(tradeIDs, true);
		long endtm = System.currentTimeMillis();
		printOut(ordersInfo, String.format("ORDERS INFO for %s", tradeIDs), starttm, endtm);
	}

	@Test
	public void testGetOpenPositions() throws IOException, InvalidKeyException, NoSuchAlgorithmException {
		// TODO
	}

	@Test
	public void testGetLedgersInfo() throws IOException, InvalidKeyException, NoSuchAlgorithmException {
		long starttm = System.currentTimeMillis();
		List<LedgerInfo> ledgers = krakenFacade.getLedgersInfo();
		long endtm = System.currentTimeMillis();
		printOut(ledgers, "LEDGERS INFO", starttm, endtm);
	}

	@Test
	public void testQueryLedgers() throws IOException, InvalidKeyException, NoSuchAlgorithmException {
		List<String> ledgerIDs = Arrays.asList("L6UNG4-HICDP-QBVUN2","LUBAH5-KEADB-YEDSMR","LYUH2V-YGORX-VPJFPX");
		long starttm = System.currentTimeMillis();
		List<LedgerInfo> ledgersInfo = krakenFacade.getLedgersInfo(ledgerIDs);
		long endtm = System.currentTimeMillis();
		printOut(ledgersInfo, String.format("LEDGERS INFO for %s", ledgerIDs), starttm, endtm);
	}

	@Test
	public void testTradeVolume() throws IOException, InvalidKeyException, NoSuchAlgorithmException {
		List<String> pairs = Arrays.asList("XXBTZUSD", "XXBTZEUR");
		long starttm = System.currentTimeMillis();
		TradeVolume tradeVolume = krakenFacade.getTradeVolume(pairs);
		long endtm = System.currentTimeMillis();
		printOut(Arrays.asList(tradeVolume), String.format("TRADE VOLUME for %s", pairs), starttm, endtm);
	}

	@Test
	public void testDepositMethods() throws IOException, InvalidKeyException, NoSuchAlgorithmException {
		List<String> pairs = Arrays.asList("XXBTZUSD", "XXBTZEUR");
		long starttm = System.currentTimeMillis();
		TradeVolume tradeVolume = krakenFacade.getTradeVolume(pairs);
		long endtm = System.currentTimeMillis();
		printOut(Arrays.asList(tradeVolume), String.format("TRADE VOLUME for %s", pairs), starttm, endtm);
	}






	private void printOut(String format, Object... params) {
		out.println(String.format(format, params));
	}
	private void printOut(List<?> list, String title, long starttm, long endtm) {
		printOut("%s", title);
		list.forEach(elem -> {
			String str = ToStringBuilder.reflectionToString(elem, ToStringStyle.SHORT_PREFIX_STYLE);
			printOut(str);
		});
		printOut("\nElapsed: %.3f sec", ((double)(endtm-starttm)/1000));
	}


}
