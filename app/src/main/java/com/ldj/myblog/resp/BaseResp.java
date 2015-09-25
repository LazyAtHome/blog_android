package com.ldj.myblog.resp;

public class BaseResp {
	public static final int OK = 0;
	public static final int FAIL = Integer.MAX_VALUE;
	protected int responseCode = Integer.MAX_VALUE;
	protected String now;
	protected String responseMsg;

	public int getResponseCode() {
		return responseCode;
	}

	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}

	public String getNow() {
		return now;
	}

	public void setNow(String now) {
		this.now = now;
	}

	public String getResponseMsg() {
		return responseMsg;
	}

	public void setResponseMsg(String responseMsg) {
		this.responseMsg = responseMsg;
	}

}
