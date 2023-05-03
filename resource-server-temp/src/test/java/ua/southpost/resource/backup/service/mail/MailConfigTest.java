package ua.southpost.resource.backup.service.mail;

import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import ua.southpost.resource.backup.WhatAndWhereApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringJUnit4ClassRunner.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@SpringBootTest(
		webEnvironment = SpringBootTest.WebEnvironment.MOCK,
		classes = WhatAndWhereApplication.class
)
public class MailConfigTest {
	@Autowired
	private HttpTransport httpTransport;
	@Autowired
	private JsonFactory jsonFactory;
	@Autowired
	private GmailCredentials gmailCredentials;

	@Test
	public void httpTransport() {
		assertNotNull(httpTransport);
	}

	@Test
	public void jsonFactory() {
		assertNotNull(jsonFactory);
	}

	@Test
	public void gmailCredentials() {
		assertNotNull(gmailCredentials);
	}
}