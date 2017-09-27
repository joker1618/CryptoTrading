package com.fede.app.crypto.trading.util;

import com.fede.app.crypto.trading.model._private.AccountBalance;
import com.fede.app.crypto.trading.model._public.*;

import java.util.Comparator;

/**
 * Created by f.barbano on 20/09/2017.
 */
public class ModelCompare {

	public static Comparator<Asset> compareAssets() {
		return Comparator.comparing(Asset::getAssetName);
	}

	public static Comparator<AssetPair> compareAssetPairs() {
		return Comparator.comparing(AssetPair::getPairName);
	}

	public static Comparator<Ohlc> compareOHLC() {
		return (o1, o2) -> {
			CompareBuilder cp = new CompareBuilder();
			cp.add(o1.getPairName(), o2.getPairName());
			cp.add(o1.getTime(), o2.getTime());
			return cp.compare();
		};
	}

	public static Comparator<RecentTrade> compareTrades() {
		return (t1, t2) -> {
			CompareBuilder cp = new CompareBuilder();
			cp.add(t1.getPairName(), t2.getPairName());
			cp.add(t1.getTime(), t2.getTime());
			cp.add(t1.getPrice(), t2.getPrice());
			return cp.compare();
		};
	}

	public static Comparator<SpreadData> compareSpreads() {
		return (s1, s2) -> {
			CompareBuilder cp = new CompareBuilder();
			cp.add(s1.getPairName(), s2.getPairName());
			cp.add(s1.getTime(), s2.getTime());
			return cp.compare();
		};
	}

	public static Comparator<AccountBalance> compareAccountBalances() {
		return (s1, s2) -> {
			CompareBuilder cp = new CompareBuilder();
			cp.add(s1.getCallTime(), s2.getCallTime());
			cp.add(s1.getAssetClass(), s2.getAssetClass());
			return cp.compare();
		};
	}

	private static class CompareBuilder {
		int result;

		CompareBuilder() {
			this.result = 0;
		}

		CompareBuilder add(String s1, String s2) {
			if(result == 0) {
				result = s1.compareTo(s2);
			}
			return this;
		}

		CompareBuilder add(Integer n1, Integer n2) {
			if(result == 0) {
				result = n1 - n2;
			}
			return this;
		}

		CompareBuilder add(Long l1, Long l2) {
			if(result == 0 && l1 != l2) {
				result = l1 < l2 ? -1 : (l1 > l2 ? 1 : 0);
			}
			return this;
		}

		CompareBuilder add(Double d1, Double d2) {
			if(result == 0) {
				result = d1 < d2 ? -1 : (d1 > d2 ? 1 : 0);
			}
			return this;
		}

		int compare() {
			return result;
		}
	}
}
