package com.fede.app.model;

import com.fede.app.crypto.trading.parser.JsonToModel;
import edu.self.kraken.api.KrakenApi;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static java.lang.System.out;

/**
 * Created by f.barbano on 13/09/2017.
 */
public class TestModel {

	private static KrakenApi api;


	@BeforeClass
	public static void initKrakenApi() {
		api = new KrakenApi();
		api.setKey("VRQ4Oe8ZNPbLEyjv26SlU7F5O8zR9DjXr07Q6EHf95ooaSszvOkISQTc");
		api.setSecret("E07VIMtiE1x+0m1ef67ERAB8b3Xrk3Z1hxOlWbEUEB0dqKxghci7a+bnPeEmDrBigdYQ1My24fpPeMW9fOplIw==");
	}

	@Test
	public void testGetServerTime() throws IOException {
		String response = api.queryPublic(KrakenApi.Method.TIME);
		out.println(response);
	}

	/**
	 Get asset info
	 URL: https://api.kraken.com/0/public/Assets

	 Input:
		 info = info to retrieve (optional):
			info = all info (default)
		 aclass = asset class (optional):
			currency (default)
		 asset = comma delimited list of assets to get info on (optional. default = all for given asset class)
	 */
	@Test
	public void testGetAssetInfo() throws IOException {
		Map<String, String> params = new HashMap<>();
//		params.put("asset", "XICN,XXDG");
		String resp = api.queryPublic(KrakenApi.Method.ASSETS);

//		List<Asset> assetList = JsonToModel.toAssetsInfo(resp);
//
//		assetList.forEach(System.out::println);

	}

	@Test
	public void testGetAccountBalance() throws IOException, NoSuchAlgorithmException, InvalidKeyException {
		Map<String, String> params = new HashMap<>();
		long callTime = System.currentTimeMillis();
		String response = api.queryPrivate(KrakenApi.Method.BALANCE);
		out.println(response);
		JsonToModel jm = new JsonToModel(response);
		jm.parseAccountBalance(callTime);
	}

	@Test
	public void testGetTradeBalance()  {
		Arrays.asList("BCH","DASH","EOS","GNO","KFEE","USDT","XDAO","XETC","XETH","XICN","XLTC","XMLN","XNMC","XREP","XXBT","XXDG","XXLM","XXMR","XXRP","XXVN","XZEC","ZCAD","ZEUR","ZGBP","ZJPY","ZKRW","ZUSD")
			.forEach(asset -> {
			try {
				Map<String, String> params = new HashMap<>();
				params.put("asset", asset);
				String response = api.queryPrivate(KrakenApi.Method.TRADE_BALANCE, params);
				out.println(String.format("%-6s%s", asset, response));
			} catch(IOException | NoSuchAlgorithmException | InvalidKeyException ex) {
				throw new RuntimeException(ex);
			}
		});

	}

	@Test
	public void testGetOpenOrders() throws IOException, NoSuchAlgorithmException, InvalidKeyException {
		Map<String, String> params = new HashMap<>();
		String response = api.queryPrivate(KrakenApi.Method.OPEN_ORDERS);
		out.println(response);
		params.put("trades", "true");
		response = api.queryPrivate(KrakenApi.Method.OPEN_ORDERS, params);
		out.println(response);

	}                                                                                                              	

	@Test
	public void testGetClosedOrders() throws IOException, NoSuchAlgorithmException, InvalidKeyException {
		Map<String, String> params = new HashMap<>();
		params.put("trades", "true");
		String response = api.queryPrivate(KrakenApi.Method.CLOSED_ORDERS, params);
		out.println(response);

	}

	@Test
	public void testQueryOrdersInfo() throws IOException, NoSuchAlgorithmException, InvalidKeyException {
		Map<String, String> params = new HashMap<>();
		params.put("txid", "O7FEMB-64FHC-UAPV77,OWXP77-XN33Z-YDODPJ");  // transaction ID  mandatory
		params.put("trades", "true");
		String response = api.queryPrivate(KrakenApi.Method.QUERY_ORDERS, params);
		out.println(response);

	}

	@Test
	public void testGetTradesHistory() throws IOException, NoSuchAlgorithmException, InvalidKeyException {
		Map<String, String> params = new HashMap<>();
		params.put("trades", "true");  
		String response = api.queryPrivate(KrakenApi.Method.TRADES_HISTORY);
		out.println(response);

	}

	@Test
	public void testQueryTradesInfo() throws IOException, NoSuchAlgorithmException, InvalidKeyException {
		Map<String, String> params = new HashMap<>();
		params.put("trades", "true");
		params.put("txid", "TJPSMJ-OQ7AL-BEE5DK");		// trade id
		String response = api.queryPrivate(KrakenApi.Method.QUERY_TRADES, params);
		out.println(response);

	}

	@Test
	public void testGetOpenPositions() throws IOException, NoSuchAlgorithmException, InvalidKeyException {
		Map<String, String> params = new HashMap<>();
//		params.put("txid", "TCPR7S-MCSPU-LBNXD6");		// tx id    optional
		params.put("docalcs", "true");     	// profit/loss calculations (optional. default = false)
		String response = api.queryPrivate(KrakenApi.Method.OPEN_POSITIONS, params);
		out.println(response);

	}

	@Test
	public void testGetLedgersInfo() throws IOException, NoSuchAlgorithmException, InvalidKeyException {
		Map<String, String> params = new HashMap<>();
//		params.put("txid", "TCPR7S-MCSPU-LBNXD6");		// trade id
		String response = api.queryPrivate(KrakenApi.Method.LEDGERS);
		out.println(response);

	}

	@Test
	public void testGetTradesVolume() throws IOException, NoSuchAlgorithmException, InvalidKeyException {
		Map<String, String> params = new HashMap<>();
		// use pair and fee-info togheter to retrieve fee info
		params.put("pair", "XXBTZEUR,XXBTZUSD");
		params.put("fee-info", "true");
		String response = api.queryPrivate(KrakenApi.Method.TRADE_VOLUME, params);
		out.println(response);

	}
}
