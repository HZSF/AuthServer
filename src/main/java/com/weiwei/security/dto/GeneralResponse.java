package com.weiwei.security.dto;

public class GeneralResponse {
	private Object data;
	private int errCode;

	public GeneralResponse(Object data, int errCode) {
		super();
		this.errCode = errCode;
		this.data = data;
	}

	public int getErrCode() {
		return errCode;
	}

	public void setErrCode(int errCode) {
		this.errCode = errCode;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

}
