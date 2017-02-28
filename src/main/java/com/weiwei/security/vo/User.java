package com.weiwei.security.vo;

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

	private final Collection<GrantedAuthority> authorities_;
	private final String password_;
	private final String username_;
	
	@SuppressWarnings("unchecked")
	private User(String username, String password) {
		this(username, password, Collections.EMPTY_LIST);
	}
	
	private User(String username, String password, String...authorities) {
		username_ = username;
		password_ = password;
		authorities_ = AuthorityUtils.createAuthorityList(authorities);
	}
	
	private User(String username, String password, Collection<GrantedAuthority> authorities) {
		super();
		username_ = username;
		password_ = password;
		authorities_ = authorities;
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities_;
	}

	@Override
	public String getPassword() {
		return password_;
	}

	@Override
	public String getUsername() {
		return username_;
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

}
