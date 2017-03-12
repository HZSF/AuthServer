package com.weiwei.security.controller;

import java.util.List;
import java.util.Optional;
import java.util.Random;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.weiwei.common.Constants;
import com.weiwei.common.ErrorCode;
import com.weiwei.security.dao.UserDao;
import com.weiwei.security.dao.UserInfoDao;
import com.weiwei.security.dto.GeneralResponse;
import com.weiwei.security.dto.RegisterValidateRequest;
import com.weiwei.security.dto.UserDto;
import com.weiwei.security.exception.RegisterTokenInvalidException;
import com.weiwei.security.exception.SmsCodeInvalidException;
import com.weiwei.security.exception.UserOnRegisteringException;
import com.weiwei.security.service.RegisterUserService;
import com.weiwei.security.service.impl.UserDetailsServiceImpl;
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

	@Autowired
	UserDetailsServiceImpl userDetailsServiceImpl;

	@ResponseBody
	@RequestMapping(method = RequestMethod.PUT)
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

		Random random = new Random();
		StringBuilder sbsms = new StringBuilder();
		for (int i = 0; i < 6; i++) {
			sbsms.append(random.nextInt(10));
		}

		String registerToken = "";
		try {
			registerToken = registerUserService.putToCache(userDto, sbsms.toString());
		} catch (UserOnRegisteringException e) {
			logger.info("Someone is registering {}", userDto.getUsername());
			logger.error("Username on cache!", e);
			return new GeneralResponse("Someone is registering this username!", ErrorCode.USERNAME_REGISTERING);
		} catch (Exception e) {
			logger.error("putToCache error!", e);
			return new GeneralResponse("Temp saving user profile error!", ErrorCode.FATAL_ERROR);
		}

		// sendSms();

		return new GeneralResponse(registerToken, ErrorCode.OK);
	}

	@RequestMapping(value = "validate", method = RequestMethod.POST)
	public GeneralResponse registerValidate(@RequestBody @Valid RegisterValidateRequest request) {
		UserDto userDto;
		try {
			Optional<UserDto> OptionalUserDto = registerUserService.retrieveThenRemoveFromCache(request);
			userDto = OptionalUserDto.get();
		} catch (RegisterTokenInvalidException e) {
			logger.error("Invalid register token!", e);
			return new GeneralResponse("Invalid registration token!", ErrorCode.REGISTER_TOKEN_INVALID);
		} catch (SmsCodeInvalidException e) {
			logger.error("Invalid smscode!", e);
			return new GeneralResponse("Invalid sms code!", ErrorCode.SMSCODE_INVALID);
		} catch (Exception e) {
			logger.error("retrieveFromCache error!", e);
			return new GeneralResponse("Retrieve temp user profile error!", ErrorCode.FATAL_ERROR);
		}

		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String hashedPassword = passwordEncoder.encode(userDto.getPassword());
		userDto.setPassword(hashedPassword);

		userDetailsServiceImpl.saveUserDto(userDto);
		return new GeneralResponse(Constants.TASK_SUCCESS, ErrorCode.OK);
	}

}
