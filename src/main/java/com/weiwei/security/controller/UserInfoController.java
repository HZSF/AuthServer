package com.weiwei.security.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.weiwei.common.Constants;
import com.weiwei.common.ErrorCode;
import com.weiwei.security.dto.GeneralResponse;
import com.weiwei.security.dto.UserDto;

@RestController
@RequestMapping("user")
public class UserInfoController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@RequestMapping(value = "update", method = RequestMethod.PATCH)
	public GeneralResponse updateAddress(@RequestBody UserDto userDto) {
		String username = SecurityContextHolder.getContext().getAuthentication().getName();
		logger.info("User \"{}\" update personal info as \"{}\"", username, userDto.toString());
		return new GeneralResponse(Constants.TASK_SUCCESS, ErrorCode.OK);
	}
	
}
