package com.weiwei.security.dao.impl;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.weiwei.common.Constants;
import com.weiwei.pojo.User;
import com.weiwei.security.dao.UserDao;

@Service
public class UserDaoImpl implements UserDao {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public Optional<UserDetails> findByUsername(String username) {
		String sql = "SELECT * FROM " + Constants.DBNAME_TRADE + "." + Constants.T_USER + " WHERE username = ?";
		UserDetails user = null;
		try {
			user = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username);
		} catch (EmptyResultDataAccessException e) {
			logger.info("{} does not exist!", username);
		}
		return Optional.ofNullable(user);
	}

}
