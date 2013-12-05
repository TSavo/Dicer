package org.tsavo.dicer;

public interface BettingStrategyListener {

	public void betMade(BetResult aResult);
	public void oddsChanged(float theOdds);
	public void betSizeChanged(long start2);

	public void started();
	public void stopped(String aReason);
}
