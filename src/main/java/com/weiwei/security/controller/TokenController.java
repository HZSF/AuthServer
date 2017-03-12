package com.weiwei.security.controller;

import java.security.Principal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.ConsumerTokenServices;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.weiwei.common.Constants;
import com.weiwei.common.ErrorCode;
import com.weiwei.security.dto.GeneralResponse;

@RestController
@RequestMapping(value = "token")
public class TokenController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private AuthorizationServerTokenServices authorizationServerTokenServices;

	@Autowired
	private ConsumerTokenServices consumerTokenServices;

	@RequestMapping(value = "revoke", method = RequestMethod.GET)
	@ResponseBody
	public GeneralResponse revoketoken(Principal principal) {
		OAuth2Authentication oAuth2Authentication = (OAuth2Authentication) principal;
		OAuth2AccessToken accessToken = authorizationServerTokenServices.getAccessToken(oAuth2Authentication);
		consumerTokenServices.revokeToken(accessToken.getValue());
		logger.info("User {} logout.", principal.getName());
		return new GeneralResponse(Constants.TASK_SUCCESS, ErrorCode.OK);
	}

}
