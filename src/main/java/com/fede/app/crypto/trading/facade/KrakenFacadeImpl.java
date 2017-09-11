package com.fede.app.crypto.trading.facade;

import com.fede.app.crypto.trading.model.Asset;
import com.fede.app.crypto.trading.util.StrUtils;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by f.barbano on 07/09/2017.
 */
public class KrakenFacadeImpl implements IKrakenFacade {

	private static final String USER_AGENT = "Mozilla/5.0";


	@Override
	public LocalDateTime retrieveServerTime() throws IOException {
		String url = "https://api.kraken.com/0/public/Time";

		String jsonData = getJSONValue(url);

		JsonReader reader = Json.createReader(new StringReader(jsonData));
		JsonObject jsonObject = reader.readObject();
		reader.close();

		jsonObject = jsonObject.getJsonObject("result");

		long unixtime = jsonObject.getJsonNumber("unixtime").longValue();
		ZonedDateTime zdt = Instant.ofEpochMilli(unixtime * 1000L).atZone(ZoneId.systemDefault());
		return zdt.toLocalDateTime();
	}

	@Override
	public List<Asset> retrieveAssets() throws IOException {
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

		return assetList;
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
}
