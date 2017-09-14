package com.fede.app.crypto.trading.types;

/**
 * Created by f.barbano on 13/09/2017.
 */
public enum OrderType {

	LIMIT("l"),
	MARKET("m")
	;

	private String label;

	OrderType(String label) {
		this.label = label;
	}

	public String label() {
		return label;
	}
}
