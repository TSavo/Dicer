package org.tsavo.dicer;

import org.tsavo.dicer.coinroll.CoinRollDicer;
import org.tsavo.dicer.local.DicerLocal;
import org.tsavo.dicer.martingale.MartingaleBettingStrategy;
import org.tsavo.dicer.rapid.RapidRecoveryBettingStrategy;

public class DicerLocalRapidRecoveryExample {

	//static Dicer dicer = new CoinRollDicer("f887-6853-6765", "0BoDZH8wDiV5LZ");
	static DicerLocal dicer = new DicerLocal();
	static RapidRecoveryBettingStrategy strategy = new RapidRecoveryBettingStrategy(dicer);
	
	public static void main(String[] args){
		dicer.setBalance(1000000);
		BettingStrategyListener listener = new BettingStrategyListener() {
			
			public void stopped(String aReason) {
				// TODO Auto-generated method stub
				
			}
			
			public void started() {
				// TODO Auto-generated method stub
				
			}
			
			public void oddsChanged(float theOdds) {
				// TODO Auto-generated method stub
				
			}
			
			public void betSizeChanged(int anAmount) {
				// TODO Auto-generated method stub
				
			}
			
			public void betMade(BetResult aResult) {
				System.out.println(aResult.isWin() + " " + aResult.getBalance());
				if(aResult.getBalance() > 10000000 || aResult.getBalance() < 10){
					System.exit(0);
				}
			}
		};
		strategy.addBettingStrategyListener(listener);

		strategy.start();
		
		while(strategy.running){
			
		}
	}
	
}
