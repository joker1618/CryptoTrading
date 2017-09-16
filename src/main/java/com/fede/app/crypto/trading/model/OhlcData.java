package com.fede.app.crypto.trading.model;

import java.util.List;

/**
 * Created by f.barbano on 13/09/2017.
 */
public class OHLCData {

	private String pairName;
	private List<OHLCInfo> OHLCInfos;
	private long last;       // last committed/valid OHLC data (to be used as 'since' parameter)

	public String getPairName() {
		return pairName;
	}
	public void setPairName(String pairName) {
		this.pairName = pairName;
	}
	public List<OHLCInfo> getOHLCInfos() {
		return OHLCInfos;
	}
	public void setOHLCInfos(List<OHLCInfo> OHLCInfos) {
		this.OHLCInfos = OHLCInfos;
	}
	public long getLast() {
		return last;
	}
	public void setLast(long last) {
		this.last = last;
	}

	
	public static class OHLCInfo implements Comparable<OHLCInfo> {
		private long time;
		private double open;
		private double high;
		private double low;
		private double close;
		private double vwrap;
		private double volume;
		private long count;

		public long getTime() {
			return time;
		}
		public void setTime(long time) {
			this.time = time;
		}
		public double getOpen() {
			return open;
		}
		public void setOpen(double open) {
			this.open = open;
		}
		public double getHigh() {
			return high;
		}
		public void setHigh(double high) {
			this.high = high;
		}
		public double getLow() {
			return low;
		}
		public void setLow(double low) {
			this.low = low;
		}
		public double getClose() {
			return close;
		}
		public void setClose(double close) {
			this.close = close;
		}
		public double getVwrap() {
			return vwrap;
		}
		public void setVwrap(double vwrap) {
			this.vwrap = vwrap;
		}
		public double getVolume() {
			return volume;
		}
		public void setVolume(double volume) {
			this.volume = volume;
		}
		public long getCount() {
			return count;
		}
		public void setCount(long count) {
			this.count = count;
		}

		@Override
		public int compareTo(OHLCInfo o) {
			return (time < o.time) ? -1 : (time > o.time) ? 1 : 0;
		}
	}
}
