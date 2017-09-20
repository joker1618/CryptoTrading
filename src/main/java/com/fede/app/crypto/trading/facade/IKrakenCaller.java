package com.fede.app.crypto.trading.facade;

import com.fede.app.crypto.trading.model.*;
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

	List<Order> getOrderBook(String pairName) throws IOException;

	Pair<Long, List<Trade>> getTrades(String pairName, long since) throws IOException;

	Pair<Long, List<Spread>> getSpreads(String pairName, long since) throws IOException;

	List<AccountBalance> getAccounteBalance() throws IOException;


}
