package com.fede.app.crypto.trading.model.types;

import java.util.Arrays;

/**
 * Created by f.barbano on 08/10/2017.
 */
public enum CallError {

	UNKNOWN_ASSET_PAIR("EQuery:Unknown asset pair", true),
	INVALID_ASSET_PAIR("EQuery:Invalid asset pair:", false),

	;

	private String descr;
	private boolean exact;

	CallError(String descr, boolean exact) {
		this.descr = descr;
		this.exact = exact;
	}

	public String descr() {
		return descr;
	}

	public boolean isExact() {
		return exact;
	}

	public static synchronized CallError getByDescr(String descr) {
		return Arrays.stream(values())
				   .filter(at -> at.exact ? at.descr.equals(descr) : descr.startsWith(at.descr))
				   .findAny()
				   .orElse(null);
	}
}
