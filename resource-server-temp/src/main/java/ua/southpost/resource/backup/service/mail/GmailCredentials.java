package ua.southpost.resource.backup.service.mail;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GmailCredentials {
	private final String userEmail;
	private final String clientId;
	private final String clientSecret;
	private final String accessToken;
	private final String refreshToken;
}
