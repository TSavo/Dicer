package org.tsavo.dicer;

public interface Dicer {

	public long getBalance();
	public BetResult bet(long amount, float odds);
	public String getDepositAddress();
	public void withdraw(String anAddress, long anAmount, String a2fa);
	public GameInfo getInfo(float odds);
	
}
