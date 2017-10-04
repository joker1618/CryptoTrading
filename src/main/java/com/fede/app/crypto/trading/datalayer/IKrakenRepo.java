package com.fede.app.crypto.trading.datalayer;

import com.fede.app.crypto.trading.model._public.Asset;

import java.util.List;

/**
 * Created by f.barbano on 01/10/2017.
 */
public interface IKrakenRepo {

	List<Asset> getAssets();
	List<Asset> getAssets(boolean forceCall);


}
