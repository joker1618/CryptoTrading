package com.fede.app.crypto.trading.model.types;

import java.util.Arrays;

/**
 * Created by f.barbano on 25/09/2017.
 */
public enum PositionStatus {

	OPNE("open"),
	CLOSED("closed"),
	;

	private String label;

	PositionStatus(String label) {
		this.label = label;
	}

	public String label() {
		return label;
	}

	public static synchronized PositionStatus getByLabel(String label) {
		return Arrays.stream(values())
				   .filter(at -> at.label.equals(label))
				   .findAny()
				   .orElse(null);
	}
}
