package com.fede.app.crypto.trading.common;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by f.barbano on 15/09/2017.
 */
public class Const {

	public static final Path CONFIG_PATH = Paths.get("config", "config.properties");

	public static final Path LOG_ALL_PATH = Paths.get("logs", "cryptoPublic_ALL.log");
	public static final Path LOG_WARN_PATH = Paths.get("logs", "cryptoPublic_WARN.log");

	
	public static final String JDBC_DRIVER_CLASS = "com.mysql.jdbc.Driver";



	// REVIEW remove
	public static final String KRAKEN_KEY = "vC/BbE6uRpVSbw1bcewtc3uErxdAg6SrxIyGh9Gt+bnRNBhuvGH8WZsY";
	public static final String KRAKEN_SECRET = "yhTNpydBCUOEpjs5jl87P1udrsAhHN7cKYX2+cxoCaibXruqHcdEGA0W4efrEduUzpopqI0HHITIU0xFocZMng==";

	public static final NumberFormat NUMBER_FORMAT;
	static {
		NUMBER_FORMAT = NumberFormat.getNumberInstance(Locale.ENGLISH);
		NUMBER_FORMAT.setMaximumFractionDigits(12);
		NUMBER_FORMAT.setGroupingUsed(false);
	}

}
