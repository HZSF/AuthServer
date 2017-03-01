package com.weiwei.security.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.weiwei.security.dao.UserDao;
import com.weiwei.security.vo.User;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserDao userDao;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> useroptional = userDao.findByUsername(username);
		if (useroptional.isPresent()) {
			return useroptional.get();
		} else {
			throw new UsernameNotFoundException("username {" + username + "} not found!");
		}
	}

}
