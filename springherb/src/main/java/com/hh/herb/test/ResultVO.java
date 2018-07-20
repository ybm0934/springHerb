package com.hh.herb.test;

public class ResultVO {
	private String message;
	private MemoVO result;
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public MemoVO getResult() {
		return result;
	}
	public void setResult(MemoVO result) {
		this.result = result;
	}
	
	@Override
	public String toString() {
		return "ResultVO [message=" + message + ", result=" + result + "]";
	}
	
	
}
