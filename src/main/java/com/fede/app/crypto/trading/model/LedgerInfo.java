package com.fede.app.crypto.trading.model;

import com.fede.app.crypto.trading.model.types.LedgerType;

/**
 * Created by f.barbano on 14/09/2017.
 */
public class LedgerInfo {

	//<ledger_id> = ledger info
	private String ledgerID;
	// refid = reference id
	private String refID;
	// time = unx timestamp of ledger
	private Long time;
	// type = type of ledger entry
	private LedgerType ledgerType;
	// aclass = asset class
	private String assetClass;
	// asset = asset
	private String assetName;
	// amount = transaction amount
	private Double amount;
	// fee = transaction fee
	private Double fee;
	// balance = resulting balance
	private Double balance;


	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof LedgerInfo)) return false;

		LedgerInfo that = (LedgerInfo) o;

		if (ledgerID != null ? !ledgerID.equals(that.ledgerID) : that.ledgerID != null) return false;
		if (refID != null ? !refID.equals(that.refID) : that.refID != null) return false;
		if (time != null ? !time.equals(that.time) : that.time != null) return false;
		if (ledgerType != that.ledgerType) return false;
		if (assetClass != null ? !assetClass.equals(that.assetClass) : that.assetClass != null) return false;
		if (assetName != null ? !assetName.equals(that.assetName) : that.assetName != null) return false;
		if (amount != null ? !amount.equals(that.amount) : that.amount != null) return false;
		if (fee != null ? !fee.equals(that.fee) : that.fee != null) return false;
		return balance != null ? balance.equals(that.balance) : that.balance == null;
	}

	@Override
	public int hashCode() {
		int result = ledgerID != null ? ledgerID.hashCode() : 0;
		result = 31 * result + (refID != null ? refID.hashCode() : 0);
		result = 31 * result + (time != null ? time.hashCode() : 0);
		result = 31 * result + (ledgerType != null ? ledgerType.hashCode() : 0);
		result = 31 * result + (assetClass != null ? assetClass.hashCode() : 0);
		result = 31 * result + (assetName != null ? assetName.hashCode() : 0);
		result = 31 * result + (amount != null ? amount.hashCode() : 0);
		result = 31 * result + (fee != null ? fee.hashCode() : 0);
		result = 31 * result + (balance != null ? balance.hashCode() : 0);
		return result;
	}

	@Override
	public String toString() {
		return ledgerID;
	}


	public String getLedgerID() {
		return ledgerID;
	}
	public void setLedgerID(String ledgerID) {
		this.ledgerID = ledgerID;
	}
	public String getRefID() {
		return refID;
	}
	public void setRefID(String refID) {
		this.refID = refID;
	}
	public Long getTime() {
		return time;
	}
	public void setTime(Long time) {
		this.time = time;
	}
	public LedgerType getLedgerType() {
		return ledgerType;
	}
	public void setLedgerType(LedgerType ledgerType) {
		this.ledgerType = ledgerType;
	}
	public String getAssetClass() {
		return assetClass;
	}
	public void setAssetClass(String assetClass) {
		this.assetClass = assetClass;
	}
	public String getAssetName() {
		return assetName;
	}
	public void setAssetName(String assetName) {
		this.assetName = assetName;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	public Double getFee() {
		return fee;
	}
	public void setFee(Double fee) {
		this.fee = fee;
	}
	public Double getBalance() {
		return balance;
	}
	public void setBalance(Double balance) {
		this.balance = balance;
	}
}
