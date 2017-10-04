package com.fede.app.crypto.trading.logger;

/**
 * Created by f.barbano on 01/10/2017.
 */
public interface ISimpleLog {

	void error(String mex, Object... params);
	void warning(String mex, Object... params);
	void info(String mex, Object... params);
	void config(String mex, Object... params);
	void fine(String mex, Object... params);
	void finer(String mex, Object... params);
	void finest(String mex, Object... params);

}
