package com.fede.app.crypto.trading.facade;

import com.fede.app.crypto.trading.model.Asset;
import com.fede.app.crypto.trading.model.AssetPair;
import com.fede.app.crypto.trading.model.OHLC;
import com.fede.app.crypto.trading.model.Ticker;
import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

/**
 * Created by f.barbano on 15/09/2017.
 */
public interface IKrakenCaller {

	long getServerTime() throws IOException;

	List<Asset> getAssets() throws IOException;

	List<AssetPair> getAssetPairs() throws IOException;

	List<Ticker> getTickers(Collection<String> pairNames) throws IOException;

	Pair<Long, List<OHLC>> getOHLCs(String pairName, long since) throws IOException;
}
