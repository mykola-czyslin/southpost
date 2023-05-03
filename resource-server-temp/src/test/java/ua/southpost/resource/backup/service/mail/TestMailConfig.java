package ua.southpost.resource.backup.service.mail;

import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.LowLevelHttpRequest;
import com.google.api.client.http.LowLevelHttpResponse;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.testing.http.MockHttpTransport;
import com.google.api.client.testing.http.MockLowLevelHttpRequest;
import com.google.api.client.testing.http.MockLowLevelHttpResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.Scope;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import static org.mockito.Mockito.spy;

@Profile({"test"})
@Configuration
public class TestMailConfig {
	private static final String RESPONSE_CONTENT = "{\n" +
			" \"id\": \"89990\",\n" +
			" \"threadId\" : \"thread-01\",\n" +
			" \"labelIds\": [ \"SENT\"],\n" +
			" \"snippet\" : \"xYz098RTbn098u9ucdkuzOZUO\"\n" +
			"}";

	@Bean
	@Scope(value = "prototype")
	GmailSender gmailSender() {
		return spy(new GmailSender());
	}

	@Bean
	HttpTransport httpTransport() {
		return new MockHttpTransport() {
			@Override
			public LowLevelHttpRequest buildRequest(String method, String url) {
				return new MockLowLevelHttpRequest() {
					@Override
					public LowLevelHttpResponse execute() {
						MockLowLevelHttpResponse response = new MockLowLevelHttpResponse() {
							@Override
							public InputStream getContent() {
								return new ByteArrayInputStream(RESPONSE_CONTENT.getBytes(StandardCharsets.UTF_8));
							}
						};
						response.setStatusCode(200);
						return response;
					}
				};
			}
		};
	}

	@Bean
	JsonFactory jsonFactory() {
		return JacksonFactory.getDefaultInstance();
	}

	@Bean
	GmailCredentials gmailCredentials() {
		return GmailCredentials.builder()
				.clientId("clientId")
				.clientSecret("clientSecret")
				.userEmail("userEMail")
				.accessToken("accessToken")
				.refreshToken("refreshToken")
				.build();
	}

}
