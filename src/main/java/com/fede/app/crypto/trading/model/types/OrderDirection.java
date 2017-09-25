package com.fede.app.crypto.trading.model.types;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by f.barbano on 18/09/2017.
 */
public enum OrderDirection {

	ASK("ask", "a"),
	BID("bid", "b")
	;

	private List<String> labels;

	OrderDirection(String main, String... others) {
		this.labels = new ArrayList<>();
		labels.add(main);
		labels.addAll(Arrays.asList(others));
	}

	public String label() {
		return labels.get(0);
	}

	public static synchronized OrderDirection getByLabel(String label) {
		return Arrays.stream(values())
				   .filter(at -> at.labels.contains(label))
				   .findAny()
				   .orElse(null);
	}
}
