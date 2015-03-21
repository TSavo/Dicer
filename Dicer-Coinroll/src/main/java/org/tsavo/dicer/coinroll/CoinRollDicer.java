package org.tsavo.dicer.coinroll;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
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
//		headers.set("accept","application/json, text/javascript, */*; q=0.01");
//		//headers.set("accept-encoding","gzip,deflate,sdch");
//		headers.set("accept-language","en-US,en;q=0.8");
//		headers.set("cookie", "__cfduid=df3ae63f79cda281df743c657f57ecb6a1385611192370; user=f887-6853-6765; password=0BoDZH8wDiV5LZ; customgames=63000%2C64000%2C59000; cf_clearance=c7109047b7b8f46c48bca456a2a123dd41026475-1392012617-604800");
//		headers.set("origin","https://coinroll.it");
//		headers.set("referer", "https://coinroll.it/play");
//		headers.set("x-requested-with","XMLHttpRequest");
//		headers.set("method", "POST");
////		headers.set("content-length","43");
//		headers.set("scheme", "https");
//		headers.set("user-agent","Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.0.1700.107 Safari/537.36");

	}

	public CoinRollDicer(String aUsername, String aPassword) {

		username = aUsername;
		password = aPassword;
		SimpleClientHttpRequestFactory rf =
			    (SimpleClientHttpRequestFactory) template.getRequestFactory();
		rf.setReadTimeout(1 * 5000);
		rf.setConnectTimeout(1 * 5000);
	}

	public BetResult makeBet(long amount, int f) {

		MultiValueMap<String, String> bodyMap = new LinkedMultiValueMap<String, String>();
		bodyMap.add("user", username);
		bodyMap.add("password", password);
		bodyMap.add("lessthan", "" + f);
		bodyMap.add("amount", "" + amount);

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(
				bodyMap, headers);
		

		
		return template.postForEntity("https://coinroll.com/bet", request,
				BetResult.class).getBody();

	}

	public long getBalance() {
		MultiValueMap<String, String> bodyMap = new LinkedMultiValueMap<String, String>();
		bodyMap.add("user", username);
		bodyMap.add("password", password);

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(
				bodyMap, headers);

		
		return template
				.postForEntity("https://coinroll.com/getbalance", request,
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
		return template.postForEntity("https://coinroll.com/withdraw", request,
				WithdrawResult.class).getBody();
	}

	public DepositStatusResult getDepositStatus() {

		MultiValueMap<String, String> bodyMap = new LinkedMultiValueMap<String, String>();
		bodyMap.add("user", username);
		bodyMap.add("password", password);

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(
				bodyMap, headers);

		return template.postForEntity("https://coinroll.com/getbalance",
				request, DepositStatusResult.class).getBody();
	}

	public org.tsavo.dicer.BetResult bet(long amount, float odds) {
		BetResult result = makeBet(amount, (int) (odds * 64000));
		return new org.tsavo.dicer.BetResult(result.getId(),
				result.getAmount(), (long) (odds * 64000), result.getLucky(),
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
