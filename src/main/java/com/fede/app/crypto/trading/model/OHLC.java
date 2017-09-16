package com.fede.app.crypto.trading.model;

/**
 * Created by f.barbano on 16/09/2017.
 */
public class OHLC implements Comparable<OHLC> {

	private String pairName;
	private long time;
	private double open;
	private double high;
	private double low;
	private double close;
	private double vwrap;
	private double volume;
	private long count;


	public String getPairName() {
		return pairName;
	}
	public void setPairName(String pairName) {
		this.pairName = pairName;
	}
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
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof OHLC)) return false;

		OHLC ohlc = (OHLC) o;

		if (time != ohlc.time) return false;
		if (Double.compare(ohlc.open, open) != 0) return false;
		if (Double.compare(ohlc.high, high) != 0) return false;
		if (Double.compare(ohlc.low, low) != 0) return false;
		if (Double.compare(ohlc.close, close) != 0) return false;
		if (Double.compare(ohlc.vwrap, vwrap) != 0) return false;
		if (Double.compare(ohlc.volume, volume) != 0) return false;
		if (count != ohlc.count) return false;
		return pairName != null ? pairName.equals(ohlc.pairName) : ohlc.pairName == null;
	}

	@Override
	public int hashCode() {
		int result;
		long temp;
		result = pairName != null ? pairName.hashCode() : 0;
		result = 31 * result + (int) (time ^ (time >>> 32));
		temp = Double.doubleToLongBits(open);
		result = 31 * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(high);
		result = 31 * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(low);
		result = 31 * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(close);
		result = 31 * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(vwrap);
		result = 31 * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(volume);
		result = 31 * result + (int) (temp ^ (temp >>> 32));
		result = 31 * result + (int) (count ^ (count >>> 32));
		return result;
	}

	@Override
	public int compareTo(OHLC o) {
		if(this.equals(o))	return 0;

		int res = pairName.compareTo(o.pairName);
		if(res != 0)	return res;

		return (int)(time - o.time);
	}
}
