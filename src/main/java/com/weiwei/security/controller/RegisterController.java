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
import com.weiwei.security.exception.UserOnRegisteringException;
import com.weiwei.security.service.RegisterUserService;
import com.weiwei.table.UserInfo;

@RestController
@RequestMapping("register")
public class RegisterController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UserDao userDao;

	@Autowired
	private UserInfoDao userInfoDao;
	
	@Autowired
	RegisterUserService registerUserService;

	@ResponseBody
	@RequestMapping(method = RequestMethod.POST)
	public GeneralResponse register(@RequestBody @Valid UserDto userDto) {
		logger.info("register request: {}", userDto.toString());
		if (userDao.findByUsername(userDto.getUsername()).isPresent()) {
			logger.info("Username already exist!");
			return new GeneralResponse("Username already exist!", ErrorCode.USERNAME_EXISTING);
		}
		
		List<UserInfo> userInfoList = userInfoDao.findByPhone(userDto.getPhone());
		if (userInfoList != null && userInfoList.size() > 0) {
			logger.info("Phone number already been registered!");
			return new GeneralResponse("Phone number already been registered!", ErrorCode.PHONE_REGISTERED);
		}
		
		userInfoList = userInfoDao.findByEmail(userDto.getEmail());
		if (userInfoList != null && userInfoList.size() > 0) {
			logger.info("Email address already been registered!");
			return new GeneralResponse("Email address already been registered!", ErrorCode.EMAIL_REGISTERED);
		}
		
		String registerToken = "";
		try {
			registerToken = registerUserService.putToCache(userDto);
		} catch (UserOnRegisteringException e) {
			e.printStackTrace();
			logger.error("Someone is registering {}", userDto.getUsername());
			return new GeneralResponse("Someone is registering this username!", ErrorCode.USERNAME_REGISTERING);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("putToCache error!", e);
		}
		
		return new GeneralResponse(registerToken, ErrorCode.OK);
	}

}
