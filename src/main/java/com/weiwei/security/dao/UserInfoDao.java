package com.weiwei.security.dao;

import java.util.List;

import com.weiwei.table.UserInfo;

public interface UserInfoDao {

	List<UserInfo> findByPhone(String phone);

	List<UserInfo> findByEmail(String email);

}
