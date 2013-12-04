package org.tsavo.dicer;

public class BetResult {

	public String id;
	public long amount;
	public long lessthan;
	public boolean win;
	public long balance;
	public long luckyNumber;
	
	public BetResult(String id, long amount, long lessthan, long luckyNumber,
			boolean win, long balance) {
		super();
		this.id = id;
		this.amount = amount;
		this.lessthan = lessthan;
		this.luckyNumber = luckyNumber;
		this.win = win;
		this.balance = balance;
	}
	
	public BetResult(){
		
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public long getAmount() {
		return amount;
	}
	public void setAmount(long amount) {
		this.amount = amount;
	}
	public long getLessthan() {
		return lessthan;
	}
	public void setLessthan(long lessthan) {
		this.lessthan = lessthan;
	}
	public boolean isWin() {
		return win;
	}
	public void setWin(boolean win) {
		this.win = win;
	}
	public long getBalance() {
		return balance;
	}
	public void setBalance(long balance) {
		this.balance = balance;
	}
	public long getLuckyNumber() {
		return luckyNumber;
	}
	public void setLuckyNumber(long luckyNumber) {
		this.luckyNumber = luckyNumber;
	}
	
}
