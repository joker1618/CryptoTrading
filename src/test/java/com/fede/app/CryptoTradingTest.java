package com.fede.app;

import com.fede.app.crypto.trading.model.Asset;
import com.fede.app.crypto.trading.util.StrUtils;
import org.junit.Test;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.System.out;

/**
 * Created by f.barbano on 07/09/2017.
 */
public class CryptoTradingTest {

	private final String USER_AGENT = "Mozilla/5.0";


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

		List<Asset> assetList = new ArrayList<>();

		for(String field : fields) {
			String[] split = StrUtils.splitAllFields(field, ":");
			String xccy = split[0].replaceAll("\"", "").trim();
			JsonObject jo = jsonObject.getJsonObject(xccy);
			Asset asset = new Asset();
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



}
