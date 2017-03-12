package com.weiwei.security.vo;

import java.io.Serializable;

public class UsernameTuple implements Serializable {

	private static final long serialVersionUID = 3162532289673760593L;

	private String username;
	private String smsCode;

	public UsernameTuple() {

	}

	public UsernameTuple(String username, String smsCode) {
		super();
		this.username = username;
		this.smsCode = smsCode;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getSmsCode() {
		return smsCode;
	}

	public void setSmsCode(String smsCode) {
		this.smsCode = smsCode;
	}

}
