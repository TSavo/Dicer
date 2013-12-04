package org.tsavo.dicer.rapid;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

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
	int badStreak = 12;
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


	int normalRisk =200;
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

	public void setComputedRiskListener(ComputedRiskListener computedRiskListener) {
		this.computedRiskListener = computedRiskListener;
	}

	public BalanceListener getBalanceListener() {
		return balanceListener;
	}

	public void setBalanceListener(BalanceListener balanceListener) {
		this.balanceListener = balanceListener;
	}


	long lastWin = System.currentTimeMillis();




	public void diableSSLVerification() throws NoSuchAlgorithmException,
			KeyManagementException {

		class MyManager implements X509TrustManager {

			public X509Certificate[] getAcceptedIssuers() {
				return null;
			}

			public void checkClientTrusted(X509Certificate[] chain,
					String authType) throws CertificateException {
			}

			public void checkServerTrusted(X509Certificate[] chain,
					String authType) throws CertificateException {

			}
		}

		TrustManager[] managers = new TrustManager[] { new MyManager() };
		final SSLContext sslContext = SSLContext.getInstance("SSL");
		sslContext.init(null, managers, new SecureRandom());

		HttpsURLConnection.setDefaultSSLSocketFactory(sslContext
				.getSocketFactory());
		HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
			public boolean verify(String hostname, SSLSession session) {
				return true;
			}
		});
	}
	
	public void useHTTPProxy(String host, int port){
		Properties props = System.getProperties();
		props.put("http.proxyHost", host);
		props.put("http.proxyPort", "" + port);
	}

	
	public void useHTTPSProxy(String host, int port){
		Properties props = System.getProperties();
		props.put("https.proxyHost", host);
		props.put("https.proxyPort", "" + port);
	}

	
	public void start(){
		BetResult bet = dicer.bet(1, .498f);
		long highscore = bet.getBalance();
		
		while(highscore < 1000){
			highscore = dicer.getBalance();
				try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		DecimalFormat format = new DecimalFormat("#.########");
		while (true) {
			if (bet.isWin() || (bet.getBalance() - highscore) > 0) {
				
					
				if (bet.getBalance() - highscore > 0) {
					profit += bet.getBalance() - highscore;
				}
				long myWin = System.currentTimeMillis();
				if (profit >= 10000) {
					System.out
							.println(new Date()
									+ " "
									+ format.format(((float) bet.getBalance()) / 100000000)
									+ " ("
									+ format.format((profit) / 100000000)
									+ " in " + ((myWin - lastWin) / 1000)
									+ " seconds)");
					lastWin = myWin;
					// System.out.println(format.format((profit) / 100000000));
					profit = 0;
				}
				highscore = Math.max(highscore, bet.getBalance());
				long loss = highscore - bet.getBalance();
				if (loss > (bet.getBalance() / lossRisk)) {
					highscore = 0;
				}
				losses = 0;
				
				if(loss > 0){
					start = loss;
				}else{
					start = bet.getBalance() / 50000;
				}
				
				start = findBestRisk(start, multiplier, badStreak, loss > 0 ? (bet.getBalance() / lossRisk) : (bet.getBalance() / normalRisk));
				long totalRisk = getDifficulty(start, multiplier, badStreak);
				
				if(computedRiskListener != null){
					computedRiskListener.setComputedRisk(totalRisk);
				}
				if(highscoreListener != null){
					highscoreListener.setHighscore(highscore);
				}
				if(startingBetListener != null){
					startingBetListener.setStartingBet(start);
				}
				if (loss == 0 && inBadStreak) {
					inBadStreak = false;
					System.out.println("Recovered: " +bet.getBalance() + ", returning to " + totalRisk + " risk.");
				}
				
				if (loss > 0 && !inBadStreak) {
					System.out.println("Loss detected: " + bet.getBalance() + ", recovering to " + highscore + " at " + totalRisk + " risk." );
					inBadStreak = true;
				}
				
				
			} else {
				// System.out.print(".");
				losses++;
				start *= multiplier;
				if(startingBetListener != null){
					startingBetListener.setStartingBet(start);
				}
			}
			if (losses >= badStreak) {
				long risk = getDifficulty(findBestRisk(100000, multiplier,badStreak, bet.getBalance() / lossRisk), multiplier, badStreak);
				System.out.println("BAD STREAK: " + bet.getBalance() +", recovering to "+ highscore + " at " + risk + " risk.");
				start = 2;
				losses = 0;
				inBadStreak = true;
			}
			
			while (true) {
				try {
					if(balanceListener != null){
						balanceListener.setBalance(bet.getBalance());
					}
					//Thread.sleep(1000 / 6);
					if(inBadStreak){
						
						bet = dicer.bet(start, .498f);
					}
					else{
						bet = dicer.bet(30, .916f);
						if(!bet.isWin()){
							bet = dicer.bet(300, .916f);
							if(!bet.isWin()){
								bet = dicer.bet(3000, .916f);
							}
						}
					}
				} catch (Exception e) {
					continue;
				}
				break;
			}
		}
		
		
	}
	
	
	
	
	public long findBestRisk(long start2, float multiplier, int steps, long l){
		long totalRisk = getDifficulty(start2,  multiplier, steps);
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
		// TODO Auto-generated method stub
		
	}

	public void addBettingStrategyListener(BettingStrategyListener aListener) {
		listeners.add(aListener);
		
	}

	public void removeBettingStrategyListener(BettingStrategyListener aListener) {
		listeners.remove(aListener);
	}

	
	
}
