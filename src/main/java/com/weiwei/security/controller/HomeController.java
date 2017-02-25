package com.weiwei.security.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "auths")
public class HomeController {
	@RequestMapping(value = "test")
	public String vssd() {
		return "hello world!";
	}
}
