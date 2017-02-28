package com.weiwei.security.dao;

import com.weiwei.security.vo.User;

public interface UserDao {

	User findByUsername(String username);

}
