package com.weiwei.security.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("register")
public class RegisterController {
	
	@ResponseBody
	@RequestMapping(value = "/init")
	public String register() {
		return "hello register";
	}
}
