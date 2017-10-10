package com.fede.app.crypto.trading.dao;

import com.fede.app.crypto.trading.model._public.Asset;

import java.util.List;

/**
 * Created by f.barbano on 30/09/2017.
 */
public interface IAssetsDao {

	List<Asset> getAssets();
	void persistNewAssets(Long callTime, List<Asset> newAssets);

}
