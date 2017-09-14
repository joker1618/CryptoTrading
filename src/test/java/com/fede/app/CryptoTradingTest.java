package com.fede.app;

/**
 * Created by f.barbano on 07/09/2017.
 */
public class CryptoTradingTest {

	// To enable this class it need to include javax.json and/or org.glassfish.json

	private final String USER_AGENT = "Mozilla/5.0";

/*
	@Test
	public void callGetServerTime() throws IOException {
		String url = "https://api.kraken.com/0/public/Time";

		String jsonData = getJSONValue(url);

		JsonReader reader = Json.createReader(new StringReader(jsonData));
		JsonObject jsonObject = reader.readObject();
		reader.close();

		jsonObject = jsonObject.getJsonObject("result");

		long unixtime = jsonObject.getJsonNumber("unixtime").longValue();
		out.println("unixtime: " + unixtime);

		long millis = unixtime * 1000;
		LocalDateTime localDateTime = millisToLocalDateTime(millis);
		out.println("localDT:  " + DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss").format(localDateTime));
	}
	private String getJSONValue(String urlStr) throws IOException {
		URL url = new URL(urlStr);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();

		con.setRequestMethod("GET");
		con.setRequestProperty("User-Agent", USER_AGENT);

		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
		while ((inputLine = in.readLine()) != null) {
			response.append(inputLine);
		}
		in.close();

		return response.toString();
	}
	private LocalDateTime millisToLocalDateTime(long millis) {
		return Instant.ofEpochMilli(millis).atZone(ZoneId.systemDefault()).toLocalDateTime();
	}


	@Test
	public void callAssets() throws IOException {
		String url = "https://api.kraken.com/0/public/Assets";

		String jsonData = getJSONValue(url);

		JsonReader reader = Json.createReader(new StringReader(jsonData));
		JsonObject jsonObject = reader.readObject();
		reader.close();

		jsonObject = jsonObject.getJsonObject("result");

		String strResult = jsonObject.toString().trim().replaceAll("^\\{", "").replaceAll("}$", "");

		String[] fields = StrUtils.splitAllFields(strResult, "},", true, false);

		List<AssetInfo> assetList = new ArrayList<>();

		for(String field : fields) {
			String[] split = StrUtils.splitAllFields(field, ":");
			String xccy = split[0].replaceAll("\"", "").trim();
			JsonObject jo = jsonObject.getJsonObject(xccy);
			AssetInfo asset = new AssetInfo();
			asset.setAssetName(xccy);
			asset.setAssetClass(jo.getString("aclass"));
			asset.setAltName(jo.getString("altname"));
			asset.setDecimals(jo.getInt("decimals"));
			asset.setDisplayDecimals(jo.getInt("display_decimals"));
			assetList.add(asset);
		}

		assetList.forEach(out::println);
	}

	@Test
	public void callTicker() throws IOException {
		String url = "https://api.kraken.com/0/public/Ticker?pair=";

		String[] pairs = new String[]{"BCHEUR","BCHUSD","BCHXBT","DASHEUR","DASHUSD","DASHXBT","EOSETH","EOSXBT","GNOETH","GNOXBT","USDTZUSD","XETCXETH","XETCXXBT","XETCZEUR","XETCZUSD","XETHXXBT","XETHZCAD","XETHZEUR","XETHZGBP","XETHZJPY","XETHZUSD","XICNXETH","XICNXXBT","XLTCXXBT","XLTCZEUR","XLTCZUSD","XMLNXETH","XMLNXXBT","XREPXETH","XREPXXBT","XREPZEUR","XXBTZCAD","XXBTZEUR","XXBTZGBP","XXBTZJPY","XXBTZUSD","XXDGXXBT","XXLMXXBT","XXMRXXBT","XXMRZEUR","XXMRZUSD","XXRPXXBT","XXRPZEUR","XXRPZUSD","XZECXXBT","XZECZEUR","XZECZUSD"};

		for(String pair : pairs) {
			String jsonData = getJSONValue(url + pair);

			JsonReader reader = Json.createReader(new StringReader(jsonData));
			JsonObject jsonObject = reader.readObject();
			reader.close();

			jsonObject = jsonObject.getJsonObject("result");
			jsonObject = jsonObject.getJsonObject(pair);

			out.println(pair);
			out.println(jsonObject+"\n");
		}
	}

*/

}
