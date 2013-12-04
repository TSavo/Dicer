package org.tsavo.dicer.martingale;

import java.util.ArrayList;
import java.util.List;

import org.tsavo.dicer.BetResult;
import org.tsavo.dicer.BettingStrategy;
import org.tsavo.dicer.BettingStrategyListener;
import org.tsavo.dicer.Dicer;

public class MartingaleBettingStrategy implements BettingStrategy {

	public boolean running = false;
	public int startingBet = 1;
	public int currentBet = 1;
	public float odds = 0.5f;
	public float multiplier = 2;
	public float stopLossInAmount = 0;
	public float stopLossInTurns = 0;
	public Dicer dicer;
	public List<BettingStrategyListener> listeners = new ArrayList<BettingStrategyListener>();
	
	public MartingaleBettingStrategy(Dicer aDicer){
		dicer = aDicer;
	}
	public void start() {
		running = true;
		broadcastStart();
		Thread t = new Thread(new Runnable(){

			public void run() {
				int numberOfTurns = 1;
				int balance = dicer.getBalance();
				if(balance < startingBet){
					stop("Starting bet is higher than the current balance.");
					return;
				}
				BetResult result = dicer.bet(startingBet, odds);
				while(running){
					broadcastBet(result);
					if(result.isWin()){
						numberOfTurns = 1;
						balance = result.getBalance();
						currentBet = startingBet;
					}else{
						numberOfTurns++;
						currentBet *= multiplier;
					}
					if(!running){
						break;
					}
					if(balance < currentBet){
						stop("Current bet is higher than the current balance.");
						return;
					}
					if(stopLossInAmount > 0 && balance < stopLossInAmount){
						stop("Balance is less than stop loss.");
						return;
					}
					if(stopLossInTurns > 0 && numberOfTurns >= stopLossInTurns){
						stop("Limit on turns reached.");
						return;
					}
					broadcastBetSizeChanged(currentBet);
					while(running){
						try{
							result = dicer.bet(currentBet, odds);
							break;
						}catch(Exception e){
							e.printStackTrace();
							continue;
						}
					}
				}
				stop("Manually stopped.");
			}

			
		});
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
	
	public void broadcastBet(BetResult aResult){
		for(BettingStrategyListener listener: listeners){
			listener.betMade(aResult);
		}
	}
	private void broadcastBetSizeChanged(int currentBet) {
		for(BettingStrategyListener listener: listeners){
			listener.betSizeChanged(currentBet);
		}
	}
	private void broadcastOddsChanged(float odds) {
		for(BettingStrategyListener listener: listeners){
			listener.oddsChanged(odds);
		}
	}	
	public void broadcastStart(){
		for(BettingStrategyListener listener: listeners){
			listener.started();
		}
	}
	
	public void broadcastStop(String aReason){
		for(BettingStrategyListener listener: listeners){
			listener.stopped(aReason);
		}
	}
	public boolean isRunning() {
		return running;
	}
	public void setRunning(boolean running) {
		this.running = running;
	}
	public int getStartingBet() {
		return startingBet;
	}
	public void setStartingBet(int startingBet) {
		this.startingBet = startingBet;
	}
	public int getCurrentBet() {
		return currentBet;
	}
	public void setCurrentBet(int currentBet) {
		this.currentBet = currentBet;
	}
	public float getOdds() {
		return odds;
	}
	public void setOdds(float odds) {
		this.odds = odds;
		broadcastOddsChanged(odds);
	}
	public float getMultiplier() {
		return multiplier;
	}
	public void setMultiplier(float multiplier) {
		this.multiplier = multiplier;
	}
	public float getStopLossInAmount() {
		return stopLossInAmount;
	}
	public void setStopLossInAmount(float stopLossInAmount) {
		this.stopLossInAmount = stopLossInAmount;
	}
	public float getStopLossInTurns() {
		return stopLossInTurns;
	}
	public void setStopLossInTurns(float stopLossInTurns) {
		this.stopLossInTurns = stopLossInTurns;
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
