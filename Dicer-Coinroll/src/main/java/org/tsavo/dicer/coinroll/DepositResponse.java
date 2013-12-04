package org.tsavo.dicer.coinroll;


public class DepositResponse {

	public String address;
	public int result;
	public String error;
	public String errorcode;
	

	public int getResult() {
		return result;
	}

	public void setResult(int result) {
		this.result = result;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
	
}
