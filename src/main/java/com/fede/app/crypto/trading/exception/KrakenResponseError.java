package com.fede.app.crypto.trading.exception;

import java.util.List;

/**
 * Created by f.barbano on 04/10/2017.
 */
public class KrakenResponseError extends Exception {

	public KrakenResponseError(String methodName, List<String> errors) {
		super(String.format("Error calling '%s': %s", methodName, errors));
	}
}
