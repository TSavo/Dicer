package org.tsavo.dicer.coinroll;


public class DepositStatusResult {
	int result;
	boolean confirmed;
	boolean deferred;
	int amount;
	public int getResult() {
		return result;
	}
	public void setResult(int result) {
		this.result = result;
	}
	public boolean isConfirmed() {
		return confirmed;
	}
	public void setConfirmed(boolean confirmed) {
		this.confirmed = confirmed;
	}
	public boolean isDeferred() {
		return deferred;
	}
	public void setDeferred(boolean deferred) {
		this.deferred = deferred;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}

}
