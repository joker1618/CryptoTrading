package com.fede.app.crypto.trading.dao.impl.csv;

import com.fede.app.crypto.trading.dao.IAssetsDao;
import com.fede.app.crypto.trading.model._public.Asset;

import java.nio.file.Path;
import java.util.List;

/**
 * Created by f.barbano on 01/10/2017.
 */
public class AssetsCsvDao implements IAssetsDao {

	private Path csvPath;

	public AssetsCsvDao(Path csvPath) {
		this.csvPath = csvPath;
	}

	@Override
	public List<Asset> getAssets() {
		// TODO impl
		return null;
	}

	@Override
	public void persistNewAssets(Long callTime, List<Asset> newAssets) {
		// TODO impl
	}
}
