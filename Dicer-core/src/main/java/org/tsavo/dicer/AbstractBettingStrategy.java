package org.tsavo.dicer;

import java.util.ArrayList;
import java.util.List;

public abstract class AbstractBettingStrategy implements BettingStrategy {

	public boolean running = false;
	public Dicer dicer;
	public List<BettingStrategyListener> listeners = new ArrayList<BettingStrategyListener>();

	public AbstractBettingStrategy(Dicer aDicer) {
		dicer = aDicer;
	}

	public abstract Runnable getRunnable();

	public void start(){
		running = true;
		broadcastStart();
		Thread t = new Thread(getRunnable());
		t.setDaemon(true);
		t.start();
	}

	public void stop(String aReason) {
		running = false;
		broadcastStop(aReason);
	}

	public void addBettingStrategyListener(BettingStrategyListener aListener) {
		listeners.add(aListener);
	}

	public void removeBettingStrategyListener(BettingStrategyListener aListener) {
		listeners.remove(aListener);
	}

	protected void broadcastBet(BetResult aResult) {
		for (BettingStrategyListener listener : listeners) {
			listener.betMade(aResult);
		}
	}

	protected void broadcastBetSizeChanged(long currentBet) {
		for (BettingStrategyListener listener : listeners) {
			listener.betSizeChanged(currentBet);
		}
	}

	protected void broadcastOddsChanged(float odds) {
		for (BettingStrategyListener listener : listeners) {
			listener.oddsChanged(odds);
		}
	}

	protected void broadcastStart() {
		for (BettingStrategyListener listener : listeners) {
			listener.started();
		}
	}

	protected void broadcastStop(String aReason) {
		for (BettingStrategyListener listener : listeners) {
			listener.stopped(aReason);
		}
	}

	public boolean isRunning() {
		return running;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}

	public Dicer getDicer() {
		return dicer;
	}

	public void setDicer(Dicer dicer) {
		this.dicer = dicer;
	}

	public List<BettingStrategyListener> getListeners() {
		return listeners;
	}

	public void setListeners(List<BettingStrategyListener> listeners) {
		this.listeners = listeners;
	}

}
