package com.fede.app.crypto.trading.logger;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by f.barbano on 01/10/2017.
 */
class SimpleLogImpl implements SimpleLog {

	private Logger logger;

	SimpleLogImpl(Logger logger) {
		this.logger = logger;
	}

	@Override
	public synchronized void error(String mex, Object... params) {
		logger.log(Level.SEVERE, String.format(mex, params));
	}
	@Override
	public synchronized void warning(String mex, Object... params) {
		logger.log(Level.WARNING, String.format(mex, params));
	}
	@Override
	public synchronized void info(String mex, Object... params) {
		logger.log(Level.INFO, String.format(mex, params));
	}
	@Override
	public synchronized void config(String mex, Object... params) {
		logger.log(Level.CONFIG, String.format(mex, params));
	}
	@Override
	public synchronized void fine(String mex, Object... params) {
		logger.log(Level.FINE, String.format(mex, params));
	}
	@Override
	public synchronized void finer(String mex, Object... params) {
		logger.log(Level.FINER, String.format(mex, params));
	}
	@Override
	public synchronized void finest(String mex, Object... params) {
		logger.log(Level.FINEST, String.format(mex, params));
	}

}