package com.fede.app.crypto.trading.model;

/**
 * Created by f.barbano on 07/09/2017.
 * This class is the result object of the call to 'https://api.kraken.com/0/public/Assets'
 */
public class Asset {

	private String assetName;
	private String altName;
	private String assetClass;
	private int decimals;
	private int displayDecimals;


	@Override
	public String toString() {
		return "Asset{" +
				   "assetName='" + assetName + '\'' +
				   ", altName='" + altName + '\'' +
				   ", assetClass='" + assetClass + '\'' +
				   ", decimals=" + decimals +
				   ", displayDecimals=" + displayDecimals +
				   '}';
	}

	public String getAssetName() {
		return assetName;
	}
	public void setAssetName(String assetName) {
		this.assetName = assetName;
	}

	public String getAltName() {
		return altName;
	}

	public void setAltName(String altName) {
		this.altName = altName;
	}

	public String getAssetClass() {
		return assetClass;
	}

	public void setAssetClass(String assetClass) {
		this.assetClass = assetClass;
	}

	public int getDecimals() {
		return decimals;
	}

	public void setDecimals(int decimals) {
		this.decimals = decimals;
	}

	public int getDisplayDecimals() {
		return displayDecimals;
	}

	public void setDisplayDecimals(int displayDecimals) {
		this.displayDecimals = displayDecimals;
	}
}
