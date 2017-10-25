package com.fede.app.crypto.trading.dao;

import com.fede.app.crypto.trading.model._public.AssetPair;

import java.util.List;

/**
 * Created by f.barbano on 30/09/2017.
 */
public interface IAssetPairsDao {

	List<AssetPair> getAssetPairs();

	void persistAssetPairs(Long callTime, List<AssetPair> newAssetPairs);

}
