package com.weiwei.table;

/*
 * DB Table: trade.user_info
 */

public class UserInfo {
	private Long id;
	private Long userId;
	private String phone;
	private String email;
	private String address;
	private String company;

	public UserInfo() {

	}

	public UserInfo(Long userId, String phone, String email, String address, String company) {
		super();
		this.userId = userId;
		this.phone = phone;
		this.email = email;
		this.address = address;
		this.company = company;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
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
