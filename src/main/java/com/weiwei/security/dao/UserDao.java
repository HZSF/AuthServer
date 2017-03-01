package com.weiwei.security.dao;

import java.util.Optional;

import com.weiwei.security.vo.User;

public interface UserDao {

	Optional<User> findByUsername(String username);

}
