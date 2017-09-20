package com.fede.app.crypto.trading.model;

/**
 * Created by f.barbano on 14/09/2017.
 */
public class TradeBalance {

	private Long callTime;
//	eb = equivalent balance (combined balance of all currencies)
	private Double equivBalance;
//	tb = trade balance (combined balance of all equity currencies)
	private Double tradeBalance;
//	m = margin amount of open positions
	private Double marginAmount;
//	n = unrealized net profit/loss of open positions
	private Double unrealizedProfitLoss;
//	c = cost basis of open positions
	private Double basisCost;
//	v = current floating valuation of open positions
	private Double currentValuation;
//	e = equity = trade balance + unrealized net profit/loss
	private Double equity;
//	mf = free margin = equity - initial margin (maximum margin available to open new positions)
	private Double freeMargin;
//	ml = margin level = (equity / initial margin) * 100
	private Double marginLevel;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof TradeBalance)) return false;

		TradeBalance that = (TradeBalance) o;

		if (callTime != null ? !callTime.equals(that.callTime) : that.callTime != null) return false;
		if (equivBalance != null ? !equivBalance.equals(that.equivBalance) : that.equivBalance != null) return false;
		if (tradeBalance != null ? !tradeBalance.equals(that.tradeBalance) : that.tradeBalance != null) return false;
		if (marginAmount != null ? !marginAmount.equals(that.marginAmount) : that.marginAmount != null) return false;
		if (unrealizedProfitLoss != null ? !unrealizedProfitLoss.equals(that.unrealizedProfitLoss) : that.unrealizedProfitLoss != null)
			return false;
		if (basisCost != null ? !basisCost.equals(that.basisCost) : that.basisCost != null) return false;
		if (currentValuation != null ? !currentValuation.equals(that.currentValuation) : that.currentValuation != null)
			return false;
		if (equity != null ? !equity.equals(that.equity) : that.equity != null) return false;
		if (freeMargin != null ? !freeMargin.equals(that.freeMargin) : that.freeMargin != null) return false;
		return marginLevel != null ? marginLevel.equals(that.marginLevel) : that.marginLevel == null;
	}

	@Override
	public int hashCode() {
		int result = callTime != null ? callTime.hashCode() : 0;
		result = 31 * result + (equivBalance != null ? equivBalance.hashCode() : 0);
		result = 31 * result + (tradeBalance != null ? tradeBalance.hashCode() : 0);
		result = 31 * result + (marginAmount != null ? marginAmount.hashCode() : 0);
		result = 31 * result + (unrealizedProfitLoss != null ? unrealizedProfitLoss.hashCode() : 0);
		result = 31 * result + (basisCost != null ? basisCost.hashCode() : 0);
		result = 31 * result + (currentValuation != null ? currentValuation.hashCode() : 0);
		result = 31 * result + (equity != null ? equity.hashCode() : 0);
		result = 31 * result + (freeMargin != null ? freeMargin.hashCode() : 0);
		result = 31 * result + (marginLevel != null ? marginLevel.hashCode() : 0);
		return result;
	}

	public Long getCallTime() {
		return callTime;
	}
	public void setCallTime(Long callTime) {
		this.callTime = callTime;
	}
	public Double getEquivBalance() {
		return equivBalance;
	}
	public void setEquivBalance(Double equivBalance) {
		this.equivBalance = equivBalance;
	}
	public Double getTradeBalance() {
		return tradeBalance;
	}
	public void setTradeBalance(Double tradeBalance) {
		this.tradeBalance = tradeBalance;
	}
	public Double getMarginAmount() {
		return marginAmount;
	}
	public void setMarginAmount(Double marginAmount) {
		this.marginAmount = marginAmount;
	}
	public Double getUnrealizedProfitLoss() {
		return unrealizedProfitLoss;
	}
	public void setUnrealizedProfitLoss(Double unrealizedProfitLoss) {
		this.unrealizedProfitLoss = unrealizedProfitLoss;
	}
	public Double getBasisCost() {
		return basisCost;
	}
	public void setBasisCost(Double basisCost) {
		this.basisCost = basisCost;
	}
	public Double getCurrentValuation() {
		return currentValuation;
	}
	public void setCurrentValuation(Double currentValuation) {
		this.currentValuation = currentValuation;
	}
	public Double getEquity() {
		return equity;
	}
	public void setEquity(Double equity) {
		this.equity = equity;
	}
	public Double getFreeMargin() {
		return freeMargin;
	}
	public void setFreeMargin(Double freeMargin) {
		this.freeMargin = freeMargin;
	}
	public Double getMarginLevel() {
		return marginLevel;
	}
	public void setMarginLevel(Double marginLevel) {
		this.marginLevel = marginLevel;
	}
}
