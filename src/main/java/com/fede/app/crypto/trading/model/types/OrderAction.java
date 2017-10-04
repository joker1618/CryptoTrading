package com.fede.app.crypto.trading.model.types;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by f.barbano on 13/09/2017.
 */
public enum OrderAction {

	BUY("buy", "b"),
	SELL("sell", "s")
	;

	private List<String> labels;

	OrderAction(String main, String... others) {
		this.labels = new ArrayList<>();
		labels.add(main);
		labels.addAll(Arrays.asList(others));
	}

	public String label() {
		return labels.get(0);
	}

	public static synchronized OrderAction getByLabel(String label) {
		return Arrays.stream(values())
				   .filter(at -> at.labels.contains(label))
				   .findAny()
				   .orElse(null);
	}
}
