package org.tsavo.dicer.priority92;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.tsavo.dicer.AbstractBettingStrategy;
import org.tsavo.dicer.BetResult;
import org.tsavo.dicer.Dicer;

public class Priority92BettingStrategy extends AbstractBettingStrategy {

	private List<Long> recoveries = new ArrayList<>();
	public long startingBet = 1000;
	public long getStartingBet() {
		return startingBet;
	}

	public void setStartingBet(long startingBet) {
		this.startingBet = startingBet;
	}

	public long currentBet = 1000;
	public long highscore = 0;

	public Priority92BettingStrategy(Dicer aDicer) {
		super(aDicer);
	}

	@Override
	public Runnable getRunnable() {
		return new Runnable() {
			public void run() {
				long balance = dicer.getBalance();
				if (balance < startingBet) {
					stop("Starting bet is higher than the current balance.");
					return;
				}
				BetResult result = dicer.bet(startingBet, 0.916f);
				while (running) {
					highscore = Math.max(highscore, result.getBalance());
					broadcastBet(result);
					currentBet = startingBet;
					balance = result.getBalance();
					if (!result.isWin() && result.getAmount() < 1000000000) {
						recoveries.add(result.getAmount() * 20);
						Collections.shuffle(recoveries);
					}
					if(highscore - result.getBalance() == 0){
						recoveries.clear();
					}
					if (Math.random() < .1 && recoveries.size() > 0) {
						currentBet = recoveries.remove(0);
					}
					if (!running) {
						break;
					}
					if (balance < currentBet) {
						stop("Current bet is higher than the current balance.");
						return;
					}
					broadcastBetSizeChanged(currentBet);
					while (running) {
						try {
							result = dicer.bet(currentBet, 0.916f);
							break;
						} catch (Exception e) {
							e.printStackTrace();
							break;
						}
					}
				}
				stop("Manually stopped.");
			}

		};
	}

}
