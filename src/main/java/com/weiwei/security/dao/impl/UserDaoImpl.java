package com.weiwei.security.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.weiwei.security.common.Constants;
import com.weiwei.security.dao.UserDao;
import com.weiwei.security.vo.User;

@Service
public class UserDaoImpl implements UserDao {

	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public User findByUsername(String username) {
		String sql = "SELECT * FROM " + Constants.DBNAME_TRADE + "." + Constants.T_USER + " WHERE username = ?";
		return jdbcTemplate.queryForObject(sql, new Object[]{username}, new BeanPropertyRowMapper<User>(User.class));
	}

}
