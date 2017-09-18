package com.fede.app.crypto.trading.types;

import java.util.Arrays;

/**
 * Created by f.barbano on 18/09/2017.
 */
public enum OrderDirection {

	ASK("a"),
	BID("b")
	;

	private String label;

	OrderDirection(String label) {
		this.label = label;
	}

	public String label() {
		return label;
	}

	public static synchronized OrderDirection getByLabel(String label) {
		return Arrays.stream(values())
				   .filter(at -> at.label.equals(label))
				   .findAny()
				   .orElse(null);
	}
}
