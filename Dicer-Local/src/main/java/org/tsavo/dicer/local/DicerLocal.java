package org.tsavo.dicer.local;
import org.tsavo.dicer.BetResult;
import org.tsavo.dicer.Dicer;
import org.tsavo.dicer.GameInfo;


public class DicerLocal implements Dicer{

	long balance = 100000000;
	
	public long getBalance() {
		return balance;
	}

	public BetResult bet(long amount, float odds) {
		if(amount > balance){
			throw new RuntimeException("Bet larger than balance.");
		}
		int lucky = (int) (Math.random() * 100);
		boolean win = true;
		if(odds * 98 > lucky){
			balance += (amount * ((1 / odds)-1));
		}else{
			balance -= amount;
			win = false;
		}
		return new BetResult(null, amount, (int) (odds * 98), lucky, win, balance);
	}

	public String getDepositAddress() {
		return null;
	}

	public void withdraw(String anAddress, long anAmount, String a2fa) {
	}

	public GameInfo getInfo(float odds) {
		return null;
	}

	public void setBalance(int i) {
		balance = i;
	}
	

}
