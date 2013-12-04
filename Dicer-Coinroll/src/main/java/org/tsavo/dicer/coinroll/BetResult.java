package org.tsavo.dicer.coinroll;


public class BetResult {
	public long result;
	public String id;
	public long lessthan;
	public long amount;
	public long lucky;
	public long nonce;
	public boolean win;
	public long diff;
	public String error;
	public String display;
	public String errorcode;
	
	public String getDisplay() {
		return display;
	}
	public void setDisplay(String display) {
		this.display = display;
	}
	public String getErrorcode() {
		return errorcode;
	}
	public void setErrorcode(String errorcode) {
		this.errorcode = errorcode;
	}
	public float multiplier;
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public float getMultiplier() {
		return multiplier;
	}
	public void setMultiplier(float multiplier) {
		this.multiplier = multiplier;
	}
	public long getResult() {
		return result;
	}
	public void setResult(long result) {
		this.result = result;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public long getLessthan() {
		return lessthan;
	}
	public void setLessthan(long lessthan) {
		this.lessthan = lessthan;
	}
	public long getAmount() {
		return amount;
	}
	public void setAmount(long amount) {
		this.amount = amount;
	}
	public long getLucky() {
		return lucky;
	}
	public void setLucky(long lucky) {
		this.lucky = lucky;
	}
	public long getNonce() {
		return nonce;
	}
	public void setNonce(long nonce) {
		this.nonce = nonce;
	}
	public boolean isWin() {
		return win;
	}
	public void setWin(boolean win) {
		this.win = win;
	}
	public long getDiff() {
		return diff;
	}
	public void setDiff(long diff) {
		this.diff = diff;
	}
	public long getBalance() {
		return balance;
	}
	public void setBalance(long balance) {
		this.balance = balance;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public long getProfit() {
		return profit;
	}
	public void setProfit(long profit) {
		this.profit = profit;
	}
	public long getBets() {
		return bets;
	}
	public void setBets(long bets) {
		this.bets = bets;
	}
	public long getWins() {
		return wins;
	}
	public void setWins(long wins) {
		this.wins = wins;
	}
	public long balance;
	public String date;
	public long profit;
	public long bets;
	public long wins;
}
