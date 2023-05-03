package ua.southpost.resource.backup.web.model.forms;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

/**
 * Search form for email.
 * Created by mykola on 31.10.16.
 */
public class FindUserByEmailForm {
	@Email
	@NotEmpty
	private String email;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
