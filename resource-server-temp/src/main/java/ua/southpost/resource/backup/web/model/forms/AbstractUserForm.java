package ua.southpost.resource.backup.web.model.forms;

import javax.validation.constraints.NotEmpty;

/**
 * Abstract edit user details form
 * Created by mykola on 16.11.16.
 */
public abstract class AbstractUserForm {
	@NotEmpty(message = "err.empty.login")
	private String login;
	@NotEmpty(message = "err.empty.first.name")
	private String firstName;
	@NotEmpty(message = "err.empty.last.name")
	private String lastName;

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
}
