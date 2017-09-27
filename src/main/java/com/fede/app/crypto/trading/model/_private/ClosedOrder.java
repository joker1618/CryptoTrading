package com.fede.app.crypto.trading.model._private;

/**
 * Created by f.barbano on 14/09/2017.
 */
public class ClosedOrder extends OpenOrder {

	// closetm = unix timestamp of when order was closed
	private Long closeTimestamp;
	// reason = additional info on status (if any)
	private String reason;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof ClosedOrder)) return false;
		if (!super.equals(o)) return false;

		ClosedOrder that = (ClosedOrder) o;

		if (closeTimestamp != null ? !closeTimestamp.equals(that.closeTimestamp) : that.closeTimestamp != null)
			return false;
		return reason != null ? reason.equals(that.reason) : that.reason == null;
	}

	@Override
	public int hashCode() {
		int result = super.hashCode();
		result = 31 * result + (closeTimestamp != null ? closeTimestamp.hashCode() : 0);
		result = 31 * result + (reason != null ? reason.hashCode() : 0);
		return result;
	}

	public Long getCloseTimestamp() {
		return closeTimestamp;
	} 
	public void setCloseTimestamp(Long closeTimestamp) {
		this.closeTimestamp = closeTimestamp;
	} 
	public String getReason() {
		return reason;
	} 
	public void setReason(String reason) {
		this.reason = reason;
	}
}
