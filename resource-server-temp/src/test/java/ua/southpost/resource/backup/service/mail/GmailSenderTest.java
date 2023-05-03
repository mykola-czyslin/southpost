package ua.southpost.resource.backup.service.mail;

import com.google.api.services.gmail.Gmail;
import ua.southpost.resource.backup.util.MessageBuilder;
import org.hamcrest.core.Is;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.MailPreparationException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import javax.mail.internet.AddressException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ActiveProfiles({"test"})
@RunWith(SpringRunner.class)
@SpringBootTest(
		webEnvironment = SpringBootTest.WebEnvironment.MOCK,
		classes = TestMailConfig.class
)
public class GmailSenderTest {
	private static final String FROM_ADDRESS_COM = "from@address.com";
	private static final SimpleMailMessage TEST_MESSAGE = new MessageBuilder()
			.withText("test message")
			.withSubj("test")
			.withFrom(FROM_ADDRESS_COM)
			.withTo("first.to@gmail.com")
			.withTo("second.to@gmail.com")
			.build();
	private static final SimpleMailMessage TEST_MESSAGE_2 = new MessageBuilder()
			.withSubj("second test message")
			.withText("This is the second test message")
			.withFrom(FROM_ADDRESS_COM)
			.withTo("first.to@gmail.com")
			.withTo("second.to@gmail.com")
			.build();
	private static final String EXCEPTION_MESSAGE = "*** test ***";
	@Autowired
	private GmailSender gmailSender;

	private GmailSender.GmailMessageSender messageSender;
	private Gmail gmail;
	private MimeMessage emailMessage;

	@Rule
	public final ExpectedException exception = ExpectedException.none();

	@Before
	public void setUp() throws Exception {
		messageSender = null;
		gmail = null;
		emailMessage = null;
		when(gmailSender.createGmailMessage(TEST_MESSAGE)).thenAnswer((Answer<MimeMessage>) inv -> emailMessage = (MimeMessage) inv.callRealMethod());
		when(gmailSender.createGmailMessageSender()).thenAnswer((Answer<GmailSender.GmailMessageSender>) invocation -> messageSender = (GmailSender.GmailMessageSender) spy(invocation.callRealMethod()));
		when(gmailSender.createGmail()).thenAnswer((Answer<Gmail>) invocation -> gmail = spy((Gmail) invocation.callRealMethod()));
	}

	@Test
	public void testSendSimpleMessageHappyPath() throws Exception {

		gmailSender.send(TEST_MESSAGE);

		assertNotNull(messageSender);
		assertNotNull(gmail);
		assertNotNull(emailMessage);
		verify(messageSender).send(TEST_MESSAGE);
		verify(gmailSender).createGmailMessage(TEST_MESSAGE);
		verify(gmailSender, atLeastOnce()).createGmailMessageSender();
		verify(gmail).users();
		verify(gmailSender).createMessageWithEmail(emailMessage);
	}

	@Test
	public void testSendSeveralSimpleMessagesHappyPath() throws Exception {

		gmailSender.send(TEST_MESSAGE, TEST_MESSAGE_2);

		assertNotNull(messageSender);
		assertNotNull(gmail);
		assertNotNull(emailMessage);
		verify(messageSender).send(TEST_MESSAGE);
		verify(messageSender).send(TEST_MESSAGE_2);
		verify(gmailSender).createGmailMessage(TEST_MESSAGE);
		verify(gmailSender).createGmailMessage(TEST_MESSAGE_2);
		verify(gmailSender, atLeastOnce()).createGmailMessageSender();
		verify(gmail, times(2)).users();
		verify(gmailSender).createMessageWithEmail(emailMessage);
	}

	@Test
	public void testSendSimpleMessageWithGmailMessageSenderCreationFailure() throws Exception {
		final AddressException addressException = new AddressException(EXCEPTION_MESSAGE);
		doThrow(addressException).when(gmailSender).createGmailMessage(TEST_MESSAGE);
		exception.expect(MailPreparationException.class);
		exception.expectCause(Is.is(addressException));

		try {
			gmailSender.send(TEST_MESSAGE);
		} finally {
			assertNotNull(messageSender);
			verify(messageSender).send(TEST_MESSAGE);
			verify(gmailSender).createGmailMessage(TEST_MESSAGE);
			verify(gmailSender, atLeastOnce()).createGmailMessageSender();
		}

	}

	@Test
	public void testSendSimpleMessageWithMessageCreationFailure() throws Exception {
		final IOException ioException = new IOException(EXCEPTION_MESSAGE);
		doThrow(ioException).when(gmailSender).createMessageWithEmail(any(MimeMessage.class));
		exception.expect(MailPreparationException.class);
		exception.expectCause(Is.is(ioException));
		try {
			gmailSender.send(TEST_MESSAGE);
		} finally {
			assertNotNull(messageSender);
			verify(messageSender).send(TEST_MESSAGE);
			verify(gmailSender).createGmailMessage(TEST_MESSAGE);
			verify(gmailSender, atLeastOnce()).createGmailMessageSender();
		}
	}
}