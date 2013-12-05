package org.tsavo.dicer.coinroll;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.tsavo.dicer.Dicer;
import org.tsavo.dicer.GameInfo;

public class CoinRollDicer implements Dicer {

	public String username;
	public String password;

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

	public static RestTemplate template = new RestTemplate();

	public static HttpHeaders headers = new HttpHeaders();

	static {
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
	}

	public CoinRollDicer(String aUsername, String aPassword) {

		username = aUsername;
		password = aPassword;
	}

	public BetResult makeBet(long amount, int f) {

		MultiValueMap<String, String> bodyMap = new LinkedMultiValueMap<String, String>();
		bodyMap.add("user", username);
		bodyMap.add("password", password);
		bodyMap.add("lessthan", "" + f);
		bodyMap.add("amount", "" + amount);

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(
				bodyMap, headers);

		return template.postForEntity("https://coinroll.it/bet", request,
				BetResult.class).getBody();

	}

	public long getBalance() {
		MultiValueMap<String, String> bodyMap = new LinkedMultiValueMap<String, String>();
		bodyMap.add("user", username);
		bodyMap.add("password", password);

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(
				bodyMap, headers);

		return template
				.postForEntity("https://coinroll.it/getbalance", request,
						BalanceResult.class).getBody().getBalance();
	}

	public WithdrawResult withdraw(String address, int amount) {
		MultiValueMap<String, String> bodyMap = new LinkedMultiValueMap<String, String>();
		bodyMap.add("user", username);
		bodyMap.add("password", password);
		bodyMap.add("address", "" + address);
		bodyMap.add("amount", "" + amount);
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(
				bodyMap, headers);
		return template.postForEntity("https://coinroll.it/withdraw", request,
				WithdrawResult.class).getBody();
	}

	public DepositStatusResult getDepositStatus() {

		MultiValueMap<String, String> bodyMap = new LinkedMultiValueMap<String, String>();
		bodyMap.add("user", username);
		bodyMap.add("password", password);

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(
				bodyMap, headers);

		return template.postForEntity("https://coinroll.it/getbalance",
				request, DepositStatusResult.class).getBody();
	}

	public org.tsavo.dicer.BetResult bet(long amount, float odds) {
		BetResult result = makeBet(amount, (int) (odds * 65535));
		return new org.tsavo.dicer.BetResult(result.getId(),
				result.getAmount(), (long) (odds * 65535), result.getLucky(),
				result.isWin(), result.getBalance());
	}

	public String getDepositAddress() {
		// TODO Auto-generated method stub
		return null;
	}

	public void withdraw(String anAddress, long anAmount, String a2fa) {
		// TODO Auto-generated method stub

	}

	public GameInfo getInfo(float odds) {
		// TODO Auto-generated method stub
		return null;
	}

}
