package com.fede.app.crypto.trading.facade;

import com.fede.app.crypto.trading.model.Asset;
import com.fede.app.crypto.trading.model.AssetPair;
import com.fede.app.crypto.trading.model.Ticker;

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

}
