package org.tsavo.dicer;

public interface Dicer {

	public int getBalance();
	public BetResult bet(int amount, float odds);
	public String getDepositAddress();
	public void withdraw(String anAddress, int anAmount, String a2fa);
	public GameInfo getInfo(float odds);
	
}
