/*
 * Copyright (c) 2018. Mykola Chyslin personal development.
 * All rights reserved
 */

package ua.southpost.resource.backup.web.model.forms;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

/**
 * Mail form object
 * Created by mchys on 13.03.2018.
 */
public class MailForm {
	@NotEmpty(message = "err.mail.recipient.required")
	@Email(message = "err.invalid.email.format")
	private String recipient;
	@NotEmpty(message = "err.mail.subject.empty")
	private String subject;
	@NotEmpty(message = "err.mail.text.empty")
	private String content;

	public String getRecipient() {
		return recipient;
	}

	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
