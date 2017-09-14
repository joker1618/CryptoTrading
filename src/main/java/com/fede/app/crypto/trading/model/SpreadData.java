package com.fede.app.crypto.trading.model;

import com.fede.app.crypto.trading.types.ActionType;
import com.fede.app.crypto.trading.types.OrderType;

import java.util.List;

/**
 * Created by f.barbano on 13/09/2017.
 */
public class SpreadData {
	
	private String pairName;
	private List<SpreadInfo> spreadInfoList;
	private long last;		// id to be used as since when polling for new spread data

	public String getPairName() {
		return pairName;
	}
	public void setPairName(String pairName) {
		this.pairName = pairName;
	}
	public List<SpreadInfo> getSpreadInfoList() {
		return spreadInfoList;
	}
	public void setSpreadInfoList(List<SpreadInfo> spreadInfoList) {
		this.spreadInfoList = spreadInfoList;
	}
	public long getLast() {
		return last;
	}
	public void setLast(long last) {
		this.last = last;
	}

	public class SpreadInfo {
		private long time;
		private double bid;
		private double ask;

		public long getTime() {
			return time;
		}
		public void setTime(long time) {
			this.time = time;
		}
		public double getBid() {
			return bid;
		}
		public void setBid(double bid) {
			this.bid = bid;
		}
		public double getAsk() {
			return ask;
		}
		public void setAsk(double ask) {
			this.ask = ask;
		}
	}

	
}
