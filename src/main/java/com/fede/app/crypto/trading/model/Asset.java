package com.fede.app.crypto.trading.model;


/**
 * Created by f.barbano on 07/09/2017.
 * This class is the result object of the call to 'https://api.kraken.com/0/public/Assets'
 */
public class Asset implements Comparable<Asset> {

	private String assetName;
	private String aClass;
	private String altName;
	private int decimals;
	private int displayDecimals;


	@Override
	public int compareTo(Asset o) {
		return assetName.compareTo(o.assetName);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Asset)) return false;

		Asset asset = (Asset) o;

		if (decimals != asset.decimals) return false;
		if (displayDecimals != asset.displayDecimals) return false;
		if (assetName != null ? !assetName.equals(asset.assetName) : asset.assetName != null) return false;
		if (aClass != null ? !aClass.equals(asset.aClass) : asset.aClass != null) return false;
		return altName != null ? altName.equals(asset.altName) : asset.altName == null;
	}

	@Override
	public int hashCode() {
		int result = assetName != null ? assetName.hashCode() : 0;
		result = 31 * result + (aClass != null ? aClass.hashCode() : 0);
		result = 31 * result + (altName != null ? altName.hashCode() : 0);
		result = 31 * result + decimals;
		result = 31 * result + displayDecimals;
		return result;
	}

	public String getAssetName() {
		return assetName;
	}
	public void setAssetName(String assetName) {
		this.assetName = assetName;
	}
	public String getAClass() {
		return aClass;
	}
	public void setAClass(String aClass) {
		this.aClass = aClass;
	}
	public String getAltName() {
		return altName;
	}
	public void setAltName(String altName) {
		this.altName = altName;
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
