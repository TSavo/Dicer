package org.tsavo.dicer;

public interface BettingStrategy {

	public void start();
	public void stop(String aReason);
	public void addBettingStrategyListener(BettingStrategyListener aListener);
	public void removeBettingStrategyListener(BettingStrategyListener aListener);
}
