/*
 * Copyright (c) 2018. Mykola Chyslin personal development.
 * All rights reserved
 */

package ua.southpost.resource.backup.service.mail;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.io.IOException;
import java.security.GeneralSecurityException;

/**
 * Mailing configuration.
 * Created by mchys on 13.03.2018.
 */
@Profile({"dev", "prod"})
@Configuration
public class MailConfig {
	@Value("${spring.mail.properties.gmail.api.app.name}")
	private String applicationName;
	@Value("${spring.mail.properties.gmail.api.from.address}")
	private String userEMail;
	@Value("${spring.mail.properties.gmail.api.auth.client.id}")
	private String clientId;
	@Value("${spring.mail.properties.gmail.api.auth.client.secret}")
	private String clientSecret;
	@Value("${spring.mail.properties.gmail.api.auth.access.token}")
	private String accessToken;
	@Value("${spring.mail.properties.gmail.api.auth.refresh.token}")
	private String refreshToken;
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Bean
	HttpTransport httpTransport() {
		try {
			return GoogleNetHttpTransport.newTrustedTransport();
		} catch (GeneralSecurityException | IOException e) {
			logger.error("Fail to obtain new HttpTransport", e);
			throw new IllegalStateException("Fail to obtain new HttpTransport", e);
		}
	}

	@Bean
	JsonFactory jsonFactory() {
		return JacksonFactory.getDefaultInstance();
	}

	@Bean
	GmailCredentials gmailCredentials() {
		return GmailCredentials.builder()
				.clientId(clientId)
				.clientSecret(clientSecret)
				.userEmail(userEMail)
				.accessToken(accessToken)
				.refreshToken(refreshToken)
				.build();
	}

}
