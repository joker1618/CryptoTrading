package com.fede.app.crypto.trading.exception;

/**
 * Created by f.barbano on 04/10/2017.
 */
public class KrakenCallException extends Exception {

	public KrakenCallException(Throwable cause, String methodName) {
		super("Method call: " + methodName, cause);
	}
}
