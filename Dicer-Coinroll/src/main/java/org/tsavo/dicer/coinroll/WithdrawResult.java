package org.tsavo.dicer.coinroll;


public class WithdrawResult {

	int result;
	boolean deferred;
	int amount;
	String txid;
	int balance;
	public int getResult() {
		return result;
	}
	public void setResult(int result) {
		this.result = result;
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
	public String getTxid() {
		return txid;
	}
	public void setTxid(String txid) {
		this.txid = txid;
	}
	public int getBalance() {
		return balance;
	}
	public void setBalance(int balance) {
		this.balance = balance;
	}
	
	
	
}
