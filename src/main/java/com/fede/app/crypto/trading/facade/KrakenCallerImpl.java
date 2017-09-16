package com.fede.app.crypto.trading.facade;

import com.fede.app.crypto.trading.model.Asset;
import com.fede.app.crypto.trading.model.AssetPair;
import com.fede.app.crypto.trading.model.Ticker;
import com.fede.app.crypto.trading.parser.JsonToModel;
import com.fede.app.crypto.trading.util.Utils;
import edu.self.kraken.api.KrakenApi;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by f.barbano on 15/09/2017.
 */
public class KrakenCallerImpl implements IKrakenCaller {

	private final KrakenApi krakenApi;
	private final String krakenKey;
	private final String krakenSecret;

	public KrakenCallerImpl(String krakenKey, String krakenSecret) {
		this.krakenKey = krakenKey;
		this.krakenSecret = krakenSecret;
		this.krakenApi = new KrakenApi();
		this.krakenApi.setKey(krakenKey);
		this.krakenApi.setSecret(krakenSecret);
	}

	@Override
	public long getServerTime() throws IOException {
		String json = krakenApi.queryPublic(KrakenApi.Method.TIME);
		JsonToModel jm = new JsonToModel(json);
		return jm.parseServerTime();
	}

	@Override
	public List<Asset> getAssets() throws IOException {
		String json = krakenApi.queryPublic(KrakenApi.Method.ASSETS);
		JsonToModel jm = new JsonToModel(json);
		return jm.parseAssets();
	}

	@Override
	public List<AssetPair> getAssetPairs() throws IOException {
		String json = krakenApi.queryPublic(KrakenApi.Method.ASSET_PAIRS);
		JsonToModel jm = new JsonToModel(json);
		return jm.parseAssetPairs();
	}

	@Override
	public List<Ticker> getTickers(Collection<String> pairNames) throws IOException {
		Map<String, String> apiParams = new HashMap<>();
		apiParams.put("pair", Utils.join(pairNames));
		long callTime = System.currentTimeMillis();
		String json = krakenApi.queryPublic(KrakenApi.Method.TICKER, apiParams);
		JsonToModel jm = new JsonToModel(json);
		return jm.parseTickers(callTime);
	}
}
