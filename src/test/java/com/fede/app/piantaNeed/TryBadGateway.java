package com.fede.app.piantaNeed;

/**
 * Created by f.barbano on 12/09/2017.
 */
public class TryBadGateway {

//	private static final int PAUSE_SEC = 4;
//	private static final Path TRACE_FILE = Paths.get("traceFile.log");
//	private KrakenApi api;
//
//
//	@Before
//	public void before() {
//		api = new KrakenApi();
//		api.setKey(Const.KRAKEN_KEY);
//		api.setSecret(Const.KRAKEN_SECRET);
//	}
//
//	@Test
//	public void startCycleOrderCancel() throws NoSuchAlgorithmException, InvalidKeyException, IOException, InterruptedException {
//		while(true) {
//			String txId = getOrder();
//
//			if(StringUtils.isBlank(txId)) {
//				System.out.println("Error performing order");
//			} else {
//				System.out.println("MarketOrder done [txId=" + txId + "]");
//				Thread.sleep(PAUSE_SEC * 1000);
//			}
//
//			Thread.sleep(PAUSE_SEC * 1000);
//			break;
//		}
//
//	}
//
//	private String getOrder() throws NoSuchAlgorithmException, InvalidKeyException, IOException {
//		Map<String, String> input = new HashMap<>();
//		input.put("pair", "XLTCZEUR");
//		input.put("type", "sell");
//		input.put("ordertype", "limit");
//		input.put("price", "42");
//		input.put("volume", "4.99948");
//		input.put("validate", "yes");
//
//
//		String response = api.queryPrivate(ApiMethod.ADD_ORDER, input);
//		System.out.println(response);
//
//		FileUtils.appendToFile(TRACE_FILE, response, true);
//
//		if(!response.contains("txid")) {
//			return null;
//		}
//
//		int idxTxid = response.indexOf("txid");
//		String substr = response.substring(idxTxid + 4);
//		String txId = StringUtils.substringBetween(substr, "[\"", "\"]").trim();
//
//		return txId;
//	}
//
//	private void cancelOrder(String txId) throws NoSuchAlgorithmException, InvalidKeyException, IOException {
//
//		Map<String, String> input = new HashMap<>();
//		input.put("txid", txId);
//
//		String response = api.queryPrivate(ApiMethod.CANCEL_ORDER, input);
//		FileUtils.appendToFile(TRACE_FILE, response, true);
//
//	}
}
