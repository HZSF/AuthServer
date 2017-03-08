package com.weiwei.security.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.weiwei.common.ErrorCode;
import com.weiwei.security.dao.UserDao;
import com.weiwei.security.dao.UserInfoDao;
import com.weiwei.security.dto.GeneralResponse;
import com.weiwei.security.dto.UserDto;
import com.weiwei.table.UserInfo;

@RestController
@RequestMapping("register")
public class RegisterController {

	final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private UserInfoDao userInfoDao;

	@ResponseBody
	@RequestMapping(method = RequestMethod.POST)
	public GeneralResponse register(@RequestBody @Valid UserDto userDto) {
		if (userDao.findByUsername(userDto.getUsername()).isPresent()) {
			return new GeneralResponse("username already exist!", ErrorCode.USERNAME_EXISTING);
		}
		List<UserInfo> userInfoList = userInfoDao.findByPhone(userDto.getPhone());
		if (userInfoList != null && userInfoList.size() > 0) {
			return new GeneralResponse("phone number already been registered!", ErrorCode.PHONE_REGISTERED);
		}
		userInfoList = userInfoDao.findByEmail(userDto.getEmail());
		if (userInfoList != null && userInfoList.size() > 0) {
			return new GeneralResponse("email address already been registered!", ErrorCode.EMAIL_REGISTERED);
		}
		return new GeneralResponse("", ErrorCode.OK);
	}

}
