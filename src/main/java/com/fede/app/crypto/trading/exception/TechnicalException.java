package com.fede.app.crypto.trading.exception;

/**
 * Created by f.barbano on 01/10/2017.
 */
public class TechnicalException extends RuntimeException {

	public TechnicalException(Throwable cause) {
		super(cause);
	}

	public TechnicalException(Throwable cause, String format, Object... params) {
		super(String.format(format, params), cause);
	}
}
