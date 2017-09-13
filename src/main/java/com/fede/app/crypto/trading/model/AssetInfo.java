package com.fede.app.crypto.trading.model;

/**
 * Created by f.barbano on 07/09/2017.
 * This class is the result object of the call to 'https://api.kraken.com/0/public/Assets'
 */
public class AssetInfo {

	private String assetName;
	private String aClass;
	private String altName;
	private int decimals;
	private int displayDecimals;


	public AssetInfo(String assetName, String aClass, String altName, int decimals, int displayDecimals) {
		this.assetName = assetName;
		this.aClass = aClass;
		this.altName = altName;
		this.decimals = decimals;
		this.displayDecimals = displayDecimals;
	}

	@Override
	public String toString() {
		return String.format("AssetInfo  -->  %s: {aClass='%s', altName='%s', decimals=%d, display_decimals=%d}",
			assetName, altName, aClass, decimals, displayDecimals
		);
	}

	public String getAssetName() {
		return assetName;
	}
	public String getAClass() {
		return aClass;
	}
	public String getAltName() {
		return altName;
	}
	public int getDecimals() {
		return decimals;
	}
	public int getDisplayDecimals() {
		return displayDecimals;
	}

}
