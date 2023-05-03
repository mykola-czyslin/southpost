package ua.southpost.resource.backup.service.mail;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.util.Base64;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.model.Message;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.MailException;
import org.springframework.mail.MailPreparationException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import static java.util.Arrays.asList;
import static java.util.Optional.ofNullable;
import static javax.mail.Message.RecipientType.TO;

@Profile({"prod", "dev"})
@Service
public class GmailSender implements MailSender {
	private final Logger logger = LoggerFactory.getLogger(GmailSender.class);
	@Value("${spring.mail.properties.gmail.api.app.name}")
	private String applicationName;

	@Resource
	private HttpTransport httpTransport;
	@Resource
	private GmailCredentials gmailCredentials;
	@Resource
	private JsonFactory jsonFactory;


	@Override
	public void send(@Nonnull SimpleMailMessage simpleMessage) throws MailException {
		final GmailMessageSender gmailMessageSender = createGmailMessageSender();
		logger.debug("Message Sender created: {}", gmailMessageSender);
		gmailMessageSender.send(simpleMessage);
	}

	@Override
	public void send(@Nonnull SimpleMailMessage... simpleMessages) throws MailException {

		final GmailMessageSender sender = createGmailMessageSender();
		Arrays.stream(simpleMessages).forEach(sender::send);
	}

	Gmail createGmail() {
		final Credential credential = authorise();
		return new Gmail.Builder(httpTransport, jsonFactory, credential)
				.setApplicationName(applicationName)
				.build();
	}

	@SuppressWarnings("deprecation")
	private Credential authorise() {

		return new GoogleCredential.Builder()
				.setTransport(httpTransport)
				.setJsonFactory(jsonFactory)
				.setClientSecrets(gmailCredentials.getClientId(), gmailCredentials.getClientSecret())
				.build()
				.setAccessToken(gmailCredentials.getAccessToken())
				.setRefreshToken(gmailCredentials.getRefreshToken());
	}

	MimeMessage createGmailMessage(SimpleMailMessage simpleMessage) throws MessagingException {
		return new EmailBuilder()
				.recipients(ofNullable(simpleMessage.getTo()).orElseThrow(() -> new MailPreparationException("No recipients")))
				.from(ofNullable(simpleMessage.getFrom()).orElseThrow(() -> new MailPreparationException("No sender")))
				.subject(simpleMessage.getSubject())
				.bodyText(simpleMessage.getText())
				.build();
	}

	Message createMessageWithEmail(MimeMessage emailContent) throws MessagingException, IOException {
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		emailContent.writeTo(buffer);

		return new Message()
				.setRaw(Base64.encodeBase64URLSafeString(buffer.toByteArray()));
	}

	GmailMessageSender createGmailMessageSender() {
		return new GmailMessageSender(createGmail());
	}

	class GmailMessageSender {
		private final Gmail gmail;

		private GmailMessageSender(Gmail gmail) {
			this.gmail = gmail;
		}

		void send(SimpleMailMessage simpleMessage) {
			try {
				final MimeMessage email = createGmailMessage(simpleMessage);

				final Message messageWithEmail = createMessageWithEmail(email);
				final Message responseMessage = gmail.users()
						.messages()
						.send(gmailCredentials.getUserEmail(), messageWithEmail)
						.execute();
				logger.debug("Response message: {}", responseMessage);
				final List<String> labelIds = responseMessage
						.getLabelIds();
				logger.debug("Label Ids: {}", labelIds);
				final boolean sent = labelIds
						.contains("SENT");
				logger.info("The {} message was sent: {}", messageWithEmail, sent);
			} catch (MessagingException | IOException e) {
				logger.error("Fail to initialize service", e);
				throw new MailPreparationException(e);
			}
		}

	}

	private static class EmailBuilder {
		private final List<String> recipients = Lists.newArrayList();
		private String from;
		private String subject;
		private String bodyText;

		EmailBuilder recipients(@Nonnull String[] recipients) {
			this.recipients.addAll(asList(recipients));
			return this;
		}

		EmailBuilder from(@Nonnull String from) {
			this.from = from;
			return this;
		}

		EmailBuilder subject(String subj) {
			this.subject = subj;
			return this;
		}

		EmailBuilder bodyText(String text) {
			this.bodyText = text;
			return this;
		}

		MimeMessage build() throws MessagingException {
			MimeMessage email = new MimeMessage(Session.getDefaultInstance(new Properties(), null));
			email.setFrom(new InternetAddress(ofNullable(from).orElseThrow(() -> new MailPreparationException("From Address is not specified"))));
			for (String to : recipients) {
				email.addRecipient(TO, new InternetAddress(to));
			}
			if (recipients.isEmpty()) {
				throw new MailPreparationException("There isn't any recipient");
			}
			recipients.forEach(a -> addRecipientToEmail(email, a));
			email.setSubject(subject);
			email.setText(bodyText);
			return email;
		}


		private void addRecipientToEmail(@Nonnull MimeMessage email, @Nullable String recipient) {
			try {
				email.addRecipients(TO, ofNullable(recipient).orElseThrow(() -> new MailPreparationException("Attempt to add null recipient")));
			} catch (MessagingException e) {
				throw new MailPreparationException("Fail to add recipient: " + recipient, e);
			}
		}

	}
}
