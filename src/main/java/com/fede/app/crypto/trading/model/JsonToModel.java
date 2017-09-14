package com.fede.app.crypto.trading.model;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by f.barbano on 13/09/2017.
 */
public class JsonToModel {

	// TODO manage errors ("errors" part of json is not taken into account)
	// for now, the following methods only create the model object parsing json (they use only "result" part of json)

	public static List<AssetInfo> toAssetList(String jsonString) {
		JsonReader reader = Json.createReader(new StringReader(jsonString));
		JsonObject jsonObject = reader.readObject();
		reader.close();

		JsonObject result = jsonObject.getJsonObject("result");

		List<AssetInfo> assetInfoList = new ArrayList<>();
		for(Map.Entry<String, JsonValue> entry : result.entrySet()) {
			String assetName = entry.getKey();
			JsonObject jsonAsset = entry.getValue().asJsonObject();
			String aclass = jsonAsset.getString("aclass");
			String altname = jsonAsset.getString("altname");
			int decimals = jsonAsset.getInt("decimals");
			int display_decimals = jsonAsset.getInt("display_decimals");
			assetInfoList.add(new AssetInfo(assetName, aclass, altname, decimals, display_decimals));
		}

		return assetInfoList;
	}


}
