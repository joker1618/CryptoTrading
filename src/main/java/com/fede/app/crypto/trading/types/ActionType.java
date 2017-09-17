package com.fede.app.crypto.trading.types;

import java.util.Arrays;

/**
 * Created by f.barbano on 13/09/2017.
 */
public enum ActionType {

	BUY("b"),
	SELL("s")
	;

	private String label;

	ActionType(String label) {
		this.label = label;
	}

	public String label() {
		return label;
	}

	public static synchronized ActionType getByLabel(String label) {
		return Arrays.stream(values())
				   .filter(at -> at.label.equals(label))
				   .findAny()
				   .orElse(null);
	}
}
