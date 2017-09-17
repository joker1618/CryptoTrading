package com.fede.app.crypto.trading.model;

import java.util.List;

/**
 * Created by f.barbano on 13/09/2017.
 */
public class Spread implements Comparable<Spread> {
	
	private String pairName;
	private long time;
	private double bid;
	private double ask;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Spread)) return false;

		Spread spread = (Spread) o;

		if (time != spread.time) return false;
		if (Double.compare(spread.bid, bid) != 0) return false;
		if (Double.compare(spread.ask, ask) != 0) return false;
		return pairName != null ? pairName.equals(spread.pairName) : spread.pairName == null;
	}

	@Override
	public int hashCode() {
		int result;
		long temp;
		result = pairName != null ? pairName.hashCode() : 0;
		result = 31 * result + (int) (time ^ (time >>> 32));
		temp = Double.doubleToLongBits(bid);
		result = 31 * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(ask);
		result = 31 * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public int compareTo(Spread o) {
		if(this.equals(o))	return 0;
		int res = pairName.compareTo(o.pairName);
		if(res == 0) {
			res = (int)(time - o.time);
			if(res == 0) {
				res = (int)(bid - o.bid);
				if(res == 0) {
					res = (int)(ask - o.ask);
				}
			}
		}
		return res;
	}


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
