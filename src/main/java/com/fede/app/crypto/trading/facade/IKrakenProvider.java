package com.fede.app.crypto.trading.facade;

import com.fede.app.crypto.trading.model.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by f.barbano on 15/09/2017.
 */
public interface IKrakenProvider {

	void persistAssets(List<Asset> assetsInfo) throws IOException;
	List<Asset> readAssets() throws IOException;
	
	void persistAssetPairs(List<AssetPair> assetPairs) throws IOException;
	List<AssetPair> readAssetPairs() throws IOException;

	void persistTickers(List<Ticker> tickers) throws IOException;
	Map<String, List<Ticker>> readTickers(List<String> pairNames) throws IOException;

	void persistOHLCs(List<OHLC> ohlcList) throws IOException;
	void persistOHLCLast(String pairName, long last) throws IOException;
	List<OHLC> readOHLCs(String pairName) throws IOException;
	long readOHLCLast(String pairName) throws IOException;

	void persistTrades(List<Trade> tradeList) throws IOException;
	void persistTradeLast(String pairName, long last) throws IOException;
	List<Trade> readTrades(String pairName) throws IOException;
	long readTradeLast(String pairName) throws IOException;

	void persistSpreads(List<Spread> tradeList) throws IOException;
	void persistSpreadLast(String pairName, long last) throws IOException;
	List<Spread> readSpreads(String pairName) throws IOException;
	long readSpreadLast(String pairName) throws IOException;





}
