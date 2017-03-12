package com.weiwei.table;

/*
 * DB Table: trade.user_info
 */

public class UserInfo {
	private String username;
	private String phone;
	private String email;
	private String address;
	private String company;

	public UserInfo() {

	}

	public UserInfo(String username, String phone, String email, String address, String company) {
		super();
		this.username = username;
		this.phone = phone;
		this.email = email;
		this.address = address;
		this.company = company;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

}
