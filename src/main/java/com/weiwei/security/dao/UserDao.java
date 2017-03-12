package com.weiwei.security.dao;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;

public interface UserDao {

	Optional<UserDetails> findByUsername(String username);
	
}
