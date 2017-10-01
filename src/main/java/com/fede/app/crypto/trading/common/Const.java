package com.fede.app.crypto.trading.common;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * Created by f.barbano on 15/09/2017.
 */
public class Const {

	public static final Path CONFIG_FILEPATH = Paths.get("config/config.properties");




	// REVIEW remove
	public static final Path CSV_FOLDER = Paths.get("files", "data", "csv");

	public static final String KRAKEN_KEY = "VRQ4Oe8ZNPbLEyjv26SlU7F5O8zR9DjXr07Q6EHf95ooaSszvOkISQTc";
	public static final String KRAKEN_SECRET = "E07VIMtiE1x+0m1ef67ERAB8b3Xrk3Z1hxOlWbEUEB0dqKxghci7a+bnPeEmDrBigdYQ1My24fpPeMW9fOplIw==";

	public static final NumberFormat NUMBER_FORMAT;
	static {
		NUMBER_FORMAT = NumberFormat.getNumberInstance(Locale.ENGLISH);
		NUMBER_FORMAT.setMaximumFractionDigits(12);
		NUMBER_FORMAT.setGroupingUsed(false);
	}
}
