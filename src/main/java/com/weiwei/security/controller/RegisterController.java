package com.weiwei.security.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.weiwei.security.dto.UserDto;

@RestController
@RequestMapping("register")
public class RegisterController {

	final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@ResponseBody
	@RequestMapping(method = RequestMethod.POST)
	public String register(@RequestBody @Valid UserDto userDto) {
		return "hello register";
	}

}
