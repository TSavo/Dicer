package org.tsavo.dicer;

public class BetResult {

	public int id;
	public int amount;
	public int lessthan;
	public boolean win;
	public int balance;
	public int luckyNumber;
	
	public BetResult(int id, int amount, int lessthan, int luckyNumber,
			boolean win, int balance) {
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
	public int getAmount() {
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
	public int getBalance() {
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
