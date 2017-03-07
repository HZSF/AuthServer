package com.weiwei.security.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.weiwei.security.dto.UserDto;

@RestController
@RequestMapping("user")
public class UserInfoController {

	final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@RequestMapping(value = "update", method = RequestMethod.PUT)
	public String update(@RequestBody @Valid UserDto userDto) {
		return "";
	}
}
