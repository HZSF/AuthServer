package com.weiwei.security.dto;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

public class UserDto implements Serializable {

	private static final long serialVersionUID = 6819818186660055586L;

	@NotNull
	@NotEmpty
	private String username;
	@NotNull
	@NotEmpty
	private String fullname;
	@NotNull
	@NotEmpty
	private String phone;
	
	private String email;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
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

}
