package com.fede.app.crypto.trading.dao;

import com.fede.app.crypto.trading.model._public.SpreadData;

import java.util.Collection;
import java.util.List;

/**
 * Created by f.barbano on 10/10/2017.
 */
public interface ISpreadDataDao {

	// review all

	List<SpreadData> getSpreadData();
	
	void persistNewSpreadData(Collection<SpreadData> spreadData);

}
