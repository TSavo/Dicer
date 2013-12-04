package org.tsavo.dicer;

public class GameInfo {

	int lessthan;
	int minimumBet;
	int maximumBet;
	float multiplier;
	float odds;
	float houseEdge;
	public GameInfo() {
		super();
		// TODO Auto-generated constructor stub
	}
	public GameInfo(int lessthan, int minimumBet, int maximumBet,
			float multiplier, float odds, float houseEdge) {
		super();
		this.lessthan = lessthan;
		this.minimumBet = minimumBet;
		this.maximumBet = maximumBet;
		this.multiplier = multiplier;
		this.odds = odds;
		this.houseEdge = houseEdge;
	}
	public int getLessthan() {
		return lessthan;
	}
	public void setLessthan(int lessthan) {
		this.lessthan = lessthan;
	}
	public int getMinimumBet() {
		return minimumBet;
	}
	public void setMinimumBet(int minimumBet) {
		this.minimumBet = minimumBet;
	}
	public int getMaximumBet() {
		return maximumBet;
	}
	public void setMaximumBet(int maximumBet) {
		this.maximumBet = maximumBet;
	}
	public float getMultiplier() {
		return multiplier;
	}
	public void setMultiplier(float multiplier) {
		this.multiplier = multiplier;
	}
	public float getOdds() {
		return odds;
	}
	public void setOdds(float odds) {
		this.odds = odds;
	}
	public float getHouseEdge() {
		return houseEdge;
	}
	public void setHouseEdge(float houseEdge) {
		this.houseEdge = houseEdge;
	}
	
	
	
	
}
