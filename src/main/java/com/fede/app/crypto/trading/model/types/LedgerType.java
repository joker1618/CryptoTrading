package com.fede.app.crypto.trading.model.types;

import java.util.Arrays;

/**
 * Created by f.barbano on 25/09/2017.
 */
public enum LedgerType {

	DEPOSIT("deposit"),
	WITHDRAWAL("withdrawal"),
	TRADE("trade"),
	MARGIN("margin"),
	;

	private String label;

	LedgerType(String label) {
		this.label = label;
	}

	public String label() {
		return label;
	}

	public static synchronized LedgerType getByLabel(String label) {
		return Arrays.stream(values())
				   .filter(at -> at.label.equals(label))
				   .findAny()
				   .orElse(null);
	}
}
