package ua.southpost.resource.backup.util;

import com.google.common.collect.Lists;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;

import javax.annotation.Nonnull;
import java.util.List;

import static org.apache.commons.collections4.CollectionUtils.isEmpty;
import static org.apache.commons.lang3.StringUtils.isBlank;

public class MessageBuilder {
	private static final String[] STRING_ARRAY_TEMPLATE = new String[0];
	private String from;
	private String subj;
	private String text;
	private final List<String> to = Lists.newArrayList();

	public MessageBuilder withTo(@Nonnull String to) {
		this.to.add(to);
		return this;
	}

	public MessageBuilder withTo(@Nonnull List<String> to) {
		this.to.addAll(to);
		return this;
	}

	public MessageBuilder withFrom(@Nonnull String from) {
		this.from = from;
		return this;
	}

	public MessageBuilder withSubj(@Nonnull String subj) {
		this.subj = subj;
		return this;
	}

	public MessageBuilder withText(@Nonnull String text) {
		this.text = text;
		return this;
	}

	public SimpleMailMessage build() {
		if(isEmpty(this.to)) {
			throw new IllegalArgumentException("No mail recipient defined");
		}
		if(isBlank(this.text)) {
			throw new IllegalArgumentException("No message text defined");
		}
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(this.to.toArray(STRING_ARRAY_TEMPLATE));
		message.setFrom(from);
		message.setSubject(this.subj);
		message.setText(this.text);
		return message;
	}

	public void buildAndSendWith(@Nonnull MailSender sender) {
		sender.send(build());
	}
}
