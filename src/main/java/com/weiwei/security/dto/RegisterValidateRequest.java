package com.weiwei.security.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

public class RegisterValidateRequest implements Serializable {

	private static final long serialVersionUID = 1005651173040151494L;

	@NotNull
	@NotEmpty
	private String vtoken;

	@NotNull
	@NotEmpty
	private String smscode;

	public String getVtoken() {
		return vtoken;
	}

	public void setVtoken(String vtoken) {
		this.vtoken = vtoken;
	}

	public String getSmscode() {
		return smscode;
	}

	public void setSmscode(String smscode) {
		this.smscode = smscode;
	}

}
