package com.fede.app.piantaNeed;

import com.fede.app.crypto.trading.util.FileUtils;
import edu.self.kraken.api.KrakenApi;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by f.barbano on 12/09/2017.
 */
public class TryBadGateway {

	private static final int PAUSE_SEC = 4;
	private static final Path TRACE_FILE = Paths.get("traceFile.log");
	private KrakenApi api;


	@Before
	public void before() {
		api = new KrakenApi();
		api.setKey("VRQ4Oe8ZNPbLEyjv26SlU7F5O8zR9DjXr07Q6EHf95ooaSszvOkISQTc");
		api.setSecret("E07VIMtiE1x+0m1ef67ERAB8b3Xrk3Z1hxOlWbEUEB0dqKxghci7a+bnPeEmDrBigdYQ1My24fpPeMW9fOplIw==");
	}

	@Test
	public void startCycleOrderCancel() throws NoSuchAlgorithmException, InvalidKeyException, IOException, InterruptedException {
		while(true) {
			String txId = getOrder();

			if(StringUtils.isBlank(txId)) {
				System.out.println("Error performing order");
			} else {
				System.out.println("Order done [txId=" + txId + "]");
				Thread.sleep(PAUSE_SEC * 1000);
			}

			Thread.sleep(PAUSE_SEC * 1000);
		}

	}

	private String getOrder() throws NoSuchAlgorithmException, InvalidKeyException, IOException {
		Map<String, String> input = new HashMap<>();
		input.put("pair", "XLTCZEUR");
		input.put("type", "sell");
		input.put("ordertype", "limit");
		input.put("price", "42");
		input.put("volume", "4.99948");
//		input.put("validate", "yes");


		String response = api.queryPrivate(KrakenApi.Method.ADD_ORDER, input);

		FileUtils.appendToFile(TRACE_FILE, response, true);

		if(!response.contains("txid")) {
			return null;
		}

		int idxTxid = response.indexOf("txid");
		String substr = response.substring(idxTxid + 4);
		String txId = StringUtils.substringBetween(substr, "[\"", "\"]").trim();

		return txId;
	}

	private void cancelOrder(String txId) throws NoSuchAlgorithmException, InvalidKeyException, IOException {

		Map<String, String> input = new HashMap<>();
		input.put("txid", txId);

		String response = api.queryPrivate(KrakenApi.Method.CANCEL_ORDER, input);
		FileUtils.appendToFile(TRACE_FILE, response, true);

	}
}
