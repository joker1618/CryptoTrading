package com.fede.app.crypto.trading.dao;

import java.util.Collection;
import java.util.Map;

/**
 * Created by f.barbano on 11/10/2017.
 */
public interface ILastCallTimeDao {

	// review all

	class LastCallDescr {
		private String elemKey;
		private String methodName;
		private Long lastTime;

		public LastCallDescr() {
		}

		public LastCallDescr(String elemKey, String methodName, Long lastTime) {
			this.elemKey = elemKey;
			this.methodName = methodName;
			this.lastTime = lastTime;
		}

		public String getElemKey() {
			return elemKey;
		}
		public void setElemKey(String elemKey) {
			this.elemKey = elemKey;
		}
		public String getMethodName() {
			return methodName;
		}
		public void setMethodName(String methodName) {
			this.methodName = methodName;
		}
		public Long getLastTime() {
			return lastTime;
		}
		public void setLastTime(Long lastTime) {
			this.lastTime = lastTime;
		}
	}

	Map<String, Long> getLastCallTimes(String methodName);
	void persistLastCallTimes(Collection<LastCallDescr> lastCallDescrs);
	
}
