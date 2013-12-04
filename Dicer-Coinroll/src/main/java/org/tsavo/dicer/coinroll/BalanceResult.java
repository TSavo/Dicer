package org.tsavo.dicer.coinroll;


public class BalanceResult {

	int result;
	long balance;
	long profit;
	long bets;
	long wins;
	String pendingtx;

	public String getPendingtx() {
		return pendingtx;
	}
	public void setPendingtx(String pendingtx) {
		this.pendingtx = pendingtx;
	}
	public int getResult() {
		return result;
	}
	public void setResult(int result) {
		this.result = result;
	}
	public long getBalance() {
		return balance;
	}
	public void setBalance(long balance) {
		this.balance = balance;
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
	
}
