package com.fede.app.model;

import com.fede.app.crypto.trading.model.AssetInfo;
import com.fede.app.crypto.trading.model.JsonToModel;
import edu.self.kraken.api.KrakenApi;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

		List<AssetInfo> assetInfoList = JsonToModel.toAssetList(resp);

		assetInfoList.forEach(System.out::println);

	}

	@Test
	public void testGetAccountBalance() throws IOException, NoSuchAlgorithmException, InvalidKeyException {
		Map<String, String> params = new HashMap<>();
		String response = api.queryPrivate(KrakenApi.Method.BALANCE);
		System.out.println(response);

	}

	@Test
	public void testGetTradeBalance() throws IOException, NoSuchAlgorithmException, InvalidKeyException {
		Map<String, String> params = new HashMap<>();
		String response = api.queryPrivate(KrakenApi.Method.TRADE_BALANCE);
		System.out.println(response);

	}

	@Test
	public void testGetOpenOrders() throws IOException, NoSuchAlgorithmException, InvalidKeyException {
		Map<String, String> params = new HashMap<>();
		String response = api.queryPrivate(KrakenApi.Method.OPEN_ORDERS);
		System.out.println(response);

	}

	@Test
	public void testGetClosedOrders() throws IOException, NoSuchAlgorithmException, InvalidKeyException {
		Map<String, String> params = new HashMap<>();
		String response = api.queryPrivate(KrakenApi.Method.CLOSED_ORDERS);
		System.out.println(response);

	}

	@Test
	public void testQueryOrdersInfo() throws IOException, NoSuchAlgorithmException, InvalidKeyException {
		Map<String, String> params = new HashMap<>();
		params.put("txid", "O7FEMB-64FHC-UAPV77");  // transaction ID
		String response = api.queryPrivate(KrakenApi.Method.QUERY_ORDERS, params);
		System.out.println(response);

	}

	@Test
	public void testGetTradesHistory() throws IOException, NoSuchAlgorithmException, InvalidKeyException {
		Map<String, String> params = new HashMap<>();
		String response = api.queryPrivate(KrakenApi.Method.TRADES_HISTORY);
		System.out.println(response);

	}

	@Test
	public void testQueryTradesInfo() throws IOException, NoSuchAlgorithmException, InvalidKeyException {
		Map<String, String> params = new HashMap<>();
		params.put("txid", "TCPR7S-MCSPU-LBNXD6");		// trade id
		String response = api.queryPrivate(KrakenApi.Method.QUERY_TRADES);
		System.out.println(response);

	}

	@Test
	public void testGetOpenPositions() throws IOException, NoSuchAlgorithmException, InvalidKeyException {
		Map<String, String> params = new HashMap<>();
//		params.put("txid", "TCPR7S-MCSPU-LBNXD6");		// trade id
		String response = api.queryPrivate(KrakenApi.Method.OPEN_POSITIONS);
		System.out.println(response);

	}

	@Test
	public void testGetLedgersInfo() throws IOException, NoSuchAlgorithmException, InvalidKeyException {
		Map<String, String> params = new HashMap<>();
//		params.put("txid", "TCPR7S-MCSPU-LBNXD6");		// trade id
		String response = api.queryPrivate(KrakenApi.Method.LEDGERS);
		System.out.println(response);

	}

	@Test
	public void testGetTradesVolume() throws IOException, NoSuchAlgorithmException, InvalidKeyException {
		Map<String, String> params = new HashMap<>();
//		params.put("pair", "ZUSD");
		params.put("fee-info", "yes");
		String response = api.queryPrivate(KrakenApi.Method.TRADE_VOLUME);
		System.out.println(response);

	}
}
