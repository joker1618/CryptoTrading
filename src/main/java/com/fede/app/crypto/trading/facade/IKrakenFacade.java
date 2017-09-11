package com.fede.app.crypto.trading.facade;

import com.fede.app.crypto.trading.model.Asset;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by f.barbano on 07/09/2017.
 */
public interface IKrakenFacade {

	/**
		 Get server time
		 URL: https://api.kraken.com/0/public/Time

		 Result: Server's time
			 unixtime =  as unix timestamp
			 rfc1123 = as RFC 1123 time format
	 */
	LocalDateTime retrieveServerTime() throws IOException;

	/**
	 Get asset info
	 URL: https://api.kraken.com/0/public/Assets

	 Input:
		 info = info to retrieve (optional):
		 info = all info (default)
		 aclass = asset class (optional):
		 currency (default)
		 asset = comma delimited list of assets to get info on (optional.  default = all for given asset class)

	 Result: array of asset names and their info
		 <asset_name> = asset name
			 altname = alternate name
			 aclass = asset class
			 decimals = scaling decimal places for record keeping
			 display_decimals = scaling decimal places for output display
	 */
	List<Asset> retrieveAssets() throws IOException;




}
