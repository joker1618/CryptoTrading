package com.fede.app.crypto.trading.manager;

import com.fede.app.crypto.trading.common.Const;
import com.fede.app.crypto.trading.facade.IKrakenCaller;
import com.fede.app.crypto.trading.facade.IKrakenProvider;
import com.fede.app.crypto.trading.facade.KrakenCallerImpl;
import com.fede.app.crypto.trading.facade.KrakenProviderImpl;
import com.fede.app.crypto.trading.model.*;
import com.fede.app.crypto.trading.util.CheckUtils;
import com.fede.app.crypto.trading.util.ModelCompare;
import com.fede.app.crypto.trading.util.Utils;
import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by f.barbano on 15/09/2017.
 */
public class KrakenManagerImpl implements IKrakenManager {

	private static final IKrakenManager INSTANCE = new KrakenManagerImpl();

	private final IKrakenCaller krakenCaller;
	private final IKrakenProvider krakenProvider;

	private Map<String, Asset> assetMap;
	private Map<String, AssetPair> assetPairMap;



	private KrakenManagerImpl() {
		this.krakenCaller = new KrakenCallerImpl(Const.KRAKEN_KEY, Const.KRAKEN_SECRET);
		this.krakenProvider = new KrakenProviderImpl(Const.CSV_FOLDER);
	}

	public static IKrakenManager getInstance() {
		return INSTANCE;
	}


	@Override
	public long getServerTime() {
		try {
			return krakenCaller.getServerTime();
		} catch (IOException ex) {
			// TODO manage
			throw new RuntimeException(ex);
		}
	}

	@Override
	public Map<String, Asset> synchronizeAssets() {
		try {
			// read from file
			if (assetMap == null) {
				List<Asset> assetList = krakenProvider.readAssets();
				assetMap = Utils.toMapSingle(assetList, Asset::getAssetName);
			}

			// download from kraken website
			List<Asset> newAssets = krakenCaller.getAssets();
			Map<String, Asset> newAssetMap = Utils.toMapSingle(newAssets, Asset::getAssetName);

			// persist if assets changed
			if(!CheckUtils.areEquals(assetMap, newAssetMap)) {
				assetMap = newAssetMap;
				krakenProvider.persistAssets(newAssets);
			}

			return assetMap;

		} catch(IOException ex) {
			// TODO manage
			throw new RuntimeException(ex);
		}
	}

	@Override
	public Map<String, AssetPair> synchronizeAssetPairs() {
		try {
			// read from file
			if (assetPairMap == null) {
				List<AssetPair> assetPairs = krakenProvider.readAssetPairs();
				assetPairMap = Utils.toMapSingle(assetPairs, AssetPair::getPairName);
			}

			// download from kraken website
			List<AssetPair> newAssetPairs = krakenCaller.getAssetPairs();
			Map<String, AssetPair> newAssetPairMap = Utils.toMapSingle(newAssetPairs, AssetPair::getPairName);

			// persist if assets changed
			if(!CheckUtils.areEquals(assetPairMap, newAssetPairMap)) {
				assetPairMap = newAssetPairMap;
				krakenProvider.persistAssetPairs(newAssetPairs);
			}

			return assetPairMap;

		} catch(IOException ex) {
			// TODO manage
			throw new RuntimeException(ex);
		}
	}

	@Override
	public Map<String, Ticker> downloadTickers() {
		try {
			// synch AssetPair names if needed
			if(assetPairMap == null) {
				synchronizeAssetPairs();
			}
			Set<String> pairNames = assetPairMap.keySet();

			// download from kraken website
			List<Ticker> tickers = krakenCaller.getTickers(pairNames);
			krakenProvider.persistTickers(tickers);
			return Utils.toMapSingle(tickers, Ticker::getPairName);

		} catch(IOException ex) {
			// TODO manage
			throw new RuntimeException(ex);
		}
	}

	@Override
	public List<MarketOrder> downloadOrderBook(String pairName) {
		try {
			List<MarketOrder> orderBook = krakenCaller.getOrderBook(pairName);
			if(orderBook != null) {
				krakenProvider.persistOrderBook(orderBook);
			}
			return orderBook;

		} catch(IOException ex) {
			// TODO manage
			throw new RuntimeException(ex);
		}
	}

	@Override
	public List<Ohlc> downloadOHLCData(String pairName) {
		try {
			long last = krakenProvider.readOHLCLast(pairName);

			// download from kraken website
			Pair<Long, List<Ohlc>> ohlc = krakenCaller.getOhlcs(pairName, last);
			if(ohlc == null) {
				return null;
			}

			List<Ohlc> ohlcList = ohlc.getValue();
			Collections.sort(ohlcList, ModelCompare.compareOHLC());
			krakenProvider.persistOHLCs(ohlcList);
			krakenProvider.persistOHLCLast(pairName, ohlc.getKey());
			return ohlcList;

		} catch(IOException ex) {
			// TODO manage
			throw new RuntimeException(ex);
		}
	}

	@Override
	public List<RecentTrade> downloadTradesData(String pairName) {
		try {
			long last = krakenProvider.readTradeLast(pairName);

			// download from kraken website
			Pair<Long, List<RecentTrade>> trades = krakenCaller.getRecentTrades(pairName, last);
			if(trades == null) {
				return null;
			}

			List<RecentTrade> recentTradeList = trades.getValue();
			Collections.sort(recentTradeList, ModelCompare.compareTrades());
			krakenProvider.persistTrades(recentTradeList);
			krakenProvider.persistTradeLast(pairName, trades.getKey());
			return recentTradeList;

		} catch(IOException ex) {
			// TODO manage
			throw new RuntimeException(ex);
		}
	}

	@Override
	public List<SpreadData> downloadSpreadsData(String pairName) {
		try {
			long last = krakenProvider.readSpreadLast(pairName);

			// download from kraken website
			Pair<Long, List<SpreadData>> spreads = krakenCaller.getSpreadData(pairName, last);
			if(spreads == null) {
				return null;
			}

			List<SpreadData> spreadDataList = spreads.getValue();
			Collections.sort(spreadDataList, ModelCompare.compareSpreads());
			krakenProvider.persistSpreads(spreadDataList);
			krakenProvider.persistSpreadLast(pairName, spreads.getKey());
			return spreadDataList;

		} catch(IOException ex) {
			// TODO manage
			throw new RuntimeException(ex);
		}
	}

	@Override
	public List<AccountBalance> getAccountBalance() {
		try {
			List<AccountBalance> accountBalance = krakenCaller.getAccountBalance();
			Collections.sort(accountBalance, ModelCompare.compareAccountBalances());
			krakenProvider.persistAccountBalance(accountBalance);
			return accountBalance;
			
		} catch (Exception ex) {
			// TODO manage
			throw new RuntimeException(ex);
		}
	}

	@Override
	public TradeBalance getTradeBalance(String pairName) {
		try {
			TradeBalance tradeBalance = krakenCaller.getTradeBalance(pairName);
			krakenProvider.persistTradeBalance(tradeBalance);
			return tradeBalance;

		} catch (Exception ex) {
			// TODO manage
			throw new RuntimeException(ex);
		}
	}

	@Override
	public List<OpenOrder> getOpenOrders() {
		try {
			return krakenCaller.getOpenOrders(true);
		} catch (Exception ex) {
			// TODO manage
			throw new RuntimeException(ex);
		}
	}

	@Override
	public List<ClosedOrder> getClosedOrders() {
		try {
			return krakenCaller.getClosedOrders(true);
		} catch (Exception ex) {
			// TODO manage
			throw new RuntimeException(ex);
		}
	}

}
