package com.fede.app.crypto.trading.model;

import java.util.List;

/**
 * Created by f.barbano on 13/09/2017.
 */
public class Spread {
	
	private String pairName;
	private Long time;
	private Double bid;
	private Double ask;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Spread)) return false;

		Spread spread = (Spread) o;

		if (pairName != null ? !pairName.equals(spread.pairName) : spread.pairName != null) return false;
		if (time != null ? !time.equals(spread.time) : spread.time != null) return false;
		if (bid != null ? !bid.equals(spread.bid) : spread.bid != null) return false;
		return ask != null ? ask.equals(spread.ask) : spread.ask == null;
	}

	@Override
	public int hashCode() {
		int result = pairName != null ? pairName.hashCode() : 0;
		result = 31 * result + (time != null ? time.hashCode() : 0);
		result = 31 * result + (bid != null ? bid.hashCode() : 0);
		result = 31 * result + (ask != null ? ask.hashCode() : 0);
		return result;
	}

	public String getPairName() {
		return pairName;
	}
	public void setPairName(String pairName) {
		this.pairName = pairName;
	}
	public Long getTime() {
		return time;
	}
	public void setTime(Long time) {
		this.time = time;
	}
	public Double getBid() {
		return bid;
	}
	public void setBid(Double bid) {
		this.bid = bid;
	}
	public Double getAsk() {
		return ask;
	}
	public void setAsk(Double ask) {
		this.ask = ask;
	}
}
