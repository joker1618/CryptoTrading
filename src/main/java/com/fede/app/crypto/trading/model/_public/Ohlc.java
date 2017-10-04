package com.fede.app.crypto.trading.model._public;

/**
 * Created by f.barbano on 16/09/2017.
 */
public class Ohlc {

	private String pairName;
	private Long time;
	private Double open;
	private Double high;
	private Double low;
	private Double close;
	private Double vwrap;
	private Double volume;
	private Long count;


	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Ohlc)) return false;

		Ohlc ohlc = (Ohlc) o;

		if (pairName != null ? !pairName.equals(ohlc.pairName) : ohlc.pairName != null) return false;
		if (time != null ? !time.equals(ohlc.time) : ohlc.time != null) return false;
		if (open != null ? !open.equals(ohlc.open) : ohlc.open != null) return false;
		if (high != null ? !high.equals(ohlc.high) : ohlc.high != null) return false;
		if (low != null ? !low.equals(ohlc.low) : ohlc.low != null) return false;
		if (close != null ? !close.equals(ohlc.close) : ohlc.close != null) return false;
		if (vwrap != null ? !vwrap.equals(ohlc.vwrap) : ohlc.vwrap != null) return false;
		if (volume != null ? !volume.equals(ohlc.volume) : ohlc.volume != null) return false;
		return count != null ? count.equals(ohlc.count) : ohlc.count == null;
	}

	@Override
	public int hashCode() {
		int result = pairName != null ? pairName.hashCode() : 0;
		result = 31 * result + (time != null ? time.hashCode() : 0);
		result = 31 * result + (open != null ? open.hashCode() : 0);
		result = 31 * result + (high != null ? high.hashCode() : 0);
		result = 31 * result + (low != null ? low.hashCode() : 0);
		result = 31 * result + (close != null ? close.hashCode() : 0);
		result = 31 * result + (vwrap != null ? vwrap.hashCode() : 0);
		result = 31 * result + (volume != null ? volume.hashCode() : 0);
		result = 31 * result + (count != null ? count.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return pairName;
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
	public Double getOpen() {
		return open;
	}
	public void setOpen(Double open) {
		this.open = open;
	}
	public Double getHigh() {
		return high;
	}
	public void setHigh(Double high) {
		this.high = high;
	}
	public Double getLow() {
		return low;
	}
	public void setLow(Double low) {
		this.low = low;
	}
	public Double getClose() {
		return close;
	}
	public void setClose(Double close) {
		this.close = close;
	}
	public Double getVwrap() {
		return vwrap;
	}
	public void setVwrap(Double vwrap) {
		this.vwrap = vwrap;
	}
	public Double getVolume() {
		return volume;
	}
	public void setVolume(Double volume) {
		this.volume = volume;
	}
	public Long getCount() {
		return count;
	}
	public void setCount(Long count) {
		this.count = count;
	}
}