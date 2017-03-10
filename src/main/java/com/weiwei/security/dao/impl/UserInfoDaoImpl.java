package com.weiwei.security.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.weiwei.common.Constants;
import com.weiwei.security.dao.UserInfoDao;
import com.weiwei.table.UserInfo;

@Service
public class UserInfoDaoImpl implements UserInfoDao {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Override
	public List<UserInfo> findByPhone(String phone) {
		String sql = "SELECT * FROM " + Constants.DBNAME_TRADE + "." + Constants.T_USER_INFO + " WHERE phone = ?";
		return jdbcTemplate.query(sql, new String[] { phone }, new BeanPropertyRowMapper<UserInfo>(UserInfo.class));
	}

	@Override
	public List<UserInfo> findByEmail(String email) {
		String sql = "SELECT * FROM " + Constants.DBNAME_TRADE + "." + Constants.T_USER_INFO + " WHERE email = ?";
		return jdbcTemplate.query(sql, new String[] { email }, new BeanPropertyRowMapper<UserInfo>(UserInfo.class));
	}

}
