package com.fede.app.crypto.trading.config;

import com.fede.app.crypto.trading.common.Const;
import com.fede.app.crypto.trading.exception.TechnicalException;
import org.apache.commons.lang3.StringUtils;

import javax.management.openmbean.KeyAlreadyExistsException;
import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.logging.Level;

import static com.fede.app.crypto.trading.config.PropKey.*;

/**
 * Created by f.barbano on 01/10/2017.
 */
public class KrakenConfigImpl implements IKrakenConfig {

	private static final IKrakenConfig INSTANCE = new KrakenConfigImpl();
	
	private static final String KEY_SEP = "=";
	private static final String COMMENT_START = "#";
	
	private Map<String, String> configMap;

	private KrakenConfigImpl() {
		this.configMap = new HashMap<>();
		try {
			loadConfigFile(Const.CONFIG_FILEPATH);
		} catch (IOException ex) {
			throw new TechnicalException(ex, "Unable to load properties from path [%s]", Const.CONFIG_FILEPATH.toAbsolutePath());
		}
	}
	
	public static synchronized IKrakenConfig getInstance() {
		return INSTANCE;
	}

	private void loadConfigFile(Path configFilePath) throws IOException {
		InputStream is = new FileInputStream(configFilePath.toFile());
		Map<String, String> loaded = new HashMap<>();

		// read properties from file
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		String line;
		while ((line = reader.readLine()) != null) {
			if(StringUtils.isNotBlank(line.trim()) && !line.trim().startsWith(COMMENT_START) && line.contains(KEY_SEP)) {
				String[] splited = line.split(KEY_SEP);
				String key = splited[0].trim();
				String value = (splited.length > 1) ? line.substring(line.indexOf(KEY_SEP) + 1).trim() : "";
				if(configMap.get(key) != null) {
					throw new KeyAlreadyExistsException("Duplicated key [" + key + "]");
				}
				if(loaded.put(key, value) != null) {
					throw new KeyAlreadyExistsException("Duplicated key [" + key + "]");
				}
			}
		}

		// replace variables
		// #var#  and  ${var}  allowed
		replaceVariables(loaded);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("##########  CONFIGS  ##########\n");
		configMap.forEach((k,v) -> sb.append(k).append(" = ").append(v).append("\n"));
		sb.append("####################à##########");
		return sb.toString();
	}

	@Override
	public synchronized String getKrakenApiKey() {
		return getString(API_KEY);
	}

	@Override
	public synchronized String getKrakenApiSecret() {
		return getString(API_SECRET);
	}

	@Override
	public synchronized boolean isDBEnabled() {
		return getBoolean(RUN_DB_ENABLED);
	}

	@Override
	public synchronized int getCallSecondsRateAssets() {
		return getInt(CALL_RATE_ASSETS);
	}

	@Override
	public synchronized int getCallSecondsRateAssetPairs() {
		return getInt(CALL_RATE_ASSET_PAIRS);
	}

	@Override
	public synchronized int getCallSecondsRateTickers() {
		return getInt(CALL_RATE_TICKERS);
	}

	@Override
	public synchronized int getCallSecondsRateSpreadData() {
		return getInt(CALL_RATE_SPREAD_DATA);
	}

	@Override
	public synchronized String getDBUrl() {
		return getString(DB_URL);
	}

	@Override
	public synchronized String getDBUsername() {
		return getString(DB_USERNAME);
	}

	@Override
	public synchronized String getDBPassword() {
		return getString(DB_PASSWORD);
	}

	@Override
	public synchronized Path getCsvPathAssets() {
		return getPath(CSV_FOLDER, CSV_FILENAME_ASSETS);
	}

	@Override
	public synchronized Path getCsvPathAssetPairs() {
		return getPath(CSV_FOLDER, CSV_FILENAME_ASSET_PAIRS);
	}

	@Override
	public synchronized Path getCsvPathTickers() {
		return getPath(CSV_FOLDER, CSV_FILENAME_TICKERS);
	}

	@Override
	public synchronized Path getCsvPathSpreadData() {
		return getPath(CSV_FOLDER, CSV_FILENAME_SPREAD_DATA);
	}

	@Override
	public synchronized Path getCsvPathAccountBalance() {
		return getPath(CSV_FOLDER, CSV_FILENAME_ACCOUNT_BALANCE);
	}

	@Override
	public Path getLogPathErrors() {
		return getPath(LOGS_FOLDER).resolve("errors.log");
	}

	@Override
	public Path getLogPathAll() {
		return getPath(LOGS_FOLDER).resolve("all.log");
	}

	@Override
	public Level getLogLevelConsole() {
		try {
			return Level.parse(getString(LOGS_CONSOLE_LEVEL));
		} catch(IllegalArgumentException ex) {
			return Level.ALL;
		}
	}


	private String getString(String propKey) {
		return configMap.get(propKey);
	}
	private boolean getBoolean(String propKey) {
		return Boolean.valueOf(getString(propKey));
	}
	private int getInt(String propKey) {
		return Integer.parseInt(getString(propKey));
	}
	private Path getPath(String key) {
		Path baseFolder = Paths.get(getString(BASE_FOLDER));
		return baseFolder.resolve(getString(key));
	}
	private Path getPath(String folderKey, String filenameKey) {
		Path folder = getPath(folderKey);
		return folder.resolve(getString(filenameKey));
	}

	private void replaceVariables(Map<String, String> loaded) {

		// add all properties that haven't variables in the value
		for(String key : new HashSet<>(loaded.keySet())) {
			String value = loaded.get(key);
			Var var = getNextVariable(value);
			if(var == null) {
				configMap.put(key, value);
				loaded.remove(key);
			}
		}

		// try to replace variables
		boolean on = true;
		while(on) {
			boolean atLeastOne = false;
			for (String key : new HashSet<>(loaded.keySet())) {
				String value = loaded.get(key);
				Var var = getNextVariable(value);
				boolean go = true;
				while (var != null && go) {
					String toIns = configMap.get(var.varName);
					if (StringUtils.isNotBlank(toIns)) {
						value = value.replace(var.placeholder, toIns);
						var = getNextVariable(value);
					} else {
						go = false;
					}
				}

				if (go) {
					configMap.put(key, value);
					loaded.remove(key);
					atLeastOne = true;
				}
			}

			if(!atLeastOne || loaded.isEmpty()) {
				on = false;
			}
		}

		// put remaining entry, where some variables in value are not replaced
		configMap.putAll(loaded);
	}

	// return the next variable found:   #var#  or  ${var}
	private Var getNextVariable(String value) {
		String varName = StringUtils.substringBetween(value, "#", "#");
		if(StringUtils.isNotBlank(varName)) {
			return new Var(varName, "#" + varName + "#");
		}

		varName = StringUtils.substringBetween(value, "${", "}");
		if(StringUtils.isNotBlank(varName)) {
			return new Var(varName, "${" + varName + "}");
		}

		return null;
	}

	private class Var {
		String varName;
		String placeholder;

		Var(String varName, String placeholder) {
			this.varName = varName;
			this.placeholder = placeholder;
		}
	}
}
