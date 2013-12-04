package org.tsavo.dicer;

public class BetResult {

	public int id;
	public long amount;
	public int lessthan;
	public boolean win;
	public long balance;
	public int luckyNumber;
	
	public BetResult(int id, long amount, int lessthan, int luckyNumber,
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
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public long getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public int getLessthan() {
		return lessthan;
	}
	public void setLessthan(int lessthan) {
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
	public void setBalance(int balance) {
		this.balance = balance;
	}
	public int getLuckyNumber() {
		return luckyNumber;
	}
	public void setLuckyNumber(int luckyNumber) {
		this.luckyNumber = luckyNumber;
	}
	
}
