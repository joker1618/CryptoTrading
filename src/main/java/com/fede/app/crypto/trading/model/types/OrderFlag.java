package com.fede.app.crypto.trading.model.types;

import java.util.Arrays;

/**
 * Created by f.barbano on 24/09/2017.
 */
public enum OrderFlag {
	//	oflags = comma delimited list of order flags
	//		viqc = volume in quote currency
	//		fcib = prefer fee in base currency (default if selling)
	//		fciq = prefer fee in quote currency (default if buying)
	//		nompp = no market price protection
	VIQC("viqc", "volume in quote currency"),
	FCIB("fcib", "prefer fee in base currency (default if selling)"),
	FCIQ("fciq", "prefer fee in quote currency (default if buying)"),
	NOMPP("nompp", "no market price protection"),
	;

	private String label;
	private String description;

	OrderFlag(String label, String description) {
		this.label = label;
		this.description = description;
	}

	public String label() {
		return label;
	}
	public String description() {
		return description;
	}

	public static synchronized OrderFlag getByLabel(String label) {
		return Arrays.stream(values())
				   .filter(at -> at.label.equals(label))
				   .findAny()
				   .orElse(null);
	}
}
