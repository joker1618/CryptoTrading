package com.fede.app.crypto.trading.datalayer;

import com.fede.app.crypto.trading.dao.IAssetsDao;
import com.fede.app.crypto.trading.kraken.IKrakenCaller;
import com.fede.app.crypto.trading.model._public.Asset;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by f.barbano on 01/10/2017.
 */
class KrakenRepoImpl implements IKrakenRepo {

	private IAssetsDao assetDao;
	private IKrakenCaller krakenCaller;

	private List<Asset> assetList = new ArrayList<>();

	@Override
	public List<Asset> getAssets() {
		return getAssets(false);
	}

	@Override
	public List<Asset> getAssets(boolean forceCall) {
		if(!assetList.isEmpty() && !forceCall) {
			return assetList;
		}



		try {
			List<Asset> assets = krakenCaller.getAssets();
//			if(assetList == null)
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}
	private List<Asset> getAssets11111(boolean forceCall) throws IOException {
		if(assetList != null && !forceCall) {
			return assetList;
		}


		List<Asset> daoList = assetDao.getAssets();
		List<Asset> callList = krakenCaller.getAssets();

		if(daoList.equals(callList)) {
			assetList = daoList;
		} else {
//			sdsd
		}



		return null;
	}


	void setAssetDao(IAssetsDao assetDao) {
		this.assetDao = assetDao;
	}
	public void setKrakenCaller(IKrakenCaller krakenCaller) {
		this.krakenCaller = krakenCaller;
	}
}
