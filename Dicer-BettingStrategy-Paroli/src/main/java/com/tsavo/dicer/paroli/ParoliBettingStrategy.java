package com.tsavo.dicer.paroli;

import java.util.Collections;

import org.tsavo.dicer.AbstractBettingStrategy;
import org.tsavo.dicer.BetResult;
import org.tsavo.dicer.Dicer;

public class ParoliBettingStrategy extends AbstractBettingStrategy {

	public long startingBet = 10;

	public long getStartingBet() {
		return startingBet;
	}

	public void setStartingBet(long startingBet) {
		this.startingBet = startingBet;
	}

	public long currentBet = 10;
	public long highscore = 0;
	public int winsInARow = 0;

	public ParoliBettingStrategy(Dicer aDicer) {
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
					balance = result.getBalance();
					if (result.isWin()) {
						winsInARow++;
						if (winsInARow < 3) {
							currentBet *= 20;
						} else {
							currentBet = startingBet;
							winsInARow=0;
						}
					} else {
						winsInARow = 0;
						currentBet = startingBet;
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
							result = dicer.bet(currentBet, 0.6f);
							break;
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
				stop("Manually stopped.");
			}

		};
	}

}
