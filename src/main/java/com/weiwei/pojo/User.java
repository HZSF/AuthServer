package com.weiwei.pojo;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

public class User implements UserDetails {
	
	private static final long serialVersionUID = -5580847214743422145L;

	public static UserDetails create(String username, String password, String... authorities) {
		return new User(username, password, authorities);
	}
	
	public User() {
		this("", "");
	}

	private Long id;
	private Collection<GrantedAuthority> authorities;
	private String password;
	private String username;

	@SuppressWarnings("unchecked")
	private User(String username, String password) {
		this(username, password, Collections.EMPTY_LIST);
	}
	
	private User(String username, String password, String...authorities) {
		this.username = username;
		this.password = password;
		this.authorities = AuthorityUtils.createAuthorityList(authorities);
	}
	
	private User(String username, String password, Collection<GrantedAuthority> authorities) {
		super();
		this.username = username;
		this.password = password;
		this.authorities = authorities;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public void setAutorities(String authorityString) {
		this.authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(authorityString);
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}
