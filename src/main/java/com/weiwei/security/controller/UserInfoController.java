package com.weiwei.security.controller;

import java.util.List;

import org.apache.commons.validator.routines.EmailValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.weiwei.common.Constants;
import com.weiwei.common.ErrorCode;
import com.weiwei.security.dao.UserInfoDao;
import com.weiwei.security.dto.GeneralResponse;
import com.weiwei.security.dto.UserDto;
import com.weiwei.security.exception.EmailOnSavingException;
import com.weiwei.security.service.EmailService;
import com.weiwei.security.service.UserInfoService;
import com.weiwei.table.UserInfo;
import com.weiwei.utils.ApplicationResource;

@RestController
@RequestMapping("user")
public class UserInfoController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UserInfoDao userInfoDao;

	@Autowired
	private UserInfoService userInfoService;

	@Autowired
	private EmailService emailService;

	@RequestMapping(value = "update/email", method = RequestMethod.PUT)
	public GeneralResponse updateEmail(@RequestParam String email) {
		if (!EmailValidator.getInstance().isValid(email)) {
			return new GeneralResponse("Email address invalid!", ErrorCode.EMAIL_INVALID);
		}
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		List<UserInfo> userInfoList = userInfoDao.findByEmail(email);
		if (!CollectionUtils.isEmpty(userInfoList)) {
			return new GeneralResponse("Email address already registered!", ErrorCode.EMAIL_REGISTERED);
		}

		String confirmToken = "";
		try {
			confirmToken = userInfoService.putToCache(email, username);
		} catch (EmailOnSavingException e) {
			logger.info("Someone is verifying {}", email);
			logger.error("Email on cache!", e);
			return new GeneralResponse("Someone is verifying this email!", ErrorCode.EMAIL_VERIFYING);
		} catch (Exception e) {
			logger.error("putToCache error!", e);
			return new GeneralResponse("Temp saving email error!", ErrorCode.FATAL_ERROR);
		}

		if (StringUtils.isEmpty(confirmToken)) {
			return new GeneralResponse("Temp saving email error!", ErrorCode.FATAL_ERROR);
		}

		final String content = confirmToken;
		ApplicationResource.THREAD_POOL.submit(() -> {
			emailService.sendConfirmEmail(email, content, username);
		});

		return new GeneralResponse(Constants.TASK_SUCCESS, ErrorCode.OK);
	}

	@RequestMapping(value = "update", method = RequestMethod.PATCH)
	public GeneralResponse updateAddress(@RequestBody UserDto userDto) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		logger.info("User \"{}\" update personal info as \"{}\"", username, userDto.toString());
		return new GeneralResponse(Constants.TASK_SUCCESS, ErrorCode.OK);
	}

}
