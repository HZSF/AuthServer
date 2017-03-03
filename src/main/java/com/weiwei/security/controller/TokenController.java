package com.weiwei.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.exceptions.InvalidClientException;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TokenController {

	@Autowired
	private DefaultTokenServices defaultTokenServices;

	@RequestMapping(value = "/oauth/token/revoke", method = RequestMethod.POST)
	public @ResponseBody void create(@RequestParam("token") String value) throws InvalidClientException {
		defaultTokenServices.revokeToken(value);
	}

}
