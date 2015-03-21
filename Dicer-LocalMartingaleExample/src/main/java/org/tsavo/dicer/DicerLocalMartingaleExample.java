package org.tsavo.dicer;

import org.tsavo.dicer.coinroll.CoinRollDicer;

import com.tsavo.dicer.paroli.ParoliBettingStrategy;

public class DicerLocalMartingaleExample {

//	static DicerLocal dicer = new DicerLocal();
	static Dicer dicer = new CoinRollDicer("f887-6853-6765",
			"0BoDZH8wDiV5LZ");

	static ParoliBettingStrategy strategy = new ParoliBettingStrategy(dicer);
	
	public static void main(String[] args){
		//dicer.setBalance(2755712);
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
			
			public void betMade(BetResult aResult) {
				System.out.println(aResult.isWin() + (aResult.isWin() ? "  " : " ") + aResult.getBalance() + " (" + aResult.getAmount() + ")");
			}

			public void betSizeChanged(long start) {
				// TODO Auto-generated method stub
				
			}
		};
		strategy.addBettingStrategyListener(listener);
		strategy.setStartingBet(1);
		strategy.start();
		
		while(strategy.running){
			
		}
	}
}
