package org.tsavo.dicer.rapid;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.tsavo.dicer.BetResult;
import org.tsavo.dicer.BettingStrategy;
import org.tsavo.dicer.BettingStrategyListener;
import org.tsavo.dicer.Dicer;

public class RapidRecoveryBettingStrategy implements BettingStrategy {

	Dicer dicer;

	List<BettingStrategyListener> listeners = new ArrayList<>();

	public RapidRecoveryBettingStrategy(Dicer aDicer) {
		dicer = aDicer;
	}

	boolean inBadStreak = false;
	int badStreak = 16;
	float multiplier = 2f;

	public int getBadStreak() {
		return badStreak;
	}

	public void setBadStreak(int badStreak) {
		this.badStreak = badStreak;
	}

	public float getMultiplier() {
		return multiplier;
	}

	public void setMultiplier(float multiplier) {
		this.multiplier = multiplier;
	}

	int normalRisk = 200;

	public boolean isInBadStreak() {
		return inBadStreak;
	}

	public void setInBadStreak(boolean inBadStreak) {
		this.inBadStreak = inBadStreak;
	}

	public int getNormalRisk() {
		return normalRisk;
	}

	public void setNormalRisk(int normalRisk) {
		this.normalRisk = normalRisk;
	}

	public int getLossRisk() {
		return lossRisk;
	}

	public void setLossRisk(int lossRisk) {
		this.lossRisk = lossRisk;
	}

	int lossRisk = 10;
	long start = 5;
	int losses = 0;
	float profit = 0;

	public boolean running = false;
	public BalanceListener balanceListener;
	public ComputedRiskListener computedRiskListener;
	public HighscoreListener highscoreListener;
	public StartingBetListener startingBetListener;

	public StartingBetListener getStartingBetListener() {
		return startingBetListener;
	}

	public void setStartingBetListener(StartingBetListener startingBetListener) {
		this.startingBetListener = startingBetListener;
	}

	public HighscoreListener getHighscoreListener() {
		return highscoreListener;
	}

	public void setHighscoreListener(HighscoreListener highscoreListener) {
		this.highscoreListener = highscoreListener;
	}

	public ComputedRiskListener getComputedRiskListener() {
		return computedRiskListener;
	}

	public void setComputedRiskListener(
			ComputedRiskListener computedRiskListener) {
		this.computedRiskListener = computedRiskListener;
	}

	public BalanceListener getBalanceListener() {
		return balanceListener;
	}

	public void setBalanceListener(BalanceListener balanceListener) {
		this.balanceListener = balanceListener;
	}

	long lastWin = System.currentTimeMillis();

	public void start() {
		running = true;
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub

				while(dicer.getBalance() < 1000){
					try {
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						return;
					}
				}
				BetResult bet = dicer.bet(1, .498f);
				long highscore = bet.getBalance();

				//DecimalFormat format = new DecimalFormat("#.########");
				while (running) {
					if (bet.isWin() || (bet.getBalance() - highscore) > 0) {

						if (bet.getBalance() - highscore > 0) {
							profit += bet.getBalance() - highscore;
						}
						long myWin = System.currentTimeMillis();
						if (profit >= 10000) {
//							System.out.println(new Date()
//									+ " "
//									+ format.format(((float) bet.getBalance()) / 100000000)
//									+ " ("
//									+ format.format((profit) / 100000000)
//									+ " in " + ((myWin - lastWin) / 1000)
//									+ " seconds)");
							lastWin = myWin;
							// System.out.println(format.format((profit) /
							// 100000000));
							profit = 0;
						}
						highscore = Math.max(highscore, bet.getBalance());
						long loss = highscore - bet.getBalance();
						if (loss > (bet.getBalance() / lossRisk)) {
							highscore = 0;
						}
						losses = 0;

						if (loss > 0) {
							start = loss;
						} else {
							start = bet.getBalance() / 50000;
						}

						start = findBestRisk(
								start,
								multiplier,
								badStreak,
								loss > 0 ? (bet.getBalance() / lossRisk) : (bet
										.getBalance() / normalRisk));
						start = Math.max(2, start);
						long totalRisk = getDifficulty(start, multiplier,
								badStreak);

						if (computedRiskListener != null) {
							computedRiskListener.setComputedRisk(totalRisk);
						}
						if (highscoreListener != null) {
							highscoreListener.setHighscore(highscore);
						}
						if (startingBetListener != null) {
							startingBetListener.setStartingBet(start);
						}
						broadcastBetSizeChanged(start);
						if (loss == 0 && inBadStreak) {
							inBadStreak = false;
							System.out.println("Recovered: " + bet.getBalance()
									+ ", returning to " + totalRisk + " risk.");
						}

						if (loss > 0 && !inBadStreak) {
							System.out.println("Loss detected: "
									+ bet.getBalance() + ", recovering to "
									+ highscore + " at " + totalRisk + " risk.");
							inBadStreak = true;
						}

					} else {
						// System.out.print(".");
						losses++;
						start *= multiplier;
						if (startingBetListener != null) {
							startingBetListener.setStartingBet(start);
						}
					}
					if (losses >= badStreak) {
						long risk = getDifficulty(
								findBestRisk(2, multiplier, badStreak,
										bet.getBalance() / lossRisk),
								multiplier, badStreak);
						System.out.println("BAD STREAK: " + bet.getBalance()
								+ ", recovering to " + highscore + " at "
								+ risk + " risk.");
						start = 2;
						losses = 0;
						inBadStreak = false;
						highscore = 0;
					}

					while (true) {
						try {
							if (balanceListener != null) {
								balanceListener.setBalance(bet.getBalance());
							}
							// Thread.sleep(1000 / 6);
							if (inBadStreak) {
								bet = dicer.bet(start, .498f);
							} else {
								bet = dicer.bet(500, .916f);
							}
						} catch (Exception e) {
							continue;
						}
						broadcastBet(bet);
						break;
					}
				}

			}
		});
		t.setDaemon(true);
		t.start();
	}

	public long findBestRisk(long start2, float multiplier, int steps, long l) {
		long totalRisk = getDifficulty(start2, multiplier, steps);
		while (totalRisk > l) {
			start2 *= .999;
			totalRisk = getDifficulty(start2, multiplier, steps);
		}
		return start2;
	}

	public long getDifficulty(long start2, float multiplier, int steps) {
		long total = 0;
		float mult = 1;
		int i;
		for (i = 0; i <= steps - 1; i++) {
			total += start2 * mult;
			mult *= multiplier;
		}
		return total;
	}

	public void stop(String aReason) {
		running = false;
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
	private void broadcastBetSizeChanged(long start2) {
		for(BettingStrategyListener listener: listeners){
			listener.betSizeChanged(start2);
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

}
