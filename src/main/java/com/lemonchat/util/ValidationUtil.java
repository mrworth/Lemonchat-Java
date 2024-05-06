package com.lemonchat.util;

import java.time.Instant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import com.lemonchat.dtos.ValidationDto;
import com.lemonchat.services.impl.PostServiceImpl;

public class ValidationUtil {
	
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(PostServiceImpl.class);
	
	private static String appId = "462015028512-6nh1a07lo9dflmv9na2d44inohgnagjg.apps.googleusercontent.com";
	
	public static boolean isAccessTokenValid(String accessToken) {
		RestTemplate restTemplate = new RestTemplate();
		ValidationDto response = restTemplate.getForObject("https://www.googleapis.com/oauth2/v3/tokeninfo?access_token="+accessToken, ValidationDto.class);
		logger.info(response.toString());
		boolean isAudCorrect = response.getAud() != null && response.getAud().equals(appId);
		boolean isAzpCorrect = response.getAzp() != null && response.getAzp().equals(appId);
		Long currentTime = Instant.now().getEpochSecond();
		boolean isExpCorrect = response.getExp() != null && response.getExp()>currentTime;
		boolean isEmailVerified = "true".equals(response.getEmail_verified());
		return isAudCorrect && isAzpCorrect && isExpCorrect && isEmailVerified;
	}
}
