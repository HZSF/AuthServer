package com.weiwei.security.service.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import com.weiwei.common.Constants;
import com.weiwei.security.dao.UserDao;
import com.weiwei.security.dto.UserDto;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UserDao userDao;

	@Autowired
	private TransactionTemplate transactionTemplate;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<UserDetails> useroptional = userDao.findByUsername(username);
		if (useroptional.isPresent()) {
			return useroptional.get();
		} else {
			throw new UsernameNotFoundException("username {" + username + "} not found!");
		}
	}

	public void saveUserDto(UserDto userDto) {
		logger.info("Saving new user: {}", userDto.toString());
		transactionTemplate.execute(status -> {
			String sqlInsertUser = "INSERT INTO " + Constants.DBNAME_TRADE + "." + Constants.T_USER
					+ " (username, password, authorities) VALUES (?,?,?)";
			String sqlInsertUserInfo = "INSERT INTO " + Constants.DBNAME_TRADE + "." + Constants.T_USER_INFO
					+ " (username, phone, email, address, company, fullname) VALUES (?,?,?,?,?,?)";
			jdbcTemplate.update(sqlInsertUser,
					new Object[] { userDto.getUsername(), userDto.getPassword(), Constants.ROLE_USER });
			return jdbcTemplate.update(sqlInsertUserInfo, new Object[] { userDto.getUsername(), userDto.getPhone(),
					userDto.getEmail(), userDto.getAddress(), userDto.getCompany(), userDto.getFullname() });
		});
		logger.info("Complete new user saving.");
	}

}
