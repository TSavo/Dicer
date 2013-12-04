package org.tsavo.dicer;

import org.tsavo.dicer.local.DicerLocal;
import org.tsavo.dicer.martingale.MartingaleBettingStrategy;

public class DicerLocalMartingaleExample {

	static DicerLocal dicer = new DicerLocal();
	static MartingaleBettingStrategy strategy = new MartingaleBettingStrategy(dicer);
	
	public static void main(String[] args){
		dicer.setBalance(100000);
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
			}
		};
		strategy.addBettingStrategyListener(listener);
		strategy.setStartingBet(10);
		strategy.start();
		
		while(strategy.running){
			
		}
	}
	
}
